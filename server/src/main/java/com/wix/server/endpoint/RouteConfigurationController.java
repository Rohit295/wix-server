package com.wix.server.endpoint;

import com.wix.common.model.RouteDTO;
import com.wix.common.model.RouteExecutionDTO;
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
        return routeConfigurationManager.createUpdateRoute(routeDTO);
    }

    @RequestMapping(value = "routes/{routeId}/executions", method = RequestMethod.POST)
    public RouteExecutionDTO assignRouteExecution(@PathVariable("routeId") String routeId,
                                                  @RequestParam("userId") String userId,
                                                  @RequestParam("deviceId") String deviceId) {
        return routeConfigurationManager.assignRouteExecution(routeId, userId, deviceId);
    }

    @RequestMapping(value = "routes", method = RequestMethod.GET)
    public List<RouteDTO> getAllRoutes() {
        return routeConfigurationManager.getAllRoutes();
    }

    @RequestMapping(value = "routes/{routeId}", method = RequestMethod.GET)
    public RouteDTO getRoute(@PathVariable("routeId") String routeId) {
        return routeConfigurationManager.getRoute(routeId, false);
    }

}
