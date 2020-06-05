package com.lc9.core.receiver;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import com.lc9.core.annotation.PushActions;

/**
 * @author: xk
 * @date: 2020/5/14
 * @description:
 */
public class PushSender {
    private static String SEND_DATA = "com.test.pushlib.sendData";

    public static void sendData(Context context, @PushActions String action, ComponentName componentName, Parcelable data) {
        Intent intent = new Intent();
        intent.putExtra(SEND_DATA, data);
        intent.setAction(action);
        //指定接收pushlib广播的广播接收器
        intent.setComponent(componentName);
        intent.setPackage(context.getPackageName());
        context.sendBroadcast(intent);
    }

    public static <T extends Parcelable> T getParcelableData(Intent intent) {
        return intent.getParcelableExtra(SEND_DATA);
    }
}
