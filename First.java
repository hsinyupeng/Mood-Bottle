import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class First {
private String ID;
private ResultSet rs;
First(String ID) throws SQLException{
	this.ID=ID;	
	FirstTime();
}

public void FirstTime() throws SQLException {
	String server = "jdbc:mysql://140.119.19.73:9306/";
	String database ="TG06?useUnicode=true&characterEncoding=UTF-8";
	String url= server + database;
	String username= "TG06";
	String sqlpassword= "bMIEqf";
	Connection conn =null;	
	ResultSet result ;
	try {
		conn= DriverManager.getConnection(url,username,sqlpassword);
		PreparedStatement stat ;
		PreparedStatement stat2 ;
		stat=conn.prepareStatement("SELECT `FirstTime` FROM `UserInformation` WHERE `UserID`=?");
		stat2=conn.prepareStatement("UPDATE `UserInformation` SET `FirstTime`=? WHERE `UserID`=?");
		stat.setString(1, ID);
		rs=stat.executeQuery();
		result = stat.getResultSet();
		if(rs.next()) {
			if(result.getInt(1)==0) {
				System.out.println("First");
				stat2.setInt(1, 1);
				stat2.setString(2, ID);
				stat2.execute();
				
			}
			
		}
		    
     }
     catch(SQLException e){
     	System.out.println("錯誤訊息:" + e.getMessage());
     	System.out.println("資料庫處理錯誤!!!");	
     }
     finally {
     	conn.close();
     }
}

}
