package Properties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Utilities {
	public static final String url="jdbc:mysql://localhost:3306/bank";
	public static final String user="root";
	public static final String pwd="root";
    public static Connection databaseConnecting() {
    	 Connection con=null;
    	 try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection(url,user,pwd);
//			System.out.println("DataBase Connected!!");
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 return con;
    	 }
}
