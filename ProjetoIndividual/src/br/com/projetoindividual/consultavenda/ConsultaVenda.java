package br.com.projetoindividual.consultavenda;

import java.io.Serializable;
import java.util.List;

import br.com.projetoindividual.modelo.ProdutoVenda;

public class ConsultaVenda implements Serializable {

	private static final long serialVersionUID = 1L;
	private String dataInicial;
	private String dataFinal;
	
	
	private List<ProdutoVenda> produtoMaisRecorrente;
	
	private List<ProdutoVenda> produtoMaisVendidoKg;
	
	private List<ProdutoVenda> produtoMaisVendidoUnidade;
	
	
	public String getDataInicial() {
		return dataInicial;
	}
	public void setDataInicial(String dataInicial) {
		this.dataInicial = dataInicial;
	}
	public String getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(String dataFinal) {
		this.dataFinal = dataFinal;
	}
	public List<ProdutoVenda> getProdutoMaisRecorrente() {
		return produtoMaisRecorrente;
	}
	public void setProdutoMaisRecorrente(List<ProdutoVenda> produtoMaisRecorrente) {
		this.produtoMaisRecorrente = produtoMaisRecorrente;
	}
	public List<ProdutoVenda> getProdutoMaisVendidoKg() {
		return produtoMaisVendidoKg;
	}
	public void setProdutoMaisVendidoKg(List<ProdutoVenda> produtoMaisVendidoKg) {
		this.produtoMaisVendidoKg = produtoMaisVendidoKg;
	}
	public List<ProdutoVenda> getProdutoMaisVendidoUnidade() {
		return produtoMaisVendidoUnidade;
	}
	public void setProdutoMaisVendidoUnidade(List<ProdutoVenda> produtoMaisVendidoUnidade) {
		this.produtoMaisVendidoUnidade = produtoMaisVendidoUnidade;
	}

	
}
