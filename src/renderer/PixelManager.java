package renderer;

/**
 * PixelManager is a helper class. It is used for multi-threading in the
 * renderer and
 * for follow up its progress.<br/>
 * A Camera uses one pixel manager object and several Pixel objects - one in
 * each thread.
 *
 * @author Dan Zilberstein
 */
class PixelManager {
    /**
     * Immutable class for object containing allocated pixel (with its row and column numbers)
     *
     * @param col the column number of the pixel
     * @param row the row number of the pixel
     */
    record Pixel(int col, int row) {
    }

    /**
     * Maximum rows of pixels
     */
    private int maxRows = 0;
    /**
     * Maximum columns of pixels
     */
    private int maxCols = 0;

    /**
     * Currently processed row of pixels
     */
    private volatile int cRow = 0;
    /**
     * Currently processed column of pixels
     */
    private volatile int cCol = -1;

    /**
     * Mutual exclusion object for synchronizing next pixel allocation between
     * threads
     */
    private final Object mutexNext = new Object();

    /**
     * Initialize pixel manager data for multi-threading
     *
     * @param maxRows the amount of pixel rows
     * @param maxCols the amount of pixel columns
     */
    PixelManager(int maxRows, int maxCols) {
        this.maxRows = maxRows;
        this.maxCols = maxCols;
    }

    /**
     * Function for thread-safe manipulating of main follow-up Pixel object - this
     * function is critical section for all the threads, and the pixel manager data
     * is the shared data of this critical section.<br/>
     * The function provides next available pixel number each call.
     *
     * @return the next pixel to be processed, or null if all pixels have been allocated.
     */
    Pixel nextPixel() {
        synchronized (mutexNext) {
            if (cRow == maxRows) return null;

            ++cCol;
            if (cCol < maxCols)
                return new Pixel(cRow, cCol);

            cCol = 0;
            ++cRow;
            if (cRow < maxRows)
                return new Pixel(cRow, cCol);
        }
        return null;
    }
}
