package model;
import io.qameta.allure.internal.shadowed.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginDataLombok {

    private String login;
    private String password;

    public LoginDataLombok(String login, String password) {
        this.login = login;
        this.password = password;
    }
}