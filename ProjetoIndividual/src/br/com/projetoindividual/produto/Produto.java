package br.com.projetoindividual.produto;

import java.io.Serializable;

public class Produto implements Serializable{


	private static final long serialVersionUID = 1L;
	private int id;
	private String nome;
	private float valor;
	private int isvalorunidade;
	private int categoriaId;
	
	public int getCategoriaId() {
		return categoriaId;
	}
	public void setCategoriaId(int categoriaId) {
		this.categoriaId = categoriaId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	public int getIsValorUnidade() {
		return isvalorunidade;
	}
	public void setIsValorUnidade(int isvalorunidade) {
		this.isvalorunidade = isvalorunidade;
	}
	
	
}
