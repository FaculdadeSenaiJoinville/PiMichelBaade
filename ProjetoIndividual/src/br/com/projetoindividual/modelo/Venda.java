package br.com.projetoindividual.modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Venda implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String data;
	private String login;
	private ArrayList<ProdutoVenda> produtos = new ArrayList<ProdutoVenda>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public ArrayList<ProdutoVenda> getProdutos() {
		return produtos;
	}
	public void setProdutos(ArrayList<ProdutoVenda> produtos) {
		this.produtos = produtos;
	}
	
}
