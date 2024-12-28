package Assertion;

import org.testng.asserts.SoftAssert;

public class AssertUtility {

    private final SoftAssert softAssert;




    public AssertUtility(SoftAssert softAssert) {
        this.softAssert = softAssert;
    }


    public AssertUtility assertTrue(boolean condition){

        softAssert.assertTrue(condition);
        return this ;
    }

    public AssertUtility assertFalse(boolean condition){

        softAssert.assertFalse(condition);
        return this ;
    }

    public AssertUtility assertEqual(String actual, String expected){

        softAssert.assertEquals(actual,expected);
        return this ;
    }


    public void assertAll(){

        softAssert.assertAll();
    }
}
