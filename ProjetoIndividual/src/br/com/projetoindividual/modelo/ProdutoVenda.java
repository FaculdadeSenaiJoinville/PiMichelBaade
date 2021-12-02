package br.com.projetoindividual.modelo;

import java.io.Serializable;

public class ProdutoVenda implements Serializable {

private static final long serialVersionUID = 1L;
	
	private int idVenda;
	private int idProduto;
	private float quantidade;
	private float valor;

	public int getIdVenda() {
		return idVenda;
	}
	public void setIdVenda(int idVenda) {
		this.idVenda = idVenda;
	}
	public int getIdProduto() {
		return idProduto;
	}
	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}
	public float getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(float quantidade) {
		this.quantidade = quantidade;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	
}
