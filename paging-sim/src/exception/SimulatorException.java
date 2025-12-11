package exception;

/**
 * Exceção base para todos os erros do simulador.
 */
public class SimulatorException extends RuntimeException {

    public SimulatorException(String message) {
        super(message);
    }

    public SimulatorException(String message, Throwable cause) {
        super(message, cause);
    }
}
