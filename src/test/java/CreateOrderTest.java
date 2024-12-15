import api.OrderApi;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import model.OrderData;
import model.OrderDataLombok;
import org.apache.http.HttpStatus;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.List;
import static org.hamcrest.CoreMatchers.instanceOf;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    protected Integer track;
    private List<String> color;
    public CreateOrderTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters ()
    public static Object[][] color() {
        return new Object[][] {
                {List.of("BLACK", "GREY")},
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of()}
        };
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Проверка, что можно создать заказ с разными параметрами цвета")
    public void orderCreateTest() {
        OrderDataLombok orderDataLombok = new OrderDataLombok("Y", "No", "Address, 111",
                "4", "+7 800 355 35 35", 5, "2024-12-12",
                "Hello. Have a nice day, reviewer :3", color);
        OrderApi orderApi = new OrderApi();

        ValidatableResponse response = orderApi.createOrderLombok(orderDataLombok);

        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("track", instanceOf(Integer.class));

        track = response.extract().path("track");
        OrderData orderData = new OrderData(Integer.toString (track));
        orderApi.cancelOrderLombok(orderData);
    }
}