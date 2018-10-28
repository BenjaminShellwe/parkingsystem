package servlet;

import infos.EnterParking;
import infos.ExitParking;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.ExitDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ExitParkingServlet extends HttpServlet {

	
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
		ExitDao exitDao = new ExitDao();
		String exit = request.getParameter("exit");
		JSONObject jsonObject = new JSONObject();
		
		if(exit == null || exit.equals("list")){
			try {
				List<ExitParking> exitParking = exitDao.getAllExit();
				if(exitParking != null){
//					request.setAttribute("exitparking", exitParking);
//					request.getRequestDispatcher("exitparking.jsp").forward(request, response);
					JSONArray jsonArray = JSONArray.fromObject(exitParking);
					jsonObject.put("code", 1);
					jsonObject.put("data", jsonArray);
					response.getWriter().println(jsonObject);
					return;
				}else{
//					response.sendRedirect("error.jsp");
					jsonObject.put("code", 0);
					jsonObject.put("data", "出场信息列表显示失败");
					response.getWriter().println(jsonObject);
					return;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if(exit.equals("add")){
			String excardno = request.getParameter("excardno");
			String excarno = request.getParameter("excarno");
			String export = request.getParameter("export");
			String entime = request.getParameter("entime");
			String extime = request.getParameter("extime");
			String time = request.getParameter("time");
			String money = request.getParameter("money");
			//出场信息添加校验
			if (excardno == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "出场VIP卡号不能为空！");
				response.getWriter().println(jsonObject);
				return;
			}else if (excarno == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "出口车辆类型不能为空！");
				response.getWriter().println(jsonObject);
				return;
			}
			else if (export == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "出口号不能为空！");
				response.getWriter().println(jsonObject);
				return;
			}else if (entime == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "入场时间不能为空！");
				response.getWriter().println(jsonObject);
				return;
			}else if (extime == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "出场时间不能为空！");
				response.getWriter().println(jsonObject);
				return;
			}else if (time == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "停车时间不能为空！");
				response.getWriter().println(jsonObject);
				return;
			}else if (money == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "结算金额不能为空！");
				response.getWriter().println(jsonObject);
				return;
			}
			ExitParking exitParking = new ExitParking();
			exitParking.setExcardno(excardno);
			exitParking.setExcarno(excarno);
			exitParking.setExport(export);
			exitParking.setEntime(entime);
			exitParking.setExtime(extime);
			exitParking.setTime(time);
			exitParking.setMoney(money);
			
			int result;
			try {
				result = exitDao.addExitparking(exitParking);
				if(result > 0){
					jsonObject.put("code", 1);
					jsonObject.put("data", "缴费成功，欢迎下次光临！");
					response.getWriter().println(jsonObject);
					return;
//					response.sendRedirect("ExitParkingServlet");
				}else{
//					response.sendRedirect("error.jsp");
					jsonObject.put("code", 0);
					jsonObject.put("data", "缴费失败！");
					response.getWriter().println(jsonObject);
					return;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if(exit.equals("del")){
			int id = Integer.parseInt(request.getParameter("id"));
			try {
				int result = exitDao.delExitParking(id);
				if(result > 0){
//					response.sendRedirect("ExitParkingServlet");
					jsonObject.put("code", 1);
					jsonObject.put("data", "删除出场信息成功！");
					response.getWriter().println(jsonObject);
					return;
				}else{
//					response.sendRedirect("error.jsp");
					jsonObject.put("code", 1);
					jsonObject.put("data", "删除出场信息失败！");
					response.getWriter().println(jsonObject);
					return;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if(exit.equals("revise")){
			int id = Integer.parseInt(request.getParameter("id"));

			try {
				ExitParking exitPark = exitDao.toupdateExitParking(id);
				if(exitPark != null){
					JSONArray jsonArray = JSONArray.fromObject(exitPark);
					jsonObject.put("code", 1);
					jsonObject.put("data", jsonArray);
					response.getWriter().println(jsonObject);
					return;
//					request.setAttribute("exitpark", exitPark);
//					request.getRequestDispatcher("updateExitParking.jsp").forward(request, response);
				}else{
//					response.sendRedirect("error.jsp");
					jsonObject.put("code", 1);
					jsonObject.put("data", "查询出场信息失败");
					response.getWriter().println(jsonObject);
					return;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if(exit.equals("update")){
			int id = Integer.parseInt(request.getParameter("id"));
			String excardno = request.getParameter("excardno");
			String excarno = request.getParameter("excarno");
			String export = request.getParameter("export");
			String entime = request.getParameter("entime");
			String extime = request.getParameter("extime");
			String time = request.getParameter("time");
			String money = request.getParameter("money");
			//出场信息添加校验
			if (excardno == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "出场VIP卡号不能为空！");
				response.getWriter().println(jsonObject);
				return;
			}else if (export == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "出口号不能为空！");
				response.getWriter().println(jsonObject);
				return;
			}else if (entime == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "入场时间不能为空！");
				response.getWriter().println(jsonObject);
				return;
			}else if (extime == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "出场时间不能为空！");
				response.getWriter().println(jsonObject);
				return;
			}else if (time == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "停车时间不能为空！");
				response.getWriter().println(jsonObject);
				return;
			}else if (money == null) {
				jsonObject.put("code", 0);
				jsonObject.put("data", "结算金额不能为空！");
				response.getWriter().println(jsonObject);
				return;
			}
			ExitParking exitParking = new ExitParking();
			exitParking.setId(id);
			exitParking.setExcardno(excardno);
			exitParking.setExcarno(excarno);
			exitParking.setExport(export);
			exitParking.setEntime(entime);
			exitParking.setExtime(extime);
			exitParking.setTime(time);
			exitParking.setMoney(money);
			
			int result = exitDao.updateExitParking(exitParking);
			if(result > 0){
//				response.sendRedirect("ExitParkingServlet");
				jsonObject.put("code", 0);
				jsonObject.put("data", "修改出场信息成功！");
				response.getWriter().println(jsonObject);
				return;
			}else{
//				response.sendRedirect("error.jsp");
				jsonObject.put("code", 0);
				jsonObject.put("data", "修改出场信息失败！");
				response.getWriter().println(jsonObject);
				return;
			}
		}
		else if(exit.equals("sumit")){
			int id = Integer.parseInt(request.getParameter("id"));

			try {
				ExitParking exitPark = exitDao.toupdateExitParking(id);
				if(exitPark != null){
					JSONArray jsonArray = JSONArray.fromObject(exitPark);
					jsonObject.put("code", 0);
					jsonObject.put("data", jsonArray);
					response.getWriter().println(jsonObject);
					return;
//					request.setAttribute("exitpark", exitPark);
//					request.getRequestDispatcher("sumitparkingchargeUI.jsp").forward(request, response);
				}else{
//					response.sendRedirect("error.jsp");
					jsonObject.put("code", 0);
					jsonObject.put("data", "结算失败");
					response.getWriter().println(jsonObject);
					return;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
