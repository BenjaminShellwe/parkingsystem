package logic;

import infos.UserPacket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.Request;

import dao.DBconnection;

public class UserDao extends DBconnection{
	Connection conn = null;
	PreparedStatement prep = null;
	ResultSet resultSet = null;
	//获取所有用户信息
	public List<UserPacket> getAllUser() throws SQLException{
		List<UserPacket> users = new ArrayList<UserPacket>();
		String sql = "select * from users";
		conn = getConnection();
		try {
			prep = conn.prepareStatement(sql);
			resultSet = prep.executeQuery();
			while(resultSet.next()){
				UserPacket user = new UserPacket();
				user.setId(resultSet.getInt("id"));
				user.setName(resultSet.getString("name"));
				user.setSex(resultSet.getString("sex"));
				user.setTel(resultSet.getString("tel"));
				user.setCardno(resultSet.getString("cardno"));
				user.setCartype(resultSet.getString("cartype"));
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return users;
	}
	//添加用户信息
	public int addUser(UserPacket user) throws SQLException{
		int result = 0;
		String sql = "insert into users(name,sex,tel,cardno,cartype) values(?, ?, ?, ?, ?)";
		conn = getConnection();
		prep = conn.prepareStatement(sql);
		prep.setString(1, user.getName());
		prep.setString(2, user.getSex());
		prep.setString(3, user.getTel());
		prep.setString(4, user.getCardno());
		prep.setString(5, user.getCartype());
		result = prep.executeUpdate();
		closeAll();
		return result;
	}
	//根据id删除用户信息
	public int delUser(int id) throws SQLException{
		int result = 0;
		String sql = "delete from users where id=?";
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
	//根据id获取数据库用户信息
	public UserPacket toupdateUser(int id) throws SQLException{
		UserPacket user = null;
		String sql = "select * from users where id = ?";
		conn = getConnection();
		try {
			prep = conn.prepareStatement(sql);
			prep.setInt(1, id);
			resultSet = prep.executeQuery();
			while(resultSet.next()){
				user = new UserPacket();
				user.setId(resultSet.getInt("id"));
				user.setName(resultSet.getString("name"));
				user.setSex(resultSet.getString("sex"));
				user.setTel(resultSet.getString("tel"));
				user.setCardno(resultSet.getString("cardno"));
				user.setCartype(resultSet.getString("cartype"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return user;
	}
	//修改在数据库的用户信息
	public int updateUser(UserPacket user){
		int result = 0;
		String sql = "update users set name=?, sex=?, tel=?, cardno=?, cartype=? where id=?";
		try {
			conn=getConnection();
			prep = conn.prepareStatement(sql);
			prep.setString(1, user.getName());
			prep.setString(2, user.getSex());
			prep.setString(3, user.getTel());
			prep.setString(4, user.getCardno());
			prep.setString(5, user.getCartype());
			prep.setInt(6, user.getId());
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
