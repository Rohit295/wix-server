package com.wix.server.manager;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.wix.common.model.*;
import com.wix.server.exception.UnknownEntityException;
import com.wix.server.exception.ValidationException;
import com.wix.server.persistence.*;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.logging.Logger;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Created by racastur on 05-03-2015.
 */
@Component("routeExecutionManager")
public class RouteExecutionManager {

    private static final Logger log = Logger.getLogger(RouteExecutionManager.class.getName());

    public RouteExecutionDTO startRouteRunExecution(String routeRunId, RouteExecutorDTO routeExecutor) throws ValidationException, UnknownEntityException {

        if (!StringUtils.hasText(routeRunId) || routeExecutor == null) {
            throw new ValidationException("routeRunId and routeExecutor are required");
        }

        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {

            RouteRun routeRun = pm.getObjectById(RouteRun.class, routeRunId);
            if (routeRun == null || !routeRun.getRouteExecutor().getUserId().equals(routeExecutor.getUserId())) {
                throw new UnknownEntityException("Unknown route run");
            }

            if (!routeRun.getRouteExecutor().getUserId().equals(routeExecutor.getUserId())) {
                throw new ValidationException("Invalid route executor");
            }

            RouteExecution routeExecution = new RouteExecution();
            routeExecution.setRouteRunId(routeRunId);
            routeExecution.setStartTime(Calendar.getInstance().getTimeInMillis());
            routeExecution.setRouteExecutor(new RouteExecutor(routeExecutor));

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

    public RouteExecutionDTO updateRouteExecutionStatus(String routeExecutionId, RouteExecutionStatus routeExecutionStatus) throws ValidationException {

        if (!StringUtils.hasText(routeExecutionId)) {
            // throw validation exception
            throw new ValidationException("routeExecutionId is required");
        }

        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {

            RouteExecution routeExecution = pm.getObjectById(RouteExecution.class, routeExecutionId);
            if (routeExecution == null) {
                throw new ValidationException("Unknown route execution");
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

        } finally {
            try {
                pm.close();
            } catch (Exception e) {
                // ignore
            }
        }

    }

    /**
     * This method updates every location update that is sent back for every route execution. Since at any time a consumer could be listening
     * for any location updates, check for listeners and update them
     *
     * @param userId
     * @param routeExecutionId
     * @param routeExecutionLocationDTO
     */
    public void postRouteExecutionLocation(String userId, String routeExecutionId, RouteExecutionLocationDTO routeExecutionLocationDTO) throws ValidationException {

        if (!StringUtils.hasText(routeExecutionId) || routeExecutionLocationDTO == null) {
            // throw validation exception
            throw new ValidationException("routeExecutionId and dto are required");
        }

        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {

            RouteExecution routeExecution = pm.getObjectById(RouteExecution.class, routeExecutionId);
            if (routeExecution == null) {
                throw new ValidationException("Unknown route execution");
            }

            RouteExecutionLocation routeExecutionLocation = new RouteExecutionLocation(routeExecutionId, routeExecutionLocationDTO);
            if (routeExecution.getRouteExecutionLocations() == null) {
                routeExecution.setRouteExecutionLocations(new ArrayList<RouteExecutionLocation>());
            }

            log.info("About to add location: " +
                    routeExecutionLocationDTO.getLocation().getLatitude() + "|" + routeExecutionLocationDTO.getLocation().getLatitude() +
                    " to route Execution: " + routeExecutionId);

            log.info("postRouteExecutionLocation - Current Locations count: " + routeExecution.getRouteExecutionLocations().size());

            routeExecution.getRouteExecutionLocations().add(routeExecutionLocation);

            pm.makePersistent(routeExecution);

            // At this point the location has been updated here, see if there are any listeners for this RouteExecution and update them
            List<String> listOfConsumers = routeExecution.getChannelsToUpdate();
            if (listOfConsumers != null) {
                for (String consumerToken : listOfConsumers) {
                    pushRouteExecutionLocations(routeExecutionId, consumerToken);
                }
            }

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
     */
    public void manageRouteExecutionListener(String userId, String routeExecutionId, String consumerToken, String action) throws ValidationException {

        log.info("About to " + action + " a listener for route: " + routeExecutionId + ", for User: " + userId);

        if (!StringUtils.hasText(routeExecutionId) || consumerToken == null) {
            throw new ValidationException("routeExecutionId and ConsumerToken that wants to listen are required");
        }

        if ((action.compareTo("add") != 0) && (action.compareTo("remove") != 0)) {
            throw new ValidationException("Action to perform with consumerToken should be add/remove");
        }

        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {

            RouteExecution routeExecution = pm.getObjectById(RouteExecution.class, routeExecutionId);
            if (routeExecution == null) {
                throw new IllegalArgumentException("Unknown Route Execution ID: " + routeExecutionId);
            }

            if (routeExecution.getChannelsToUpdate() == null) {
                routeExecution.setChannelsToUpdate(new ArrayList<String>(5));
            }

            if (action.compareTo("add") == 0) {

                routeExecution.addChannelToUpdate(consumerToken);

                log.info("Added " + consumerToken + " as a listener for route: " + routeExecutionId);

                pushRouteExecutionLocations(routeExecutionId, consumerToken);

            } else if (action.compareTo("remove") == 0) {

                routeExecution.removeChannelFromUpdate(consumerToken);

                log.info("Removed " + consumerToken + " as listener from route: " + routeExecutionId);

            }

            pm.makePersistent(routeExecution);

        } finally {
            try {
                pm.close();
            } catch (Exception e) {
                // ignore
            }
        }

    }

    /**
     * Use this to push List of Locations for a specific route execution, to a specific consumer
     *
     * @param routeExecutionId
     * @param consumerToken
     */
    private void pushRouteExecutionLocations(String routeExecutionId, String consumerToken) throws ValidationException {
        //log.info("Preparing to push locations for route execution: " + routeExecutionId + " to consumer: " + consumerToken);
        ChannelService channelService = ChannelServiceFactory.getChannelService();
        channelService.sendMessage(new ChannelMessage(consumerToken, getLocationsOnARouteExecution(routeExecutionId)));
        log.info("Pushed out current locations for route execution: " + routeExecutionId + " to consumer: " + consumerToken);
    }

    /**
     * for a specific route Execution ID, return the Lat, Long as a formatted string. This is normally for use in the
     * client that is listening to a route and wants to paint in real time
     *
     * @param routeExecutionId
     * @return
     */
    private String getLocationsOnARouteExecution(String routeExecutionId) throws ValidationException {
        log.info("About to generate locations list");
        PersistenceManager pm = PMF.get().getPersistenceManager();
        String msgLatLong = "";
        try {

            RouteExecution routeExecution = pm.getObjectById(RouteExecution.class, routeExecutionId);
            if (routeExecution == null) {
                throw new ValidationException("Unknown Route Execution ID: " + routeExecutionId);
            }

            // TODO this should ideally be the interpolated, matched to Road list of locations
            // Get all the Locations on the RouteExecution and return them. Maybe there are no locations stored just now
            // in which case there is nothing really to return
            List<RouteExecutionLocation> listLocations = routeExecution.getRouteExecutionLocations();
            if (listLocations == null)
                return msgLatLong;
            for (RouteExecutionLocation location : listLocations) {
                msgLatLong += location.getLocation().getLatitude() + ":" + location.getLocation().getLongitude() + "|";
            }

        } finally {
            log.info("Locations List is: " + msgLatLong);
            try {
                pm.close();
            } catch (Exception e) {
                // ignore
            }

        }

        return msgLatLong;

    }

    public List<RouteExecutionDTO> getRouteExecutions(String routeRunId) {

        if (!StringUtils.hasText(routeRunId)) {
            return new ArrayList<>();
        }

        PersistenceManager pm = PMF.get().getPersistenceManager();

        Query q = pm.newQuery(RouteExecution.class);
        q.setFilter("routeRunId == routeRunIdParam");
        q.declareParameters("String routeRunIdParam");

        try {

            List<RouteExecution> results = (List<RouteExecution>) q.execute(routeRunId);
            if (results == null || results.isEmpty()) {
                return new ArrayList<>();
            }

            Collections.sort(results, new Comparator<RouteExecution>() {
                @Override
                public int compare(RouteExecution o1, RouteExecution o2) {
                    if (o1.getStartTime() > o2.getStartTime()) {
                        return -1;
                    } else if (o1.getStartTime() < o2.getStartTime()) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            });

            List<RouteExecutionDTO> dtos = new ArrayList<>();
            for (RouteExecution routeExecution : results) {
                dtos.add(routeExecution.getDTO());
            }

            return dtos;

        } finally {
            q.closeAll();
        }

    }

    /**
     * Get the route execution details for a specific route execution
     *
     * @param routeExecutionId
     * @return
     */
    public RouteExecutionDTO getRouteExecutionById(String routeExecutionId) {

        if (!StringUtils.hasText(routeExecutionId)) {
            return null;
        }

        PersistenceManager pm = PMF.get().getPersistenceManager();

        RouteExecution routeExecution = pm.getObjectById(RouteExecution.class, routeExecutionId);
        if (routeExecution == null) {
            return null;
        }

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

        // TODO For a consumer, get the Routes of Interest, get the RouteExecution from there
        Extent<RouteExecution> routeExecutions = pm.getExtent(RouteExecution.class);
        if (routeExecutions == null) {
            return new ArrayList<>();
        }

        List<String> listRouteExecutions = new ArrayList<>();
        for (RouteExecution routeExecution : routeExecutions) {
            listRouteExecutions.add(routeExecution.getId());
        }

        return listRouteExecutions;
    }

}
