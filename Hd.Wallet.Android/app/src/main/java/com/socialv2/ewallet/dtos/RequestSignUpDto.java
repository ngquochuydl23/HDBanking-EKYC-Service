package com.socialv2.ewallet.dtos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestSignUpDto {

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

    @SerializedName("faceVerificationUrl")
    @Expose
    private String faceVerificationUrl;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("address")
    @Expose
    private Address address;


    public RequestSignUpDto() {

    }

    public RequestSignUpDto(String phoneNumber, String fullName, String email, String dateOfBirth, int sex, String idCardNo, String faceVerificationUrl, String password, Address address) {
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.idCardNo = idCardNo;
        this.faceVerificationUrl = faceVerificationUrl;
        this.password = password;
        this.address = address;
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

    public String getFaceVerificationUrl() {
        return faceVerificationUrl;
    }

    public void setFaceVerificationUrl(String faceVerificationUrl) {
        this.faceVerificationUrl = faceVerificationUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "RequestSignUpDto{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", sex=" + sex +
                ", idCardNo='" + idCardNo + '\'' +
                ", faceVerificationUrl='" + faceVerificationUrl + '\'' +
                ", password='" + password + '\'' +
                ", address=" + address +
                '}';
    }

    public static class Address {

        @SerializedName("street")
        @Expose
        private String street;

        @SerializedName("provinceOrCity")
        @Expose
        private String provinceOrCity;

        @SerializedName("district")
        @Expose
        private String district;

        @SerializedName("wardOrCommune")
        @Expose
        private String wardOrCommune;

        public Address() {}


        public Address(String street, String provinceOrCity, String district, String wardOrCommune) {
            this.street = street;
            this.provinceOrCity = provinceOrCity;
            this.district = district;
            this.wardOrCommune = wardOrCommune;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getProvinceOrCity() {
            return provinceOrCity;
        }

        public void setProvinceOrCity(String provinceOrCity) {
            this.provinceOrCity = provinceOrCity;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getWardOrCommune() {
            return wardOrCommune;
        }

        public void setWardOrCommune(String wardOrCommune) {
            this.wardOrCommune = wardOrCommune;
        }

        @Override
        public String toString() {
            return "Address{" +
                    "street='" + street + '\'' +
                    ", provinceOrCity='" + provinceOrCity + '\'' +
                    ", district='" + district + '\'' +
                    ", wardOrCommune='" + wardOrCommune + '\'' +
                    '}';
        }
    }
}
