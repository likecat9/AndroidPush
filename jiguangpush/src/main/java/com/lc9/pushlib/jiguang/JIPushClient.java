package com.lc9.pushlib.jiguang;

import android.content.Context;

import androidx.annotation.NonNull;

import com.lc9.core.PushLib;
import com.lc9.core.annotation.OperationType;
import com.lc9.core.receiver.IPushClient;

import java.util.Arrays;
import java.util.HashSet;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.ups.JPushUPSManager;
import cn.jpush.android.ups.TokenResult;
import cn.jpush.android.ups.UPSRegisterCallBack;
import cn.jpush.android.ups.UPSTurnCallBack;
import cn.jpush.android.ups.UPSUnRegisterCallBack;

/**
 * @author: xk
 * @date: 2020/5/15
 * @description:
 */
public class JIPushClient implements IPushClient {
    private Context context;
    public static final int ADD_TAG_SEQUENCE = 771;
    public static final int DELETE_TAG_SEQUENCE = 772;
    public static final int SET_TAG_SEQUENCE = 773;
    public static final int GET_ALL_TAG_SEQUENCE = 774;
    public static final int GET_ALL_ALIAS_SEQUENCE = 775;
    public static final int SET_ALIAS_SEQUENCE = 776;
    public static final int DELETE_ALIAS_SEQUENCE = 777;
    private String app_id;
    private String app_key;

    @Override
    public void init(Context context) {
        this.context = context;
    }

    @Override
    public void register() {
        JPushUPSManager.registerToken(context, "8704267958ee6d0b0fed288b", null, null, new UPSRegisterCallBack() {
            @Override
            public void onResult(TokenResult tokenResult) {
                PushLib.getInstance()
                        .sendOperationResult(context, OperationType.TYPE_REGISTER,
                                tokenResult.getReturnCode(), tokenResult.toString(), null, null);
            }
        });
    }

    @Override
    public void setIdAndKey(String app_id, String app_key) {
        this.app_id = app_id;
        this.app_key = app_key;
        register();
    }

    @Override
    public void unregister() {
        JPushUPSManager.unRegisterToken(context, new UPSUnRegisterCallBack() {
            @Override
            public void onResult(TokenResult tokenResult) {
                PushLib.getInstance().sendOperationResult(context, OperationType.TYPE_UNREGISTER,
                        tokenResult.getReturnCode(), tokenResult.toString(), null, null);
            }
        });
    }

    @Override
    public void stop() {
        JPushUPSManager.turnOffPush(context, new UPSTurnCallBack() {
            @Override
            public void onResult(TokenResult tokenResult) {
                PushLib.getInstance().sendOperationResult(context, OperationType.TYPE_STOP,
                        tokenResult.getReturnCode(), tokenResult.toString(), null, null);
            }
        });
    }

    @Override
    public void resume() {
        JPushUPSManager.turnOnPush(context, new UPSTurnCallBack() {
            @Override
            public void onResult(TokenResult tokenResult) {
                PushLib.getInstance().sendOperationResult(context, OperationType.TYPE_RESUME,
                        tokenResult.getReturnCode(), tokenResult.toString(), null, null);
            }
        });
    }

    @Override
    public void setTags(String... tags) {
        JPushInterface.addTags(context, SET_TAG_SEQUENCE, new HashSet<>(Arrays.asList(tags)));
    }

    //这个接口是覆盖逻辑，而不是增量逻辑。即新的调用会覆盖之前的设置。
    @Override
    public void addTags(@NonNull String... tags) {
        JPushInterface.addTags(context, ADD_TAG_SEQUENCE, new HashSet<>(Arrays.asList(tags)));
    }

    @Override
    public void deleteTags(@NonNull String... tags) {
        JPushInterface.deleteTags(context, DELETE_TAG_SEQUENCE, new HashSet<String>(Arrays.asList(tags)));
    }

    @Override
    public void getTags() {
        JPushInterface.getAllTags(context, GET_ALL_TAG_SEQUENCE);
    }

    @Override
    public void setAlias(@NonNull String alias) {
        JPushInterface.setAlias(context, SET_ALIAS_SEQUENCE, alias);
    }

    @Override
    public void deleteAlias(@NonNull String alias) {

    }

    @Override
    public void deleteAllAlias() {
        JPushInterface.deleteAlias(context, DELETE_ALIAS_SEQUENCE);
    }

    @Override
    public void getAlias() {
        JPushInterface.getAlias(context, GET_ALL_ALIAS_SEQUENCE);
    }

    @Override
    public void getToken() {

    }

    @Override
    public void clearNotification() {
        JPushInterface.clearAllNotifications(context);
    }
}
