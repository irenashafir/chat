package shafir.irena.vetstreet.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by irena on 29/07/2017.
 */

public class ChatItem implements Parcelable {

    String userName;
    String message;
    String time;


    public ChatItem() {
    }

    public ChatItem(String userName, String message, String time) {
        this.userName = userName;
        this.message = message;
        this.time= time;
    }

    protected ChatItem(Parcel in) {
        userName = in.readString();
        message = in.readString();
        time = in.readString();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public static final Creator<ChatItem> CREATOR = new Creator<ChatItem>() {
        @Override
        public ChatItem createFromParcel(Parcel in) {
            return new ChatItem(in);
        }

        @Override
        public ChatItem[] newArray(int size) {
            return new ChatItem[size];
        }
    };

    @Override
    public String toString() {
        return "ChatItem{" +
                "userName='" + userName + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(message);
        dest.writeString(time);
    }
}
