package shafir.irena.vetstreet.models;

/**
 * Created by irena on 13/07/2017.
 */

public class Favorite {
    private String link;

    private String userUID;
    private String userName;

    private String title;
    private String description;
    private String image;


    public Favorite() {
    }
    public Favorite(String link, String userUID, String userName, String title, String description, String image) {
        this.link = link;
        this.userUID = userUID;
        this.userName = userName;
        this.title = title;
        this.description = description;
        this.image = image;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "link='" + link + '\'' +
                ", userUID='" + userUID + '\'' +
                ", userName='" + userName + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
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
}
