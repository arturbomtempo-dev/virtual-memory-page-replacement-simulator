package exception;

/**
 * Exceção lançada quando a entrada fornecida é inválida.
 * Armazena o campo e valor inválido para facilitar diagnóstico.
 */
public class InvalidInputException extends SimulatorException {

    private final String fieldName;
    private final Object invalidValue;

    public InvalidInputException(String message) {
        super(message);
        this.fieldName = null;
        this.invalidValue = null;
    }

    public InvalidInputException(String fieldName, Object invalidValue, String reason) {
        super(String.format("Campo '%s' inválido (valor: %s): %s", fieldName, invalidValue, reason));
        this.fieldName = fieldName;
        this.invalidValue = invalidValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getInvalidValue() {
        return invalidValue;
    }
}
