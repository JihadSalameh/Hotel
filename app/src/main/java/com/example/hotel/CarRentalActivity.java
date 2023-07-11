package com.example.hotel;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CarRentalActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String urlAddress = "http://192.168.1.14/hotel/cars.php";
    String urlAddress1 = "http://192.168.1.14/hotel/bookCar.php";
    String[] carNumber, price, imagepath, carName;
    BufferedInputStream is;
    String line = null, result = null;
    ListView listView;
    EditText search;
    public static String currentCarName, currentCarNumber, nights, date, total;
    RequestQueue requestQueue;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saveSharedPref();
        setContentView(R.layout.activity_car_rental);

        initialize();
        collectData();
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        CustomListView customListView = new CustomListView(this,carNumber,price,imagepath);
        listView.setAdapter(customListView);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            AlertDialog dialog;
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            final View popUp = getLayoutInflater().inflate(R.layout.popup, null);

            TextView txt = findViewById(R.id.textView7);
            Button rent = popUp.findViewById(R.id.rent);
            Button noRent = popUp.findViewById(R.id.cancel);

            alertDialogBuilder.setView(popUp);
            dialog = alertDialogBuilder.create();
            dialog.show();
            //txt.setText("You can use the code: res123 by the reception to get 20% discount");

            rent.setOnClickListener(v -> {
                currentCarNumber = carNumber[i];
                rentCar(currentCarNumber);
                Intent intent=new Intent(CarRentalActivity.this, HomeActivity.class);
                startActivity(intent);
            });

            noRent.setOnClickListener(v -> dialog.dismiss());
        });

        search.setOnEditorActionListener((v, actionId, event) -> {
            try {
                String roomNo = search.getText().toString();
                int index = Integer.parseInt(roomNo.substring(3));
                listView.performItemClick(listView.getAdapter().getView(index-1, null, null), index-1, listView.getItemIdAtPosition(3));
            } catch(Exception e) {
                e.printStackTrace();
            }

            return true;
        });
    }

    private void rentCar(String currentCarNumber) {
        StringRequest request = new StringRequest(Request.Method.POST, urlAddress1,
                response -> Toast.makeText(getApplicationContext(), "car Reserved", Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show()) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("carNumber", currentCarNumber);
                return params;
            }
        };

        requestQueue.add(request);
    }

    private void saveSharedPref() {
        SharedPreferences sharedPreferences= getSharedPreferences(MainActivity.SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString(MainActivity.CAR_NUMBER,currentCarNumber);
        editor.putString(MainActivity.CAR_NAME,currentCarName);
        editor.putString(MainActivity.NIGHTS,nights);
        editor.putString(MainActivity.DATE,date);
        editor.putString(MainActivity.TOTAL,total);
        editor.apply();
    }

    private void initialize() {
        listView = findViewById(R.id.carListview);
        search = findViewById(R.id.carSearch);
        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
    }

    private void collectData() {
        //connection
        try {
            URL url=new URL(urlAddress);
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            is=new BufferedInputStream(con.getInputStream());
        } catch(Exception e) {
            e.printStackTrace();
        }

        //content
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            StringBuilder stringBuilder = new StringBuilder();

            while((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            is.close();
            result = stringBuilder.toString();
        } catch(Exception e) {
            e.printStackTrace();
        }

        //JSON
        try{
            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject;
            carNumber = new String[jsonArray.length()];
            price = new String[jsonArray.length()];
            imagepath = new String[jsonArray.length()];
            carName = new String[jsonArray.length()];

            for(int i = 0; i <= jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);

                carNumber[i] = jsonObject.getString("number");
                price[i] = jsonObject.getString("price_per_night");
                imagepath[i] = jsonObject.getString("photo");
                carName[i] = jsonObject.getString("name");
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void clearReservation(){
        currentCarName = "";
        currentCarNumber = "";
        nights = "";
        date = "";
        total = "";
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

}