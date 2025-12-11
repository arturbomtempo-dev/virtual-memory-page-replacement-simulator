package model;

/**
 * Representa um frame na memória física.
 * Armazena o índice da página carregada e metadados (timestamps) utilizados
 * pelas políticas de substituição, especialmente LRU.
 */
public class MemoryFrame {

    private int pageIndex;
    private long lastAccessTime; // Timestamp do último acesso (LRU)
    private long loadTime; // Timestamp de quando a página foi carregada

    public MemoryFrame(int pageIndex, long currentTime) {
        this.pageIndex = pageIndex;
        this.lastAccessTime = currentTime;
        this.loadTime = currentTime;
    }

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
