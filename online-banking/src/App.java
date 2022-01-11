import java.math.BigDecimal;
import java.time.LocalDate;

import service.UserServices;

public class App {
    public static void main(String[] args) throws Exception {
        
        UserServices userServices = new UserServices();
        userServices.register("John Doe", "534234324", LocalDate.of(1994, 1, 1), "4324214");
        userServices.login("12345678901", "12345678901");
        userServices.deposit(new BigDecimal(100));
        userServices.draw(new BigDecimal(100));
        userServices.transfer(new BigDecimal(100), 2);

    }
}
