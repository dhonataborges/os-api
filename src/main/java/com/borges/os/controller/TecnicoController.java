package com.borges.os.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.borges.os.domain.Tecnico;
import com.borges.os.dtos.TecnicoDTO;
import com.borges.os.serveces.TecnicoService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/tecnico")
public class TecnicoController {

	@Autowired
	private TecnicoService service;
	
	/*
	 * Busca Tecnico pelo ID
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id) {
		TecnicoDTO objDTO = new TecnicoDTO(service.findbyId(id));
		return ResponseEntity.ok().body(objDTO);
	}
	
	/*
	 * 	Busca todos os tecnicos da base de dados
	 */
	@GetMapping
	public ResponseEntity<List<TecnicoDTO>> findall() {
		List<TecnicoDTO> listDTO = service.findAll().stream().map(obj -> new TecnicoDTO(obj))
				.collect(Collectors.toList());

		return ResponseEntity.ok().body(listDTO);
	}

	/*
	 * 	Cria um tecnico
	 */
	@PostMapping
	public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO objDto) {
		Tecnico newObj = service.create(objDto);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	/*
	 * Atualiza um Tecnico
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id, @Valid @RequestBody TecnicoDTO objDto) {
		TecnicoDTO newObj = new TecnicoDTO(service.update(id, objDto));
		return ResponseEntity.ok().body(newObj);
	}

	/*
	 * Deleta um Tecnico
	 */

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();

	}

}