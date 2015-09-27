package de.packmon;

import android.app.Application;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.test.ApplicationTestCase;
import android.util.Log;
import android.widget.TextView;


import java.io.UnsupportedEncodingException;

import de.packmon.rabbitmq.MessageConsumer;

/**
 * Created by georg_000 on 26/09/2015.
 */
public class RabbitMQTest  extends ApplicationTestCase<Application> {

    private static final String TAG = "RabbitMQTesterClass" ;
    private MessageConsumer mConsumer;

    //TODO Rework this
    public RabbitMQTest(Class<Application> applicationClass) {
        super(applicationClass);
    }


    public void setUp() {
    }

    public void testMessageReceiving(){

    }


}