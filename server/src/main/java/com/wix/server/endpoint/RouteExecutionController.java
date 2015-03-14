package com.wix.server.endpoint;

import com.wix.common.model.RouteExecutionDTO;
import com.wix.common.model.RouteExecutionLocationDTO;
import com.wix.common.model.RouteExecutionStatus;
import com.wix.server.manager.RouteExecutionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by racastur on 31-10-2014.
 */
@RestController
@RequestMapping("/services/v1/*")
public class RouteExecutionController {

    private static final Logger log = Logger.getLogger(RouteExecutionController.class.getName());

    @Autowired
    private RouteExecutionManager routeExecutionManager;

    @RequestMapping(value = "routeexecutions/{routeExecutionId}/status", method = RequestMethod.POST)
    public RouteExecutionDTO updateRouteExecutionStatus(@RequestHeader("userId") String userId,
                                                        @PathVariable("routeExecutionId") String routeExecutionId,
                                                        @RequestParam("executionStatus") RouteExecutionStatus routeExecutionStatus) {
        return routeExecutionManager.updateRouteExecutionStatus(userId, routeExecutionId, routeExecutionStatus);
    }

    @RequestMapping(value = "routeexecutions/{routeExecutionId}/location", method = RequestMethod.POST)
    public void postRouteExecutionLocation(@RequestHeader("userId") String userId,
                                           @PathVariable("routeExecutionId") String routeExecutionId,
                                           @RequestBody RouteExecutionLocationDTO routeExecutionLocationDTO) {
        routeExecutionManager.postRouteExecutionLocation(userId, routeExecutionId, routeExecutionLocationDTO);
    }

    @RequestMapping(value = "routeexecutions", method = RequestMethod.GET)
    public List<RouteExecutionDTO> getAssignedRouteExecutions(@RequestHeader("userId") String userId) {
        return routeExecutionManager.getAssignedRouteExecutions(userId);
    }

    @RequestMapping(value = "routeexecutions/{routeExecutionId}", method = RequestMethod.GET)
    public RouteExecutionDTO getAssignedRouteExecution(@RequestHeader("userId") String userId,
                                                       @PathVariable("routeExecutionId") String routeExecutionId) {
        return routeExecutionManager.getAssignedRouteExecution(userId, routeExecutionId);
    }

    /**
     * Whenever a new listener needs to get added to an instance of RouteExecution. Basically some observer wants to see
     * the route as it is being executed
     * @param userId
     * @param routeExecutionId
     * @param listenerChannel
     */
    @RequestMapping(value = "routeexecutions/{routeExecutionId}/listener", method = RequestMethod.POST)
    public void postRouteExecutionListener(@RequestHeader("userId") String userId,
                                           @PathVariable("routeExecutionId") String routeExecutionId,
                                           @RequestParam String listenerChannel) {
        routeExecutionManager.postRouteExecutionListener(userId, routeExecutionId, listenerChannel);
    }


}
