package com.ivanmorgillo.fromgodactivitytomvp.api.models;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

@Data
@Accessors(prefix="m")
public class Question implements Parcelable {

    @Getter
    @SerializedName("tags")
    ArrayList<String> mTags;

    @Getter
    @SerializedName("comments")
    ArrayList<Comment> mComments;

    @Getter
    @SerializedName("answers")
    ArrayList<Answer> mAnswers;

    @Getter
    @SerializedName("owner")
    Owner mOwner;

    @Getter
    @SerializedName("is_answered")
    boolean mAnswered;

    @Getter
    @SerializedName("view_count")
    int mViewCount;

    @Getter
    @SerializedName("answer_count")
    int mAnswerCount;

    @Getter
    @SerializedName("score")
    int mScore;

    @SerializedName("last_activity_date")
    long mLastActivityDate;

    @SerializedName("creation_date")
    long mCreationDate;

    @Getter
    @SerializedName("question_id")
    long mQuestionId;

    @Getter
    @SerializedName("link")
    private String mLink;

    @Getter
    @SerializedName("title")
    String mTitle;

    @Getter
    @SerializedName("body")
    String mBody;

    public DateTime getCreationDate() {
        return new DateTime(mCreationDate*1000);
    }

    public DateTime getLastActivityDate() {
        return new DateTime(mLastActivityDate*1000);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.mTags);
        dest.writeSerializable(this.mComments);
        dest.writeSerializable(this.mAnswers);
        dest.writeParcelable(this.mOwner, 0);
        dest.writeByte(mAnswered ? (byte) 1 : (byte) 0);
        dest.writeInt(this.mViewCount);
        dest.writeInt(this.mAnswerCount);
        dest.writeInt(this.mScore);
        dest.writeLong(this.mLastActivityDate);
        dest.writeLong(this.mCreationDate);
        dest.writeLong(this.mQuestionId);
        dest.writeString(this.mLink);
        dest.writeString(this.mTitle);
        dest.writeString(this.mBody);
    }

    public Question() {
    }

    private Question(Parcel in) {
        this.mTags = (ArrayList<String>) in.readSerializable();
        this.mComments = (ArrayList<Comment>) in.readSerializable();
        this.mAnswers = (ArrayList<Answer>) in.readSerializable();
        this.mOwner = in.readParcelable(Owner.class.getClassLoader());
        this.mAnswered = in.readByte() != 0;
        this.mViewCount = in.readInt();
        this.mAnswerCount = in.readInt();
        this.mScore = in.readInt();
        this.mLastActivityDate = in.readLong();
        this.mCreationDate = in.readLong();
        this.mQuestionId = in.readLong();
        this.mLink = in.readString();
        this.mTitle = in.readString();
        this.mBody = in.readString();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        public Question createFromParcel(Parcel source) {
            return new Question(source);
        }

        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
}
