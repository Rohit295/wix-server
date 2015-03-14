package com.wix.server.web.user;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.wix.common.model.LocationDTO;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * Master Controller for all User Pages in WIX
 * @author rohitman
 *
 */
@Controller
public class UserPagesController {
	
	private static String MAP_ROADS_URL = "https://roads.googleapis.com/v1/snapToRoads?parameters&key=AIzaSyBhsW91k1kk72HSr4Qqk-LxhJHfHF7IYdA";
	

    @RequestMapping("/user/console")
    public ModelAndView getUserConsole() {
    	
    	// TODO Create a proper channel for this user. Add that to the track(s) that the user is following
    	
    	// get the current user ID, to create a Channel 
		UserService userService = UserServiceFactory.getUserService();
		String userID = "rohit295@gmail.com";
		//String userID = userService.getCurrentUser().getUserId();
		
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		String channelID = channelService.createChannel(userID);
    			
        return new ModelAndView("user/console", "channelID", channelID);
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