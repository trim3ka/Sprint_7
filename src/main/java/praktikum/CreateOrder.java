package praktikum;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import java.net.HttpURLConnection;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static praktikum.Constants.*;

public class CreateOrder {

    //Создание нового заказа
    public ValidatableResponse createOrder(String firstName, String lastName, String address, String metroStation, String phone, int rentTime,
                                           String deliveryDate, String comment, List<String> color) {
        ОrderCreated order = new ОrderCreated(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(order)
                .when()
                .post(ORDER_CREATED)
                .then()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .body("track", notNullValue());
    }


    //Получение списка заказов
    public ValidatableResponse getOrdersList() {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .when()
                .get(ORDERS_LIST)
                .then()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("orders", notNullValue());
    }
}