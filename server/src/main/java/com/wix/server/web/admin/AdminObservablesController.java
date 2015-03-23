package com.wix.server.web.admin;

import com.wix.common.model.ObservableDTO;
import com.wix.common.model.RouteDTO;
import com.wix.common.model.RouteLocationDTO;
import com.wix.common.model.RouteStopDTO;
import com.wix.server.manager.ObservableConfigurationManager;
import com.wix.server.manager.RouteConfigurationManager;
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

import java.util.*;

/**
 * Created by racastur on 08-11-2014.
 */
@Controller
public class AdminObservablesController {

    @Autowired
    private RouteConfigurationManager routeConfigurationManager;

    @Autowired
    private ObservableConfigurationManager observableConfigurationManager;

    @RequestMapping("/admin/observables")
    public ModelAndView getAllObservables() {

        List<RouteDTO> routes = routeConfigurationManager.getAllRoutes();

        Map<String, RouteDTO> routesMap = new HashMap<>();
        Map<String, RouteStopDTO> routeStopsMap = new HashMap<>();
        for (RouteDTO route : routes) {
            routesMap.put(route.getId(), route);
            for (RouteLocationDTO location : route.getRouteLocations()) {
                if (location.getRouteStop() != null) {
                    routeStopsMap.put(location.getId(), location.getRouteStop());
                }
            }
        }

        List<ObservableDTO> observables = observableConfigurationManager.getAllObservables();

        Collections.sort(observables, new Comparator<ObservableDTO>() {
            @Override
            public int compare(ObservableDTO o1, ObservableDTO o2) {
                return 0;
            }
        });

        Map<String, Object> model = new HashMap<>();
        model.put("activeTab", "observables");
        model.put("observables", observables);
        model.put("routes", routesMap);
        model.put("routeStops", routeStopsMap);
        model.put("userInfo", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser());

        return new ModelAndView("admin/allobservables", model);

    }

    @RequestMapping("/admin/observables/{observableId}")
    public ModelAndView getObservable(@PathVariable("observableId") String observableId) {

        ObservableDTO observable = observableConfigurationManager.getObservable(observableId);
        if (observable == null) {
            // TODO throw an error
        }

        Map<String, Object> model = new HashMap<>();
        model.put("activeTab", "observables");
        model.put("observable", observable);
        model.put("userInfo", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser());

        return new ModelAndView("admin/addEditObservable", model);

    }

    @RequestMapping(value = "/admin/observables/add")
    public ModelAndView getNewObservable() {

        // TODO security check!! accessible only by admin roles

        Map<String, Object> model = new HashMap<>();
        model.put("activeTab", "observables");
        model.put("observable", new ObservableDTO());
        model.put("userInfo", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser());

        return new ModelAndView("admin/addEditObservable", model);

    }

    @RequestMapping(value = "/admin/observables", method = RequestMethod.POST)
    public ModelAndView saveAccount(@ModelAttribute("observable") ObservableDTO observable) {

        // TODO security check!! accessible only by admin roles

        try {
            observableConfigurationManager.createUpdateObservable(observable);
        } catch (Exception e) {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("observable", observable);
            List<String> errors = new ArrayList<String>();
            errors.add("System error");
            model.put("errors", errors);
            return new ModelAndView("admin/addEditObservable", "model", model);
        }

        return new ModelAndView(new RedirectView("/admin/observables"));

    }

}