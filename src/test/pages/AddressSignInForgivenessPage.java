package test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AddressSignInForgivenessPage extends BaseForgivenessPage {

    private By email = By.id("session_email");

    public static AddressSignInForgivenessPage visit(WebDriver driver) {
        driver.get("http://a.testaddressbook.com/");
        driver.findElement(By.id("sign-in")).click();
        return new AddressSignInForgivenessPage(driver);
    }

    public AddressSignInForgivenessPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillEmail(String name) {
        sendKeys(email, name);
    }
}
