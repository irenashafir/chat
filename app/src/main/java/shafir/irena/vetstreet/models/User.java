package shafir.irena.vetstreet.models;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by irena on 13/07/2017.
 */

public class User {
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
        this.profileImage = profileImage;
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
}
