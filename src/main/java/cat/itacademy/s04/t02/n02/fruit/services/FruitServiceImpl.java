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
import org.springframework.stereotype.Service;

@Service
public class FruitServiceImpl implements FruitService {

    private final FruitRepository fruitRepository;
    private final ProviderRepository providerRepository;

    public FruitServiceImpl(FruitRepository fruitRepository, ProviderRepository providerRepository) {
        this.fruitRepository = fruitRepository;
        this.providerRepository = providerRepository;
    }

    @Override
    public FruitResponseDTO createFruit(FruitRequestDTO dto) {
        validate(dto);

        Provider provider = providerRepository.findById(dto.providerId())
                .orElseThrow(() -> new ProviderNotFoundException(
                        "Provider with ID " + dto.providerId() + " not found."
                ));

        Fruit fruit = new Fruit(dto.name(), dto.weight(), provider);
        Fruit saved = fruitRepository.save(fruit);

        return new FruitResponseDTO(saved.getId(), saved.getName(), saved.getWeight(), saved.getProvider().getId(), saved.getProvider().getName());
    }

    private void validate(FruitRequestDTO dto) {
        if (dto.name() == null || dto.name().trim().isEmpty()) {
            throw new InvalidFruitNameException("Fruit name cannot be null or blank.");
        }

        if (dto.weight() <= 0) {
            throw new InvalidFruitWeightException("Fruit weight must be greater than 0.");
        }

        if (dto.providerId() == null) {
            throw new InvalidFruitProviderException("Fruit must have a provider ID.");
        }
    }
}