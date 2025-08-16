package br.edu.infnet.karlaapi.model.loader;

import br.edu.infnet.karlaapi.model.domain.entities.Ativo;
import br.edu.infnet.karlaapi.model.domain.entities.Endereco;
import br.edu.infnet.karlaapi.model.domain.entities.Ocorrencia;
import br.edu.infnet.karlaapi.model.infraestructure.enums.PrioridadeOcorrecia;
import br.edu.infnet.karlaapi.model.infraestructure.enums.StatusAtivo;
import br.edu.infnet.karlaapi.model.infraestructure.enums.StatusOcorrecia;
import br.edu.infnet.karlaapi.model.infraestructure.enums.TipoAtivo;
import br.edu.infnet.karlaapi.model.service.OcorreciaService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;

@Component
@Order(3)
public class OcorrenciaLoader implements ApplicationRunner{

    private final OcorreciaService ocorreciaService;

    public OcorrenciaLoader(OcorreciaService ocorreciaService) {
        this.ocorreciaService = ocorreciaService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        FileReader arquivo = new FileReader("ocorrencia.txt");
        BufferedReader leitura = new BufferedReader(arquivo);

        String linha = leitura.readLine();

        String[] campos = null;

        while (linha != null) {

            campos = linha.split(";");

            Ocorrencia ocorrencia = new Ocorrencia();

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
            ativo.setId(Integer.valueOf(campos[11]));

            ocorrencia.setAtivo(ativo);

            ocorrencia.setDescricaoOcorrencia(campos[9]);
            ocorrencia.setDataRegistroOcorrencia(LocalDate.now());
            ocorrencia.setPrioridadeOcorrecia(PrioridadeOcorrecia.valueOf(campos[10]));
            ocorrencia.setStatusOcorrecia(StatusOcorrecia.REGISTRADA);

           // ocorreciaService.incluir(ocorrencia);

            System.out.println(ocorrencia);

            linha = leitura.readLine();
        }

        System.out.println(" - " + ocorreciaService.obterLista().size());

        leitura.close();
    }
}
