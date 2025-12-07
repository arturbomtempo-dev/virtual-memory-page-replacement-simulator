package model;

/**
 * Representa um frame na memória física.
 * Armazena o índice da página carregada e metadados para as políticas.
 */
public class MemoryFrame {

    private int pageIndex;
    private long lastAccessTime;
    private long loadTime;

    /**
     * Construtor de um frame de memória.
     * 
     * @param pageIndex   Índice da página carregada
     * @param currentTime Timestamp atual da simulação
     */
    public MemoryFrame(int pageIndex, long currentTime) {
        this.pageIndex = pageIndex;
        this.lastAccessTime = currentTime;
        this.loadTime = currentTime;
    }

    /**
     * Atualiza o timestamp do último acesso (usado por LRU).
     */
    public void updateAccessTime(long currentTime) {
        this.lastAccessTime = currentTime;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public long getLoadTime() {
        return loadTime;
    }

    @Override
    public String toString() {
        return String.format("Frame[page = %d, lastAccess = %d, loadTime = %d]",
                pageIndex, lastAccessTime, loadTime);
    }
}
