package policy;

import model.MemoryFrame;
import model.PageSequence;
import model.SimulationResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Política MIN/OPT (Ótima) de substituição de páginas - Algoritmo de Belady.
 * Remove a página que não será utilizada pelo maior período de tempo no futuro.
 * Requer conhecimento antecipado da sequência completa (não implementável na
 * prática).
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

    private boolean isPageInMemory(int pageIndex) {
        for (MemoryFrame frame : frames) {
            if (frame.getPageIndex() == pageIndex) {
                return true;
            }
        }

        return false;
    }

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

    // Retorna Integer.MAX_VALUE quando a página nunca mais será usada (garante que
    // seja escolhida como vítima)
    private int findNextUse(int pageIndex, List<Integer> allRequests, int startIndex) {
        for (int i = startIndex; i < allRequests.size(); i++) {
            if (allRequests.get(i) == pageIndex) {
                return i;
            }
        }

        return Integer.MAX_VALUE;
    }

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
