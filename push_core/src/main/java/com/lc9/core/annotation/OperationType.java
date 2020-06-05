package com.lc9.core.annotation;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * author: xk
 * date: 2020/5/14
 * description: 定义了操作类型
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({OperationType.TYPE_REGISTER, OperationType.TYPE_UNREGISTER,
        OperationType.TYPE_TAG_ADD, OperationType.TYPE_TAG_DELETE, OperationType.TYPE_TAG_GET,
        OperationType.TYPE_TAG_SET, OperationType.TYPE_ALIAS_SET, OperationType.TYPE_ALIAS_DELETE,
        OperationType.TYPE_ALIAS_GET, OperationType.TYPE_STOP, OperationType.TYPE_RESUME})
public @interface OperationType {
    int TYPE_REGISTER = 251;
    int TYPE_UNREGISTER = 252;
    int TYPE_TAG_SET = 253;
    int TYPE_TAG_ADD = 254;
    int TYPE_TAG_DELETE = 255;
    int TYPE_TAG_GET = 256;
    int TYPE_ALIAS_SET = 257;
    int TYPE_ALIAS_DELETE = 258;
    int TYPE_ALIAS_GET = 259;
    int TYPE_STOP = 230;
    int TYPE_RESUME = 231;
}