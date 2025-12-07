package policy;

import model.PageSequence;
import model.SimulationResult;

/**
 * Interface que define o contrato para políticas de substituição de páginas.
 * Cada política deve implementar esta interface de forma independente.
 */
public interface PageReplacementPolicy {
    
    /**
     * Executa a simulação para uma sequência de requisições de páginas.
     * 
     * @param sequence Sequência de requisições de páginas
     * @param numberOfFrames Número de frames disponíveis na memória física
     * @return Resultado da simulação com page faults, tempo e estado do swap
     */
    SimulationResult simulate(PageSequence sequence, int numberOfFrames);
    
    /**
     * Retorna o nome da política.
     */
    String getPolicyName();
    
    /**
     * Reinicia o estado interno da política.
     * Deve ser chamado antes de cada nova simulação.
     */
    void reset();
}
