package br.com.projetoindividual.modelo;

import java.io.Serializable;
import java.util.Date;

public class Usuario implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private String login;
	private String senha;
	private int nivel_usuario;
	private String data_nasc;
	private long cpf;
	private String nome;
	private String email;
	private long telefone;

	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public int getNivel_usuario() {
		return nivel_usuario;
	}
	public void setNivel_usuario(int nivel_usuario) {
		this.nivel_usuario = nivel_usuario;
	}
	public String getData_nasc() {
		return data_nasc;
	}
	public void setData_nasc(String data_nasc) {
		this.data_nasc = data_nasc;
	}
	public long getCpf() {
		return cpf;
	}
	public void setCpf(long cpf) {
		this.cpf = cpf;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getTelefone() {
		return telefone;
	}
	public void setTelefone(long telefone) {
		this.telefone = telefone;
	}
	
}
