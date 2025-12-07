package app;

import model.SystemConfiguration;
import model.PageSequence;
import parser.InputParser;
import java.util.List;
import java.util.Scanner;

/**
 * Classe principal do simulador de memória virtual.
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
                System.out.println("FIFO");
            }

            sc.close();
        } catch (IllegalArgumentException e) {
            System.err.println("Erro na entrada: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
