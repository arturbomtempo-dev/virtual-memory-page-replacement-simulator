package app;

import exception.InvalidConfigurationException;
import exception.InvalidInputException;
import exception.SimulatorException;
import model.SystemConfiguration;
import model.PageSequence;
import model.SimulationResult;
import parser.InputParser;
import policy.FIFOPolicy;
import policy.LRUPolicy;
import policy.OPTPolicy;
import policy.RANDPolicy;
import policy.PageReplacementPolicy;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

/**
 * Aplicação principal do simulador de memória virtual.
 * Processa todos os arquivos .txt da pasta input/ e gera saídas na pasta
 * output/
 * aplicando as 4 políticas de substituição: FIFO, RAND, LRU e MIN (OPT).
 */
public class Application {

    private static final String INPUT_DIR = "input";
    private static final String OUTPUT_DIR = "output";

    public static void main(String[] args) {
        createOutputDirectory();

        File inputDir = new File(INPUT_DIR);

        if (!inputDir.exists() || !inputDir.isDirectory()) {
            System.err.println("Erro: Pasta 'input/' não encontrada!");
            System.err.println("Crie a pasta 'input/' e adicione arquivos .txt de entrada.");
            System.exit(1);
        }

        File[] inputFiles = inputDir.listFiles((dir, name) -> name.endsWith(".txt"));

        if (inputFiles == null || inputFiles.length == 0) {
            System.err.println("Erro: Nenhum arquivo .txt encontrado na pasta 'input/'");
            System.exit(1);
        }

        for (File inputFile : inputFiles) {
            processFile(inputFile);
        }

    }

    private static void createOutputDirectory() {
        File outputDir = new File(OUTPUT_DIR);

        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }
    }

    private static void processFile(File inputFile) {
        String inputFileName = inputFile.getName();
        String outputFileName = inputFileName.replace(".txt", "_output.txt");
        File outputFile = new File(OUTPUT_DIR, outputFileName);

        try (Scanner sc = new Scanner(new FileInputStream(inputFile));
                PrintWriter writer = new PrintWriter(outputFile)) {

            InputParser parser = new InputParser(sc);

            SystemConfiguration config = parser.readConfiguration();

            List<PageSequence> sequences = parser.readSequences(config);

            writer.println(config.getPageSize());
            writer.println(config.getNumberOfFrames());
            writer.println(config.getSwapSize());
            writer.println();

            for (int i = 0; i < sequences.size(); i++) {
                PageSequence sequence = sequences.get(i);

                // Adiciona linha em branco antes da segunda sequência em diante
                if (i > 0) {
                    writer.println();
                }

                writer.println(i + 1);
                writer.println();
                writer.println(sequence);

                // Executa todas as políticas na ordem: FIFO, RAND, LRU, MIN
                PageReplacementPolicy[] policies = {
                        new FIFOPolicy(),
                        new RANDPolicy(),
                        new LRUPolicy(),
                        new OPTPolicy()
                };

                for (PageReplacementPolicy policy : policies) {
                    SimulationResult result = policy.simulate(sequence, config.getNumberOfFrames());

                    writer.println(result.getPolicyName());
                    writer.println(result.getExecutionTimeSeconds());
                    writer.println(result.getPageFaults());
                    writer.println(result.getSwapStateFormatted());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado - " + inputFileName);
        } catch (InvalidInputException e) {
            System.err.println("Erro na entrada (" + inputFileName + "): " + e.getMessage());
        } catch (InvalidConfigurationException e) {
            System.err.println("Erro na configuração (" + inputFileName + "): " + e.getMessage());
        } catch (SimulatorException e) {
            System.err.println("Erro no simulador (" + inputFileName + "): " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado (" + inputFileName + "): " + e.getMessage());
            e.printStackTrace();
        }
    }
}
