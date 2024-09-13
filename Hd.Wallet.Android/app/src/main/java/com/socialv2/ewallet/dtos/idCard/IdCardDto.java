package com.socialv2.ewallet.dtos.idCard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class IdCardDto {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("full_name")
    @Expose
    private String fullName;

    @SerializedName("date_of_birth")
    @Expose
    private String dateOfBirth;

    @SerializedName("sex")
    @Expose
    private String sex;

    @SerializedName("nationality")
    @Expose
    private String nationality;

    @SerializedName("place_of_origin")
    @Expose
    private String placeOfOrigin;

    @SerializedName("place_of_residence")
    @Expose
    private String placeOfResidence;

    @SerializedName("date_of_expiry")
    @Expose
    private String dateOfExpiry;

    public IdCardDto() {

    }

    public IdCardDto(String id, String fullName, String dateOfBirth, String sex, String nationality, String placeOfOrigin, String placeOfResidence, String dateOfExpiry) {
        this.id = id;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.nationality = nationality;
        this.placeOfOrigin = placeOfOrigin;
        this.placeOfResidence = placeOfResidence;
        this.dateOfExpiry = dateOfExpiry;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPlaceOfOrigin() {
        return placeOfOrigin;
    }

    public void setPlaceOfOrigin(String placeOfOrigin) {
        this.placeOfOrigin = placeOfOrigin;
    }

    public String getPlaceOfResidence() {
        return placeOfResidence;
    }

    public void setPlaceOfResidence(String placeOfResidence) {
        this.placeOfResidence = placeOfResidence;
    }

    public String getDateOfExpiry() {
        return dateOfExpiry;
    }

    public void setDateOfExpiry(String dateOfExpiry) {
        this.dateOfExpiry = dateOfExpiry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdCardDto idCardDto = (IdCardDto) o;
        return Objects.equals(id, idCardDto.id) && Objects.equals(fullName, idCardDto.fullName) && Objects.equals(dateOfBirth, idCardDto.dateOfBirth) && Objects.equals(sex, idCardDto.sex) && Objects.equals(nationality, idCardDto.nationality) && Objects.equals(placeOfOrigin, idCardDto.placeOfOrigin) && Objects.equals(placeOfResidence, idCardDto.placeOfResidence) && Objects.equals(dateOfExpiry, idCardDto.dateOfExpiry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, dateOfBirth, sex, nationality, placeOfOrigin, placeOfResidence, dateOfExpiry);
    }

    @Override
    public String toString() {
        return "IdCardDto{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", sex='" + sex + '\'' +
                ", nationality='" + nationality + '\'' +
                ", placeOfOrigin='" + placeOfOrigin + '\'' +
                ", placeOfResidence='" + placeOfResidence + '\'' +
                ", dateOfExpiry='" + dateOfExpiry + '\'' +
                '}';
    }
}
