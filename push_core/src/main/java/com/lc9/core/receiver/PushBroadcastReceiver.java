package com.lc9.core.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Parcelable;

import com.lc9.core.PushLib;
import com.lc9.core.annotation.OperationType;
import com.lc9.core.annotation.PushActions;
import com.lc9.core.entity.PushMessage;
import com.lc9.core.entity.PushOperation;

/**
 * @author: xk
 * @date: 2020/5/14
 * @description: 转接第三方推送
 */
public abstract class PushBroadcastReceiver extends BroadcastReceiver implements IPushReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) return;

        String action = intent.getAction();
        Parcelable data = PushSender.getParcelableData(intent);
        PushLib.getInstance().printLog("PushBroadcastReceiver  Received " + " action: " + action);
        PushLib.getInstance().printLog(data.toString());

        switch (action) {
            case PushActions
                    .PUSH_ARRIVED:
                onPushArrived(context, (PushMessage) data);
                break;
            case PushActions
                    .PUSH_CLICKED:
                onPushClicked(context, (PushMessage) data);
                break;
            case PushActions
                    .PUSH_CUSTOMMESSAGE:
                break;
            case PushActions.PUSH_OPERATION_RESULT:
                PushOperation operation = (PushOperation) data;
                onOperationResult(context, operation);
                //如果注册失败
                if (operation.type == OperationType.TYPE_REGISTER && (operation).code != 0) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            PushLib.getInstance().addErrorTime();
                            PushLib.getInstance().setAvailable(false);
                            PushLib.getInstance().reInit();
                            PushLib.getInstance().register();
                        }
                    },3000);
                } else if (operation.type == OperationType.TYPE_REGISTER) {
                    PushLib.getInstance().setAvailable(true);
                    PushLib.getInstance().clearErrorTime();
                }
                //注销操作
                if (operation.type == OperationType.TYPE_UNREGISTER) {
                    PushLib.getInstance().setAvailable(false);
                }
                break;
        }
    }
}
