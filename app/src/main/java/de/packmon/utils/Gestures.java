package de.packmon.utils;

import android.graphics.PointF;

import com.here.android.mpa.common.ViewObject;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapGesture;

import java.util.List;

/**
 * Created by sarah on 26.09.15.
 */
public class Gestures implements MapGesture.OnGestureListener {

    private Map map;

    public Gestures(Map map) {
        this.map = map;
    }

    @Override
    public void onPanStart() {
    }

    @Override
    public void onPanEnd() {
    }

    @Override
    public void onMultiFingerManipulationStart() {
    }

    @Override
    public void onMultiFingerManipulationEnd() {
    }

    @Override
    public boolean onMapObjectsSelected(List<ViewObject> objects) {
        return false;
    }

    @Override
    public boolean onTapEvent(PointF p) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(PointF p) {
        return false;
    }

    @Override
    public void onPinchLocked() {
    }

    @Override
    public boolean onPinchZoomEvent(float scaleFactor, PointF p) {
        return false;
    }

    @Override
    public void onRotateLocked() {
    }

    @Override
    public boolean onRotateEvent(float rotateAngle) {
        return false;
    }

    @Override
    public boolean onTiltEvent(float angle) {
        return false;
    }

    @Override
    public boolean onLongPressEvent(PointF p) {
        return false;
    }

    @Override
    public void onLongPressRelease() {
    }

    @Override
    public boolean onTwoFingerTapEvent(PointF p) {
        // Create a custom marker image
        com.here.android.mpa.common.Image myImage =
                new com.here.android.mpa.common.Image();

      /*  try {
            myImage.setImageResource(R.drawable.boxclosed);
        } catch (IOException e) {
            //finish();
        }*/

    // Create the MapMarkerUtil
        /*GeoCoordinate geoLocation = new GeoCoordinate();
        MapMarkerUtil myMapMarker =
               // new MapMarkerUtil(new GeoCoordinate(GeoCoordinategetCoordinate()), myImage);

        //ap.addMapObject(myMapMarker);*/
        return false;
    }
}
