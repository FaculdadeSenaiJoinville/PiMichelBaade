package br.com.projetoindividual.rest;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.com.projetoindividual.bd.Conexao;
import br.com.projetoindividual.jdbc.JDBCVendaDAO;
import br.com.projetoindividual.modelo.Venda;

@Path("venda")
public class VendaRest extends UtilRest{

	@POST
	@Path("/inserir")
	@Consumes("application/*")
	public Response inserir(String vendaParam, @Context HttpServletRequest req) {

		try {
			Venda venda = new Gson().fromJson(vendaParam, Venda.class);
			
			HttpSession sessao = req.getSession();		
			venda.setLogin((String) sessao.getAttribute("login"));
			
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCVendaDAO jdbcVenda = new JDBCVendaDAO(conexao);
			String msg = jdbcVenda.inserir(venda);
			
			conec.fecharConexao();
			return this.buildResponse(msg);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	
	@POST
	@Path("/atualizaValores")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response atualizaValores(String produtoAntigo) {

		System.out.println("normal: "+produtoAntigo);
		List<JsonObject> listaValores = new ArrayList<JsonObject>();
		String prodCortada = produtoAntigo.substring(14);
		
		System.out.println("cortada: "+prodCortada);
		String [] idProdutos = prodCortada.split("\\s*,\\s*");
		try {
			
			
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCVendaDAO jdbcVenda = new JDBCVendaDAO(conexao);
			listaValores = jdbcVenda.atualizaValores(idProdutos);
			System.out.println(listaValores);
			conec.fecharConexao();
			String json = new Gson().toJson(listaValores);		
			
			return this.buildResponse(json);			
			
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	
	
	
}
