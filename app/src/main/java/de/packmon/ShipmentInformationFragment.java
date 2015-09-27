package de.packmon;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import de.packmon.rabbitmq.MessageConsumer;


/**
 * @author Georgi.Alipiev
 *  Ugly as hell code for hackthon
 */
public class ShipmentInformationFragment extends Fragment implements AdapterView.OnItemClickListener {

    private WebView webView;
    private GridView gridView;
    private ImageButtonAdapter buttonAdapter;
    private String openMSG;

    private MessageConsumer mConsumer;

    public ShipmentInformationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_parcel_info, container, false);

        mConsumer = new MessageConsumer(Values.SERVER_ADDRESS, Values.EXCHANGE_NAME, "fanout");
        new ConsumerConnect().execute();

//please don't post this ugly shit to the DailyWTF, too bored to do it properly
        mConsumer.setOnReceiveMessageHandler(new MessageConsumer.OnReceiveMessageHandler() {
            @Override
            public void onReceiveMessage(byte[] message) {
                String text = "";
                try {
                    text = new String(message, "UTF8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if(!text.equals("")){
                    try {
                        JSONObject receivedMSG;
                        receivedMSG = new JSONObject(text);
                        if (receivedMSG.has("eventlevel")){
                            if((int)receivedMSG.get("eventlevel") == 0) {
                                openMSG = receivedMSG.getString("message");
                                markPackageOpened();
                            }
                        }
                    } catch (JSONException ex){
                        Log.v("Shipment information", "Message was not empty, but still there was a JSON error. Malformed JSON?");
                    }
                }
            }
        });

        webView = (WebView) root.findViewById(R.id.parcelinfo_webview);
        webView.loadUrl("http://screenshots.en.sftcdn.net/en/scrn/69671000/69671412/image-04-700x393.jpg");

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

        ImageView img = (ImageView) view;
        switch (position){
            case 0:
                if(!openMSG.equals("")) {
                    Toast.makeText(getActivity(), openMSG, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "All seems to be okay", Toast.LENGTH_SHORT).show();
                }
                break;
            case 3:
                //TODO Sarah hook locaiton
                break;
        }
    }

    private void markPackageOpened(){
        //Order sensitive!
        Integer[] set2 = {R.drawable.lock_problem, R.drawable.temp_ok, R.drawable.warnnig,
                R.drawable.location, R.drawable.calendar, R.drawable.wrench};
        buttonAdapter.changeButtons(set2);
        buttonAdapter.notifyDataSetChanged();
        gridView.invalidateViews();
    }

    private class ConsumerConnect extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... Message) {
            try {
                mConsumer.connectToRabbitMQ();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            // TODO Auto-generated method stub
            return null;
        }
    }
}
