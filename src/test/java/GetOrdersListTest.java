import api.OrderApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrdersListTest {

    @Test
    @DisplayName("Получения списка заказов")
    @Description("Проверка, что можно получить список всех заказов")
    public void orderGetList() {
        OrderApi orderApi = new OrderApi();

        ValidatableResponse response = orderApi.getOrdersListLombok();

        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("orders", notNullValue());
    }
}