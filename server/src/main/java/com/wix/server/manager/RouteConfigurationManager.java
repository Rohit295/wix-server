package com.wix.server.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.wix.common.model.RouteRunDTO;
import com.wix.server.exception.DuplicateEntityException;
import com.wix.server.exception.UnknownEntityException;
import com.wix.server.exception.ValidationException;
import com.wix.server.persistence.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.wix.common.model.RouteDTO;

/**
 * Created by racastur on 05-03-2015.
 */
@Component("routeConfigurationManager")
public class RouteConfigurationManager {

    private static final Logger log = Logger.getLogger(RouteConfigurationManager.class.getName());

    public RouteDTO createRoute(RouteDTO routeDTO) throws ValidationException, UnknownEntityException, DuplicateEntityException {

        if (routeDTO == null || !StringUtils.hasText(routeDTO.getName())) {
            // throw validation exception
            throw new ValidationException("invalid route data");
        }

        PersistenceManager pm = PMF.get().getPersistenceManager();

        try {

            Route route = getRouteByName(routeDTO.getName());
            if (route != null) {
                throw new DuplicateEntityException("route with given name already exists");
            }

            route = new Route(routeDTO);

            pm.makePersistent(route);

            return route.getDTO();

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
            return new ArrayList<>();
        }

        List<RouteDTO> dtos = new ArrayList<>();
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

    public RouteRunDTO configureRouteRun(String routeId, RouteRunDTO routeRunDTO) throws ValidationException, UnknownEntityException {

        if (!StringUtils.hasText(routeId) || routeRunDTO == null) {
            // throw validation exception
            throw new ValidationException("routeId, routeRun are required");
        }

        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {

            Route route = pm.getObjectById(Route.class, routeId);
            if (route == null) {
                throw new UnknownEntityException("Unknown route");
            }

            RouteRun routeRun = new RouteRun();
            routeRun.setRouteId(routeRunDTO.getRouteId());
            routeRun.setDefaultStopPurpose(routeRunDTO.getDefaultStopPurpose().name());
            routeRun.setExecutionStartTime(routeRunDTO.getExecutionStartTime());
            routeRun.setRouteExecutor(new RouteExecutor(routeRunDTO.getRouteExecutor()));

            pm.makePersistent(routeRun);

            return routeRun.getDTO();

        } finally {
            try {
                pm.close();
            } catch (Exception e) {
                // ignore
            }
        }

    }

    public List<RouteRunDTO> getAssignedRouteRuns(String userId) {

        // TODO this method name should match similar method to get Routes for a specific consumer

        if (!StringUtils.hasText(userId)) {
            return null;
        }

        PersistenceManager pm = PMF.get().getPersistenceManager();

        Extent<RouteRun> routeRuns = pm.getExtent(RouteRun.class);
        if (routeRuns == null) {
            return new ArrayList<>();
        }

        List<RouteRunDTO> dtos = new ArrayList<>();
        for (RouteRun routeRun : routeRuns) {
            if (routeRun.getRouteExecutor().getUserId().equals(userId)) {
                dtos.add(routeRun.getDTO());
            }
        }

        return dtos;

    }

    public List<RouteRunDTO> getAllRouteRuns(String routeId) {

        if (!StringUtils.hasText(routeId)) {
            return new ArrayList<>();
        }

        PersistenceManager pm = PMF.get().getPersistenceManager();

        Query q = pm.newQuery(RouteRun.class);
        q.setFilter("routeId == routeIdParam");
        q.declareParameters("String routeIdParam");

        List<RouteRun> results = (List<RouteRun>) q.execute(routeId);
        if (results == null || results.isEmpty()) {
            return new ArrayList<>();
        }

        List<RouteRunDTO> dtos = new ArrayList<>();
        for (RouteRun routeRun : results) {
            dtos.add(routeRun.getDTO());
        }

        return dtos;

    }

    public RouteRunDTO getRouteRun(String routeId, String routeRunId) {

        if (!StringUtils.hasText(routeId) || !StringUtils.hasText(routeRunId)) {
            return null;
        }

        PersistenceManager pm = PMF.get().getPersistenceManager();

        RouteRun routeRun = pm.getObjectById(RouteRun.class, routeId);
        if (routeRun == null) {
            return null;
        }

        return routeRun.getDTO();

    }

    private Route getRouteByName(String name) {

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
