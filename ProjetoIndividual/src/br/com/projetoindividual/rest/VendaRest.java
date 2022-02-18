package br.com.projetoindividual.rest;

import java.sql.Connection;

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
	public Response atualizaValores(String idProduto) {

		String prodCortada = idProduto.substring(14);
	
		try {
			
			
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCVendaDAO jdbcVenda = new JDBCVendaDAO(conexao);
			String listaValores = jdbcVenda.atualizaValores(prodCortada);
			//System.out.println("Valor que vai retornar: "+listaValores);
			conec.fecharConexao();
			String json = new Gson().toJson(listaValores);		
			
			return this.buildResponse(json);			
			
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	
	
	
}
