/*
 * MainActivity.java -- Simple demo application for the Thingsee cloud server agent
 *
 * Request 20 latest position measurements and displays them on the
 * listview wigdet.
 *
 * Note: you need to insert the following line before application -tag in
 * the AndroidManifest.xml file
 *  <uses-permission android:name="android.permission.INTERNET" />
 *
 * Author(s): Bishwas Shrestha
 * Modification(s):
 *   First version created on 04.02.2017
 *   Clears the positions array before button pressed 15.02.2017
 *   Stores username and password to SharedPreferences 17.02.2017
 */
package com.example.austi.thingsee;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.austi.thingsee.Main2Activity;
import com.example.austi.thingsee.MapsActivity;
import com.example.austi.thingsee.ThingSee;
import com.example.austi.thingsee.User;
import com.example.austi.thingsee.calculationActivity;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int    MAXPOSITIONS = 40;
    private static final String  PREFERENCEID = "Credentials";
    private static final String  data = "places";


    String nam, pas;

    private String username, password;
    private String[] positions = new String[MAXPOSITIONS];
    private ArrayAdapter<String> myAdapter;

    double sumDeltaX = 0;
    double sumDeltaY = 0;
    double totalDistance = 0;
    double velocity = 0;
    double lat,lon,timeTaken;
    private List<LatLng> lt ;
    LatLng latLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Context context = getApplicationContext();
        CharSequence text = "onCreate has been called";
        int length = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, "ohoho", length);
        toast.show();



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        // initialize the array so that every position has an object (even it is empty string)
        for (int i = 0; i < positions.length; i++)
            positions[i] = "";

        // setup the adapter for the array
        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, positions);

        // then connect it to the list in application's layout
        ListView listView = (ListView) findViewById(R.id.mylist);
        listView.setAdapter(myAdapter);

        // setup the button event listener to receive onClick events
        ((Button)findViewById(R.id.mybutton)).setOnClickListener(this);
        ((Button)findViewById(R.id.map)).setOnClickListener(this);
        ((Button)findViewById(R.id.calcbtn)).setOnClickListener(this);
        ((Button) findViewById(R.id.weather)).setOnClickListener(this);




        /*// check that we know username and password for the Thingsee cloud
        SharedPreferences prefGet = getSharedPreferences(PREFERENCEID, MainActivity.MODE_PRIVATE);
        username = prefGet.getString("username", "kari.salo@metropolia.fi");
        password = prefGet.getString("password", "Tinkku22");
=======
        // check that we know username and password for the Thingsee cloud
        SharedPreferences prefGet = getSharedPreferences(PREFERENCEID, Activity.MODE_PRIVATE);
        username = prefGet.getString("username", "");
        password = prefGet.getString("password", "");
>>>>>>> a2f76c364a3184d6663e45d21347e4d3a37f69d8
        if (username.length() == 0 || password.length() == 0)
            // no, ask them from the user
            queryDialog(this, getResources().getString(R.string.prompt));
    }

    private void queryDialog(Context context, String msg) {
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.credentials_dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final TextView dialogMsg      = (TextView) promptsView.findViewById(R.id.textViewDialogMsg);
        final EditText dialogUsername = (EditText) promptsView.findViewById(R.id.editTextDialogUsername);
        final EditText dialogPassword = (EditText) promptsView.findViewById(R.id.editTextDialogPassword);

        dialogMsg.setText(msg);
        dialogUsername.setText(username);
        dialogPassword.setText(password);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                username = dialogUsername.getText().toString();
                                password = dialogPassword.getText().toString();

                                SharedPreferences prefPut = getSharedPreferences(PREFERENCEID, MainActivity.MODE_PRIVATE);
                                SharedPreferences.Editor prefEditor = prefPut.edit();
                                prefEditor.putString("kari.salo@metropolia.fi",username);
                                prefEditor.putString("Tinkku22", password);
                                prefEditor.commit();

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();*/
    }



    public void onClick(View v) {
        Log.d("USR", "Button pressed");

        switch(v.getId()) {
            // we make the request to the Thingsee cloud server in backgroud
            // (AsyncTask) so that we don't block the UI (to prevent ANR state, Android Not Responding)
            case R.id.mybutton:
                /*User user = new User(MainActivity.this);
                user.setNam(nam);
                user.setPas(pas);*/
                SharedPreferences prefGet = getSharedPreferences(PREFERENCEID, MainActivity.MODE_PRIVATE);
                username = prefGet.getString("username", "kari.salo@metropolia.fi");
                password = prefGet.getString("password", "Tinkku22");
               /* username = getIntent().getExtras().getString("username");
                password = getIntent().getExtras().getString("password");*/

                new TalkToThingsee().execute("QueryState");

                break;


            case R.id.calcbtn:
                Intent intent = new Intent(this, calculationActivity.class);
                intent.putExtra("distance",totalDistance);
                intent.putExtra("velocity",velocity);
                startActivity(intent);


                break;
            case R.id.weather:
                Intent intent1 = new Intent(MainActivity.this, weather.class);
                startActivity(intent1);

                break;


            case R.id.map:
                intent = new Intent(this, MapsActivity.class);
                intent.putExtra("lat",lat);
                intent.putExtra("lon",lon);
                intent.putExtra("timeTaken", timeTaken);
                startActivity(intent);
                break;

            //case R.id.Guide:





        }
    }

    public void web(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://app.thingsee.com/#/login"));
        startActivity(i);

    }

    public void logout(View view) {
        new User(MainActivity.this).removeUser();
        startActivity(new Intent(MainActivity.this, Main2Activity.class));
        finish();
    }

    /* This class communicates with the ThingSee client on a separate thread (background processing)
     * so that it does not slow down the user interface (UI)
     */
    private class TalkToThingsee extends AsyncTask<String, Integer, String> {
        ThingSee thingsee;
        List<Location> coordinates = new ArrayList<Location>();



        @Override
        protected String doInBackground(String... params) {
            String result = "NOT OK";

            // here we make the request to the cloud server for MAXPOSITION number of coordinates
            try {
                thingsee = new ThingSee(username, password);

                JSONArray events = thingsee.Events(thingsee.Devices(), MAXPOSITIONS);
                System.out.println(events);
                coordinates = thingsee.getPath(events);

                for (Location coordinate: coordinates)
                    System.out.println(coordinate);
                result = "OK";
            } catch(Exception e) {
                Log.d("NET", "Communication error: " + e.getMessage());
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
         SensorValue sensorValue = new SensorValue();

            // check that the background communication with the client was succesfull
            if (result.equals("OK")) {
                // now the coordinates variable has those coordinates
                // elements of these coordinates is the Location object who has
                // fields for longitude, latitude and time when the position was fixed


                for (int i = 0; i < coordinates.size()-1; i++) {
                    Location loc = coordinates.get(i);

                    positions[i] = (new Date(loc.getTime())) +
                            " (" +sensorValue.getTemp() +"," + loc.getLatitude() + "," +
                            loc.getLongitude() + ")";
                    coordinates.get(i).toString();


                    calculateDistance(coordinates.get(i),coordinates.get(i+1));
                    User.addlat(loc.getLatitude());
                    User.addlng(loc.getLongitude());


                }


                long endTime = coordinates.get(0).getTime();
                long startTime = coordinates.get(coordinates.size()-1).getTime();
                long timeTaken = endTime-startTime;
                velocity = totalDistance*1000/(timeTaken);
                Log.d("DISTANCE", totalDistance + " velocity : " + velocity);

                lat = coordinates.get(coordinates.size()-1).getLatitude();
                lon = coordinates.get(coordinates.size()-1).getLongitude();
            } else {
                // no, tell that to the user and ask a new username/password pair
                positions[0] = getResources().getString(R.string.no_connection);
                //Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                //startActivity(intent);
                //queryDialog(MainActivity.this, getResources().getString(R.string.info_prompt));
            }
            myAdapter.notifyDataSetChanged();


        }

        @Override
        protected void onPreExecute() {
            // first clear the previous entries (if they exist)
            for (int i = 0; i < positions.length; i++)
                positions[i] = "";
            myAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }
    }

public void calculateDistance(Location loc1, Location loc2){

        sumDeltaX = (loc1.getLatitude()-loc2.getLatitude())*55.55;
        sumDeltaY = (loc1.getLongitude()-loc2.getLongitude())*111.11;
    totalDistance += Math.sqrt(Math.pow(sumDeltaX,2) + Math.pow(sumDeltaY,2));
}

    @Override
    protected void onStart() {

        Context context = getApplicationContext();
        CharSequence text = "onStart has been called";
        int length = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, "oloo", length);
        toast.show();
        super.onStart();



    }

    @Override
    protected void onPause() {

        super.onPause();


    }
}
