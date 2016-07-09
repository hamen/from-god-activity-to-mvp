package com.ivanmorgillo.fromgodactivitytomvp.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(prefix="m")
public class Comment implements Parcelable {

@Getter
    @SerializedName("body")
    private String mBody;

    @Getter
    @SerializedName("comment_id ")
    private int mCommentId;

    @SerializedName("creation_date")
    long mCreationDate;

    @Getter
    @SerializedName("edited")
    private boolean mEdited;

    @Getter
    @SerializedName("owner")
    private Owner mOwner;

    @Getter
    @SerializedName("post_id")
    private int mPostId;

    @Getter
    @SerializedName("reply_to_user")
    private Owner mReplyToUser;

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
        dest.writeInt(this.mCommentId);
        dest.writeLong(this.mCreationDate);
        dest.writeByte(mEdited ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.mOwner, 0);
        dest.writeInt(this.mPostId);
        dest.writeParcelable(this.mReplyToUser, 0);
        dest.writeInt(this.mScore);
    }

    public Comment() {
    }

    private Comment(Parcel in) {
        this.mBody = in.readString();
        this.mCommentId = in.readInt();
        this.mCreationDate = in.readLong();
        this.mEdited = in.readByte() != 0;
        this.mOwner = in.readParcelable(Owner.class.getClassLoader());
        this.mPostId = in.readInt();
        this.mReplyToUser = in.readParcelable(Owner.class.getClassLoader());
        this.mScore = in.readInt();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
