SONHOS.venda = new Object();

$(document).ready(function() {

	SONHOS.venda = function(){};

	produtoAntigo = new Array();
	produtoAntigo[0] = "";
	
	categoriaAntigo = new Array();
	categoriaAntigo[0] = "";
	
	QuantidadePesoAntigo = new Array();
	QuantidadePesoAntigo[0] = "";
	
	valorAntigo = new Array();
	valorAntigo[0] = "";
		
	var valorTotalAntigo = parseFloat(0);
	
	SONHOS.venda.guardaValores = function(id){
		
		var produtoAtual = document.getElementsByName('selProduto[]');
		var categoriaAtual = document.getElementsByName('selCategoria[]');
		var QuantidadePesoAtual = document.getElementsByName('QuantidadePeso[]');
		var valorAtual = document.getElementsByName('valor[]');
			
		for (var i = 0; i < $("tr.detalhes").length; i++) {
			produtoAntigo[i] = produtoAtual[i].value;
			categoriaAntigo[i] = categoriaAtual[i].value;
			QuantidadePesoAntigo[i] = QuantidadePesoAtual[i].value;
			valorAntigo[i] = valorAtual[i].value;
			console.log("i="+i+" : "+valorAntigo[i]);
		}	
		console.log(valorTotalAntigo);
		//valorTotalAntigo = valorTotalAntigo.replace(".", ",");
		
		$("#totalValores").html("Total: R$ "+SONHOS.formatarDinheiro(valorTotalAntigo));
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
	}
	
	
	SONHOS.venda.carregarProdutos = function(botao) {
		
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
				$(selectProduto[linhaAlterada]).html("");
				
				if (produtos.length) {
					$(selectProduto[linhaAlterada]).removeClass("aviso");
					
					var option = document.createElement("option");
					option.setAttribute("value","");
					option.innerHTML = ("Escolha");
					$(selectProduto[linhaAlterada]).append(option);
					for (var i = 0; i < produtos.length; i++) {
						var option = document.createElement("option");
						option.setAttribute("value",produtos[i].id);
						option.innerHTML = (produtos[i].nome);
						$(selectProduto[linhaAlterada]).append(option);
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
	}
	
	SONHOS.venda.validaDetalhe = function() {
	
	var categoriasValidar = document.getElementsByName('selCategoria[]');
	var produtosValidar = document.getElementsByName('selProduto[]');
	
	var qtdeValidar = document.getElementsByName('QuantidadePeso[]');
	var valorValidar = document.getElementsByName('valor[]');
	
	for (var i = 0; i < produtosValidar.length; i++) {
		
		var linha = i+1;
		if((qtdeValidar[i].value<="0")){
			SONHOS.exibirAviso("Não é permitido inserir um produto com quantidade negativa(linha "+linha+").");
			return false;
		}
		if ((produtosValidar[i].value=="")||(categoriasValidar[i].value=="")||(qtdeValidar[i].value=="")||(valorValidar[i].value=="")) {
			
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
				async:false,
				success: function (msg) {
					console.log(msg);
					if(msg.includes('Venda realizada com sucesso!')){
						SONHOS.carregaPagina('venda');
					}
					SONHOS.exibirAviso(msg);
				},
				error: function (info) {
				
					SONHOS.exibirAviso("Erro ao cadastrar um novo produto: "+info.status+" - "+info.statusText);
				}
			});
			
		}
		
	}
	
	
SONHOS.venda.AtualizaValor = function() {
		
	var linhaAlterada = "0";
	
	var selectProduto = document.getElementsByName('selProduto[]');
	
	for (var j = 0; j < selectProduto.length; j++) {
		
		if ((produtoAntigo[j] != selectProduto[j].value)) {
			linhaAlterada = j;
		}
		
	}
	
	var QuantidadePesoAtual = document.getElementsByName('QuantidadePeso[]');
	
	for (var j = 0; j < QuantidadePesoAtual.length; j++) {
		
		if ((QuantidadePesoAntigo[j] != QuantidadePesoAtual[j].value)) {
			linhaAlterada = j;
		}
		
	}
		$.ajax({
			type: "POST",
			url: SONHOS.PATH + "venda/atualizaValores",
			data:"produtoAntigo="+selectProduto[linhaAlterada].value,
			async:false,
			success: function (valoresProdutos) {
				
				valoresProdutos = JSON.parse(valoresProdutos);
				
				var venda = new Object();
				var quantidades = document.getElementsByName('QuantidadePeso[]');
				if(quantidades[linhaAlterada].value.indexOf(",") > -1){
					caracteresubstitui = quantidades[linhaAlterada].value.indexOf();
					console.log(caracteresubstitui);
					quantidades[linhaAlterada].value = quantidades[linhaAlterada].value.replace(",", ".");
				}
				var valores = document.getElementsByName('valor[]');
				var idprodutos = document.getElementsByName('selProduto[]');
				
				venda.produtos = new Array(valoresProdutos.length);
				valores[linhaAlterada].value = (valoresProdutos * quantidades[linhaAlterada].value);
				
				valorTotalAntigo = parseFloat(0);
				for (var i = 0; i < valores.length; i++) {
					valorTotalAntigo = parseFloat(valorTotalAntigo) + parseFloat(valores[i].value); 
				}
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
	}
	
});
