package steps;

import Assertion.AssertUtility;
import Factory.DriverFactory;
import Utilities.DataUtility;
import Utilities.LogsUtility;
import Utilities.Utility;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

import static Factory.DriverFactory.GetThreadDriver;

public class EmployeeLogin {

    /*************************************************************************************************/


    @Given("user is on the CRM Portal")
    public void user_is_on_the_crm_portal() {

        DriverFactory.SetupThreadDriver("edge");
        GetThreadDriver().get("https://easyupload.io/");
        GetThreadDriver().navigate().refresh();
        Utility.takeFullDesktopScreenshot("newphoto");

    }
    @When("user enters username, password and click on Login")
    public void user_enters_username_password_and_click_on_login() {


    }
    @Then("user should be logged in successfully and main page should open successfully")
    public void user_should_be_logged_in_successfully_and_main_page_should_open_successfully() {

    }

    @After
    public void tearDown() {
        try {
            DriverFactory.QuitThreadDriver();
            LogsUtility.LoggerInfo("Browser closed successfully.");
        } catch (Exception e) {
            LogsUtility.LoggerError("Error while closing the browser: " + e.getMessage());
        }
    }

}
