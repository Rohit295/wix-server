package com.wix.server.manager;

import com.wix.common.model.UserDTO;
import com.wix.server.persistence.PMF;
import com.wix.server.persistence.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by racastur on 14-03-2015.
 */
@Component("userManager")
public class UserManager {

    public UserDTO createUpdateUser(UserDTO dto) {

        PersistenceManager pm = PMF.get().getPersistenceManager();

        try {

            User user = null;
            if (StringUtils.hasText(dto.getId())) {

                // existing user

                user = pm.getObjectById(User.class, dto.getId());
                if (user == null) {
                    throw new IllegalArgumentException("unknown user");
                }

                user.setUserName(dto.getUserName());
                user.setEmailId(dto.getEmailId());
                user.setAdminRole(dto.isAdminRole());

            } else {

                // new user

                user = new User(dto);

            }

            pm.makePersistent(user);

            return user.getDTO();

        } catch (Exception e) {
            // TODO
            e.printStackTrace();
            throw new RuntimeException("Unknown error");
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
            return new ArrayList<UserDTO>();
        }

        List<UserDTO> dtos = new ArrayList<UserDTO>();
        for (User user : users) {
            dtos.add(user.getDTO());
        }

        return dtos;

    }

    public UserDTO getUser(String userId) {

        PersistenceManager pm = PMF.get().getPersistenceManager();

        User user = pm.getObjectById(User.class, userId);
        if (user == null) {
            throw new IllegalArgumentException("Unknown user");
        }

        return user.getDTO();

    }

}
