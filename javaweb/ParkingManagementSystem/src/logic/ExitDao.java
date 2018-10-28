package logic;

import infos.EnterParking;
import infos.ExitParking;
import infos.ExitParking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.DBconnection;

public class ExitDao extends DBconnection{
	Connection conn = null;
	PreparedStatement prep = null;
	ResultSet resultSet = null;
	//获取所有出口车辆信息
	public List<ExitParking> getAllExit() throws SQLException{
		List<ExitParking> exitParkings = new ArrayList<ExitParking>();
		String sql = "select * from exitpark";
		conn = getConnection();
		try {
			prep = conn.prepareStatement(sql);
			resultSet = prep.executeQuery();
			while(resultSet.next()){
				ExitParking park = new ExitParking();
				park.setId(resultSet.getInt("id"));
				park.setExcardno(resultSet.getString("excardno"));
				park.setExcarno(resultSet.getString("excarno"));
				park.setExport(resultSet.getString("export"));
				park.setEntime(resultSet.getString("entime"));
				park.setExtime(resultSet.getString("extime"));
				park.setTime(resultSet.getString("time"));
				park.setMoney(resultSet.getString("money"));
				exitParkings.add(park);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return exitParkings;
	}
	
	public int addExitparking(ExitParking exitparking) throws SQLException{
		int result = 0;
		String sql = "insert into exitpark(excardno, excarno, export, entime, extime, time, money) values(?, ?, ?, ?, ?, ?, ?)";
		conn = getConnection();
		prep = conn.prepareStatement(sql);
		prep.setString(1, exitparking.getExcardno());
		prep.setString(2, exitparking.getExcarno());
		prep.setString(3, exitparking.getExport());
		prep.setString(4, exitparking.getEntime());
		prep.setString(5, exitparking.getExtime());
		prep.setString(6, exitparking.getTime());
		prep.setString(7, exitparking.getMoney());
		result = prep.executeUpdate();
		closeAll();
		return result;
	}
	
	public int delExitParking(int id) throws SQLException{
		int result = 0;
		String sql = "delete from exitpark where id=?";
		conn = getConnection();
		try {
			prep = conn.prepareStatement(sql);
			prep.setInt(1, id);
			result = prep.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return result;
	}
	//从数据中取出数据更新
	public ExitParking toupdateExitParking(int id) throws SQLException{
		ExitParking park = null;
		String sql = "select * from exitpark where id = ?";
		conn = getConnection();
		try {
			prep = conn.prepareStatement(sql);
			prep.setInt(1, id);
			resultSet = prep.executeQuery();
			while(resultSet.next()){
				park = new ExitParking();
				park.setId(resultSet.getInt("id"));
				park.setExcardno(resultSet.getString("excardno"));
				park.setExcarno(resultSet.getString("excarno"));
				park.setExport(resultSet.getString("export"));
				park.setEntime(resultSet.getString("entime"));
				park.setExtime(resultSet.getString("extime"));
				park.setTime(resultSet.getString("time"));
				park.setMoney(resultSet.getString("money"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return park;
	}
	
	public int updateExitParking(ExitParking exitparking){
		int result = 0;
		String sql = "update exitpark set excardno=?, excarno=?, export=?, entime=?, extime=?, time=?, money=? where id=?";
		try {
			conn=getConnection();
			prep = conn.prepareStatement(sql);
			prep.setString(1, exitparking.getExcardno());
			prep.setString(2, exitparking.getExcarno());
			prep.setString(3, exitparking.getExport());
			prep.setString(4, exitparking.getEntime());
			prep.setString(5, exitparking.getExtime());
			prep.setString(6, exitparking.getTime());
			prep.setString(7, exitparking.getMoney());
			prep.setInt(8, exitparking.getId());
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
}
