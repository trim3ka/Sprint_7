package praktikum;
import java.util.Random;

public class CourierCreated {
    private String login;
    private String password;
    private String firstName;

    //Основной конструктор курьера
    public CourierCreated(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }
    //Метод для генерации случайного курьера
    public static CourierCreated random() {
        var rnd = new Random();
        return new CourierCreated ("mva" + rnd.nextInt(10000), "P@ssw0rd111", "Pere Noel");
    }
    //Пустой конструктор для десериализации
    public CourierCreated() {}

    //Геттеры-методы для доступа к полям
    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}

