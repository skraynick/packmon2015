package de.packmon;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.view.Menu;
import android.view.MenuItem;

import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapFragment;

import de.packmon.utils.RoutingTool;

public class HomeActivity extends FragmentActivity {

    private Fragment homeActivityFragment;
    private Fragment shipmentItemInformationFragment;
    private FragmentTabHost mTabHost;
    // map embedded in the map fragment
    private Map map = null;
    // map fragment embedded in this activity
    private String trackingCodeInputed;


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home);
            RenderMap renderMap = new RenderMap();
            renderMap.execute(map);
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.searchShipment) {
            showInputDialog();
        }

        return true;
    }

    private void showInputDialog() {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.shipment_tracking_dialogue, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        trackingCodeInputed = editText.getText().toString();

                        RoutingTool routing = new RoutingTool(
                                52.3667,
                                52.5167,
                                4.9000,
                                13.3833,
                                map);
                        Log.i("item", trackingCodeInputed);
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

   /*     if (savedInstanceState == null) {

       if (savedInstanceState == null) {
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

    private class RenderMap extends AsyncTask<Map, String, Map> {

        private MapFragment mapFragment = null;

        @Override
        protected Map doInBackground(Map... param) {
            try {
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
                            map.setCenter(new GeoCoordinate(
                                    52.5167,
                                    13.3833,
                                    0.0), Map.Animation.NONE);
                            // Set the zoom level to the average between min and max
                            map.setZoomLevel(
                                    (map.getMaxZoomLevel() + map.getMinZoomLevel()) / 2);
                        } else {
                            System.out.println("ERROR: Cannot initialize Map Fragment");
                        }
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return null;
        }

        protected void onProgressUpdate(String... progress) {
            // NO OP
        }

        protected void onPostExecute(Bitmap ret) {

        }
    }


}