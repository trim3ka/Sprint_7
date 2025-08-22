package praktikum.model;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class CourierLogin {
    private String login;
    private String password;

    // Метод для генерации случайного курьера
    public static CourierLogin from(CourierCreated courierCreated) {
        return new CourierLogin(courierCreated.getLogin(), courierCreated.getPassword());
    }
}
