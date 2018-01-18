package com.effone.machinetest.common;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.effone.machinetest.MainActivity;

/**
 * Created by Nagendra on 1/17/2018.
 */

public class StaticConfig {

    public static int REQUEST_CODE_REGISTER = 2000;

    public void openActivity(Context context, Class<?> calledActivity){
        Intent intent = new Intent(context,calledActivity);
        context.startActivity(intent);
    }

    public void toastMessages(Context context, String s) {
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }
}
