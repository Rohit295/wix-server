package com.wix.server.web.admin;

import com.wix.common.model.RouteDTO;
import com.wix.common.model.RouteRunDTO;

import java.util.List;

/**
 * Created by racastur on 03-04-2015.
 */
public class RouteInfo {

    private RouteDTO route;

    private int numberOfStops;

    private List<RouteRunDTO> routeRuns;

    private boolean runningStatus;

    private long lastRunCompletedTimestamp;

    public RouteInfo(RouteDTO route, int numberOfStops, List<RouteRunDTO> routeRuns, boolean runningStatus, long lastRunCompletedTimestamp) {
        this.route = route;
        this.numberOfStops = numberOfStops;
        this.routeRuns = routeRuns;
        this.runningStatus = runningStatus;
        this.lastRunCompletedTimestamp = lastRunCompletedTimestamp;
    }

    public RouteDTO getRoute() {
        return route;
    }

    public void setRoute(RouteDTO route) {
        this.route = route;
    }

    public int getNumberOfStops() {
        return numberOfStops;
    }

    public void setNumberOfStops(int numberOfStops) {
        this.numberOfStops = numberOfStops;
    }

    public List<RouteRunDTO> getRouteRuns() {
        return routeRuns;
    }

    public void setRouteRuns(List<RouteRunDTO> routeRuns) {
        this.routeRuns = routeRuns;
    }

    public boolean isRunningStatus() {
        return runningStatus;
    }

    public void setRunningStatus(boolean runningStatus) {
        this.runningStatus = runningStatus;
    }

    public long getLastRunCompletedTimestamp() {
        return lastRunCompletedTimestamp;
    }

    public void setLastRunCompletedTimestamp(long lastRunCompletedTimestamp) {
        this.lastRunCompletedTimestamp = lastRunCompletedTimestamp;
    }

}
