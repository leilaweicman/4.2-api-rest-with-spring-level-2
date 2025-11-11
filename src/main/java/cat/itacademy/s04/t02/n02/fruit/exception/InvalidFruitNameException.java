package cat.itacademy.s04.t02.n02.fruit.exception;

public class InvalidFruitNameException extends RuntimeException {
    public InvalidFruitNameException(String message) {
        super(message);
    }
}
