package logic;

import infos.ParkingCharge;
import infos.ParkingCharge;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.DBconnection;

public class ChargeDao extends DBconnection{
	Connection conn = null;
	PreparedStatement prep = null;
	ResultSet resultSet = null;
	//获取所有收费信息
	public List<ParkingCharge> getAllCharge() throws SQLException{
		List<ParkingCharge> parkCharges = new ArrayList<ParkingCharge>();
		String sql = "select * from parkcharge";
		conn = getConnection();
		try {
			prep = conn.prepareStatement(sql);
			resultSet = prep.executeQuery();
			while(resultSet.next()){
				ParkingCharge park = new ParkingCharge();
				park.setId(resultSet.getInt("id"));
				park.setCardno(resultSet.getString("cardno"));
				park.setCartype(resultSet.getString("cartype"));
				park.setTime(resultSet.getString("time"));
				park.setPrice(resultSet.getString("price"));
				park.setMoney(resultSet.getString("money"));
				parkCharges.add(park);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return parkCharges;
	}
	public int addParkingCharge(ParkingCharge charge) throws SQLException{
		int result = 0;
		String sql = "insert into parkcharge(cardno, cartype, time, price, money) values(?, ?, ?, ?, ?)";
		conn = getConnection();
		prep = conn.prepareStatement(sql);
		prep.setString(1, charge.getCardno());
		prep.setString(2, charge.getCartype());
		prep.setString(3, charge.getTime());
		prep.setString(4, charge.getPrice());
		prep.setString(5, charge.getMoney());
		result = prep.executeUpdate();
		closeAll();
		return result;
	}
	
	public int delParkingCharge(int id) throws SQLException{
		int result = 0;
		String sql = "delete from parkcharge where id=?";
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
}
