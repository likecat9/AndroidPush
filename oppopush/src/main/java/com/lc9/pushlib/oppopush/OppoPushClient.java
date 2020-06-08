package com.lc9.pushlib.oppopush;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.heytap.msp.push.HeytapPushManager;
import com.heytap.msp.push.callback.ICallBackResultService;
import com.lc9.core.PushLib;
import com.lc9.core.annotation.OperationType;
import com.lc9.core.receiver.IPushClient;

/**
 * @author: xk
 * @date: 2020/5/15
 * @description:
 */
public class OppoPushClient implements IPushClient {
    private Context context;
    private String id;
    private String key;

    @Override
    public void init(Context context) {
        this.context = context;
        HeytapPushManager.requestNotificationPermission();
        HeytapPushManager.init(context, true);
    }

    @Override
    public void register() {
        boolean supportPush = HeytapPushManager.isSupportPush();
        if (supportPush) {
            HeytapPushManager.register(context, id, key, new ICallBackResultService() {
                @Override
                public void onRegister(int i, String s) {
                    PushLib.getInstance().sendOperationResult(context, OperationType.TYPE_REGISTER,
                            0, s, null, null);
                }

                @Override
                public void onUnRegister(int i) {
                    PushLib.getInstance().sendOperationResult(context, OperationType.TYPE_UNREGISTER,
                            i, null, null, null);
                }

                @Override
                public void onSetPushTime(int i, String s) {

                }

                @Override
                public void onGetPushStatus(int i, int i1) {

                }

                @Override
                public void onGetNotificationStatus(int i, int i1) {

                }
            });
        } else {
            Log.d("OppoPushClient", "此设备不支持OPPO推送！！！");
            PushLib.getInstance().sendOperationResult(context, OperationType.TYPE_REGISTER,
                    -1, "", null, null);
            for (int i = 0; i < 2; i++) {
                PushLib.getInstance().addErrorTime();
            }
        }
    }

    @Override
    public void setIdAndKey(String app_id, String app_key) {
        id = app_id;
        key = app_key;
    }

    @Override
    public void unregister() {
        HeytapPushManager.unRegister();
    }

    @Override
    public void stop() {
        HeytapPushManager.pausePush();
    }

    @Override
    public void resume() {
        HeytapPushManager.resumePush();
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
    }

    @Override
    public void deleteAlias(@NonNull String alias) {

    }

    @Override
    public void deleteAllAlias() {

    }

    @Override
    public void getAlias() {

    }

    @Override
    public void getToken() {
    }

    @Override
    public void clearNotification() {

    }
}
