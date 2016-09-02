/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;

/**
 *
 * @author Kevin
 */
public class Insta implements Serializable{
    private final String type = "Insta";
    private String caption;
    private long likes;
    private String link;
    private String userName;
    private String fullName;
    private String profilePictureURL;
    private long createdTime;
    private String mediaType;
    private String mediaURL;
    private long mediaWidth;
    private long mediaHeight;

    public Insta() {
        caption = null;
        likes = 0;
        link = null;
        userName = null;
        fullName = null;
        profilePictureURL = null;
        createdTime = 0;
        mediaType = null;
        mediaURL = null;
        mediaWidth = 0;
        mediaHeight = 0;
    }

    public Insta(String caption, long likes, String link, String userName, String fullName, String profilePictureURL, long createdTime, String mediaType, String mediaURL, long mediaWidth, long mediaHeight) {
        this.caption = caption;
        this.likes = likes;
        this.link = link;
        this.userName = userName;
        this.fullName = fullName;
        this.profilePictureURL = profilePictureURL;
        this.createdTime = createdTime;
        this.mediaType = mediaType;
        this.mediaURL = mediaURL;
        this.mediaWidth = mediaWidth;
        this.mediaHeight = mediaHeight;
    }

    public String getType() {
        return type;
    }
    
    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    
    public String getMediaURL() {
        return mediaURL;
    }

    public void setMediaURL(String mediaURL) {
        this.mediaURL = mediaURL;
    }

    public long getMediaWidth() {
        return mediaWidth;
    }

    public void setMediaWidth(long mediaWidth) {
        this.mediaWidth = mediaWidth;
    }

    public long getMediaHeight() {
        return mediaHeight;
    }

    public void setMediaHeight(long mediaHeight) {
        this.mediaHeight = mediaHeight;
    }

}
