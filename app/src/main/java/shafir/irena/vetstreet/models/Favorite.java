package shafir.irena.vetstreet.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by irena on 13/07/2017.
 */

public class Favorite implements Parcelable{
    private String link;

    private String userUID;
    private String userName;

    private String title;
    private String description;
    private String image;
    private String favoriteUID;


    public Favorite() {}

    public Favorite(String link, String userUID, String userName, String title, String description, String image) {
        this.link = link;
        this.userUID = userUID;
        this.userName = userName;
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public Favorite(Parcel in) {
        link = in.readString();
        userUID = in.readString();
        userName = in.readString();
        title = in.readString();
        description = in.readString();
        image = in.readString();
    }

    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel in) {
            return new Favorite(in);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };

    @Override
    public String toString() {
        return "Favorite{" +
                "link='" + link + '\'' +
                ", userUID='" + userUID + '\'' +
                ", userName='" + userName + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", favoriteUID='" + favoriteUID + '\'' +
                '}';
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(link);
        dest.writeString(userUID);
        dest.writeString(userName);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(image);
    }
}
