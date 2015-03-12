package com.wix.server.manager;

import com.wix.common.model.RouteDTO;
import com.wix.server.persistence.PMF;
import com.wix.server.persistence.Route;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;

/**
 * Created by racastur on 05-03-2015.
 */
@Component("routeConfigurationManager")
public class RouteConfigurationManager {

	public RouteDTO createUpdateRoute(RouteDTO routeDTO) {

		if (routeDTO == null) {
			// throw validation exception
			throw new IllegalArgumentException("route is required");
		}

		PersistenceManager pm = PMF.get().getPersistenceManager();

		try {

			boolean newRoute = false;

			Route route;
			if (StringUtils.hasText(routeDTO.getId())) {

				route = pm.getObjectById(Route.class, routeDTO.getId());
				if (route == null) {
					throw new IllegalArgumentException("Unknown route");
				}

				// TODO check if the route re-naming clashes with a different route's name

				route.setName(routeDTO.getName());
				route.setDefaultStopPurpose(routeDTO.getDefaultStopPurpose());
				route.setExecutionStartTime(routeDTO.getExecutionStartTime());

			} else {
				newRoute = true;
				route = new Route(routeDTO);
			}

			if (newRoute) {
				pm.makePersistent(route);
			}

			return route.getDTO();

		} catch (Exception e) {
			// TODO
			e.printStackTrace();
			throw new RuntimeException("Unknown error");
		} finally {
			try {
				pm.close();
			} catch (Exception e) {
				// ignore
			}
		}

	}

	public List<RouteDTO> getAllRoutes() {

		PersistenceManager pm = PMF.get().getPersistenceManager();

		Extent<Route> routeExtent = pm.getExtent(Route.class);
		if (routeExtent == null) {
			return new ArrayList<RouteDTO>();
		}

		List<RouteDTO> dtos = new ArrayList<RouteDTO>();
		for (Route route : routeExtent) {
			dtos.add(route.getDTO());
		}

		return dtos;

	}

	public RouteDTO getRoute(String routeId, boolean includeRouteStops) {

		if (!StringUtils.hasText(routeId)) {
			throw new IllegalArgumentException("route id is required");
		}

		PersistenceManager pm = PMF.get().getPersistenceManager();

		Route route = pm.getObjectById(Route.class, routeId);
		if (route == null) {
			throw new IllegalArgumentException("Unknown route");
		}

		return route.getDTO();

	}

}
