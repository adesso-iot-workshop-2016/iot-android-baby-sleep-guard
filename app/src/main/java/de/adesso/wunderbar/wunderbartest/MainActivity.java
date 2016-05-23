package de.adesso.wunderbar.wunderbartest;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import de.adesso.wunderbar.wunderbartest.Helper.SensorValueInfo;
import io.relayr.android.RelayrSdk;
import io.relayr.java.model.Transmitter;
import io.relayr.java.model.TransmitterDevice;
import io.relayr.java.model.User;
import io.relayr.java.model.action.Reading;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {

    private Subscription mDeviceSubscription;
    private Subscription mTransmitterSubscription;
    private Subscription mUserInfoSubscription;
    private boolean logInStarted = false;
    private HashMap<String,View> sensorRows = new HashMap();
    private LinearLayout container;
    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        container = (LinearLayout)findViewById(R.id.value_list_container);
        inflater =(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        new RelayrSdk.Builder(getApplicationContext()).inMockMode(false).build();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkUserState();
    }

    private void checkUserState() {
        if (RelayrSdk.isUserLoggedIn()) {
            loadUserInfo();
        } else {
            if (logInStarted) {
                onBackPressed();
            } else {
                logInStarted = true;
                RelayrSdk.logIn(this).subscribe(new Observer<User>() {
                    @Override public void onCompleted() {}

                    @Override public void onError(Throwable e) {
                        logInStarted = false;
                        Toast.makeText(MainActivity.this,"Login unsuccessful",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override public void onNext(User user) {
                        logInStarted = false;
                        Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    private void loadUserInfo() {
        mUserInfoSubscription = RelayrSdk.getUser()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override public void onCompleted() {}

                    @Override public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, "No Userdata found",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override public void onNext(User user) {
                        loadTransmitters(user);
                    }
                });
    }

    private void loadDevice(final String transmitterId) {
        mDeviceSubscription = RelayrSdk.getRelayrApi()
                .getTransmitterDevices(transmitterId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<TransmitterDevice>>() {
                    @Override public void onCompleted() {}

                    @Override public void onError(Throwable e) {
                        showToast(R.string.error_loading_device_data, " " + e.getMessage());
                    }

                    @Override public void onNext(List<TransmitterDevice> transmitterDevices) {
                        for (TransmitterDevice device : transmitterDevices) {
                            subscribeForDeviceReadings(device);
                        }
                    }
                });
    }

    private void subscribeForDeviceReadings(final TransmitterDevice device) {
        device.subscribeToCloudReadings()
                //.timeout(7, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Reading>() {
                    @Override public void onCompleted() {}

                    @Override public void onError(Throwable e) {
                        showToast(R.string.error_loading_device_data, e.getMessage());
                    }

                    @Override public void onNext(Reading reading) {
                        updateValue( reading);
                    }
                });
    }

    private void updateValue(final Reading reading){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearLayout row = (LinearLayout) sensorRows.get(reading.meaning);
                if(row==null){
                    row = (LinearLayout) inflater.inflate(R.layout.sensor_entry, null);
                    container.addView(row);
                    sensorRows.put(reading.meaning,row);
                }
                TextView nameView = (TextView)row.findViewById(R.id.sensor_entry_name);
                TextView valueView  = (TextView)row.findViewById(R.id.sensor_entry_value);
                TextView infoView  = (TextView)row.findViewById(R.id.sensor_entry_info);

                nameView.setText(reading.meaning);
                SensorValueInfo info = SensorValueInfos.getByName(reading.meaning);
                valueView.setText(info.getReadableString(reading));
                infoView.setText(info.getInfo(reading));
                infoView.setTextColor(info.getColor(reading));
            }
        });
    }

    private void loadTransmitters(User user) {
        mTransmitterSubscription = user.getTransmitters()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Transmitter>>() {
                    @Override public void onCompleted() {}

                    @Override public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, R.string.error_loading_transmitters, Toast.LENGTH_LONG).show();
                    }

                    @Override public void onNext(List<Transmitter> transmitters) {
                        if(transmitters.size()==0){
                            Toast.makeText(MainActivity.this, "No transmitters detected", Toast.LENGTH_LONG).show();
                        } else {
                            for(Transmitter transmitter : transmitters){
                                loadDevice(transmitter.getId());
                            }
                        }
                    }
                });
    }

    protected void showToast(final int stringId,final String msg) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), getApplicationContext().getString(stringId) + " " + msg, Toast.LENGTH_LONG).show();
                }
            });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
