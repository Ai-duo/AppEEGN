package com.kd.appeegn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.kd.appeegn.databinding.ActivityMainBinding;

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
        amb = DataBindingUtil.setContentView(this,R.layout.activity_main);
        initRetrofit();
        initTask();
        start();
    }
    private void initRetrofit(){
        retrofit = new Retrofit.Builder().baseUrl("http://115.220.4.68:8081/qxdata/QxService.svc/getqxo2data/").addConverterFactory(GsonConverterFactory.create()).build();
        HttpAPI httpAPI = retrofit.create(HttpAPI.class);
        ee = httpAPI.getInfo("EET01");

    }
    Timer tr,timer1;
    private void start(){
        tr = new Timer();
        tr.schedule(new TimerTask() {
            @Override
            public void run() {
                ee.enqueue(new Callback<Info>() {
                    @Override
                    public void onResponse(Call<Info> call, Response<Info> response) {
                        if(response.isSuccessful()){
                            try{
                                final Info info = response.body();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        amb.setInfo(info);
                                    }
                                });
                            }catch (Exception e){

                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<Info> call, Throwable t) {

                    }
                });
            }
        },0,60*60*1000);
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
                        amb.setDate(dateFormat.format(new Date())+"\n"+lunar.getAllDate());
                    }
                });
            }
        };
        timer1 = new Timer();

        timer1.schedule(dates, 0, 40 * 1000);
    }
}