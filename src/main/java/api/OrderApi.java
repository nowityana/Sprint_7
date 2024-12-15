package api;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.OrderData;
import model.OrderDataLombok;
import static io.restassured.RestAssured.given;

public class OrderApi extends RestApi {

    public static final String CREATE_ORDER_URL = "/api/v1/orders";
    public static final String CANCEL_ORDER_URL = "/api/v1/orders/cancel";

    @Step("Create order")
    public ValidatableResponse createOrderLombok(OrderDataLombok order){
        return given()
                .spec(requestSpecification())
                .and()
                .body(order)
                .when()
                .post(CREATE_ORDER_URL)
                .then();
    }

    @Step("Get orders list")
    public ValidatableResponse getOrdersListLombok(){
        return given()
                .spec(requestSpecification())
                .when()
                .get(CREATE_ORDER_URL)
                .then();
    }

    @Step("Cancel order")
    public ValidatableResponse cancelOrderLombok(OrderData order){
        return given()
                .spec(requestSpecification())
                .and()
                .body(order)
                .when()
                .put(CANCEL_ORDER_URL)
                .then();
    }
}