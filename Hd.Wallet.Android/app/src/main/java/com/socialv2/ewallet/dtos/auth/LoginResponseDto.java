package com.socialv2.ewallet.dtos.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.socialv2.ewallet.dtos.users.UserDto;

public class LoginResponseDto {
    @SerializedName("user")
    @Expose
    private UserDto user;

    @SerializedName("token")
    @Expose
    private String token;

    public LoginResponseDto() { }

    public LoginResponseDto(UserDto user, String token) {
        this.user = user;
        this.token = token;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginResponseDto{" +
                "user=" + user +
                ", token='" + token + '\'' +
                '}';
    }
}
