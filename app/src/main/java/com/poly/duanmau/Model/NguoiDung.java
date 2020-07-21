package com.poly.duanmau.Model;

import androidx.annotation.NonNull;

public class NguoiDung {
    private String userName, passWord, phone, hoTen;

    public NguoiDung() {
    }

    public NguoiDung(String userName, String passWord, String phone, String hoTen) {
        this.userName = userName;
        this.passWord = passWord;
        this.phone = phone;
        this.hoTen = hoTen;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    @NonNull
    @Override
    public String toString() {
        return "NguoiDung{" +
                "userName='" + userName + '\'' +
                ", password='" + passWord + '\'' +
                ", phone='" + phone + '\'' +
                ", hoTen='" + hoTen + '\'' +
                '}';
    }
}
