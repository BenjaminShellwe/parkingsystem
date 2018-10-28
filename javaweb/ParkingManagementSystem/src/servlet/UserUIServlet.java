package servlet;

import infos.FreeTable;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.PowerDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class UserUIServlet extends HttpServlet {
	
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
		PowerDao powerDao = new PowerDao();
		String index = request.getParameter("index");
		
		if(index == null || index.equals("list")){
			try {
				JSONObject jObject = new JSONObject();
				List<FreeTable> free = powerDao.getAll();
				JSONArray data = JSONArray.fromObject(free);
				jObject.put("code", 1);
				jObject.put("data", data);
				response.getWriter().write(jObject.toString());
				return;
//				request.setAttribute("free", free);
//				request.getRequestDispatcher("customerUI.jsp").forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
