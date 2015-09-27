package de.packmon.utils;

import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.ViewObject;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapGesture;
import com.here.android.mpa.mapping.MapMarker;
import com.here.android.mpa.mapping.MapObject;

import java.io.IOException;
import java.util.List;


/**
 * Created by sarah on 27.09.15.
 */
public class MapMarkerUtil {

    public void setMarker(int markerStringLocation, double markerLat, double markerLong, Map map){
            com.here.android.mpa.common.Image myImage =
                    new com.here.android.mpa.common.Image();

            try {
                myImage.setImageResource(markerStringLocation);
            } catch (IOException e) {
                //finish();
            }

            GeoCoordinate geoCoordinate = new GeoCoordinate(markerLat, markerLong);
            // Create the MapMarkerUtil
            MapMarker myMapMarker =
                    new MapMarker(geoCoordinate, myImage);

            map.addMapObject(myMapMarker);


            // Create a gesture listener and add it to the MapFragment
            MapGesture.OnGestureListener listener =
                    new MapGesture.OnGestureListener.OnGestureListenerAdapter() {
                        @Override
                        public boolean onMapObjectsSelected(List<ViewObject> objects) {
                            for (ViewObject viewObj : objects) {
                                if (viewObj.getBaseType() == ViewObject.Type.USER_OBJECT) {
                                    if (((MapObject) viewObj).getType() == MapObject.Type.MARKER) {
                                        // At this point we have the originally added
                                        // map marker, so we can do something with it
                                        // (like change the visibility, or more
                                        // marker-specific actions)
                                        ((MapObject) viewObj).setVisible(false);
                                    }
                                }
                            }
                            // return false to allow the map to handle this callback also
                            return false;
                        }

                    };
    }
}
