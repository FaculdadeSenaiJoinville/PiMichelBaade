SONHOS.venda = new Object();

$(document).ready(function() {

	SONHOS.venda = function(){};

	produtoAntigo = new Array();
	produtoAntigo[0] = "";
	
	categoriaAntigo = new Array();
	categoriaAntigo[0] = "";
	
	valorProdutoAntigo = new Array();
	valorProdutoAntigo[0] = "";
	
	QuantidadePesoAntigo = new Array();
	QuantidadePesoAntigo[0] = "";
	
	valorAntigo = new Array();
	valorAntigo[0] = "";
		
	var valorTotalAntigo = "";
	
	SONHOS.venda.guardaValores = function(id){
		
		var produtoAtual = document.getElementsByName('selProduto[]');
		var categoriaAtual = document.getElementsByName('selCategoria[]');
		var QuantidadePesoAtual = document.getElementsByName('QuantidadePeso[]');
		var valorAtual = document.getElementsByName('valor[]');
		var valorProdutoAtual = document.getElementsByName('ValorProduto[]');
		
		
		
		for (var i = 0; i < $("tr.detalhes").length; i++) {
			produtoAntigo[i] = produtoAtual[i].value;
			categoriaAntigo[i] = categoriaAtual[i].value;
			QuantidadePesoAntigo[i] = QuantidadePesoAtual[i].value;
			valorAntigo[i] = valorAtual[i].value;
			
			valorProdutoAntigo[i] = valorProdutoAtual[i].value;
		
			//console.log("GuardaValores: \n");		
			//console.log("Produtos: \n"+produtoAntigo[i]);
			//console.log("Categorias: \n"+categoriaAntigo[i]);
			//console.log("Quantidade/Peso: \n"+QuantidadePesoAntigo[i]);
			//console.log("Valor total da linha: \n"+valorAntigo[i]);		
		}		
	}


	SONHOS.venda.carregarCategorias = function(id){
		
		var camposCategorias = document.getElementsByName('selCategoria[]');
		
			$.ajax({
				type: "GET",
				url: SONHOS.PATH + "categoria/buscar",
				success: function (categorias) {				
					$(camposCategorias[id]).html("");
					
					if (categorias.length){
						
						var option = document.createElement("option");
						option.setAttribute ("value", "");
						option.innerHTML = ("Escolha");					
						$(camposCategorias[id]).append(option);
						
						for(var i = 0; i< categorias.length; i++){
							
							var option = document.createElement("option");
							option.setAttribute ("value", categorias[i].id);
							option.innerHTML = (categorias[i].nome);
							$(camposCategorias[id]).append(option);
										
						}
						
					}else{
						
						var option = document.createElement("option");
						option.setAttribute ("value", "");
						option.innerHTML = ("Cadastre uma categoria primeiro!");
						
						$(camposCategorias[id]).addClass("aviso");
						$(camposCategorias[id]).append(option);
						
					}
					
					
				},
				error: function (info) {
				
				SONHOS.exibirAviso("Erro ao buscar as categorias: "+ info.status + " - " +info.statusText);
				
				$(camposCategorias[id]).html("");
				var option = document.createElement("option");
				option.setAttribute ("value", "");
				option.innerHTML = ("Erro ao carregar categorias!");
				$(camposCategorias[id]).addClass("aviso");
				$(camposCategorias[id]).append(option);
				
				}
			});		
		}

		SONHOS.venda.carregarCategorias(0);
	
	//Criação de uma nova linha de produto na venda
	
	
	$(".botaoAdd").click(function (){
		
		var novoCampo = $("tr.detalhes:first").clone();
		//ao setar os novos campos do clone ele não deixa alterar mais por javascript.
		novoCampo.find("input").val(0);
		novoCampo.insertAfter("tr.detalhes:last");
		
		//SONHOS.venda.AtualizaValor();
		
		SONHOS.venda.guardaValores();
		
	});
	
	
	SONHOS.venda.removeCampo = function(botao) {
		
		if($("tr.detalhes").length > 1){
			
			$(botao).parent().parent().remove();
			
			SONHOS.venda.guardaValores();
			SONHOS.venda.AtualizaValor();			
		}else{
			SONHOS.exibirAviso("A última linha não pode ser removida!");
		}
	// fecha função removeCampo	
	}
	
	
	SONHOS.venda.carregarProdutos = function(botao) {
		
		var valorProduto = document.getElementsByName('ValorProduto[]');
		var selectProduto = document.getElementsByName('selProduto[]');
		var categoriaAtual =  document.getElementsByName('selCategoria[]');
		
		for (var j = 0; j < selectProduto.length; j++) {
			
			if ((categoriaAntigo[j] != categoriaAtual[j].value)) {
				
				linhaAlterada = j;
			}
			
		}
		if ((categoriaAtual[linhaAlterada].value=="")) {
			
			SONHOS.venda.guardaValores();
			
			return false;
		}
		categoriaCod = categoriaAtual[linhaAlterada].value;
		$(selectProduto[linhaAlterada]).html("<option>Aguarde</option>");
		
		
		$.ajax({
			type: "GET",
			url: SONHOS.PATH + "produto/buscarParaVenda",
			data: "categoria="+categoriaCod,
			success: function (produtos) {
				
				produtos = JSON.parse(produtos);
				console.log("produtos valor: "+produtos);
				$(selectProduto[linhaAlterada]).html("");
				
				if (produtos.length) {
					console.log("quantidade de produtossssssss: "+produtos.length);
					$(selectProduto[linhaAlterada]).removeClass("aviso");
					
					var option = document.createElement("option");
					option.setAttribute("value","");
					option.innerHTML = ("Escolha");
					$(selectProduto[linhaAlterada]).append(option);
					//console.log("quantidade de produtos"+produtos.length);
					for (var i = 0; i < produtos.length; i++) {
						var option = document.createElement("option");
						option.setAttribute("value",produtos[i].id);
						option.innerHTML = (produtos[i].nome);
						$(selectProduto[linhaAlterada]).append(option);
						valorProduto[i] = produtos[i].valor;
						//valorProduto[i].value = produtos[i].valor;
						//valores[i].value = produtos[i].valor;
						//valorProduto[i-1].value = produtos[i].valor;
						
						console.log("Valor do produto ["+i+"] "+produtos[i].valor);
					}
				}else{
					
					var option = document.createElement("option");
					option.setAttribute("value","");
					option.innerHTML = ("Não há produtos correspondentes!");
					$(selectProduto[linhaAlterada]).append(option);
				
					$(selectProduto[linhaAlterada]).addClass("aviso");
					
				}
				
			},
			error: function (info) {
			
				
				SONHOS.exibirAviso("Erro ao buscar os produtos: " + info.status + " - " + info.statusText);
				
				$(selectProduto[linhaAlterada]).html("");
				var option = document.createElement("option");
				option.setAttribute("value","");
				option.innerHTML = ("Erro ao carregar produtos!");
				$(selectProduto[linhaAlterada]).append(option);
				$(selectProduto[linhaAlterada]).addClass("aviso");
				
			}
		});
		
		SONHOS.venda.guardaValores();
		
		//console.log("Produtos: \n"+produtoAntigo);
		//console.log("Categorias: \n"+categoriaAntigo);
		//console.log("Quantidade/Peso: \n"+QuantidadePesoAntigo);
		//console.log("Valor do Produto: \n"+valorProdutoAntigo);
		//console.log("Valor total da linha: \n"+valorAntigo);
			
	}
	
	SONHOS.venda.validaDetalhe = function() {
	
	var produtosValidar = document.getElementsByName('selProduto[]');
	
	var qtdeValidar = document.getElementsByName('QuantidadePeso[]');
	var valorValidar = document.getElementsByName('valor[]');
	
	for (var i = 0; i < produtosValidar.length; i++) {
		
		var linha = i+1;
		if ((produtosValidar[i].value=="")||(qtdeValidar[i].value=="")||(valorValidar[i].value=="")) {
			
			SONHOS.exibirAviso("A linha "+linha+" não foi completamente preenchida.");
			return false;
			
		}
	}
	return true;
		
	}
	
	
	SONHOS.venda.cadastrar = function() {
	
		if (SONHOS.venda.validaDetalhe()) {
		
			var venda = new Object();
			
			var produtos = document.getElementsByName('selProduto[]');
			var quantidades = document.getElementsByName('QuantidadePeso[]');
			var valores = document.getElementsByName('valor[]');
			venda.produtos = new Array(produtos.length);
			
			for (var i = 0; i < produtos.length; i++) {
				venda.produtos[i] = new Object();
				venda.produtos[i].idProduto = produtos[i].value;
				venda.produtos[i].quantidade = quantidades[i].value;
				venda.produtos[i].valor = valores[i].value;
				
			}
			
			$.ajax({
				type: "POST",
				url: SONHOS.PATH + "venda/inserir",
				data: JSON.stringify(venda),
				success: function (msg) {
					
					SONHOS.exibirAviso(msg);
				
				},
				error: function (info) {
				
					SONHOS.exibirAviso("Erro ao cadastrar um novo produto: "+info.status+" - "+info.statusText);
				}
			});
		}
		SONHOS.carregaPagina("venda");
	}
	
	
SONHOS.venda.AtualizaValor = function() {
		
		$.ajax({
			type: "POST",
			url: SONHOS.PATH + "venda/atualizaValores",
			data:"produtoAntigo="+produtoAntigo,
			async:false,
			success: function (valoresProdutos) {
				
				valoresProdutos = JSON.parse(valoresProdutos);
				//console.log(valoresProdutos);
				
				var venda = new Object();
				var quantidades = document.getElementsByName('QuantidadePeso[]');
				var valores = document.getElementsByName('valor[]');
				var idprodutos = document.getElementsByName('selProduto[]');
				venda.produtos = new Array(valoresProdutos.length);
				//console.log(valoresProdutos.length);
				var totalValores = 0;
				
				//console.log("Quantidade que ta no valores guardado: "+QuantidadePesoAntigo[0]);
				//console.log("Quantidade que ta vindo para o atualiza: "+quantidades[0].value);
				
				
				for (var j = 0; j < $("tr.detalhes").length; j++) {
					//console.log("Id do produto antigo: "+produtoAntigo[j]);
					//console.log("Id do produto novo: "+idprodutos[j].value);
					console.log("valor atual da linha "+j+": "+valorAntigo[j]);
					if ((produtoAntigo[j] != idprodutos[j].value)) {
						console.log("Quantidade que ta no valores guardado: "+QuantidadePesoAntigo[j]);
						console.log("Quantidade que ta vindo para o atualiza: "+quantidades[j].value);
						
						linhaAlterada = j;
						console.log("linhaAlterada: "+linhaAlterada);
						

						//totalValores -= (valorAtual[j] * QuantidadePesoAntigo[j]);
						
						//totalValores += (valoresProdutos[j].valor * quantidades[j].value);
					}
					
					
					
				}
				
				for (var i = 0; i < $("tr.detalhes").length; i++) {
					venda.produtos[i] = new Object();
					venda.produtos[i].valor = valoresProdutos[i].valor;
					valores[i].value = (valoresProdutos[i].valor * quantidades[i].value);
					
					totalValores += (valoresProdutos[i].valor * quantidades[i].value);
				}
				valorTotalAntigo = totalValores;
				console.log("totalValores: "+totalValores);
				console.log("valortotalAntigo: "+valorTotalAntigo);
				$("#totalValores").html("Total: R$ "+valorTotalAntigo);
				SONHOS.venda.guardaValores();
			},
			error: function (info) {
						
				SONHOS.exibirAviso("Erro ao buscar os produtos: " + info.status + " - " + info.statusText);			
				$(selectProduto[linhaAlterada]).html("");
				var option = document.createElement("option");
				option.setAttribute("value","");
				option.innerHTML = ("Erro ao carregar produtos!");
				$(selectProduto[linhaAlterada]).append(option);
				$(selectProduto[linhaAlterada]).addClass("aviso");
				SONHOS.venda.guardaValores();
			}
		});
		
		SONHOS.venda.guardaValores();
		
		//console.log("Produtos: \n"+produtoAntigo);
		//console.log("Categorias: \n"+categoriaAntigo);
		//console.log("Quantidade/Peso: \n"+QuantidadePesoAntigo);
		//console.log("Valor do Produto: \n"+valorProdutoAntigo);
		//console.log("Valor total da linha: \n"+valorAntigo);	
	}
	
});
