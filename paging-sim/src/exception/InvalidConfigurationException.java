package exception;

/**
 * Exceção lançada quando as configurações do sistema são inválidas ou
 * inconsistentes.
 */
public class InvalidConfigurationException extends SimulatorException {

    public InvalidConfigurationException(String message) {
        super(message);
    }

    public InvalidConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
