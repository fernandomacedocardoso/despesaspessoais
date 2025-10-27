package br.com.desafio.despesaspessoais.entities;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

import br.com.desafio.despesaspessoais.pag.FormaDePagamento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Entity
public class Despesa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@NotNull
	@Size(min = 3, max = 120)
	private String descricao;
	
	@NotNull
	@DecimalMin("0.01")
	@Digits(integer = 15, fraction = 2)
	private BigDecimal valor;
	
	@NotNull
	private LocalDate data;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "categoria_Id",  nullable = false)
	private Categoria categoria;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private FormaDePagamento formaDePagamento;
	
	 @Column(updatable = false)
    private Instant createdAt;

    private Instant updatedAt;

    public Despesa() {
	
	}
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }

	public Despesa(@NotNull @Size(min = 3, max = 120) String descricao,
			@NotNull @DecimalMin("0.01") @Digits(integer = 15, fraction = 2) BigDecimal valor, @NotNull LocalDate data,
			Categoria categoria, @NotNull FormaDePagamento formaDePagamento) {
		super();
		this.descricao = descricao;
		this.valor = valor;
		this.data = data;
		this.categoria = categoria;
		this.formaDePagamento = formaDePagamento;
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

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public FormaDePagamento getFormaDePagamento() {
		return formaDePagamento;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public void setFormaDePagamento(FormaDePagamento formaDePagamento) {
		this.formaDePagamento = formaDePagamento;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}
    
}