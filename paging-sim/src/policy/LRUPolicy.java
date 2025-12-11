package policy;

import model.MemoryFrame;
import model.PageSequence;
import model.SimulationResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Política LRU (Least Recently Used) de substituição de páginas.
 * Remove a página cujo último acesso ocorreu no tempo mais distante.
 * Atualiza timestamp tanto em hits quanto em misses para refletir uso real.
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

    private MemoryFrame findFrameByPage(int pageIndex) {
        for (MemoryFrame frame : frames) {
            if (frame.getPageIndex() == pageIndex) {
                return frame;
            }
        }
        return null;
    }

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
