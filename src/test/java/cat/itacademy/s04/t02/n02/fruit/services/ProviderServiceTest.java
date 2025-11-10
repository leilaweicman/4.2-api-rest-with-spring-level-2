package cat.itacademy.s04.t02.n02.fruit.services;

import cat.itacademy.s04.t02.n02.fruit.exception.DuplicateProviderNameException;
import cat.itacademy.s04.t02.n02.fruit.exception.InvalidProviderCountryException;
import cat.itacademy.s04.t02.n02.fruit.exception.InvalidProviderNameException;
import cat.itacademy.s04.t02.n02.fruit.model.Provider;
import cat.itacademy.s04.t02.n02.fruit.repository.ProviderRepository;
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

    @InjectMocks
    private ProviderServiceImpl providerService;

    @Test
    void createProvider_shouldAddProvider_whenValidData() {
        Provider provider = new Provider("Frutas Tropic", "Spain");
        when(providerRepository.existsByName("Frutas Tropic")).thenReturn(false);
        when(providerRepository.save(any(Provider.class))).thenReturn(provider);

        Provider result = providerService.createProvider(provider);

        assertEquals("Frutas Tropic", result.getName());
        assertEquals("Spain", result.getCountry());

        verify(providerRepository).save(any(Provider.class));
    }

    @Test
    void createProvider_shouldThrowException_whenNameIsEmpty() {
        Provider provider = new Provider("", "Spain");

        assertThrows(InvalidProviderNameException.class, () -> providerService.createProvider(provider));

        verify(providerRepository, never()).save(any());
    }

    @Test
    void createProvider_shouldThrowException_whenNameExists() {
        Provider provider = new Provider("Frutas Tropic", "Spain");
        when(providerRepository.existsByName("Frutas Tropic")).thenReturn(true);
        assertThrows(DuplicateProviderNameException.class, () -> providerService.createProvider(provider));

        verify(providerRepository, never()).save(any());
    }

    @Test
    void createProvider_shouldThrowException_whenCountryIsEmpty() {
        Provider provider = new Provider("Frutas Tropic", "");

        assertThrows(InvalidProviderCountryException.class, () -> providerService.createProvider(provider));

        verify(providerRepository, never()).save(any());
    }

}
