package br.com.unipix.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.unipix.api.model.Produto;

public interface RotaRepository extends JpaRepository<Produto, Integer> {

	@Query(value="SELECT fornecedor_id,prefixo from tb_produto a \r\n"
			+ "inner join tb_rota b on a.rota_id=b.id\r\n"
			+ "inner join tb_rota_fornecedor c on c.rota_id=a.rota_id\r\n"
			+ "inner join tb_rota_fornecedor_prefixo d on c.id=d.tb_rota_fornecedor_id\r\n"
			+ "where a.id=?1", nativeQuery=true)
	List<FornecedorPrefixo> obterPrioridadeProdutoRota(Integer produtoId);
}