package br.com.projetoindividual.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import br.com.projetoindividual.jdbcinterface.ConsultaVendaDAO;

public class JDBCConsultaVendaDAO implements ConsultaVendaDAO {

	private Connection conexao;

	public JDBCConsultaVendaDAO(Connection conexao) {
		this.conexao = conexao;
	}

	public List<JsonObject> produtosMaisVendidos(String dataInicio, String dataFinal) {
		List<JsonObject> listaConsultaVenda = new ArrayList<JsonObject>();
		JsonObject aparicao;
		String comando = "SELECT COUNT(vendas_has_produtos.produtos_id) as produtosAparicao, produtos.nome  FROM vendas INNER JOIN vendas_has_produtos ON vendas.id = vendas_has_produtos.vendas_id INNER JOIN produtos ON vendas_has_produtos.produtos_id= produtos.id WHERE CAST(data_venda AS DATE) >= '"
				+ dataInicio + "' AND CAST(data_venda AS DATE) <= '" + dataFinal
				+ "' GROUP BY vendas_has_produtos.produtos_id LIMIT 5";
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while (rs.next()) {
				int produtosAparicao = rs.getInt("produtosAparicao");
				String nome = rs.getString("nome");
				aparicao = new JsonObject();
				aparicao.addProperty("produtosAparicao", produtosAparicao);
				aparicao.addProperty("nome", nome);
				listaConsultaVenda.add(aparicao);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaConsultaVenda;
	}

	public List<JsonObject> produtosMaisVendidosKg(String dataInicio, String dataFinal) {
		List<JsonObject> listaConsultaVenda = new ArrayList<JsonObject>();
		JsonObject quantidadeTotal;
		String comando = "SELECT SUM(vendas_has_produtos.quantidade) as quantidade, produtos.nome  FROM vendas INNER JOIN vendas_has_produtos ON vendas.id = vendas_has_produtos.vendas_id INNER JOIN produtos ON vendas_has_produtos.produtos_id= produtos.id WHERE produtos.isvalorunidade = 2 AND CAST(data_venda AS DATE) >= '"
				+ dataInicio + "' AND CAST(data_venda AS DATE) <= '" + dataFinal
				+ "' GROUP BY vendas_has_produtos.produtos_id LIMIT 5";
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while (rs.next()) {
				int quantidade = rs.getInt("quantidade");
				String nome = rs.getString("nome");
				quantidadeTotal = new JsonObject();
				quantidadeTotal.addProperty("quantidade", quantidade);
				quantidadeTotal.addProperty("nome", nome);
				listaConsultaVenda.add(quantidadeTotal);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaConsultaVenda;
	}
	
	public List<JsonObject> produtosMaisVendidosUnidade(String dataInicio, String dataFinal) {
		List<JsonObject> listaConsultaVenda = new ArrayList<JsonObject>();
		JsonObject quantidadeTotal;
		String comando = "SELECT SUM(vendas_has_produtos.quantidade) as quantidade, produtos.nome  FROM vendas INNER JOIN vendas_has_produtos ON vendas.id = vendas_has_produtos.vendas_id INNER JOIN produtos ON vendas_has_produtos.produtos_id= produtos.id WHERE produtos.isvalorunidade = 1 AND CAST(data_venda AS DATE) >= '"
				+ dataInicio + "' AND CAST(data_venda AS DATE) <= '" + dataFinal
				+ "' GROUP BY vendas_has_produtos.produtos_id LIMIT 5";
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while (rs.next()) {
				int quantidade = rs.getInt("quantidade");
				String nome = rs.getString("nome");
				quantidadeTotal = new JsonObject();
				quantidadeTotal.addProperty("quantidade", quantidade);
				quantidadeTotal.addProperty("nome", nome);
				listaConsultaVenda.add(quantidadeTotal);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaConsultaVenda;
	}
}
