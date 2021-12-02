package br.com.projetoindividual.jdbcinterface;

import java.util.List;

import com.google.gson.JsonObject;

import br.com.projetoindividual.modelo.Categoria;

public interface CategoriaDAO {

	public String inserir(Categoria categoria);
	public List<JsonObject> buscarPorNome(String nome);
	public String deletar(int id);
	public Categoria buscarPorId(int id);
	public String alterar(Categoria categoria);
	
}
