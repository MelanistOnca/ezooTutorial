	<!-- Header -->
	<jsp:include page="header.jsp" />
	
	<!-- JSTL includes -->
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
	
<!-- 	Just some stuff you need -->
	<header>
	  <div class="container">

	<c:choose>
	<c:when test="${not empty message }">
	  <p class="alert ${messageClass}">${message }</p>
	<%
	  session.setAttribute("message", null);
	  session.setAttribute("messageClass", null);
	%>
	</c:when>
	</c:choose>
 
		<h1>eZoo <small>Feeding Schedules</small></h1>
		<hr class="paw-primary">
		<table class="table table-striped table-hover table-responsive ezoo-datatable">
			<thead>
				<tr>
					<th class="text-center">ID</th>
					<th class="text-center">Feeding Time</th>
					<th class="text-center">Food</th>
					<th class="text-center">Notes</th>
					<th class="text-center">Recurrence</th>
					<th class="text-center">Delete?</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="feedingSchedule" items="${feedingSchedules}" varStatus="myIndex">
				
					<tr>
<%-- 						<c:out value="${ console.log(feedingSchedule, 'was feedingSchedule') }"/> --%>
						<td><c:out value="${feedingSchedule.getFeedingScheduleID()}" /></td>
						<td><c:out value="${feedingSchedule.getFeedingTime()}" /></td>
<%-- 						<td><c:out value="${feedingSchedule.feeding_time}" /></td> --%>
<!-- the .propertyName works in the animals version, but not here, and I don't know why -->
<!-- after fiddling some more, .propertyName IS working, AND I STILL DON'T KNOW WHY!!!! ASDGASGASDGASGSDGSD -->
<!-- they stopped working. -->
<!-- the getter calls seem to never not work, so we're sticking with them. -->
						<td><c:out value="${feedingSchedule.getFood()}" /></td> 
						<td><c:out value="${feedingSchedule.getNotes()}" /></td>
						<td><c:out value="${feedingSchedule.getRecurrence()}" /></td>
						<td>
						<form action="deleteFeedingSchedule" method="post" class="form-horizontal">
							<input type="hidden" name="id" id="id<c:out value="${feedingSchedule.getFeedingScheduleID()}" />" value="<c:out value="${feedingSchedule.getFeedingScheduleID()}"/>" />
							<div class="col-sm-offset-4 col-sm-1">
		      					<button type="submit" class="btn btn-primary" >Delete</button>
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
