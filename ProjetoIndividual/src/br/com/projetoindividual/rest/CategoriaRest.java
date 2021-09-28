package br.com.projetoindividual.rest;

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
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import br.com.projetoindividual.bd.Conexao;
import br.com.projetoindividual.jdbc.JDBCCategoriaDAO;
import br.com.projetoindividual.modelo.Categoria;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


@Path("categoria")
public class CategoriaRest extends UtilRest {

	@POST
	@Path("/inserir")
	@Consumes("application/*")
	public Response inserir(String categoriaParam) {

		try {
			Categoria categoria = new Gson().fromJson(categoriaParam, Categoria.class);

			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();

			JDBCCategoriaDAO jdbcCategoria = new JDBCCategoriaDAO(conexao);

			boolean retorno = jdbcCategoria.inserir(categoria);

			String msg = "";

			if (retorno) {
				msg = "Categoria cadastrada com sucesso!";
			} else {
				msg = "Erro ao cadastrar categoria.";
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

			List<JsonObject> listaCategorias = new ArrayList<JsonObject>();

			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCCategoriaDAO jdbcCategoria = new JDBCCategoriaDAO(conexao);
			listaCategorias = jdbcCategoria.buscarPorNome(nome);
			conec.fecharConexao();
			String json = new Gson().toJson(listaCategorias);

			return this.buildResponse(json);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());

		}

	}

	@DELETE
	@Path("/excluir/{id}")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response excluir(@PathParam("id") int id) {

		try {

			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCCategoriaDAO jdbcCategoria = new JDBCCategoriaDAO(conexao);

			boolean retorno = jdbcCategoria.deletar(id);

			String msg = "";

			if (retorno) {
				msg = "Categoria excluida com sucesso!";
			} else {
				msg = "Erro ao excluir categoria.";
			}
			conec.fecharConexao();
			return this.buildResponse(msg);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());

		}

	}

	@GET
	@Path("/buscarPorId")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPorId(@QueryParam("id") int id) {

		try {
			Categoria categoria = new Categoria();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCCategoriaDAO jdbcCategoria = new JDBCCategoriaDAO(conexao);
			categoria = jdbcCategoria.buscarPorId(id);
			conec.fecharConexao();
			return this.buildResponse(categoria);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}

	}

	@PUT
	@Path("/alterar")
	@Consumes("application/*")
	public Response alterar(String categoriaParam) {

		try {
			Categoria categoria = new Gson().fromJson(categoriaParam, Categoria.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCCategoriaDAO jdbcCategoria = new JDBCCategoriaDAO(conexao);

			boolean retorno = jdbcCategoria.alterar(categoria);

			String msg = "";

			if (retorno) {
				msg = "Categoria alterada com sucesso!";
			} else {
				msg = "Erro ao alterar categoria.";
			}
			conec.fecharConexao();
			return this.buildResponse(msg);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}

	}
	
}
