package net;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Copyright (C) 2021,2021/8/4, a Tencent company. All rights reserved.
 * <p>
 * User : v_xhangxie
 * <p>
 * Desc :
 */
public class HistoryEntity implements Parcelable {
    String type;
    String content;
    String time;

    public HistoryEntity(){

    }

    public HistoryEntity(String type,String content,String time){
        this.type = type;
        this.content = content;
        this.time = time;
    }

    protected HistoryEntity(Parcel in) {
        type = in.readString();
        content = in.readString();
        time = in.readString();
    }

    public static final Creator<HistoryEntity> CREATOR = new Creator<HistoryEntity>() {
        @Override
        public HistoryEntity createFromParcel(Parcel in) {
            return new HistoryEntity(in);
        }

        @Override
        public HistoryEntity[] newArray(int size) {
            return new HistoryEntity[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(content);
        dest.writeString(time);
    }
}
