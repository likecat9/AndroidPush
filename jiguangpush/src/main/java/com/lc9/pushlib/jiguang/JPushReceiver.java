package com.lc9.pushlib.jiguang;

import android.content.Context;

import com.lc9.core.PushLib;
import com.lc9.core.annotation.OperationType;

import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;

/**
 * @author: xk
 * @date: 2020/5/15
 * @description:
 */
public class JPushReceiver extends JPushMessageReceiver {
    @Override
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        switch (jPushMessage.getSequence()) {
            case JIPushClient.SET_TAG_SEQUENCE:
                PushLib.getInstance().sendOperationResult(context, OperationType.TYPE_TAG_SET,
                        jPushMessage.getSequence(), jPushMessage.toString(), null,
                        String.valueOf(jPushMessage.getErrorCode()));
                break;
            case JIPushClient.ADD_TAG_SEQUENCE:
                PushLib.getInstance().sendOperationResult(context, OperationType.TYPE_TAG_ADD,
                        jPushMessage.getSequence(), jPushMessage.toString(), null,
                        String.valueOf(jPushMessage.getErrorCode()));
                break;
            case JIPushClient.DELETE_TAG_SEQUENCE:
                PushLib.getInstance().sendOperationResult(context, OperationType.TYPE_TAG_DELETE,
                        jPushMessage.getSequence(), jPushMessage.toString(), null,
                        String.valueOf(jPushMessage.getErrorCode()));
                break;
            case JIPushClient.GET_ALL_TAG_SEQUENCE:
                PushLib.getInstance().sendOperationResult(context, OperationType.TYPE_TAG_GET,
                        jPushMessage.getSequence(), jPushMessage.toString(), null,
                        String.valueOf(jPushMessage.getErrorCode()));
                break;
        }
    }

    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage notificationMessage) {
        PushLib.getInstance().sendMessageArrived(context, String.valueOf(notificationMessage.notificationId),
                notificationMessage.notificationTitle, notificationMessage.notificationContent,
                notificationMessage.notificationExtras);
    }

    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage notificationMessage) {
        PushLib.getInstance().sendMessageClicked(context, String.valueOf(notificationMessage.notificationId),
                notificationMessage.notificationTitle, notificationMessage.notificationContent,
                notificationMessage.notificationExtras);
    }

    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        switch (jPushMessage.getSequence()) {
            case JIPushClient.SET_ALIAS_SEQUENCE:
                PushLib.getInstance().sendOperationResult(context, OperationType.TYPE_ALIAS_SET,
                        jPushMessage.getSequence(), jPushMessage.toString(), null,
                        String.valueOf(jPushMessage.getErrorCode()));
                break;
            case JIPushClient.DELETE_ALIAS_SEQUENCE:
                PushLib.getInstance().sendOperationResult(context, OperationType.TYPE_ALIAS_DELETE,
                        jPushMessage.getSequence(), jPushMessage.toString(), null,
                        String.valueOf(jPushMessage.getErrorCode()));
                break;
            case JIPushClient.GET_ALL_ALIAS_SEQUENCE:
                PushLib.getInstance().sendOperationResult(context, OperationType.TYPE_ALIAS_GET,
                        jPushMessage.getSequence(), jPushMessage.toString(), null,
                        String.valueOf(jPushMessage.getErrorCode()));
                break;
        }
    }
}
