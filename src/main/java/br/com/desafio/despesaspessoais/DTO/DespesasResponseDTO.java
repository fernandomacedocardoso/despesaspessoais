package br.com.desafio.despesaspessoais.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DespesasResponseDTO {

	private Long id;
	private String descricao;
	private BigDecimal valor;
	private LocalDate data;
	private String formaDePagamento;
	private CategoriaSimplesDTO categoria;
	
	public DespesasResponseDTO() {
	
	}

	public DespesasResponseDTO(Long id, String descricao, BigDecimal valor, LocalDate data, String formaDePagamento,
			CategoriaSimplesDTO categoria) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.valor = valor;
		this.data = data;
		this.formaDePagamento = formaDePagamento;
		this.categoria = categoria;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public String getFormaDePagamento() {
		return formaDePagamento;
	}

	public void setFormaDePagamento(String formaDePagamento) {
		this.formaDePagamento = formaDePagamento;
	}

	public CategoriaSimplesDTO getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaSimplesDTO categoria) {
		this.categoria = categoria;
	}
	
	
}
