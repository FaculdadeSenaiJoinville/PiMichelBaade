package br.com.projetoindividual.jdbcinterface;

import java.util.List;

import com.google.gson.JsonObject;

import br.com.projetoindividual.produto.Produto;

public interface ProdutoDAO {

	public String inserir(Produto produto);
	public List<JsonObject> buscarPorNome(String nome);
	public String deletar(int id);
	public String alterar(Produto produto);
	public List<JsonObject> buscarParaVenda(int categoria);
}
