package br.com.desafio.despesaspessoais.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.desafio.despesaspessoais.DTO.CategoriaSimplesDTO;
import br.com.desafio.despesaspessoais.DTO.DespesaFilterDTO;
import br.com.desafio.despesaspessoais.DTO.DespesasResponseDTO;
import br.com.desafio.despesaspessoais.DTO.ResumoMensalDTO;
import br.com.desafio.despesaspessoais.DTO.ResumoProjection;
import br.com.desafio.despesaspessoais.entities.Categoria;
import br.com.desafio.despesaspessoais.entities.Despesa;
import br.com.desafio.despesaspessoais.exeception.BadRequestException;
import br.com.desafio.despesaspessoais.exeception.ResourceNotFoundException;
import br.com.desafio.despesaspessoais.repository.DespesaRepository;
import br.com.desafio.despesaspessoais.specifications.DespesaSpecification;
import jakarta.transaction.Transactional;

@Service
public class DespesaService {

	private final DespesaRepository despesaRepository;
	private final CategoriaService categoriaService; 

	public DespesaService(DespesaRepository despesaRepository, CategoriaService categoriaService) {
		this.despesaRepository = despesaRepository;
		this.categoriaService = categoriaService;
	}

	@Transactional
	public Despesa criar(Despesa despesa) {
		validarRegras(despesa);
		Long categoriaId = despesa.getCategoria().getId(); // ver se existe
		Categoria categoria = categoriaService.buscarPorId(categoriaId); 
		despesa.setCategoria(categoria);
		return despesaRepository.save(despesa);
	}

	public Despesa buscarPorId(Long id) {
		return despesaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Despesa não encontrada com id " + id));
	}

	@Transactional
	public Despesa atualizar(Long id, Despesa despesaAtualizada) {
		Despesa despesaExistente = buscarPorId(id); 
		validarRegras(despesaAtualizada);
		if (!despesaExistente.getCategoria().getId().equals(despesaAtualizada.getCategoria().getId())) { 																					// existe
			Long novaCategoriaId = despesaAtualizada.getCategoria().getId();
			Categoria novaCategoria = categoriaService.buscarPorId(novaCategoriaId);
			despesaExistente.setCategoria(novaCategoria);
		}

		// Atualiza campos
		despesaExistente.setDescricao(despesaAtualizada.getDescricao());
		despesaExistente.setValor(despesaAtualizada.getValor());
		despesaExistente.setData(despesaAtualizada.getData());
		despesaExistente.setFormaDePagamento(despesaAtualizada.getFormaDePagamento());

		return despesaRepository.save(despesaExistente);
	}

	@Transactional
	public void excluir(Long id) {
		buscarPorId(id);
		despesaRepository.deleteById(id);
	}

	private void validarRegras(Despesa despesa) {
		if (despesa.getValor().compareTo(BigDecimal.ZERO) <= 0) {
			throw new BadRequestException(
					"O valor da despesa deve ser maior que zero."); // erro 400
		}
	}

	public Page<DespesasResponseDTO> listarComFiltros(DespesaFilterDTO filtro, Pageable pageable) {
		
		Specification<Despesa> specif = DespesaSpecification.build(filtro);

		return despesaRepository.findAll(specif, pageable).map(this::mapToResponseDTO);
	}

	
	public ResumoMensalDTO gerarResumoMensal(YearMonth mes) {
		LocalDate dataInicio = mes.atDay(1);
		LocalDate dataFim = mes.atEndOfMonth();

		// 1. Executa a query customizada para obter o total por categoria
		List<ResumoProjection> itensResumo = despesaRepository.calcularTotalPorCategoriaNoMes(dataInicio, dataFim);

		// 2. Calcula o Total Geral somando os totais das categorias
		BigDecimal totalGeral = itensResumo.stream().map(ResumoProjection::getTotal).reduce(BigDecimal.ZERO,
				BigDecimal::add);

		return new ResumoMensalDTO(mes.toString(), itensResumo, totalGeral);
	}

	private DespesasResponseDTO mapToResponseDTO(Despesa despesa) {

		CategoriaSimplesDTO categoriaDTO = new CategoriaSimplesDTO(despesa.getCategoria().getId(),
				despesa.getCategoria().getNome());
		// Mapeamento da Despesa
		return new DespesasResponseDTO(despesa.getId(), despesa.getDescricao(), despesa.getValor(), despesa.getData(),
				despesa.getFormaDePagamento().toString(), categoriaDTO);
	}
}
