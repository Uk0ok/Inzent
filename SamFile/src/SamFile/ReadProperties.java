package SamFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReadProperties {
	public static String DBDriver1;
	public static String DBDriver2;
	public static String DBUrl1;
	public static String DBUrl2;
	public static String DBId1;
	public static String DBPwd1;
	public static String DBId2;
	public static String DBPwd2;
	public static String Query1;
	public static String Query2;
	public static String Query3;
	public static String Query4;
	public static String FilePath;
	public static String Delimiter;
	
	public static void read() {
		Properties prop1 = new Properties();
		Properties prop2 = new Properties();
		Properties prop3 = new Properties();
		
		try {
			prop1.load(new FileInputStream("conf/conf.properties"));
			prop2.load(new FileInputStream("conf/DB1conf.properties"));
			prop3.load(new FileInputStream("conf/DB2conf.properties"));
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		
		DBDriver1 = prop2.getProperty("DBDRIVER1");
		DBDriver2 = prop3.getProperty("DBDRIVER2");
		DBUrl1 = prop2.getProperty("DBURL1");
		DBUrl2 = prop3.getProperty("DBURL2");
		DBId1 = prop2.getProperty("DBID1");
		DBPwd1 = prop2.getProperty("DBPWD1");
		DBId2 = prop3.getProperty("DBID2");
		DBPwd2 = prop3.getProperty("DBPWD2");
		Query1 = prop1.getProperty("QUERY1");
		Query2 = prop1.getProperty("QUERY2");
		Query3 = prop1.getProperty("QUERY3");
		Query4 = prop1.getProperty("QUERY4");
		FilePath = prop1.getProperty("FILEPATH");
		Delimiter = prop1.getProperty("DELIMITER");
	}
}
