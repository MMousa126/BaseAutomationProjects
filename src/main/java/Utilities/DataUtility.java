package Utilities;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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

    public static void writePropertiesDataToFile(String propertiesFilename, String key, String value) {
        Properties properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream(TestData_Path + propertiesFilename + ".properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        try (FileOutputStream outputStream = new FileOutputStream(TestData_Path + propertiesFilename + ".properties")) {
            properties.setProperty(key, value);
            properties.store(outputStream, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public static void writeDataToExcelFile(String excelFilename, String sheetName, int x, int y, String value) {
        String filePath = TestData_Path + excelFilename + ".xlsx";

        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                sheet = workbook.createSheet(sheetName);
            }

            Row row = sheet.getRow(x);
            if (row == null) {
                row = sheet.createRow(x);
            }

            Cell cell = row.getCell(y);
            if (cell == null) {
                cell = row.createCell(y);
            }

            cell.setCellValue(value);

            // Write changes back to the file
            try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
                workbook.write(fileOutputStream);
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error reading or writing file: " + filePath);
            e.printStackTrace();
        }
    }

    /**
     * Creates an Excel file with configurable parameters.
     *
     * @param filename  The file path where the Excel file will be saved.
     * @param sheetName The name of the sheet to be created.
     * @param headers   Array of header values to be written to the first row.
     * @param data      2D array of data to be written to the subsequent rows.
     */
    public static void createExcelFile(String filename, String sheetName, String[] headers, String[][] data) {
        // Create a new workbook
        Workbook workbook = new XSSFWorkbook();

        // Create a sheet with the specified name
        Sheet sheet = workbook.createSheet(sheetName);

        // Create the header row
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Fill in the data rows
        for (int rowIndex = 0; rowIndex < data.length; rowIndex++) {
            Row row = sheet.createRow(rowIndex + 1); // Start from the second row
            for (int colIndex = 0; colIndex < data[rowIndex].length; colIndex++) {
                Cell cell = row.createCell(colIndex);
                cell.setCellValue(data[rowIndex][colIndex]);
            }
        }

        // Save the workbook to the specified file path
        try  {
            FileOutputStream fileOut = new FileOutputStream(TestData_Path + filename + ".xlsx");
            workbook.write(fileOut);
        } catch (IOException e) {
            System.err.println("Error while creating Excel file: " + e.getMessage());
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                System.err.println("Error while closing the workbook: " + e.getMessage());
            }
        }
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
