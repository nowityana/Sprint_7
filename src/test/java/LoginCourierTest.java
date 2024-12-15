import api.CourierApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.CourierDataLombok;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.equalTo;

public class LoginCourierTest {

    protected Integer courierId;
    protected String loginParam;
    protected String passwordParam;
    protected String firstNameParam;

    @Before
    public void setUp() {
        loginParam = "Yana" + RandomStringUtils.randomAlphabetic(4);
        passwordParam = "password" + RandomStringUtils.randomAlphabetic(5);
    }

    @Test
    @DisplayName("Авторизация курьера")
    @Description("Проверка, что можно авторизоваться курьером, заполнив валидными данными поля")
    public void courierAutoTest() {
        CourierDataLombok courierDataLombok = new CourierDataLombok(loginParam, passwordParam);
        CourierApi courierApi = new CourierApi();
        courierApi.createCourierLombok(courierDataLombok);

        ValidatableResponse response = courierApi.loginCourierLombok(courierDataLombok);

        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("id", instanceOf(Integer.class));

        courierId = response.extract().path("id");
        if (courierId != null) {
            courierApi.deleteCourierLombok(courierId);
        }
    }

    @Test
    @DisplayName("Авторизация курьера без логина")
    @Description("Проверка,что нельзя авторизоваться курьером без логина")
    public void courierAutoWithoutLoginTest() {
        CourierDataLombok courierDataLombok = new CourierDataLombok(null, passwordParam);
        CourierApi courierApi = new CourierApi();

        ValidatableResponse response = courierApi.loginCourierLombok(courierDataLombok);

        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация курьера без пароля")
    @Description("Проверка,что нельзя авторизоваться курьером без пароля")
    public void courierAutoWithoutPasswordTest() {
        CourierDataLombok courierDataLombok = new CourierDataLombok(loginParam, null);
        CourierApi courierApi = new CourierApi();

        ValidatableResponse response = courierApi.loginCourierLombok(courierDataLombok);

        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация курьера с несуществующими данными")
    @Description("Проверка, что нельзя авторизоваться курьером с несуществующими данными")
    public void courierAutoNotfoundTest() {
        CourierDataLombok courierDataLombok = new CourierDataLombok("loginParamYana", "passwordParamYana");
        CourierApi courierApi = new CourierApi();

        ValidatableResponse response = courierApi.loginCourierLombok(courierDataLombok);

        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}