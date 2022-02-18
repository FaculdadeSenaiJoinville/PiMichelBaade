SONHOS.relatoriofaturamento = new Object();

$(document).ready(function() {

	SONHOS.relatoriofaturamento = function(){};
	




SONHOS.relatoriofaturamento.filtrar = function(){

	var relatoriofaturamento = new Object();
	relatoriofaturamento.datainicio = document.frmFiltraFaturamento.datainicio.value;
	relatoriofaturamento.datafinal = document.frmFiltraFaturamento.datafinal.value;
	
	$.ajax({
				
				type: "GET",
				url: SONHOS.PATH+"relatoriofaturamento/buscar",
				data: {'datainicio': relatoriofaturamento.datainicio,'datafinal': relatoriofaturamento.datafinal},
				success: function(dados){
					
					dados = JSON.parse(dados);
					console.log(dados);
					$("#relatorioFaturamento").html(SONHOS.relatoriofaturamento.exibir(dados));				
				},
				error: function (info){
					console.log(info);
					SONHOS.exibirAviso(info.responseText);
				}		
			});
	};
	
	//SONHOS.relatoriofaturamento.filtrar();
	
	SONHOS.relatoriofaturamento.exibir = function (listaDeFaturamentos){
		
		var tabela = "<div id='imgPdf'> <table id='tablePdf' class='listaRegistrosRelatorio'>"+
		"<tr class='listaRegistrosRelatorio'>"+
		"<th colspan='3' class='listaRegistrosRelatorio' >Período: "+listaDeFaturamentos[0].dataInicioFormatada+" - "+listaDeFaturamentos[0].dataFinalFormatada+"</th>"+
		"</tr>"+
		"<tr class='listaRegistrosRelatorio'>"+
		"<th class='listaRegistrosRelatorio'>Data</th>"+
		"<th class='listaRegistrosRelatorio'>Quantidade de Vendas</th>"+
		"<th class='listaRegistrosRelatorio'>Total do dia</th>"+
		"</tr>";
		
		if(listaDeFaturamentos != undefined && listaDeFaturamentos.length > 0){
			var total = parseInt(0);
			var quantidadeTotal = parseInt(0);
			for(var i=0;i<listaDeFaturamentos.length; i++){
				tabela += "<tr class='listaRegistrosRelatorio'> "+
				"<td class='listaRegistrosRelatorio'>"+listaDeFaturamentos[i].data+"</td>"+
				"<td class='listaRegistrosRelatorio'>"+listaDeFaturamentos[i].quantidade+"</td>"+
				"<td class='listaRegistrosRelatorio'><a>R$ "+SONHOS.formatarDinheiro(listaDeFaturamentos[i].valorTotal)+"</a></td>"+	
				"</tr>"
				total += listaDeFaturamentos[i].valorTotal;
				quantidadeTotal += listaDeFaturamentos[i].quantidade;
			}
			tabela += "<tr class='listaRegistrosRelatorio' id='rodapeRelatorio'><td class='listaRegistrosRelatorio'>Totais: </td><td class='listaRegistrosRelatorio'> "+quantidadeTotal+" </td><td class='listaRegistrosRelatorio'>R$ "+SONHOS.formatarDinheiro(total)+" </td></tr>";
			tabela += "</table><div class='divImg'> <a class='imgPdf' onclick='SONHOS.relatoriofaturamento.gerarPdf()'><img class='imagemPDF' src='/ProjetoIndividual/imgs/pdf.png'></a></div> </div>";
		}else if(listaDeFaturamentos == ""){
			tabela += "<tr class='listaRegistrosRelatorio'><td colspan='3' class='listaRegistrosRelatorio'>Não foi realizado nenhuma venda no período selecionado</td></tr>";
			tabela += "</table> </div>";
		}
		
		return tabela;
	};




SONHOS.relatoriofaturamento.gerarPdf = function(){
		
	
	
	
	
	
	
	
	
	
	//não mostra a table
	//var doc = new jsPDF();
	//doc.text(35, 25, "Relatório de Faturamento");
	//var source = window.document.getElementsById("tablePdf")[0];
	//doc.fromHTML($('table').get(1));
	//doc.save("RelatorioTeste.pdf");
	
	var pdf = new jsPDF('p','pt','a4');
	pdf.text(200, 50, "Relatório de Faturamento");
	pdf.addHTML(document.getElementById("tablePdf"),0,100,function() {
	pdf.save('RelatorioFaturamento.pdf');
	});	
	
	
	}
	

	
	
});