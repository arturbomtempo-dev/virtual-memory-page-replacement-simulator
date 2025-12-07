package model;

import validation.InputValidator;

/**
 * Representa as configurações do sistema de memória virtual.
 * Armazena os parâmetros de entrada e calcula valores derivados.
 */
public class SystemConfiguration {

    private final int physicalMemorySize;
    private final int virtualMemorySize;
    private final String architecture;
    private final int numberOfPages;

    private final int pageSize;
    private final int numberOfFrames;
    private final int swapSize;

    /**
     * Construtor que calcula automaticamente os parâmetros derivados.
     * 
     * @param physicalMemorySize Tamanho da memória física em bytes
     * @param virtualMemorySize  Tamanho da memória virtual em bytes
     * @param architecture       Arquitetura do sistema (x86 ou x64)
     * @param numberOfPages      Número total de páginas virtuais
     * @throws IllegalArgumentException se os parâmetros forem inválidos
     */
    public SystemConfiguration(int physicalMemorySize, int virtualMemorySize,
            String architecture, int numberOfPages) {
        validateInputs(physicalMemorySize, virtualMemorySize, architecture, numberOfPages);

        this.physicalMemorySize = physicalMemorySize;
        this.virtualMemorySize = virtualMemorySize;
        this.architecture = architecture;
        this.numberOfPages = numberOfPages;

        this.pageSize = calculatePageSize();
        this.numberOfFrames = physicalMemorySize / pageSize;
        this.swapSize = (numberOfPages - numberOfFrames) * pageSize;
    }

    /**
     * Valida os parâmetros de entrada.
     */
    private void validateInputs(int physicalMemorySize, int virtualMemorySize,
            String architecture, int numberOfPages) {
        InputValidator.requirePositive(physicalMemorySize, "Tamanho da memória física");
        InputValidator.requirePositive(virtualMemorySize, "Tamanho da memória virtual");
        InputValidator.requireValidArchitecture(architecture);
        InputValidator.requirePositive(numberOfPages, "Número de páginas");
        InputValidator.requireVirtualGreaterOrEqualPhysical(virtualMemorySize, physicalMemorySize);
    }

    /**
     * Calcula o tamanho da página (deve ser potência de 2).
     * SP = V / P
     */
    private int calculatePageSize() {
        int size = virtualMemorySize / numberOfPages;
        InputValidator.requirePowerOfTwo(size, "Tamanho da página");
        return size;
    }

    public int getPhysicalMemorySize() {
        return physicalMemorySize;
    }

    public int getVirtualMemorySize() {
        return virtualMemorySize;
    }

    public String getArchitecture() {
        return architecture;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getNumberOfFrames() {
        return numberOfFrames;
    }

    public int getSwapSize() {
        return swapSize;
    }
}
