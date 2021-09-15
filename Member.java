import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Member {
private String ID;
private String name;
private String password;


String server = "jdbc:mysql://140.119.19.73:9306/";
String database ="TG06?useUnicode=true&characterEncoding=UTF-8";
String url= server + database;
String username= "TG06";
String sqlpassword= "bMIEqf";
Connection conn =null;
ResultSet rs;

public void create(String ID,String password) throws SQLException {
	
	try {
		conn= DriverManager.getConnection(url,username,sqlpassword);
		PreparedStatement stat ;
		stat=conn.prepareStatement("INSERT INTO `UserInformation`(`UserID`, `Password`) VALUES(?,?)");
		stat.setString(1, ID);
		stat.setString(2, password);
		int i=stat.executeUpdate();
		if(i==1) 
		{ 
		JOptionPane.showMessageDialog(null, "註冊成功","提示訊息",JOptionPane.WARNING_MESSAGE); 
		}else 
		{ 
		JOptionPane.showMessageDialog(null, "註冊失敗","提示訊息",JOptionPane.ERROR_MESSAGE); 
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
public void verify(String ID,String password) throws SQLException {
	try {
		conn= DriverManager.getConnection(url,username,sqlpassword);
		PreparedStatement stat ;
		stat=conn.prepareStatement("select * from `UserInformation` where UserID=?");
		stat.setString(1, ID);
		
		rs=stat.executeQuery(); 
		if(rs.next()) 
		{ 
		JOptionPane.showMessageDialog(null, "該使用者名稱已經存在", "提示資訊", JOptionPane.WARNING_MESSAGE); 
		}
		else {
			this.create(ID,password);
			/*new First(ID);*/
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
public String login(String ID,String password) throws SQLException{ 
  try { 
	conn= DriverManager.getConnection(url,username,sqlpassword);
    PreparedStatement stat ;
    stat=conn.prepareStatement("select * from `UserInformation` where UserID=? and Password=?"); 
    stat.setString(1, ID); 
    stat.setString(2, password); 
    // ResultSet結果集,大家可以把ResultSet理解成返回一張錶行的結果集 
    rs = stat.executeQuery(); 
  if(rs.next()) { 
		return  "1";
  }
  else { 
    JOptionPane.showMessageDialog(null, "使用者名稱或者密碼錯誤，請重新輸入！", "提示訊息", JOptionPane.ERROR_MESSAGE); 
    return  "0";
  } 
  } 
  catch (SQLException e) { 
	  System.out.println("錯誤訊息:" + e.getMessage());
  }
  finally {
   	conn.close();
   }
return null;
  } 

public String getID() {
	return ID;
}

public String getName() {
	return name;
}

public String getPassword() {
	return password;
}


}
