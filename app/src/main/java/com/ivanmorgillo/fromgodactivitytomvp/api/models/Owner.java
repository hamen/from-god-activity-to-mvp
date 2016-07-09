package com.ivanmorgillo.fromgodactivitytomvp.api.models;

import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(prefix = "m")
public class Owner implements Parcelable {

    @Getter
    @SerializedName("reputation")
    private int mReputation;

    @Getter
    @SerializedName("user_id")
    private long mUserId;

    @Getter
    @SerializedName("user_type")
    private String mUserType;

    @Getter
    @SerializedName("accept_rate")
    private int mAcceptRate;

    @Getter
    @SerializedName("profile_image")
    private String mProfileImage;

    @Getter
    @SerializedName("display_name")
    private String mDisplayName;

    @Getter
    @SerializedName("link")
    private String mLink;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mReputation);
        dest.writeLong(this.mUserId);
        dest.writeString(this.mUserType);
        dest.writeInt(this.mAcceptRate);
        dest.writeString(this.mProfileImage);
        dest.writeString(this.mDisplayName);
        dest.writeString(this.mLink);
    }

    public Owner() {
    }

    private Owner(Parcel in) {
        this.mReputation = in.readInt();
        this.mUserId = in.readLong();
        this.mUserType = in.readString();
        this.mAcceptRate = in.readInt();
        this.mProfileImage = in.readString();
        this.mDisplayName = in.readString();
        this.mLink = in.readString();
    }

    public static final Creator<Owner> CREATOR = new Creator<Owner>() {
        public Owner createFromParcel(Parcel source) {
            return new Owner(source);
        }

        public Owner[] newArray(int size) {
            return new Owner[size];
        }
    };
}
