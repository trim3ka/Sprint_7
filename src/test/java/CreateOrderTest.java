import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import praktikum.CreateOrder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CreateOrderTest {

    @ParameterizedTest
    @MethodSource("orderParam")
    @DisplayName("Создание заказа с разными цветами")
    public void creatingOrderSuccess(String firstName, String lastName, String address, String metroStation,
                                     String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        CreateOrder createOrder = new CreateOrder();
        int track = createOrder.createOrder(firstName, lastName, address, metroStation,
                        phone, rentTime, deliveryDate, comment, color)
                .extract()
                .path("track");

        assertNotNull(track, "Трек номер заказа не должен быть null");
    }

    static Stream<Arguments> orderParam() {
        return Stream.of(
                Arguments.of("Pere", "Noel", "North Pole, 1", "1", "+7 123 456 78 90", 10, "2023-12-25", "Christmas delivery", Arrays.asList("BLACK")),
                Arguments.of("Pere", "Noel", "North Pole, 1", "1", "+7 123 456 78 90", 10, "2023-12-25", "Christmas delivery", Arrays.asList("GREY")),
                Arguments.of("Pere", "Noel", "North Pole, 1", "1", "+7 123 456 78 90", 10, "2023-12-25", "Christmas delivery", Arrays.asList("BLACK", "GREY")),
                Arguments.of("Pere", "Noel", "North Pole, 1", "1", "+7 123 456 78 90", 10, "2023-12-25", "Christmas delivery", Arrays.asList())
        );
    }
}
