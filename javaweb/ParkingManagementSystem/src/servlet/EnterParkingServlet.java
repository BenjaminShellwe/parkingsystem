package servlet;

import infos.EnterParking;
import infos.FreeTable;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.EnterDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class EnterParkingServlet extends HttpServlet {

	
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
		EnterDao enterDao = new EnterDao();
		String enter = request.getParameter("enter");
		JSONObject jsonObject = new JSONObject();
		
		if(enter == null || enter.equals("list")){
			try {

				List<EnterParking> enterParking = enterDao.getAllEnter();
//				request.setAttribute("enterParking", enterParking);
//				request.getRequestDispatcher("enterparking.jsp").forward(request, response);
				JSONArray jsonArray = JSONArray.fromObject(enterParking);
				jsonObject.put("code", 1);
				jsonObject.put("data", jsonArray);
				response.getWriter().print(jsonObject);
				return;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if(enter.equals("add")){
			String encardno = request.getParameter("encardno");
			String encarno = request.getParameter("encarno");
			String enport = request.getParameter("enport");
			String entime = request.getParameter("entime");
			String position = request.getParameter("position");
			
			if (encardno == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "入场VIP账号不能为空");
				response.getWriter().print(jsonObject);
				return;
			}else if (encarno == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "入场车牌号不能为空");
				response.getWriter().print(jsonObject);
				return;
			}else if (enport == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "入场入口号不能为空");
				response.getWriter().print(jsonObject);
				return;
			}else if (entime == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "入场时间不能为空");
				response.getWriter().print(jsonObject);
				return;
			}else if (position == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "停车位置号码不能为空");
				response.getWriter().print(jsonObject);
				return;
			}
			EnterParking enterParking = new EnterParking();
			enterParking.setEncardno(encardno);
			enterParking.setEncarno(encarno);
			enterParking.setEnport(enport);
			enterParking.setEntime(entime);
			enterParking.setPosition(position);
			
			int result;
			try {
				result = enterDao.addEnterparking(enterParking);
				if(result > 0){
//					response.sendRedirect("EnterParkingServlet");
					jsonObject.put("code", 1);
					jsonObject.put("data", "入场添加成功");
					response.getWriter().print(jsonObject);
					return;
				}else{
//					response.sendRedirect("error.jsp");
					jsonObject.put("code", 0);
					jsonObject.put("data", "入场添加失败");
					response.getWriter().print(jsonObject);
					return;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if(enter.equals("del")){
			int id = Integer.parseInt(request.getParameter("id"));
			try {
				int result = enterDao.delEnterParking(id);
				if(result > 0){
//					response.sendRedirect("EnterParkingServlet");
					jsonObject.put("code", 1);
					jsonObject.put("data", "删除入场车辆成功");
					response.getWriter().print(jsonObject);
					return;
				}else{
//					response.sendRedirect("error.jsp");
					jsonObject.put("code", 0);
					jsonObject.put("data", "删除入场车辆失败");
					response.getWriter().print(jsonObject);
					return;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if(enter.equals("revise")){
			int id = Integer.parseInt(request.getParameter("id"));
			
			try {
				EnterParking enterPark = enterDao.toupdateEnterParking(id);
				if(enterPark != null){
//					request.setAttribute("enterPark", enterPark);
//					request.getRequestDispatcher("updateEnterParking.jsp").forward(request, response);
					JSONArray jsonArray = JSONArray.fromObject(enterPark);
					jsonObject.put("code", 1);
					jsonObject.put("data", jsonArray);
					response.getWriter().print(jsonObject);
					return;
				}else{
//					response.sendRedirect("error.jsp");
					jsonObject.put("code", 0);
					jsonObject.put("data", "显示修改入场信息失败");
					response.getWriter().print(jsonObject);
					return;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if(enter.equals("update")){
			int id = Integer.parseInt(request.getParameter("id"));
			String encardno = request.getParameter("encardno");
			String encarno = request.getParameter("encarno");
			String enport = request.getParameter("enport");
			String entime = request.getParameter("entime");
			String position = request.getParameter("position");
			//入场信息校验
			if (encardno == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "入场VIP账号不能为空");
				response.getWriter().print(jsonObject);
				return;
			}else if (encarno == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "入场车牌号不能为空");
				response.getWriter().print(jsonObject);
				return;
			}else if (enport == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "入场入口号不能为空");
				response.getWriter().print(jsonObject);
				return;
			}else if (entime == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "入场时间不能为空");
				response.getWriter().print(jsonObject);
				return;
			}else if (position == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "停车位置号不能为空");
				response.getWriter().print(jsonObject);
				return;
			}
			EnterParking enterParking = new EnterParking();
			enterParking.setId(id);
			enterParking.setEncardno(encardno);
			enterParking.setEncarno(encarno);
			enterParking.setEnport(enport);
			enterParking.setEntime(entime);
			enterParking.setPosition(position);
			
			int result = enterDao.updateEnterParking(enterParking);
			if(result > 0){
//				response.sendRedirect("EnterParkingServlet");
				jsonObject.put("code", 1);
				jsonObject.put("data", "修改入场信息成功！");
				response.getWriter().print(jsonObject);
				return;
			}else{
//				response.sendRedirect("error.jsp");
				jsonObject.put("code", 1);
				jsonObject.put("data", "修改入场信息失败！");
				response.getWriter().print(jsonObject);
				return;
			}
		}
		else if(enter.equals("sumit")){
			int id = Integer.parseInt(request.getParameter("id"));

			try {
				EnterParking enterPark = enterDao.toupdateEnterParking(id);
				if(enterPark != null){
//					request.setAttribute("enterpark", enterPark);
//					request.getRequestDispatcher("sumitexitparkingUI.jsp").forward(request, response);
					JSONArray jsonArray = JSONArray.fromObject(enterPark);
					jsonObject.put("code", 1);
					jsonObject.put("data", jsonArray);
					response.getWriter().print(jsonObject);
					return;
				}else{
//					response.sendRedirect("error.jsp");
					jsonObject.put("code", 0);
					jsonObject.put("data", "失败");
					response.getWriter().print(jsonObject);
					return;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
