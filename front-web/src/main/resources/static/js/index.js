$(function() {
	$.getJSON("../json/main.json", function(data) {
		app.items = data.nrietData;
	})
})
var app = new Vue({
	el : '.demo',
	data : {
		items : []
	},
	methods : {
		click : function(event) {
			if (event) {
				let url = $(event.target).attr('href');
				$("#test").load(url);
			}
		}
	}
})