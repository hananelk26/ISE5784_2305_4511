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
     * Total amount of pixels in the generated image
     */
    private long totalPixels = 0;

    /**
     * Currently processed row of pixels
     */
    private volatile int cRow = 0;
    /**
     * Currently processed column of pixels
     */
    private volatile int cCol = -1;
    /**
     * Amount of pixels that have been processed
     */
    private volatile long pixels = 0;
    /**
     * Last printed progress update percentage
     */
    private volatile int lastPrinted = 0;

    /**
     * Flag of debug printing of progress percentage
     */
    private boolean print = false;

    /**
     * Printing format
     */
    private static final String PRINT_FORMAT = "%5.1f%%\r";
    /**
     * Mutual exclusion object for synchronizing next pixel allocation between
     * threads
     */
    private final Object mutexNext = new Object();
    /**
     * Mutual exclusion object for printing progress percentage in console window
     * by different threads
     */
    private final Object mutexPixels = new Object();

    /**
     * Initialize pixel manager data for multi-threading
     *
     * @param maxRows  the amount of pixel rows
     * @param maxCols  the amount of pixel columns
     */
    PixelManager(int maxRows, int maxCols) {
        this.maxRows = maxRows;
        this.maxCols = maxCols;
        totalPixels = (long) maxRows * maxCols;


    }

    /**
     * Function for thread-safe manipulating of main follow-up Pixel object - this
     * function is critical section for all the threads, and the pixel manager data
     * is the shared data of this critical section.<br/>
     * The function provides next available pixel number each call.
     *
     * @return the next pixel if next pixel is allocated, null if there are no more pixels
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
