package br.edu.infnet.karlaapi.model.loader;

import br.edu.infnet.karlaapi.model.domain.entities.*;
import br.edu.infnet.karlaapi.model.infraestructure.enums.*;
import br.edu.infnet.karlaapi.model.service.OrdemServicoService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;

@Component
@Order(4)
public class OrdemServicoLoader implements ApplicationRunner{

    private final OrdemServicoService ordemServicoService;

    public OrdemServicoLoader(OrdemServicoService ordemServicoService) {
        this.ordemServicoService = ordemServicoService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        FileReader arquivo = new FileReader("ordem-servico.txt");
        BufferedReader leitura = new BufferedReader(arquivo);

        String linha = leitura.readLine();

        String[] campos = null;

        while (linha != null) {

            campos = linha.split(";");

            OrdemServico ordemServico = new OrdemServico();

            Ativo ativo = new Ativo();

            Endereco endereco = new Endereco();
            endereco.setCep(campos[0]);
            endereco.setRua(campos[1]);
            endereco.setNumero(Integer.valueOf(campos[2]));
            endereco.setComplemento(campos[3]);
            endereco.setCidade(campos[4]);
            endereco.setEstado(campos[5]);

            ativo.setEndereco(endereco);
            ativo.setTipoAtivo(TipoAtivo.valueOf(campos[6]));
            ativo.setDataInstalacao(LocalDate.parse(campos[7]));
            ativo.setStatusAtivo(StatusAtivo.valueOf(campos[8]));
            ativo.setId(Integer.valueOf(campos[9]));

            Ocorrencia ocorrencia = new Ocorrencia();

            ocorrencia.setAtivo(ativo);
            ocorrencia.setDescricaoOcorrencia(campos[10]);
            ocorrencia.setDataRegistroOcorrencia(LocalDate.now());
            ocorrencia.setPrioridadeOcorrecia(PrioridadeOcorrecia.valueOf(campos[11]));
            ocorrencia.setStatusOcorrecia(StatusOcorrecia.REGISTRADA);

            Tecnico tecnico = new Tecnico();
            tecnico.setNome(campos[12]);
            tecnico.setCpf(campos[13]);
            tecnico.setEmail(campos[14]);
            tecnico.setTelefone(campos[15]);
            tecnico.setUltimoSalario(Double.valueOf(campos[16]));
            tecnico.setAtivo(Boolean.valueOf(campos[17]));
            tecnico.setEspecialidade(campos[18]);
            tecnico.setDisponivel(Boolean.valueOf(campos[19]));

            Endereco enderecoTecnico = new Endereco();
            enderecoTecnico.setCep(campos[20]);
            enderecoTecnico.setRua(campos[21]);
            enderecoTecnico.setNumero(Integer.valueOf(campos[22]));
            enderecoTecnico.setComplemento(campos[23]);
            enderecoTecnico.setCidade(campos[24]);
            enderecoTecnico.setEstado(campos[25]);

            tecnico.setEndereco(enderecoTecnico);

            ordemServico.setOcorrencia(ocorrencia);
            ordemServico.setTecnico(tecnico);
            ordemServico.setDescricaoServico(campos[26]);
            ordemServico.setDataCriacaoOS(LocalDate.parse(campos[27]));
            ordemServico.setDataCriacaoOS(LocalDate.parse(campos[28]));
            ordemServico.setStatusOS(StatusOS.valueOf(campos[29]));

            ordemServicoService.incluir(ordemServico);

            System.out.println(ordemServico);

            linha = leitura.readLine();
        }

        System.out.println(" - " + ordemServicoService.obterLista().size());

        leitura.close();
    }
}
