package model;
import io.qameta.allure.internal.shadowed.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourierDataLombok {

    private String login;
    private String password;
    private String firstName;

    public CourierDataLombok(String login, String password) {
        this.login = login;
        this.password = password;
    }
}