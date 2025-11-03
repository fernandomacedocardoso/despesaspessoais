package br.com.desafio.despesaspessoais.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.desafio.despesaspessoais.pag.FormaDePagamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class DespesasDTO {

/*
 * 
 *    ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 */
	private Long id; 

	@NotBlank
	@Size(min = 3, max = 120)
	private String descricao;

	@NotNull
	@Positive
	private BigDecimal valor;

	@NotNull
	private LocalDate data;

	@NotNull
	private Long categoriaId;

	@NotNull
	private FormaDePagamento formaDePagamento;

	public DespesasDTO() {
		
	}

	public DespesasDTO(@NotBlank @Size(min = 3, max = 120) String descricao, @NotNull @Positive BigDecimal valor,
			@NotNull LocalDate data, @NotNull Long categoriaId, @NotNull FormaDePagamento formaPagamento) {
		super();
		this.descricao = descricao;
		this.valor = valor;
		this.data = data;
		this.categoriaId = categoriaId;
		this.formaDePagamento = formaPagamento;
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

	public Long getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Long categoriaId) {
		this.categoriaId = categoriaId;
	}

	public FormaDePagamento getFormaDePagamento() {
		return formaDePagamento;
	}

	public void setFormaDePagamento(FormaDePagamento formaDePagamento) {
		this.formaDePagamento = formaDePagamento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
}
