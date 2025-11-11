package cat.itacademy.s04.t02.n02.fruit.controllers;

import cat.itacademy.s04.t02.n02.fruit.dto.ProviderRequestDTO;
import cat.itacademy.s04.t02.n02.fruit.dto.ProviderResponseDTO;
import cat.itacademy.s04.t02.n02.fruit.exception.DuplicateProviderNameException;
import cat.itacademy.s04.t02.n02.fruit.services.ProviderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/providers")
public class ProviderController {

    private final ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @PostMapping
    public ResponseEntity<ProviderResponseDTO> createProvider(@Valid @RequestBody ProviderRequestDTO dto) {
        ProviderResponseDTO created = providerService.createProvider(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @ExceptionHandler(DuplicateProviderNameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleDuplicateProvider(DuplicateProviderNameException ex) {
        return ex.getMessage();
    }
}
