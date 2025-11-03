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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }

}