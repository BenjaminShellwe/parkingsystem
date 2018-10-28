package dao;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBconnection {
	
	private String dbdriver = "com.mysql.jdbc.Driver";
	private String dburl = "jdbc:mysql://localhost/jdbclib?useUnicode=true&characterEncoding=utf8";
	private String dbuserName = "root";
	private String dbpassWord = "admin";
	
	protected Connection conncetion;
	protected Statement statement;
	protected PreparedStatement preparedStatement;
	protected ResultSet resultSet;
	
	public Connection getConnection(){
		try {
			
			Class.forName(dbdriver);	
			//��ݿ�URL��ַ
			conncetion = DriverManager.getConnection(dburl,dbuserName,dbpassWord);
			/*
			if(conncetion != null){
				System.out.println("��ݿ����ӳɹ���");
			}*/
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return conncetion;
	}

	/*public void preParams(String sql,Object... params) throws SQLException{
		preparedStatement = conncetion.prepareStatement(sql);
		//操作数据库
		if(params != null && params.length>0)
		for(int i=0; i<params.length; i++){
			preparedStatement.setObject(i+1, params[i]);
		}
	}*/
	//关闭连接
	public void closeAll() throws SQLException{
		if(resultSet!=null){
			resultSet.close();
		}
		if(statement != null){
			statement.close();
		}
		if(preparedStatement != null){
			preparedStatement.close();
		}
		if(conncetion != null && conncetion.isClosed()){
			conncetion.close();
		}
	}
}

