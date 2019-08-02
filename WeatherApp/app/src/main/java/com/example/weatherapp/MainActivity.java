package com.example.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText etcity;
    Button btn;
    String city;
    TextView cityname,temperature,minmaxtemp,main,description,humidity,clouds,cloudlbl,humiditylbl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn=(Button)findViewById(R.id.btn);        // submit button
        etcity=(EditText)findViewById(R.id.cityet);  // edit text for entering city name
        cityname=(TextView)findViewById(R.id.citytxt);     //  Textview for showing cityname
        temperature=(TextView)findViewById(R.id.temperature);  // Textview for displaying temperature
        minmaxtemp=(TextView)findViewById(R.id.minmaxtemp);   // Textview for displaying maximum and minimum temperature
        main=(TextView)findViewById(R.id.main);             // Textview for displaying main weather details
        description=(TextView)findViewById(R.id.description); // Textview for displaying description details
        humidity=(TextView)findViewById(R.id.humidityval);   // Textview for displaying humidity percentage
        clouds=(TextView)findViewById(R.id.cloudval);        // Textview for displaying clouds percentage
        humiditylbl=(TextView)findViewById(R.id.humiditylbl);  // label for displaying humidity
        cloudlbl=(TextView)findViewById(R.id.cloudlbl);        // label for displaying clouds

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWeather();
            }
        });
    }
    public void getWeather(){

        // base URl, ready to attach the city name
        final String baseURL="https://api.openweathermap.org/data/2.5/weather?q=";
        String key="&appid=05253b6c60edbaa5a338b98e90637ec9"; // api key of openweather map
        city=etcity.getText().toString();
        city=city.toLowerCase();
        Log.d(city,"city name");
        //  Attach user input, or "Halifax" if city name is none.
        String urlWithBase=baseURL.concat(TextUtils.isEmpty(city)?"Halifax":city);
        String finalURL=urlWithBase.concat(key);
        Log.d(finalURL,"url");

        final JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, finalURL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    // toast for successful response
                    Toast.makeText(getApplicationContext(),"Sucess",Toast.LENGTH_SHORT).show();
                        JSONObject main_object=response.getJSONObject("main");
                        JSONObject cloud_obj=response.getJSONObject("clouds");
                        JSONArray array=response.getJSONArray("weather");
                        JSONObject weather_obj=array.getJSONObject(0);
                        // parse the json object to get the required details
                        String temp=main_object.getString("temp");
                        String Humidity=main_object.getString("humidity")+"%";
                        String Clouds=cloud_obj.getString("all")+"%";
                        String Temp_Min=main_object.getString("temp_min");
                        String Temp_Max=main_object.getString("temp_max");
                        String Description=weather_obj.getString("description");
                        String Main_Weather=weather_obj.getString("main");
                        String City=response.getString("name");
                        // set values of textview to display all the details
                        cityname.setText(City);
                        main.setText(Main_Weather);
                        description.setText(Description);
                        humidity.setText(Humidity);
                        clouds.setText(Clouds);
                        cloudlbl.setText("Clouds");
                        humiditylbl.setText("Humidity");
                        // convert the  temperature in to celsius from kelvin
                        double temp_int=Double.parseDouble(temp);
                        double centi=(temp_int-273.15);
                        int temp_centi=(int)Math.round(centi);
                        temperature.setText(String.valueOf(temp_centi)+"\u00b0"+" C");

                        double temp_max=((Double.parseDouble(Temp_Max))-273.15);
                        int max=(int)Math.round(temp_max);

                        double temp_min=((Double.parseDouble(Temp_Min))-273.15);
                        int min=(int)Math.round(temp_min);

                        minmaxtemp.setText("Min. "+String.valueOf(min)+"\u00b0"+" C "+ "Max. "+String.valueOf(max)+"\u00b0"+" C");
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Error fetching data",Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Please Enter a valid city name like \'London'",Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(request);
    }
}
