package policy;

import model.MemoryFrame;
import model.PageSequence;
import model.SimulationResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementação da política MIN/OPT (Ótima).
 * Substitui a página que não será utilizada pelo período de tempo mais longo no
 * futuro.
 */
public class OPTPolicy implements PageReplacementPolicy {

    private List<MemoryFrame> frames;
    private int pageFaultCount;

    public OPTPolicy() {
        this.frames = new ArrayList<>();
        this.pageFaultCount = 0;
    }

    @Override
    public String getPolicyName() {
        return "MIN";
    }

    @Override
    public void reset() {
        frames.clear();
        pageFaultCount = 0;
    }

    @Override
    public SimulationResult simulate(PageSequence sequence, int numberOfFrames) {
        reset();

        long startTime = System.currentTimeMillis();

        List<Integer> requests = sequence.getRequests();
        for (int i = 0; i < requests.size(); i++) {
            int pageIndex = requests.get(i);
            processPageRequest(pageIndex, numberOfFrames, requests, i);
        }

        long endTime = System.currentTimeMillis();
        long executionTimeSeconds = Math.round((endTime - startTime) / 1000.0);

        Set<Integer> swapState = calculateSwapState(sequence);

        return new SimulationResult(getPolicyName(), executionTimeSeconds,
                pageFaultCount, swapState);
    }

    /**
     * Processa uma requisição de página usando a política ótima.
     * 
     * @param pageIndex      Índice da página requisitada
     * @param numberOfFrames Número total de frames disponíveis
     * @param allRequests    Sequência completa de requisições (para análise do
     *                       futuro)
     * @param currentIndex   Posição atual na sequência
     */
    private void processPageRequest(int pageIndex, int numberOfFrames,
            List<Integer> allRequests, int currentIndex) {
        if (isPageInMemory(pageIndex)) {
            return;
        }

        pageFaultCount++;

        if (frames.size() < numberOfFrames) {
            frames.add(new MemoryFrame(pageIndex, currentIndex));
        } else {
            int victimIndex = findOptimalVictim(allRequests, currentIndex);
            frames.set(victimIndex, new MemoryFrame(pageIndex, currentIndex));
        }
    }

    /**
     * Encontra a página em memória que está no frame vítima.
     * 
     * @param pageIndex Índice da página procurada
     * @return true se a página está em memória, false caso contrário
     */
    private boolean isPageInMemory(int pageIndex) {
        for (MemoryFrame frame : frames) {
            if (frame.getPageIndex() == pageIndex) {
                return true;
            }
        }

        return false;
    }

    /**
     * Encontra o índice do frame vítima usando a política ótima (MIN/OPT).
     * 
     * Algoritmo:
     * 1. Para cada página em memória, encontra quando será usada novamente no
     * futuro
     * 2. Escolhe a página que será usada mais tarde (ou nunca mais)
     * 3. Se múltiplas páginas têm o mesmo uso futuro, escolhe a última (critério de
     * desempate)
     * 
     * @param allRequests  Sequência completa de requisições
     * @param currentIndex Posição atual na sequência
     * @return Índice do frame a ser substituído
     */
    private int findOptimalVictim(List<Integer> allRequests, int currentIndex) {
        int victimIndex = 0;
        int farthestUse = -1;

        for (int i = 0; i < frames.size(); i++) {
            int pageInFrame = frames.get(i).getPageIndex();
            int nextUse = findNextUse(pageInFrame, allRequests, currentIndex + 1);

            if (nextUse >= farthestUse) {
                farthestUse = nextUse;
                victimIndex = i;
            }
        }

        return victimIndex;
    }

    /**
     * Encontra a próxima posição em que a página será usada no futuro.
     * 
     * @param pageIndex   Índice da página a procurar
     * @param allRequests Sequência completa de requisições
     * @param startIndex  Índice para começar a busca (futuro)
     * @return Índice da próxima ocorrência, ou Integer.MAX_VALUE se nunca mais for
     *         usada
     */
    private int findNextUse(int pageIndex, List<Integer> allRequests, int startIndex) {
        for (int i = startIndex; i < allRequests.size(); i++) {
            if (allRequests.get(i) == pageIndex) {
                return i;
            }
        }

        return Integer.MAX_VALUE;
    }

    /**
     * Calcula quais páginas estão no swap (não estão em memória).
     * 
     * @param sequence Sequência de requisições original
     * @return Conjunto de páginas no swap
     */
    private Set<Integer> calculateSwapState(PageSequence sequence) {
        Set<Integer> allPages = new HashSet<>(sequence.getRequests());

        Set<Integer> pagesInMemory = new HashSet<>();

        for (MemoryFrame frame : frames) {
            pagesInMemory.add(frame.getPageIndex());
        }

        allPages.removeAll(pagesInMemory);
        return allPages;
    }
}
