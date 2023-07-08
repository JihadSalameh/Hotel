package com.example.hotel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String urladdress = "http://192.168.1.9/hotel/rooms.php";
    String[] roomNumber, price, imagepath;
    BufferedInputStream is;
    String line = null, result = null;
    ListView listView;
    ImageView currentReserv;
    EditText search;
    public static String currentRoomNumber, nights, date, total;
    public static ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saveSharedPref();
        setContentView(R.layout.activity_home);

        initialize();
        collectData();

        System.out.println(this + "++" + Arrays.toString(roomNumber) + "++" + Arrays.toString(price) + "++" + Arrays.toString(imagepath));

        CustomListView customListView = new CustomListView(this,roomNumber,price,imagepath);
        listView.setAdapter(customListView);

        listView.setOnItemClickListener((adapter, view, position, arg) -> {
            TextView textView = view.findViewById(R.id.textViewRoomNumber);
            TextView textViewPrice = view.findViewById(R.id.textViewPrice);
            imageView = findViewById(R.id.imageViewRoomPhoto);
            listView.setEnabled(false);
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(() -> listView.setEnabled(true), 2000);

            if(imageView.getDrawable() != null) {
                Intent intent = new Intent(HomeActivity.this, RoomActivity.class);
                Bundle b = new Bundle();
                System.out.print("qqqq"+ imageView.getDrawable())  ;

                imageView.invalidate();
                BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                // Toast.makeText(getApplicationContext(), drawable.toString(), Toast.LENGTH_SHORT).show();
                Bitmap bitmap = drawable.getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                byte[] byteArray = stream.toByteArray();
                b.putString("key", textView.getText().toString().trim()); //Your id
                b.putString("price", textViewPrice.getText().toString().trim()); //Your id
                intent.putExtra("image",byteArray);
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
            }
        });

        search.setOnEditorActionListener((v, actionId, event) -> {
            try {
                String roomNo= search.getText().toString();
                int index = Integer.parseInt(roomNo.substring(3));
                listView.performItemClick(listView.getAdapter().getView(index-1, null, null), index-1, listView.getItemIdAtPosition(3));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        });

    }

    private void initialize() {
        listView = findViewById(R.id.lview);
        search = findViewById(R.id.edSearch);
        currentReserv = findViewById(R.id.currentResv);
        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
    }

    private void collectData() {
        //Connection
        try{
            URL url=new URL(urladdress);
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            is=new BufferedInputStream(con.getInputStream());
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        //content
        try{
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            StringBuilder sb=new StringBuilder();
            while ((line=br.readLine())!=null){
                sb.append(line+"\n");
            }
            is.close();
            result=sb.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //JSON
        try{
            JSONArray ja=new JSONArray(result);
            JSONObject jo=null;
            roomNumber=new String[ja.length()];
            price=new String[ja.length()];
            imagepath=new String[ja.length()];

            for(int i=0;i<=ja.length();i++){
                jo=ja.getJSONObject(i);
                roomNumber[i]=jo.getString("number");
                price[i]=jo.getString("price_per_night");
                imagepath[i]=jo.getString("photo");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public static void clearReservation(){
        currentRoomNumber="";
        nights="";
        date="";
        total="";
    }

    public void saveSharedPref(){
        SharedPreferences sharedPreferences= getSharedPreferences(MainActivity.SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString(MainActivity.ROOM,currentRoomNumber);
        editor.putString(MainActivity.NIGHTS,nights);
        editor.putString(MainActivity.DATE,date);
        editor.putString(MainActivity.TOTAL,total);
        editor.apply();
    }

    public void currentReserve(View view) {
        Intent intent = new Intent(HomeActivity.this , CurrentReservationActivity.class);
        startActivity(intent);
    }
}
