package br.edu.infnet.karlaapi.controller;

import br.edu.infnet.karlaapi.model.domain.entities.Tecnico;
import br.edu.infnet.karlaapi.model.service.TecnicoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/tecnicos")
@AllArgsConstructor
public class TecnicoController {

    private TecnicoService tecnicoService;

    @GetMapping
    public Tecnico obterTecnico(){
        Tecnico tecnico = new Tecnico();
        tecnico.setNome("Karla");
        tecnico.setCpf("123456789-00");
        tecnico.setEmail("karlacrika@gmail.com");
        tecnico.setTelefone("83996220199");
        tecnico.setMatricula(1234);
        tecnico.setUltimoSalario(5000.00);
        tecnico.setEhAtivo(true);
        tecnico.setId(1);
        tecnico.setEspecialidade("Elétrica");
        tecnico.setDisponivel(true);
        return tecnico;
    }
}
