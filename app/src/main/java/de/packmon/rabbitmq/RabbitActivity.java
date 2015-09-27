package de.packmon.rabbitmq;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;

import de.packmon.R;
import de.packmon.Values;

public class RabbitActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    private TextView mRlog;
    private TextView mRmsg;

    private MessageConsumer mConsumer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rabbit);

        mRlog = (TextView) findViewById(R.id.rabbit_log);
        mRmsg = (TextView) findViewById(R.id.rabbit_msg);

        mConsumer = new MessageConsumer(Values.SERVER_ADDRESS, Values.EXCHANGE_NAME, "fanout");
        new ConsumerConnect().execute();


        mConsumer.setOnReceiveMessageHandler(new MessageConsumer.OnReceiveMessageHandler() {
            @Override
            public void onReceiveMessage(byte[] message) {
                String text = "";
                try {
                    text = new String(message, "UTF8");
                    mRmsg.setText(mRmsg.getText() + "\n" + text);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.v(TAG, "Received Message with text: " + text);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rabbit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
