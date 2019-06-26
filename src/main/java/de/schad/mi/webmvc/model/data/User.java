package de.schad.mi.webmvc.model.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class User {

    @Id
    @Column(unique = true)
    private String loginname;

    private String password;

    private String fullname;
    private boolean active;

    private String role;

    private String avatar;

    public User() {}

    public User(String loginname, String password, String fullname) {
        this.loginname = loginname;
        this.password = password;
        this.fullname = fullname;
        this.role = "MEMBER";
        this.active = false;
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

    public boolean isActive() {
        return this.active;
    }

    public boolean getActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return String.format("loginname: %s, password: %s, fullname: %s, active: %s",
            this.loginname, this.password, this.fullname, this.active);
    }

}