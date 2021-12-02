SONHOS = new Object();
function MostraModal(){
	

	var modalEsqueciSenha = {
			title: "Esqueci minha senha",
			height: 250,
			width: 400,
			modal: true,
			buttons:{
				"Enviar": function(){
					var email = document.frmEsqueciSenha.email.value;
					
					$.ajax({
						type: "GET",
						url: "/ProjetoIndividual/rest/redefinirsenha/buscarPorEmail",
						data: "email="+email,
						success: function(msg){		
							
							var modalAviso = {
									title: "Mensagem",
									height: 250,
									width: 400,
									modal: true,
									buttons: {
										"OK": function(){
											$(this).dialog("close");
										}
									}
								};
							$("#modalAviso").html(msg);
							$("#modalAviso").dialog(modalAviso);
								
						},
						error: function (info){
							
						}
					});
					
					console.log("email: "+email);
					console.log("teste");
					$("#modalEsqueciSenha").dialog("close");
				},
				"Cancelar": function(){
					$(this).dialog("close");
				}
			},
			close: function(){
			}
		};
		
		$("#modalEsqueciSenha").dialog(modalEsqueciSenha);
		
		
	return true;
	
}