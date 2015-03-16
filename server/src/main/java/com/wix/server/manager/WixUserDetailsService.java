package com.wix.server.manager;

import com.wix.server.persistence.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.jdo.PersistenceManager;

/**
 * Created by racastur on 15-03-2015.
 */
@Component("userDetailsService")
public class WixUserDetailsService implements UserDetailsService {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserManager userManager;

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user = userManager.getUserByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("unknown userName");
        }

        return new com.wix.server.persistence.UserDetails(user);

    }

}
