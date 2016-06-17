window.onload  = function() {
	var baseUrl = "/bamboo";
	function populateForm() {
		AJS.$.ajax({
			url: baseUrl + "/rest/teamserver-admin/1.0/",
			dataType: "json",
			success: function(config) {
				AJS.$("#username").val(config.username);
				AJS.$("#apiKey").val(config.apikey);
				AJS.$("#serviceKey").val(config.servicekey);
				AJS.$("#url").val(config.url);
				AJS.$("#servername").val(config.servername);
				AJS.$("#uuid").val(config.uuid);
				AJS.$("#profilename").val(config.profilename);
			},
			error: function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR.responseText);
				console.log(textStatus);
				console.log(errorThrown);
			}
		});
	}
	function updateConfig() {
		var user = AJS.$("#username").attr("value");
		var api = AJS.$("#apiKey").attr("value");
		var service = AJS.$("#serviceKey").attr("value");
		var TSurl = AJS.$("#url").attr("value");
		var servername = AJS.$("#servername").attr("value");
		var uuid = AJS.$("#uuid").attr("value");
		var profilename = AJS.$("#profilename").attr("value");
		
		var JSONPayload = {
				"profilename":profilename,
				"username": user,
				"apikey": api,
				"servicekey": service,
				"url": TSurl,
				"servername":servername,
				"uuid":uuid
			};
		var stringPayload = JSON.stringify(JSONPayload);
		AJS.$.ajax({
			url: baseUrl + "/rest/teamserver-admin/1.0/",
			type: "POST",
			contentType: "application/json",
			dataType:"json",
			data:stringPayload,
			processData: false
		});
	}
	AJS.$("#admin-submit").removeAttr('onsubmit').submit(function(event){
        event.preventDefault();
    });
	populateForm();
    AJS.$('#admin-submit').click(function(){
		updateConfig();
		return false;
	});
};