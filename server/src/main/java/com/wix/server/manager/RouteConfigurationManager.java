package com.wix.server.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.wix.common.model.RouteDTO;
import com.wix.common.model.RouteExecutionDTO;
import com.wix.server.persistence.PMF;
import com.wix.server.persistence.Route;
import com.wix.server.persistence.RouteExecution;
import com.wix.server.persistence.RouteExecutor;

/**
 * Created by racastur on 05-03-2015.
 */
@Component("routeConfigurationManager")
public class RouteConfigurationManager {

    private static final Logger log = Logger.getLogger(RouteConfigurationManager.class.getName());

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
                route.setOrganizationId(routeDTO.getOrganizationId());
                route.setDefaultStopPurpose(routeDTO.getDefaultStopPurpose());
                route.setExecutionStartTime(routeDTO.getExecutionStartTime());
                //route.setRouteLocations(routeDTO.getRouteLocations());

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

    public RouteExecutionDTO assignRouteExecution(String routeId, String userId, String deviceId) {

        if (!StringUtils.hasText(routeId) || !StringUtils.hasText(userId) || !StringUtils.hasText(deviceId)) {
            // throw validation exception
            throw new IllegalArgumentException("routeId, userId, deviceId are required");
        }

        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {

            Route route = pm.getObjectById(Route.class, routeId);
            if (route == null) {
                throw new IllegalArgumentException("Unknown route");
            }

            RouteExecution routeExecution = new RouteExecution();
            routeExecution.setRouteId(routeId);

            // TODO validate userId
            RouteExecutor routeExecutor = new RouteExecutor();
            routeExecutor.setUserId(userId);
            routeExecutor.setDeviceId(deviceId);

            routeExecution.setRouteExecutor(routeExecutor);

            pm.makePersistent(routeExecution);

            return routeExecution.getDTO();

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
