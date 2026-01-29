package br.com.desafio.despesaspessoais.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.desafio.despesaspessoais.DTO.ResumoProjection;
import br.com.desafio.despesaspessoais.entities.Despesa;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {

	@Query("SELECT new br.com.desafio.despesaspessoais.DTO.ResumoProjection("
			+ "d.categoria.id, d.categoria.nome, SUM(d.valor)) "
			+ "FROM Despesa d WHERE d.data BETWEEN :dataInicio AND :dataFim "
			+ "GROUP BY d.categoria.id, d.categoria.nome")
	List<ResumoProjection> calcularTotalPorCategoriaNoMes(
			@Param("dataInicio") LocalDate dataInicio,
			@Param("dataFim") LocalDate dataFim);

	boolean existsByCategoriaId(Long categoriaId); // se existe cat vinculadas

	Page<Despesa> findAll(Specification<Despesa> spec, Pageable pageable);
}
