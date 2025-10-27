package br.com.desafio.despesaspessoais.service;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.desafio.despesaspessoais.DTO.CategoriaDTO;
import br.com.desafio.despesaspessoais.DTO.CategoriaResponseDTO;
import br.com.desafio.despesaspessoais.entities.Categoria;
import br.com.desafio.despesaspessoais.exeception.ConflictException;
import br.com.desafio.despesaspessoais.exeception.ResourceNotFoundException;
import br.com.desafio.despesaspessoais.repository.CategoriaRepository;
import br.com.desafio.despesaspessoais.repository.DespesaRepository;


@Service
@Transactional
public class CategoriaService {
	
	private final CategoriaRepository categoriaRepository;
    private final DespesaRepository despesaRepository;

   
    public CategoriaService(CategoriaRepository categoriaRepository, DespesaRepository despesaRepository) {
        this.categoriaRepository = categoriaRepository;
        this.despesaRepository = despesaRepository;
    }

    public CategoriaResponseDTO criar(CategoriaDTO dto) {
        if (categoriaRepository.existsByNomeIgnoreCase(dto.getNome())) {
            throw new ConflictException("Já existe uma categoria com o nome: " + dto.getNome());
        }

        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());

        Categoria saved = categoriaRepository.save(categoria);

        return toResponseDTO(saved); // converte pra DTO
    }

    @Transactional(readOnly = true)
    public Page<CategoriaResponseDTO> listar(Pageable pageable) {
        return categoriaRepository.findAll(pageable)
                .map(this::toResponseDTO);
    }
    /*
     * ^^^^^^^^^^^ estudar direito ^^^^^^^^^^^^^^
     */

    @Transactional(readOnly = true)
    public Categoria buscarPorId(Long id) {
    	    return categoriaRepository.findById(id)
    	            .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com o ID: " + id));
    }

    public CategoriaResponseDTO atualizar(Long id, CategoriaDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com o ID: " + id));
        
        Optional<Categoria> existe = categoriaRepository.findByNomeIgnoreCase(dto.getNome());
        if (existe.isPresent() && !existe.get().getId().equals(id)) {
            throw new ConflictException("Já existe outra categoria com o nome: " + dto.getNome());
        }

        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());

        Categoria updated = categoriaRepository.save(categoria);
        return toResponseDTO(updated); // linha 110
    }

    public void excluir(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoria não encontrada com o ID: " + id);
        }

        if (despesaRepository.existsByCategoriaId(id)) {
            throw new ConflictException("Categoria não pode ser removida pois existem despesas vinculadas a ela."); 
            // erro 409
        }

        categoriaRepository.deleteById(id);
    }   
    
    private CategoriaResponseDTO toResponseDTO(Categoria categoria) {
    	
        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNome(),
                categoria.getDescricao(),
                categoria.getCreatedAt(), 
                categoria.getUpdatedAt()
        );
    }
    
    @Transactional(readOnly = true)
    public CategoriaResponseDTO buscarResponseDTO(Long id) { 
        Categoria categoria = buscarPorId(id); 
        return toResponseDTO(categoria); 
    }
}

