package com.wix.server.web.admin;

import com.wix.common.model.*;
import com.wix.server.manager.RouteConfigurationManager;
import com.wix.server.manager.RouteExecutionManager;
import com.wix.server.manager.UserManager;
import com.wix.server.persistence.RouteExecution;
import com.wix.server.persistence.RouteRun;
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

import java.net.CacheRequest;
import java.util.*;

/**
 * Created by racastur on 08-11-2014.
 */
@Controller
public class AdminRoutesController {

    @Autowired
    private RouteConfigurationManager routeConfigurationManager;

    @Autowired
    private RouteExecutionManager routeExecutionManager;

    @Autowired
    private UserManager userManager;

    @RequestMapping("/admin/routes")
    public ModelAndView getAllRoutes() {

        List<RouteDTO> routes = routeConfigurationManager.getAllRoutes();

        List<RouteInfo> routeInfos = new ArrayList<>(routes.size());
        for (RouteDTO route : routes) {

            int numberOfStops = 0;
            for (RouteLocationDTO routeLocation : route.getRouteLocations()) {
                if (routeLocation.getRouteStop() != null) {
                    numberOfStops++;
                }
            }

            boolean runningStatus = false;
            long lastRunCompletedTimestamp = 0;

            List<RouteRunDTO> routeRuns = routeConfigurationManager.getAllRouteRuns(route.getId());
            for (RouteRunDTO routeRun : routeRuns) {
                List<RouteExecutionDTO> routeExecutions = routeExecutionManager.getRouteExecutions(routeRun.getId());
                if (routeExecutions != null && routeExecutions.size() > 0) {
                    if (routeExecutions.get(0).getEndTime() <= 0) {
                        runningStatus = true;
                    } else {
                        lastRunCompletedTimestamp = routeExecutions.get(0).getEndTime();
                    }
                }
            }

            routeInfos.add(new RouteInfo(route, numberOfStops, routeRuns, runningStatus, lastRunCompletedTimestamp));

        }

        Map<String, Object> model = new HashMap<>();
        model.put("activeTab", "routes");
        model.put("routes", routeInfos);
        model.put("userInfo", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser());

        return new ModelAndView("admin/allroutes", model);

    }

    @RequestMapping("/admin/routes/{routeId}")
    public ModelAndView getRoute(@PathVariable("routeId") String routeId) {

        RouteDTO route = routeConfigurationManager.getRoute(routeId, true);
        if (route == null) {
            // TODO throw an error
        }

        List<RouteRunDTO> routeRuns = routeConfigurationManager.getAllRouteRuns(routeId);

        Map<String, UserDTO> users = new HashMap<>();
        for (RouteRunDTO routeRun : routeRuns) {
            users.put(routeRun.getRouteExecutor().getUserId(), userManager.getUser(routeRun.getRouteExecutor().getUserId()));
        }

        Map<String, Object> model = new HashMap<>();
        model.put("activeTab", "routes");
        model.put("route", route);
        model.put("routeRuns", routeRuns);
        model.put("users", users);
        model.put("userInfo", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser());

        return new ModelAndView("admin/addEditRoute", model);

    }

    @RequestMapping("/admin/routes/add")
    public ModelAndView getAddNewRoute() {

        // TODO security check!! accessible only by admin roles

        Map<String, Object> model = new HashMap<>();
        model.put("activeTab", "routes");
        model.put("route", new RouteDTO());
        model.put("routeRuns", new ArrayList<>());
        model.put("userInfo", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser());

        return new ModelAndView("admin/addEditRoute", model);

    }

    @RequestMapping(value = "/admin/routes", method = RequestMethod.POST)
    public ModelAndView saveRoute(@ModelAttribute("route") RouteDTO route) {

        // TODO security check!! accessible only by admin roles

        try {
            routeConfigurationManager.createRoute(route);
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

        RouteDTO route = routeConfigurationManager.getRoute(routeExecution.getRouteRunId(), true);
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