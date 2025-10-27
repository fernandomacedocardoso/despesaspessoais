package br.com.desafio.despesaspessoais.DTO;

import java.time.LocalDate;

import br.com.desafio.despesaspessoais.pag.FormaDePagamento;

public class DespesaFilterDTO {

	private LocalDate dataInicio;
    private LocalDate dataFim;
    private Long categoriaId;
    private FormaDePagamento formaDePagamento;
    private String descricao;
    
    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }
    
    public DespesaFilterDTO() {
	
	}
    
	public DespesaFilterDTO(LocalDate dataInicio, LocalDate dataFim, Long categoriaId,
			FormaDePagamento formaDePagamento, String descricao) {
		super();
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.categoriaId = categoriaId;
		this.formaDePagamento = formaDePagamento;
		this.descricao = descricao;
	}

	public LocalDate getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
