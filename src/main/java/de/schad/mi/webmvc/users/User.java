package de.schad.mi.webmvc.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "USERS")
public class User {

    @Id
    @Size(min = 3, message = "Minimum 3 Characters")
    @Column(unique = true)
    private String loginname;

    @NotBlank(message = "This field may not be empty")
    private String password;

    @NotBlank(message = "This field may not be empty")
    private String fullname;
    private boolean active;

    public User() {}

    public User(String loginname, String password, String fullname) {
        this.loginname = loginname;
        this.password = password;
        this.fullname = fullname;
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

    @Override
    public String toString() {
        return String.format("loginname: %s, password: %s, fullname: %s, active: %s",
            this.loginname, this.password, this.fullname, this.active);
    }

}