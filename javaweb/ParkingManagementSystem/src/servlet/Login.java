package servlet;

import infos.Power;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.PowerDao;
import net.sf.json.JSONObject;

public class Login extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		request.setCharacterEncoding("utf-8"); //统一字符编码为utf-8
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		String number = request.getParameter("username");
		String password = request.getParameter("password");
		String power = request.getParameter("power");
		if(number==null||password==null){
			JSONObject jsonObject = getJSON(0,"用户名或密码不能为空");
			response.getWriter().print(jsonObject);
//			response.sendRedirect("login.jsp?num=blank");//如果输入账号、密码或权限为空则登录失败
			return;
		}
		if(power == null){
			JSONObject jsonObject = getJSON(0,"请选择权限");
			response.getWriter().print(jsonObject);
			return;
		}
		PowerDao powerDao = new PowerDao();
		try {
			Power powers = powerDao.login(number);//调用PowerDao类的login方法验证登录信息
			if(powers == null){
				JSONObject jsonObject = getJSON(0,"用户账号不存在，请先注册");
				response.getWriter().print(jsonObject);
				return;
			}else if(!password.equals(powers.getPassword())){
				JSONObject jsonObject = getJSON(0,"密码不正确，请重新输入");
				response.getWriter().print(jsonObject);
				return;
			}else if(!power.equals(powers.getPower())){
				JSONObject jsonObject = getJSON(0,"用户权限不正确，请重新选择");
				response.getWriter().print(jsonObject);
				return;
			}else{
				if(Integer.parseInt(powers.getPower())==1){
					response.sendRedirect("PowerServlet");	//验证信息正确，重定向到PowerServlet
				}else{
					response.sendRedirect("UserUIServlet");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//返回JSON数据
	private JSONObject getJSON(int code, Object data) {
		JSONObject jObject = new JSONObject();
		jObject.put("code", code);
		jObject.put("data", data);
		return jObject;
	}

}
