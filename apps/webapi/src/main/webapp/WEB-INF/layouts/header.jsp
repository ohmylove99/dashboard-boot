<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="pagePath" value="${pageContext.request.requestURI}"/>
<div id="header">
	<div id="title">
		<h2>
	<c:choose> 
      <c:when test="${fn:contains(pagePath, 'login')}">    
          <a href="${ctx}">Dashboard</a>
      </c:when> 
      <c:otherwise>    
           <a href="${ctx}/task">Recruit</a> - <small><a href="${ctx}/job">Job Management</a> | <a href="${ctx}/resume">Resume Management</a></small>
      </c:otherwise> 
    </c:choose> 
			<shiro:user>
				<div class="btn-group pull-right">
					<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
						<i class="icon-user"></i> <shiro:principal property="name" /> <span
						class="caret"></span>
					</a>

					<ul class="dropdown-menu">
						<shiro:hasRole name="admin">
							<li><a href="${ctx}/admin/user">Admin Users</a></li>
							<li class="divider"></li>
						</shiro:hasRole>
						<li><a href="${ctx}/api">APIs</a></li>
						<li><a href="${ctx}/profile">Edit Profile</a></li>
						<li><a href="${ctx}/logout">Logout</a></li>
					</ul>
				</div>
			</shiro:user>
		</h2>
	</div>
</div>