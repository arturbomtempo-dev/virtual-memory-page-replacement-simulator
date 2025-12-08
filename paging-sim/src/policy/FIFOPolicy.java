package policy;

import model.PageSequence;
import model.SimulationResult;
import java.util.*;

/**
 * Implementação da política FIFO (First-In, First-Out) de substituição de
 * páginas.
 * Substitui a página que reside na memória há mais tempo (primeira a entrar).
 */
public class FIFOPolicy implements PageReplacementPolicy {

    private Queue<Integer> frameQueue;
    private Set<Integer> pagesInMemory;
    private Set<Integer> swapState;
    private int pageFaults;

    public FIFOPolicy() {
        reset();
    }

    @Override
    public void reset() {
        frameQueue = new LinkedList<>();
        pagesInMemory = new HashSet<>();
        swapState = new TreeSet<>();
        pageFaults = 0;
    }

    @Override
    public SimulationResult simulate(PageSequence sequence, int numberOfFrames) {
        reset();
        long startTime = System.nanoTime();

        List<Integer> requests = sequence.getRequests();

        for (int pageIndex : requests) {
            if (!pagesInMemory.contains(pageIndex)) {
                pageFaults++;

                swapState.remove(pageIndex);

                if (pagesInMemory.size() < numberOfFrames) {
                    pagesInMemory.add(pageIndex);
                    frameQueue.add(pageIndex);
                } else {
                    int pageToReplace = frameQueue.poll();
                    pagesInMemory.remove(pageToReplace);
                    swapState.add(pageToReplace);

                    pagesInMemory.add(pageIndex);
                    frameQueue.add(pageIndex);
                }
            }
        }

        long endTime = System.nanoTime();
        long executionTimeSeconds = Math.round((endTime - startTime) / 1_000_000_000.0);

        return new SimulationResult(getPolicyName(), executionTimeSeconds, pageFaults,
                new HashSet<>(swapState));
    }

    @Override
    public String getPolicyName() {
        return "FIFO";
    }
}
