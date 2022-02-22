SONHOS = new Object();

$(document).ready(function() {
	SONHOS.PATH = "/ProjetoIndividual/rest/";
	
	$("header").load("/ProjetoIndividual/pages/admin/general/header.html");
	$("footer").load("/ProjetoIndividual/pages/admin/general/footer.html");
		
	SONHOS.carregaPagina = function(pagename){
		
		if($(".ui-dialog"))
			$(".ui-dialog").remove();
		
		$("section").empty();
		
		$("section").load(pagename+"/", function(response, status, info) {
			if(status == "error"){
				var msg = "Houve um erro ao encontrar a página: "+ info.status +" - " + info.statusText;
				$("section").html(msg);
			}
		});	
	}

	SONHOS.exibirAviso = function(aviso){
		var modal = {
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
		$("#modalAviso").html(aviso);
		$("#modalAviso").dialog(modal);
	};
	
	
	
});

SONHOS.formatarDinheiro = function(valor){
	
	return valor.toFixed(2).replace('.',',').replace(/(\d)(?=(\d{3})+\,)/g, "$1.");	
}