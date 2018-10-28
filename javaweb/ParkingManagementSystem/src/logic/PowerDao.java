package logic;



import infos.FreeTable;
import infos.Power;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import dao.DBconnection;

public class PowerDao extends DBconnection{
	Connection conn = null;
	ResultSet resultSet = null;
	public Power login(String number) throws SQLException{
		Power power = null;
		try {
			conn = getConnection();
			
			String sql = "select * from power where number = ?";
			PreparedStatement prep = conn.prepareStatement(sql);
			prep.setString(1, number);
			resultSet = prep.executeQuery();
			if(resultSet.next()){
				power = new Power();
				power.setId(resultSet.getInt("id"));
				power.setNumber(resultSet.getString("number"));
				power.setPassword(resultSet.getString("password"));
				power.setName(resultSet.getString("name"));
				power.setPower(resultSet.getString("power"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return power;
	}
	//从数据库取出所有列表信息
	public ArrayList<FreeTable> getAll() throws SQLException{
		ArrayList<FreeTable> frees = new ArrayList<FreeTable>();;
		conn = getConnection();
		String sql = "select * from freetable";
		try {
			PreparedStatement prep = conn.prepareStatement(sql);
			resultSet = prep.executeQuery();
			while(resultSet.next()){
				FreeTable free = new FreeTable();
				free.setId(resultSet.getInt("id"));
				free.setNumber(resultSet.getString("number"));
				free.setCardtype(resultSet.getString("cardtype"));
				free.setCartype(resultSet.getString("cartype"));
				free.setTime(resultSet.getString("time"));
				free.setSum(resultSet.getString("sum"));
				frees.add(free);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeAll();
		}
		return frees;
	}
	//添加收费标准
	public int addFree(FreeTable obj){
		int result = 0;
		String sql = "insert into freetable(number,cardtype,cartype,time,sum) values(?,?,?,?,?)";
		conn = getConnection();
		try {
			PreparedStatement prep = conn.prepareStatement(sql);
			prep.setString(1, obj.getNumber());
			prep.setString(2, obj.getCardtype());
			prep.setString(3, obj.getCartype());
			prep.setString(4, obj.getTime());
			prep.setString(5, obj.getSum());
			result = prep.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	//删除收费标准
	public int delFree(int id){
		int result = 0;
		String sql = "delete from freetable where id=?";
		conn = getConnection();
		try {
			PreparedStatement prep = conn.prepareStatement(sql);
			prep.setInt(1, id);
			result = prep.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				closeAll();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	//获得数据库信息
	public FreeTable updateFree(int id){
		FreeTable freeTable = null;
		String sql = "select * from freetable where id=?";
		conn = getConnection();
		try {
			PreparedStatement prep = conn.prepareStatement(sql);
			prep.setInt(1, id);
			resultSet = prep.executeQuery();
			if(resultSet.next()){
				freeTable = new FreeTable();
				freeTable.setId(resultSet.getInt("id"));
				freeTable.setNumber(resultSet.getString("number"));
				freeTable.setCardtype(resultSet.getString("cardtype"));
				freeTable.setCartype(resultSet.getString("cartype"));
				freeTable.setTime(resultSet.getString("time"));
				freeTable.setSum(resultSet.getString("sum"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				closeAll();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return freeTable;
	}
	//更新收费标准
	public int updateFree(FreeTable free){
		int result = 0;
		conn = getConnection();
		String sql = "update freetable set number = ?, cardtype = ?, cartype = ?, time = ?, sum = ? where id = ?";
		try {
			PreparedStatement prep = conn.prepareStatement(sql);
			prep.setString(1, free.getNumber());
			prep.setString(2, free.getCardtype());
			prep.setString(3, free.getCartype());
			prep.setString(4, free.getTime());
			prep.setString(5, free.getSum());
			prep.setInt(6, free.getId());
			result = prep.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
