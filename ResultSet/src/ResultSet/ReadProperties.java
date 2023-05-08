package ResultSet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReadProperties {
	public static String DBDriver19c;
	public static String DBDriver11g;
	public static String DBUrl19c;
	public static String DBUrl11g;
	public static String DBId19c;
	public static String DBPwd19c;
	public static String DBId11g;
	public static String DBPwd11g;
	public static String QuerySelectElement;
	public static String QuerySelectContentElement;
	public static String QueryInsertElement;
	public static String QueryInsertContentElement;
	public static String FilePathElement;
	public static String FilePathContentElement;
	public static String Delimiter;
	
	public static void read() {
		Properties prop1 = new Properties();
		Properties prop2 = new Properties();
		Properties prop3 = new Properties();
		
		try {
			prop1.load(new FileInputStream("conf/conf.properties"));
			prop2.load(new FileInputStream("conf/DB19cconf.properties"));
			prop3.load(new FileInputStream("conf/DB11gconf.properties"));
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		
		DBDriver19c = prop2.getProperty("DBDRIVER19C");
		DBDriver11g = prop3.getProperty("DBDRIVER11G");
		DBUrl19c = prop2.getProperty("DBURL19C");
		DBUrl11g = prop3.getProperty("DBURL111G");
		DBId19c = prop2.getProperty("DBID19C");
		DBPwd19c = prop2.getProperty("DBPWD19C");
		DBId11g = prop3.getProperty("DBID11G");
		DBPwd11g = prop3.getProperty("DBPWD11G");
		QuerySelectElement = prop1.getProperty("QUERYSELECTELEMENT");
		QuerySelectContentElement = prop1.getProperty("QUERYSELECTCONTENTELEMENT");
		QueryInsertElement = prop1.getProperty("QUERYINSERTELEMENT");
		QueryInsertContentElement = prop1.getProperty("QUERYINSERTCONTENTELEMENT");
		FilePathElement = prop1.getProperty("FILEPATHELEMENT");
		FilePathContentElement = prop1.getProperty("FILEPATHCONTENTELEMENT");
		Delimiter = prop1.getProperty("DELIMITER");
	}
}
