package com.example.administrator.julia_project;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;


public class BlandActivity extends AppCompatActivity {

    private TextView mTemperatureTextView;
    private TextView mWeatherConditionsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bland);

        mTemperatureTextView = (TextView) findViewById(R.id.temperature);
        mWeatherConditionsTextView = (TextView) findViewById(R.id.conditions);

        Weather weather = new Weather();
        weather.execute("http://api.openweathermap.org/data/2.5/weather?q=London,uk&units=metric");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bland, menu);
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
    public class Weather extends AsyncTask<String, Void, JSONObject>
    {

        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                InputStream inputStream = new URL(params[0]).openStream();
                String inputStreamString = new Scanner(inputStream,"UTF-8").useDelimiter("\\A").next();
                System.out.println(inputStreamString);

                JSONObject weather = new JSONObject(inputStreamString);
                return weather;

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            String temperature = null;
            String conditions = null;
            try {
                JSONObject weather = null;
                temperature = jsonObject.getJSONObject("main").getString("temp");
                conditions = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
                mTemperatureTextView.setText("Temperature: " + temperature + "\u2103");
                mWeatherConditionsTextView.setText("Conditions: " + conditions);
                System.out.println(temperature);
                System.out.println(conditions);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}

