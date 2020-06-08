package com.lct9.pushlib.xiaomi;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;

import androidx.annotation.NonNull;

import com.lc9.core.PushLib;
import com.lc9.core.annotation.OperationType;
import com.lc9.core.receiver.IPushClient;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

/**
 * @author: xk
 * @date: 2020/5/18
 * @description:
 */
public class XiaomiClient implements IPushClient {
    private Context context;
    private String id;
    private String key;

    @Override
    public void init(Context context) {
        this.context = context;
    }

    @Override
    public void register() {
        if (shouldInit()) {
            MiPushClient.registerPush(context, id, key);
        }
    }

    @Override
    public void setIdAndKey(String app_id, String app_key) {
        id = app_id;
        key = app_key;
    }

    //小米注销没有广播
    @Override
    public void unregister() {
        MiPushClient.unregisterPush(context);
        PushLib.getInstance().sendOperationResult(context, OperationType.TYPE_UNREGISTER,
                0, null, null, null);
    }

    @Override
    public void stop() {
        MiPushClient.pausePush(context, null);
    }

    @Override
    public void resume() {
        MiPushClient.resumePush(context, null);
    }

    @Override
    public void setTags(@NonNull String... tags) {
    }

    @Override
    public void addTags(@NonNull String... tags) {

    }

    @Override
    public void deleteTags(@NonNull String... tags) {

    }

    @Override
    public void getTags() {

    }

    @Override
    public void setAlias(@NonNull String alias) {
        MiPushClient.setAlias(context, alias, null);
    }

    @Override
    public void deleteAlias(String alias) {
        MiPushClient.unsetAlias(context, alias, null);
    }

    @Override
    public void deleteAllAlias() {

    }

    @Override
    public void getAlias() {
        MiPushClient.getAllAlias(context);
    }

    @Override
    public void getToken() {
    }

    @Override
    public void clearNotification() {
        MiPushClient.clearNotification(context);
    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = context.getApplicationInfo().processName;
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
