package com.wix.server.endpoint;

import com.wix.common.model.UserDTO;
import com.wix.server.persistence.PMF;
import com.wix.server.persistence.User;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/services/v1/*")
public class RegistrationEndpoint {

    private static final Logger log = Logger.getLogger(RegistrationEndpoint.class.getName());

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public UserDTO login(@RequestParam("emailId") String emailId) {

        User user = findUserByEmail(emailId);
        if (user == null) {
            return null;
        }

        return user.getDTO();

    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@RequestParam("userId") String userId,
                         @RequestParam("deviceId") String deviceId,
                         @RequestParam("gcmRegistrationId") String gcmRegistrationId) {


    }

    private User findUserByEmail(String emailId) {

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

    private User findUserById(String userId) {

        PersistenceManager pm = PMF.get().getPersistenceManager();

        User user = pm.getObjectById(User.class, userId);
        if (user == null) {
            throw new IllegalArgumentException("unknown user");
        }

        return user;

    }

}