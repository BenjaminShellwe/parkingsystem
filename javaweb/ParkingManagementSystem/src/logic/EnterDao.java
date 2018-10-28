package logic;

import infos.EnterParking;
import infos.EnterParking;
import infos.EnterParking;
import infos.ParkingInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.DBconnection;

public class EnterDao extends DBconnection{
	Connection conn = null;
	PreparedStatement prep = null;
	ResultSet resultSet = null;
	//获取所有用户信息
	public List<EnterParking> getAllEnter() throws SQLException{
		List<EnterParking> enterParkings = new ArrayList<EnterParking>();
		String sql = "select * from enterpark";
		conn = getConnection();
		try {
			prep = conn.prepareStatement(sql);
			resultSet = prep.executeQuery();
			while(resultSet.next()){
				EnterParking park = new EnterParking();
				park.setId(resultSet.getInt("id"));
				park.setEncardno(resultSet.getString("encardno"));
				park.setEncarno(resultSet.getString("encarno"));
				park.setEnport(resultSet.getString("enport"));
				park.setEntime(resultSet.getString("entime"));
				park.setPosition(resultSet.getString("position"));
				enterParkings.add(park);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return enterParkings;
	}
	
	public int addEnterparking(EnterParking enparking) throws SQLException{
		int result = 0;
		String sql = "insert into enterpark(encardno, encarno, enport, entime, position) values(?, ?, ?, ?, ?)";
		conn = getConnection();
		prep = conn.prepareStatement(sql);
		prep.setString(1, enparking.getEncardno());
		prep.setString(2, enparking.getEncarno());
		prep.setString(3, enparking.getEnport());
		prep.setString(4, enparking.getEntime());
		prep.setString(5, enparking.getPosition());
		result = prep.executeUpdate();
		closeAll();
		return result;
	}
	
	public int delEnterParking(int id) throws SQLException{
		int result = 0;
		String sql = "delete from enterpark where id=?";
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
	
	public EnterParking toupdateEnterParking(int id) throws SQLException{
		EnterParking park = null;
		String sql = "select * from enterpark where id = ?";
		conn = getConnection();
		try {
			prep = conn.prepareStatement(sql);
			prep.setInt(1, id);
			resultSet = prep.executeQuery();
			while(resultSet.next()){
				park = new EnterParking();
				park.setId(resultSet.getInt("id"));
				park.setEncardno(resultSet.getString("encardno"));
				park.setEncarno(resultSet.getString("encarno"));
				park.setEnport(resultSet.getString("enport"));
				park.setEntime(resultSet.getString("entime"));
				park.setPosition(resultSet.getString("position"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return park;
	}
	
	public int updateEnterParking(EnterParking enparking){
		int result = 0;
		String sql = "update enterpark set encardno=?, encarno=?, enport=?, entime=?, position=? where id=?";
		try {
			conn=getConnection();
			prep = conn.prepareStatement(sql);
			prep.setString(1, enparking.getEncardno());
			prep.setString(2, enparking.getEncarno());
			prep.setString(3, enparking.getEnport());
			prep.setString(4, enparking.getEntime());
			prep.setString(5, enparking.getPosition());
			prep.setInt(6, enparking.getId());
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
