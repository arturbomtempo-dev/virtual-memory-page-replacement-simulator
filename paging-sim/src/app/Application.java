package app;

import exception.InvalidConfigurationException;
import exception.InvalidInputException;
import exception.SimulatorException;
import model.SystemConfiguration;
import model.PageSequence;
import parser.InputParser;
import java.util.List;
import java.util.Scanner;

/**
 * Processa entrada padrão e executa simulações de políticas de substituição.
 */
public class Application {

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            InputParser parser = new InputParser(sc);

            SystemConfiguration config = parser.readConfiguration();

            List<PageSequence> sequences = parser.readSequences(config);

            System.out.println(config.getPageSize());
            System.out.println(config.getNumberOfFrames());
            System.out.println(config.getSwapSize());
            System.out.println();

            System.out.println(sequences.size());
            System.out.println();

            for (PageSequence sequence : sequences) {
                System.out.println(sequence);
                // TODO: Implementar simulação das políticas (Tarefa 2+)
            }

            sc.close();

        } catch (InvalidInputException e) {
            System.err.println("Erro na entrada: " + e.getMessage());
            System.exit(1);
        } catch (InvalidConfigurationException e) {
            System.err.println("Erro na configuração: " + e.getMessage());
            System.exit(1);
        } catch (SimulatorException e) {
            System.err.println("Erro no simulador: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
