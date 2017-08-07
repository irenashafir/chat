package shafir.irena.vetstreet.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.LocalDateTime;

/**
 * Created by irena on 29/07/2017.
 */

public class ChatItem implements Parcelable {

    String userName;
    String message;
    String time;
    private String profileImage;

    public ChatItem() {
    }

    public ChatItem(User user, String message) {
        this.userName = user.getDisplayName();
        this.message = message;
        this.time= LocalDateTime.now().toString();
        this.profileImage = user.getProfileImage();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ChatItem{" +
                "userName='" + userName + '\'' +
                ", message='" + message + '\'' +
                ", time='" + time + '\'' +
                ", profileImage='" + profileImage + '\'' +
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

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userName);
        dest.writeString(this.message);
        dest.writeString(this.time);
        dest.writeString(this.profileImage);
    }

    protected ChatItem(Parcel in) {
        this.userName = in.readString();
        this.message = in.readString();
        this.time = in.readString();
        this.profileImage = in.readString();
    }

    public static final Creator<ChatItem> CREATOR = new Creator<ChatItem>() {
        @Override
        public ChatItem createFromParcel(Parcel source) {
            return new ChatItem(source);
        }

        @Override
        public ChatItem[] newArray(int size) {
            return new ChatItem[size];
        }
    };
}
