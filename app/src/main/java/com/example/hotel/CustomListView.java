package com.example.hotel;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.Nullable;

import java.io.InputStream;

public class CustomListView extends ArrayAdapter<String>{

    private final String[] roomNumber, price, imagepath;
    private final Activity context;
    Bitmap bitmap;

    public CustomListView(Activity context,String[] roomNumber,String[] price,String[] imagepath)  {
        super(context, R.layout.layout,roomNumber);
        this.context = context;
        this.roomNumber = roomNumber;
        this.price = price;
        this.imagepath = imagepath;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View r=convertView;
        final ViewHolder viewHolder;
        if(r == null) {
            LayoutInflater layoutInflater=context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.layout,null,true);
            viewHolder= new ViewHolder(r);
            r.setTag(viewHolder);
        } else {
            viewHolder=(ViewHolder)r.getTag();
        }

        viewHolder.tvRoomNumber.setText(roomNumber[position]);
        viewHolder.tvPrice.setText(price[position]);
        new GetImageFromURL(viewHolder.ivPhoto).execute(imagepath[position]);

        return r;
    }

    static class ViewHolder{

        TextView tvRoomNumber, tvPrice;
        ImageView ivPhoto;

        ViewHolder(View v){
            tvRoomNumber = v.findViewById(R.id.textViewRoomNumber);
            tvPrice = v.findViewById(R.id.textViewPrice);
            ivPhoto = v.findViewById(R.id.imageViewRoomPhoto);
        }

    }

    public class GetImageFromURL extends AsyncTask<String,Void,Bitmap> {

        ImageView imgView;
        public GetImageFromURL(ImageView imgv)
        {
            this.imgView=imgv;
        }

        @Override
        protected Bitmap doInBackground(String... url) {
            String urldisplay = url[0];
            bitmap = null;

            try {
                InputStream ist=new java.net.URL(urldisplay).openStream();
                bitmap= BitmapFactory.decodeStream(ist);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap){
            super.onPostExecute(bitmap);
            imgView.setImageBitmap(bitmap);
        }

    }

}
