package br.edu.infnet.karlaapi.auth.dto;

import java.util.List;

public record LoginJwtResponse(String login, List<String> roles, String token) {



}
