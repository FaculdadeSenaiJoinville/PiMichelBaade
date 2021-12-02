package br.com.projetoindividual.jdbc;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Random;

import org.apache.commons.mail.SimpleEmail;

import br.com.projetoindividual.jdbcinterface.RedefinirSenhaDAO;

public class JDBCRedefinirSenha implements RedefinirSenhaDAO {

	private Connection conexao;

	public JDBCRedefinirSenha(Connection conexao) {
		this.conexao = conexao;
	}

	@SuppressWarnings("deprecation")
	public String consultarPorEmail(String email) {

		try {
			String comando = "SELECT * from usuarios WHERE email = '" + email + "' ";

			java.sql.Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			if (rs.next()) {
				String novaSenha = alterar(email);

				SimpleEmail emailEnviar = new SimpleEmail();
				try {
					emailEnviar.setDebug(true);
					emailEnviar.setSmtpPort(465);
					emailEnviar.setSslSmtpPort("465");
					emailEnviar.setHostName("smtp.gmail.com");
					emailEnviar.setAuthentication("softwaredossonhos@gmail.com", "SS159357");
					emailEnviar.setSSL(true);
					emailEnviar.addTo(email); // pode ser qualquer um email
					emailEnviar.setFrom("softwaredossonhos@gmail.com"); // aqui necessita ser o email que voce fara a
																		// autenticacao
					emailEnviar.setSubject("Alteração de senha");
					emailEnviar.setMsg("Olá, sua senha no Software dos Sonhos foi redefinida para: " + novaSenha);
					emailEnviar.send();
				} catch (Exception e) {

					System.out.println(e.getMessage());

				}
				return "Sua senha foi alterada corretamente e estará no seu e-mail!";
			} else {
				return "Não existe nenhum usuário com o e-mail informado!";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public String alterar(String email) {

		try {

			String comando2 = "UPDATE usuarios SET senha=? WHERE email = ?";

			PreparedStatement p2 = this.conexao.prepareStatement(comando2);
			Random ran = new Random();
			String[] caracteres = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g",
					"h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A",
					"B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
					"V", "W", "X", "Y", "Z" };
			String senhaNova = "";

			for (int i = 0; i < 8; i++) {
				int a = ran.nextInt(caracteres.length);
				senhaNova += caracteres[a];
			}
			System.out.println("Senha nova: " + senhaNova);

			String encoded = new String(Base64.getEncoder().encode(senhaNova.getBytes()));
			System.out.println(encoded);
			String senmd5 = "";

			MessageDigest md = null;

			try {
				md = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}

			md.update(encoded.getBytes());
			byte[] bytes = md.digest();
			BigInteger hash = new BigInteger(1, bytes);

			System.out.println(bytes);
			senmd5 = hash.toString(16);
			System.out.println(senmd5);

			p2.setString(1, senmd5);
			p2.setString(2, email);

			System.out.println(p2);
			p2.executeUpdate();

			System.out.println("return senhaNova: " + senhaNova);
			return senhaNova;
		} catch (SQLException e) {
			e.printStackTrace();
			return e.getMessage();
		}

	}

}
