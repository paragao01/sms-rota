package br.com.unipix.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.unipix.api.model.RotaFornecedorPrefixo;
import br.com.unipix.api.model.RotaFornecedorPrefixoId;

public interface RotaFornecedorPrefixoRepository extends JpaRepository<RotaFornecedorPrefixo, RotaFornecedorPrefixoId> {

}