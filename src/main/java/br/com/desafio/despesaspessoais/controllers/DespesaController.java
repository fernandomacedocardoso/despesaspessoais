package br.com.desafio.despesaspessoais.controllers;


import java.net.URI;
import java.time.YearMonth;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.desafio.despesaspessoais.DTO.CategoriaSimplesDTO;
import br.com.desafio.despesaspessoais.DTO.DespesaFilterDTO;
import br.com.desafio.despesaspessoais.DTO.DespesasDTO;
import br.com.desafio.despesaspessoais.DTO.DespesasResponseDTO;
import br.com.desafio.despesaspessoais.DTO.ResumoMensalDTO;
import br.com.desafio.despesaspessoais.entities.Categoria;
import br.com.desafio.despesaspessoais.entities.Despesa;
import br.com.desafio.despesaspessoais.service.DespesaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/despesas")
public class DespesaController {

	private final DespesaService despesaService;

    public DespesaController(DespesaService despesaService) {
        this.despesaService = despesaService;
    }
    
    private Despesa mapToEntity(DespesasDTO dto) { 
        Categoria categoria = new Categoria();
        categoria.setId(dto.getCategoriaId());

        Despesa despesa = new Despesa();
        despesa.setDescricao(dto.getDescricao());
        despesa.setValor(dto.getValor());
        despesa.setData(dto.getData());
        despesa.setFormaDePagamento(dto.getFormaDePagamento()); 
        despesa.setCategoria(categoria);
        
        // Se for uma atualização (PUT), mapeie o ID
        if (dto.getId() != null) {
            despesa.setId(dto.getId());
        }
        
        return despesa;
    }

    /**
     * converte entidade despesa para despesasResponseDTO
     */
    private DespesasResponseDTO mapToResponseDTO(Despesa despesa) {
        CategoriaSimplesDTO categoriaDTO = new CategoriaSimplesDTO(
            despesa.getCategoria().getId(),
            despesa.getCategoria().getNome()
        );

        // mapeamento da despesa
        DespesasResponseDTO dto = new DespesasResponseDTO(
            despesa.getId(),
            despesa.getDescricao(),
            despesa.getValor(),
            despesa.getData(),
            despesa.getFormaDePagamento().toString(),
            categoriaDTO
        );
        
        return dto;
    }
    
    @PostMapping
    public ResponseEntity<DespesasResponseDTO> criar(@RequestBody @Valid DespesasDTO dto) {
        Despesa despesaToCreate = mapToEntity(dto);
        
        Despesa despesaSalva = despesaService.criar(despesaToCreate);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(despesaSalva.getId())
                .toUri();
        
    
        DespesasResponseDTO responseDTO = mapToResponseDTO(despesaSalva);

        return ResponseEntity.created(location).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DespesasResponseDTO> detalhar(@PathVariable Long id) {
        Despesa despesa = despesaService.buscarPorId(id);
        
        return ResponseEntity.ok(mapToResponseDTO(despesa));
    }

    
    //api/v1/despesas?categoriaId=1&dataInicio=2025-01-01&page=0&size=10
    @GetMapping
    public ResponseEntity<Page<DespesasResponseDTO>> listarComFiltros(
            DespesaFilterDTO filtro,
            @PageableDefault(sort = "data", size = 10) Pageable pageable) {
        
        Page<DespesasResponseDTO> page = despesaService.listarComFiltros(filtro, pageable);
        
        return ResponseEntity.ok(page);
    }

    // api/v1/despesas/resumo?mes=10&ano=2025
    @GetMapping("/resumo")
    public ResponseEntity<ResumoMensalDTO> gerarResumo(
            @RequestParam("mes") Integer mes, 
            @RequestParam("ano") Integer ano) {
        
        YearMonth yearMonth = YearMonth.of(ano, mes);
        ResumoMensalDTO resumo = despesaService.gerarResumoMensal(yearMonth);
        
        return ResponseEntity.ok(resumo);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<DespesasResponseDTO> atualizar(
            @PathVariable Long id, 
            @RequestBody @Valid DespesasDTO dto) {

        Despesa despesaToUpdate = mapToEntity(dto);
        Despesa despesaAtualizada = despesaService.atualizar(id, despesaToUpdate);

        return ResponseEntity.ok(mapToResponseDTO(despesaAtualizada));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        despesaService.excluir(id);
        return ResponseEntity.noContent().build(); 
    }

}
