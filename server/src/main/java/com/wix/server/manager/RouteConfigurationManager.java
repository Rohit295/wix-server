package com.wix.server.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.wix.server.exception.DuplicateEntityException;
import com.wix.server.exception.UnknownEntityException;
import com.wix.server.exception.ValidationException;
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

    public RouteDTO createUpdateRoute(RouteDTO routeDTO) throws ValidationException, UnknownEntityException, DuplicateEntityException {

        if (routeDTO == null || !StringUtils.hasText(routeDTO.getName())) {
            // throw validation exception
            throw new ValidationException("invalid route data");
        }

        PersistenceManager pm = PMF.get().getPersistenceManager();

        try {

            boolean newRoute = false;

            Route route;
            if (StringUtils.hasText(routeDTO.getId())) {

                route = pm.getObjectById(Route.class, routeDTO.getId());
                if (route == null) {
                    throw new UnknownEntityException("Unknown route");
                }

                Route routeBySameName = getRouteByName(routeDTO.getName());
                if (routeBySameName != null && !routeBySameName.getId().equals(route.getId())) {
                    throw new DuplicateEntityException("another route with given name already exists");
                }

                route.setName(routeDTO.getName());
                route.setOrganizationId(routeDTO.getOrganizationId());
                route.setDefaultStopPurpose(routeDTO.getDefaultStopPurpose().name());
                route.setExecutionStartTime(routeDTO.getExecutionStartTime());
                //route.setRouteLocations(routeDTO.getRouteLocations());

            } else {

                newRoute = true;

                route = getRouteByName(routeDTO.getName());
                if (route != null) {
                    throw new DuplicateEntityException("route with given name already exists");
                }

                route = new Route(routeDTO);

            }

            if (newRoute) {
                pm.makePersistent(route);
            }

            return route.getDTO();

        } finally {
            try {
                pm.close();
            } catch (Exception e) {
                // ignore
            }
        }

    }

    public RouteExecutionDTO assignRouteExecution(String routeId, String userId, String deviceId) throws ValidationException, UnknownEntityException {

        if (!StringUtils.hasText(routeId) || !StringUtils.hasText(userId) || !StringUtils.hasText(deviceId)) {
            // throw validation exception
            throw new ValidationException("routeId, userId, deviceId are required");
        }

        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {

            Route route = pm.getObjectById(Route.class, routeId);
            if (route == null) {
                throw new UnknownEntityException("Unknown route");
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
            return null;
        }

        PersistenceManager pm = PMF.get().getPersistenceManager();

        Route route = pm.getObjectById(Route.class, routeId);
        if (route == null) {
            return null;
        }

        return route.getDTO();

    }

    public Route getRouteByName(String name) {

        PersistenceManager pm = PMF.get().getPersistenceManager();

        Query q = pm.newQuery(Route.class);
        q.setFilter("name == nameParam");
        q.declareParameters("String nameParam");

        try {

            List<Route> results = (List<Route>) q.execute(name);
            if (results == null || results.isEmpty() || results.size() > 1) {
                return null;
            }

            return results.get(0);

        } finally {
            q.closeAll();
        }

    }

}
