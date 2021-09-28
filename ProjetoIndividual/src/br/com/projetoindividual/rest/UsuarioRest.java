package br.com.projetoindividual.rest;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.com.projetoindividual.bd.Conexao;
import br.com.projetoindividual.jdbc.JDBCUsuarioDAO;
import br.com.projetoindividual.modelo.Usuario;

@Path("usuario")
public class UsuarioRest extends UtilRest {

	
	
	@POST
	@Path("/inserir")
	@Consumes("application/*")
	public Response inserir(String usuarioParam) {

		try {
			Usuario usuario = new Gson().fromJson(usuarioParam, Usuario.class);

			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();

			JDBCUsuarioDAO jdbcUsuario = new JDBCUsuarioDAO(conexao);

			boolean retorno = jdbcUsuario.inserir(usuario);

			String msg = "";

			if (retorno) {
				msg = "Usuário cadastrado com sucesso!";
			} else {
				msg = "Erro ao cadastrar funcionário.";
			}
			conec.fecharConexao();

			return this.buildResponse(msg);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());

		}

	}

	@GET
	@Path("/buscar")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPorNome(@QueryParam("valorBusca") String nome) {

		try {

			List<JsonObject> listaUsuarios = new ArrayList<JsonObject>();

			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCUsuarioDAO jdbcUsuario = new JDBCUsuarioDAO(conexao);
			listaUsuarios = jdbcUsuario.buscarPorNome(nome);
			conec.fecharConexao();
			String json = new Gson().toJson(listaUsuarios);

			return this.buildResponse(json);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());

		}

	}

	@DELETE
	@Path("/excluir/{login}")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response excluir(@PathParam("login") String login) {

		try {

			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCUsuarioDAO jdbcUsuario = new JDBCUsuarioDAO(conexao);

			boolean retorno = jdbcUsuario.deletar(login);

			String msg = "";

			if (retorno) {
				msg = "Usuário excluido com sucesso!";
			} else {
				msg = "Erro ao excluir usuário.";
			}
			conec.fecharConexao();
			return this.buildResponse(msg);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());

		}

	}

	@GET
	@Path("/buscarPorLogin")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPorLogin(@QueryParam("login") String login) {

		try {
			Usuario usuario = new Usuario();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCUsuarioDAO jdbcUsuario = new JDBCUsuarioDAO(conexao);
			usuario = jdbcUsuario.buscarPorLogin(login);
			conec.fecharConexao();
			return this.buildResponse(usuario);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}

	}

	@PUT
	@Path("/alterar")
	@Consumes("application/*")
	public Response alterar(String usuarioParam) {

		try {
			Usuario usuario = new Gson().fromJson(usuarioParam, Usuario.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCUsuarioDAO jdbcUsuario = new JDBCUsuarioDAO(conexao);

			boolean retorno = jdbcUsuario.alterar(usuario);

			String msg = "";

			if (retorno) {
				msg = "Usuário alterado com sucesso!";
			} else {
				msg = "Erro ao alterar usuário.";
			}
			conec.fecharConexao();
			return this.buildResponse(msg);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}

	}
	
}



	