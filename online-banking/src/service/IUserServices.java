package service;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface IUserServices {
    
    void register(String fullName, String tcNo, LocalDate birthDate, String password) throws Exception;

    void login(String tcNo, String password) throws Exception;

    void draw(BigDecimal amount) throws Exception;

    void deposit(BigDecimal amount) throws Exception;

    void transfer(BigDecimal amount, int userId) throws Exception;

}
