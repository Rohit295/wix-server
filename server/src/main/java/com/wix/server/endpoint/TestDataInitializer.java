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
    private RouteDTO route2;
    private List<RouteLocationDTO> route2Stops = new ArrayList<>();

    public void init() {

        UserDTO chirecAdmin = new UserDTO("1", "Chirec Admin", "chirec-admin", "123", "admin@chirec.co.in", true);
        try {
            userManager.createUpdateUser(chirecAdmin);
        } catch (Exception e) {
            log.log(Level.WARNING, "Error creating user [" + e.getMessage() + "]", e);
        }

        UserDTO ramaUser = new UserDTO("1", "Bing Flick", "bingflick", "xxx", "bingflick@gmail.com", false);
        try {
            ramaUser = userManager.createUpdateUser(ramaUser);
            ramaUserId = ramaUser.getId();
        } catch (Exception e) {
            log.log(Level.WARNING, "Error creating user [" + e.getMessage() + "]", e);
        }

        UserDTO rohitUser = new UserDTO("1", "Rohit Mani", "rohit", "yyy", "rohit295@gmail.com", false);
        try {
            rohitUser = userManager.createUpdateUser(rohitUser);
            rohitUserId = rohitUser.getId();
        } catch (Exception e) {
            log.log(Level.WARNING, "Error creating user [" + e.getMessage() + "]", e);
        }

        createRoute1AndAssignToRama();
        createRoute2();

        createObservables();

    }

    private void createRoute1AndAssignToRama() {

        route1 = new RouteDTO();
        route1.setName("Route 1 - LnT Serene County");

        List<RouteLocationDTO> routeLocations = new ArrayList<>();

        routeLocations.add(new RouteLocationDTO(new LocationDTO(17.419847, 78.364436), new RouteStopDTO("Oakridge School (Start)", "Khajaguda, Hyderabad")));
        routeLocations.add(new RouteLocationDTO(new LocationDTO(17.417933, 78.369446), null));
        routeLocations.add(new RouteLocationDTO(new LocationDTO(17.418209, 78.370733), null));
        routeLocations.add(new RouteLocationDTO(new LocationDTO(17.410921, 78.374875), new RouteStopDTO("Lanco Hills", "Manikonda, Hyderabad")));
        routeLocations.add(new RouteLocationDTO(new LocationDTO(17.422611, 78.381891), null));
        routeLocations.add(new RouteLocationDTO(new LocationDTO(17.436154, 78.366554), null));
        routeLocations.add(new RouteLocationDTO(new LocationDTO(17.432469, 78.362885), new RouteStopDTO("LnT Stop #1", "Gachibowli, Hyderabad")));
        routeLocations.add(new RouteLocationDTO(new LocationDTO(17.433313, 78.361614), new RouteStopDTO("LnT Stop #2", "Gachibowli, Hyderabad")));
        routeLocations.add(new RouteLocationDTO(new LocationDTO(17.437198, 78.365406), null));
        routeLocations.add(new RouteLocationDTO(new LocationDTO(17.419847, 78.364436), new RouteStopDTO("Oakridge School (End)", "Khajaguda, Hyderabad")));

        route1.setRouteLocations(routeLocations);

        try {

            route1 = routeConfigurationManager.createRoute(route1);

            for (RouteLocationDTO rl : route1.getRouteLocations()) {
                if (rl.getRouteStop() != null) {
                    route1Stops.add(rl);
                }
            }

            try {

                RouteRunDTO route1Run1 = new RouteRunDTO();
                route1Run1.setRouteId(route1.getId());
                route1Run1.setRouteExecutor(new RouteExecutorDTO(ramaUserId, ""));
                route1Run1.setDefaultStopPurpose(StopPurpose.Pickup);
                route1Run1.setExecutionStartTime("Daily 7 AM");

                route1Run1 = routeConfigurationManager.configureRouteRun(route1.getId(), route1Run1);

                RouteExecutionDTO routeExecutionDTO = routeExecutionManager.startRouteRunExecution(route1Run1.getId(), new RouteExecutorDTO(ramaUserId, ""));

                try {

                    routeExecutionManager.updateRouteExecutionStatus(routeExecutionDTO.getId(), RouteExecutionStatus.Started);

                    RouteExecutionLocationDTO rel = new RouteExecutionLocationDTO(
                            new LocationDTO(17.432483, 78.362863),
                            new RouteExecutionStopDTO(route1.getRouteLocations().get(0).getId()));
                    routeExecutionManager.postRouteExecutionLocation(ramaUserId, routeExecutionDTO.getId(), rel);

                    rel = new RouteExecutionLocationDTO(new LocationDTO(17.432299, 78.363963), null);
                    routeExecutionManager.postRouteExecutionLocation(ramaUserId, routeExecutionDTO.getId(), rel);

                    rel = new RouteExecutionLocationDTO(new LocationDTO(17.433378, 78.364655), null);
                    routeExecutionManager.postRouteExecutionLocation(ramaUserId, routeExecutionDTO.getId(), rel);

                    rel = new RouteExecutionLocationDTO(new LocationDTO(17.435010, 78.365771), null);
                    routeExecutionManager.postRouteExecutionLocation(ramaUserId, routeExecutionDTO.getId(), rel);

                    rel = new RouteExecutionLocationDTO(new LocationDTO(17.437316, 78.365190), null);
                    routeExecutionManager.postRouteExecutionLocation(ramaUserId, routeExecutionDTO.getId(), rel);

                } catch (Exception e) {
                    log.log(Level.WARNING, "Error updating route execution status [" + e.getMessage() + "]", e);
                }

                RouteRunDTO route1Run2 = new RouteRunDTO();
                route1Run2.setRouteId(route1.getId());
                route1Run2.setRouteExecutor(new RouteExecutorDTO(ramaUserId, ""));
                route1Run2.setDefaultStopPurpose(StopPurpose.PickupAndDropoff);
                route1Run2.setExecutionStartTime("Daily 1 PM");

                route1Run2 = routeConfigurationManager.configureRouteRun(route1.getId(), route1Run2);

                RouteRunDTO route1Run3 = new RouteRunDTO();
                route1Run3.setRouteId(route1.getId());
                route1Run3.setRouteExecutor(new RouteExecutorDTO(ramaUserId, ""));
                route1Run3.setDefaultStopPurpose(StopPurpose.Pickup);
                route1Run3.setExecutionStartTime("Daily 3.30 PM");

                route1Run3 = routeConfigurationManager.configureRouteRun(route1.getId(), route1Run3);

            } catch (Exception e) {
                log.log(Level.WARNING, "Error assigning route [" + e.getMessage() + "]", e);
            }

        } catch (Exception e) {
            log.log(Level.WARNING, "Error creating/assiging route [" + e.getMessage() + "]", e);
        }

    }

    private void createRoute2() {

        route2 = new RouteDTO();
        route2.setName("Route 2 - Jayabheri Silicon County");

        List<RouteLocationDTO> routeLocations = new ArrayList<>();

        routeLocations.add(new RouteLocationDTO(new LocationDTO(17.419847, 78.364436), new RouteStopDTO("Oakridge School (Start)", "Khajaguda, Hyderabad")));
        routeLocations.add(new RouteLocationDTO(new LocationDTO(17.422611, 78.381891), null));
        routeLocations.add(new RouteLocationDTO(new LocationDTO(17.437950, 78.364478), null));
        routeLocations.add(new RouteLocationDTO(new LocationDTO(17.443933, 78.362815), null));
        routeLocations.add(new RouteLocationDTO(new LocationDTO(17.454485, 78.363717), new RouteStopDTO("Whitefields", "Kothaguda, Hyderabad")));
        routeLocations.add(new RouteLocationDTO(new LocationDTO(17.458216, 78.370572), new RouteStopDTO("Jayabheri", "Kondapur, Hyderabad")));
        routeLocations.add(new RouteLocationDTO(new LocationDTO(17.456906, 78.376479), null));
        routeLocations.add(new RouteLocationDTO(new LocationDTO(17.429987, 78.375266), null));
        routeLocations.add(new RouteLocationDTO(new LocationDTO(17.419847, 78.364436), new RouteStopDTO("Oakridge School (End)", "Khajaguda, Hyderabad")));

        route2.setRouteLocations(routeLocations);

        try {

            route2 = routeConfigurationManager.createRoute(route2);

            for (RouteLocationDTO rl : route2.getRouteLocations()) {
                if (rl.getRouteStop() != null) {
                    route2Stops.add(rl);
                }
            }

            RouteRunDTO route2Run1 = new RouteRunDTO();
            route2Run1.setRouteId(route2.getId());
            route2Run1.setRouteExecutor(new RouteExecutorDTO(rohitUserId, ""));
            route2Run1.setDefaultStopPurpose(StopPurpose.Pickup);
            route2Run1.setExecutionStartTime("Daily 6.30 AM");

            route2Run1 = routeConfigurationManager.configureRouteRun(route1.getId(), route2Run1);

            RouteRunDTO route2Run2 = new RouteRunDTO();
            route2Run2.setRouteId(route2.getId());
            route2Run2.setRouteExecutor(new RouteExecutorDTO(rohitUserId, ""));
            route2Run2.setDefaultStopPurpose(StopPurpose.Pickup);
            route2Run2.setExecutionStartTime("Daily 3.30 PM");

            route2Run2 = routeConfigurationManager.configureRouteRun(route1.getId(), route2Run2);

        } catch (Exception e) {
            log.log(Level.WARNING, "Error creating/assiging route [" + e.getMessage() + "]", e);
        }

    }

    private void createObservables() {

        List<ObservableDTO> obs = new ArrayList<>();

        obs.add(new ObservableDTO("1", "Aditya", route1.getId(), route1Stops.get(0).getId(), StopPurpose.PickupAndDropoff));
        obs.add(new ObservableDTO("1", "Kavya", route1.getId(), route1Stops.get(0).getId(), StopPurpose.PickupAndDropoff));
        obs.add(new ObservableDTO("1", "Foo Foo", route1.getId(), route1Stops.get(1).getId(), StopPurpose.PickupAndDropoff));
        obs.add(new ObservableDTO("1", "Bar Bar", route1.getId(), route1Stops.get(2).getId(), StopPurpose.PickupAndDropoff));
        obs.add(new ObservableDTO("1", "Foo Bar", route1.getId(), route1Stops.get(2).getId(), StopPurpose.PickupAndDropoff));

        try {
            for (ObservableDTO dto : obs) {
                observableConfigurationManager.createUpdateObservable(dto);
            }
        } catch (Exception e) {
            log.log(Level.WARNING, "Error creating/assiging observables [" + e.getMessage() + "]", e);
        }
    }

}
