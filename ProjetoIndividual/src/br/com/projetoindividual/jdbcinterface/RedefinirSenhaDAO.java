package br.com.projetoindividual.jdbcinterface;


public interface RedefinirSenhaDAO {

	public String consultarPorEmail(String email);
	public String alterar(String email);
	
}
