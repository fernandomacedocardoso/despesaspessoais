package br.com.desafio.despesaspessoais.DTO;

public class CategoriaSimplesDTO {

	private Long id;
	private String nome;
	
	public CategoriaSimplesDTO() {
	}

	public CategoriaSimplesDTO(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
}
