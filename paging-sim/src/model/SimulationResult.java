package model;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * Representa o resultado de uma simulação de política de substituição.
 * Contém métricas de desempenho e estado final.
 */
public class SimulationResult {
    
    private final String policyName;
    private final long executionTimeSeconds;
    private final int pageFaults;
    private final Set<Integer> swapState;
    
    /**
     * Construtor do resultado da simulação.
     * 
     * @param policyName Nome da política executada
     * @param executionTimeSeconds Tempo de execução em segundos
     * @param pageFaults Número de page faults ocorridos
     * @param swapState Conjunto de páginas no swap ao final
     */
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
    
    /**
     * Retorna o estado do swap (somente leitura, ordenado).
     */
    public Set<Integer> getSwapState() {
        return Collections.unmodifiableSet(swapState);
    }
    
    /**
     * Retorna representação formatada do estado do swap.
     * Se vazio, retorna "0". Caso contrário, retorna páginas separadas por espaço.
     */
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
