package com.wix.server.web.admin;

import com.wix.common.model.UserDTO;
import com.wix.server.manager.UserManager;
import com.wix.server.persistence.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by racastur on 14-03-2015.
 */
@Controller
public class AdminUserAccountsController {

    @Autowired
    private UserManager userManager;

    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    public ModelAndView getUsers() {

        // TODO security check!! accessible only by admin roles

        Map<String, Object> model = new HashMap<>();
        model.put("activeTab", "users");
        model.put("accounts", userManager.getUsers());
        model.put("userInfo", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser());

        return new ModelAndView("admin/users", model);

    }

    @RequestMapping(value = "/admin/users/{userId}", method = RequestMethod.GET)
    public ModelAndView getAdminAccount(@PathVariable("userId") String userId) {

        // TODO security check!! accessible only by admin roles

        Map<String, Object> model = new HashMap<>();
        model.put("activeTab", "users");
        model.put("account", userManager.getUser(userId));
        model.put("userInfo", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser());

        return new ModelAndView("admin/addeditusers", model);

    }

    @RequestMapping(value = "/admin/users/add", method = RequestMethod.GET)
    public ModelAndView getNewAdminAccount() {

        // TODO security check!! accessible only by admin roles

        Map<String, Object> model = new HashMap<>();
        model.put("activeTab", "users");
        model.put("account", new UserDTO());
        model.put("userInfo", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser());

        return new ModelAndView("admin/addeditusers", model);

    }

    @RequestMapping(value = "/admin/users", method = RequestMethod.POST)
    public ModelAndView saveAccount(@ModelAttribute("account") UserDTO account) {

        // TODO security check!! accessible only by admin roles

        try {
            userManager.createUpdateUser(account);
        } catch (Exception e) {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("account", account);
            List<String> errors = new ArrayList<String>();
            errors.add("System error");
            model.put("errors", errors);
            return new ModelAndView("admin/addeditusers", "model", model);
        }

        return new ModelAndView(new RedirectView("/admin/users"));

    }

}