package praktikum.model;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.Random;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourierCreated {
    private String login;
    private String password;
    private String firstName;

    // Метод для генерации случайного курьера
    public static CourierCreated random() {
        var rnd = new Random();
        return new CourierCreated(
                "mva" + rnd.nextInt(10000),
                "P@ssw0rd111",
                "Pere Noel"
        );
    }
}