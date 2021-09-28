package br.com.projetoindividual.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import br.com.projetoindividual.jdbcinterface.CategoriaDAO;
import br.com.projetoindividual.modelo.Categoria;


public class JDBCCategoriaDAO implements CategoriaDAO {

	private Connection conexao;

	public JDBCCategoriaDAO(Connection conexao) {
		this.conexao = conexao;
	}

	public boolean inserir(Categoria categoria) {

		String comando = "SELECT id FROM categorias WHERE nome ='" + categoria.getNome() + "' && id != "
				+ categoria.getId() + " ";
		PreparedStatement p;
		try {
			p = this.conexao.prepareStatement(comando);
			ResultSet rs = p.executeQuery(comando);

			while (rs.next()) {
				return false;
			}
		

			String comando2 = "INSERT INTO categorias (id, nome) VALUES (?,?)";

			PreparedStatement p2 = this.conexao.prepareStatement(comando2);

			p2.setInt(1, categoria.getId());
			p2.setString(2, categoria.getNome());

			p2.execute();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<JsonObject> buscarPorNome(String nome) {

		String comando = "SELECT * FROM categorias ";
		if (!nome.equals("")) {
			comando += "WHERE nome LIKE '%" + nome + "%' ";
		}
		comando += " ORDER BY id ASC";

		List<JsonObject> listaCategorias = new ArrayList<JsonObject>();
		JsonObject categoria;

		try {

			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);

			while (rs.next()) {

				int id = rs.getInt("id");
				String catNome = rs.getString("nome");

				categoria = new JsonObject();

				categoria.addProperty("id", id);
				categoria.addProperty("nome", catNome);

				listaCategorias.add(categoria);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return listaCategorias;

	}

	public boolean deletar(int id) {
		String comando = "DELETE FROM categorias WHERE id = ?";
		PreparedStatement p;
		try {
			p = this.conexao.prepareStatement(comando);

			p.setInt(1, id);
			p.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Categoria buscarPorId(int id) {
		String comando = "SELECT * FROM categorias WHERE categorias.id = ?";

		Categoria categoria = new Categoria();

		try {
			PreparedStatement p = this.conexao.prepareStatement(comando);
			p.setInt(1, id);

			ResultSet rs = p.executeQuery();
			while (rs.next()) {

				String nome = rs.getString("nome");

				categoria.setId(id);
				categoria.setNome(nome);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categoria;
	}

	public boolean alterar(Categoria categoria) {

		String comando = "SELECT id FROM categorias WHERE nome ='" + categoria.getNome() + "' && id != "
				+ categoria.getId() + " ";
		PreparedStatement p;
		try {
			p = this.conexao.prepareStatement(comando);
			ResultSet rs = p.executeQuery(comando);

			while (rs.next()) {
				return false;
			}

			String comando2 = "UPDATE categorias SET nome=? WHERE id=?";
			PreparedStatement p2 = this.conexao.prepareStatement(comando2);

			p2.setString(1, categoria.getNome());
			p2.setInt(2, categoria.getId());

			p2.executeUpdate();

			return true;

		} catch (SQLException e1) {
			e1.printStackTrace();
			return false;
		}

	}

}
