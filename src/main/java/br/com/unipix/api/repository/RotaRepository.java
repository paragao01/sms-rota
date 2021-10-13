package br.com.unipix.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.unipix.api.model.Rota;

public interface RotaRepository extends JpaRepository<Rota, Integer> {

	@Query(value="SELECT c.rota_id as rotaId, fornecedor_id as fornecedorId,prefixo from tb_produto a \r\n"
			+ "inner join tb_rota b on a.rota_id=b.id\r\n"
			+ "inner join tb_rota_fornecedor c on c.rota_id=a.rota_id\r\n"
			+ "inner join tb_rota_fornecedor_prefixo d on c.id=d.tb_rota_fornecedor_id\r\n"
			+ "where a.id=?1 and (?2 is null or fornecedor_id <> ?2)", nativeQuery=true)
	List<FornecedorPrefixo> obterFornecedorPorPrefixo(Long produtoId, Long fornecedorId);
}