package cat.itacademy.s04.t02.n02.fruit.controllers;

import cat.itacademy.s04.t02.n02.fruit.dto.ProviderRequestDTO;
import cat.itacademy.s04.t02.n02.fruit.repository.ProviderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProviderIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProviderRepository providerRepository;

    @BeforeEach
    void cleanDatabase() {
        providerRepository.deleteAll();
    }

    @Test
    void createProvider_returnsCreatedProvider() throws Exception {
        ProviderRequestDTO provider = new ProviderRequestDTO("Frutas Tropic", "Spain");

        mockMvc.perform(post("/providers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(provider)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Frutas Tropic"))
                .andExpect(jsonPath("$.country").value("Spain"));
    }

    @Test
    void createProvider_returnsBadRequest_whenNameIsBlank() throws Exception {
        ProviderRequestDTO provider = new ProviderRequestDTO("", "Spain");

        mockMvc.perform(post("/providers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(provider)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createProvider_returnsConflict_whenNameAlreadyExists() throws Exception {
        ProviderRequestDTO provider = new ProviderRequestDTO("Frutas Tropic", "Spain");

        mockMvc.perform(post("/providers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(provider)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/providers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(provider)))
                .andExpect(status().isConflict());
    }

    @Test
    void createProvider_returnsBadRequest_whenCountryIsBlank() throws Exception {
        ProviderRequestDTO provider = new ProviderRequestDTO("Frutas Tropic", "");

        mockMvc.perform(post("/providers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(provider)))
                .andExpect(status().isBadRequest());
    }
}
