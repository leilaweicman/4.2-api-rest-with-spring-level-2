package cat.itacademy.s04.t02.n02.fruit.model;

import jakarta.persistence.*;

@Entity
@Table(name = "fruits")
public class Fruit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int weight;

    @ManyToOne(optional = false)
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    public Fruit() {
    }

    public Fruit(String name, int weight, Provider provider) {
        this.name = name;
        this.weight = weight;
        this.provider = provider;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public Provider getProvider() {
        return provider;
    }
}
