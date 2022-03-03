SONHOS.alterausuario = new Object();

function validaUsuario(){
	
	if(document.frmEditaUsuario.nome.value == ""){
		SONHOS.exibirAviso("O campo nome foi preenchido incorretamente!");
		return false;
	}
	
	var cpf = document.frmEditaUsuario.cpf.value;
	var expRegCpf = new RegExp("([0-9]{2}[\.]?[0-9]{3}[\.]?[0-9]{3}[\/]?[0-9]{4}[-]?[0-9]{2})|([0-9]{3}[\.]?[0-9]{3}[\.]?[0-9]{3}[-]?[0-9]{2})");
	if(!expRegCpf.test(cpf)){
		SONHOS.exibirAviso("O CPF foi preenchido incorretamente! Por favor preencha sem pontuação, conforme o seguinte exemplo: 09484433363");
		return false;
	}
	if(document.frmEditaUsuario.datanasc.value == ""){
		SONHOS.exibirAviso("A data de nascimento foi preenchida incorretamente!");
		return false;
	}
	
	var telefone = document.frmEditaUsuario.telefone.value;
	var expRegTel = new RegExp("([0-9]{3}|[0-9]{2})?([0-9]{2})([0-9]{4,5})([0-9]{4})");
	if(!expRegTel.test(telefone)){
		SONHOS.exibirAviso("O telefone foi preenchido incorretamente! Por favor preencha sem pontuação, conforme um dos seguintes exemplos: \n 5547996015808 \n 47996015808");
		return false;
	}
	if(document.frmEditaUsuario.email.value == ""){
		SONHOS.exibirAviso("O e-mail foi preenchido incorretamente!");
		return false;
	}
	
	if(document.frmEditaUsuario.login.value == ""){
		SONHOS.exibirAviso("O login foi preenchido incorretamente!");
		return false;
	}
	
	
	SONHOS.alterausuario.confirmarEdicao();
}

$(document).ready(function() {

	SONHOS.alterausuario = function(){};

	
	
	
	SONHOS.alterausuario.confirmarEdicao = function(){
		
				if(document.frmEditaUsuario.login.value == undefined){
					SONHOS.exibirAviso("O usuário não foi encontrado no banco de dados. Por favor recarregue a página e tente novamente!");
				}else{
				
					var modalConfirmarEdicao = {
							title: "Editar usuario",
							height: 300,
							width: 600,
							modal: true,
							buttons:{
								"Salvar": function(){					
								SONHOS.alterausuario.editar();
													
								},
								"Cancelar": function(){
									$(this).dialog("close");
								}
							},
							close: function(){
							}
						};
						
						$("#modalConfirmarEdicao").dialog(modalConfirmarEdicao);
			
				}
			
	};
	
	
	SONHOS.alterausuario.exibirEdicao = function(){
		$.ajax({
			type:"GET",
			url: SONHOS.PATH + "usuario/buscarPorLoginAlterar",
			success: function(usuario){
				
				
				document.frmEditaUsuario.login.value = usuario.login;			
				document.frmEditaUsuario.nivel_usuario.value = usuario.nivel_usuario;
				document.frmEditaUsuario.datanasc.value = usuario.data_nasc;
				document.frmEditaUsuario.cpf.value = usuario.cpf;
				document.frmEditaUsuario.nome.value = usuario.nome;
				document.frmEditaUsuario.email.value = usuario.email;
				document.frmEditaUsuario.telefone.value = usuario.telefone;		
				
				function leftPad(value, totalWidth, paddingChar) {
					  var length = totalWidth - value.toString().length + 1;
					  document.frmEditaUsuario.cpf.value = Array(length).join(paddingChar || '0') + value;
					};
				leftPad(document.frmEditaUsuario.cpf.value, 11);
				if(usuario.cpf == "0"){
				SONHOS.exibirAviso("O usuário não foi encontrado no banco de dados. Por favor recarregue a página e tente novamente!");
				}else{
				
				
				}
		
			},
			error: function(info){
				SONHOS.exibirAviso("Erro ao buscar o funcionário para edição: "+ info.status +" - "+ info.statusText);
			}
		});		
	};
	
	SONHOS.alterausuario.exibirEdicao();
	
	SONHOS.alterausuario.editar = function(){
		
		var usuario = new Object();
		usuario.login = document.frmEditaUsuario.login.value;
		usuario.senha = btoa(document.frmEditaUsuario.senha.value);	
		usuario.nivel_usuario = document.frmEditaUsuario.nivel_usuario.value;
		usuario.data_nasc = document.frmEditaUsuario.datanasc.value;
		usuario.cpf = document.frmEditaUsuario.cpf.value;
		usuario.nome = document.frmEditaUsuario.nome.value;
		usuario.email = document.frmEditaUsuario.email.value;
		usuario.telefone = document.frmEditaUsuario.telefone.value;
		
		$.ajax({
			type:"PUT",
			url: SONHOS.PATH + "usuario/alterar",
			data: JSON.stringify(usuario),
			success: function(msg){
				SONHOS.exibirAviso(msg);
				SONHOS.alterausuario.exibirEdicao();
				$("#modalConfirmarEdicao").dialog("close");
			},
			error: function(info){
				SONHOS.exibirAviso("Erro ao editar o usuário: "+ info.status +" - "+ info.statusText);
			}
		});			
	};		
});



