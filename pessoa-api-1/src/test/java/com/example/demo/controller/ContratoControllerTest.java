package com.example.demo.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import com.example.demo.model.Contato;
import com.example.demo.repository.ContatoRepository;
import com.example.demo.repository.PessoaRepository;

import io.restassured.http.ContentType;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;

@WebMvcTest
public class ContratoControllerTest {
	
	@Autowired
	private ContatoController contatoController;
	
	@MockBean
	private ContatoRepository contatoRepository;
	
	@MockBean
	private PessoaRepository pessoaRepository;
	
	@BeforeEach
	public void setup () {
		standaloneSetup(this.contatoController);
	}
		
	@Test
	public void deveRetornarSucesso() {
		
		Contato contato = new Contato();
		contato.setEmail("eduardo@gmail.com");
		contato.setNome("Eduardo");
		contato.setTelefone("34421659");
		
		List<Contato> contatos = new ArrayList<Contato>();
		contatos.add(contato);
		
		when(this.contatoRepository.findAll())
		.thenReturn(contatos);
		
		given().accept(ContentType.JSON)
			.when()
			.get("/contatos", contatos)
			.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarErroEmailInvalido() {
		
		Contato contato = new Contato();
		contato.setEmail("emailinvalido");
		contato.setNome("Eduardo");
		contato.setTelefone("34421659");
		
		List<Contato> contatos = new ArrayList<Contato>();
		contatos.add(contato);
		
		given().accept(ContentType.JSON)
			.when()
			.post("/contatos", contatos)
			.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
}
