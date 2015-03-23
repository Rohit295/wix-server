package com.wix.server.web.admin;

import com.wix.common.model.RouteDTO;
import com.wix.common.model.RouteExecutionDTO;
import com.wix.server.manager.ObservableConfigurationManager;
import com.wix.server.manager.RouteConfigurationManager;
import com.wix.server.manager.RouteExecutionManager;
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
 * Created by racastur on 08-11-2014.
 */
@Controller
public class AdminRoutesController {

    @Autowired
    private RouteConfigurationManager routeConfigurationManager;

    @Autowired
    private RouteExecutionManager routeExecutionManager;

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

        List<RouteExecutionDTO> routeExecutions = null;
        try {
            routeExecutions = routeExecutionManager.getRouteExecutions(routeId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Map<String, Object> model = new HashMap<>();
        model.put("activeTab", "routes");
        model.put("route", route);
        model.put("routeExecutions", routeExecutions);
        model.put("userInfo", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser());

        return new ModelAndView("admin/addEditRoute", model);

    }

    @RequestMapping("/admin/routes/add")
    public ModelAndView getAddNewRoute() {

        // TODO security check!! accessible only by admin roles

        Map<String, Object> model = new HashMap<>();
        model.put("activeTab", "routes");
        model.put("route", new RouteDTO());
        model.put("userInfo", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser());

        return new ModelAndView("admin/addEditRoute", model);

    }

    @RequestMapping(value = "/admin/routes", method = RequestMethod.POST)
    public ModelAndView saveAccount(@ModelAttribute("route") RouteDTO route) {

        // TODO security check!! accessible only by admin roles

        try {
            routeConfigurationManager.createUpdateRoute(route);
        } catch (Exception e) {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("route", route);
            List<String> errors = new ArrayList<String>();
            errors.add("System error");
            model.put("errors", errors);
            return new ModelAndView("admin/addEditRoute", "model", model);
        }

        return new ModelAndView(new RedirectView("/admin/routes"));

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

}