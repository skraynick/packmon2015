package de.packmon;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapFragment;


/**
 * @author Georgi.Alipiev
 *  Ugly as hell code for hackthon
 */
public class ShipmentInformationFragment extends Fragment implements AdapterView.OnItemClickListener {

    private WebView webView;
    private GridView gridView;
    private ImageButtonAdapter buttonAdapter;
    private Map map = null;

    public ShipmentInformationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_parcel_info, container, false);


        //webView = (WebView) root.findViewById(R.id.parcelinfo_webview);
        //webView.loadUrl("http://screenshots.en.sftcdn.net/en/scrn/69671000/69671412/image-04-700x393.jpg");

        //The rabbitMQ service would be executing the same code in order to change the icons 
        Button testButton = (Button) root.findViewById(R.id.parcelinfo_testButton);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Order sensitive!
                Integer[] set2 = {R.drawable.lock_problem, R.drawable.temp_problem, R.drawable.warnnig,
                        R.drawable.location, R.drawable.calendar, R.drawable.wrench};
                buttonAdapter.changeButtons(set2);
                buttonAdapter.notifyDataSetChanged();
                gridView.invalidateViews();

            }

        });

        gridView = (GridView) root.findViewById(R.id.parcelinfo_gridview);
        //Order sensitive!
        Integer[] set1 =  {R.drawable.lock_ok, R.drawable.temp_ok, R.drawable.warnnig,
                R.drawable.location, R.drawable.calendar, R.drawable.wrench};

        buttonAdapter = new ImageButtonAdapter(getActivity(),set1);
        gridView.setAdapter(buttonAdapter);
        gridView.setOnItemClickListener(this);

        RenderMap renderMap = new RenderMap();
        renderMap.execute(map);

        return root;
    }

    //TODO handle the clicking of each button to display the relevant information. order sensitive
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "Clicked: " + position, Toast.LENGTH_SHORT).show();
        ImageView img = (ImageView) view;
        img.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.boxclosed));
    }


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
