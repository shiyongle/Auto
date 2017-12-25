<%@ page language="java" pageEncoding="UTF-8"%>
<% 
request.setCharacterEncoding("utf-8");
response.setHeader("Content-Type","application/force-download");
response.addHeader("Content-Type", "application/vnd.ms-word");
response.addHeader("Content-Type", "application/x-msword");
String filename=request.getParameter("execlfileName");
response.setHeader("Content-Disposition","attachment;filename="+ filename+".doc");
out.print(request.getParameter("exportContent")); 
%> 
