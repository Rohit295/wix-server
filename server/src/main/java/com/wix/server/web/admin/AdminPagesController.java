package com.wix.server.web.admin;

import com.wix.common.model.RouteDTO;
import com.wix.common.model.RouteExecutionDTO;
import com.wix.server.manager.RouteConfigurationManager;

import com.wix.server.manager.RouteExecutionManager;
import com.wix.server.persistence.RouteExecution;
import com.wix.server.persistence.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by racastur on 08-11-2014.
 */
@Controller
public class AdminPagesController {

    @Autowired
    private RouteConfigurationManager routeConfigurationManager;

    @Autowired
    private RouteExecutionManager routeExecutionManager;

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

    @RequestMapping("/admin/routes")
    public ModelAndView getAllRoutes() {

        List<RouteDTO> routes = routeConfigurationManager.getAllRoutes();

        Map<String, Object> model = new HashMap<>();
        model.put("activeTab", "routes");
        model.put("routes", routes);
        model.put("userInfo", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser());

        return new ModelAndView("admin/allroutes", model);

    }

    @RequestMapping("/admin/routes/{routeId}")
    public ModelAndView getRoute(@PathVariable("routeId") String routeId) {

        RouteDTO route = routeConfigurationManager.getRoute(routeId, true);
        if (route == null) {
            // TODO throw an error
        }

        List<RouteExecutionDTO> routeExecutions = routeExecutionManager.getRouteExecutions(routeId);

        Map<String, Object> model = new HashMap<>();
        model.put("activeTab", "routes");
        model.put("route", route);
        model.put("routeExecutions", routeExecutions);
        model.put("userInfo", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser());

        return new ModelAndView("admin/route", model);

    }

    @RequestMapping("/admin/routeexecution/{routeExecutionId}")
    public ModelAndView getRouteExecution(@PathVariable("routeExecutionId") String routeExecutionId) {

        RouteExecutionDTO routeExecution = routeExecutionManager.getRouteExecutionById(routeExecutionId);
        if (routeExecution == null) {
            // TODO throw an error
        }

        RouteDTO route = routeConfigurationManager.getRoute(routeExecution.getRouteId(), true);
        if (route == null) {
            // TODO throw an error
        }

        Map<String, Object> model = new HashMap<>();
        model.put("activeTab", "routes");
        model.put("route", route);
        model.put("routeExecution", routeExecution);
        model.put("userInfo", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser());

        return new ModelAndView("admin/routeexecution", model);

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