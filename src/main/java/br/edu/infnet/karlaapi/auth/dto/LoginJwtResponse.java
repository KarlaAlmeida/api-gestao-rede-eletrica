package br.edu.infnet.karlaapi.auth.dto;

import java.util.List;

public record LoginJwtResponse(String username, List<String> roles, String token) {



}
