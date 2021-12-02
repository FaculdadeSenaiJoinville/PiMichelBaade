package br.com.projetoindividual.jdbcinterface;

import java.sql.SQLException;

import br.com.projetoindividual.modelo.ProdutoVenda;

public interface ProdutoVendaDAO {

	public String inserir (ProdutoVenda produtoVenda)throws SQLException;
}
