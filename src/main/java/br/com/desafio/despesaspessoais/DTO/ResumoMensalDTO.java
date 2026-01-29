package br.com.desafio.despesaspessoais.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResumoMensalDTO {

	private String mes;
	private List<ResumoProjection> itens; 
    private BigDecimal totalGeral;

}
