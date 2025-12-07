package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa uma sequência de requisições de páginas.
 */
public class PageSequence {

    private final List<Integer> requests;

    /**
     * Construtor que cria uma sequência de páginas.
     * 
     * @param requests Lista de índices de páginas requisitadas
     * @throws IllegalArgumentException se a sequência for inválida
     */
    public PageSequence(List<Integer> requests) {
        if (requests == null || requests.isEmpty()) {
            throw new IllegalArgumentException("Sequência de requisições não pode ser vazia");
        }
        
        this.requests = new ArrayList<>(requests);
    }

    /**
     * Valida se todos os índices de página estão no intervalo válido [0,
     * maxPages-1].
     * 
     * @param maxPages Número máximo de páginas do sistema
     * @throws IllegalArgumentException se algum índice for inválido
     */
    public void validate(int maxPages) {
        for (int pageIndex : requests) {
            if (pageIndex < 0 || pageIndex >= maxPages) {
                throw new IllegalArgumentException(
                        String.format("Índice de página inválido: %d (deve estar entre 0 e %d)",
                                pageIndex, maxPages - 1));
            }
        }
    }

    /**
     * Retorna a lista de requisições (somente leitura).
     */
    public List<Integer> getRequests() {
        return Collections.unmodifiableList(requests);
    }

    /**
     * Retorna o tamanho da sequência.
     */
    public int size() {
        return requests.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < requests.size(); i++) {
            if (i > 0)
                sb.append(" ");
            sb.append(requests.get(i));
        }
        return sb.toString();
    }
}
