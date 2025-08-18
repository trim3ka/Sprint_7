package praktikum;
import java.util.Random;

public class CourierLogin {
    private String login;
    private String password;


    //Основной конструктор курьера
    public CourierLogin(String login, String password) {
        this.login = login;
        this.password = password;
    }
    //Метод для генерации случайного курьера
    public static CourierLogin from (CourierCreated courierCreated) {
        return new CourierLogin(courierCreated.getLogin(), courierCreated.getPassword());
    }

    //Геттеры
    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
