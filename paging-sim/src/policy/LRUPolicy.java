package policy;

import model.MemoryFrame;
import model.PageSequence;
import model.SimulationResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementação da política LRU (Least Recently Used).
 * Substitui a página cujo último acesso ocorreu no tempo mais distante.
 */
public class LRUPolicy implements PageReplacementPolicy {

    private List<MemoryFrame> frames;
    private int pageFaultCount;

    public LRUPolicy() {
        this.frames = new ArrayList<>();
        this.pageFaultCount = 0;
    }

    @Override
    public String getPolicyName() {
        return "LRU";
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
            processPageRequest(pageIndex, numberOfFrames, i);
        }

        long endTime = System.currentTimeMillis();
        long executionTimeSeconds = Math.round((endTime - startTime) / 1000.0);

        Set<Integer> swapState = calculateSwapState(sequence);

        return new SimulationResult(getPolicyName(), executionTimeSeconds,
                pageFaultCount, swapState);
    }

    /**
     * Processa uma requisição de página.
     * 
     * @param pageIndex      Índice da página requisitada
     * @param numberOfFrames Número total de frames disponíveis
     * @param currentTime    Timestamp atual (índice na sequência)
     */
    private void processPageRequest(int pageIndex, int numberOfFrames, long currentTime) {
        MemoryFrame existingFrame = findFrameByPage(pageIndex);

        if (existingFrame != null) {
            existingFrame.updateAccessTime(currentTime);
            return;
        }

        pageFaultCount++;

        if (frames.size() < numberOfFrames) {
            frames.add(new MemoryFrame(pageIndex, currentTime));
        } else {
            int victimIndex = findLRUVictim();
            frames.set(victimIndex, new MemoryFrame(pageIndex, currentTime));
        }
    }

    /**
     * Encontra o frame que contém a página especificada.
     * 
     * @param pageIndex Índice da página procurada
     * @return Frame contendo a página, ou null se não encontrado
     */
    private MemoryFrame findFrameByPage(int pageIndex) {
        for (MemoryFrame frame : frames) {
            if (frame.getPageIndex() == pageIndex) {
                return frame;
            }
        }
        return null;
    }

    /**
     * Encontra o índice do frame vítima usando a política LRU.
     * Seleciona o frame com o menor lastAccessTime (menos recentemente usado).
     * 
     * @return Índice do frame a ser substituído
     */
    private int findLRUVictim() {
        int victimIndex = 0;
        long oldestAccessTime = frames.get(0).getLastAccessTime();

        for (int i = 1; i < frames.size(); i++) {
            long accessTime = frames.get(i).getLastAccessTime();
            if (accessTime < oldestAccessTime) {
                oldestAccessTime = accessTime;
                victimIndex = i;
            }
        }

        return victimIndex;
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
