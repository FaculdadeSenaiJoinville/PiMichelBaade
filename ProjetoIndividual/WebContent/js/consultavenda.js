SONHOS.consultavenda = new Object();

$(document).ready(function() {

SONHOS.consultavenda = function(){};
	
SONHOS.consultavenda.filtrar = function(){

	var consultavenda = new Object();
	consultavenda.datainicio = document.frmFiltraConsultaVenda.datainicio.value;
	consultavenda.datafinal = document.frmFiltraConsultaVenda.datafinal.value;

	$.ajax({
	    type: "GET",
	    url: SONHOS.PATH + "consultavenda/produtosMaisVendidos",
	    data: {
	        'datainicio': consultavenda.datainicio,
	        'datafinal': consultavenda.datafinal
	    },
	    success: function(dados) {
	        dados = JSON.parse(dados);
	        console.log(dados);

	        google.charts.load('current', {
	            packages: ['corechart', 'bar']
	        });
	        google.charts.setOnLoadCallback(drawBasic);

	        function drawBasic() {

	            var data = new google.visualization.DataTable();
	            data.addColumn('string', 'Produtos que mais apareceram em vendas');
	            data.addColumn('number', 'Aparição nas vendas');

	            for (var i = 0; i < dados.length; i++) {
	                data.addRow([dados[i].nome, dados[i].produtosAparicao]);
	            }

	            var options = {
	                title: 'Produtos que mais apareceram em vendas',
	                colors: ['#eeff00'],
	                backgroundColor: '#66ccff',
	                is3D: true,
	                hAxis: {
	                    title: 'Produtos'
	                },
	                vAxis: {
	                    title: 'Aparição'
	                }
	            };

	            var chart = new google.visualization.ColumnChart(
	                document.getElementById('chartProdutosMaisVendidos'));

	            chart.draw(data, options);
	        }

	    },
	    error: function(info) {
	        console.log(info);
	        SONHOS.exibirAviso(info.responseText);
	    }
	});
	$.ajax({
	    type: "GET",
	    url: SONHOS.PATH + "consultavenda/produtosMaisVendidosKg",
	    data: {
	        'datainicio': consultavenda.datainicio,
	        'datafinal': consultavenda.datafinal
	    },
	    success: function(dados) {

	        dados = JSON.parse(dados);
	        console.log(dados);

	        google.charts.load('current', {
	            packages: ['corechart', 'bar']
	        });
	        google.charts.setOnLoadCallback(drawBasic);

	        function drawBasic() {

	            var data = new google.visualization.DataTable();
	            data.addColumn('string', 'Produtos mais vendidos por Kg');
	            data.addColumn('number', 'Quantidade por Kg');

	            for (var i = 0; i < dados.length; i++) {
	                data.addRow([dados[i].nome, dados[i].quantidade]);
	            }

	            var options = {
	                title: 'Produtos mais vendidos por Kg',
	                colors: ['#db832a'],
	                backgroundColor: '#66ccff',
	                is3D: true,
	                hAxis: {
	                    title: 'Produtos(Kg)'
	                },
	                vAxis: {
	                    title: 'Quantidade(Kg)'
	                }
	            };

	            var chart = new google.visualization.ColumnChart(
	                document.getElementById('chartProdutosMaisVendidosKg'));

	            chart.draw(data, options);
	        }

	    },
	    error: function(info) {
	        console.log(info);
	        SONHOS.exibirAviso(info.responseText);
	    }
	});
	$.ajax({
	    type: "GET",
	    url: SONHOS.PATH + "consultavenda/produtosMaisVendidosUnidade",
	    data: {
	        'datainicio': consultavenda.datainicio,
	        'datafinal': consultavenda.datafinal
	    },
	    success: function(dados) {

	        dados = JSON.parse(dados);
	        console.log(dados);

	        google.charts.load('current', {
	            packages: ['corechart', 'bar']
	        });
	        google.charts.setOnLoadCallback(drawBasic);

	        function drawBasic() {

	            var data = new google.visualization.DataTable();
	            data.addColumn('string', 'Produtos mais vendidos por unidade');
	            data.addColumn('number', 'Quantidade por Unidade');

	            for (var i = 0; i < dados.length; i++) {
	                data.addRow([dados[i].nome, dados[i].quantidade]);
	            }

	            var options = {
	                title: 'Produtos mais vendidos por unidade',
	                colors: ['#db2a2a'],
	                backgroundColor: '#66ccff',
	                is3D: true,
	                hAxis: {
	                    title: 'Produtos(Un.)'
	                },
	                vAxis: {
	                    title: 'Quantidade(Un.)'
	                }
	            };

	            var chart = new google.visualization.ColumnChart(
	                document.getElementById('chartProdutosMaisVendidosUnidade'));

	            chart.draw(data, options);
	        }

	    },
	    error: function(info) {
	        console.log(info);
	        SONHOS.exibirAviso(info.responseText);
	    }
	});
	
	};
	
});