SONHOS.funcionario = new Object();

function validaFuncionario(){
	
	var cpf = document.frmAddFuncionario.cpf.value;
	var expRegCpf = new RegExp("([0-9]{2}[\.]?[0-9]{3}[\.]?[0-9]{3}[\/]?[0-9]{4}[-]?[0-9]{2})|([0-9]{3}[\.]?[0-9]{3}[\.]?[0-9]{3}[-]?[0-9]{2})");
	
	if(!expRegCpf.test(cpf)){
		alert("O CPF foi preenchido incorretamente!");
		return false;
	}
	
	
	
	var telefone = document.frmAddFuncionario.nome.value;
	var expRegTel = new RegExp("/^1\d\d(\d\d)?$|^0800 ?\d{3} ?\d{4}$|^(\(0?([1-9a-zA-Z][0-9a-zA-Z])?[1-9]\d\) ?|0?([1-9a-zA-Z][0-9a-zA-Z])?[1-9]\d[ .-]?)?(9|9[ .-])?[2-9]\d{3}[ .-]?\d{4}$/gm");
	
	if(!expRegTel.test(telefone)){
		alert("O telefone foi preenchido incorretamente!");
		return false;
	}
	
	
}






SONHOS.funcionario = new Object();

$(document).ready(function() {

	
	
	SONHOS.funcionario = function(){};
	
	SONHOS.funcionario.cadastrar = function(){
		
		var funcionario = new Object();
		funcionario.nome = document.frmAddFuncionario.nome.value;
		
		if(funcionario.nome == ""){
			SONHOS.exibirAviso("Preencha todos os campos!");
		}else{
			$.ajax({
				
				type: "POST",
				url: SONHOS.PATH+"funcionario/inserir",
				data:JSON.stringify(funcionario),
				success: function(msg){								
					SONHOS.exibirAviso(msg);
					$("#addFuncionario").trigger("reset");
					SONHOS.funcionario.buscar();
				},
				error: function (info){
					SONHOS.exibirAviso("Erro ao cadastrar uma novo funcionario: "+ info.status +" - "+ info.statusText);
				}		
			});
		}
		
	}
	
	
	SONHOS.funcionario.buscar = function(){
	
	var valorBusca = $("#campoBuscaFuncionario").val();
	
	$.ajax({
				
				type: "GET",
				url: SONHOS.PATH+"funcionario/buscar",
				data:"valorBusca="+valorBusca,
				success: function(dados){
					
					dados = JSON.parse(dados);					
					$("#listaFuncionarios").html(SONHOS.funcionario.exibir(dados));				
				},
				error: function (info){
					SONHOS.exibirAviso("Erro ao consultar os funcionários: "+ info.status +" - "+ info.statusText);
				}		
			});
	};
	
	
	SONHOS.funcionario.exibir = function (listaDeFuncionarios){
		
		var tabela = "<table>"+
		"<tr>"+
		"<th>Id</th>"+
		"<th>Nome</th>"+
		"<th class='acoes'>Ações</th>"+
		"</tr>";
		
		if(listaDeFuncionarios != undefined && listaDeFuncionarios.length > 0){
			
			for(var i=0;i<listaDeFuncionarios.length; i++){
				tabela += "<tr>"+
				"<td>"+listaDeFuncionarios[i].id+"</td>"+
				"<td>"+listaDeFuncionarios[i].nome+"</td>"+
				"<td>"+
					"<a onclick=\"SONHOS.funcionario.exibirEdicao('"+listaDeFuncionarios[i].id+"')\"><img src='/ProjetoIndividual/imgs/edit.png' alt='Editar registro'></a>"+
					"<a onclick=\"SONHOS.funcionario.exibirExclusao('"+listaDeFuncionarios[i].id+"')\"><img src='/ProjetoIndividual/imgs/delete.png' alt='Excluir registro'></a>"+
				"</td>"+
				"</tr>"
					
			}
			
			
		}else if(listaDefuncionarios == ""){
			tabela += "<tr><td colspan='3'>Nenhum registro encontrado</td></tr>";
		}
		tabela += "</table>";
		return tabela;
	};
	
	
	SONHOS.funcionario.buscar();
	
	
	
	SONHOS.funcionario.excluir = function(id){
		
		$.ajax({
			type:"DELETE",
			url: SONHOS.PATH + "funcionario/excluir/"+id,
			success: function(msg){
				SONHOS.funcionario.buscar();
				SONHOS.exibirAviso(msg);				
				$("#modalExcluiFuncionario").dialog("close");			
			},
			error: function(info){
				SONHOS.exibirAviso("Erro ao excluir o funcionário: "+ info.status +" - "+ info.statusText);
			}
		});		
	};
	
	
	
	SONHOS.funcionario.exibirExclusao = function(id){
		
		$.ajax({
			type:"GET",
			url: SONHOS.PATH + "funcionario/buscarPorId",
			data: "id="+id,
			success: function(funcionario){
				
				document.frmExcluiFuncionario.idFuncionario.value = funcionario.id;
				
				
				var modalExcluiFuncionario = {
					title: "Excluir funcionário",
					height: 400,
					width: 500,
					modal: true,
					buttons:{
						"Excluir": function(){
							SONHOS.funcionario.excluir(id);
						},
						"Cancelar": function(){
							$(this).dialog(close);
						}
					},
					close: function(){
					}
				};
				
				$("#modalExcluiFuncionario").dialog(modalExcluiFuncionario);
				
			},
			error: function(info){
				SONHOS.exibirAviso("Erro ao buscar a funcionário para exclusão: "+ info.status +" - "+ info.statusText);
			}
		});		
	};
	
	
	SONHOS.funcionario.exibirEdicao = function(id){
		
		$.ajax({
			type:"GET",
			url: SONHOS.PATH + "funcionario/buscarPorId",
			data: "id="+id,
			success: function(funcionario){
				
				document.frmEditaFuncionario.idFuncionario.value = funcionario.id;
				document.frmEditaFuncionario.nome.value = funcionario.nome;
				
				
				var modalEditaFuncionario = {
					title: "Editar funcionário",
					height: 400,
					width: 500,
					modal: true,
					buttons:{
						"Salvar": function(){
							SONHOS.funcionario.editar();
						},
						"Cancelar": function(){
							$(this).dialog(close);
						}
					},
					close: function(){
					}
				};
				
				$("#modalEditaFuncionario").dialog(modalEditaFuncionario);
				
			},
			error: function(info){
				SONHOS.exibirAviso("Erro ao buscar o funcionário para edição: "+ info.status +" - "+ info.statusText);
			}
		});		
	};
	
	
	SONHOS.funcionario.editar = function(){
		
		var funcionario = new Object();
		funcionario.id = document.frmEditaFuncionario.idFuncionario.value;
		funcionario.nome = document.frmEditaFuncionario.nome.value;
		
		
		$.ajax({
			type:"PUT",
			url: SONHOS.PATH + "funcionario/alterar",
			data: JSON.stringify(funcionario),
			success: function(msg){
				SONHOS.exibirAviso(msg);
				SONHOS.funcionario.buscar();
				$("#modalEditaFuncionario").dialog("close");
			},
			error: function(info){
				SONHOS.exibirAviso("Erro ao editar o funcionário: "+ info.status +" - "+ info.statusText);
			}
		});	
		
		
	};
	
	
});



