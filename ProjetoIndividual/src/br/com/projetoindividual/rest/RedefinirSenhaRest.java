package br.com.projetoindividual.rest;

import java.sql.Connection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.projetoindividual.bd.Conexao;
import br.com.projetoindividual.jdbc.JDBCRedefinirSenha;

@Path("redefinirsenha")
public class RedefinirSenhaRest extends UtilRest {

	@GET
	@Path("/buscarPorEmail")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPorEmail(@QueryParam("email") String email) {

		try {
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCRedefinirSenha jdbcRedefinirSenha = new JDBCRedefinirSenha(conexao);
			String existeEmail = jdbcRedefinirSenha.consultarPorEmail(email);
			conec.fecharConexao();
			return this.buildResponse(existeEmail);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}

	}
	
}
