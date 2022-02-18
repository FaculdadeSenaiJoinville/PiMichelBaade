package br.com.projetoindividual.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import br.com.projetoindividual.jdbcinterface.RelatorioFaturamentoDAO;

public class JDBCRelatorioFaturamentoDAO implements RelatorioFaturamentoDAO {

	private Connection conexao;

	public JDBCRelatorioFaturamentoDAO(Connection conexao) {
		this.conexao = conexao;
	}

	public List<JsonObject> buscar(String dataInicio, String dataFinal) {

		
		List<JsonObject> listaFaturamentos = new ArrayList<JsonObject>();
		JsonObject faturamento;
		
		
		String comando = "SELECT CAST(data_venda AS DATE) as data, COUNT(distinct vendas.id) as quantidade, SUM(vendas_has_produtos.valor) as valorTotal FROM vendas INNER JOIN vendas_has_produtos ON vendas.id = vendas_has_produtos.vendas_id WHERE CAST(data_venda AS DATE) >= '"+dataInicio+"' AND CAST(data_venda AS DATE) <= '"+dataFinal+"' GROUP BY CAST(data_venda AS DATE)";
		System.out.println(comando);
		
		try {

			
			String dataIni = dataInicio.replaceAll("-", "/");
			String[] s = dataIni.split("/");
			String dataInicioFormatada = s[2]+"/"+s[1]+"/"+s[0];
			System.out.println("formatada: "+dataInicioFormatada);
			
			String dataFim = dataFinal.replaceAll("-", "/");
			String[] sp = dataFim.split("/");
			String dataFinalFormatada = sp[2]+"/"+sp[1]+"/"+sp[0];
			System.out.println("formatada: "+dataFinalFormatada);
			
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);

			while (rs.next()) {
				
				int quantidade = rs.getInt("quantidade");
				float valorTotal = rs.getFloat("valorTotal");
				Date dia = rs.getDate("data");
				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
				String dataFormatada = formato.format(dia);
				
				faturamento = new JsonObject();
				
				faturamento.addProperty("data", dataFormatada);
				faturamento.addProperty("quantidade", quantidade);
				faturamento.addProperty("valorTotal", valorTotal);
				faturamento.addProperty("dataInicioFormatada", dataInicioFormatada);
				faturamento.addProperty("dataFinalFormatada", dataFinalFormatada);
				
				listaFaturamentos.add(faturamento);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaFaturamentos;
	}

}
