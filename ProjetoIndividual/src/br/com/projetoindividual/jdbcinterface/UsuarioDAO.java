package br.com.projetoindividual.jdbcinterface;

import java.util.List;

import com.google.gson.JsonObject;

import br.com.projetoindividual.modelo.Usuario;

public interface UsuarioDAO {

	
	public boolean inserir(Usuario usuario);
	public List<JsonObject> buscarPorNome(String nome);
	public boolean deletar(String login);
	public Usuario buscarPorLogin(String login);
	public boolean alterar(Usuario usuario);
	
}
