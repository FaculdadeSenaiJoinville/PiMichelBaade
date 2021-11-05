SONHOS.categoria = new Object();

$(document).ready(function() {

	SONHOS.categoria = function(){};
	
	SONHOS.categoria.cadastrar = function(){
		
		var categoria = new Object();
		categoria.nome = document.frmAddCategoria.nome.value;
		
		if(categoria.nome == ""){
			SONHOS.exibirAviso("Preencha todos os campos!");
		}else{
			$.ajax({
				
				type: "POST",
				url: SONHOS.PATH+"categoria/inserir",
				data:JSON.stringify(categoria),
				success: function(msg){								
					SONHOS.exibirAviso(msg);
					$("#addCategoria").trigger("reset");
					SONHOS.categoria.buscar();
				},
				error: function (info){
					SONHOS.exibirAviso("Erro ao cadastrar uma nova categoria: "+ info.status +" - "+ info.statusText);
				}		
			});
		}
		
	}
	
	
	SONHOS.categoria.buscar = function(){
	
	var valorBusca = $("#campoBuscaCategoria").val();
	
	$.ajax({
				
				type: "GET",
				url: SONHOS.PATH+"categoria/buscar",
				data:"valorBusca="+valorBusca,
				success: function(dados){
					
					dados = JSON.parse(dados);					
					$("#listaCategorias").html(SONHOS.categoria.exibir(dados));				
				},
				error: function (info){
					SONHOS.exibirAviso("Erro ao consultar as categoria: "+ info.status +" - "+ info.statusText);
				}		
			});
	};
	
	
	SONHOS.categoria.exibir = function (listaDeCategorias){
		
		var tabela = "<table>"+
		"<tr>"+
		"<th>Id</th>"+
		"<th>Nome</th>"+
		"<th class='acoes'>Ações</th>"+
		"</tr>";
		
		if(listaDeCategorias != undefined && listaDeCategorias.length > 0){
			
			for(var i=0;i<listaDeCategorias.length; i++){
				tabela += "<tr>"+
				"<td>"+listaDeCategorias[i].id+"</td>"+
				"<td>"+listaDeCategorias[i].nome+"</td>"+
				"<td>"+
					"<a onclick=\"SONHOS.categoria.exibirEdicao('"+listaDeCategorias[i].id+"')\"><img src='/ProjetoIndividual/imgs/edit.png' alt='Editar registro'></a>"+
					"<a onclick=\"SONHOS.categoria.exibirExclusao('"+listaDeCategorias[i].id+"')\"><img src='/ProjetoIndividual/imgs/delete.png' alt='Excluir registro'></a>"+
				"</td>"+
				"</tr>"
					
			}
			
			
		}else if(listaDeCategorias == ""){
			tabela += "<tr><td colspan='3'>Nenhum registro encontrado</td></tr>";
		}
		tabela += "</table>";
		return tabela;
	};
	
	
	SONHOS.categoria.buscar();
	
	
	
	SONHOS.categoria.excluir = function(id){
		
		$.ajax({
			type:"DELETE",
			url: SONHOS.PATH + "categoria/excluir/"+id,
			success: function(msg){
				SONHOS.categoria.buscar();
				SONHOS.exibirAviso(msg);				
				$("#modalExcluiCategoria").dialog("close");			
			},
			error: function(info){
				SONHOS.exibirAviso("Erro ao excluir a categoria: "+ info.status +" - "+ info.statusText);
			}
		});		
	};
	
	
	
	SONHOS.categoria.exibirExclusao = function(id){
		
		$.ajax({
			type:"GET",
			url: SONHOS.PATH + "categoria/buscarPorId",
			data: "id="+id,
			success: function(categoria){
				
				document.frmExcluiCategoria.idCategoria.value = categoria.id;
				
				
				var modalExcluiCategoria = {
					title: "Excluir Categoria",
					height: 400,
					width: 500,
					modal: true,
					buttons:{
						"Excluir": function(){
							SONHOS.categoria.excluir(id);
						},
						"Cancelar": function(){
							$(this).dialog("close");
						}
					},
					close: function(){
					}
				};
				
				$("#modalExcluiCategoria").dialog(modalExcluiCategoria);
				
			},
			error: function(info){
				SONHOS.exibirAviso("Erro ao buscar a categoria para exclusão: "+ info.status +" - "+ info.statusText);
			}
		});		
	};
	
	
	SONHOS.categoria.exibirEdicao = function(id){
		
		$.ajax({
			type:"GET",
			url: SONHOS.PATH + "categoria/buscarPorId",
			data: "id="+id,
			success: function(categoria){
				
				document.frmEditaCategoria.idCategoria.value = categoria.id;
				document.frmEditaCategoria.nome.value = categoria.nome;
				
				
				var modalEditaCategoria = {
					title: "Editar Categoria",
					height: 400,
					width: 500,
					modal: true,
					buttons:{
						"Salvar": function(){
							SONHOS.categoria.editar();
						},
						"Cancelar": function(){
							$(this).dialog("close");
						}
					},
					close: function(){
					}
				};
				
				$("#modalEditaCategoria").dialog(modalEditaCategoria);
				
			},
			error: function(info){
				SONHOS.exibirAviso("Erro ao buscar a categoria para edição: "+ info.status +" - "+ info.statusText);
			}
		});		
	};
	
	
	SONHOS.categoria.editar = function(){
		
		var categoria = new Object();
		categoria.id = document.frmEditaCategoria.idCategoria.value;
		categoria.nome = document.frmEditaCategoria.nome.value;
		
		
		$.ajax({
			type:"PUT",
			url: SONHOS.PATH + "categoria/alterar",
			data: JSON.stringify(categoria),
			success: function(msg){
				SONHOS.exibirAviso(msg);
				SONHOS.categoria.buscar();
				$("#modalEditaCategoria").dialog("close");
			},
			error: function(info){
				SONHOS.exibirAviso("Erro ao editar a categoria: "+ info.status +" - "+ info.statusText);
			}
		});	
		
		
	};
	
	
});