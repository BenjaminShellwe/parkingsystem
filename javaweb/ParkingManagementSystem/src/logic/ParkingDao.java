package logic;

import infos.ParkingInfo;
import infos.ParkingInfo;
import infos.UserPacket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.DBconnection;

public class ParkingDao extends DBconnection {
	Connection conn = null;
	PreparedStatement prep = null;
	ResultSet resultSet = null;
	//获取所有用户信息
	public List<ParkingInfo> getAllParking() throws SQLException{
		List<ParkingInfo> parks = new ArrayList<ParkingInfo>();
		String sql = "select * from parking";
		conn = getConnection();
		try {
			prep = conn.prepareStatement(sql);
			resultSet = prep.executeQuery();
			while(resultSet.next()){
				ParkingInfo park = new ParkingInfo();
				park.setId(resultSet.getInt("id"));
				park.setParkname(resultSet.getString("parkname"));
				park.setParktotal(resultSet.getString("parktotal"));
				park.setEnterport(resultSet.getString("enterport"));
				park.setExitport(resultSet.getString("exitport"));
				park.setParktel(resultSet.getString("parktel"));
				parks.add(park);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return parks;
	}
	//添加停车场信息
	public int addParking(ParkingInfo parking) throws SQLException{
		int result = 0;
		String sql = "insert into parking(parkname, parktotal, enterport, exitport, parktel) values(?, ?, ?, ?, ?)";
		conn = getConnection();
		prep = conn.prepareStatement(sql);
		prep.setString(1, parking.getParkname());
		prep.setString(2, parking.getParktotal());
		prep.setString(3, parking.getEnterport());
		prep.setString(4, parking.getExitport());
		prep.setString(5, parking.getParktel());
		result = prep.executeUpdate();
		closeAll();
		return result;
	}
	//根据id删除停车场信息
	public int delParking(int id) throws SQLException{
		int result = 0;
		String sql = "delete from parking where id=?";
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
	public ParkingInfo toupdateParking(int id) throws SQLException{
		ParkingInfo parking = null;
		String sql = "select * from parking where id = ?";
		conn = getConnection();
		try {
			prep = conn.prepareStatement(sql);
			prep.setInt(1, id);
			resultSet = prep.executeQuery();
			while(resultSet.next()){
				parking = new ParkingInfo();
				parking.setId(resultSet.getInt("id"));
				parking.setParkname(resultSet.getString("parkname"));
				parking.setParktotal(resultSet.getString("parktotal"));
				parking.setEnterport(resultSet.getString("enterport"));
				parking.setExitport(resultSet.getString("exitport"));
				parking.setParktel(resultSet.getString("parktel"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return parking;
	}
	//修改在数据库的停车场信息
		public int updateParking(ParkingInfo parking){
			int result = 0;
			String sql = "update parking set parkname=?, parktotal=?, enterport=?, exitport=?, parktel=? where id=?";
			try {
				conn=getConnection();
				prep = conn.prepareStatement(sql);
				prep.setString(1, parking.getParkname());
				prep.setString(2, parking.getParktotal());
				prep.setString(3, parking.getEnterport());
				prep.setString(4, parking.getExitport());
				prep.setString(5, parking.getParktel());
				prep.setInt(6, parking.getId());
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
	