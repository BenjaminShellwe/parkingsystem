package servlet;

import infos.ParkingCharge;
import infos.ParkingCharge;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.ChargeDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import logic.ChargeDao;


public class ParkingChargeServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		ChargeDao chargeDao = new ChargeDao();
		String charge = request.getParameter("charge");
		JSONObject jsonObject = new JSONObject();
		
		if(charge == null || charge.equals("list")){
			try {
				
				List<ParkingCharge> parkingCharge = chargeDao.getAllCharge();
				if(parkingCharge != null){
//					request.setAttribute("parkingcharge", parkingCharge);
//					request.getRequestDispatcher("parkingcharge.jsp").forward(request, response);
					JSONArray jsonArray = JSONArray.fromObject(parkingCharge);
					jsonObject.put("code", 1);
					jsonObject.put("data", jsonArray);
					response.getWriter().print(jsonObject);
					return;
				}else {
//					response.sendRedirect("error.jsp");
					jsonObject.put("code", 1);
					jsonObject.put("data", "收费列表显示失败");
					response.getWriter().print(jsonObject);
					return;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if(charge.equals("add")){
			String cardno = request.getParameter("cardno");
			String cartype = request.getParameter("cartype");
			String time = request.getParameter("time");
			String price = request.getParameter("price");
			String money = request.getParameter("money");
			//收费校验
			if (cardno == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "VIP卡号不能为空！");
				response.getWriter().print(jsonObject);
				return;
			}else if (cartype == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "车型不能为空！");
				response.getWriter().print(jsonObject);
				return;
			}else if (time == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "时间不能为空！");
				response.getWriter().print(jsonObject);
				return;
			}else if (price == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "缴费价格不能为空！");
				response.getWriter().print(jsonObject);
				return;
			}else if (money == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "缴费费用不能为空！");
				response.getWriter().print(jsonObject);
				return;
			}
			ParkingCharge parkingCharge = new ParkingCharge();
			parkingCharge.setCardno(cardno);
			parkingCharge.setCartype(cartype);
			parkingCharge.setTime(time);
			parkingCharge.setPrice(price);
			parkingCharge.setMoney(money);
			
			int result;
			try {
				result = chargeDao.addParkingCharge(parkingCharge);
				if(result > 0){
//					response.sendRedirect("ParkingChargeServlet");
					jsonObject.put("code", 0);
					jsonObject.put("data", "添加缴费成功！");
					response.getWriter().print(jsonObject);
					return;
				}else{
//					response.sendRedirect("error.jsp");
					jsonObject.put("code", 0);
					jsonObject.put("data", "添加缴费失败！");
					response.getWriter().print(jsonObject);
					return;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else if(charge.equals("del")){
			int id = Integer.parseInt(request.getParameter("id"));
			try {
				int result = chargeDao.delParkingCharge(id);
				if(result > 0){
//					response.sendRedirect("ParkingChargeServlet");
					jsonObject.put("code", 0);
					jsonObject.put("data", "删除缴费信息成功！");
					response.getWriter().print(jsonObject);
					return;
				}else{
//					response.sendRedirect("error.jsp");
					jsonObject.put("code", 0);
					jsonObject.put("data", "删除缴费信息失败！");
					response.getWriter().print(jsonObject);
					return;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
