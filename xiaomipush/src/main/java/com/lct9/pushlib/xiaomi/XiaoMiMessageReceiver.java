package com.lct9.pushlib.xiaomi;

import android.content.Context;

import com.lc9.core.PushLib;
import com.lc9.core.annotation.OperationType;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

/**
 * @author: xk
 * @date: 2020/5/18
 * @description:
 */
public class XiaoMiMessageReceiver extends PushMessageReceiver {
    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage miPushMessage) {
        PushLib.getInstance().printLog("XiaoMiMessageReceiver -- onReceivePassThroughMessage: " + miPushMessage);
        PushLib.getInstance().sendThroughPassMessage(context, miPushMessage.getMessageId()
                , miPushMessage.getContent());
    }

    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage miPushMessage) {
        PushLib.getInstance().printLog("XiaoMiMessageReceiver -- onNotificationMessageClicked: " + miPushMessage);
        PushLib.getInstance().sendMessageClicked(context, miPushMessage.getMessageId()
                , miPushMessage.getTitle(), miPushMessage.getContent(), PushLib.MapToJson(miPushMessage.getExtra()));
    }

    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage miPushMessage) {
        PushLib.getInstance().printLog("XiaoMiMessageReceiver -- onNotificationMessageArrived: " + miPushMessage);
        PushLib.getInstance().sendMessageArrived(context, miPushMessage.getMessageId(),
                miPushMessage.getTitle(), miPushMessage.getContent(), PushLib.MapToJson(miPushMessage.getExtra()));
    }

    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage miPushCommandMessage) {
        PushLib.getInstance().sendOperationResult(context, OperationType.TYPE_REGISTER,
                miPushCommandMessage.getResultCode() == 0 ? 0 : -1,
                miPushCommandMessage.getCommand(), null,
                miPushCommandMessage.getResultCode() != 0 ? String.valueOf(miPushCommandMessage.getResultCode()) : null);
    }

    @Override
    public void onCommandResult(Context context, MiPushCommandMessage miPushCommandMessage) {
        switch (miPushCommandMessage.getCommand()) {
            case MiPushClient.COMMAND_SET_ALIAS:
                PushLib.getInstance().sendOperationResult(context, OperationType.TYPE_ALIAS_SET,
                        miPushCommandMessage.getResultCode() == 0 ? 0 : -1,
                        miPushCommandMessage.getCommand(), null,
                        miPushCommandMessage.getResultCode() != 0 ? String.valueOf(miPushCommandMessage.getResultCode()) : null);
                break;
            case MiPushClient.COMMAND_UNSET_ALIAS:
                PushLib.getInstance().sendOperationResult(context, OperationType.TYPE_ALIAS_DELETE,
                        miPushCommandMessage.getResultCode() == 0 ? 0 : -1,
                        miPushCommandMessage.getCommand(), null,
                        miPushCommandMessage.getResultCode() != 0 ? String.valueOf(miPushCommandMessage.getResultCode()) : null);
                break;
        }
    }
}
