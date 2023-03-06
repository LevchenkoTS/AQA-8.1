package ru.netology.test;


import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SqlHelper;
import ru.netology.page.LoginPage;


import static com.codeborne.selenide.Selenide.*;
import static ru.netology.data.SqlHelper.cleanAuthCodesInDatabase;
import static ru.netology.data.SqlHelper.cleanDatabase;

public class LoginTest {

    @AfterAll
    static void teardown() {
        cleanDatabase();
    }

    @SneakyThrows
    @Test
    void successfulLogin() {
        cleanAuthCodesInDatabase();
        var loginPage = open("http://localhost:9999/", LoginPage.class);
        var authInfo = DataHelper.getValidAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verificationPageVisibility();
        var verificationCode = SqlHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode);
    }


    @Test
    void errorWhenInvalidVerificationCode() {
        var loginPage = open("http://localhost:9999/", LoginPage.class);
        var authInfo = DataHelper.getValidAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verificationPageVisibility();
        var verificationCode = DataHelper.generateRandomVerificationCode();
        verificationPage.verify(verificationCode.getCode());
        verificationPage.errorNotificationVisibility();
    }

    @Test
    void errorNotificationWhenMoreThreeInvalidPassword() {
        var loginPage = open("http://localhost:9999/", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoValidLoginInvalidPassword();
        loginPage.invalidLogin(authInfo);
        loginPage.invalidLogin(authInfo);
        loginPage.invalidLogin(authInfo);
        loginPage.errorNotificationWhenMoreThreeInvalidPass();
    }


    @Test
    void shouldErrorMessageWhenInvalidUser() {
        var loginPage = open("http://localhost:9999/", LoginPage.class);
        var authInfo = DataHelper.generateRandomUser();
        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotificationVisibility();
    }
}
