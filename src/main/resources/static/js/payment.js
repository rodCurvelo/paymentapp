$('#ExcludingModal').on('show.bs.modal', function(event){
	
	var button = $(event.relatedTarget);
	
	var x = button.data('coding');
	var y = button.data('description');
	
	var modal = $(this);
	var form = modal.find('form');
	var action = form.data('url-base');
	if (!action.endsWith('/')) {
		action += '/';
	}
	form.attr('action', action + x);
	
	modal.find('.modal-body span').html('Do you want delete  <strong>' + y + '</strong>?');
	
});

$(function(){
	$('[rel="tooltip"]').tooltip();
	$('.js-currency').maskMoney({thousands:'.', decimal:'.', allowZero:true});
	
	$('.js-updating-status').on('click', function(event) {
		event.preventDefault();
		
		var receiveButton = $(event.currentTarget);
		var urlReceive = receiveButton.attr('href');
		
		var response = $.ajax({
			url: urlReceive,
			type: 'PUT'
		});
		
		
		
		response.done(function(e) {
			var codingInvoice = receiveButton.data('coding');
			$('[data-role=' + codingInvoice + ']').html('<span class="label label-success">' + e + '</span>');
			receiveButton.hide();
		});
		
		response.fail(function(e){
			console.log(e);
			alert('Error detected changing payment status');
		});
		
	});
});