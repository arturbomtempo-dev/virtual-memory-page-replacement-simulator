package validation;

import exception.InvalidInputException;
import exception.InvalidConfigurationException;

/**
 * Classe utilitária para validação de entrada.
 * Centraliza regras de validação e tratamento de erros.
 */
public final class InputValidator {

    private InputValidator() {
    }

    /**
     * Valida se um valor inteiro é positivo.
     */
    public static int requirePositive(int value, String fieldName) {
        if (value <= 0) {
            throw new InvalidInputException(fieldName, value, "deve ser positivo");
        }

        return value;
    }

    /**
     * Valida se um valor inteiro está dentro de um intervalo.
     */
    public static int requireInRange(int value, int min, int max, String fieldName) {
        if (value < min || value > max) {
            throw new InvalidInputException(
                    fieldName,
                    value,
                    String.format("deve estar entre %d e %d", min, max));
        }

        return value;
    }

    /**
     * Valida se uma string não é nula ou vazia.
     */
    public static String requireNonEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidInputException(fieldName, value, "não pode ser vazio");
        }

        return value.trim();
    }

    /**
     * Valida se a arquitetura é válida (x86 ou x64).
     */
    public static String requireValidArchitecture(String architecture) {
        requireNonEmpty(architecture, "Arquitetura");

        if (!architecture.equals("x86") && !architecture.equals("x64")) {
            throw new InvalidInputException(
                    "Arquitetura",
                    architecture,
                    "deve ser 'x86' ou 'x64'");
        }
        
        return architecture;
    }

    /**
     * Valida se a memória virtual é maior ou igual à memória física.
     */
    public static void requireVirtualGreaterOrEqualPhysical(int virtualMemory, int physicalMemory) {
        if (virtualMemory < physicalMemory) {
            throw new InvalidConfigurationException(
                    String.format(
                            "Memória virtual (%d bytes) deve ser >= memória física (%d bytes)",
                            virtualMemory, physicalMemory));
        }
    }

    /**
     * Valida se um valor é potência de 2.
     */
    public static void requirePowerOfTwo(int value, String fieldName) {
        if (value <= 0 || (value & (value - 1)) != 0) {
            throw new InvalidConfigurationException(
                    String.format("%s (%d) deve ser uma potência de 2", fieldName, value));
        }
    }

    /**
     * Valida se uma lista não é nula ou vazia.
     */
    public static <T> void requireNonEmptyList(java.util.List<T> list, String fieldName) {
        if (list == null || list.isEmpty()) {
            throw new InvalidInputException(fieldName, null, "não pode ser vazio");
        }
    }
}
