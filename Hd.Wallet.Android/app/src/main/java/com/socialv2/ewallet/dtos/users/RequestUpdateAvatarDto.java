package com.socialv2.ewallet.dtos.users;

public class RequestUpdateAvatarDto {
    public String avatar;

    public RequestUpdateAvatarDto() {}

    public RequestUpdateAvatarDto(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "RequestUpdateAvatarDto{" +
                "avatar='" + avatar + '\'' +
                '}';
    }
}
