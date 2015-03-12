package com.wix.server.endpoint;

import com.wix.common.model.RouteExecutionDTO;
import com.wix.common.model.RouteExecutionLocationDTO;
import com.wix.server.manager.RouteExecutionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

	@RequestMapping(value = "routes/{routeId}/executions", method = RequestMethod.POST)
	public RouteExecutionDTO startRouteExecution(@PathVariable("routeId") String routeId,
			@RequestHeader("userId") String userId) {
		return routeExecutionManager.startRouteExecution(userId, routeId);
	}

	@RequestMapping(value = "routeexecutions/{routeExecutionId}/location", method = RequestMethod.POST)
	public void postRouteExecutionLocation(@PathVariable("routeExecutionId") String routeExecutionId,
			@RequestHeader("userId") String userId, @RequestBody RouteExecutionLocationDTO routeExecutionLocationDTO) {
		routeExecutionManager.postRouteExecutionLocation(userId, routeExecutionId, routeExecutionLocationDTO);
	}

	@RequestMapping(value = "routeexecutions/{routeExecutionId}", method = RequestMethod.GET)
	public RouteExecutionDTO getRouteExecution(@PathVariable("routeExecutionId") Long routeExecutionId,
			@RequestHeader("userId") String userId) {
		return routeExecutionManager.getRouteExecution(routeExecutionId);
	}

}
