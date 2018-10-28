package logic;

import infos.Power;
import infos.Power;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.DBconnection;

public class ManagePowerDao extends DBconnection {
	Connection conn = null;
	PreparedStatement prep = null;
	ResultSet resultSet = null;
	//获取所有权限信息
	public List<Power> getAllPower() throws SQLException{
		List<Power> powers = new ArrayList<Power>();
		String sql = "select * from power";
		conn = getConnection();
		try {
			prep = conn.prepareStatement(sql);
			resultSet = prep.executeQuery();
			while(resultSet.next()){
				Power power = new Power();
				power.setId(resultSet.getInt("id"));
				power.setNumber(resultSet.getString("number"));
				power.setPassword(resultSet.getString("password"));
				power.setName(resultSet.getString("name"));
				power.setPower(resultSet.getString("power"));
				powers.add(power);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return powers;
	}
	//添加权限信息
	public int addPower(Power power) throws SQLException{
		int result = 0;
		String sql = "insert into power(number, password, name, power) values(?, ?, ?, ?)";
		conn = getConnection();
		prep = conn.prepareStatement(sql);
		prep.setString(1, power.getNumber());
		prep.setString(2, power.getPassword());
		prep.setString(3, power.getName());
		prep.setString(4, power.getPower());
		result = prep.executeUpdate();
		closeAll();
		return result;
	}
	//根据id删除权限信息
	public int delPower(int id) throws SQLException{
		int result = 0;
		String sql = "delete from power where id=?";
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
	public Power toupdatePower(int id) throws SQLException{
		Power power = null;
		String sql = "select * from power where id = ?";
		conn = getConnection();
		try {
			prep = conn.prepareStatement(sql);
			prep.setInt(1, id);
			resultSet = prep.executeQuery();
			while(resultSet.next()){
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
	//修改在数据库的权限信息
		public int updateParking(Power power){
			int result = 0;
			String sql = "update power set number=?, password=?, name=?, power=? where id=?";
			try {
				conn=getConnection();
				prep = conn.prepareStatement(sql);
				prep.setString(1, power.getNumber());
				prep.setString(2, power.getPassword());
				prep.setString(3, power.getName());
				prep.setString(4, power.getPower());
				prep.setInt(5, power.getId());
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
