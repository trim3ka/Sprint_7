import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import praktikum.CourierClient;
import praktikum.model.CourierCreated;
import java.net.HttpURLConnection;
import java.util.Random;
import static org.hamcrest.Matchers.equalTo;


public class CourierCreateTest {

    final CourierClient client = new CourierClient();
    private String courierId;
    private CourierCreated testCourier;

    @AfterEach
    void deleteCourier() {
        if (courierId != null) {
            client.deleteCourier(courierId);
        }
    }

    @Test
    @DisplayName("Успешный кейс создания курьера")
    @Step("Создание уникального курьера")
    public void createUniqueCourier() {

        var courier = CourierCreated.random();

        client.getNewCourier(courier)
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Проверка, что нельзя создать двух одинаковых курьеров")

    public void createDuplicateCourierShouldFail() {

        var courier = CourierCreated.random();

        //Создание курьера с опред логином
        client.getNewCourier(courier)
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .body("ok", equalTo(true));

        //Создание курьера с таким же набором login, password, firstname
        client.getNewCourier(courier)
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CONFLICT)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Проверка, что нельзя создать курьера с уже существующим логином")

    public void createDuplicateLoginCourierShouldFail() {

        var firstCourier = CourierCreated.random();

        //Создание курьера с опред логином
        client.getNewCourier(firstCourier)
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .body("ok", equalTo(true));

        //Создание 2го курьера с аналогичным логином
        var random = new Random();
        var secondCourier = new CourierCreated(firstCourier.getLogin(), "password_" + random.nextInt(10000), "name_" + random.nextInt(1000));
        //Создание курьера с таким же логином
        client.getNewCourier(secondCourier)
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CONFLICT)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Проверка обязательности поля 'login' при создании курьера")
    public void checkLoginFieldIsRequired() {
        Allure.step("Создание курьера без login", () -> {
            var courier = CourierCreated.random();
            courier.setLogin(null);

            client.getNewCourier(courier)
                    .assertThat()
                    .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                    .body("message", equalTo("Недостаточно данных для создания учетной записи"));
        });
    }

    @Test
    @DisplayName("Проверка обязательности поля 'password' при создании курьера")
    public void checkPasswordFieldIsRequired() {
        Allure.step("Создание курьера без password", () -> {
            var courier = CourierCreated.random();
            courier.setPassword(null);

            client.getNewCourier(courier)
                    .assertThat()
                    .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                    .body("message", equalTo("Недостаточно данных для создания учетной записи"));
        });
    }
}
