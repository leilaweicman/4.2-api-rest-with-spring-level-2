package cat.itacademy.s04.t02.n02.fruit.services;

import cat.itacademy.s04.t02.n02.fruit.dto.ProviderRequestDTO;
import cat.itacademy.s04.t02.n02.fruit.dto.ProviderResponseDTO;
import cat.itacademy.s04.t02.n02.fruit.exception.DuplicateProviderNameException;
import cat.itacademy.s04.t02.n02.fruit.exception.InvalidProviderCountryException;
import cat.itacademy.s04.t02.n02.fruit.exception.InvalidProviderNameException;
import cat.itacademy.s04.t02.n02.fruit.model.Provider;
import cat.itacademy.s04.t02.n02.fruit.repository.ProviderRepository;
import cat.itacademy.s04.t02.n02.fruit.validator.ProviderValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProviderServiceTest {

    @Mock
    private ProviderRepository providerRepository;

    @Mock
    private ProviderValidator providerValidator;

    @InjectMocks
    private ProviderServiceImpl providerService;

    @Test
    void createProvider_shouldAddProvider_whenValidData() {
        ProviderRequestDTO dto = new ProviderRequestDTO("Frutas Tropic", "Spain");
        Provider savedProvider = new Provider(1L, "Frutas Tropic", "Spain");

        when(providerRepository.save(any(Provider.class))).thenReturn(savedProvider);

        ProviderResponseDTO result = providerService.createProvider(dto);

        assertNotNull(result);
        assertEquals("Frutas Tropic", result.name());
        assertEquals("Spain", result.country());

        verify(providerValidator).validate(any(Provider.class));
        verify(providerRepository).save(any(Provider.class));
    }

    @Test
    void createProvider_shouldThrowException_whenNameIsEmpty() {
        ProviderRequestDTO dto = new ProviderRequestDTO("", "Spain");

        doThrow(new InvalidProviderNameException("Provider name cannot be empty."))
                .when(providerValidator).validate(any(Provider.class));

        assertThrows(InvalidProviderNameException.class, () -> providerService.createProvider(dto));

        verify(providerValidator).validate(any(Provider.class));
        verify(providerRepository, never()).save(any());
    }

    @Test
    void createProvider_shouldThrowException_whenNameExists() {
        ProviderRequestDTO dto = new ProviderRequestDTO("Frutas Tropic", "Spain");

        doThrow(new DuplicateProviderNameException("Provider already exists."))
                .when(providerValidator).validate(any(Provider.class));

        assertThrows(DuplicateProviderNameException.class, () -> providerService.createProvider(dto));

        verify(providerValidator).validate(any(Provider.class));
        verify(providerRepository, never()).save(any());
    }

    @Test
    void createProvider_shouldThrowException_whenCountryIsEmpty() {
        ProviderRequestDTO dto = new ProviderRequestDTO("Frutas Tropic", "");

        doThrow(new InvalidProviderCountryException("Provider country cannot be empty."))
                .when(providerValidator).validate(any(Provider.class));

        assertThrows(InvalidProviderCountryException.class, () -> providerService.createProvider(dto));

        verify(providerValidator).validate(any(Provider.class));
        verify(providerRepository, never()).save(any());
    }

}
