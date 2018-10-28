package servlet;

import infos.ParkingInfo;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.ParkingDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Parking extends HttpServlet {

	
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
		
		String sign = request.getParameter("sign");
		ParkingDao parkingDao = new ParkingDao();
		JSONObject jsonObject = new JSONObject();
		if(sign == null || sign.equals("list")){
			List<ParkingInfo> parking;
			try {
				parking = parkingDao.getAllParking();
				if(parking != null){
//					request.setAttribute("parking", parking);
//					request.getRequestDispatcher("parkingUI.jsp").forward(request, response);
					JSONArray jsonArray = JSONArray.fromObject(parking);
					jsonObject.put("code", 1);
					jsonObject.put("data", jsonArray);
					response.getWriter().print(jsonObject);
					return;
				}else{
//					response.sendRedirect("error.jsp");
					jsonObject.put("code", 0);
					jsonObject.put("data", "获取信息失败");
					response.getWriter().print(jsonObject);
					return;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if(sign.equals("add")){
			String parkname = request.getParameter("parkname");
			String parktotal = request.getParameter("parktotal");
			String enterport = request.getParameter("enterport");
			String exitport = request.getParameter("exitport");
			String parktel = request.getParameter("parktel");
			if (parkname == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "停车场名字不能为空");
				response.getWriter().print(jsonObject);
				return;
			}else if (parktotal == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "停车场停车位数不能为空");
				response.getWriter().print(jsonObject);
				return;
			}else if (enterport == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "停车场入口号不能为空");
				response.getWriter().print(jsonObject);
				return;
			}else if (exitport == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "停车场出口号不能为空");
				response.getWriter().print(jsonObject);
				return;
			}else if (parktel == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "停车场值班电话不能为空");
				response.getWriter().print(jsonObject);
				return;
			}
			ParkingInfo parking = new ParkingInfo();
			parking.setParkname(parkname);
			parking.setParktotal(parktotal);
			parking.setEnterport(enterport);
			parking.setExitport(exitport);
			parking.setParktel(parktel);
			
			int result;
			try {
				result = parkingDao.addParking(parking);
				if(result > 0){
//					response.sendRedirect("Parking");
					jsonObject.put("code", 1);
					jsonObject.put("data", "添加停车场信息成功");
					response.getWriter().print(jsonObject);
					return;
				}else{
//					response.sendRedirect("error.jsp");
					jsonObject.put("code", 0);
					jsonObject.put("data", "添加停车场信息失败");
					response.getWriter().print(jsonObject);
					return;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if(sign.equals("del")){
			int id = Integer.parseInt(request.getParameter("id"));
			int result = 0;
			try {
				result = parkingDao.delParking(id);
				if(result > 0){
//					response.sendRedirect("Parking");
					jsonObject.put("code", 1);
					jsonObject.put("data", "删除停车场信息成功");
					response.getWriter().print(jsonObject);
					return;
				}else{
//					response.sendRedirect("error.jsp");
					jsonObject.put("code", 0);
					jsonObject.put("data", "删除停车场信息失败");
					response.getWriter().print(jsonObject);
					return;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if(sign.equals("revise")){
			int id = Integer.parseInt(request.getParameter("id"));
			ParkingInfo parkingInfo = new ParkingInfo();
			try {
				parkingInfo = parkingDao.toupdateParking(id);
				if(parkingInfo != null){
//					request.setAttribute("parking", parkingInfo);
//					request.getRequestDispatcher("updatepaking.jsp").forward(request, response);
					JSONArray jsonArray = JSONArray.fromObject(parkingInfo);
					jsonObject.put("code", 1);
					jsonObject.put("data", jsonArray);
					response.getWriter().print(jsonObject);
					return;
				}else{
//					response.sendRedirect("error.jsp");
					jsonObject.put("code", 1);
					jsonObject.put("data", "查询停车场数据失败");
					response.getWriter().print(jsonObject);
					return;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if(sign.equals("update")){
			int id = Integer.parseInt(request.getParameter("id"));
			String parkname = request.getParameter("parkname");
			String parktotal = request.getParameter("parktotal");
			String enterport = request.getParameter("enterport");
			String exitport = request.getParameter("exitport");
			String parktel = request.getParameter("parktel");
			//判断输入的信息
			if (parkname == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "停车场名字不能为空");
				response.getWriter().print(jsonObject);
				return;
			}else if (parktotal == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "停车场停车位数不能为空");
				response.getWriter().print(jsonObject);
				return;
			}else if (enterport == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "停车场入口号不能为空");
				response.getWriter().print(jsonObject);
				return;
			}else if (exitport == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "停车场出口号不能为空");
				response.getWriter().print(jsonObject);
				return;
			}else if (parktel == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "停车场值班电话不能为空");
				response.getWriter().print(jsonObject);
				return;
			}
			ParkingInfo parking = new ParkingInfo();
			parking.setId(id);
			parking.setParkname(parkname);
			parking.setParktotal(parktotal);
			parking.setEnterport(enterport);
			parking.setExitport(exitport);
			parking.setParktel(parktel);
			
			int result;
			result = parkingDao.updateParking(parking);
			if(result > 0){
//				response.sendRedirect("Parking");
				jsonObject.put("code", 0);
				jsonObject.put("data", "更新停车场信息成功！");
				response.getWriter().print(jsonObject);
				return;
			}else{
//				response.sendRedirect("error.jsp");
				jsonObject.put("code", 0);
				jsonObject.put("data", "更新停车场信息失败！");
				response.getWriter().print(jsonObject);
				return;
			}
		}
	}
}
