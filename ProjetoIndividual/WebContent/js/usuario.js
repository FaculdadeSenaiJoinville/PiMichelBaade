SONHOS.usuario = new Object();

function validaUsuario(){
	
	if(document.frmAddUsuario.nome.value == ""){
		alert("O campo nome foi preenchido incorretamente!");
		return false;
	}
	
	var cpf = document.frmAddUsuario.cpf.value;
	var expRegCpf = new RegExp("([0-9]{2}[\.]?[0-9]{3}[\.]?[0-9]{3}[\/]?[0-9]{4}[-]?[0-9]{2})|([0-9]{3}[\.]?[0-9]{3}[\.]?[0-9]{3}[-]?[0-9]{2})");
	if(!expRegCpf.test(cpf)){
		alert("O CPF foi preenchido incorretamente! Por favor preencha sem pontuação, conforme o seguinte exemplo: 09484433363");
		return false;
	}
	if(document.frmAddUsuario.datanasc.value == ""){
		alert("A data de nascimento foi preenchida incorretamente!");
		return false;
	}
	
	var telefone = document.frmAddUsuario.telefone.value;
	var expRegTel = new RegExp("([0-9]{3}|[0-9]{2})?([0-9]{2})([0-9]{4,5})([0-9]{4})");
	if(!expRegTel.test(telefone)){
		alert("O telefone foi preenchido incorretamente! Por favor preencha sem pontuação, conforme um dos seguintes exemplos: \n 5547996015808 \n 47996015808");
		return false;
	}
	if(document.frmAddUsuario.email.value == ""){
		alert("O e-mail foi preenchido incorretamente!");
		return false;
	}
	
	if(document.frmAddUsuario.login.value == ""){
		alert("O login foi preenchido incorretamente!");
		return false;
	}
	if(document.frmAddUsuario.senha.value == ""){
		alert("A senha foi preenchida incorretamente!");
		return false;
	}
	if(document.frmAddUsuario.nivel_usuario.value == ""){
		alert("O nível de usuário foi preenchido incorretamente!");
		return false;
	}
	
	
	SONHOS.usuario.cadastrar()
}

$(document).ready(function() {

	SONHOS.usuario = function(){};
	
	SONHOS.usuario.cadastrar = function(){
		
		var usuario = new Object();
		usuario.login = document.frmAddUsuario.login.value;
		usuario.senha = document.frmAddUsuario.senha.value;
		usuario.nivel_usuario = document.frmAddUsuario.nivel_usuario.value;
		usuario.data_nasc = document.frmAddUsuario.datanasc.value;
		usuario.cpf = document.frmAddUsuario.cpf.value;
		usuario.nome = document.frmAddUsuario.nome.value;	
		usuario.email = document.frmAddUsuario.email.value;
		usuario.telefone = document.frmAddUsuario.telefone.value;
		
			$.ajax({
				
				type: "POST",
				url: SONHOS.PATH+"usuario/inserir",
				data:JSON.stringify(usuario),
				success: function(msg){								
					SONHOS.exibirAviso(msg);
					$("#addUsuario").trigger("reset");
					SONHOS.usuario.buscar();
				},
				error: function (info){
					SONHOS.exibirAviso("Erro ao cadastrar um novo usuario: "+ info.status +" - "+ info.statusText);
				}		
			});
		
		
	}
	
	
	SONHOS.usuario.buscar = function(){
	
	var valorBusca = $("#campoBuscaUsuario").val();
	
	$.ajax({
				
				type: "GET",
				url: SONHOS.PATH+"usuario/buscar",
				data:"valorBusca="+valorBusca,
				success: function(dados){
					
					dados = JSON.parse(dados);					
					$("#listaUsuarios").html(SONHOS.usuario.exibir(dados));				
				},
				error: function (info){
					SONHOS.exibirAviso("Erro ao consultar os funcionários: "+ info.status +" - "+ info.statusText);
				}		
			});
	};
	
	
	SONHOS.usuario.exibir = function (listaDeUsuarios){
		
		var tabela = "<table>"+
		"<tr>"+
		"<th>Nível de usuário</th>"+
		"<th>Nome</th>"+
		"<th>Login</th>"+
		"<th>E-mail</th>"+		
		"<th class='acoes'>Ações</th>"+
		"</tr>";
		
		if(listaDeUsuarios != undefined && listaDeUsuarios.length > 0){
			
			for(var i=0;i<listaDeUsuarios.length; i++){
				tabela += "<tr>"+
				"<td>"+listaDeUsuarios[i].nivel_usuario+"</td>"+
				"<td>"+listaDeUsuarios[i].nome+"</td>"+
				"<td>"+listaDeUsuarios[i].login+"</td>"+
				"<td>"+listaDeUsuarios[i].email+"</td>"+
				"<td>"+
					"<a onclick=\"SONHOS.usuario.exibirEdicao('"+listaDeUsuarios[i].login+"')\"><img src='/ProjetoIndividual/imgs/edit.png' alt='Editar registro'></a>"+
					"<a onclick=\"SONHOS.usuario.exibirExclusao('"+listaDeUsuarios[i].login+"')\"><img src='/ProjetoIndividual/imgs/delete.png' alt='Excluir registro'></a>"+
				"</td>"+
				"</tr>"
					
			}
			
			
		}else if(listaDeUsuarios == ""){
			tabela += "<tr><td colspan='5'>Nenhum registro encontrado</td></tr>";
		}
		tabela += "</table>";
		return tabela;
	};
	
	
	SONHOS.usuario.buscar();
	
	
	
	SONHOS.usuario.excluir = function(login){
		
		$.ajax({
			type:"DELETE",
			url: SONHOS.PATH + "usuario/excluir/"+login,
			success: function(msg){
				SONHOS.usuario.buscar();
				SONHOS.exibirAviso(msg);				
				$("#modalExcluiUsuario").dialog("close");			
			},
			error: function(info){
				SONHOS.exibirAviso("Erro ao excluir o funcionário: "+ info.status +" - "+ info.statusText);
			}
		});		
	};
	
	
	
	SONHOS.usuario.exibirExclusao = function(login){
		
		$.ajax({
			type:"GET",
			url: SONHOS.PATH + "usuario/buscarPorLogin",
			data: "login="+login,
			success: function(usuario){
				
				document.frmExcluiUsuario.login.value = usuario.login;
				
				
				var modalExcluiUsuario = {
					title: "Excluir funcionário",
					height: 400,
					width: 500,
					modal: true,
					buttons:{
						"Excluir": function(){
							SONHOS.usuario.excluir(login);
						},
						"Cancelar": function(){
							$(this).dialog(close);
						}
					},
					close: function(){
					}
				};
				
				$("#modalExcluiUsuario").dialog(modalExcluiUsuario);
				
			},
			error: function(info){
				SONHOS.exibirAviso("Erro ao buscar a funcionário para exclusão: "+ info.status +" - "+ info.statusText);
			}
		});		
	};
	
	
	SONHOS.usuario.exibirEdicao = function(login){
		
		$.ajax({
			type:"GET",
			url: SONHOS.PATH + "usuario/buscarPorLogin",
			data: "login="+login,
			success: function(usuario){
				
				document.frmEditaUsuario.login.value = usuario.login;
				document.frmEditaUsuario.senha.value = usuario.senha;
				document.frmEditaUsuario.nivel_usuario.value = usuario.nivel_usuario;
				document.frmEditaUsuario.datanasc.value = usuario.data_nasc;
				document.frmEditaUsuario.cpf.value = usuario.cpf;
				document.frmEditaUsuario.nome.value = usuario.nome;
				document.frmEditaUsuario.email.value = usuario.email;
				document.frmEditaUsuario.telefone.value = usuario.telefone;		
				
				var modalEditaUsuario = {
					title: "Editar funcionário",
					height: 400,
					width: 1150,
					modal: true,
					buttons:{
						"Salvar": function(){
							SONHOS.usuario.editar();
						},
						"Cancelar": function(){
							$(this).dialog(close);
						}
					},
					close: function(){
					}
				};
				
				$("#modalEditaUsuario").dialog(modalEditaUsuario);
				
			},
			error: function(info){
				SONHOS.exibirAviso("Erro ao buscar o funcionário para edição: "+ info.status +" - "+ info.statusText);
			}
		});		
	};
	
	
	SONHOS.usuario.editar = function(){
		
		var usuario = new Object();
		usuario.login = document.frmEditaUsuario.login.value;
		usuario.senha = document.frmEditaUsuario.senha.value;
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
				SONHOS.usuario.buscar();
				$("#modalEditaUsuario").dialog("close");
			},
			error: function(info){
				SONHOS.exibirAviso("Erro ao editar o funcionário: "+ info.status +" - "+ info.statusText);
			}
		});			
	};		
});



