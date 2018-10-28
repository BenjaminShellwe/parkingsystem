package servlet;

import infos.FreeTable;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.PowerDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PowerServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request,response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 允许跨域访问的域名：若有端口需写全（协议+域名+端口），若没有端口末尾不用加'/'
		response.setHeader("Access-Control-Allow-Origin", "*");
		   request.setCharacterEncoding("utf-8");
		   response.setCharacterEncoding("utf-8");
		   response.setContentType("text/html;charset=UTF-8");
		PowerDao powerDao = new PowerDao();
		String index = request.getParameter("index");
		
		if(index == null || index.equals("list")){
			try {
				
				JSONObject jObject = new JSONObject();
				List<FreeTable> free = powerDao.getAll();
				JSONArray data = JSONArray.fromObject(free);
				jObject.put("code", 1);
				jObject.put("data", data);
				response.getWriter().print(jObject);
//				request.getRequestDispatcher("managepage.jsp").forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if(index.equals("add")){		//
			
			String number = request.getParameter("number");
			String cardtype = request.getParameter("cardtype");
			String cartype = request.getParameter("cartype");
			String time = request.getParameter("time");
			String sum = request.getParameter("sum");
			
			FreeTable freeTable = new FreeTable();
			freeTable.setNumber(number);
			freeTable.setCardtype(cardtype);
			freeTable.setCartype(cartype);
			freeTable.setTime(time);
			freeTable.setSum(sum);
			
			int result = powerDao.addFree(freeTable);
			if(result > 0){
				response.sendRedirect("PowerServlet");
			}else{
				response.sendRedirect("error.jsp");
			}
		}else if(index.equals("del")){ 	//
			int id = Integer.parseInt(request.getParameter("id"));
			
			int result = powerDao.delFree(id);
			if(result > 0){
				response.sendRedirect("PowerServlet");
			}else{
				response.sendRedirect("error.jsp");
			}
		}else if(index.equals("revise")){
			int id = Integer.parseInt(request.getParameter("id"));
			
			FreeTable freeTable = powerDao.updateFree(id);
			if(freeTable != null){
				request.setAttribute("free", freeTable);
				request.getRequestDispatcher("updatefreetable.jsp").forward(request, response);
			}else{
				response.sendRedirect("error.jsp");
			}
		}else if(index.equals("toupdate")){
			int id = Integer.parseInt(request.getParameter("id"));
			String number = request.getParameter("number");
			String cardtype = request.getParameter("cardtype");
			String cartype = request.getParameter("cartype");
			String time = request.getParameter("time");
			String sum = request.getParameter("sum");
			
			FreeTable freeTable = new FreeTable();
			freeTable.setId(id);
			freeTable.setNumber(number);
			freeTable.setCardtype(cardtype);
			freeTable.setCartype(cartype);
			freeTable.setTime(time);
			freeTable.setSum(sum);
			
			int result = powerDao.updateFree(freeTable);
			if(result > 0){
				response.sendRedirect("PowerServlet");
			}else{
				response.sendRedirect("error.jsp");
			}
		}
		
	}

}
