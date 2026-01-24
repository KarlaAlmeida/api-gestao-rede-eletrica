package br.edu.infnet.karlaapi.model.service;

import br.edu.infnet.karlaapi.model.domain.dto.in.AtivoRequestDTO;
import br.edu.infnet.karlaapi.model.domain.dto.out.AtivoResponseDTO;
import br.edu.infnet.karlaapi.model.domain.dto.out.EnderecoGeorreferenciadoResponseDTO;
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

import java.util.List;
import java.util.UUID;

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

        EnderecoGeorreferenciadoResponseDTO enderecoGeorreferenciadoResponseDTO =
                enderecoGeorreferenciadoService.obterEnderecoGeorreferenciadoPorCep(cepLimpo);

        EnderecoGeorreferenciado enderecoGeorreferenciado =
                new EnderecoGeorreferenciado(enderecoGeorreferenciadoResponseDTO);

        ativo.setEndereco(enderecoGeorreferenciado);

        return new AtivoResponseDTO(ativoRepository.save(ativo));
    }

    public AtivoResponseDTO alterar(Integer id, AtivoRequestDTO ativoRequestDTO) {

        AtivoResponseDTO ativoResponseDTO = obterPorId(id);

        if(ativoRequestDTO.getTipoAtivo() != null)
            ativoResponseDTO.setTipoAtivo(TipoAtivo.fromString(ativoRequestDTO.getTipoAtivo()));

        if(ativoRequestDTO.getDataInstalacao() != null)
            ativoResponseDTO.setDataInstalacao(ativoRequestDTO.getDataInstalacao());

        if(ativoRequestDTO.getCep() != null) {
            String cepLimpo = ativoRequestDTO.getCep().replace("-", "");

            EnderecoGeorreferenciadoResponseDTO enderecoGeorreferenciadoResponseDTO =
                    enderecoGeorreferenciadoService.obterEnderecoGeorreferenciadoPorCep(cepLimpo);

            ativoResponseDTO.setEndereco(enderecoGeorreferenciadoResponseDTO);
        }

        Ativo ativo = new Ativo(ativoResponseDTO);

        return new AtivoResponseDTO(ativoRepository.save(ativo));
    }

    public AtivoResponseDTO alterarStatus(Integer id, String status){
        AtivoResponseDTO ativoResponseDTO = obterPorId(id);

        StatusAtivo statusNovo = StatusAtivo.fromString(status);

        if(statusNovo.equals(ativoResponseDTO.getStatusAtivo())){
            throw new IllegalStateException("O status atual do ativo já é " + status);
        }

        ativoResponseDTO.setStatusAtivo(statusNovo);

        Ativo ativo = new Ativo(ativoResponseDTO);

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
        AtivoResponseDTO ativoResponseDTO = obterPorId(id);
        ativoRepository.delete(new Ativo(ativoResponseDTO));
    }

}
