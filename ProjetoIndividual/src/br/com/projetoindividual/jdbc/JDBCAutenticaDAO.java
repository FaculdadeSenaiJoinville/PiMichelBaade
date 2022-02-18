package br.com.projetoindividual.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;

import br.com.projetoindividual.jdbcinterface.AutenticaDAO;
import br.com.projetoindividual.modelo.Usuario;

public class JDBCAutenticaDAO implements AutenticaDAO {

	private Connection conexao;

	public JDBCAutenticaDAO(Connection conexao) {
		this.conexao = conexao;
	}

	public int consultar(Usuario usuario) {

		try {
			String comando = "SELECT * from usuarios WHERE login = '" + usuario.getLogin() + "' and senha = '"
					+ usuario.getSenha() + "'";
			
			java.sql.Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			if (rs.next()) {
				return rs.getInt("nivel_usuario");
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

}
