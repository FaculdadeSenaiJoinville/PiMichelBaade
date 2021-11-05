$(document).ready(function() {
	
		$.ajax({
			type: "GET",
			url: "/ProjetoIndividual/rest/login/buscarPorLogin",
			success: function(usuario){		
				var mensagemBemVindo = "<center><h1>BEM-VINDO À PÁGINA PRINCIPAL "+usuario+"</h1></center>";			
				
				$("#mensagemBemVindo").html(mensagemBemVindo);
			},
			error: function (info){
				
			}
		});		
		
		
});