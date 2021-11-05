package br.com.projetoindividual.jdbcinterface;

import java.util.List;

import com.google.gson.JsonObject;

import br.com.projetoindividual.modelo.Usuario;

public interface UsuarioDAO {	
	public String inserir(Usuario usuario);
	public List<JsonObject> buscarPorNome(String nome);
	public String deletar(String login);
	public Usuario buscarPorLogin(String login);
	public String alterar(Usuario usuario);	
}
