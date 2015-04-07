package com.wix.server.endpoint;

import com.wix.common.model.RouteExecutionDTO;
import com.wix.common.model.RouteExecutionLocationDTO;
import com.wix.common.model.RouteExecutionStatus;
import com.wix.common.model.RouteExecutorDTO;
import com.wix.server.manager.RouteExecutionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "routeruns/{routeRunId}/execute", method = RequestMethod.POST)
    public RouteExecutionDTO startRouteRunExecution(@RequestHeader("userId") String userId,
                                                    @PathVariable("routeRunId") String routeRunId) {
        try {
            return routeExecutionManager.startRouteRunExecution(routeRunId, new RouteExecutorDTO(userId, ""));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "routeexecutions/{routeExecutionId}/status", method = RequestMethod.POST)
    public RouteExecutionDTO updateRouteExecutionStatus(@RequestHeader("userId") String userId,
                                                        @PathVariable("routeExecutionId") String routeExecutionId,
                                                        @RequestParam("executionStatus") RouteExecutionStatus routeExecutionStatus) {
        try {
            return routeExecutionManager.updateRouteExecutionStatus(routeExecutionId, routeExecutionStatus);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "routeexecutions/{routeExecutionId}/location", method = RequestMethod.POST)
    public void postRouteExecutionLocation(@RequestHeader("userId") String userId,
                                           @PathVariable("routeExecutionId") String routeExecutionId,
                                           @RequestBody RouteExecutionLocationDTO routeExecutionLocationDTO) {
        try {
            routeExecutionManager.postRouteExecutionLocation(userId, routeExecutionId, routeExecutionLocationDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Whenever a new listener needs to get added to an instance of RouteExecution. Basically some observer wants to see
     * the route as it is being executed
     *
     * @param userId
     * @param routeExecutionId
     */
    @RequestMapping(value = "routeexecutions/{routeExecutionId}/addListener", method = RequestMethod.POST)
    public void addRouteExecutionListener(@RequestHeader("userId") String userId,
                                          @PathVariable("routeExecutionId") String routeExecutionId,
                                          @RequestParam String consumerToken) {
        try {
            routeExecutionManager.manageRouteExecutionListener(userId, routeExecutionId, consumerToken, "add");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Whenever a listener needs to get removed from an instance of RouteExecution. Basically some observer is done seeing
     * the route as it is being executed
     *
     * @param userId
     * @param routeExecutionId
     */
    @RequestMapping(value = "routeexecutions/{routeExecutionId}/removeListener", method = RequestMethod.POST)
    public void removeRouteExecutionListener(@RequestHeader("userId") String userId,
                                             @PathVariable("routeExecutionId") String routeExecutionId,
                                             @RequestParam String consumerToken) {
        try {
            routeExecutionManager.manageRouteExecutionListener(userId, routeExecutionId, consumerToken, "remove");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}