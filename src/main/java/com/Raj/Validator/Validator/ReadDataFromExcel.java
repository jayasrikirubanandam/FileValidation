package com.Raj.Validator.Validator;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;

import com.jdbc.Utility.JDBCUtility;

public class ReadDataFromExcel {

	private static final int STRING = 0;
	private static final int NUMERIC = 0;
	private static final int BOOLEAN = 0;

	//public static void main(String[] args) throws IOException, SQLException {

	public static void UploadFileAndValidate(String filelocAddr) throws IOException, SQLException{
		
		String filePath = filelocAddr ;
		String url ="JDBC:MySql://${MYSQL_HOST:LOCALHOST}:3306/mySQL_Learning/Student";
		String userName="root";
		String userPassword = "BeStrong23!";


		Connection connect = null;
		Statement stmnt = null;
		ResultSet rs = null;


		FileInputStream fis = new FileInputStream(filePath);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheetAt(0);
		DataFormatter dataFormatter = new DataFormatter();

		try {
			connect = JDBCUtility.getDBConnection();
			if(connect!= null) {
				stmnt = connect.createStatement();
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}


		//reading the first cell of each row and to find whether they are present in db.
		Iterator<Row> row = sheet.iterator();
		Row headerRow= row.next();

		Cell newHeaderCell = headerRow.createCell(headerRow.getLastCellNum(),CellType.STRING);
		newHeaderCell.setCellValue("VALIDATION");
		while(row.hasNext()) {
			Row nextRow = row.next();


			String cellValue = dataFormatter.formatCellValue(nextRow.getCell(0));
			System.out.println("CELL VALUE "+ cellValue);
			if (!cellValue.isEmpty()) {
				if(stmnt!=null) {
					String query = "SELECT COUNT(*) FROM Student WHERE StudentName = ?";
					PreparedStatement preparedStatement = connect.prepareStatement(query);
					preparedStatement.setString(1, cellValue);
					ResultSet resultSet = preparedStatement.executeQuery();

					int lastCellNum = nextRow.getLastCellNum();
					Cell newCell = nextRow.createCell(lastCellNum, CellType.STRING);
						

					if (resultSet.next() && resultSet.getInt(1) > 0) {
						System.out.println("Value " + cellValue + " exists in the database.");
						newCell.setCellValue(" exists in the database.");


					} else {
						System.out.println("Value " + cellValue + " does not exist in the database."); 
						newCell.setCellValue(" does not exist in the database.");

					}
				}

			}

		}

		//to write the output in a excel sheet 
		try(FileOutputStream fos = new FileOutputStream("/Users/jayk/Documents/StudentValidation.xlsx")){
			workbook.write(fos);
			
		}
		catch(IOException io) {
			io.printStackTrace();
		}

	}
	} 

//}
