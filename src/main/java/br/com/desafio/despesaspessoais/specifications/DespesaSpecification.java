package br.com.desafio.despesaspessoais.specifications;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.com.desafio.despesaspessoais.DTO.DespesaFilterDTO;
import br.com.desafio.despesaspessoais.entities.Categoria;
import br.com.desafio.despesaspessoais.entities.Despesa;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;

public class DespesaSpecification {

	
	public static Specification<Despesa> build(DespesaFilterDTO filtro) {
		return (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (filtro.getDataInicio() != null) {
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("data"), filtro.getDataInicio()));
			}
			if (filtro.getDataFim() != null) {
				predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("data"), filtro.getDataFim()));
			}
			if (filtro.getCategoriaId() != null) {
				Join<Despesa, Categoria> categoriaJoin = root.join("categoria");
				predicates.add(criteriaBuilder.equal(categoriaJoin.get("id"), filtro.getCategoriaId()));
			}
			if (filtro.getFormaDePagamento() != null) {
				predicates.add(criteriaBuilder.equal(root.get("formaDePagamento"), filtro.getFormaDePagamento()));
			}
			if (filtro.getDescricao() != null && !filtro.getDescricao().trim().isEmpty()) {
				String likePattern = "%" + filtro.getDescricao().toLowerCase() + "%";
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("descricao")), likePattern));
			}

			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}
}
