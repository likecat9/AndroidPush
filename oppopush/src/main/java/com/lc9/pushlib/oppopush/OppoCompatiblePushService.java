package com.lc9.pushlib.oppopush;

import android.content.Context;

import com.heytap.msp.push.mode.DataMessage;
import com.heytap.msp.push.service.CompatibleDataMessageCallbackService;
import com.lc9.core.PushLib;

/**
 * @author: xk
 * @date: 2020/5/27
 * @description:
 */
public class OppoCompatiblePushService extends CompatibleDataMessageCallbackService {
    @Override
    public void processMessage(Context context, DataMessage dataMessage) {
        PushLib.getInstance().sendMessageArrived(context,dataMessage.getMessageID(),
                dataMessage.getTitle(), dataMessage.getContent(), dataMessage.getDescription());
    }
}
