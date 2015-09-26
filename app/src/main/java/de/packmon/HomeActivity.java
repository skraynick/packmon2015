package de.packmon;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapFragment;

public class HomeActivity extends FragmentActivity {

    private Fragment homeActivityFragment;
    private Fragment shipmentItemInformationFragment;
    private FragmentTabHost mTabHost;
    // map embedded in the map fragment
    private Map map = null;
    // map fragment embedded in this activity
    private MapFragment mapFragment = null;


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home);

            // Search for the map fragment to finish setup by calling init().
            mapFragment = (MapFragment)getFragmentManager().findFragmentById(
                    R.id.mapfragment);
            mapFragment.init(new OnEngineInitListener() {
                @Override
                public void onEngineInitializationCompleted(
                        OnEngineInitListener.Error error)
                {
                    if (error == OnEngineInitListener.Error.NONE) {
                        // retrieve a reference of the map from the map fragment
                        map = mapFragment.getMap();
                        // Set the map center to the Vancouver region (no animation)
                        map.setCenter(new GeoCoordinate(52.5167, 13.3833, 0.0),
                                Map.Animation.NONE);
                        // Set the zoom level to the average between min and max
                        map.setZoomLevel(
                                (map.getMaxZoomLevel() + map.getMinZoomLevel()) / 2);
                    } else {
                        System.out.println("ERROR: Cannot initialize Map Fragment");
                    }
                }
            });
        }


   /*     if (savedInstanceState == null) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            HomeActivityFragment fragment = new HomeActivityFragment();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {

        }
        return super.onOptionsItemSelected(item);
    }

*/

}