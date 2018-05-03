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

<!-- this may end up being a part of another page, or its own thing -->

<!-- 		this is assuming its part of a dropdown -->
		<h1>eZoo <small>Assign Feeding Schedule</small></h1>
		<hr class="paw-primary">
		
		<form action="assignFeedingSchedule" method="post" class="form-horizontal">
		
		<div class="form-group">
			<label for="assign_animal_id" class="col-sm-4 control-label">Animal ID</label>
			<div class="col-sm-4">
				<input type="number" class="form-control" id="assign_animal_id" name="assign_animal_id" placeholder="Animal ID" required="required"/>
			</div>
		</div>
		
		<div class="form-group">
			<label for="assign_schedule_id" class="col-sm-4 control-label">Schedule ID</label>
			<div class="col-sm-4">
				<input type="number" class="form-control" id="assign_schedule_id" name="assign_schedule_id" placeholder="Schedule ID" required="required"/>
			</div>
		</div>
		<div class="form-group">
		    <div class="col-sm-offset-4 col-sm-1">
		      <button type="submit" class="btn btn-primary" id="assign_button">Assign</button>
		    </div>
		 </div>
		</form>
	</div>

</header>

	<!-- Footer -->
	<jsp:include page="footer.jsp" />