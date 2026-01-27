package br.edu.infnet.karlaapi.model.service;

import br.edu.infnet.karlaapi.model.domain.dto.in.AtivoRequestDTO;
import br.edu.infnet.karlaapi.model.domain.dto.out.AtivoResponseDTO;
import br.edu.infnet.karlaapi.model.domain.entities.Ativo;
import br.edu.infnet.karlaapi.model.domain.entities.EnderecoGeorreferenciado;
import br.edu.infnet.karlaapi.model.infraestructure.enums.StatusAtivo;
import br.edu.infnet.karlaapi.model.infraestructure.enums.TipoAtivo;
import br.edu.infnet.karlaapi.model.infraestructure.exceptions.ResourceNotFoundException;
import br.edu.infnet.karlaapi.model.repository.AtivoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AtivoService{

    private final AtivoRepository ativoRepository;
    private final EnderecoGeorreferenciadoService enderecoGeorreferenciadoService;

    public AtivoService(AtivoRepository ativoRepository,
                        EnderecoGeorreferenciadoService enderecoGeorreferenciadoService) {
        this.ativoRepository = ativoRepository;
        this.enderecoGeorreferenciadoService = enderecoGeorreferenciadoService;
    }

    public AtivoResponseDTO incluir(AtivoRequestDTO ativoRequestDTO) {

        Ativo ativo = new Ativo();
        ativo.setTipoAtivo(TipoAtivo.fromString(ativoRequestDTO.getTipoAtivo()));
        ativo.setDataInstalacao(ativoRequestDTO.getDataInstalacao());
        ativo.setStatusAtivo(StatusAtivo.ATIVO);

        String cepLimpo = ativoRequestDTO.getCep().replace("-", "");

        EnderecoGeorreferenciado enderecoGeorreferenciado =
                enderecoGeorreferenciadoService.obterEnderecoGeorreferenciadoPorCep(cepLimpo);

        ativo.setEndereco(enderecoGeorreferenciado);

        return new AtivoResponseDTO(ativoRepository.save(ativo));
    }

    public AtivoResponseDTO alterar(Integer id, AtivoRequestDTO ativoRequestDTO) {

        Ativo ativo = ativoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ativo não encontrado"));

        if(ativoRequestDTO.getTipoAtivo() != null)
            ativo.setTipoAtivo(TipoAtivo.fromString(ativoRequestDTO.getTipoAtivo()));

        if(ativoRequestDTO.getDataInstalacao() != null)
            ativo.setDataInstalacao(ativoRequestDTO.getDataInstalacao());

        if(ativoRequestDTO.getCep() != null) {
            String cepLimpo = ativoRequestDTO.getCep().replace("-", "");

            EnderecoGeorreferenciado enderecoGeorreferenciado =
                    enderecoGeorreferenciadoService.obterEnderecoGeorreferenciadoPorCep(cepLimpo);

            ativo.setEndereco(enderecoGeorreferenciado);
        }

        Ativo ativoAtualizado = ativoRepository.save(ativo);

        return new AtivoResponseDTO(ativoAtualizado);
    }

    public AtivoResponseDTO alterarStatus(Integer id, String status){
        Ativo ativo = ativoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ativo não encontrado"));

        StatusAtivo statusNovo = StatusAtivo.fromString(status);

        if(statusNovo.equals(ativo.getStatusAtivo())){
            throw new IllegalStateException("O status atual do ativo já é " + status);
        }

        ativo.setStatusAtivo(statusNovo);

        return new AtivoResponseDTO(ativoRepository.save(ativo));
    }

    public AtivoResponseDTO obterPorId(Integer id) {
        Ativo ativo = ativoRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("O ativo com ID " + id + " não foi encontrado."));

        return new AtivoResponseDTO(ativo);
    }

    /*public List<AtivoResponseDTO> obterLista() {
        return ativoRepository.findAll()
                .stream()
                .map(AtivoResponseDTO::new) // chama o construtor DTO(Tecnico)
                .toList();
    }*/

    public Page<AtivoResponseDTO> obterLista(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        return ativoRepository.findAll(pageable)
                .map(AtivoResponseDTO::new);
    }

    public void excluir(Integer id) {
        Ativo ativo = ativoRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("O ativo com ID " + id + " não foi encontrado."));
        ativoRepository.delete(ativo);
    }

}
