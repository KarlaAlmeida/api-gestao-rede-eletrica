package br.edu.infnet.karlaapi.model.service;

import br.edu.infnet.karlaapi.model.clients.OpenStreetMapFeignClient;
import br.edu.infnet.karlaapi.model.clients.ViaCepFeignClient;
import br.edu.infnet.karlaapi.model.domain.entities.EnderecoGeorreferenciado;
import br.edu.infnet.karlaapi.model.domain.entities.Geolocalizacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EnderecoGeorreferenciadoServiceTest {

    @Mock
    private ViaCepFeignClient viaCepFeignClient;

    @Mock
    private OpenStreetMapFeignClient openStreetMapFeignClient;

    @InjectMocks
    private EnderecoGeorreferenciadoService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(service, "userAgent", "Infnet-Karla-Georef-App-2026");
    }

    @Test
    void testObterEnderecoGeorreferenciadoPorCep() {
        String cep = "59150015";
        EnderecoGeorreferenciado endereco = new EnderecoGeorreferenciado();
        endereco.setCep(cep);
        endereco.setLogradouro("Rua Pantanal");
        endereco.setLocalidade("Parnamirim");
        endereco.setUf("RN");

        Geolocalizacao geolocalizacao = new Geolocalizacao();
        geolocalizacao.setLat("-5.918");
        geolocalizacao.setLon("-35.275");
        List<Geolocalizacao> geolocalizacoes = Collections.singletonList(geolocalizacao);

        when(viaCepFeignClient.findByCep(cep)).thenReturn(endereco);
        when(openStreetMapFeignClient.search(anyString(), anyString(), anyInt(), anyString())).thenReturn(geolocalizacoes);

        EnderecoGeorreferenciado result = service.obterEnderecoGeorreferenciadoPorCep(cep);

        assertNotNull(result);
        assertEquals("-5.918", result.getLatitude());
        assertEquals("-35.275", result.getLongitude());
        verify(openStreetMapFeignClient).search(contains("Rua Pantanal"), eq("jsonv2"), eq(10), eq("Infnet-Karla-Georef-App-2026"));
    }
}
