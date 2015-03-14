package com.wix.server.web.admin;

import com.wix.common.model.RouteDTO;
import com.wix.server.manager.RouteConfigurationManager;
import com.wix.server.persistence.Track;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wix.server.OfyService.ofy;

/**
 * Created by racastur on 08-11-2014.
 */
@Controller
public class AdminPagesController {

    @Autowired
    private RouteConfigurationManager routeConfigurationManager;

    @RequestMapping("/login")
    public ModelAndView getLogin() {
        return new ModelAndView("login");
    }

    @RequestMapping({"/admin/index", "/admin", "/admin/"})
    public ModelAndView getIndex() {
        Map<String, Object> model = new HashMap<>();
        model.put("activeTab", "index");
        return new ModelAndView("admin/index", model);
    }

    @RequestMapping("/admin/routes")
    public ModelAndView getAllRoutes() {

        List<RouteDTO> routes = routeConfigurationManager.getAllRoutes();

        Map<String, Object> model = new HashMap<>();
        model.put("activeTab", "routes");
        model.put("routes", routes);

        return new ModelAndView("admin/allroutes", model);

    }

    @RequestMapping("/admin/analytics")
    public ModelAndView getAnalytics() {
        Map<String, Object> model = new HashMap<>();
        model.put("activeTab", "analytics");
        return new ModelAndView("admin/analytics", model);
    }

    @RequestMapping("/admin/tracks")
    public ModelAndView getAllTracks() {

        List<Track> records = ofy().load().type(Track.class).list();
        if (records == null || records.isEmpty()) {
            records = new ArrayList<>();
        }

        Collections.sort(records, new Comparator<Track>() {
            @Override
            public int compare(Track o1, Track o2) {
                if (o1.getUserId() < o2.getUserId()) {
                    return -1;
                } else if (o1.getUserId() > o2.getUserId()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        return new ModelAndView("admin/alltracks", "tracks", records);

    }

    @RequestMapping("/admin/console")
    public ModelAndView getAdminConsole() {
        return new ModelAndView("admin/console");
    }

}