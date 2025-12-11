package model;

import validation.InputValidator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa uma sequência de requisições de páginas.
 * Valida que todos os índices estão no intervalo [0, maxPages-1].
 */
public class PageSequence {

    private final List<Integer> requests;

    public PageSequence(List<Integer> requests) {
        InputValidator.requireNonEmptyList(requests, "Sequência de requisições");
        this.requests = new ArrayList<>(requests);
    }

    public void validate(int maxPages) {
        for (int pageIndex : requests) {
            InputValidator.requireInRange(pageIndex, 0, maxPages - 1, "Índice de página");
        }
    }

    public List<Integer> getRequests() {
        return Collections.unmodifiableList(requests);
    }

    public int size() {
        return requests.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < requests.size(); i++) {
            if (i > 0) {
                sb.append(" ");
            }

            sb.append(requests.get(i));
        }

        return sb.toString();
    }
}
