package Listeners;

import Utilities.LogsUtility;
import Utilities.Utility;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ITestResultListeners implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {


        LogsUtility.LoggerInfo("Test Case " + result.getName() + " has Started");

    }


    @Override
    public void onTestSuccess(ITestResult result) {

        LogsUtility.LoggerInfo("Test Case " + result.getName() + " has Passed Successfully");
    }


    @Override
    public void onTestSkipped(ITestResult result) {
        
        LogsUtility.LoggerInfo("Test Case " + result.getName() + " has been Skipped");

    }
    @Override
    public void onTestFailure(ITestResult result) {

    }


}
