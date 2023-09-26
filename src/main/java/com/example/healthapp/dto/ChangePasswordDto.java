package com.example.healthapp.dto;

public class ChangePasswordDto {
    private String oldPassword;
    private String newPassword;
    private String NewPasswordRepeat;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordRepeat() {
        return NewPasswordRepeat;
    }

    public void setNewPasswordRepeat(String newPasswordRepeat) {
        NewPasswordRepeat = newPasswordRepeat;
    }
}
