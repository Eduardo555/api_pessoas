package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Contato;
import com.example.demo.model.Pessoa;
import com.example.demo.repository.ContatoRepository;
import com.example.demo.repository.PessoaRepository;

@RestController
@RequestMapping("/contatos")
public class ContatoController {
	
	@Autowired
	private ContatoRepository contatoRepository;
	
	@GetMapping()
	public List<Contato> getAll() {
		return contatoRepository.findAll();
	}
	
	@PostMapping()
	//@ResponseStatus(HttpStatus.CREATED)
	public Contato Salvar(@Valid @RequestBody  Contato contato) {
		return contatoRepository.save(contato);
	}
}
