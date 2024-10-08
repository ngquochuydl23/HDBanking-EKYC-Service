package com.socialv2.ewallet.dtos.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class UserDto {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;

    @SerializedName("fullName")
    @Expose
    private String fullName;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("dateOfBirth")
    @Expose
    private String dateOfBirth;

    @SerializedName("sex")
    @Expose
    private int sex;

    @SerializedName("idCardNo")
    @Expose
    private String idCardNo;

    @SerializedName("nationality")
    @Expose
    private String nationality;

    @SerializedName("placeOfOrigin")
    @Expose
    private String placeOfOrigin;

    @SerializedName("placeOfResidence")
    @Expose
    private String placeOfResidence;

    @SerializedName("dateOfExpiry")
    @Expose
    private String dateOfExpiry;

    @SerializedName("frontIdCardUrl")
    @Expose
    private String frontIdCardUrl;

    @SerializedName("backIdCardUrl")
    @Expose
    private String backIdCardUrl;

    @SerializedName("idCardType")
    @Expose
    private String idCardType;

    @SerializedName("isEkycVerfied")
    @Expose
    private boolean isEkycVerfied;

    @SerializedName("faceVerificationUrl")
    @Expose
    private String faceVerificationUrl;

    @SerializedName("avatar")
    @Expose
    private String avatar;

    public UserDto() { }

    public UserDto(String id, String phoneNumber, String fullName, String email, String dateOfBirth, int sex, String idCardNo, String nationality, String placeOfOrigin, String placeOfResidence, String dateOfExpiry, String frontIdCardUrl, String backIdCardUrl, String idCardType, boolean isEkycVerfied, String faceVerificationUrl, String avatar) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.idCardNo = idCardNo;
        this.nationality = nationality;
        this.placeOfOrigin = placeOfOrigin;
        this.placeOfResidence = placeOfResidence;
        this.dateOfExpiry = dateOfExpiry;
        this.frontIdCardUrl = frontIdCardUrl;
        this.backIdCardUrl = backIdCardUrl;
        this.idCardType = idCardType;
        this.isEkycVerfied = isEkycVerfied;
        this.faceVerificationUrl = faceVerificationUrl;
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
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

    public String getFrontIdCardUrl() {
        return frontIdCardUrl;
    }

    public void setFrontIdCardUrl(String frontIdCardUrl) {
        this.frontIdCardUrl = frontIdCardUrl;
    }

    public String getBackIdCardUrl() {
        return backIdCardUrl;
    }

    public void setBackIdCardUrl(String backIdCardUrl) {
        this.backIdCardUrl = backIdCardUrl;
    }

    public String getIdCardType() {
        return idCardType;
    }

    public void setIdCardType(String idCardType) {
        this.idCardType = idCardType;
    }

    public boolean isEkycVerfied() {
        return isEkycVerfied;
    }

    public void setEkycVerfied(boolean ekycVerfied) {
        isEkycVerfied = ekycVerfied;
    }

    public String getFaceVerificationUrl() {
        return faceVerificationUrl;
    }

    public void setFaceVerificationUrl(String faceVerificationUrl) {
        this.faceVerificationUrl = faceVerificationUrl;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id='" + id + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", sex=" + sex +
                ", idCardNo='" + idCardNo + '\'' +
                ", nationality='" + nationality + '\'' +
                ", placeOfOrigin='" + placeOfOrigin + '\'' +
                ", placeOfResidence='" + placeOfResidence + '\'' +
                ", dateOfExpiry='" + dateOfExpiry + '\'' +
                ", frontIdCardUrl='" + frontIdCardUrl + '\'' +
                ", backIdCardUrl='" + backIdCardUrl + '\'' +
                ", idCardType='" + idCardType + '\'' +
                ", isEkycVerfied=" + isEkycVerfied +
                ", faceVerificationUrl='" + faceVerificationUrl + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) && Objects.equals(phoneNumber, userDto.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phoneNumber);
    }
}
