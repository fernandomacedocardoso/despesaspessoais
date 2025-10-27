package br.com.desafio.despesaspessoais.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoriaDTO {
//    ____        _     ___       _ _
//	 / __ \      (_)    | |      (_) |
//	| |  | |_   _ _  ___| | _____ _| |_   _____ _ __
//	| |  | | | | | |/ __| |/ / __| | \ \ / / _ \ '__|
//	| |__| | |_| | | (__|   <\__ \ | |\ V /  __/ |
//	 \___\_\\__,_|_|\___|_|\_\___/_|_| \_/ \___|_|
//
//	
	
	private Long id;
	
	@NotBlank
    @Size(min = 3, max = 60)
	private String nome;
	
	@Size(max = 255)
	private String descricao;
	
	public CategoriaDTO() {
		
	}

	public CategoriaDTO(Long id, String nome, String descricao) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
