$(document).ready(function(){
		$('table.offerTable tr').each(function() {
			var input = $(this).find('input.reservedCheckBox:checked');
			if(input.length > 0) {
	      		$(this).addClass('reserved');
			}
		});
		$('table.attractionTable tr').each(function() {
			var input = $(this).find('span.alreadyReserved');
			if(input.length > 0) {
	      		$(this).addClass('reserved');
			}
		});
	});