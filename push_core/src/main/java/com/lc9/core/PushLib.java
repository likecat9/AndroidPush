package com.lc9.core;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.lc9.core.annotation.MessageType;
import com.lc9.core.annotation.OperationType;
import com.lc9.core.annotation.PushActions;
import com.lc9.core.annotation.PushClientType;
import com.lc9.core.entity.PushMessage;
import com.lc9.core.entity.PushOperation;
import com.lc9.core.receiver.IPushClient;
import com.lc9.core.receiver.PushBroadcastReceiver;
import com.lc9.core.receiver.PushSender;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * author: xk
 * date: 2020/5/14
 * description:
 */
public class PushLib {
    private String TAG = "PushLib";
    private final String errorTimes = "errorTimes";
    private final String pushInitSP = "pushInitSP";
    private static PushLib instance;
    private Application application;
    private String PUSHLIB_PREFIX = "pushlib_";
    private String PUSHKEY_PREFIX = "pushkey_";
    private String PUSHID_PREFIX = "pushid_";
    private IPushClient IPushClient;
    private ComponentName componentName;
    private boolean isAvailable = false;

    public @PushClientType
    String getClientName() {
        if (IPushClient != null) {
            if (IPushClient.getClass().getSimpleName().equals("JIPushClient")) {
                return PushClientType.JIGUANG;
            } else if (IPushClient.getClass().getSimpleName().equals("HuaweiClient")) {
                return PushClientType.HUAWEI;
            } else if (IPushClient.getClass().getSimpleName().equals("OppoPushClient")) {
                return PushClientType.OPPO;
            } else if (IPushClient.getClass().getSimpleName().equals("XiaomiClient")) {
                return PushClientType.XIAOMI;
            } else if (IPushClient.getClass().getSimpleName().equals("VivoClient")) {
                return PushClientType.VIVO;
            } else if (IPushClient.getClass().getSimpleName().equals("MeizuClient")) {
                return PushClientType.MEIZU;
            }
        }
        return null;
    }

    public void printLog(String content) {
        Log.d(TAG, content);
    }

    public static PushLib getInstance() {
        if (instance == null) {
            instance = new PushLib();
        }
        return instance;
    }

    public void reInit() {
        if (application == null) {
            printLog("reInit: 请先调用init()!!!");
            return;
        }
        init(application);
    }

    public void init(Application application) {
        this.application = application;
        printLog("init: start");
        if (!checkErrorTimes(application.getSharedPreferences(pushInitSP, Context.MODE_PRIVATE))) {
            HashMap<String, String> clientMap = readClient();
            HashMap<String, String> idAndKey = readIdAndKey();
            String key, id;
            String brand = Build.BRAND;
            printLog("机型为： " + brand);
            for (Map.Entry<String, String> entry : clientMap.entrySet()) {
                String platformName = entry.getKey();
                String className = entry.getValue();
                platformName = platformName.split("_")[1];
                //判断机型
                if ((brand.equalsIgnoreCase("huawei") || brand.equalsIgnoreCase("honor")) &&
                        platformName.equalsIgnoreCase("huawei")) {
                    IPushClient = createClient(className);
                } else if (brand.equalsIgnoreCase("oppo") &&
                        platformName.equalsIgnoreCase("oppo")) {
                    IPushClient = createClient(className);
                    if (IPushClient != null) {
                        IPushClient.setIdAndKey(idAndKey.get(PUSHID_PREFIX + "oppo"), idAndKey.get(PUSHKEY_PREFIX + "oppo"));
                    }
                } else if (brand.equalsIgnoreCase("xiaomi") &&
                        platformName.equalsIgnoreCase("xiaomi")) {
                    IPushClient = createClient(className);
                    if (IPushClient != null) {
                        IPushClient.setIdAndKey(idAndKey.get(PUSHID_PREFIX + "xiaomi"), idAndKey.get(PUSHKEY_PREFIX + "xiaomi"));
                    }
                } else if (brand.equalsIgnoreCase("vivo") &&
                        platformName.equalsIgnoreCase("vivo")) {
                    IPushClient = createClient(className);
                    if (IPushClient != null) {
                        IPushClient.setIdAndKey(idAndKey.get(PUSHID_PREFIX + "vivo"), idAndKey.get(PUSHKEY_PREFIX + "vivo"));
                    }
                } else if (brand.equalsIgnoreCase("meizu") &&
                        platformName.equalsIgnoreCase("meizu")) {
                    IPushClient = createClient(className);
                }
            }
            if (IPushClient == null) {//其他机型默认使用极光
                IPushClient = createClient(clientMap.get(PUSHLIB_PREFIX + "jiguang"));
            }
        }
        printLog("init: PushClient为" + IPushClient.getClass().getName());
        IPushClient.init(application);
        printLog("init: finish");
    }

    private boolean checkErrorTimes(SharedPreferences pushInitSP) {
        printLog("checkErrorTimes: start");
        int errorTimes = pushInitSP.getInt(this.errorTimes, -1);
        if (errorTimes == -1) {//第一次初始化
            printLog("checkErrorTimes: first init");
            pushInitSP.edit().putInt(this.errorTimes, 0).apply();
            return false;
        } else if (errorTimes >= 3) {//使用极光注册
            printLog("checkErrorTimes: 使用极光注册");
            String className = null;
            try {
                Bundle metaData = application.getPackageManager().getApplicationInfo(application.getPackageName(), PackageManager.GET_META_DATA).metaData;
                if (metaData != null) {
                    Set<String> allKeys = metaData.keySet();
                    if (allKeys != null && !allKeys.isEmpty()) {
                        for (String key : allKeys) {
                            if (key.equals("pushlib_jiguang")) {
                                className = metaData.getString(key);
                            }
                        }
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            if (className == null) {
                throw new UnsupportedOperationException("请至少配置极光推送！");
            }
            IPushClient = createClient(className);
            return true;
        }
        //继续尝试厂商推送
        return false;
    }

    private IPushClient createClient(String className) {
        try {
            Class<?> currentClz = Class.forName(className);
            Class<?>[] interfaces = currentClz.getInterfaces();
            List<Class<?>> allInterfaces = Arrays.asList(interfaces);
            if (allInterfaces.contains(IPushClient.class)) {
                return (IPushClient) currentClz.newInstance();
            } else {
                throw new IllegalArgumentException(className + "is not implements " + IPushClient.class.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //设置是否为激活状态 防止重复多次注册
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void register() {
//        if (!isAvailable) {
            IPushClient.register();
//        }
    }

    public void unregister() {
        IPushClient.unregister();
    }

    public void resume() {
        if (!isAvailable) {
            IPushClient.resume();
            PushLib.getInstance().sendOperationResult(application, OperationType.TYPE_RESUME, 0,
                    null, null, null);
        }
    }

    public void stop() {
        IPushClient.stop();
        PushLib.getInstance().sendOperationResult(application, OperationType.TYPE_STOP, 0,
                null, null, null);
    }

    public void addTags(String... tags) {
        IPushClient.addTags(tags);
    }

    public void deleteTags(String... tags) {
        IPushClient.deleteTags(tags);
    }

    public void getTags() {
        IPushClient.getTags();
    }

    public void setAlias(String alias) {
        IPushClient.setAlias(alias);
    }

    public void deleteAlias(String alias) {
        IPushClient.deleteAlias(alias);
    }

    public void getAlias() {
        IPushClient.getAlias();
    }

    public void getToken() {
        IPushClient.getToken();
    }

    public void clearNotification() {
        IPushClient.clearNotification();
    }

    //设置自定义的消息接收
    public void setReceiver(Class<? extends PushBroadcastReceiver> receiver) {
        componentName = new ComponentName(application, receiver);
    }

    //发送推送操作结果 详见 @OperationType
    public void sendOperationResult(Context context,
                                    @OperationType int type,
                                    int resultCode,
                                    String content,
                                    String extraMsg,
                                    String error) {
        PushSender.sendData(context, PushActions.PUSH_OPERATION_RESULT, componentName,
                new PushOperation(type, resultCode, content, extraMsg, error));
    }

    public void sendMessageArrived(Context context, String id, String title, String content, String customMsg) {
        PushSender.sendData(context, PushActions.PUSH_ARRIVED, componentName, new PushMessage(id, MessageType.NORMAL, title, content, customMsg));
    }

    public void sendMessageClicked(Context context, String id, String title, String content, String customMsg) {
        PushSender.sendData(context, PushActions.PUSH_CLICKED, componentName, new PushMessage(id, MessageType.NORMAL, title, content, customMsg));
    }

    public void sendThroughPassMessage(Context context, String id, String json) {
        PushSender.sendData(context, PushActions.PUSH_ARRIVED, componentName, new PushMessage(id, MessageType.THROUGH_PASS, json));
    }

    public static String MapToJson(Map<String, String> map) {
        return new Gson().toJson(map);
    }

    public void addErrorTime() {
        SharedPreferences sp = application.getSharedPreferences(pushInitSP, Context.MODE_PRIVATE);
        int times = sp.getInt(errorTimes, -1);
        printLog("addErrorTime: times=" + times);
        if (times != -1) {
            sp.edit().putInt(errorTimes, ++times).apply();
        }
    }

    public void clearErrorTime() {
        SharedPreferences sp = application.getSharedPreferences(pushInitSP, Context.MODE_PRIVATE);
        int times = sp.getInt(errorTimes, -1);
        printLog("clearErrorTime: times=" + times);
        if (times != -1) {
            sp.edit().putInt(errorTimes, 0).apply();
        }
    }
    //华为推送需要activity进行初始化
    public void setActivityForHuaWei(Activity activityForHuaWei) {
        IPushClient.init(activityForHuaWei);
    }

    public void enableNotificationChannel() {
        if (application == null) {
            printLog("请先调用 init！！！");
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = application.getString(R.string.ulife_channel_id);
            printLog("channelId :  "+channelId);
            NotificationManager manager = (NotificationManager) application.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = manager.getNotificationChannel(channelId);
            if (channel == null) {
                channel = new NotificationChannel(channelId, application.getString(R.string.ulife_channel_name), NotificationManager.IMPORTANCE_HIGH);
                channel.enableLights(true);
                channel.setLightColor(Color.GREEN);
                channel.enableVibration(true);
                channel.setDescription("U享通知通道");
                channel.setShowBadge(true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    channel.setAllowBubbles(true);
                }
                manager.createNotificationChannel(channel);
            }
        }
    }

    private HashMap<String, String> readClient() {
        HashMap<String, String> map = new HashMap<>();
        try {
            Bundle metaData = application.getPackageManager().getApplicationInfo(application.getPackageName(), PackageManager.GET_META_DATA).metaData;
            if (metaData != null) {
                printLog("readClient: 开始读取meta data");
                Set<String> allKeys = metaData.keySet();
                if (allKeys != null && !allKeys.isEmpty()) {
                    for (String key : allKeys) {
                        if (key.startsWith(PUSHLIB_PREFIX)) {
                            String value = metaData.getString(key);
                            printLog("key : " + key + "\t value : " + value);
                            map.put(key, value);
                        }
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (map.isEmpty()) {
            throw new UnsupportedOperationException("请配置第三方Client！");
        }
        return map;
    }

    private HashMap<String, String> readIdAndKey() {
        HashMap<String, String> map = new HashMap<>();
        try {
            Bundle metaData = application.getPackageManager().getApplicationInfo(application.getPackageName(), PackageManager.GET_META_DATA).metaData;
            if (metaData != null) {
                printLog("readIdAndKey: 开始读取meta data");
                Set<String> allKeys = metaData.keySet();
                if (allKeys != null && !allKeys.isEmpty()) {
                    for (String key : allKeys) {
                        if (key.startsWith(PUSHKEY_PREFIX) || key.startsWith(PUSHID_PREFIX)) {
                            String value = metaData.getString(key);
                            printLog("key : " + key + "\t value : " + value);
                            map.put(key, value);
                        }
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return map;
    }
}