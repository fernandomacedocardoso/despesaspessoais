package br.com.desafio.despesaspessoais.DTO;

import java.math.BigDecimal;

public class ResumoProjection {

	private Long categoriaId;
	private String categoriaNome;
	private BigDecimal total;
	
	public ResumoProjection() {
	
	}

	public ResumoProjection(Long categoriaId, String categoriaNome, BigDecimal total) {
		super();
		this.categoriaId = categoriaId;
		this.categoriaNome = categoriaNome;
		this.total = total;
	}

	public Long getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Long categoriaId) {
		this.categoriaId = categoriaId;
	}

	public String getCategoriaNome() {
		return categoriaNome;
	}

	public void setCategoriaNome(String categoriaNome) {
		this.categoriaNome = categoriaNome;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	
}
