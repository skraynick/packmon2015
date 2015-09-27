package de.packmon;

import android.app.Fragment;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;


/**
 * @author Georgi.Alipiev
 *  Ugly as hell code for hackthon
 */
public class ShipmentInformationFragment extends Fragment implements AdapterView.OnItemClickListener {

    private WebView webView;
    private GridView gridView;
    private ImageButtonAdapter buttonAdapter;

    public ShipmentInformationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_parcel_info, container, false);


        webView = (WebView) root.findViewById(R.id.parcelinfo_webview);
        webView.loadUrl("http://screenshots.en.sftcdn.net/en/scrn/69671000/69671412/image-04-700x393.jpg");

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

        return root;
    }

    //TODO handle the clicking of each button to display the relevant information. order sensitive
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "Clicked: " + position, Toast.LENGTH_SHORT).show();
        ImageView img = (ImageView) view;
        img.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.box_closed));
    }
}
