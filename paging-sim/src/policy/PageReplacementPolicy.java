package policy;

import model.PageSequence;
import model.SimulationResult;

/**
 * Interface que define o contrato para políticas de substituição de páginas.
 * Implementa o Strategy Pattern, permitindo trocar algoritmos dinamicamente.
 */
public interface PageReplacementPolicy {

    SimulationResult simulate(PageSequence sequence, int numberOfFrames);

    String getPolicyName();

    void reset();
}
