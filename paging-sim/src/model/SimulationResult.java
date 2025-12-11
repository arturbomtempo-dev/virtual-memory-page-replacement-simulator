package model;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * Representa o resultado de uma simulação de política de substituição.
 * Contém métricas de desempenho (page faults, tempo) e estado final do swap.
 * Utiliza TreeSet para manter o swap ordenado na saída.
 */
public class SimulationResult {

    private final String policyName;
    private final long executionTimeSeconds;
    private final int pageFaults;
    private final Set<Integer> swapState; // TreeSet mantém ordenação automática

    public SimulationResult(String policyName, long executionTimeSeconds,
            int pageFaults, Set<Integer> swapState) {
        this.policyName = policyName;
        this.executionTimeSeconds = executionTimeSeconds;
        this.pageFaults = pageFaults;
        this.swapState = new TreeSet<>(swapState);
    }

    public String getPolicyName() {
        return policyName;
    }

    public long getExecutionTimeSeconds() {
        return executionTimeSeconds;
    }

    public int getPageFaults() {
        return pageFaults;
    }

    public Set<Integer> getSwapState() {
        return Collections.unmodifiableSet(swapState);
    }

    public String getSwapStateFormatted() {
        if (swapState.isEmpty()) {
            return "0";
        }

        StringBuilder sb = new StringBuilder();
        for (int page : swapState) {
            if (sb.length() > 0) {
                sb.append(" ");
            }

            sb.append(page);
        }

        return sb.toString();
    }
}
