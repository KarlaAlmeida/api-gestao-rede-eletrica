package br.edu.infnet.karlaapi.model.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Geolocalizacao {

    private String lat;
    private String lon;

}
