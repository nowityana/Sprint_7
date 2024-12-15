package api;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.CourierDataLombok;
import static io.restassured.RestAssured.given;

public class CourierApi extends RestApi {

    public static final String CREATE_COURIER_URL = "/api/v1/courier";
    public static final String LOGIN_COURIER_URL = "/api/v1/courier/login";
    public static final String DELETE_COURIER_URL = "/api/v1/courier/";

    @Step("Create courier")
    public ValidatableResponse createCourierLombok(CourierDataLombok courier){
        return given()
                .spec(requestSpecification())
                .and()
                .body(courier)
                .when()
                .post(CREATE_COURIER_URL)
                .then();
    }

    @Step("Login courier")
    public ValidatableResponse loginCourierLombok(CourierDataLombok courier){
        return given()
                .spec(requestSpecification())
                .and()
                .body(courier)
                .when()
                .post(LOGIN_COURIER_URL)
                .then();
    }

    @Step("Delete courier")
    public ValidatableResponse deleteCourierLombok(int courierId){
        return given()
                .spec(requestSpecification())
                .when()
                .delete(DELETE_COURIER_URL + courierId)
                .then();
    }
}