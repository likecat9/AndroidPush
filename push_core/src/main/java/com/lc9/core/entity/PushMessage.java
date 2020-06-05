package com.lc9.core.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.lc9.core.annotation.MessageType;

/**
 * @author: xk
 * @date: 2020/5/14
 * @description: 统一消息封装类
 */
public class PushMessage implements Parcelable {
    public String id;
    public @MessageType
    int type;
    public String json;
    public String title;
    public String content;
    public String customMsg;

    public PushMessage(String id, int type, String title, String content, String customMsg) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.content = content;
        this.customMsg = customMsg;
    }

    public PushMessage(String id, int type, String json) {
        this.id = id;
        this.type = type;
        this.json = json;
    }

    @Override
    public String toString() {
        return "PushMessage{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", json='" + json + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", customMsg='" + customMsg + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeInt(this.type);
        dest.writeString(this.json);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.customMsg);
    }

    public PushMessage() {
    }

    protected PushMessage(Parcel in) {
        this.id = in.readString();
        this.type = in.readInt();
        this.json = in.readString();
        this.title = in.readString();
        this.content = in.readString();
        this.customMsg = in.readString();
    }

    public static final Creator<PushMessage> CREATOR = new Creator<PushMessage>() {
        @Override
        public PushMessage createFromParcel(Parcel source) {
            return new PushMessage(source);
        }

        @Override
        public PushMessage[] newArray(int size) {
            return new PushMessage[size];
        }
    };
}
