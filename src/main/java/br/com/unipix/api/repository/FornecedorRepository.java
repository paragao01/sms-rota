package br.com.unipix.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.unipix.api.model.Rota;

public interface FornecedorRepository extends JpaRepository<Rota, Integer> {

}