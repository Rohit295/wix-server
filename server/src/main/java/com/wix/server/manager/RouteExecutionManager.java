package com.wix.server.manager;

import com.wix.common.model.*;
import com.wix.server.persistence.*;

import org.springframework.stereotype.Component;

import java.util.Calendar;

import javax.jdo.PersistenceManager;

/**
 * Created by racastur on 05-03-2015.
 */
@Component("routeExecutionManager")
public class RouteExecutionManager {

	public RouteExecutionDTO startRouteExecution(String userId, String routeId) {

		if (routeId == null) {
			// throw validation exception
			throw new IllegalArgumentException("routeId is required");
		}

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {

			Route route = pm.getObjectById(Route.class, routeId);
			if (route == null) {
				throw new IllegalArgumentException("Unknown route");
			}

			// TODO validate userId
			RouteExecutor routeExecutor = new RouteExecutor();
			routeExecutor.setUserId(userId);

			RouteExecution routeExecution = new RouteExecution();
			routeExecution.setRouteId(routeId);
			routeExecution.setRouteExecutorId(routeExecutor.getId());
			routeExecution.setStartTime(Calendar.getInstance().getTimeInMillis());

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

	public void postRouteExecutionLocation(String userId, String routeExecutionId,
			RouteExecutionLocationDTO routeExecutionLocationDTO) {

		if (routeExecutionId == null || routeExecutionLocationDTO == null) {
			// throw validation exception
			throw new IllegalArgumentException("routeExecutionId and dto are required");
		}

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {

			RouteExecution routeExecution = pm.getObjectById(RouteExecution.class, routeExecutionId);
			if (routeExecution == null) {
				throw new IllegalArgumentException("Unknown route execution");
			}

			RouteExecutionLocation routeExecutionLocation = new RouteExecutionLocation(routeExecutionId,
					routeExecutionLocationDTO);

			pm.makePersistent(routeExecutionLocation);

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

	public RouteExecutionDTO getRouteExecution(Long routeExecutionId) {

		if (routeExecutionId == null) {
			throw new IllegalArgumentException("route execution id is required");
		}

		PersistenceManager pm = PMF.get().getPersistenceManager();

		RouteExecution routeExecution = pm.getObjectById(RouteExecution.class, routeExecutionId);
		if (routeExecution == null) {
			throw new IllegalArgumentException("Unknown route execution");
		}

		return routeExecution.getDTO();

	}

}
