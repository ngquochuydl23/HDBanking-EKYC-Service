package com.socialv2.ewallet.dtos.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkDto {
    @SerializedName("occupation")
    @Expose
    private String occupation;

    @SerializedName("position")
    @Expose
    private String position;

    public WorkDto() {

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

    @Override
    public String toString() {
        return "WorkDto{" +
                "occupation='" + occupation + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
