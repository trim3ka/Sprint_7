import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import praktikum.CreateOrder;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class GetOrderListTest {
    private final CreateOrder createOrder = new CreateOrder();

    @Test
    @DisplayName("Проверка получения списка заказов")
    @Description("Проверяем, что эндпоинт возвращает валидный список заказов")
    public void shouldReturnValidOrdersList() {


        // Получаем ответ от API
        var response = createOrder.getOrdersList();

        // Проверяем основные аспекты ответа
        checkResponseStatus(response);
        checkResponseContainsOrdersList(response);
    }

    private void checkResponseStatus(ValidatableResponse response) {
        response.statusCode(HttpURLConnection.HTTP_OK);
    }

    private void checkResponseContainsOrdersList(ValidatableResponse response) {
        response.body("orders", notNullValue());

        // Извлекаем список заказов для дальнейших проверок
        List<Map<String, Object>> orders = response.extract().jsonPath().getList("orders");
        assertFalse(orders.isEmpty(), "Список заказов не должен быть пустым");
    }
}

