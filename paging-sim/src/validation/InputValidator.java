package validation;

import exception.InvalidInputException;
import exception.InvalidConfigurationException;

/**
 * Classe utilitária para validação de entrada.
 * Centraliza todas as regras de validação e tratamento de erros,
 * garantindo consistência nas mensagens e fail-fast behavior.
 */
public final class InputValidator {

    private InputValidator() {
    }

    public static int requirePositive(int value, String fieldName) {
        if (value <= 0) {
            throw new InvalidInputException(fieldName, value, "deve ser positivo");
        }

        return value;
    }

    public static int requireInRange(int value, int min, int max, String fieldName) {
        if (value < min || value > max) {
            throw new InvalidInputException(
                    fieldName,
                    value,
                    String.format("deve estar entre %d e %d", min, max));
        }

        return value;
    }

    public static String requireNonEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidInputException(fieldName, value, "não pode ser vazio");
        }

        return value.trim();
    }

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

    public static void requireVirtualGreaterOrEqualPhysical(int virtualMemory, int physicalMemory) {
        if (virtualMemory < physicalMemory) {
            throw new InvalidConfigurationException(
                    String.format(
                            "Memória virtual (%d bytes) deve ser >= memória física (%d bytes)",
                            virtualMemory, physicalMemory));
        }
    }

    public static void requirePowerOfTwo(int value, String fieldName) {
        if (value <= 0 || (value & (value - 1)) != 0) {
            throw new InvalidConfigurationException(
                    String.format("%s (%d) deve ser uma potência de 2", fieldName, value));
        }
    }

    public static <T> void requireNonEmptyList(java.util.List<T> list, String fieldName) {
        if (list == null || list.isEmpty()) {
            throw new InvalidInputException(fieldName, null, "não pode ser vazio");
        }
    }
}
