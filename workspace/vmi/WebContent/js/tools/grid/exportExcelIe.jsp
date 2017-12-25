<%@ page language="java" pageEncoding="UTF-8"%>
<% 
request.setCharacterEncoding("utf-8");
response.setHeader("Content-Type","application/force-download");
response.setHeader("Content-Type","application/vnd.ms-excel");
String filename=request.getParameter("execlfileName");
response.setHeader("Content-Disposition","attachment;filename="+ filename+".xls");
out.print(request.getParameter("exportContent")); 
%> 
