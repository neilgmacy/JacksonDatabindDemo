package org.mcguiggan.jackson_databind_demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * A user from the social data.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private String mId;
    private String mPictureUrl;
    private String mEmail;
    private String mPhoneNumber;
    private String mAbout;
    private List<Friend> mFriends;

    @JsonProperty("_id")
    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    @JsonProperty("picture")
    public String getPictureUrl() {
        return mPictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        mPictureUrl = pictureUrl;
    }

    @JsonProperty("email")
    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    @JsonProperty("phone")
    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    @JsonProperty("about")
    public String getAbout() {
        return mAbout;
    }

    public void setAbout(String about) {
        mAbout = about;
    }

    @JsonProperty("friends")
    public List<Friend> getFriends() {
        return mFriends;
    }

    public void setFriends(List<Friend> friends) {
        mFriends = friends;
    }
}
