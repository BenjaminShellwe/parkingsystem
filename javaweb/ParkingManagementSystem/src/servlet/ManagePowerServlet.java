package servlet;

import infos.Power;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.ManagePowerDao;
import logic.PowerDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ManagePowerServlet extends HttpServlet {

	
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
		
		String power = request.getParameter("power");
		ManagePowerDao managePowerDao = new ManagePowerDao();
		PowerDao powerDao = new PowerDao();
		JSONObject jsonObject = new JSONObject();
		
		if(power == null || power.equals("list")){
			List<Power> powerManage;
			try {
				powerManage = managePowerDao.getAllPower();
				if(powerManage != null){
					JSONArray jsonArray = JSONArray.fromObject(powerManage);
					jsonObject.put("code", 1);
					jsonObject.put("data", jsonArray);
					response.getWriter().println(jsonObject);
					return;
//					request.setAttribute("powermanage", powerManage);
//					request.getRequestDispatcher("superpowerUI.jsp").forward(request, response);
				}else{
					response.sendRedirect("error.jsp");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if(power.equals("add")){
			String number = request.getParameter("number");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String powers = request.getParameter("powers");
			//添加信息校验
			if (number == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "账号不能为空");
				response.getWriter().println(jsonObject);
				return;
			}else if (password == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "密码不能为空");
				response.getWriter().println(jsonObject);
				return;
			}else if (name == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "用户名不能为空");
				response.getWriter().println(jsonObject);
				return;
			}else if (powers == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "请选择权限");
				response.getWriter().println(jsonObject);
				return;
			}
			Power powering = new Power();
			powering.setNumber(number);
			powering.setPassword(password);
			powering.setName(name);
			powering.setPower(powers);
	
			int result;
			try {
				result = managePowerDao.addPower(powering);
				if(result > 0){
//					response.sendRedirect("ManagePowerServlet");
					jsonObject.put("code", 1);
					jsonObject.put("data", "添加成功");
					response.getWriter().println(jsonObject);
					return;
				}else{
//					response.sendRedirect("error.jsp");
					jsonObject.put("code", 0);
					jsonObject.put("data", "添加失败");
					response.getWriter().println(jsonObject);
					return;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if(power.equals("del")){
			int id = Integer.parseInt(request.getParameter("id"));
			int result = 0;
			try {
				result = managePowerDao.delPower(id);
				if(result > 0){
//					response.sendRedirect("ManagePowerServlet");
					jsonObject.put("code", 1);
					jsonObject.put("data", "删除信息成功");
					response.getWriter().println(jsonObject);
					return;
				}else{
//					response.sendRedirect("error.jsp");
					jsonObject.put("code", 1);
					jsonObject.put("data", "删除信息失败");
					response.getWriter().println(jsonObject);
					return;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if(power.equals("revise")){
			int id = Integer.parseInt(request.getParameter("id"));
			Power powering = new Power();
			try {
				powering = managePowerDao.toupdatePower(id);
				if(powering != null){
					JSONArray jsonArray = JSONArray.fromObject(powering);
					jsonObject.put("code", 1);
					jsonObject.put("data", jsonArray);
					response.getWriter().println(jsonObject);
					return;
//					request.setAttribute("powering", powering);
//					request.getRequestDispatcher("updatepower.jsp").forward(request, response);
				}else{
//					response.sendRedirect("error.jsp");
					jsonObject.put("code", 1);
					jsonObject.put("data", "查询用户信息失败");
					response.getWriter().println(jsonObject);
					return;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if(power.equals("update")){
			int id = Integer.parseInt(request.getParameter("id"));
			String number = request.getParameter("number");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String powers = request.getParameter("powers");
			//添加信息校验
			if (number == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "账号不能为空");
				response.getWriter().println(jsonObject);
				return;
			}else if (password == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "密码不能为空");
				response.getWriter().println(jsonObject);
				return;
			}else if (name == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "用户名不能为空");
				response.getWriter().println(jsonObject);
				return;
			}else if (powers == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "请选择权限");
				response.getWriter().println(jsonObject);
				return;
			}
			Power powering = new Power();
			powering.setId(id);
			powering.setNumber(number);
			powering.setPassword(password);
			powering.setName(name);
			powering.setPower(powers);
			
			int result;
			result = managePowerDao.updateParking(powering);
			if(result > 0){
//				response.sendRedirect("ManagePowerServlet");
				jsonObject.put("code", 1);
				jsonObject.put("data", "修改信息成功");
				response.getWriter().println(jsonObject);
				return;
			}else{
//				response.sendRedirect("error.jsp");
				jsonObject.put("code", 0);
				jsonObject.put("data", "修改信息失败");
				response.getWriter().println(jsonObject);
				return;
			}
		}
		else if(power.equals("registered")){
			String number = request.getParameter("number");		//获取输入账号信息
			String password = request.getParameter("password");	//输入密码信息
			String name = request.getParameter("name");			//获取输入姓名
			String powers = request.getParameter("powers");		//获取选择的权限
			if(number== null || password == null){
//				request.setAttribute("str", "false");	//判读输入信息为空，直接转发到注册页显示注册失败
//				request.getRequestDispatcher("registeredpower.jsp").forward(request, response);
				jsonObject.put("code", 0);
				jsonObject.put("data", "注册账号或密码不能为空");
				response.getWriter().print(jsonObject);
				return ;
			}
			if (name == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "用户名不能为空");
				response.getWriter().print(jsonObject);
				return ;
			}
			if (powers == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "请选择权限");
				response.getWriter().print(jsonObject);
				return ;
			}
			Power user = null;
			try {
				user = powerDao.login(number);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (user != null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "账号已经注册，请重新注册账号！");
				response.getWriter().print(jsonObject);
				return ;
			}
			System.out.println(number);
			Power powering = new Power();//将从输入框获得的信息存到Power对象
			powering.setNumber(number);
			powering.setPassword(password);
			powering.setName(name);
			powering.setPower(powers);
			//注册前先判断是否已经注册了
			
			int result;
			try {
				result = managePowerDao.addPower(powering);//调用PowerDao类的addPower方法完成注册功能
				if(result > 0){
//					request.setAttribute("str", "true");//如果注册成功转发到注册页显示注册成功提示框
//					request.getRequestDispatcher("registeredpower.jsp").forward(request, response);
					jsonObject.put("code", 1);
					jsonObject.put("data", "恭喜你，注册成功！");
					response.getWriter().print(jsonObject);
					return ;
				}else{
//					request.setAttribute("str", "false");
//					request.getRequestDispatcher("registeredpower.jsp").forward(request, response);
					jsonObject.put("code", 0);
					jsonObject.put("data", "不好意思，注册失败！");
					response.getWriter().print(jsonObject);
					return ;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}	
}
