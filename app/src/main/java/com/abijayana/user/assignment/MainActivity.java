package com.abijayana.user.assignment;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv;String rslt="";
    MyAdapter adapter;
    ArrayList<abi> abiList;
    Animation abhii;
    LinearLayout ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv=(ListView)findViewById(R.id.lsvw1);
        abhii= AnimationUtils.loadAnimation(MainActivity.this,R.anim.entering);
        abiList=new ArrayList<abi>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#FFB8860B"));
        }
        new Astsk().execute("https://api.myjson.com/bins/1adnn");
    }

    public class Astsk extends AsyncTask<String,Void,Boolean>{


        @Override
        protected Boolean doInBackground(String... params) {
            try {
                URL url=new URL(params[0]);
                HttpURLConnection htpcon=(HttpURLConnection)url.openConnection();
                htpcon.connect();
                BufferedReader bf=new BufferedReader(new InputStreamReader(htpcon.getInputStream()));
                String ld;
                while((ld=bf.readLine())!=null){
                    rslt=rslt+ld;
                }
                JSONObject jsno=new JSONObject(rslt);
                JSONArray jarray=jsno.getJSONArray("family");
                for(int i=0;i<jarray.length();i++)
                {
                    JSONObject jsn=jarray.getJSONObject(i);
                    abi th=new abi();
                    th.setName(jsn.getString("name"));
                    th.setDob(jsn.getString("description"));
                    th.setDesc(jsn.getString("desc"));
                    th.setImage(jsn.getString("image"));
                    abiList.add(th);

                }
                return true;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if(result==false){
                Toast.makeText(MainActivity.this,"PARSE ERROR",Toast.LENGTH_SHORT).show();
            }
            else{
                adapter=new MyAdapter(getApplicationContext(),R.layout.single,abiList);
                lv.setAdapter(adapter);

                //ad.startAnimation(abhii);
            }
        }
    }


}
