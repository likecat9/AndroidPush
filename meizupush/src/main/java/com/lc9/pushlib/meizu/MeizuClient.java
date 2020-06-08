package com.lc9.pushlib.meizu;

import android.content.Context;

import androidx.annotation.NonNull;

import com.lc9.core.receiver.IPushClient;
import com.meizu.cloud.pushsdk.PushManager;

/**
 * @author: xk
 * @date: 2020/5/26
 * @description:
 */
public class MeizuClient implements IPushClient {
    private Context context;
    private String APP_ID;
    private String APP_KEY;
    public static String PUSH_ID;


    @Override
    public void init(Context context) {
        this.context = context;
    }

    @Override
    public void register() {
        PushManager.register(context, APP_ID, APP_KEY);
    }

    @Override
    public void setIdAndKey(String app_id, String app_key) {
        APP_ID = app_id;
        APP_KEY = app_key;
    }

    @Override
    public void unregister() {
        PushManager.unRegister(context, APP_ID, APP_KEY);
    }

    @Override
    public void stop() {
    }

    @Override
    public void resume() {

    }

    //标签名称，多个逗号隔离，每个标签不能超过 20 个字符，限 100 个。
    @Override
    public void setTags(@NonNull String... tags) {
        PushManager.subScribeTags(context, APP_ID, APP_KEY, PUSH_ID, getTagString(tags));
    }


    @Override
    public void addTags(@NonNull String... tags) {

    }

    @Override
    public void deleteTags(@NonNull String... tags) {
        PushManager.unSubScribeTags(context,APP_ID,APP_KEY,PUSH_ID, getTagString(tags));
    }

    @Override
    public void getTags() {

    }

    //别名名称，长度不能超过 20 个字符，每一个应用用户仅能设置一个别名。
    @Override
    public void setAlias(@NonNull String alias) {
        PushManager.subScribeAlias(context, APP_ID, APP_KEY, PUSH_ID, alias);
    }

    @Override
    public void deleteAlias(@NonNull String alias) {
        PushManager.unSubScribeAlias(context, APP_ID, APP_KEY, PUSH_ID, alias);
    }

    @Override
    public void deleteAllAlias() {
    }

    @Override
    public void getAlias() {
        PushManager.checkSubScribeAlias(context, APP_ID, APP_KEY, PUSH_ID);
    }

    @Override
    public void getToken() { }

    @Override
    public void clearNotification() {
        PushManager.clearNotification(context);
    }

    private String getTagString(@NonNull String[] tags) {
        StringBuilder sb = new StringBuilder();
        for (String tag : tags) {
            sb.append(tag).append(",");
        }
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }
}
