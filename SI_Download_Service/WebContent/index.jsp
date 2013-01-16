<%@page import="de.fhb.dlService.DownloadHelper" import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Main</title>
</head>
<body>

	<form method="post" enctype="multipart/form-data"
		action="/SI_Download_Service/UploadService">
		<input type="file" size=20 name="fname" accept="*.csv"> <input
			type="Submit" value="Upload">
	</form>

	<ul>
		<%
			DownloadHelper helper = new DownloadHelper();
			List<String> links = helper.getDlLinks();

			for (String link : links) {
		%>
		<li><a href="DownloadService/<%=link%>"><%=link%></a></li>
		<%
			}
		%>
	</ul>
</body>
</html>