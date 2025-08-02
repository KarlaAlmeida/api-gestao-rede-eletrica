package br.edu.infnet.karlaapi;

import br.edu.infnet.karlaapi.model.domain.entities.Tecnico;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;

@Component
public class TecnicoLoader implements ApplicationRunner{

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
            tecnico.setMatricula(Integer.valueOf(campos[4]));
            tecnico.setUltimoSalario(Double.valueOf(campos[5]));
            tecnico.setEhAtivo(Boolean.valueOf(campos[6]));
            tecnico.setId(Integer.valueOf(campos[7]));
            tecnico.setEspecialidade(campos[8]);
            tecnico.setDisponivel(Boolean.valueOf(campos[9]));

            System.out.println(tecnico);

            linha = leitura.readLine();
        }
        leitura.close();
    }
}
