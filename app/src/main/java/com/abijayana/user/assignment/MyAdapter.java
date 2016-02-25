package com.abijayana.user.assignment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by user on 12-02-2016.
 */
public class MyAdapter extends ArrayAdapter<abi> {
    ArrayList<abi> ArrayListabi;
    int Resourse;
    Context context;
    LayoutInflater lf;

    public MyAdapter(Context context, int resource, ArrayList<abi> objects) {
        super(context, resource, objects);
        ArrayListabi=objects;
        Resourse=resource;
        this.context=context;
        lf=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView==null){
            convertView=lf.inflate(Resourse,null);
            holder=new ViewHolder();
            holder.iv=(ImageView)convertView.findViewById(R.id.imagenew);
            holder.tv1=(TextView)convertView.findViewById(R.id.tv1);
            holder.tv2=(TextView)convertView.findViewById(R.id.tv2);
            holder.tv3=(TextView)convertView.findViewById(R.id.tv3);
            convertView.setTag(holder);


        }
        else{

            holder=(ViewHolder)convertView.getTag();
        }
        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.entering);

        convertView.startAnimation(animation);


        new Download(holder.iv).execute(ArrayListabi.get(position).getImage());
        holder.tv1.setText(ArrayListabi.get(position).getName());
        holder.tv2.setText(ArrayListabi.get(position).getDob());
        holder.tv3.setText(ArrayListabi.get(position).getDesc());










        return convertView;
    }
    static class ViewHolder{
        public ImageView iv;
        public TextView tv1;
        public TextView tv2;
        public TextView tv3;




    }
    public class Download extends AsyncTask<String, Void, Bitmap> {
        ImageView im;

        public Download(ImageView im){
           this.im=im;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String urldisplay=params[0];
            Bitmap micon=null;
            try {
                InputStream im=new java.net.URL(urldisplay).openStream();
                micon= BitmapFactory.decodeStream(im);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return micon;
        }

        @Override
        protected void onPostExecute(Bitmap btmp) {
            im.setImageBitmap(btmp);
        }
    }
}
