package br.com.desafio.despesaspessoais.DTO;

import java.math.BigDecimal;
import java.util.List;

public class ResumoMensalDTO {

	private String mes;
	private List<ResumoProjection> itens; 
    private BigDecimal totalGeral;
    
    public ResumoMensalDTO() {
	
	}

	public ResumoMensalDTO(String mes, List<ResumoProjection> itens, BigDecimal totalGeral) {
		super();
		this.mes = mes;
		this.itens = itens;
		this.totalGeral = totalGeral;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public List<ResumoProjection> getItens() {
		return itens;
	}

	public void setItens(List<ResumoProjection> itens) {
		this.itens = itens;
	}

	public BigDecimal getTotalGeral() {
		return totalGeral;
	}

	public void setTotalGeral(BigDecimal totalGeral) {
		this.totalGeral = totalGeral;
	}
    
    
}
