package br.com.desafio.despesaspessoais.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.desafio.despesaspessoais.DTO.CategoriaDTO;
import br.com.desafio.despesaspessoais.DTO.CategoriaResponseDTO;
import br.com.desafio.despesaspessoais.service.CategoriaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {

	private CategoriaService service;

	public CategoriaController(CategoriaService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<CategoriaResponseDTO> criar(@RequestBody @Valid CategoriaDTO categoriaDTO) {
		CategoriaResponseDTO response = service.criar(categoriaDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	// http://localhost:8080/api/v1/categorias?page=0&size=5&sort=nome,asc

	@GetMapping
	public Page<CategoriaResponseDTO> listar(Pageable pageable) {
		return service.listar(pageable);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CategoriaResponseDTO> detalhar(@PathVariable Long id) {
		CategoriaResponseDTO response = service.buscarResponseDTO(id);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CategoriaResponseDTO> atualizar(@PathVariable Long id,
			@RequestBody @Valid CategoriaDTO categoriaDTO) {
		CategoriaResponseDTO response = service.atualizar(id, categoriaDTO);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		service.excluir(id);
		return ResponseEntity.noContent().build();
	}

}
