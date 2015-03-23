package com.wix.server.endpoint;

import com.wix.common.model.*;
import com.wix.server.manager.ObservableConfigurationManager;
import com.wix.server.manager.RouteConfigurationManager;
import com.wix.server.manager.RouteExecutionManager;
import com.wix.server.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by racastur on 16-03-2015.
 */
@Component
public class TestDataInitializer {

    private static final Logger log = Logger.getLogger(TestDataInitializer.class.getName());

    @Autowired
    private UserManager userManager;

    @Autowired
    private RouteConfigurationManager routeConfigurationManager;

    @Autowired
    private RouteExecutionManager routeExecutionManager;

    @Autowired
    private ObservableConfigurationManager observableConfigurationManager;

    private String ramaUserId;
    private String rohitUserId;
    private RouteDTO route1;
    private List<RouteLocationDTO> route1Stops = new ArrayList<>();

    public void init() {

        UserDTO chirecAdmin = new UserDTO();
        chirecAdmin.setOrganizationId("1");
        chirecAdmin.setName("Chirec Admin");
        chirecAdmin.setUserName("chirec-admin");
        chirecAdmin.setPassword("123");
        chirecAdmin.setEmailId("admin@chirec.co.in");
        chirecAdmin.setAdminRole(true);

        try {
            userManager.createUpdateUser(chirecAdmin);
        } catch (Exception e) {
            log.log(Level.WARNING, "Error creating user [" + e.getMessage() + "]", e);
        }

        UserDTO ramaUser = new UserDTO();
        ramaUser.setOrganizationId("1");
        ramaUser.setName("Bing Flick");
        ramaUser.setUserName("bingflick");
        ramaUser.setPassword("xxx");
        ramaUser.setEmailId("bingflick@gmail.com");

        try {
            ramaUser = userManager.createUpdateUser(ramaUser);
            ramaUserId = ramaUser.getId();
        } catch (Exception e) {
            log.log(Level.WARNING, "Error creating user [" + e.getMessage() + "]", e);
        }

        UserDTO rohitUser = new UserDTO();
        rohitUser.setOrganizationId("1");
        rohitUser.setName("Rohit Mani");
        rohitUser.setUserName("rohit");
        rohitUser.setPassword("yyy");
        rohitUser.setEmailId("rohit295@gmail.com");

        try {
            rohitUser = userManager.createUpdateUser(rohitUser);
            rohitUserId = rohitUser.getId();
        } catch (Exception e) {
            log.log(Level.WARNING, "Error creating user [" + e.getMessage() + "]", e);
        }

        createRoute1AndAssignToRama();

        createObservables();

    }

    private void createRoute1AndAssignToRama() {

        route1 = new RouteDTO();
        route1.setName("LnT Morning Pickup Route");
        route1.setDefaultStopPurpose(StopPurpose.Pickup);
        route1.setExecutionStartTime("daily");

        List<RouteLocationDTO> routeLocations = new ArrayList<>();

        LocationDTO location = new LocationDTO();
        location.setLatitude(17.432483);
        location.setLongitude(78.362863);
        location.setAddress("Urdu University Rd, Telecom Nagar, Hyderabad, Telangana 500032");

        RouteStopDTO routeStop = new RouteStopDTO();
        routeStop.setName("L&T Serene County");
        routeStop.setAddress("Urdu University Rd, Telecom Nagar, Hyderabad, Telangana 500032");

        RouteLocationDTO routeLocation = new RouteLocationDTO();
        routeLocation.setLocation(location);
        routeLocation.setRouteStop(routeStop);

        routeLocations.add(routeLocation);

        location = new LocationDTO();
        location.setLatitude(17.436163);
        location.setLongitude(78.366532);
        location.setAddress("Urdu University Road, Gachibowli, Hyderabad, Telangana 500032");

        routeLocation = new RouteLocationDTO();
        routeLocation.setLocation(location);

        routeLocations.add(routeLocation);

        location = new LocationDTO();
        location.setLatitude(17.442468);
        location.setLongitude(78.356769);
        location.setAddress("Vinayak Nagar, Gachibowli, Hyderabad, Telangana 500032");

        routeStop = new RouteStopDTO();
        routeStop.setName("Vinayak Nagar");
        routeStop.setAddress("Vinayak Nagar, Gachibowli, Hyderabad, Telangana 500032");

        routeLocation = new RouteLocationDTO();
        routeLocation.setLocation(location);
        routeLocation.setRouteStop(routeStop);

        routeLocations.add(routeLocation);

        location = new LocationDTO();
        location.setLatitude(17.444852);
        location.setLongitude(78.352765);
        location.setAddress("ISB Rd, Gachibowli, Hyderabad, Telangana 500032");

        routeLocation = new RouteLocationDTO();
        routeLocation.setLocation(location);

        routeLocations.add(routeLocation);

        location = new LocationDTO();
        location.setLatitude(17.429235);
        location.setLongitude(78.342799);
        location.setAddress("Boulder Hills Gate 2 Rd, Madhava Reddy Colony, Gachibowli Hyderabad, Telangana 500032");

        routeLocation = new RouteLocationDTO();
        routeLocation.setLocation(location);

        routeLocations.add(routeLocation);

        location = new LocationDTO();
        location.setLatitude(17.429560);
        location.setLongitude(78.342766);
        location.setAddress("Microsoft IDC, ISB Road, Gachibowli Hyderabad, Telangana 500032");

        routeStop = new RouteStopDTO();
        routeStop.setName("Microsoft IDC Gate #2");
        routeStop.setAddress("Microsoft IDC, ISB Road, Gachibowli Hyderabad, Telangana 500032");

        routeLocation = new RouteLocationDTO();
        routeLocation.setLocation(location);
        routeLocation.setRouteStop(routeStop);

        routeLocations.add(routeLocation);

        route1.setRouteLocations(routeLocations);

        try {

            route1 = routeConfigurationManager.createUpdateRoute(route1);

            for (RouteLocationDTO rl : route1.getRouteLocations()) {
                if (rl.getRouteStop() != null) {
                    route1Stops.add(rl);
                }
            }

            try {

                RouteExecutionDTO routeExecutionDTO = routeConfigurationManager.assignRouteExecution(route1.getId(), ramaUserId, "device1");
                RouteExecutionDTO routeExecutionDTO1 = routeConfigurationManager.assignRouteExecution(route1.getId(), rohitUserId, "deviceA");

                try {

                    routeExecutionManager.updateRouteExecutionStatus(ramaUserId, routeExecutionDTO.getId(), RouteExecutionStatus.Started);

                    LocationDTO loc = new LocationDTO();
                    loc.setLatitude(17.432483);
                    loc.setLongitude(78.362863);
                    loc.setAddress("Urdu University Rd, Telecom Nagar, Hyderabad, Telangana 500032");

                    RouteExecutionStopDTO res = new RouteExecutionStopDTO();
                    res.setRouteStopId(route1.getRouteLocations().get(0).getId());

                    RouteExecutionLocationDTO rel = new RouteExecutionLocationDTO();
                    rel.setLocation(loc);
                    rel.setRouteExecutionStop(res);

                    routeExecutionManager.postRouteExecutionLocation(ramaUserId, routeExecutionDTO.getId(), rel);

                    loc = new LocationDTO();
                    loc.setLatitude(17.432299);
                    loc.setLongitude(78.363963);
                    loc.setAddress("1 Urdu University Rd, Sri Shyam Nagar, Telecom Nagar, Gachibowli, Hyderabad, Telangana 500032");

                    rel = new RouteExecutionLocationDTO();
                    rel.setLocation(loc);

                    routeExecutionManager.postRouteExecutionLocation(ramaUserId, routeExecutionDTO.getId(), rel);

                    loc = new LocationDTO();
                    loc.setLatitude(17.433378);
                    loc.setLongitude(78.364655);
                    loc.setAddress("2 Urdu University Rd, Sri Shyam Nagar, Telecom Nagar, Gachibowli, Hyderabad, Telangana 500032");

                    rel = new RouteExecutionLocationDTO();
                    rel.setLocation(loc);

                    routeExecutionManager.postRouteExecutionLocation(ramaUserId, routeExecutionDTO.getId(), rel);

                    loc = new LocationDTO();
                    loc.setLatitude(17.435010);
                    loc.setLongitude(78.365771);
                    loc.setAddress("3 Urdu University Rd, Sri Shyam Nagar, Telecom Nagar, Gachibowli, Hyderabad, Telangana 500032");

                    rel = new RouteExecutionLocationDTO();
                    rel.setLocation(loc);

                    routeExecutionManager.postRouteExecutionLocation(ramaUserId, routeExecutionDTO.getId(), rel);

                    loc = new LocationDTO();
                    loc.setLatitude(17.437316);
                    loc.setLongitude(78.365190);
                    loc.setAddress("Next to flyover, Gachibowli, Hyderabad, Telangana 500032");

                    rel = new RouteExecutionLocationDTO();
                    rel.setLocation(loc);

                    routeExecutionManager.postRouteExecutionLocation(ramaUserId, routeExecutionDTO.getId(), rel);

                    routeExecutionManager.updateRouteExecutionStatus(rohitUserId, routeExecutionDTO1.getId(), RouteExecutionStatus.Started);

                } catch (Exception e) {
                    log.log(Level.WARNING, "Error updating route execution status [" + e.getMessage() + "]", e);
                }

            } catch (Exception e) {
                log.log(Level.WARNING, "Error assigning route [" + e.getMessage() + "]", e);
            }

        } catch (Exception e) {
            log.log(Level.WARNING, "Error creating/assiging route [" + e.getMessage() + "]", e);
        }

    }

    private void createObservables() {

        List<ObservableDTO> obs = new ArrayList<>();

        ObservableDTO obs1 = new ObservableDTO();
        obs1.setOrganizationId("1");
        obs1.setName("Aditya Casturi");
        obs1.setRouteId(route1.getId());
        obs1.setRouteStopId(route1Stops.get(0).getId());
        obs1.setStopPurpose(StopPurpose.Pickup);
        obs.add(obs1);

        ObservableDTO obs2 = new ObservableDTO();
        obs2.setOrganizationId("1");
        obs2.setName("Kavya Casturi");
        obs2.setRouteId(route1.getId());
        obs2.setRouteStopId(route1Stops.get(0).getId());
        obs2.setStopPurpose(StopPurpose.Pickup);
        obs.add(obs2);

        ObservableDTO obs3 = new ObservableDTO();
        obs3.setOrganizationId("1");
        obs3.setName("Foo Bar");
        obs3.setRouteId(route1.getId());
        obs3.setRouteStopId(route1Stops.get(1).getId());
        obs3.setStopPurpose(StopPurpose.Pickup);
        obs.add(obs3);

        ObservableDTO obs4 = new ObservableDTO();
        obs4.setOrganizationId("1");
        obs4.setName("Foo Foo");
        obs4.setRouteId(route1.getId());
        obs4.setRouteStopId(route1Stops.get(2).getId());
        obs4.setStopPurpose(StopPurpose.Pickup);
        obs.add(obs4);

        ObservableDTO obs5 = new ObservableDTO();
        obs5.setOrganizationId("1");
        obs5.setName("Bar Bar");
        obs5.setRouteId(route1.getId());
        obs5.setRouteStopId(route1Stops.get(2).getId());
        obs5.setStopPurpose(StopPurpose.Pickup);
        obs.add(obs5);

        try {
            for (ObservableDTO dto : obs) {
                observableConfigurationManager.createUpdateObservable(dto);
            }
        } catch (Exception e) {
            log.log(Level.WARNING, "Error creating/assiging observables [" + e.getMessage() + "]", e);
        }
    }

}
