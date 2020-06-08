package com.lc9.pushlib.oppopush;

import android.content.Context;

import com.heytap.msp.push.mode.DataMessage;
import com.heytap.msp.push.service.DataMessageCallbackService;
import com.lc9.core.PushLib;

/**
 * @author: xk
 * @date: 2020/5/15
 * @description: 目前只支持通知栏消息，透传消息暂不支持。
 */
public class OppoDataMessagePushService extends DataMessageCallbackService {
    @Override
    public void processMessage(Context context, DataMessage dataMessage) {
        PushLib.getInstance().sendMessageArrived(context,dataMessage.getMessageID(),
                dataMessage.getTitle(), dataMessage.getContent(), dataMessage.getDescription());
    }
}
