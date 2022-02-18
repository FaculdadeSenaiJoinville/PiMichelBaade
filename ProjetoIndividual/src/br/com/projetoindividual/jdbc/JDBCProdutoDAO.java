package br.com.projetoindividual.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import br.com.projetoindividual.jdbcinterface.ProdutoDAO;
import br.com.projetoindividual.produto.Produto;

public class JDBCProdutoDAO implements ProdutoDAO {

	private Connection conexao;

	public JDBCProdutoDAO(Connection conexao) {
		this.conexao = conexao;
	}
	
	public String inserir(Produto produto) {

		String comando = "SELECT id FROM produtos WHERE nome ='" + produto.getNome() + "' && categorias_id = " + produto.getCategoriaId() + " && id != "
				+ produto.getId() + " ";
		PreparedStatement p;
		try {
			p = this.conexao.prepareStatement(comando);
			ResultSet rs = p.executeQuery(comando);

			while (rs.next()) {
				return "Já existe um produto com esse nome na mesma categoria.";
			}
		
			String comando2 = "INSERT INTO produtos (nome, valor, isvalorunidade, categorias_id) VALUES (?,?,?,?)";
			PreparedStatement p2 = this.conexao.prepareStatement(comando2);

			p2.setString(1, produto.getNome());
			p2.setFloat(2, produto.getValor());
			p2.setInt(3, produto.getIsValorUnidade());
			p2.setInt(4, produto.getCategoriaId());
			p2.execute();
			return "Produto alterado com sucesso!";

		} catch (SQLException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
public List<Produto> buscar() {
		
		String comando = "SELECT produtos.*,categorias.nome as nomeCat FROM produtos INNER JOIN categorias on categorias_id = categorias.id ";

		List<Produto> listaProdutos = new ArrayList<Produto>();

		try {

			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);

			while (rs.next()) {
				
				Produto produto = new Produto();
				
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				float valor = rs.getFloat("valor");
				int isvalorunidade = rs.getInt("isvalorunidade");
				//String catNome = rs.getString("nomeCat");
				

				produto.setId(id);
				produto.setNome(nome);
				produto.setValor(valor);
				produto.setIsValorUnidade(isvalorunidade);
				//produto.setCategoriaId(catNome);

				listaProdutos.add(produto);
			}
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaProdutos;
	}
	

	public List<JsonObject> buscarPorNome(String nome) {
		
		String comando = "SELECT produtos.*,categorias.nome as nomeCat FROM produtos INNER JOIN categorias on categorias_id = categorias.id ";
		if (!nome.equals("")) {
			comando += "WHERE produtos.nome LIKE '%" + nome + "%' ";
		}
		comando += " ORDER BY id ASC";
		List<JsonObject> listaProdutos = new ArrayList<JsonObject>();
		JsonObject produto;

		try {

			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);

			while (rs.next()) {
				produto = new JsonObject();

				
				int id = rs.getInt("id");
				String nomeProduto = rs.getString("nome");
				float valor = rs.getFloat("valor");
				
				String catNome = rs.getString("nomeCat");
				
				
				String isvalorunidade = rs.getString("isvalorunidade");
				if (isvalorunidade.equals("1")) {
					isvalorunidade = "Quantidade";
				}else if(isvalorunidade.equals("2")){
					isvalorunidade = "Peso";
				}
				
							
				produto.addProperty("id", id);
				produto.addProperty("nome", nomeProduto);
				produto.addProperty("valor", valor);
				produto.addProperty("isvalorunidade", isvalorunidade);
				produto.addProperty("catNome", catNome);				
				
				listaProdutos.add(produto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return listaProdutos;

	}

	public String deletar(int id) {
		String comando = "DELETE FROM produtos WHERE id = ?";
		PreparedStatement p;
		try {
			p = this.conexao.prepareStatement(comando);

			p.setInt(1, id);
			p.execute();
			return "Produto deletado com sucesso!";
		} catch (SQLException e) {
			e.printStackTrace();		
			return e.getMessage();
		}
	}

	public Produto buscarPorId(int id) {
		String comando = "SELECT produtos.*,categorias.nome as nomeCat FROM produtos INNER JOIN categorias on categorias_id = categorias.id WHERE produtos.id = ?";

		Produto produto = new Produto();

		try {
			PreparedStatement p = this.conexao.prepareStatement(comando);
			p.setInt(1, id);
			
			ResultSet rs = p.executeQuery();
			while (rs.next()) {

				String nome = rs.getString("nome");
				int categoriaId = rs.getInt("categorias_id");
				int isvalorunidade = rs.getInt("isvalorunidade");
				float valor = rs.getFloat("valor");

				produto.setId(id);
				produto.setNome(nome);
				produto.setCategoriaId(categoriaId);
				produto.setIsValorUnidade(isvalorunidade);
				produto.setValor(valor);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return produto;
	}

	public String alterar(Produto produto) {

		String comando = "SELECT id FROM produtos WHERE nome ='" + produto.getNome() + "' && categorias_id = " + produto.getCategoriaId() + " && id != "
				+ produto.getId() + " ";
		PreparedStatement p;
		try {
			p = this.conexao.prepareStatement(comando);
			ResultSet rs = p.executeQuery(comando);

			while (rs.next()) {
				return "Já existe um produto com esse nome na mesma categoria.";
			}

			String comando2 = "UPDATE produtos SET nome=?,valor=?,isvalorunidade=?,categorias_id=? WHERE id=?";
			PreparedStatement p2 = this.conexao.prepareStatement(comando2);

			p2.setString(1, produto.getNome());
			p2.setFloat(2, produto.getValor());
			p2.setInt(3, produto.getIsValorUnidade());
			p2.setInt(4, produto.getCategoriaId());
			p2.setInt(5, produto.getId());
			p2.executeUpdate();

			return "Produto alterado com sucesso!";

		} catch (SQLException e1) {
			e1.printStackTrace();
			return e1.getMessage();
		}

	}

	
	public List<JsonObject> buscarParaVenda(int categoria) {
		
		String comando = "SELECT * FROM produtos WHERE  categorias_id = "+categoria+" ORDER BY nome ASC";
		List<JsonObject> listaProdutos = new ArrayList<JsonObject>();
		JsonObject produto = null;
		try {

			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);

			while (rs.next()) {
				
				
				
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				float valor = rs.getFloat("valor");
				
				produto = new JsonObject();
				produto.addProperty("id", id);
				produto.addProperty("nome", nome);
				produto.addProperty("valor", valor);;

				listaProdutos.add(produto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaProdutos;
	}
	
	
}
