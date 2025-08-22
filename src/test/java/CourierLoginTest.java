import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import praktikum.CourierClient;
import praktikum.model.CourierCreated;
import praktikum.model.CourierLogin;
import java.net.HttpURLConnection;
import static org.hamcrest.Matchers.*;

class CourierLoginTest {

    private CourierClient client;
    private CourierCreated testCourier;
    private String courierId;

    @BeforeEach
    public void setUp() {
        client = new CourierClient();
        testCourier = CourierCreated.random();

        // Создаем курьера для авторизации
        client.getNewCourier(testCourier)
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED);

        // Получаем ID курьера
        courierId = client.logInCourier(CourierLogin.from(testCourier))
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .path("id").toString();
    }

    @Test
    @DisplayName("Успешная авторизация курьера")

    public void logInCourierSuccessfully () {

        // Выполняем авторизацию
        client.logInCourier(CourierLogin.from(testCourier))
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("id", notNullValue()); // Проверяем, что вернулся id курьера
    }

    @Test
    @DisplayName("Авторизация курьера без login")
    public void checkRequiredFieldLoginForCourierLogIn() {
        client.logInCourier(new CourierLogin(null, testCourier.getPassword()))
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация курьера без password")
    public void checkRequiredFieldPasswordForCourierLogIn() {
        client.logInCourier(new CourierLogin(testCourier.getLogin(), null))
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация курьера c неправильным login")
    public void checkCorrectLoginForCourierLogIn() {
        client.logInCourier(new CourierLogin(testCourier.getLogin() + 1, testCourier.getPassword()))
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация курьера c неправильным password")
    public void checkCorrectPasswordForCourierLogIn() {
        client.logInCourier(new CourierLogin(testCourier.getLogin(), testCourier.getPassword() + 1))
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }




    @AfterEach
    public void tearDown() {
        // Удаляем созданного курьера, если ID был получен
        if (courierId != null && !courierId.isEmpty()) {
            client.deleteCourier(courierId)
                    .assertThat()
                    .statusCode(HttpURLConnection.HTTP_OK);
        }
    }
}