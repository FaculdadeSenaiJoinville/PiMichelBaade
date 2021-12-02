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
			//System.out.println("usuario: "+usuario.getLogin());
			//System.out.println("senha: "+usuario.getSenha());
			
			String comando = "SELECT * from usuarios WHERE login = '" + usuario.getLogin() + "' and senha = '"
					+ usuario.getSenha() + "'";
			
			//System.out.println(comando);
			
			java.sql.Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			if (rs.next()) {
				System.out.println("true");
				return rs.getInt("nivel_usuario");
			} else {
				System.out.println("false");
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

}
