package br.com.projetoindividual.rest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.w3c.dom.Document;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.com.projetoindividual.bd.Conexao;
import br.com.projetoindividual.faturamento.Faturamento;
import br.com.projetoindividual.jdbc.JDBCRelatorioFaturamentoDAO;

@Path("relatoriofaturamento")
public class RelatorioFaturamentoRest extends UtilRest  {

	@GET
	@Path("/buscar")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscar(@QueryParam("datainicio") String dataInicio,@QueryParam("datafinal") String dataFinal) {
		
		try {
			if (dataInicio.equals("")||dataFinal.equals("")) {
				return this.buildErrorResponse("Por favor preencha corretamente os campos de data inicial/data final!");
				
			}
	        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	            java.util.Date date1 = sdf.parse(dataInicio);
	            java.util.Date date2 = sdf.parse(dataFinal);
	            
	            if (date1.after(date2)) {
				return this.buildErrorResponse("Não é possível filtrar um período onde a data inicial é posterior à data final!");
					
				 }
	          
		
			List<JsonObject> listaRegistros = new ArrayList<JsonObject>();

			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCRelatorioFaturamentoDAO jdbcRelatorioFaturamento = new JDBCRelatorioFaturamentoDAO(conexao);
			listaRegistros = jdbcRelatorioFaturamento.buscar(dataInicio,dataFinal);
			conec.fecharConexao();
			String json = new Gson().toJson(listaRegistros);
			
			return this.buildResponse(json);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());

		}

	}
	
	
	
		
		
	}

		

