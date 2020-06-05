package com.lc9.core.annotation;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * author: xk
 * date: 2020/5/14
 * description: 广播action
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({PushActions.PUSH_ARRIVED, PushActions.PUSH_CLICKED, PushActions.PUSH_CUSTOMMESSAGE,
        PushActions.PUSH_OPERATION_RESULT})
public @interface PushActions {
    String PUSH_OPERATION_RESULT = "com.pushlib.push_operation_result";
    String PUSH_ARRIVED = "com.pushlib.push_arrived";
    String PUSH_CLICKED = "com.pushlib.push_clicked";
    String PUSH_CUSTOMMESSAGE = "coom.pushlib.push_customMessage";
}
