package com.lc9.core.receiver;

import android.content.Context;

import androidx.annotation.NonNull;

/**
 * @author: xk
 * @date: 2020/5/14
 * @description: 第三方推送需要实现 用于注册、初始化、tag与别名的操作
 */
public interface IPushClient {
    void init(Context context);

    void register();

    void setIdAndKey(String app_id, String app_key);

    void unregister();

    void stop();

    void resume();

    void setTags(@NonNull String... tags);

    void addTags(@NonNull String... tags);

    void deleteTags(@NonNull String... tags);

    void getTags();

    void setAlias(@NonNull String alias);

    void deleteAlias(@NonNull String alias);

    void deleteAllAlias();

    void getAlias();

    void getToken();

    void clearNotification();
}
