package com.arnav.model.user;

import org.hibernate.validator.constraints.Email;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotNull;

/**
 * Created by Shankar on 2/23/2017.
 */
public class SocialLoginRequest {

    @Email
    @NotNull(message = "Username should not be blank.")
    @Indexed(unique=true)
    private String username;

    @NotNull(message = "social Id should not be blank.")
    private String socialId;

    @NotNull(message = "social type should not be blank.")
    private String type;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SocialLoginRequest{" +
                "username='" + username + '\'' +
                ", socialId='" + socialId + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
