package br.com.desafio.despesaspessoais.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.desafio.despesaspessoais.entities.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

	Optional<Categoria> findByNomeIgnoreCase(String nome); // compara os nomes

	boolean existsByNomeIgnoreCase(String nome); // ver existencia do nome

}
