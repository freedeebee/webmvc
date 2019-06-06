package de.schad.mi.webmvc.model;

import org.springframework.web.multipart.MultipartFile;

/**
 * UserCreationForm
 */
public class UserCreationForm {

    private String loginname;
    private String password;
    private String fullname;
    private MultipartFile avatar;

    public UserCreationForm() {}


    public UserCreationForm(String loginname, String password, String fullname, MultipartFile avatar) {
        this.loginname = loginname;
        this.password = password;
        this.fullname = fullname;
        this.avatar = avatar;
    }

    public String getLoginname() {
        return this.loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return this.fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public MultipartFile getAvatar() {
        return this.avatar;
    }

    public void setAvatar(MultipartFile avatar) {
        this.avatar = avatar;
    }
}