package br.com.unipix.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.unipix.api.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

}