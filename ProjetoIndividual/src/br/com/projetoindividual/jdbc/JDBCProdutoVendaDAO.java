package br.com.projetoindividual.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.com.projetoindividual.jdbcinterface.ProdutoVendaDAO;
import br.com.projetoindividual.modelo.ProdutoVenda;

public class JDBCProdutoVendaDAO implements ProdutoVendaDAO {

	private Connection conexao;

	public JDBCProdutoVendaDAO(Connection conexao) {
		this.conexao = conexao;
	}
	
	public String inserir(ProdutoVenda produtoVenda) throws SQLException {
			
			String comando = "INSERT INTO vendas_has_produtos (vendas_id,produtos_id,valor,quantidade) VALUES (?,?,?,?)";

			PreparedStatement p = this.conexao.prepareStatement(comando, PreparedStatement.RETURN_GENERATED_KEYS);
			p.setInt(1, produtoVenda.getIdVenda());
			//System.out.println("produtoVenda.getIdVenda()   =   "+produtoVenda.getIdVenda());
			p.setInt(2, produtoVenda.getIdProduto());
			//System.out.println("produtoVenda.getIdProduto()   =   "+produtoVenda.getIdProduto());
			p.setFloat(3, produtoVenda.getValor());
			//System.out.println("produtoVenda.getValor()   =   "+produtoVenda.getValor());
			p.setFloat(4, produtoVenda.getQuantidade());
			//System.out.println("produtoVenda.getQuantidade()   =   "+produtoVenda.getQuantidade());
			System.out.println(comando);
			p.execute();
			
			return "Venda realizada com sucesso!";
	}
	
}
