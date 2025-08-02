package br.edu.infnet.karlaapi.model.service;

import br.edu.infnet.karlaapi.model.domain.entities.Tecnico;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
public class TecnicoService {

    public String obter(){
        return "Karla Almeida";
    }
}
