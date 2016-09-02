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
public class Tweet implements Serializable {
    private final String type = "Tweet";
    private String timeStamp;
    private String userImageURL;
    private String name;
    private String screenName;
    private String text;
    
    public Tweet() {
        timeStamp = null;
        userImageURL = null;
        name = null;
        screenName = null;
        text = null;
    }
    
    public Tweet(String timeStamp, String userImageURL, String name, 
            String screenName, String text) {
        this.timeStamp = timeStamp;
        this.userImageURL = userImageURL;
        this.name = name;
        this.screenName = screenName;
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
    
    public String getUserImageURL() {
        return userImageURL;
    }

    public void setUserImageURL(String userImageURL) {
        this.userImageURL = userImageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
