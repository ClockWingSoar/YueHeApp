
$(document).ready(function($) {
	$(".clickable-row").click(function() {
			
		window.location.href = "/duty/edit/"+$(this).find("td:eq(0)").text();
		console.log(window.location);
		});
  });