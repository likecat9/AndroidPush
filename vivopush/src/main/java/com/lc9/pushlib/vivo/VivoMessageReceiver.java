package com.lc9.pushlib.vivo;

import android.content.Context;

import com.lc9.core.PushLib;
import com.lc9.core.annotation.OperationType;
import com.vivo.push.model.UPSNotificationMessage;
import com.vivo.push.sdk.OpenClientPushMessageReceiver;

/**
 * @author: xk
 * @date: 2020/5/19
 * @description:
 */
public class VivoMessageReceiver extends OpenClientPushMessageReceiver {
    @Override
    public void onNotificationMessageClicked(Context context, UPSNotificationMessage upsNotificationMessage) {
        String push_key = upsNotificationMessage.getParams().get("Push_Key");
        PushLib.getInstance().sendMessageClicked(context, String.valueOf(upsNotificationMessage.getMsgId()),
                upsNotificationMessage.getTitle(), upsNotificationMessage.getContent(), push_key);
    }

    //当首次turnOnPush成功或regId发生改变时，回调此方法
    @Override
    public void onReceiveRegId(Context context, String s) {
        PushLib.getInstance().sendOperationResult(context, OperationType.TYPE_REGISTER,
                s == null ? -1 : 0, s, null, null);
    }
}
