import api.CourierApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.CourierDataLombok;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest {

    protected Integer courierId;
    protected CourierDataLombok courierDataLombok;
    protected CourierApi courierApi;
    protected String loginParam;
    protected String passwordParam;
    protected String firstNameParam;

    @Before
    public void setUp() {
        loginParam = "Yana" + RandomStringUtils.randomAlphabetic(4);
        passwordParam = "password" + RandomStringUtils.randomAlphabetic(5);
        firstNameParam = "Yana" + RandomStringUtils.randomAlphabetic(3);
    }

    @After
    public void cleanUp() {
        if ((courierDataLombok.getLogin() != null) && (courierDataLombok.getPassword() != null)) {
            ValidatableResponse response = courierApi.loginCourierLombok(courierDataLombok);
            courierId = response.extract().path("id");
            if (courierId != null) {
                courierApi.deleteCourierLombok(courierId);
            }
        }
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Проверка, что можно создать курьера, заполнив все поля валидными данными")
    public void courierCreateTest() {
        courierDataLombok = new CourierDataLombok(loginParam, passwordParam, firstNameParam);
        courierApi = new CourierApi();

        ValidatableResponse response = courierApi.createCourierLombok(courierDataLombok);

        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    @Description("Проверка, что нельзя создать двух одинаковых курьеров")
    public void courierTwinNotCreateTest() {
        courierDataLombok = new CourierDataLombok(loginParam, passwordParam);
        courierApi = new CourierApi();

        courierApi.createCourierLombok(courierDataLombok);
        ValidatableResponse response = courierApi.createCourierLombok(courierDataLombok);

        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CONFLICT)
                .body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Проверка, что нельзя создать курьера без логина")
    public void courierWithoutLoginNotCreateTest() {
        courierDataLombok = new CourierDataLombok(null, passwordParam);
        courierApi = new CourierApi();

        ValidatableResponse response = courierApi.createCourierLombok(courierDataLombok);

        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Проверка, что нельзя создать курьера без пароля")
    public void courierWithoutPasswordNotCreateTest() {
        courierDataLombok = new CourierDataLombok(loginParam, null);
        courierApi = new CourierApi();

        ValidatableResponse response = courierApi.createCourierLombok(courierDataLombok);

        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}