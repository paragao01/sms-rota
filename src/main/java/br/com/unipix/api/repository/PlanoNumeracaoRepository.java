package br.com.unipix.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.unipix.api.model.PlanoNumeracao;

public interface PlanoNumeracaoRepository extends JpaRepository<PlanoNumeracao, String>{
	
}
