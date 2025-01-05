package runner;

import Factory.DriverFactory;
import Factory.DriverFactoryCucumber;
import io.cucumber.testng.CucumberOptions;

// why extending from DriverFactory: so the runner would be able to see the drivers like chrome driver and firefox
/* and the driver is in the different location we didn't add like this

public class TestRunner extends AbstractTestNGCucumberTests
 */
@CucumberOptions(features = "src/test/java/features"
        ,glue = {"steps"}
        ,plugin = {"pretty","html:target/AllureResults"})
public class TestRunner extends DriverFactoryCucumber {


}