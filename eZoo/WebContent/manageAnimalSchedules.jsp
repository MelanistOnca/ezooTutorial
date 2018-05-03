	<!-- Header -->
	<jsp:include page="header.jsp" />
	
	<!-- JSTL includes -->
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
	
<!-- 	Just some stuff you need -->
<header>
		<div class="container">
<!-- 	monkey see monkey do start -->
	<c:choose>
	<c:when test="${not empty message }">
	  <p class="alert ${messageClass}">${message }</p>
	<%
	  session.setAttribute("message", null);
	  session.setAttribute("messageClass", null);
	%>
	</c:when>
	</c:choose>
<!-- 	monkey see monkey do end -->

<%-- <c:set var="join" value="${joins }" scope="page"/> --%>
	<h1>eZoo <small>Animals's Feeding Schedules</small></h1>
	<hr class="paw-primary">
	<table class="table table-striped table-hover table-responsive ezoo-datatable">
			<thead>
				<tr>
					<th class="text-center">Animal ID</th>
					<th class="text-center">Animal Name</th>
					<th class="text-center">Schedule Start</th>
					<th class="text-center">Schedule ID</th>
					<th class="text-center">Unassign?</th>					
				</tr>
			</thead>
			<tbody>

			<c:forEach var="join" items="${joins}">
					<tr>
						<td><c:out value="${join.getAnimalID()}" /></td>
						<td><c:out value="${join.getAnimalName()}" /></td>
						<td><c:out value="${join.getScheduleFeedingTime()}" /></td>
						<td><c:out value="${join.getScheduleID()}" /></td>	
						<td>
						<form action="unassignFeedingSchedule" method="post" class="form-horizontal">
							<input type="hidden" name="id" id="id<c:out value="${join.getAnimalID() }" />,<c:out value="${join.getScheduleID() }" />" value="<c:out value="${join.getAnimalID() }" />,<c:out value="${join.getScheduleID() }" />" />
							<div class="col-sm-offset-4 col-sm-1">
		      					<button type="submit" class="btn btn-primary" >Unassign</button>
		    				</div>
		    			</form>
		    			</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

	</div>
</header>

	<!-- Footer -->
	<jsp:include page="footer.jsp" />