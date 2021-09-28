package br.com.projetoindividual.jdbcinterface;

import java.util.List;

import com.google.gson.JsonObject;

import br.com.projetoindividual.modelo.Categoria;

public interface CategoriaDAO {

	public boolean inserir(Categoria categoria);
	public List<JsonObject> buscarPorNome(String nome);
	public boolean deletar(int id);
	public Categoria buscarPorId(int id);
	public boolean alterar(Categoria categoria);
	
}
