package com.socialv2.ewallet.dtos.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestUpdateUserDto {
    @SerializedName("occupation")
    @Expose
    private String occupation;

    @SerializedName("position")
    @Expose
    private String position;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("currentLiving")
    @Expose
    private String currentLiving;

    @SerializedName("marriageStatus")
    @Expose
    private String marriageStatus;

    @SerializedName("studyLevel")
    @Expose
    private String studyLevel;

    @SerializedName("sex")
    @Expose
    private int sex;

    public RequestUpdateUserDto() {

    }

    public RequestUpdateUserDto(
            String occupation,
            String position,
            String email,
            String currentLiving,
            String marriageStatus,
            String studyLevel,
            int sex) {
        this.occupation = occupation;
        this.position = position;
        this.email = email;
        this.currentLiving = currentLiving;
        this.marriageStatus = marriageStatus;
        this.studyLevel = studyLevel;
        this.sex = sex;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrentLiving() {
        return currentLiving;
    }

    public void setCurrentLiving(String currentLiving) {
        this.currentLiving = currentLiving;
    }

    public String getMarriageStatus() {
        return marriageStatus;
    }

    public void setMarriageStatus(String marriageStatus) {
        this.marriageStatus = marriageStatus;
    }

    public String getStudyLevel() {
        return studyLevel;
    }

    public void setStudyLevel(String studyLevel) {
        this.studyLevel = studyLevel;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "RequestUpdateUserDto{" +
                "occupation='" + occupation + '\'' +
                ", position='" + position + '\'' +
                ", email='" + email + '\'' +
                ", currentLiving='" + currentLiving + '\'' +
                ", marriageStatus='" + marriageStatus + '\'' +
                ", studyLevel='" + studyLevel + '\'' +
                ", sex=" + sex +
                '}';
    }
}
