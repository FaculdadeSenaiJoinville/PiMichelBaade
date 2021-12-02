package br.com.projetoindividual.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.gson.JsonObject;

import br.com.projetoindividual.jdbcinterface.VendaDAO;
import br.com.projetoindividual.modelo.ProdutoVenda;
import br.com.projetoindividual.modelo.Venda;

public class JDBCVendaDAO implements VendaDAO {

	private Connection conexao;

	public JDBCVendaDAO(Connection conexao) {
		this.conexao = conexao;
	}
	
	public String inserir(Venda venda) {

		try {
		
			String comando = "INSERT INTO vendas (data_venda,usuarios_login) VALUES (?,?)";

			String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		    //System.out.println("dia: "+timeStamp);
			
			PreparedStatement p = this.conexao.prepareStatement(comando, PreparedStatement.RETURN_GENERATED_KEYS);
			//p.setInt(1, venda.getId());
			//System.out.println("id da Venda: "+venda.getId());
			p.setString(1, timeStamp);
			//System.out.println("data da Venda: "+timeStamp);
			p.setString(2, venda.getLogin());
			//System.out.println("login da Venda: "+venda.getLogin());
			System.out.println(comando);
			
			List<Integer> idsProdutos = new ArrayList<Integer>();
			
			for (ProdutoVenda produto : venda.getProdutos()) {

				if (idsProdutos.contains(produto.getIdProduto())) {
					return "Não é permitido inserir mais de uma vez o mesmo produto na mesma venda!";
				}else {
					idsProdutos.add(produto.getIdProduto());
				}
				
			}
			p.execute();
			ResultSet idGerado = p.getGeneratedKeys();
			
			while (idGerado.next()) {
				for (ProdutoVenda produto : venda.getProdutos()) {

					produto.setIdVenda(idGerado.getInt(1));
					JDBCProdutoVendaDAO jdbcProdutoVenda = new JDBCProdutoVendaDAO(this.conexao);
					jdbcProdutoVenda.inserir(produto);
					
				}
				
			}
			return "Venda realizada com sucesso!";

		} catch (SQLException e) {
			e.printStackTrace();		
			return e.getMessage();
		}catch (Exception e) {
			e.printStackTrace();		
			return e.getMessage();
		}
	}
	
	
	public List<JsonObject> atualizaValores(String[] idProdutos) {
		
		List<JsonObject> listaValores = new ArrayList<JsonObject>();
		JsonObject valor;
		//System.out.println("id dos produtos: "+idProdutos);
		//float valores[] = new float[idProdutos.length];
		try {
			
			for (int i = 0; i < idProdutos.length; i++) {
				
				String comando = "SELECT valor FROM produtos WHERE id = "+idProdutos[i];
				System.out.println(comando);
				Statement stmt = conexao.createStatement();
				
				ResultSet rs = stmt.executeQuery(comando);
				valor = new JsonObject();
				if (rs.next()) {
					

					valor.addProperty("valor", rs.getFloat("valor"));
				}
				
				
				listaValores.add(valor);
				
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listaValores;
	}

}
