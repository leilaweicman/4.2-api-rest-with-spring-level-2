package cat.itacademy.s04.t02.n02.fruit.services;

import cat.itacademy.s04.t02.n02.fruit.dto.FruitRequestDTO;
import cat.itacademy.s04.t02.n02.fruit.dto.FruitResponseDTO;
import cat.itacademy.s04.t02.n02.fruit.exception.InvalidFruitNameException;
import cat.itacademy.s04.t02.n02.fruit.exception.InvalidFruitProviderException;
import cat.itacademy.s04.t02.n02.fruit.exception.InvalidFruitWeightException;
import cat.itacademy.s04.t02.n02.fruit.exception.ProviderNotFoundException;
import cat.itacademy.s04.t02.n02.fruit.model.Fruit;
import cat.itacademy.s04.t02.n02.fruit.model.Provider;
import cat.itacademy.s04.t02.n02.fruit.repository.FruitRepository;
import cat.itacademy.s04.t02.n02.fruit.repository.ProviderRepository;
import cat.itacademy.s04.t02.n02.fruit.validator.FruitValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FruitServiceTest {

    @Mock
    private FruitRepository fruitRepository;
    @Mock
    private ProviderRepository providerRepository;

    private FruitServiceImpl fruitService;

    private final FruitValidator fruitValidator = new FruitValidator();
    @BeforeEach
    void setUp() {
        fruitService = new FruitServiceImpl(fruitRepository, providerRepository, fruitValidator);
    }

    @Test
    void createFruit_shouldAddFruit_whenValidData() {
        Provider provider = new Provider("Frutas Tropic", "Spain");
        ReflectionTestUtils.setField(provider, "id", 1L);

        Fruit fruit = new Fruit("Apple", 5, provider);

        when(providerRepository.findById(1L)).thenReturn(Optional.of(provider));
        when(fruitRepository.save(any(Fruit.class))).thenReturn(fruit);

        FruitResponseDTO result = fruitService.createFruit(new FruitRequestDTO("Apple", 5, 1L));

        assertEquals("Apple", result.name());
        assertEquals(5, result.weight());
        assertEquals("Frutas Tropic", result.providerName());

        verify(fruitRepository).save(any(Fruit.class));
    }

    @Test
    void createFruit_shouldThrowException_whenProviderIdIsNull() {
        assertThrows(InvalidFruitProviderException.class,
                () -> fruitService.createFruit(new FruitRequestDTO("Apple", 5, null)));
    }

    @Test
    void createFruit_shouldThrowException_whenProviderNotFound() {
        when(providerRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ProviderNotFoundException.class,
                () -> fruitService.createFruit(new FruitRequestDTO("Banana", 3, 99L)));

        verify(fruitRepository, never()).save(any());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void createFruit_shouldThrowException_whenNameIsNullOrBlank(String name) {
        assertThrows(InvalidFruitNameException.class,
                () -> fruitService.createFruit(new FruitRequestDTO(name, 5, 1L)));
    }

    @Test
    void createFruit_shouldThrowException_whenWeightIsInvalid() {
        assertThrows(InvalidFruitWeightException.class,
                () -> fruitService.createFruit(new FruitRequestDTO("Apple", -1, 1L)));
    }
}
