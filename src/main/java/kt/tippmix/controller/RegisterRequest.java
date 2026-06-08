package kt.tippmix.controller;

import jakarta.persistence.Column;
import org.jspecify.annotations.NonNull;

public class RegisterRequest {
    private String userName;

    private String pw;

    private String email;

    public RegisterRequest() {
    }

    public RegisterRequest(@NonNull String userName, @NonNull String pw, @NonNull String email) {
        this.userName = userName;
        this.pw = pw;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
