SONHOS.produto = new Object();

$(document).ready(function() {

	SONHOS.produto = function(){};
	
	
	//renomear nomes
	SONHOS.produto.carregarCategorias = function(id){
		
		if(id!=undefined){
			select = "#selCategoriaEdicao";
		}else{
			select = "#selCategoria";
		}
		
		$.ajax({
			type: "GET",
			url: SONHOS.PATH + "categoria/buscar",
			success: function (categorias) {
				
				if (categorias!=""){
					
					$(select).html("");
					var option = document.createElement("option");
					option.setAttribute ("value", "");
					option.innerHTML = ("Escolha");
					$(select).append(option);
					
					for(var i = 0; i< categorias.length; i++){
						
						var option = document.createElement("option");
						option.setAttribute ("value", categorias[i].id);
						
						if((id!=undefined)&&(id==categorias[i].id))
							option.setAttribute ("selected", "selected");
						
						option.innerHTML = (categorias[i].nome);
						$(select).append(option);
												
					}
					
				}else{
					
					$(select).html("");
					
					var option = document.createElement("option");
					option.setAttribute ("value", "");
					option.innerHTML = ("Cadastre uma categoria primeiro!");
					$(select).append(option);
					$(select).addClass("aviso");
				}
				
				
			},
			error: function (info) {
			
			SONHOS.exibirAviso("Erro ao buscar as categorias: "+ info.status + " - " +info.statusText);
			
			$(select).html("");
			var option = document.createElement("option");
			option.setAttribute ("value", "");
			option.innerHTML = ("Erro ao carregar categorias!");
			$(select).append(option);
			$(select).addClass("aviso");
			
			}
		});
		
	};
		
	SONHOS.produto.carregarCategorias();
	
	
	
	SONHOS.produto.cadastrar = function(){
		
		var produto = new Object();
		produto.nome = document.frmAddProduto.nome.value;
		produto.isvalorunidade = document.querySelector('input[name="isvalorunidade"]:checked').value;	
		produto.categoriaId = document.frmAddProduto.categoriaId.value;
		produto.valor = document.frmAddProduto.valor.value;
		
		if(produto.nome == "" || produto.categoriaId == "" || produto.isvalorunidade == "" || produto.valor == ""){
			SONHOS.exibirAviso("Preencha todos os campos!");
		}else{
			$.ajax({
				
				type: "POST",
				url: SONHOS.PATH+"produto/inserir",
				data:JSON.stringify(produto),
				success: function(msg){								
					SONHOS.exibirAviso(msg);
					$("#addProduto").trigger("reset");
					SONHOS.produto.buscar();
				},
				error: function (info){
					SONHOS.exibirAviso("Erro ao cadastrar um novo produto: "+ info.status +" - "+ info.statusText);
				}		
			});
		}
		
	}
	
	
	SONHOS.produto.buscar = function(){
	
	var valorBusca = $("#campoBuscaProduto").val();
	
	$.ajax({
				
				type: "GET",
				url: SONHOS.PATH+"produto/buscar",
				data:"valorBusca="+valorBusca,
				success: function(dados){
					
					dados = JSON.parse(dados);					
					$("#listaProdutos").html(SONHOS.produto.exibir(dados));				
				},
				error: function (info){
					SONHOS.exibirAviso("Erro ao consultar os produtos: "+ info.status +" - "+ info.statusText);
				}		
			});
	};
	
	
	
	SONHOS.produto.buscar();
	
	
	
	
	SONHOS.produto.exibir = function (listaDeProdutos){
		
		var tabela = "<table>"+
		"<tr>"+
		"<th>Id</th>"+
		"<th>Categoria</th>"+
		"<th>Nome</th>"+
		"<th>Valor</th>"+
		"<th>Tipo</th>"+
		"<th class='acoes'>Ações</th>"+
		"</tr>";
		
		if(listaDeProdutos != undefined && listaDeProdutos.length > 0){
			
			for(var i=0;i<listaDeProdutos.length; i++){
				tabela += "<tr>"+
				"<td>"+listaDeProdutos[i].id+"</td>"+
				"<td>"+listaDeProdutos[i].catNome+"</td>"+
				"<td>"+listaDeProdutos[i].nome+"</td>"+
				"<td>"+listaDeProdutos[i].valor+"</td>"+
				"<td>"+listaDeProdutos[i].isvalorunidade+"</td>"+		
				"<td>"+
					"<a onclick=\"SONHOS.produto.exibirEdicao('"+listaDeProdutos[i].id+"')\"><img src='/ProjetoIndividual/imgs/edit.png' alt='Editar registro'></a>"+
					"<a onclick=\"SONHOS.produto.exibirExclusao('"+listaDeProdutos[i].id+"')\"><img src='/ProjetoIndividual/imgs/delete.png' alt='Excluir registro'></a>"+
				"</td>"+
				"</tr>"
					
			}
			
			
		}else if(listaDeProdutos == ""){
			tabela += "<tr><td colspan='6'>Nenhum registro encontrado</td></tr>";
		}
		tabela += "</table>";
		return tabela;
	};
	
	
	SONHOS.produto.excluir = function(id){
		
		$.ajax({
			type:"DELETE",
			url: SONHOS.PATH + "produto/excluir/"+id,
			success: function(msg){
				SONHOS.produto.buscar();
				SONHOS.exibirAviso(msg);				
				$("#modalExcluiProduto").dialog("close");			
			},
			error: function(info){
				SONHOS.exibirAviso("Erro ao excluir o produto: "+ info.status +" - "+ info.statusText);
			}
		});		
	};
	
	
	
	SONHOS.produto.exibirExclusao = function(id){
		
		$.ajax({
			type:"GET",
			url: SONHOS.PATH + "produto/buscarPorId",
			data: "id="+id,
			success: function(produto){
				
				document.frmExcluiProduto.idProduto.value = produto.id;
				
				var modalExcluiProduto = {
					title: "Excluir Produto",
					height: 300,
					width: 500,
					modal: true,
					buttons:{
						"Excluir": function(){
							SONHOS.produto.excluir(id);
						},
						"Cancelar": function(){
							$(this).dialog("close");
						}
					},
					close: function(){
					}
				};
				
				$("#modalExcluiProduto").dialog(modalExcluiProduto);
				
			},
			error: function(info){
				SONHOS.exibirAviso("Erro ao buscar o produto para exclusão: "+ info.status +" - "+ info.statusText);
			}
		});		
	};
	
	
	SONHOS.produto.exibirEdicao = function(id){
		
		$.ajax({
			type:"GET",
			url: SONHOS.PATH + "produto/buscarPorId",
			data: "id="+id,
			success: function(produto){
				
				document.frmEditaProduto.idProduto.value = produto.id;
				document.frmEditaProduto.nome.value = produto.nome;
				document.frmEditaProduto.categoriaId.value = produto.categoriaId;
				document.frmEditaProduto.isvalorunidade.value = produto.isvalorunidade;
				document.frmEditaProduto.valor.value = produto.valor;
				
				SONHOS.produto.carregarCategorias(produto.categoriaId);
			
				
				
				var modalEditaProduto = {
					title: "Editar Produto",
					height: 300,
					width: 610,
					modal: true,
					buttons:{
						"Salvar": function(){
							SONHOS.produto.editar();
						},
						"Cancelar": function(){
							$(this).dialog("close");
						}
					},
					close: function(){
					}
				};
				
				$("#modalEditaProduto").dialog(modalEditaProduto);
				
			},
			error: function(info){
				SONHOS.exibirAviso("Erro ao buscar o produto para edição: "+ info.status +" - "+ info.statusText);
			}
		});		
	};
	
	
	SONHOS.produto.editar = function(){
		
		var produto = new Object();
		produto.id = document.frmEditaProduto.idProduto.value;
		produto.nome = document.frmEditaProduto.nome.value;
		produto.categoriaId = document.frmEditaProduto.categoriaId.value;
		produto.isvalorunidade = document.frmEditaProduto.isvalorunidade.value;
		produto.valor = document.frmEditaProduto.valor.value;
					
		$.ajax({
			type:"PUT",
			url: SONHOS.PATH + "produto/alterar",
			data: JSON.stringify(produto),
			success: function(msg){
				SONHOS.exibirAviso(msg);
				SONHOS.produto.buscar();
				$("#modalEditaProduto").dialog("close");
			},
			error: function(info){
				SONHOS.exibirAviso("Erro ao editar o produto: "+ info.status +" - "+ info.statusText);
			}
		});	
		
		
	};
	
	
});