package com.lc9.core.annotation;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author: xk
 * @date: 2020/6/5
 * @description:
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({PushClientType.JIGUANG,PushClientType.HUAWEI,PushClientType.XIAOMI,
        PushClientType.MEIZU,PushClientType.VIVO,PushClientType.OPPO,})
public @interface PushClientType {
    String JIGUANG="jiguang";
    String HUAWEI="huawei";
    String XIAOMI="xiaomi";
    String MEIZU="meizu";
    String VIVO="vivo";
    String OPPO="oppo";
}
