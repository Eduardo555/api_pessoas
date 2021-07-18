package com.example.demo.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import com.example.demo.model.Contato;
import com.example.demo.model.Pessoa;
import com.example.demo.repository.ContatoRepository;
import com.example.demo.repository.PessoaRepository;

import io.restassured.http.ContentType;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;

@WebMvcTest
public class PessoaControllerTest {

	@Autowired
	private PessoaController pessoaController;
	
	@MockBean
	private ContatoRepository contatoRepository;
	
	@MockBean
	private PessoaRepository pessoaRepository;
	
	@BeforeEach
	public void setup () {
		standaloneSetup(this.pessoaController);
	}
	
	@Test
	public void deveRetornarSucesso_aoSalvar() {
		Contato contato = new Contato();
		contato.setEmail("eduardo@gmail.com");
		contato.setNome("Eduardo");
		contato.setTelefone("34421659");
		
		List<Contato> contatos = new ArrayList<Contato>();
		contatos.add(contato);
		
		Pessoa pessoa = new Pessoa();
		pessoa.setNome("Eduardo");
		pessoa.setCpf("828.966.580-68");
		//Date date = new Date();
		Date date = new Date();
		pessoa.setDataNascimento(date);
		pessoa.setContatos(contatos);
		
		when(this.pessoaRepository.save(pessoa))
		.thenReturn(pessoa);
		
		given().accept(ContentType.JSON)
		.when()
		.post("/pessoas", pessoa)
		.then()
		.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarSucesso() {
		
		Contato contato = new Contato();
		contato.setEmail("eduardo@gmail.com");
		contato.setNome("Eduardo");
		contato.setTelefone("34421659");
		
		List<Contato> contatos = new ArrayList<Contato>();
		contatos.add(contato);
		
		Pessoa pessoa = new Pessoa();
		pessoa.setNome("Eduardo");
		pessoa.setCpf("327.331.730-22");
		//Date date = new Date();
		Date date = new Date();
		pessoa.setDataNascimento(date);
		//pessoa.setContatos(contatos);
		
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		pessoas.add(pessoa);
		
		Pageable paginacao = PageRequest.of(0, 2);
		when(this.pessoaRepository.findAll(paginacao).toList())
		.thenReturn(pessoas);
		
		given().accept(ContentType.JSON)
		.when()
		.get("/pessoas/paginacao/0", pessoas)
		.then()
		.statusCode(HttpStatus.OK.value());
		
	}
	
}
