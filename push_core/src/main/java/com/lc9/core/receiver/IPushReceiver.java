package com.lc9.core.receiver;

import android.content.Context;

import com.lc9.core.entity.PushMessage;
import com.lc9.core.entity.PushOperation;

/**
 * @author: xk
 * @date: 2020/5/14
 * @description:
 */
public interface IPushReceiver {
    void onOperationResult(Context context,PushOperation result);

    void onPushArrived(Context context,PushMessage message);

    void onPushClicked(Context context,PushMessage message);
}
