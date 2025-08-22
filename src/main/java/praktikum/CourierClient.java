package praktikum;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import praktikum.model.CourierCreated;
import praktikum.model.CourierLogin;

import static io.restassured.RestAssured.given;

public class CourierClient {

    //создание нового курьера
    public ValidatableResponse getNewCourier(CourierCreated courier) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(Constants.BASE_URL)
                .body(courier)
                .when()
                .post(Constants.CREATE_COURIER)
                .then().log().all();
    }

    //логин курьера в системе
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
    //удаление курьера из системы
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
