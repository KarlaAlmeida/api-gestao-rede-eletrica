package br.edu.infnet.karlaapi.model.service;

import br.edu.infnet.karlaapi.model.domain.dto.in.TecnicoRequestDTO;
import br.edu.infnet.karlaapi.model.domain.dto.out.EnderecoGeorreferenciadoResponseDTO;
import br.edu.infnet.karlaapi.model.domain.dto.out.TecnicoResponseDTO;
import br.edu.infnet.karlaapi.model.domain.entities.EnderecoGeorreferenciado;
import br.edu.infnet.karlaapi.model.domain.entities.Tecnico;
import br.edu.infnet.karlaapi.model.infraestructure.exceptions.ResourceNotFoundException;
import br.edu.infnet.karlaapi.model.repository.TecnicoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TecnicoService{

    private final TecnicoRepository tecnicoRepository;
    private final EnderecoGeorreferenciadoService enderecoGeorreferenciadoService;

    public TecnicoService(TecnicoRepository tecnicoRepository,
                          EnderecoGeorreferenciadoService enderecoGeorreferenciadoService) {
        this.tecnicoRepository = tecnicoRepository;
        this.enderecoGeorreferenciadoService = enderecoGeorreferenciadoService;
    }


    public TecnicoResponseDTO incluir(TecnicoRequestDTO tecnicoRequestDTO) {

        Tecnico tecnico = new Tecnico();
        tecnico.setNome(tecnicoRequestDTO.getNome());
        tecnico.setCpf(tecnicoRequestDTO.getCpf());
        tecnico.setEmail(tecnicoRequestDTO.getEmail());
        tecnico.setTelefone(tecnicoRequestDTO.getTelefone());
        tecnico.setUltimoSalario(tecnicoRequestDTO.getUltimoSalario());
        tecnico.setAtivo(tecnicoRequestDTO.isAtivo());
        tecnico.setEspecialidade(tecnicoRequestDTO.getEspecialidade());
        tecnico.setDisponivel(tecnicoRequestDTO.isDisponivel());

        String cepLimpo = tecnicoRequestDTO.getCep().replace("-", "");

        EnderecoGeorreferenciadoResponseDTO enderecoGeorreferenciadoResponseDTO =
                enderecoGeorreferenciadoService.obterEnderecoGeorreferenciadoPorCep(cepLimpo);

        EnderecoGeorreferenciado enderecoGeorreferenciado =
                new EnderecoGeorreferenciado(enderecoGeorreferenciadoResponseDTO);

        enderecoGeorreferenciado.setNumero(tecnicoRequestDTO.getNumero());
        enderecoGeorreferenciado.setComplementoNumero(tecnicoRequestDTO.getComplementoNumero());

        tecnico.setEndereco(enderecoGeorreferenciado);

        return new TecnicoResponseDTO(tecnicoRepository.save(tecnico));
    }


    public TecnicoResponseDTO alterar(Integer id, TecnicoRequestDTO tecnicoAtualizado) {

        TecnicoResponseDTO tecnicoResponseDTO = obterPorId(id);

        if(tecnicoAtualizado.getNome() != null) tecnicoResponseDTO.setNome(tecnicoAtualizado.getNome());
        if(tecnicoAtualizado.getCpf() != null) tecnicoResponseDTO.setCpf(tecnicoAtualizado.getCpf());
        if(tecnicoAtualizado.getEmail() != null) tecnicoResponseDTO.setEmail(tecnicoAtualizado.getEmail());
        if(tecnicoAtualizado.getTelefone() != null) tecnicoResponseDTO.setTelefone(tecnicoAtualizado.getTelefone());
        if(tecnicoAtualizado.getUltimoSalario() != 0) tecnicoResponseDTO.setUltimoSalario(tecnicoAtualizado.getUltimoSalario());
        if(tecnicoAtualizado.getEspecialidade() != null) tecnicoResponseDTO.setEspecialidade(tecnicoAtualizado.getEspecialidade());

        Tecnico tecnico = new Tecnico(tecnicoResponseDTO);

        if(tecnicoAtualizado.getCep() != null) {
            String cepLimpo = tecnicoAtualizado.getCep().replace("-", "");

            EnderecoGeorreferenciadoResponseDTO enderecoGeorreferenciadoResponseDTO =
                enderecoGeorreferenciadoService.obterEnderecoGeorreferenciadoPorCep(cepLimpo);

            EnderecoGeorreferenciado enderecoGeorreferenciado =
                new EnderecoGeorreferenciado(enderecoGeorreferenciadoResponseDTO);

            if(tecnicoAtualizado.getNumero() != 0) enderecoGeorreferenciado.setNumero(tecnicoAtualizado.getNumero());
            if(tecnicoAtualizado.getComplementoNumero() != null) enderecoGeorreferenciado.setComplementoNumero(tecnicoAtualizado.getComplementoNumero());

        tecnico.setEndereco(enderecoGeorreferenciado);
        }

        return new TecnicoResponseDTO(tecnicoRepository.save(tecnico));
    }

    public TecnicoResponseDTO inativar(Integer id) {
        TecnicoResponseDTO tecnicoResponseDTO = obterPorId(id);
        tecnicoResponseDTO.setId(id);

        if(!tecnicoResponseDTO.isAtivo()) {
            System.out.println("Técnico " + tecnicoResponseDTO.getNome() + " já está inativo!");
            return tecnicoResponseDTO;
        }
        tecnicoResponseDTO.setAtivo(false);

        Tecnico tecnico = new Tecnico(tecnicoResponseDTO);

        return new TecnicoResponseDTO(tecnicoRepository.save(tecnico));
    }

    public TecnicoResponseDTO alterarStatus(Integer id, boolean ativo) {
        Tecnico tecnico = tecnicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Técnico não encontrado"));

        tecnico.setAtivo(ativo);

        tecnicoRepository.save(tecnico);

        return new TecnicoResponseDTO(tecnico);
    }

    public TecnicoResponseDTO alterarDisponibilidade(Integer id, boolean disponivel) {
        Tecnico tecnico = tecnicoRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Técnico não encontrado"));

        tecnico.setDisponivel(disponivel);

        tecnicoRepository.save(tecnico);

        return new TecnicoResponseDTO(tecnico);
    }



    public TecnicoResponseDTO obterPorId(Integer id) {
        Tecnico tecnico = tecnicoRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("O técnico com ID " + id + " não foi encontrado."));

        return new TecnicoResponseDTO(tecnico);

    }

    /*public List<TecnicoResponseDTO> obterLista() {
        return tecnicoRepository.findAll()
                .stream()
                .map(TecnicoResponseDTO::new) // chama o construtor DTO(Tecnico)
                .toList();
    }*/

    public Page<TecnicoResponseDTO> obterLista(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        return tecnicoRepository.findAll(pageable)
                .map(TecnicoResponseDTO::new);
    }

    public List<TecnicoResponseDTO> buscarPorNomeEspecialidade(String nomePrefixo, String especialidade) {
        return tecnicoRepository.
                findByNomeStartingWithIgnoreCaseAndEspecialidadeIgnoreCase(
                        nomePrefixo, especialidade)
                .stream()
                .map(TecnicoResponseDTO::new) // chama o construtor DTO(Tecnico)
                .collect(Collectors.toList());
    }

    public void excluir(Integer id) {
        TecnicoResponseDTO tecnicoResponseDTO = obterPorId(id);
        tecnicoRepository.delete(new Tecnico(tecnicoResponseDTO));
    }

}
