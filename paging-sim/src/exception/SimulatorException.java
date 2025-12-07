package exception;

/**
 * Exceção base para erros do simulador.
 * Representa erros gerais que podem ocorrer durante a execução.
 */
public class SimulatorException extends RuntimeException {

    public SimulatorException(String message) {
        super(message);
    }

    public SimulatorException(String message, Throwable cause) {
        super(message, cause);
    }
}
