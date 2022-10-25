package com.kd.appeegn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.kd.appeegn.databinding.ActivityMainBinding;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    Retrofit retrofit;
    Call<Info> ee;
    ActivityMainBinding amb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        amb = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initRetrofit();
        initTask();
        start();
    }
    HttpAPI httpAPI;
    private void initRetrofit() {
        retrofit = new Retrofit.Builder().baseUrl("http://115.220.4.68:8081/qxdata/QxService.svc/getqxo2data/").addConverterFactory(GsonConverterFactory.create()).build();
       httpAPI= retrofit.create(HttpAPI.class);


    }

    Timer tr, timer1;

    private void start() {
        tr = new Timer();
        tr.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    ee = httpAPI.getInfo("EET01");
                    Response<Info> execute = ee.execute();
                    if (execute.isSuccessful()) {
                        try {
                            final Info info = execute.body();
                            if (info!=null)
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        amb.setInfo(info);;
                                        Log.i("TAG","更新信息："+info);
                                    }
                                });
                            Log.i("TAG","更新信息");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 20 * 1000);
    }

    public TimerTask dates;
    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年M月dd日 E HH:mm", Locale.CHINA);

    public void initTask() {

        dates = new TimerTask() {
            @Override
            public void run() {
                final Lunar lunar = new Lunar(Calendar.getInstance());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        amb.setDate(dateFormat.format(new Date()) + "\n" + lunar.getAllDate());
                    }
                });
            }
        };
        timer1 = new Timer();

        timer1.schedule(dates, 0, 40 * 1000);
    }
}