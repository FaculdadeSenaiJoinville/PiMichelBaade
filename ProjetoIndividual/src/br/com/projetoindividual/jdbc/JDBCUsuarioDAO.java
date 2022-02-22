package br.com.projetoindividual.jdbc;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import br.com.projetoindividual.jdbcinterface.UsuarioDAO;
import br.com.projetoindividual.modelo.Usuario;

public class JDBCUsuarioDAO implements UsuarioDAO {

	private Connection conexao;

	public JDBCUsuarioDAO(Connection conexao) {
		this.conexao = conexao;
	}

	public String inserir(Usuario usuario) {
		String comando = "SELECT login FROM usuarios WHERE cpf =" + usuario.getCpf() + " || email ='"
				+ usuario.getEmail() + "' || login = '" + usuario.getLogin() + "' ";
		PreparedStatement p;
		try {
			p = this.conexao.prepareStatement(comando);
			ResultSet rs = p.executeQuery(comando);
			while (rs.next()) {
				return "Já existe um usuário com o mesmo Login,CPF ou E-mail.";
			}
			String comando2 = "INSERT INTO usuarios (login,senha,nivel_usuario,data_nasc,cpf,nome,email,telefone) VALUES (?,?,?,?,?,?,?,?)";
			String senmd5 = "";
			MessageDigest md = null;
			try {
				md = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			BigInteger hash = new BigInteger(1, md.digest(usuario.getSenha().getBytes()));
			senmd5 = hash.toString(16);
			usuario.setSenha(senmd5);
			PreparedStatement p2 = this.conexao.prepareStatement(comando2);
			p2.setString(1, usuario.getLogin());
			p2.setString(2, usuario.getSenha());
			p2.setInt(3, usuario.getNivel_usuario());
			p2.setString(4, usuario.getData_nasc());
			p2.setLong(5, usuario.getCpf());
			p2.setString(6, usuario.getNome());
			p2.setString(7, usuario.getEmail());
			p2.setLong(8, usuario.getTelefone());
			p2.execute();
			return "Usuário cadastrado com sucesso!";
		} catch (SQLException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public List<JsonObject> buscarPorNome(String nome) {
		String comando = "SELECT * FROM usuarios ";
		if (!nome.equals("")) {
			comando += "WHERE nome LIKE '%" + nome + "%' ";
		}
		comando += " ORDER BY nivel_usuario ASC";
		List<JsonObject> listaUsuarios = new ArrayList<JsonObject>();
		JsonObject usuario;
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while (rs.next()) {
				int nivel_usuario = rs.getInt("nivel_usuario");
				String nivel = "0";
				if (nivel_usuario == 1) {
					nivel = "Administrador";
				} else if (nivel_usuario == 2) {
					nivel = "Caixa";
				} else if (nivel_usuario == 3) {
					nivel = "Estoquista";
				}
				String UsuNome = rs.getString("nome");
				String login = rs.getString("login");
				String email = rs.getString("email");
				usuario = new JsonObject();
				usuario.addProperty("nivel_usuario", nivel);
				usuario.addProperty("nome", UsuNome);
				usuario.addProperty("login", login);
				usuario.addProperty("email", email);
				listaUsuarios.add(usuario);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaUsuarios;
	}

	public String deletar(String login) {
		String comando = "DELETE FROM usuarios WHERE login = ?";
		PreparedStatement p;
		try {
			p = this.conexao.prepareStatement(comando);
			p.setString(1, login);
			p.execute();
			return "Usuário deletado com sucesso!";
		} catch (SQLException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public String buscarSenha(String login) {
		String comando = "SELECT senha FROM usuarios WHERE login = ?";
		try {
			PreparedStatement p = this.conexao.prepareStatement(comando);
			p.setString(1, login);
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				return rs.getString("senha");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Ocorreu um erro ao buscar a senha";
	}

	public Usuario buscarPorLogin(String login) {
		String comando = "SELECT * FROM usuarios WHERE login = ?";
		Usuario usuario = new Usuario();
		try {
			PreparedStatement p = this.conexao.prepareStatement(comando);
			p.setString(1, login);
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				String Usulogin = rs.getString("login");
				String senha = rs.getString("senha");
				int nivel = rs.getInt("nivel_usuario");
				String data_nasc = rs.getString("data_nasc");
				long cpf = rs.getLong("cpf");
				String nome = rs.getString("nome");
				String email = rs.getString("email");
				long telefone = rs.getLong("telefone");
				usuario.setLogin(Usulogin);
				usuario.setSenha(senha);
				usuario.setNivel_usuario(nivel);
				usuario.setData_nasc(data_nasc);
				usuario.setCpf(cpf);
				usuario.setNome(nome);
				usuario.setEmail(email);
				usuario.setTelefone(telefone);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usuario;
	}

	public String alterar(Usuario usuario) {
		String comando = "SELECT login FROM usuarios WHERE (cpf =" + usuario.getCpf() + " || email ='"
				+ usuario.getEmail() + "') && login != '" + usuario.getLogin() + "' ";
		PreparedStatement p;
		try {
			p = this.conexao.prepareStatement(comando);
			ResultSet rs = p.executeQuery(comando);
			while (rs.next()) {
				return "Já existe um usuário com o mesmo Login,CPF ou E-mail.";
			}
			String comando2 = "";
			boolean alterarSenha = true;
			if (usuario.getSenha().equals("")) {
				comando2 = "UPDATE usuarios SET nivel_usuario=?,data_nasc=?,cpf=?,nome=?,email=?,telefone=? WHERE login = ?";
				alterarSenha = false;
			} else {
				comando2 = "UPDATE usuarios SET nivel_usuario=?,data_nasc=?,cpf=?,nome=?,email=?,telefone=?,senha=? WHERE login = ?";
			}
			PreparedStatement p2 = this.conexao.prepareStatement(comando2);
			String senmd5 = "";
			MessageDigest md = null;
			try {
				md = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			BigInteger hash = new BigInteger(1, md.digest(usuario.getSenha().getBytes()));
			senmd5 = hash.toString(16);
			usuario.setSenha(senmd5);
			p2.setInt(1, usuario.getNivel_usuario());
			p2.setString(2, usuario.getData_nasc());
			p2.setLong(3, usuario.getCpf());
			p2.setString(4, usuario.getNome());
			p2.setString(5, usuario.getEmail());
			p2.setLong(6, usuario.getTelefone());
			if (alterarSenha) {
				p2.setString(7, usuario.getSenha());
				p2.setString(8, usuario.getLogin());
			} else {
				p2.setString(7, usuario.getLogin());
			}
			p2.executeUpdate();
			return "Usuário alterado com sucesso!";
		} catch (SQLException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public boolean validaExistencia(String login) {
		String comando = "SELECT login FROM usuarios WHERE login = '" + login + "' ";
		PreparedStatement p;
		try {
			p = this.conexao.prepareStatement(comando);
			ResultSet rs = p.executeQuery(comando);
			while (rs.next()) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
