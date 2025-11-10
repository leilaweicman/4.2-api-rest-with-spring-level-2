package cat.itacademy.s04.t02.n02.fruit.services;

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
        Provider provider = new Provider("Frutas Tropic", "Spain");
        when(providerRepository.save(any(Provider.class))).thenReturn(provider);

        Provider result = providerService.createProvider(provider);

        assertEquals("Frutas Tropic", result.getName());
        assertEquals("Spain", result.getCountry());

        verify(providerValidator).validate(provider);
        verify(providerRepository).save(any(Provider.class));
    }

    @Test
    void createProvider_shouldThrowException_whenNameIsEmpty() {
        Provider provider = new Provider("", "Spain");

        doThrow(new InvalidProviderNameException("Provider name cannot be empty."))
                .when(providerValidator).validate(provider);

        assertThrows(InvalidProviderNameException.class, () -> providerService.createProvider(provider));

        verify(providerValidator).validate(provider);
        verify(providerRepository, never()).save(any());
    }

    @Test
    void createProvider_shouldThrowException_whenNameExists() {
        Provider provider = new Provider("Frutas Tropic", "Spain");

        doThrow(new DuplicateProviderNameException("Provider already exists."))
                .when(providerValidator).validate(provider);

        assertThrows(DuplicateProviderNameException.class, () -> providerService.createProvider(provider));

        verify(providerValidator).validate(provider);
        verify(providerRepository, never()).save(any());
    }

    @Test
    void createProvider_shouldThrowException_whenCountryIsEmpty() {
        Provider provider = new Provider("Frutas Tropic", "");

        doThrow(new InvalidProviderCountryException("Country cannot be empty."))
                .when(providerValidator).validate(provider);

        assertThrows(InvalidProviderCountryException.class, () -> providerService.createProvider(provider));

        verify(providerValidator).validate(provider);
        verify(providerRepository, never()).save(any());
    }

}
