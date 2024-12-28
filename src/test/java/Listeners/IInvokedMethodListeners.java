package Listeners;

import Utilities.LogsUtility;
import Utilities.Utility;
import io.qameta.allure.Allure;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static Factory.DriverFactory.GetThreadDriver;
import static java.sql.DriverManager.getDriver;

public class IInvokedMethodListeners implements IInvokedMethodListener {

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {


        LogsUtility.LoggerInfo("Test Case " + context.getStartDate());

    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        //Utility.takeFullScreenshot(getDriver(), new P02_LandingPage(getDriver()).getNumberOfSelectedProductsOnCart());
        switch (testResult.getStatus()) {
            case ITestResult.FAILURE:
                LogsUtility.LoggerInfo("Test Case " + testResult.getName() + " failed");
                Utility.TakingScreenShot(GetThreadDriver(), testResult.getName()); //validLoginTC-2024-03-03-8-17pm
                break;
            case ITestResult.SUCCESS:
                LogsUtility.LoggerInfo("Test Case " + testResult.getName() + " passed");
                break;
            case ITestResult.SKIP:
                LogsUtility.LoggerInfo("Test Case " + testResult.getName() + " skipped");
                break;
        }
        try {
            File logFile = Utility.GetLatestFile(LogsUtility.Logs_Path);
            assert logFile != null;
            Allure.addAttachment("logs.log", Files.readString(Path.of(logFile.getPath())));
        } catch (IOException e) {
            LogsUtility.LoggerError(e.getMessage());
        }

    }
}
