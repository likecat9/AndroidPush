package com.lc9.pushlib.huawei;

import android.content.Context;

import androidx.annotation.NonNull;

import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.hmf.tasks.OnCompleteListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.push.HmsMessaging;
import com.lc9.core.PushLib;
import com.lc9.core.annotation.OperationType;
import com.lc9.core.receiver.IPushClient;

/**
 * @author: xk
 * @date: 2020/5/18
 * @description:
 */
public class HuaweiClient implements IPushClient {
    private Context context;

    @Override
    public void init(Context context) {
        this.context = context;
    }

    @Override
    public void register() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String appId = AGConnectServicesConfig.fromContext(context).getString("client/app_id");
                    String token = HmsInstanceId.getInstance(context).getToken(appId, "HCM");
                    if (token == null) {
                        PushLib.getInstance().sendOperationResult(context, OperationType.TYPE_REGISTER,
                                -1, null, null, "华为推送获取token失败！");
                    } else {
                        PushLib.getInstance().sendOperationResult(context, OperationType.TYPE_REGISTER,
                                0, token, null, null);
                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void setIdAndKey(String app_id, String app_key) {
        register();
    }

    @Override
    public void unregister() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String appId = AGConnectServicesConfig.fromContext(context).getString("client/app_id");
                    HmsInstanceId.getInstance(context).deleteToken(appId, "HCM");
                    PushLib.getInstance().sendOperationResult(context, OperationType.TYPE_UNREGISTER,
                            0, null, null, null);
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void stop() {
        HmsMessaging.getInstance(context).turnOffPush().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                if (task.isSuccessful()) {
                    PushLib.getInstance().printLog("turnOffPush Complete");
                } else {
                    PushLib.getInstance().printLog("turnOffPush failed: ret=" + task.getException().getMessage());
                }
            }
        });
    }

    @Override
    public void resume() {
        HmsMessaging.getInstance(context).turnOnPush().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                if (task.isSuccessful()) {
                    PushLib.getInstance().printLog("turnOnPush Complete");
                } else {
                    PushLib.getInstance().printLog("turnOnPush failed: ret=" + task.getException().getMessage());
                }
            }
        });
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
