package shafir.irena.vetstreet.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by irena on 13/07/2017.
 */

public class User implements Parcelable {
    private String DisplayName;
    private String profileImage;
    private String uid;
    private String email;

    public User(FirebaseUser currentUser) {
    }

    public User(String displayName, String profileImage, String uid, String email) {
        DisplayName = displayName;
        this.profileImage = profileImage;
        this.uid = uid;
        this.email = email;
    }


    protected User(Parcel in) {
        DisplayName = in.readString();
        profileImage = in.readString();
        uid = in.readString();
        email = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public String toString() {
        return "User{" +
                "DisplayName='" + DisplayName + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", uid='" + uid + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        if (profileImage != null) {
            this.profileImage = profileImage;
        }
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(DisplayName);
        dest.writeString(profileImage);
        dest.writeString(uid);
        dest.writeString(email);
    }
}
