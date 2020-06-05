package com.lc9.core.annotation;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * author: xk
 * date: 2020/5/15
 * description:
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({MessageType.NORMAL, MessageType.THROUGH_PASS})
public @interface MessageType {
    int NORMAL = 20;
    int THROUGH_PASS = 21;
}
