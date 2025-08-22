package praktikum;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import praktikum.model.CourierCreated;
import praktikum.model.CourierLogin;

import static io.restassured.RestAssured.given;

public class CourierClient {

    @Step("Создание нового курьера")
    public ValidatableResponse getNewCourier(CourierCreated courier) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(Constants.BASE_URL)
                .body(courier)
                .when()
                .post(Constants.CREATE_COURIER)
                .then().log().all();
    }

    @Step("Логин курьера в системе")
    public ValidatableResponse logInCourier(CourierLogin courier) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .baseUri(Constants.BASE_URL)
                .body(courier)
                .when()
                .post(Constants.LOGIN_COURIER)
                .then().log().all();
    }
    @Step("Удаление курьера из системы")
    public ValidatableResponse deleteCourier(String id) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .baseUri(Constants.BASE_URL)
                .when()
                .delete(Constants.DELETE_COURIER + id)
                .then();
    }

}
