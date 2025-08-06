package br.edu.infnet.karlaapi;

import br.edu.infnet.karlaapi.model.domain.entities.Endereco;
import br.edu.infnet.karlaapi.model.domain.entities.Tecnico;
import br.edu.infnet.karlaapi.model.service.TecnicoService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;

@Component
public class TecnicoLoader implements ApplicationRunner{

    private final TecnicoService tecnicoService;

    public TecnicoLoader(TecnicoService tecnicoService) {
        this.tecnicoService = tecnicoService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        FileReader arquivo = new FileReader("tecnico.txt");
        BufferedReader leitura = new BufferedReader(arquivo);

        String linha = leitura.readLine();

        String[] campos = null;

        while (linha != null) {

            campos = linha.split(";");

            Tecnico tecnico = new Tecnico();
            tecnico.setNome(campos[0]);
            tecnico.setCpf(campos[1]);
            tecnico.setEmail(campos[2]);
            tecnico.setTelefone(campos[3]);
            tecnico.setUltimoSalario(Double.valueOf(campos[4]));
            tecnico.setEhAtivo(Boolean.valueOf(campos[5]));
            tecnico.setEspecialidade(campos[6]);
            tecnico.setDisponivel(Boolean.valueOf(campos[7]));

            Endereco endereco = new Endereco();
            endereco.setCep(campos[8]);
            endereco.setRua(campos[9]);
            endereco.setNumero(Integer.valueOf(campos[10]));
            endereco.setComplemento(campos[11]);
            endereco.setCidade(campos[12]);
            endereco.setEstado(campos[13]);

            tecnico.setEndereco(endereco);

            tecnicoService.salvar(tecnico);

            System.out.println(tecnico);

            linha = leitura.readLine();
        }

        System.out.println(" - " + tecnicoService.obterLista().size());

        leitura.close();
    }
}
