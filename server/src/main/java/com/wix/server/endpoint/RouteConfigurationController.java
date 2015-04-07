package com.wix.server.endpoint;

import com.wix.common.model.RouteDTO;
import com.wix.common.model.RouteRunDTO;
import com.wix.server.manager.RouteConfigurationManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by racastur on 31-10-2014.
 */
@RestController
@RequestMapping("/services/v1/*")
public class RouteConfigurationController {

    private static final Logger log = Logger.getLogger(RouteConfigurationController.class.getName());

    @Autowired
    private RouteConfigurationManager routeConfigurationManager;

    @RequestMapping(value = "routes", method = RequestMethod.POST)
    public RouteDTO createNewRoute(@RequestBody RouteDTO routeDTO) {
        try {
            return routeConfigurationManager.createRoute(routeDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "routes", method = RequestMethod.GET)
    public List<RouteDTO> getAllRoutes() {
        return routeConfigurationManager.getAllRoutes();
    }

    @RequestMapping(value = "routes/{routeId}", method = RequestMethod.GET)
    public RouteDTO getRoute(@PathVariable("routeId") String routeId) {
        return routeConfigurationManager.getRoute(routeId, false);
    }

    @RequestMapping(value = "routes/{routeId}/routeruns", method = RequestMethod.POST)
    public RouteRunDTO configureRouteRun(@PathVariable("routeId") String routeId, @RequestBody RouteRunDTO routeRunDTO) {
        try {
            return routeConfigurationManager.configureRouteRun(routeId, routeRunDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "users/{userId}/routeruns", method = RequestMethod.GET)
    public List<RouteRunDTO> getAssignedRouteRuns(@PathVariable("userId") String userId) {
        return routeConfigurationManager.getAssignedRouteRuns(userId);
    }

    @RequestMapping(value = "routes/{routeId}/routeruns", method = RequestMethod.GET)
    public List<RouteRunDTO> getAllRouteRuns(@PathVariable("routeId") String routeId) {
        return routeConfigurationManager.getAllRouteRuns(routeId);
    }

    @RequestMapping(value = "routes/{routeId}/routeruns/{routeRunId}", method = RequestMethod.GET)
    public RouteRunDTO getRouteRun(@PathVariable("routeId") String routeId, @PathVariable("routeRunId") String routeRunId) {
        return routeConfigurationManager.getRouteRun(routeId, routeRunId);
    }

}
