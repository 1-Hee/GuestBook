<%@page import="com.onehee.guestbook.model.UserDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<c:if test="${!empty msg}">
	<script type="text/javascript">
		alert("${msg}");
	</script>
</c:if>
<% 	
	session.removeAttribute("msg");
	UserDTO userInfo = null;
	if(session.getAttribute("userinfo") != null){
		userInfo = (UserDTO) session.getAttribute("userinfo");
	}
%>

<!DOCTYPE html>
<html lang="ko">
 <head>
   <meta charset="UTF-8" />
   <meta http-equiv="X-UA-Compatible" content="IE=edge" />
   <meta name="viewport" content="width=device-width, initial-scale=1.0" />
   <link rel="stylesheet" href="${root}/assets/css/color.css"/>
   <link rel="stylesheet" href="${root}/assets/css/commons.css"/>
  
