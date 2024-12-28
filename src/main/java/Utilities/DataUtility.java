package Utilities;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;

import java.io.*;
import java.nio.file.Paths;
import java.security.PublicKey;
import java.util.Properties;

// this class concerns with the Data like handling data from external files
public class DataUtility {

    public static final String TestData_Path = "src/test/resources/TestData/";


    /* Reading From Json File */
    public static String GetJsonDataFromFile(String jsonfilename, String field) {

        try {
            FileReader reader = new FileReader(TestData_Path + jsonfilename + ".json");

            JsonElement jsonElement = JsonParser.parseReader(reader);

            return jsonElement.getAsJsonObject().get(field).getAsString();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }


    /* Reading From Properties File */
    public static String GetPropertiesDataFromFile(String propertiesfilename, String key) {

        try {

            Properties properties = new Properties();
            properties.load(new FileInputStream(TestData_Path + propertiesfilename + ".properties"));
            return properties.getProperty(key);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }


    private static FileInputStream getInputStream(String filename){

        String fileName = (TestData_Path + filename + ".xlsx");
        FileInputStream file = null;
        File srcfile = new File(fileName);
        try {
            file = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return file;
    }

    public static Object[][] GetExcelDataFromFile(String excelfilename , String sheetname){

        FileInputStream file = getInputStream(excelfilename);
        String[][] strArray;
        try {
            XSSFWorkbook wb = new XSSFWorkbook(file);
            XSSFSheet sheet = wb.getSheet(sheetname);

            int noOfRows = sheet.getPhysicalNumberOfRows();
            int noOfCols = sheet.getRow(0).getLastCellNum();

            strArray = new String[noOfRows][noOfCols];

            for (int i = 0; i < noOfRows; i++) {

                for (int j = 0; j < noOfCols; j++) {
                    XSSFRow row = sheet.getRow(i);
                    strArray[i][j] = row.getCell(j).toString();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
        return strArray;
    }


    public static String GetSpecificFieldFromExcelFile(String excelfilename , String sheetname,int x, int y){

        Object[][] colsRow = (String[][]) GetExcelDataFromFile(excelfilename,sheetname);

        return (String) colsRow[x][y];


    }

    public static void WriteDataIntoJsonFile(String filename,String key,String value){

        JSONObject obj = new JSONObject();
        obj.put(key,value);

        try {
            FileWriter file = new FileWriter(TestData_Path + filename + ".json");
            file.write(obj.toString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getProjectRoot() {
        return Paths.get(".").toAbsolutePath().normalize().toString();
    }

    public static String getTestDataFolder(String fileNameWithExtention) {
        return getProjectRoot() + fileSeparator() + TestData_Path + fileNameWithExtention;
    }

    public static String fileSeparator() {
        return System.getProperty("file.separator");
    }

}
