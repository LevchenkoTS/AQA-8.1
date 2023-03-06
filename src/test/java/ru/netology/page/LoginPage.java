package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
public class LoginPage {
//    @FindBy(css = "[data-test-id=login] input")
//    private SelenideElement loginField;
//    @FindBy(css = "[data-test-id=password] input")
//    private SelenideElement passwordField;
//    @FindBy(css = "[data-test-id=action-login]")
//    private SelenideElement loginButton;
//    @FindBy (css = "[data-test-id='error-notification']")
//    private SelenideElement errorNotification;
//    @FindBy (css = "Пользователь заблокирован")
//    private String errorNotificationWhenMoreThreeInvalidPass;


        private SelenideElement loginField = $("[data-test-id='login'] input");
        private SelenideElement passwordField = $("[data-test-id='password'] input");
        private SelenideElement loginButton = $("[data-test-id='action-login']");
        private SelenideElement errorNotification = $("[data-test-id='error-notification']");
        private String errorNotificationWhenMoreThreeInvalidPass = "Пользователь заблокирован";

        public void verifyErrorNotificationVisibility() {
            errorNotification.shouldBe(visible);
        }

        public VerificationPage validLogin(DataHelper.AuthInfo info) {
            loginField.setValue(info.getLogin());
            passwordField.setValue(info.getPassword());
            loginButton.click();
            return new VerificationPage();
        }

        public LoginPage invalidLogin(DataHelper.AuthInfo info) {
            loginField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME, Keys.BACK_SPACE));
            loginField.setValue(info.getLogin());
            passwordField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME, Keys.BACK_SPACE));
            passwordField.setValue(info.getPassword());
            loginButton.click();
            return new LoginPage();
        }

        public void errorNotificationWhenMoreThreeInvalidPass() {
            errorNotification.shouldHave(text(errorNotificationWhenMoreThreeInvalidPass)).shouldBe(visible);
        }
}
