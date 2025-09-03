package br.edu.infnet.karlaapi.model.loader;

import br.edu.infnet.karlaapi.model.domain.dto.in.OcorrenciaRequestDTO;
import br.edu.infnet.karlaapi.model.domain.entities.Ativo;
import br.edu.infnet.karlaapi.model.domain.entities.Endereco;
import br.edu.infnet.karlaapi.model.domain.entities.Ocorrencia;
import br.edu.infnet.karlaapi.model.infraestructure.enums.PrioridadeOcorrencia;
import br.edu.infnet.karlaapi.model.infraestructure.enums.StatusAtivo;
import br.edu.infnet.karlaapi.model.infraestructure.enums.StatusOcorrencia;
import br.edu.infnet.karlaapi.model.infraestructure.enums.TipoAtivo;
import br.edu.infnet.karlaapi.model.service.OcorrenciaService;
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

    private final OcorrenciaService ocorrenciaService;

    public OcorrenciaLoader(OcorrenciaService ocorrenciaService) {
        this.ocorrenciaService = ocorrenciaService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        FileReader arquivo = new FileReader("ocorrencia.txt");
        BufferedReader leitura = new BufferedReader(arquivo);

        String linha = leitura.readLine();

        String[] campos = null;

        while (linha != null) {

            campos = linha.split(";");

            OcorrenciaRequestDTO ocorrencia = new OcorrenciaRequestDTO();

            ocorrencia.setAtivoId(Integer.valueOf(campos[0]));
            ocorrencia.setDescricaoOcorrencia(campos[1]);
            ocorrencia.setPrioridadeOcorrencia(campos[2]);

            ocorrenciaService.incluir(ocorrencia);

            System.out.println(ocorrencia);

            linha = leitura.readLine();
        }

        System.out.println(" - " + ocorrenciaService.obterLista().size());

        leitura.close();
    }
}
