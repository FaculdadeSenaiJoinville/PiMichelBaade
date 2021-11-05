package AutenticacaoJdbcDao;

import java.sql.Connection;
import java.sql.ResultSet;

import ArmzanaDadosUsuario.ArmaDadosUsuario;


public class JDBCAutenticaDAO {
	
	private Connection conexao;
	
	public JDBCAutenticaDAO(Connection conexao) {
		this.conexao = conexao;
	}
	
	
	public boolean consultar(ArmaDadosUsuario dadosautentica) {
		
		try {

			String comando = "SELECT * from dadosusuario WHERE usuario = '" + dadosautentica.getUsuario()
					+ "' and senha = '" + dadosautentica.getSenha() + "'";
			java.sql.Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			if (rs.next()) {
				System.out.println("true");
				return true;
			} else {
				System.out.println("false");
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
}
