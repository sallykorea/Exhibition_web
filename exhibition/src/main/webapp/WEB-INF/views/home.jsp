<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="include/resource.jsp" />
<!-- fullcalendar -->
<script>
  document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
      plugins: [ 'interaction', 'dayGrid', 'timeGrid' ],
      defaultView: 'dayGridMonth',
      defaultDate: new Date(),
      header: {
          left: 'prev,next today',
          center: 'title',
          right: 'dayGridMonth,timeGridWeek,timeGridDay'
        },
       //샘플 data
       events: [
         {
           title: 'All Day Event',
           start: '2020-02-01'
         },
         {
           title: 'Long Event',
           start: '2020-02-07',
           end: '2020-02-10'
         },
         {
           groupId: '999',
           title: 'Repeating Event',
           start: '2020-02-09T16:00:00'
         },
         {
           groupId: '999',
           title: 'Repeating Event',
           start: '2020-02-16T16:00:00'
         },
         {
           title: 'Conference',
           start: '2020-02-11',
           end: '2020-02-13'
         },
         {
           title: 'Meeting',
           start: '2020-02-12T10:30:00',
           end: '2020-02-12T12:30:00'
         },
         {
           title: 'Lunch',
           start: '2020-02-12T12:00:00'
         },
         {
           title: 'Meeting',
           start: '2020-02-12T14:30:00'
         },
         {
           title: 'Birthday Party',
           start: '2020-02-13T07:00:00'
         },
         {
           title: 'Click for Google',
           url: 'http://google.com/',
           start: '2020-02-28'
         }
       ] 
   });

    calendar.render();
  });
</script>
<!-- owl.carousel -->
<script>
$(document).ready(function(){
  $('.owl-carousel').owlCarousel({
		loop:true,
		margin:10,
		responsiveClass:true,
		responsive:{
		    0:{
		        items:1,
		        nav:true
		    },
		    600:{
		        items:3,
		        nav:false
		    },
		    1000:{
		        items:5,
		        nav:true,
		        loop:false
		    }
		}
  })
});
</script>
</head>
<body>
<jsp:include page="include/navbar.jsp" />
<div class="container">
	<h3>공연 일정</h3>
	<div id='calendar'></div>
	
	<h3>인기 공연</h3>
	<div class="row">
		<div class="large-12 columns">
			<div class="owl-carousel owl-theme">
				<div class="item">
			      <h4>1</h4>
			    </div>
			    <div class="item">
			      <h4>2</h4>
			    </div>
			    <div class="item">
			      <h4>3</h4>
			    </div>
			    <div class="item">
			      <h4>4</h4>
			    </div>
			    <div class="item">
			      <h4>5</h4>
			    </div>
			    <div class="item">
			      <h4>6</h4>
			    </div>
			    <div class="item">
			      <h4>7</h4>
			    </div>
			    <div class="item">
			      <h4>8</h4>
			    </div>
			    <div class="item">
			      <h4>9</h4>
			    </div>
			    <div class="item">
			      <h4>10</h4>
			    </div>	
			</div>
		</div>
	</div>
</div>
<jsp:include page="include/footer.jsp" />
</body>
</html>