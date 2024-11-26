package com.socialv2.ewallet.dtos.users;

public class RequestResetPasswordDto {
    private String fullName;
    private String idCardNo;
    private String phoneNumber;

    public RequestResetPasswordDto() {

    }

    public RequestResetPasswordDto(String fullName, String idCardNo, String phoneNumber) {
        this.fullName = fullName;
        this.idCardNo = idCardNo;
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "RequestResetPasswordDto{" +
                "fullName='" + fullName + '\'' +
                ", idCardNo='" + idCardNo + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
