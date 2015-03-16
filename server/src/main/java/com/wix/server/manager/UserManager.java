package com.wix.server.manager;

import com.wix.common.model.UserDTO;
import com.wix.server.exception.DuplicateEntityException;
import com.wix.server.exception.UnknownEntityException;
import com.wix.server.exception.ValidationException;
import com.wix.server.persistence.PMF;
import com.wix.server.persistence.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by racastur on 14-03-2015.
 */
@Component("userManager")
public class UserManager {

    private static final Logger log = Logger.getLogger(UserManager.class.getName());

    @Autowired
    private WixUserDetailsService userDetailsService;

    public UserDTO createUpdateUser(UserDTO dto) throws ValidationException, UnknownEntityException, DuplicateEntityException {

        if (dto == null ||
                !StringUtils.hasText(dto.getOrganizationId()) || !StringUtils.hasText(dto.getUserName()) ||
                !StringUtils.hasText(dto.getEmailId()) || !StringUtils.hasText(dto.getPassword())) {
            throw new ValidationException("invalid user data");
        }

        if (StringUtils.hasText(dto.getId())) {
            return updateUser(dto);
        } else {
            return createUser(dto);
        }

    }

    private UserDTO createUser(UserDTO dto) throws DuplicateEntityException {

        log.log(Level.INFO, String.format("Creating user [%s][%s]", dto.getUserName(), dto.getEmailId()));

        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {

            User user = getUserByUserName(dto.getUserName());
            if (user != null) {
                throw new DuplicateEntityException("user with given name already exists");
            }

            user = getUserByEmail(dto.getEmailId());
            if (user != null) {
                throw new DuplicateEntityException("user with given email already exists");
            }

            user = new User(dto);
            user.setPassword(userDetailsService.getPasswordEncoder().encode(dto.getPassword()));

            pm.makePersistent(user);

            return user.getDTO();

        } finally {
            try {
                pm.close();
            } catch (Exception e) {
                // ignore
            }
        }

    }

    private UserDTO updateUser(UserDTO dto) throws UnknownEntityException, DuplicateEntityException {

        log.log(Level.INFO, String.format("Updating user [%s][%s][%s]", dto.getId(), dto.getUserName(), dto.getEmailId()));

        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {

            User user = pm.getObjectById(User.class, dto.getId());
            if (user == null) {
                throw new UnknownEntityException("unknown user");
            }

            User userBySameName = getUserByUserName(dto.getUserName());
            if (userBySameName != null && !user.getId().equals(userBySameName.getId())) {
                throw new DuplicateEntityException("a different user with given name already exists");
            }

            User userBySameEmail = getUserByEmail(dto.getEmailId());
            if (userBySameEmail != null && !user.getId().equals(userBySameEmail.getId())) {
                throw new DuplicateEntityException("a different user with given email already exists");
            }

            user.setOrganizationId(dto.getOrganizationId());
            user.setUserName(dto.getUserName());

            user.setPassword(userDetailsService.getPasswordEncoder().encode(dto.getPassword()));

            user.setEmailId(dto.getEmailId());
            user.setAdminRole(dto.isAdminRole());

            return user.getDTO();

        } finally {
            try {
                pm.close();
            } catch (Exception e) {
                // ignore
            }
        }

    }

    public List<UserDTO> getUsers() {

        PersistenceManager pm = PMF.get().getPersistenceManager();

        Extent<User> users = pm.getExtent(User.class);
        if (users == null) {
            return new ArrayList<>();
        }

        List<UserDTO> dtos = new ArrayList<>();
        for (User user : users) {
            dtos.add(user.getDTO());
        }

        return dtos;

    }

    public UserDTO getUser(String userId) {

        PersistenceManager pm = PMF.get().getPersistenceManager();

        User user = pm.getObjectById(User.class, userId);
        if (user == null) {
            return null;
        }

        return user.getDTO();

    }

    public User getUserByUserName(String userName) {

        PersistenceManager pm = PMF.get().getPersistenceManager();

        Query q = pm.newQuery(User.class);
        q.setFilter("userName == userNameParam");
        q.declareParameters("String userNameParam");

        try {

            List<User> results = (List<User>) q.execute(userName);
            if (results == null || results.isEmpty() || results.size() > 1) {
                return null;
            }

            return results.get(0);

        } finally {
            q.closeAll();
        }

    }

    public User getUserByEmail(String emailId) {

        PersistenceManager pm = PMF.get().getPersistenceManager();

        Query q = pm.newQuery(User.class);
        q.setFilter("emailId == emailIdParam");
        q.declareParameters("String emailIdParam");

        try {

            List<User> results = (List<User>) q.execute(emailId);
            if (results == null || results.isEmpty() || results.size() > 1) {
                return null;
            }

            return results.get(0);

        } finally {
            q.closeAll();
        }

    }

}
