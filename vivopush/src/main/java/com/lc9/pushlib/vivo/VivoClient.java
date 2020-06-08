package com.lc9.pushlib.vivo;

import android.content.Context;

import androidx.annotation.NonNull;

import com.lc9.core.PushLib;
import com.lc9.core.annotation.OperationType;
import com.lc9.core.receiver.IPushClient;
import com.vivo.push.IPushActionListener;
import com.vivo.push.PushClient;
import com.vivo.push.ups.CodeResult;
import com.vivo.push.ups.TokenResult;
import com.vivo.push.ups.UPSRegisterCallback;
import com.vivo.push.ups.UPSTurnCallback;
import com.vivo.push.ups.VUpsManager;

/**
 * @author: xk
 * @date: 2020/5/19
 * @description:
 */
public class VivoClient implements IPushClient {
    private Context context;
    private String id;
    private String key;
    @Override
    public void init(final Context context) {
        this.context = context;
        PushClient.getInstance(context).initialize();
    }

    @Override
    public void register() {
        VUpsManager.getInstance().registerToken(context, id, key,
                "d4e577fc-3daa-45bc-a877-58e4e8a4ea4d", new UPSRegisterCallback() {
            @Override
            public void onResult(TokenResult tokenResult) {
                PushLib.getInstance().sendOperationResult(context, OperationType.TYPE_REGISTER,
                        tokenResult.getReturnCode(), tokenResult.getToken(), null, null);
            }
        });
    }

    @Override
    public void setIdAndKey(String app_id, String app_key) {
        id = app_id;
        key = app_key;
    }

    @Override
    public void unregister() {
        VUpsManager.getInstance().unRegisterToken(context, new UPSRegisterCallback() {
            @Override
            public void onResult(TokenResult tokenResult) {
                PushLib.getInstance().sendOperationResult(context, OperationType.TYPE_UNREGISTER,
                        tokenResult.getReturnCode(), null, null, null);
            }
        });
    }

    @Override
    public void stop() {
        VUpsManager.getInstance().turnOffPush(context, new UPSTurnCallback() {
            @Override
            public void onResult(CodeResult codeResult) {
                PushLib.getInstance().sendOperationResult(context, OperationType.TYPE_STOP,
                        codeResult.getReturnCode(), null, null, null);
            }
        });
    }

    @Override
    public void resume() {
        VUpsManager.getInstance().turnOnPush(context, new UPSTurnCallback() {
            @Override
            public void onResult(CodeResult codeResult) {
                PushLib.getInstance().sendOperationResult(context, OperationType.TYPE_RESUME,
                        codeResult.getReturnCode(), codeResult.toString(), null, null);
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
        PushClient.getInstance(context).bindAlias(alias, new IPushActionListener() {
            @Override
            public void onStateChanged(int i) {
                PushLib.getInstance().sendOperationResult(context, OperationType.TYPE_ALIAS_SET, i,
                        null, null, null);
            }
        });
    }

    @Override
    public void deleteAlias(@NonNull String alias) {
        PushClient.getInstance(context).unBindAlias(alias, new IPushActionListener() {
            @Override
            public void onStateChanged(int i) {
                PushLib.getInstance().sendOperationResult(context, OperationType.TYPE_ALIAS_DELETE, i,
                        null, null, null);
            }
        });
    }

    @Override
    public void deleteAllAlias() {

    }

    @Override
    public void getAlias() {
        PushLib.getInstance().sendOperationResult(context, OperationType.TYPE_ALIAS_GET, 0,
                PushClient.getInstance(context).getAlias(), null, null);
    }

    @Override
    public void getToken() {

    }

    @Override
    public void clearNotification() {
    }
}
