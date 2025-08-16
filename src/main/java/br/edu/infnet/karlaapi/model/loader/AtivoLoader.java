package br.edu.infnet.karlaapi.model.loader;

import br.edu.infnet.karlaapi.model.domain.entities.Ativo;
import br.edu.infnet.karlaapi.model.domain.entities.Endereco;
import br.edu.infnet.karlaapi.model.infraestructure.enums.StatusAtivo;
import br.edu.infnet.karlaapi.model.infraestructure.enums.TipoAtivo;
import br.edu.infnet.karlaapi.model.service.AtivoService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;

@Component
@Order(2)
public class AtivoLoader implements ApplicationRunner{

    private final AtivoService ativoService;

    public AtivoLoader(AtivoService ativoService) {
        this.ativoService = ativoService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        FileReader arquivo = new FileReader("ativo.txt");
        BufferedReader leitura = new BufferedReader(arquivo);

        String linha = leitura.readLine();

        String[] campos = null;

        while (linha != null) {

            campos = linha.split(";");

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

            ativoService.incluir(ativo);

            System.out.println(ativo);

            linha = leitura.readLine();
        }

        System.out.println(" - " + ativoService.obterLista().size());

        leitura.close();
    }
}
