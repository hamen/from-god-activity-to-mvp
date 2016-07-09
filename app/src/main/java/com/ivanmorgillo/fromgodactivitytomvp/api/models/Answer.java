package com.ivanmorgillo.fromgodactivitytomvp.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(prefix="m")
public class Answer implements Parcelable {

@Getter
    @SerializedName("body")
    private String mBody;

    @Getter
    @SerializedName("answer_id ")
    private int mAnswerId;

    @SerializedName("community_owned_date")
    long mCommunityOwnedDate;

    @SerializedName("creation_date")
    long mCreationDate;

    @Getter
    @SerializedName("is_accepted")
    private boolean mAccepted;

    @SerializedName("last_activity_date")
    long mLastActivityDate;

    @SerializedName("last_edit_date")
    long mLastEditDate;

    @SerializedName("locked_date")
    long mLockedDate;

    @Getter
    @SerializedName("owner")
    private Owner mOwner;

    @Getter
    @SerializedName("question_id")
    private int mQuestionId;

    @Getter
    @SerializedName("score")
    private int mScore;

    public DateTime getCreationDate() {
        return new DateTime(mCreationDate*1000);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mBody);
        dest.writeInt(this.mAnswerId);
        dest.writeLong(this.mCommunityOwnedDate);
        dest.writeLong(this.mCreationDate);
        dest.writeByte(mAccepted ? (byte) 1 : (byte) 0);
        dest.writeLong(this.mLastActivityDate);
        dest.writeLong(this.mLastEditDate);
        dest.writeLong(this.mLockedDate);
        dest.writeParcelable(this.mOwner, 0);
        dest.writeInt(this.mQuestionId);
        dest.writeInt(this.mScore);
    }

    public Answer() {
    }

    private Answer(Parcel in) {
        this.mBody = in.readString();
        this.mAnswerId = in.readInt();
        this.mCommunityOwnedDate = in.readLong();
        this.mCreationDate = in.readLong();
        this.mAccepted = in.readByte() != 0;
        this.mLastActivityDate = in.readLong();
        this.mLastEditDate = in.readLong();
        this.mLockedDate = in.readLong();
        this.mOwner = in.readParcelable(Owner.class.getClassLoader());
        this.mQuestionId = in.readInt();
        this.mScore = in.readInt();
    }

    public static final Creator<Answer> CREATOR = new Creator<Answer>() {
        public Answer createFromParcel(Parcel source) {
            return new Answer(source);
        }

        public Answer[] newArray(int size) {
            return new Answer[size];
        }
    };
}
