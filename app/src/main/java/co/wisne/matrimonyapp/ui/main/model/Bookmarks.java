package co.wisne.matrimonyapp.ui.main.model;

import android.net.Uri;

import java.util.UUID;

public class Bookmarks {
    String UUID;
    String fullname;
    Uri profilePictureUri;

    public Bookmarks(String UUID) {
        this.UUID = UUID;
    }

    public Bookmarks(String UUID, String Fullname){
        this.UUID = UUID;
        fullname = Fullname;
    }

    public Bookmarks(String UUID, String fullname, Uri profilePictureUri) {
        this.UUID = UUID;
        this.fullname = fullname;
        this.profilePictureUri = profilePictureUri;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Uri getProfilePictureUri() {
        return profilePictureUri;
    }

    public void setProfilePictureUri(Uri profilePictureUri) {
        this.profilePictureUri = profilePictureUri;
    }
}
