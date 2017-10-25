package oop.main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Hashtable;

public class DataProcessing {
	//********************************数据库操作******************************//
	private static boolean connectToDB = false;
	private static String user_name = "root";
	private static String user_pwd = "123456";
	private static String url = "jdbc:mysql://localhost:3306/document";
	private static  Connection cn = null;
	private static Statement stmt = null;
	private static String sql = null;
	private static ResultSet rs = null;
	
	public static Hashtable<String, User> users;
	public static Hashtable<String, Doc> docs;//文件系统哈希表
	
	public static void connectDataBase() {//连接数据库 在mainGUi中使用
		try {
		      String driver = "com.mysql.jdbc.Driver";
		      Class.forName(driver);
		      cn = DriverManager.getConnection(url, user_name, user_pwd);
		      stmt = cn.createStatement();
		      
		      users = new Hashtable<String, User>();
		      docs = new Hashtable<String, Doc>();
		      
		      connectToDB = true;                                    	//否则将导致SQLException异常。
			   }catch (ClassNotFoundException cnfex){
			        System.out.println("Load JDBC/ODBC driver fail:");
			        cnfex.printStackTrace();
			   } catch (SQLException sqlex){
			        System.out.println("Can not connect to SQL server:");
			        sqlex.printStackTrace();
			   } catch (Exception ex){
			        System.out.println("NoClassDefException:");
			        ex.printStackTrace();
			   }
	}

	
	public static void closeDatabase(){
		if( connectToDB ){
			try{
				rs.close();
				stmt.close();
				cn.close();
			}catch(SQLException e){
				System.out.println("关闭时数据库异常");
			}finally{
				connectToDB = false;
			}
	}
	}
	//**************************User类******************************
	public static User search(String name, String password) throws SQLException {
		User temp = null;
		if(  !connectToDB )
			throw new SQLException("Not Conneced to Database");
		
		String sql = "select * from user_info where username = '"+name+"'";
		stmt = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		rs = stmt.executeQuery(sql);
		
		if(rs.next()){
			if(password.equals(rs.getString("password"))){
			String role = rs.getString("role");
			temp = new Administrator(name, password, role);//返回一个对象而已  User类不能实例化
			}
		}
		return temp;
	}
	
	public static User search(String name) throws SQLException {
		User temp = null;
		if(  !connectToDB )
			throw new SQLException("Not Conneced to Database");
		
		String sql = "select * from user_info where username = '"+name+"'";
		stmt = cn.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY
				);
		rs = stmt.executeQuery(sql);
		
		if(rs.next()){	
			String role = rs.getString("role");
			String password = rs.getString("password");
			return new Administrator(name, password, role);//返回一个对象而已  User类不能实例化
	    }else 
	    	return temp;
	}
	
	public static boolean delete(String name) throws SQLException{
		if( !connectToDB){
			throw new SQLException("Not connected to Daatabase");///////////////////////////
		}
		//在数据库删除时同时在哈希表中删除，这样方便于显示左右用户
		users.remove(name);
		
		String sql = "delete from user_info where username = '"+name+"'";
		stmt = cn.createStatement();
		stmt.executeUpdate(sql);
		return true; 
	}
	
	public static boolean update(String name, String password, String role) throws SQLException{
		if( !connectToDB){
			throw new SQLException("Not connected to Daatabase");
		}
		//在数据库删除时同时在哈希表中删除，这样方便于显示左右用户(后面还会加上新的数据)
		users.remove(name);
		
		String sql = "UPDATE user_info set password = '"+password+"' where username = '"+name+"'";  
		stmt = cn.createStatement();
		stmt.executeUpdate(sql);
		sql = "UPDATE user_info set role = '"+role+"' where username = '"+name+"'";  
		stmt = cn.createStatement();
		stmt.executeUpdate(sql);
		return true;
	}
	
	public static boolean insert(String name, String password, String role) throws SQLException{
		if( !connectToDB){
			throw new IllegalStateException("Not connected to Daatabase");
		}
		String sql = "insert into user_info(username,password,role)values('"+name+"','"+password+"','"+role+"')";
		stmt = cn.createStatement();
		stmt.executeUpdate(sql);
		return connectToDB;
	}
	
	public static void getAllUser() throws SQLException {
		stmt = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE); 
		rs = stmt.executeQuery("select * from user_info");
		while(rs.next()){
			String name = rs.getString("username");
			String password = rs.getString("password");
			String role = rs.getString("role");
			if(role.equals("operator")){
				users.put(name, new Operator(name, password, role));}
			else if(role.equals("administrator")){
				users.put(name, new Administrator(name, password, role));//抽象类不能实例化
			}
			else if(role.equals("browser")){
				users.put(name, new Browser(name, password, role));
			}
		}
	}
	//************************************Doc************************
	
	public static Doc searchDoc(String ID) throws SQLException {
		Doc temp = null;
		if(  !connectToDB )
			throw new SQLException("Not Conneced to Database");
		String sql = "select * from doc_info where Id = '"+ID+"'";
		
		stmt = cn.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY
				);
		rs = stmt.executeQuery(sql);
		
		if(rs.next()){
			String ID1 = rs.getString("Id");
			String creator = rs.getString("creator");
			Timestamp timestamp = rs.getTimestamp("timestamp");
			String description = rs.getString("description");
			String filename = rs.getString("filename");
			temp = new Doc(ID1,creator,timestamp,description,filename);
		}
		return temp;
	}
	
	public static boolean insertDoc(String ID, String creator, Timestamp timestamp, 
			String description, String filename) throws SQLException{
		if( !connectToDB){
			throw new SQLException("Not connected to Daatabase");
		}
		String sql = "insert into doc_info(Id,creator,timestamp,description,filename) values('"+ID+"','"+creator+"','"+timestamp+"','"+description+"','"+filename+"')";
		stmt = cn.createStatement();
		stmt.executeUpdate(sql);
		return true;
	}
	//保存到一个hashmap中  保护前几次开发
	public static void getAllDoc() throws SQLException{
		stmt = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE); 
		rs = stmt.executeQuery("select * from doc_info");
		while(rs.next()){
			String id = rs.getString("Id");
			String creator = rs.getString("creator");
			Timestamp timestamp = rs.getTimestamp("timestamp");
			String description = rs.getString("description");
			String filename = rs.getString("filename");
			
			docs.put(id, new Doc(id,creator,timestamp,description,filename));
		}
	}
	
	
}