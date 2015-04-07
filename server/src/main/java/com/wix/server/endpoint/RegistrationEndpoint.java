package com.wix.server.endpoint;

import com.wix.common.model.UserDTO;
import com.wix.server.manager.UserManager;
import com.wix.server.persistence.User;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/services/v1/*")
public class RegistrationEndpoint implements InitializingBean {

    private static final Logger log = Logger.getLogger(RegistrationEndpoint.class.getName());

    @Autowired
    private UserManager userManager;

    @Autowired
    private TestDataInitializer initializer;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public UserDTO login(@RequestParam("emailId") String emailId) {

        User user = userManager.getUserByEmail(emailId);
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

    @Override
    public void afterPropertiesSet() throws Exception {
        //initializer.init();
    }

}