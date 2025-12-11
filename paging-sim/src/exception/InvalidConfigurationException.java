package exception;

/**
 * Exceção lançada quando as configurações do sistema são inválidas
 * (ex: memória virtual < física, tamanho de página não é potência de 2).
 */
public class InvalidConfigurationException extends SimulatorException {

    public InvalidConfigurationException(String message) {
        super(message);
    }

    public InvalidConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
