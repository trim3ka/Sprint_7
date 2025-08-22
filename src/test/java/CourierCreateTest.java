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

    @Step("Создание 2го идентичного курьера")
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
    @DisplayName("Проверки обязательных полей при создании курьера")

    public void checkRequiredFieldsForCourierCreation() {
        Allure.step("Создание курьера: поле 'login' обязательно", () -> {
            var courier = CourierCreated.random();
            courier.setLogin(null);

            //Создание курьера без login
            client.getNewCourier(courier)
                    .assertThat()
                    .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                    .body("message", equalTo("Недостаточно данных для создания учетной записи"));
        });

        Allure.step("Создание курьера: поле 'password' обязательно", () -> {
            var courier = CourierCreated.random();
            courier.setPassword(null);

            //Создание курьера без password
            client.getNewCourier(courier)
                    .assertThat()
                    .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                    .body("message", equalTo("Недостаточно данных для создания учетной записи"));
        });

    }
}
