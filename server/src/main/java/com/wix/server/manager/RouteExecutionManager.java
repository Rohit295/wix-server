package com.wix.server.manager;

import com.wix.common.model.*;
import com.wix.server.persistence.*;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;

/**
 * Created by racastur on 05-03-2015.
 */
@Component("routeExecutionManager")
public class RouteExecutionManager {

    private static final Logger log = Logger.getLogger(RouteExecutionManager.class.getName());

    public RouteExecutionDTO updateRouteExecutionStatus(String userId, String routeExecutionId, RouteExecutionStatus routeExecutionStatus) {

        if (!StringUtils.hasText(routeExecutionId)) {
            // throw validation exception
            throw new IllegalArgumentException("routeExecutionId is required");
        }

        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {

            RouteExecution routeExecution = pm.getObjectById(RouteExecution.class, routeExecutionId);
            if (routeExecution == null) {
                throw new IllegalArgumentException("Unknown route execution");
            }

            switch (routeExecutionStatus) {
                case Started:
                    routeExecution.setStartTime(Calendar.getInstance().getTimeInMillis());
                    break;
                case Finished:
                    routeExecution.setEndTime(Calendar.getInstance().getTimeInMillis());
                    break;
                default:
            }

            pm.makePersistent(routeExecution);

            return routeExecution.getDTO();

        } catch (Exception e) {
            // TODO
            e.printStackTrace();
            throw new RuntimeException("Unknown error", e);
        } finally {
            try {
                pm.close();
            } catch (Exception e) {
                // ignore
            }
        }

    }

    public void postRouteExecutionLocation(String userId, String routeExecutionId, RouteExecutionLocationDTO routeExecutionLocationDTO) {

        if (!StringUtils.hasText(routeExecutionId) || routeExecutionLocationDTO == null) {
            // throw validation exception
            throw new IllegalArgumentException("routeExecutionId and dto are required");
        }

        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {

            RouteExecution routeExecution = pm.getObjectById(RouteExecution.class, routeExecutionId);
            if (routeExecution == null) {
                throw new IllegalArgumentException("Unknown route execution");
            }

            RouteExecutionLocation routeExecutionLocation = new RouteExecutionLocation(routeExecutionId, routeExecutionLocationDTO);
            if (routeExecution.getRouteExecutionLocations() == null) {
                routeExecution.setRouteExecutionLocations(new ArrayList<RouteExecutionLocation>());
            }
            routeExecution.getRouteExecutionLocations().add(routeExecutionLocation);

        } catch (Exception e) {
            // TODO
            throw new RuntimeException("Unknown error", e);
        } finally {
            try {
                pm.close();
            } catch (Exception e) {
                // ignore
            }
        }

    }

    /**
     * Whenever a new listener needs to get added to / removed from an instance of RouteExecution. Basically some observer wants
     * to see the route as it is being executed OR is finished observing
     *
     * @param userId
     * @param routeExecutionId
     * @param listenerChannel
     */
    public void manageRouteExecutionListener(String userId, String routeExecutionId, String listenerChannel, String action) {

        if (!StringUtils.hasText(routeExecutionId) || listenerChannel == null) {
            // throw validation exception
            throw new IllegalArgumentException("routeExecutionId and Channel that wants to listen are required");
        }

        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {

            RouteExecution routeExecution = pm.getObjectById(RouteExecution.class, routeExecutionId);
            if (routeExecution == null) {
                throw new IllegalArgumentException("Unknown Route Execution ID: " + routeExecutionId);
            }

            if (action.compareTo("add") == 0)
                routeExecution.addChannelToUpdate(listenerChannel);
            else if (action.compareTo("remove") == 0)
                routeExecution.removeChannelFromUpdate(listenerChannel);

            pm.makePersistent(routeExecution);

        } catch (Exception e) {
            // TODO
            throw new RuntimeException(getClass().getName() + " - Unknown Error: " + e.getMessage());
        } finally {
            try {
                pm.close();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    public List<RouteExecutionDTO> getAssignedRouteExecutions(String userId) {

        if (!StringUtils.hasText(userId)) {
            throw new IllegalArgumentException("userId is required");
        }

        PersistenceManager pm = PMF.get().getPersistenceManager();

        Extent<RouteExecution> routeExecutions = pm.getExtent(RouteExecution.class);
        if (routeExecutions == null) {
            return new ArrayList<>();
        }

        List<RouteExecutionDTO> dtos = new ArrayList<>();
        for (RouteExecution routeExecution : routeExecutions) {
            if (routeExecution.getRouteExecutor().getUserId().equals(userId)) {
                dtos.add(routeExecution.getDTO());
            }
        }

        return dtos;

    }

    public RouteExecutionDTO getAssignedRouteExecution(String userId, String routeExecutionId) {

        if (!StringUtils.hasText(routeExecutionId)) {
            throw new IllegalArgumentException("route execution id is required");
        }

        PersistenceManager pm = PMF.get().getPersistenceManager();

        RouteExecution routeExecution = pm.getObjectById(RouteExecution.class, routeExecutionId);
        if (routeExecution == null || !routeExecution.getRouteExecutor().getUserId().equals(userId)) {
            throw new IllegalArgumentException("Unknown route execution");
        }

        return routeExecution.getDTO();

    }

    /**
     * Get the route execution details for a specific route execution
     *
     * @param routeExecutionID
     * @return
     */
    public RouteExecutionDTO getRouteExecutionByID(String routeExecutionID) {
        if (!StringUtils.hasText(routeExecutionID)) {
            throw new IllegalArgumentException("route execution id is required");
        }

        PersistenceManager pm = PMF.get().getPersistenceManager();
        RouteExecution routeExecution = pm.getObjectById(RouteExecution.class, routeExecutionID);
        return routeExecution.getDTO();
    }

    /**
     * get the route executions that a specific consumer could be interested in
     *
     * @param consumerID
     * @return
     */
    public List<String> getRouteExecutionsForConsumer(String consumerID) {
        PersistenceManager pm = PMF.get().getPersistenceManager();

        // TODO Consumer is interested in Route, each Route has RouteExecutions

        return null;
    }

}
