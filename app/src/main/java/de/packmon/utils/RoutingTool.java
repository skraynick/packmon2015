package de.packmon.utils;

import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapRoute;
import com.here.android.mpa.routing.RouteManager;
import com.here.android.mpa.routing.RouteOptions;
import com.here.android.mpa.routing.RoutePlan;
import com.here.android.mpa.routing.RouteResult;

import java.util.List;

/**
 * Created by sarah on 26.09.15.
 */
public class RoutingTool
{
    private RoutePlan routePlan;
    private MapRoute mapRoute;
    private Map map;

    public RoutingTool(double latitudeStart,
                       double latitudeEnd,
                       double longitudeStart,
                       double longitudeEnd,
                       Map map){

        RouteManager rm = new RouteManager();

        // Create the RoutePlan and add two waypoints
        routePlan = new RoutePlan();
        routePlan.addWaypoint(new GeoCoordinate(latitudeStart, longitudeStart));
        routePlan.addWaypoint(new GeoCoordinate(latitudeEnd, longitudeEnd));
        setTransportOptionsForRoute();
        rm.calculateRoute(routePlan, new RouteListener(map));
    }


    private void setTransportOptionsForRoute() {
        // Create the RouteOptions and set its transport mode & routing type
        RouteOptions routeOptions = new RouteOptions();
        routeOptions.setTransportMode(RouteOptions.TransportMode.CAR);
        routeOptions.setRouteType(RouteOptions.Type.FASTEST);

        routePlan.setRouteOptions(routeOptions);
    }

    public void setExtraWayPoint(double latitude, double longitude) {
        //fill in for people to be able to set way point for custom routing.
    }

    private class RouteListener implements RouteManager.Listener {

        private Map map;
        public RouteListener(Map map){
            setMap(map);
        }

        // Method defined in Listener
        public void onProgress(int percentage) {
            // Display a message indicating calculation progress
        }

        public void setMap(Map map){
            this.map = map;
        }
        // Method defined in Listener
        public void onCalculateRouteFinished(
                RouteManager.Error error,
                List<RouteResult> routeResult) {

            // If the route was calculated successfully
            if (error == RouteManager.Error.NONE) {
                // Render the route on the map
                mapRoute = new MapRoute(routeResult.get(0).getRoute());
                map.addMapObject(mapRoute);

            }
            else {
                // Display a message indicating route calculation failure
            }
        }
    }
}
