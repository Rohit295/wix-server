package com.wix.server.web.admin;

import com.wix.server.persistence.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by racastur on 08-11-2014.
 */
@Controller
public class AdminPagesController {

    @RequestMapping("/login")
    public ModelAndView getLogin() {
        return new ModelAndView("login");
    }

    @RequestMapping({"/admin/index", "/admin", "/admin/"})
    public ModelAndView getIndex() {

        Map<String, Object> model = new HashMap<>();
        model.put("activeTab", "index");
        model.put("userInfo", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser());

        return new ModelAndView("admin/index", model);

    }

    @RequestMapping("/admin/analytics")
    public ModelAndView getAnalytics() {

        Map<String, Object> model = new HashMap<>();
        model.put("activeTab", "analytics");
        model.put("userInfo", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser());

        return new ModelAndView("admin/analytics", model);

    }

    @RequestMapping("/admin/console")
    public ModelAndView getAdminConsole() {
        return new ModelAndView("admin/console");
    }

}