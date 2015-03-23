package com.wix.server.web.user;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import com.wix.server.persistence.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.api.channel.ChannelServiceFactory;
import com.wix.common.model.LocationDTO;
import com.wix.server.manager.RouteExecutionManager;
import com.wix.server.persistence.RouteExecutionLocation;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * Master Controller for all User Pages in WIX
 *
 * @author rohitman
 */
@Controller
public class UserPagesController {

    private static final Logger log = Logger.getLogger(RouteExecutionManager.class.getName());

    private static String MAP_ROADS_URL = "https://roads.googleapis.com/v1/snapToRoads?parameters&key=AIzaSyBhsW91k1kk72HSr4Qqk-LxhJHfHF7IYdA";

    @Autowired
    private RouteExecutionManager routeExecutionManager;

    @RequestMapping("/user/console")
    public ModelAndView getUserConsole() {

        // get the Consumer's user ID
        String userID = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getEmailId();

        String channelID = getChannelForConsumer(userID);
        List<String> listOfRouteExecutions = getRoutesExecutionsOfInterest(userID);

        ModelAndView mav = new ModelAndView("user/console");
        mav.addObject("channelID", channelID);
        mav.addObject("RouteExecutions", listOfRouteExecutions);

        return mav;

    }

    private String getChannelForConsumer(String consumerID) {
        return ChannelServiceFactory.getChannelService().createChannel(consumerID);
    }

    /**
     * Given a specific user, identify the Routes this user could be interested in
     *
     * @param consumerID
     * @return
     */
    private List<String> getRoutesExecutionsOfInterest(String consumerID) {
        return routeExecutionManager.getRouteExecutionsForConsumer(consumerID);
    }

    /**
     * RouteExecution points collected for a route may or may not align to the road. To ensure we can draw it well,
     * convert the points to the closest road possible
     */
    private List<LocationDTO> mapToRoads(List<LocationDTO> pointsToAlign) {
        String url = String.format(MAP_ROADS_URL, pointsToAlign);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);
        try {
            HttpResponse response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() != 200) {
                //logErrorAndThrowException("POST", url, response);
            }
        } catch (ClientProtocolException cpe) {

        } catch (IOException ioe) {

        }

        return null;
    }

}