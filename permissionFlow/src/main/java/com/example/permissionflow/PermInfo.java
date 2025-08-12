package com.example.permissionflow;

import android.os.Parcel;
import android.os.Parcelable;

public class PermInfo implements Parcelable {
    public String permName;
    public String uiLabel;
    public String fixIntentUri;

    public PermInfo() {}

    public PermInfo(String permName, String uiLabel, String fixIntentUri) {
        this.permName = permName;
        this.uiLabel = uiLabel;
        this.fixIntentUri = fixIntentUri;
    }

    protected PermInfo(Parcel in) {
        permName = in.readString();
        uiLabel = in.readString();
        fixIntentUri = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(permName);
        dest.writeString(uiLabel);
        dest.writeString(fixIntentUri);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PermInfo> CREATOR = new Creator<PermInfo>() {
        @Override
        public PermInfo createFromParcel(Parcel in) {
            return new PermInfo(in);
        }

        @Override
        public PermInfo[] newArray(int size) {
            return new PermInfo[size];
        }
    };
}
