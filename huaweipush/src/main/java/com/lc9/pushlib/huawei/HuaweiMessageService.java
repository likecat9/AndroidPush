package com.lc9.pushlib.huawei;

import com.huawei.hms.push.HmsMessageService;
import com.lc9.core.PushLib;
import com.lc9.core.annotation.OperationType;

/**
 * @author: xk
 * @date: 2020/5/18
 * @description:
 */
public class HuaweiMessageService extends HmsMessageService {
    @Override
    public void onNewToken(String s) {
        PushLib.getInstance().sendOperationResult(this, OperationType.TYPE_REGISTER,
                0, s, null, null);
    }

    @Override
    public void onTokenError(Exception e) {
        PushLib.getInstance().sendOperationResult(this, OperationType.TYPE_REGISTER,
                -1, null, null, e.getMessage());
    }
}
