$(document).ready(function() {
	
		$.ajax({
			type: "GET",
			url: "/ProjetoIndividual/rest/login/buscarPorLogin",
			success: function(usuario){		
				//var nomeUsuario = "<h1>"+usuario+"</h1>";		
				//alert(usuario);
				$("#nomeUsuario").html(usuario);
			},
			error: function (info){
				
			}
		});	
		
});