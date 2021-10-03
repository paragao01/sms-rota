package br.com.unipix.api.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.unipix.api.repository.FornecedorPrefixo;
import br.com.unipix.api.repository.RotaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RotaServiceTest {

	@Autowired
	private RotaRepository rotaRepository;
	
	@Test
	public void obterFornecedorTest() {
		
		List<FornecedorPrefixo> lista = rotaRepository.obterPrioridadeProdutoRota(1);
		System.out.println(lista);
	}
}
