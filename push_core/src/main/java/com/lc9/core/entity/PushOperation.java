package com.lc9.core.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.lc9.core.annotation.OperationType;

/**
 * @author: xk
 * @date: 2020/5/14
 * @description:
 */
public class PushOperation implements Parcelable {
    public @OperationType int type;
    public int code;
    public String content;
    public String extraMsg;
    public String error;


    public PushOperation(int type, int code, String content, String extraMsg, String error) {
        this.type = type;
        this.code = code;
        this.content = content;
        this.extraMsg = extraMsg;
        this.error = error;
    }

    public PushOperation() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        dest.writeInt(this.code);
        dest.writeString(this.content);
        dest.writeString(this.extraMsg);
        dest.writeString(this.error);
    }

    protected PushOperation(Parcel in) {
        this.type = in.readInt();
        this.code = in.readInt();
        this.content = in.readString();
        this.extraMsg = in.readString();
        this.error = in.readString();
    }

    public static final Creator<PushOperation> CREATOR = new Creator<PushOperation>() {
        @Override
        public PushOperation createFromParcel(Parcel source) {
            return new PushOperation(source);
        }

        @Override
        public PushOperation[] newArray(int size) {
            return new PushOperation[size];
        }
    };

    @Override
    public String toString() {
        String typeStr;
        switch (type) {
            case OperationType.TYPE_ALIAS_DELETE:
                typeStr = "TYPE_ALIAS_DELETE";
                break;
            case OperationType.TYPE_ALIAS_GET:
                typeStr = "TYPE_ALIAS_GET";
                break;
            case OperationType.TYPE_ALIAS_SET:
                typeStr = "TYPE_ALIAS_SET";
                break;
            case OperationType.TYPE_TAG_ADD:
                typeStr = "TYPE_TAG_ADD";
                break;
            case OperationType.TYPE_TAG_DELETE:
                typeStr = "TYPE_TAG_DELETE";
                break;
            case OperationType.TYPE_TAG_GET:
                typeStr = "TYPE_TAG_GET";
                break;
            case OperationType.TYPE_TAG_SET:
                typeStr = "TYPE_TAG_SET";
                break;
            case OperationType.TYPE_REGISTER:
                typeStr = "TYPE_REGISTER";
                break;
            case OperationType.TYPE_UNREGISTER:
                typeStr = "TYPE_UNREGISTER";
                break;
            case OperationType.TYPE_STOP:
                typeStr = "TYPE_STOP";
                break;
            case OperationType.TYPE_RESUME:
                typeStr = "TYPE_RESUME";
                break;
            default:
                typeStr = "unknown";
        }

        return "PushOperation{" +
                "type=" + typeStr +
                ", code=" + code +
                ", content='" + content + '\'' +
                ", extraMsg='" + extraMsg + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
