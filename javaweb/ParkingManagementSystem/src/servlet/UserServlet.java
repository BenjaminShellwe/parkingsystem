package servlet;

import infos.UserPacket;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.UserDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class UserServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		String index = request.getParameter("index");
		UserDao userDao = new UserDao();		//创建UserDao对象，调用逻辑方法
		JSONObject jObject = new JSONObject(); //JSON数据返回
		if(index == null || index.equals("list")){
			List<UserPacket> users;
			try {
				users = userDao.getAllUser();
				if(users != null){
					JSONArray jsonArray = JSONArray.fromObject(users);
//					request.setAttribute("users", users);
//					request.getRequestDispatcher("userUI.jsp").forward(request, response);
//					jObject.put("code", "1");
//					jObject.put(jsonArray);
					response.getWriter().write(jsonArray.toString());
//					response.getWriter().flush();
//					response.getWriter().close();
					return;
				}else{
//					response.sendRedirect("error.jsp");
					jObject.put("code", 0);
					jObject.put("data", "获取用户列表信息失败");
					response.getWriter().write(jObject.toString());
					return;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if(index.equals("add")){	//添加用户
			String name = request.getParameter("name");
			String sex = request.getParameter("sex");
			String tel = request.getParameter("tel");
			String cardno = request.getParameter("cardno");
			String cartype = request.getParameter("cartype");
			if (name == null) {
				jObject.put("code", 0);
				jObject.put("data", "车主名不能为空");
				response.getWriter().write(jObject.toString());
				return;
			}else if (tel == null) {
				jObject.put("code", 0);
				jObject.put("data", "车主联系电话不能为空");
				response.getWriter().write(jObject.toString());
				return;
			}else if (cardno == null) {
				jObject.put("code", 0);
				jObject.put("data", "车牌号不能为空");
				response.getWriter().write(jObject.toString());
				return;
			}else if (cartype == null) {
				jObject.put("code", 0);
				jObject.put("data", "车型不能为空");
				response.getWriter().write(jObject.toString());
				return;
			}
			UserPacket user = new UserPacket();
			user.setName(name);
			user.setSex(sex);
			user.setTel(tel);
			user.setCardno(cardno);
			user.setCartype(cartype);
			
			int result;
			try {
				result = userDao.addUser(user);
				if(result > 0){
//					response.sendRedirect("UserServlet");
					jObject.put("code", 1);
					jObject.put("data", "添加成功");
					response.getWriter().write(jObject.toString());
				}else{
//					response.sendRedirect("error.jsp");
					jObject.put("code", 0);
					jObject.put("data", "添加失败");
					response.getWriter().write(jObject.toString());
					return;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if(index.equals("del")){		//删除用户逻辑
			int id = Integer.parseInt(request.getParameter("id"));
			int result = 0;
			try {
				result = userDao.delUser(id);
				if(result > 0){
//					response.sendRedirect("UserServlet");
					jObject.put("code", 0);
					jObject.put("data", "删除用户成功！");
					response.getWriter().write(jObject.toString());
					return;
				}else{
//					response.sendRedirect("error.jsp");
					jObject.put("code", 0);
					jObject.put("data", "删除用户失败！");
					response.getWriter().write(jObject.toString());
					return;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if(index.equals("revise")){	//显示要修改用户数据的逻辑
			int id = Integer.parseInt(request.getParameter("id"));
			UserPacket userPacket = new UserPacket();
			try {
				userPacket = userDao.toupdateUser(id);
				if(userPacket != null){
//					request.setAttribute("user", userPacket);
//					request.getRequestDispatcher("updateuser.jsp").forward(request, response);
					JSONArray jsonArray = JSONArray.fromObject(userPacket);
//					request.setAttribute("users", users);
//					request.getRequestDispatcher("userUI.jsp").forward(request, response);
					jObject.put("code", 1);
					jObject.put("data", jsonArray);
					response.getWriter().write(jObject.toString());
					return;
				}else{
//					response.sendRedirect("error.jsp");
					jObject.put("code", 0);
					jObject.put("data", "修改用户数据失败");
					response.getWriter().write(jObject.toString());
					return;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if(index.equals("update")){	//修改用户数据逻辑
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			String sex = request.getParameter("sex");
			String tel = request.getParameter("tel");
			String cardno = request.getParameter("cardno");
			String cartype = request.getParameter("cartype");
			
			if (name == null) {
				jObject.put("code", 0);
				jObject.put("data", "车主名不能为空");
				response.getWriter().write(jObject.toString());
				return;
			}else if (tel == null) {
				jObject.put("code", 0);
				jObject.put("data", "车主联系电话不能为空");
				response.getWriter().write(jObject.toString());
				return;
			}else if (cardno == null) {
				jObject.put("code", 0);
				jObject.put("data", "车牌号不能为空");
				response.getWriter().write(jObject.toString());
				return;
			}else if (cartype == null) {
				jObject.put("code", 0);
				jObject.put("data", "车型不能为空");
				response.getWriter().write(jObject.toString());
				return;
			}
			
			UserPacket user = new UserPacket();
			user.setId(id);
			user.setName(name);
			user.setSex(sex);
			user.setTel(tel);
			user.setCardno(cardno);
			user.setCartype(cartype);
			
			int result = userDao.updateUser(user);
			if(result > 0){
//				response.sendRedirect("UserServlet");
				jObject.put("code", 1);
				jObject.put("data", "更新用户成功！");
				response.getWriter().write(jObject.toString());
				return;
			}else{
//				response.sendRedirect("error");
				jObject.put("code", 0);
				jObject.put("data", "更新用户失败！");
				response.getWriter().write(jObject.toString());
				return;
			}
			
		}
	}

}
