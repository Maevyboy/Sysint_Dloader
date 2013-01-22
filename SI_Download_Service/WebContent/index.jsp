<%@page import="de.fhb.dlService.DownloadHelper" import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Main</title>
<link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>
	<div id="main">
		<h3>Choose a File to Upload.</h3>
		<form method="post" enctype="multipart/form-data"
			action="/SI_Download_Service/UploadService">
			<input type="file" size=20 name="fname" accept="*.csv"> <br>
			<input type="Submit" value="Upload">
		</form>
		<%
			DownloadHelper helper = new DownloadHelper();
			List<String> links = helper.getDlLinks();
		%>
		<table>
			<%
				for (int i = 0; i < links.size(); i++) {
					String link = links.get(i);
					if (i % 8 == 0) {
			%>
			<tr>
				<%
					}
				%>
				<td><a href="DownloadService/<%=link%>"><%=link%></a></td>
				<%
					}
				%>
			</tr>
		</table>
	</div>
</body>
</html>