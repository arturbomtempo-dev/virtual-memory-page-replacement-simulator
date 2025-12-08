package policy;

import model.PageSequence;
import model.SimulationResult;
import java.util.*;

/**
 * Implementação da política RAND (Random) de substituição de páginas.
 * Substitui um frame escolhido aleatoriamente da memória física.
 */
public class RANDPageReplacementPolicy implements PageReplacementPolicy {

    private Set<Integer> pagesInMemory;
    private Set<Integer> swapState;
    private int pageFaults;
    private Random random;

    public RANDPageReplacementPolicy() {
        reset();
    }

    @Override
    public void reset() {
        pagesInMemory = new HashSet<>();
        swapState = new TreeSet<>();
        pageFaults = 0;
        random = new Random();
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
                } else {
                    int pageToReplace = selectRandomPage();
                    pagesInMemory.remove(pageToReplace);
                    swapState.add(pageToReplace);

                    pagesInMemory.add(pageIndex);
                }
            }
        }

        long endTime = System.nanoTime();
        long executionTimeSeconds = Math.round((endTime - startTime) / 1_000_000_000.0);

        return new SimulationResult(getPolicyName(), executionTimeSeconds, pageFaults,
                new HashSet<>(swapState));
    }

    /**
     * Seleciona uma página aleatória da memória física.
     * 
     * @return índice da página selecionada aleatoriamente
     */
    private int selectRandomPage() {
        List<Integer> pages = new ArrayList<>(pagesInMemory);
        int randomIndex = random.nextInt(pages.size());
        return pages.get(randomIndex);
    }

    @Override
    public String getPolicyName() {
        return "RAND";
    }
}
