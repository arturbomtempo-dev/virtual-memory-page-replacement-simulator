package parser;

import exception.InvalidInputException;
import model.SystemConfiguration;
import model.PageSequence;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Parser responsável por ler e validar a entrada padrão (stdin).
 * Processa configurações do sistema (memória, arquitetura, páginas) e
 * sequências de requisições conforme especificação do projeto.
 */
public class InputParser {

    private final Scanner scanner;

    public InputParser(Scanner scanner) {
        this.scanner = scanner;
    }

    public SystemConfiguration readConfiguration() {
        try {
            int physicalMemorySize = readPositiveInt("Tamanho da memória física");
            int virtualMemorySize = readPositiveInt("Tamanho da memória virtual");
            String architecture = readArchitecture();
            int numberOfPages = readPositiveInt("Número de páginas");

            return new SystemConfiguration(physicalMemorySize, virtualMemorySize,
                    architecture, numberOfPages);
        } catch (Exception e) {
            throw new InvalidInputException("Erro ao ler configurações: " + e.getMessage());
        }
    }

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
            throw new InvalidInputException("Erro ao ler sequências: " + e.getMessage());
        }
    }

    private PageSequence readSingleSequence() {
        int numberOfRequests = readPositiveInt("Número de requisições");

        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        List<Integer> requests = new ArrayList<>();

        String line = scanner.nextLine().trim();
        String[] tokens = line.split("\\s+");

        if (tokens.length != numberOfRequests) {
            throw new InvalidInputException(
                    String.format("Esperado %d requisições, encontrado %d",
                            numberOfRequests, tokens.length));
        }

        for (String token : tokens) {
            try {
                int pageIndex = Integer.parseInt(token);
                requests.add(pageIndex);
            } catch (NumberFormatException e) {
                throw new InvalidInputException("Índice de página", token, "deve ser um número inteiro");
            }
        }

        return new PageSequence(requests);
    }

    private int readPositiveInt(String fieldName) {
        if (!scanner.hasNextInt()) {
            throw new InvalidInputException(fieldName, "<não numérico>", "deve ser um número inteiro");
        }
        int value = scanner.nextInt();
        if (value <= 0) {
            throw new InvalidInputException(fieldName, value, "deve ser positivo");
        }
        return value;
    }

    private String readArchitecture() {
        if (!scanner.hasNext()) {
            throw new InvalidInputException("Arquitetura não encontrada");
        }
        String arch = scanner.next().trim();
        if (!arch.equals("x86") && !arch.equals("x64")) {
            throw new InvalidInputException("Arquitetura", arch, "deve ser 'x86' ou 'x64'");
        }
        return arch;
    }
}
