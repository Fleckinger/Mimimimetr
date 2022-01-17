package ru.inovus.mimimimetr.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDto {

    @NotEmpty(message = "Введите email")
    @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
            message = "Неверный формат")
    @NotNull
    private String email;

    @NotNull
    private String confirmedEmail;

    @NotNull
    @Size(min = 6, message = "Пароль должен быть не короче 6 символов")
    private String password;

    @NotNull
    @Size(min = 6, message = "Пароль должен быть не короче 6 символов")
    private String confirmedPassword;

    public UserDto() {
    }

    public UserDto(String email, String confirmedEmail, String password, String confirmedPassword) {
        this.email = email;
        this.confirmedEmail = confirmedEmail;
        this.password = password;
        this.confirmedPassword = confirmedPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmedEmail() {
        return confirmedEmail;
    }

    public void setConfirmedEmail(String confirmedEmail) {
        this.confirmedEmail = confirmedEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "email='" + email + '\'' +
                ", confirmedEmail='" + confirmedEmail + '\'' +
                ", password='" + password + '\'' +
                ", confirmedPassword='" + confirmedPassword + '\'' +
                '}';
    }
}
