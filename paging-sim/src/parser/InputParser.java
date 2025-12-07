package parser;

import model.SystemConfiguration;
import model.PageSequence;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Parser responsável por ler e validar a entrada padrão (stdin).
 * Processa configurações do sistema e sequências de requisições.
 */
public class InputParser {

    private final Scanner scanner;

    public InputParser(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Lê as configurações do sistema da entrada padrão.
     * 
     * @return Objeto SystemConfiguration com os parâmetros do sistema
     * @throws IllegalArgumentException se a entrada for inválida
     */
    public SystemConfiguration readConfiguration() {
        try {
            int physicalMemorySize = readPositiveInt("Tamanho da memória física");
            int virtualMemorySize = readPositiveInt("Tamanho da memória virtual");
            String architecture = readArchitecture();
            int numberOfPages = readPositiveInt("Número de páginas");

            return new SystemConfiguration(physicalMemorySize, virtualMemorySize,
                    architecture, numberOfPages);
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao ler configurações: " + e.getMessage(), e);
        }
    }

    /**
     * Lê todas as sequências de requisições da entrada padrão.
     * 
     * @param config Configuração do sistema para validação
     * @return Lista de sequências de páginas
     * @throws IllegalArgumentException se a entrada for inválida
     */
    public List<PageSequence> readSequences(SystemConfiguration config) {
        try {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            int numberOfSequences = readPositiveInt("Número de sequências");
            List<PageSequence> sequences = new ArrayList<>();

            for (int i = 0; i < numberOfSequences; i++) {
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }

                PageSequence sequence = readSingleSequence();
                sequence.validate(config.getNumberOfPages());
                sequences.add(sequence);
            }

            return sequences;
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao ler sequências: " + e.getMessage(), e);
        }
    }

    /**
     * Lê uma única sequência de requisições.
     */
    private PageSequence readSingleSequence() {
        int numberOfRequests = readPositiveInt("Número de requisições");

        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        List<Integer> requests = new ArrayList<>();

        String line = scanner.nextLine().trim();
        String[] tokens = line.split("\\s+");

        if (tokens.length != numberOfRequests) {
            throw new IllegalArgumentException(
                    String.format("Esperado %d requisições, encontrado %d",
                            numberOfRequests, tokens.length));
        }

        for (String token : tokens) {
            try {
                int pageIndex = Integer.parseInt(token);
                requests.add(pageIndex);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Índice de página inválido: " + token);
            }
        }

        return new PageSequence(requests);
    }

    /**
     * Lê um inteiro positivo da entrada.
     */
    private int readPositiveInt(String fieldName) {
        if (!scanner.hasNextInt()) {
            throw new IllegalArgumentException(fieldName + " deve ser um número inteiro");
        }
        int value = scanner.nextInt();
        if (value <= 0) {
            throw new IllegalArgumentException(fieldName + " deve ser positivo");
        }
        return value;
    }

    /**
     * Lê e valida a arquitetura do sistema.
     */
    private String readArchitecture() {
        if (!scanner.hasNext()) {
            throw new IllegalArgumentException("Arquitetura não encontrada");
        }
        String arch = scanner.next().trim();
        if (!arch.equals("x86") && !arch.equals("x64")) {
            throw new IllegalArgumentException("Arquitetura deve ser x86 ou x64");
        }
        return arch;
    }
}
