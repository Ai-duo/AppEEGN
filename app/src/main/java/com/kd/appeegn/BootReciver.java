package com.kd.appeegn;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context,MainActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
    }
}
