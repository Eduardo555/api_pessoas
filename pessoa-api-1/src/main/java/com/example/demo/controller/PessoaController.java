package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Pessoa;
import com.example.demo.repository.PessoaRepository;

@RestController
@RequestMapping
public class PessoaController {

	@Autowired
	private PessoaRepository pessoaRespository;
	
	@RequestMapping(value = "/pessoas/paginacao/{pagina}", method = RequestMethod.GET)
	public List<Pessoa> getAll(@PathVariable Integer pagina) {
		
		Pageable paginacao = PageRequest.of(pagina, 2);
				
		return pessoaRespository.findAll(paginacao).toList();
	}
	
	@RequestMapping(value = "/pessoas/{id}", method = RequestMethod.GET)
	public ResponseEntity<Pessoa> getOne(@PathVariable Long id) {
		Optional<Pessoa> pessoa = pessoaRespository.findById(id);
		if(pessoa.isPresent()) {
			return new ResponseEntity<Pessoa>(pessoa.get(), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/pessoas", method = RequestMethod.POST)
	public Pessoa Salvar(@Valid @RequestBody  Pessoa pessoa) {
		
		// Salvar pessoa.
		return pessoaRespository.save(pessoa);
	}
	
	@RequestMapping(value = "/pessoas", method = RequestMethod.PUT)
	public ResponseEntity<Pessoa> update(@Valid @RequestBody Pessoa pessoa) {
		
		if (pessoa.getId() == null || pessoa == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Pessoa _pessoa = pessoaRespository.getById(pessoa.getId());
		if (!_pessoa.toString().isEmpty()) {
			_pessoa.setCpf(pessoa.getCpf());
			_pessoa.setDataNascimento(pessoa.getDataNascimento());
			_pessoa.setNome(pessoa.getNome());
			_pessoa.setContatos(pessoa.getContatos());
			Pessoa novaPessoa = pessoaRespository.save(_pessoa);
			return new ResponseEntity<Pessoa>(novaPessoa, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/pessoas/{id}", method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable Long id) {
		
		Optional<Pessoa> pessoa = pessoaRespository.findById(id);
		if (!pessoa.isPresent()) {
			return new ResponseEntity<>("Pessoa inexistente!", HttpStatus.BAD_REQUEST);
		}
		else {
			pessoaRespository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
}
