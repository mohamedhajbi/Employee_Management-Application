package spring.jpa.dto;

import jakarta.validation.constraints.NotEmpty;

public class LoginForm {

    @NotEmpty(message = "Login is required")
    private String login;

    @NotEmpty(message = "Password is required")
    private String password;

    // Getters and Setters

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
