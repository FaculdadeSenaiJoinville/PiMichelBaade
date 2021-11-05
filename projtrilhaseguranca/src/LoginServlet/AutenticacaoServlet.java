package LoginServlet;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ArmzanaDadosUsuario.ArmaDadosUsuario;
import AutenticacaoJdbcDao.JDBCAutenticaDAO;
import Conexao.Conexao;

public class AutenticacaoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	


	private void process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NoSuchAlgorithmException {
		ArmaDadosUsuario dadosautentica = new ArmaDadosUsuario();
		
		dadosautentica.setUsuario(request.getParameter("usuario"));
		String textodeserializado = new String(Base64.getUrlDecoder().decode(request.getParameter("senha")));
		System.out.println("Texto deserializado: "+textodeserializado);
		
		String senmd5 = "";
		
		MessageDigest md = null;
		
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		BigInteger hash = new BigInteger(1, md.digest(request.getParameter("senha").getBytes()));
		
		senmd5 = hash.toString(16);
		System.out.println(senmd5);
		dadosautentica.setSenha(senmd5);
		Conexao conec = new Conexao();
		Connection conexao = (Connection)conec.abrirConexao();
		
		JDBCAutenticaDAO jdbAutentica =  new JDBCAutenticaDAO(conexao);
		boolean retorno = jdbAutentica.consultar(dadosautentica);
		
		if (retorno) {
			HttpSession sessao = request.getSession();
			sessao.setAttribute("login", request.getParameter("usuario"));
			//response.sendRedirect("principal.html");
			response.sendRedirect("Acesso/principal.html");
		} else {
			//response.sendRedirect("index.html");
			response.sendRedirect("erro.html");
		}
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			process(request, response);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			process(request, response);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
