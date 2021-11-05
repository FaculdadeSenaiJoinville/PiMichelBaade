package Conexao;

import java.sql.Connection;
import com.mysql.cj.jdbc.*;

public class Conexao {

	private Connection conexao;
	
	public Connection abrirConexao() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexao = (Connection) java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/bdusuario", "root","root");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conexao;
	}
	
	public void fecharConexao() {
		try {
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
