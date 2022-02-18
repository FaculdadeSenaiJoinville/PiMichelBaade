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
import br.com.projetoindividual.jdbc.JDBCProdutoDAO;
import br.com.projetoindividual.produto.Produto;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


@Path("produto")
public class ProdutoRest extends UtilRest {

	
	
		@POST
		@Path("/inserir")
		@Consumes("application/*")
		public Response inserir(String produtoParam) {

			try {
				Produto produto = new Gson().fromJson(produtoParam, Produto.class);

				Conexao conec = new Conexao();
				Connection conexao = conec.abrirConexao();

				JDBCProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);

				String retorno = jdbcProduto.inserir(produto);

				conec.fecharConexao();
 
				return this.buildResponse(retorno);
			} catch (Exception e) {
				e.printStackTrace();
				return this.buildErrorResponse(e.getMessage());

			}

		}
		
		
		@GET
		@Path("/buscar")
		@Consumes("application/*")
		@Produces(MediaType.APPLICATION_JSON)
		public Response buscar(@QueryParam("valorBusca") String nome) {

			try {

				List<JsonObject> listaProdutos = new ArrayList<JsonObject>();

				Conexao conec = new Conexao();
				Connection conexao = conec.abrirConexao();
				JDBCProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
				listaProdutos = jdbcProduto.buscarPorNome(nome);
				conec.fecharConexao();
				String json = new Gson().toJson(listaProdutos);
				
				return this.buildResponse(json);
			} catch (Exception e) {
				e.printStackTrace();
				return this.buildErrorResponse(e.getMessage());

			}

		}
		
		
		@GET
		@Path("/buscarParaVenda")
		@Consumes("application/*")
		@Produces(MediaType.APPLICATION_JSON)
		public Response buscarParaVenda(@QueryParam("categoria") int categoria) {

			try {

				List<JsonObject> listaProdutos = new ArrayList<JsonObject>();

				Conexao conec = new Conexao();
				Connection conexao = conec.abrirConexao();
				JDBCProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
				listaProdutos = jdbcProduto.buscarParaVenda(categoria);
				conec.fecharConexao();
				String json = new Gson().toJson(listaProdutos);
				return this.buildResponse(json);
			} catch (Exception e) {
				e.printStackTrace();
				return this.buildErrorResponse(e.getMessage());

			}

		}
		
		

		@GET
		@Path("/buscarPorNome")
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
				JDBCProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);

				String retorno = jdbcProduto.deletar(id);	
				conec.fecharConexao();
				return this.buildResponse(retorno);
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
				Produto produto = new Produto();
				Conexao conec = new Conexao();
				Connection conexao = conec.abrirConexao();
				JDBCProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
				produto = jdbcProduto.buscarPorId(id);
				conec.fecharConexao();
				return this.buildResponse(produto);
			} catch (Exception e) {
				e.printStackTrace();
				return this.buildErrorResponse(e.getMessage());
			}

		}

		@PUT
		@Path("/alterar")
		@Consumes("application/*")
		public Response alterar(String produtoParam) {

			try {
				Produto produto = new Gson().fromJson(produtoParam, Produto.class);
				Conexao conec = new Conexao();
				Connection conexao = conec.abrirConexao();
				JDBCProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);

				String retorno = jdbcProduto.alterar(produto);

				conec.fecharConexao();
				return this.buildResponse(retorno);
			} catch (Exception e) {
				e.printStackTrace();
				return this.buildErrorResponse(e.getMessage());
			}

		}
	
}
