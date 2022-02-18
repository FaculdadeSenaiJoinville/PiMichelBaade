package br.com.projetoindividual.rest;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.com.projetoindividual.bd.Conexao;
import br.com.projetoindividual.jdbc.JDBCConsultaVendaDAO;

@Path("consultavenda")
public class ConsultaVendaRest extends UtilRest  {

	@GET
	@Path("/produtosMaisVendidos")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response produtosMaisVendidos(@QueryParam("datainicio") String dataInicio,
			@QueryParam("datafinal") String dataFinal) {
		try {
			if (dataInicio.equals("") || dataFinal.equals("")) {
				return this.buildErrorResponse("Por favor preencha corretamente os campos de data inicial/data final!");
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date1 = sdf.parse(dataInicio);
			java.util.Date date2 = sdf.parse(dataFinal);
			if (date1.after(date2)) {
				return this.buildErrorResponse(
						"Não é possível filtrar um período onde a data inicial é posterior à data final!");
			}
			List<JsonObject> listaRegistros = new ArrayList<JsonObject>();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCConsultaVendaDAO jdbcConsultaVenda = new JDBCConsultaVendaDAO(conexao);
			listaRegistros = jdbcConsultaVenda.produtosMaisVendidos(dataInicio, dataFinal);
			System.out.println(listaRegistros);
			conec.fecharConexao();
			String json = new Gson().toJson(listaRegistros);
			return this.buildResponse(json);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@GET
	@Path("/produtosMaisVendidosKg")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response produtosMaisVendidosKg(@QueryParam("datainicio") String dataInicio,
			@QueryParam("datafinal") String dataFinal) {
		try {
			if (dataInicio.equals("") || dataFinal.equals("")) {
				return this.buildErrorResponse("Por favor preencha corretamente os campos de data inicial/data final!");
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date1 = sdf.parse(dataInicio);
			java.util.Date date2 = sdf.parse(dataFinal);
			if (date1.after(date2)) {
				return this.buildErrorResponse(
						"Não é possível filtrar um período onde a data inicial é posterior à data final!");
			}
			List<JsonObject> listaRegistros = new ArrayList<JsonObject>();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCConsultaVendaDAO jdbcConsultaVenda = new JDBCConsultaVendaDAO(conexao);
			listaRegistros = jdbcConsultaVenda.produtosMaisVendidosKg(dataInicio, dataFinal);
			System.out.println(listaRegistros);
			conec.fecharConexao();
			String json = new Gson().toJson(listaRegistros);
			return this.buildResponse(json);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@GET
	@Path("/produtosMaisVendidosUnidade")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response produtosMaisVendidosUnidade(@QueryParam("datainicio") String dataInicio,
			@QueryParam("datafinal") String dataFinal) {
		try {
			if (dataInicio.equals("") || dataFinal.equals("")) {
				return this.buildErrorResponse("Por favor preencha corretamente os campos de data inicial/data final!");
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date1 = sdf.parse(dataInicio);
			java.util.Date date2 = sdf.parse(dataFinal);
			if (date1.after(date2)) {
				return this.buildErrorResponse(
						"Não é possível filtrar um período onde a data inicial é posterior à data final!");
			}
			List<JsonObject> listaRegistros = new ArrayList<JsonObject>();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCConsultaVendaDAO jdbcConsultaVenda = new JDBCConsultaVendaDAO(conexao);
			listaRegistros = jdbcConsultaVenda.produtosMaisVendidosUnidade(dataInicio, dataFinal);
			System.out.println(listaRegistros);
			conec.fecharConexao();
			String json = new Gson().toJson(listaRegistros);
			return this.buildResponse(json);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}	
}