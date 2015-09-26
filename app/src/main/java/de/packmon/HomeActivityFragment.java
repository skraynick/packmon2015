package de.packmon;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import retrofit.Call;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Path;


/**
 * A placeholder fragment containing a simple view.
 */
public class HomeActivityFragment extends Fragment {

    private View view;
    public static final String BASE_URL = "http://{1-4}.base.maps.api.here.com";



    public HomeActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .build();

        GitHubService service = retrofit.create(GitHubService.class);
        view = inflater.inflate(R.layout.fragment_home, container, false);
        showMap(view);
        return view;
    }

    public interface GitHubService {
        @GET("/users/{user}/repos")
        Call<List<String>> listRepos(@Path("skraynick") String user);
    }

    private void showMap(View view) {
        try {
            //URL url = new URL("http://2.base.maps.cit.api.here.com/maptile/2.1/basetile/newest/normal.day/14/4769/6203/256/png8?app_id=&app_code=c_rjgR49ZG44tUJodLsYDw");
            URL url = new URL("https://www.google.de/maps");
            TextView viewer = (TextView) view.findViewById(R.id.map);
            viewer.setText(url.toString());


        } catch (Exception ex) {
            System.out.print(ex);

        }    }


    private class RenderMapAPI extends AsyncTask<String, String, String> {

        //String urlString=params[0];
        String resultToDisplay;
        //emailVerificationResult result = null;
        InputStream in = null;


        @Override
        protected String doInBackground(String... params) {
            // HTTP Get
           try {
               URL url = new URL("http://2.base.maps.cit.api.here.com/maptile/2.1/basetile/newest/normal.day/14/4769/6203/256/png8?app_id=&app_code=c_rjgR49ZG44tUJodLsYDw");
               HttpURLConnection connection  = (HttpURLConnection) url.openConnection();

               InputStream is = connection.getInputStream();
               Bitmap img = BitmapFactory.decodeStream(is);

               TextView view = (TextView) getActivity().findViewById(R.id.map);
               view.setText(url.toString());
           } catch (Exception ex) {
               System.out.print(ex);

            }

            return "";
        }

        protected void onPostExecute(String result) {
            //Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
           // intent.putExtra(EXTRA_MESSAGE, result);
           // startActivity(intent);
        }
    }
}
