package com.lc9.pushlib.meizu;

import android.content.Context;
import android.util.Log;

import com.lc9.core.PushLib;
import com.lc9.core.annotation.OperationType;
import com.meizu.cloud.pushsdk.MzPushMessageReceiver;
import com.meizu.cloud.pushsdk.handler.MzPushMessage;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;

/**
 * @author: xk
 * @date: 2020/5/26
 * @description:
 */
public class MeizuMessageReceiver extends MzPushMessageReceiver {
    //调用订阅方法后，会在此方法回调结果
    @Override
    public void onRegisterStatus(Context context, RegisterStatus registerStatus) {
        MeizuClient.PUSH_ID = registerStatus.getPushId();
        PushLib.getInstance().sendOperationResult(context, OperationType.TYPE_REGISTER,
                Integer.parseInt(registerStatus.code), registerStatus.message, null, null);
    }

    //调用取消订阅方法后，会在此方法回调结果
    @Override
    public void onUnRegisterStatus(Context context, UnRegisterStatus unRegisterStatus) {
        PushLib.getInstance().sendOperationResult(context, OperationType.TYPE_UNREGISTER,
                Integer.parseInt(unRegisterStatus.code), unRegisterStatus.message, null, null);
    }

    /* 调用开关转换或检查开关状态方法后，会在此方法回调开关状态
     * 通知栏开关转换方法：PushManager.switchPush(context, appId, appKey, pushId, pushType, switcher)
     * 检查开关状态方法：PushManager.checkPush(context, appId, appKey, pushId)*/
    @Override
    public void onPushStatus(Context context, PushSwitchStatus pushSwitchStatus) {

    }

    /* 调用标签订阅、取消标签订阅、取消所有标签订阅和获取标签列表方法后，会在此方法回调标签相关信息
     * 标签订阅方法：PushManager.subScribeTags(context, appId, appKey, pushId, tags)
     * 取消标签订阅方法：PushManager.unSubScribeTags(context, appId, appKey, pushId,tags)
     * 取消所有标签订阅方法：PushManager.unSubScribeAllTags(context, appId, appKey, pushId)
     * 获取标签列表方法：PushManager.checkSubScribeTags(context, appId, appKey, pushId)*/
    @Override
    public void onSubTagsStatus(Context context, SubTagsStatus subTagsStatus) {
        Log.d("TAG", "onSubTagsStatus: ");
    }

    /* 调用别名订阅、取消别名订阅和获取别名方法后，会在此方法回调别名相关信息
     * 别名订阅方法：PushManager.subScribeAlias(context, appId, appKey, pushId, alias)
     * 取消别名订阅方法：PushManager.unSubScribeAlias(context, appId, appKey, pushId, alias)
     * 获取别名方法：PushManager.checkSubScribeAlias(context, appId, appKey, pushId)*/
    @Override
    public void onSubAliasStatus(Context context, SubAliasStatus subAliasStatus) {
        Log.d("TAG", "onSubTagsStatus: ");
    }

    //当用户点击通知栏消息后会在此方法回调
    @Override
    public void onNotificationClicked(Context context, MzPushMessage mzPushMessage) {
        PushLib.getInstance().sendMessageClicked(context, String.valueOf(mzPushMessage.getNotifyId()),
                mzPushMessage.getTitle(), mzPushMessage.getContent(), mzPushMessage.getSelfDefineContentString());
    }

    @Override
    public void onNotificationArrived(Context context, MzPushMessage mzPushMessage) {
        PushLib.getInstance().sendMessageArrived(context, String.valueOf(mzPushMessage.getNotifyId()),
                mzPushMessage.getTitle(), mzPushMessage.getContent(), mzPushMessage.getSelfDefineContentString());
    }
}
