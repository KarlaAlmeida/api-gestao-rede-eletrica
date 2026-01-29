package br.edu.infnet.karlaapi.controller;

import br.edu.infnet.karlaapi.model.domain.dto.DashboardResponseDTO;
import br.edu.infnet.karlaapi.model.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DashboardController {


    private final DashboardService dashboardService;


    @GetMapping
    public ResponseEntity<DashboardResponseDTO> obterDashboard() {
        return ResponseEntity.ok(dashboardService.obterDashboard());
    }
}
