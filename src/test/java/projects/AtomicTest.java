package test.java.projects;

import org.junit.Assert;
import org.junit.Test;
import test.java.SauceTestSynchBase;
import test.java.data.Person;
import test.java.data.Product;
import test.java.exceptions.PageValidationException;
import test.java.pages.*;

public class AtomicTest extends SauceTestSynchBase {
    public static Product swag1 = new Product();
    public static Product swag2 = new Product();

    @Test
    public void login() {
        HomePage homePage = HomePage.visit();

        try {
            homePage.loginSuccessfully();
        } catch (PageValidationException e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void addItemsToCart() {
        // Assume signing in is optional in this app,
        // so visiting Inventory page directly

        InventoryPage inventoryPage = InventoryPage.visit();

        Product product1 = inventoryPage.selectProduct();
        Product product2 = inventoryPage.selectProduct();

        ShoppingCartPage shoppingCartPage = new ShoppingCartPage();
        try {
            shoppingCartPage.validateItem(product1);
            shoppingCartPage.validateItem(product2);
        } catch (PageValidationException e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void checkoutFromCart() {
        InventoryPage inventoryPage = InventoryPage.visit();
        inventoryPage.selectProduct(swag1);
        inventoryPage.selectProduct(swag2);

        ShoppingCartPage shoppingCartPage = ShoppingCartPage.visit();
        try {
            shoppingCartPage.checkOutSuccessfully();
        } catch (PageValidationException e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void submitUserInformationInCheckout() {
        InventoryPage inventoryPage = InventoryPage.visit();
        inventoryPage.selectProduct(swag1);
        inventoryPage.selectProduct(swag2);

        // Can skip the shopping cart and go directly to Info Page
        InformationPage informationPage = InformationPage.visit();
        try {
            Person person = Person.beverlyHills();
            informationPage.addInformationSuccessfully(person);
        } catch (PageValidationException e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void finishOrderFromOverviewPage() {
        InventoryPage inventoryPage = InventoryPage.visit();
        inventoryPage.selectProduct(swag1);
        inventoryPage.selectProduct(swag2);

        // Can't skip information page before getting to overview page
        InformationPage informationPage = InformationPage.visit();
        informationPage.addInformationSuccessfully();

        OverviewPage overviewPage = new OverviewPage();
        try {
            overviewPage.finishSuccessfully();
        } catch (PageValidationException e) {
            Assert.fail(e.toString());
        }
    }
}
