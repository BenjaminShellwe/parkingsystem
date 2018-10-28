<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<style>
.col {
	background-color: red;
}
</style>
<title>欢迎登录进出收费管理系统</title>
<meta http-equiv="Content-Type " content="text/html; charset=UTF-8 ">
<link rel="stylesheet" href="CSS/login.css">
</head>
<body>
	<div class="box">
		<div class="title">
			<h1>进出场收费管理系统</h1>
		</div>
		<div class="w">

			<div class="form">
				<form action="Login" method="post">
					<h3>用户登录</h3>
					<div class="txt">
						<p>
							<label class="lab">帐号</label><input type="text" name="username"
								class="user">
						</p>
						<p>
							<label class="lab">密码</label> <input type="password"
								name="password" class="user">
						</p>
						<p>
							<label class="usertype">用户类型</label> <select name="power"
								id="power" class="power">
								<option disabled>选择用户类型</option>
								<option value="1">管理员</option>
								<option value="2">普通用户</option>
							</select>

						</p>
						<p>
							<input type="submit" value="登录" class="btn"> <input
								type="reset" value="重置" class="btn">
						</p>
						<p class="register">
							<a href="registeredpower.jsp">用户注册</a>
						</p>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
