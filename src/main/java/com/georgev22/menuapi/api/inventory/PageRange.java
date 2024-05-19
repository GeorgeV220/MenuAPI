package com.georgev22.menuapi.api.inventory;

/**
 * Represents a range of pages.
 * <p>
 * The `PageRange` class encapsulates a start page and an end page. It provides methods to retrieve the start and end pages, as well as check if a given page number falls within the specified range.
 * </p>
 * <p>
 * Example usage:
 * <pre>
 *     PageRange range = new PageRange(5, 10);
 *     int currentPage = 7;
 *     boolean isPageInRange = range.isPageInRange(currentPage); // Returns true
 * </pre>
 * </p>
 */
@SuppressWarnings("ALL")
public class PageRange {
    private final int startPage;
    private final int endPage;

    /**
     * Constructs a `PageRange` with the specified start and end pages.
     *
     * @param startPage The start page of the range (inclusive).
     * @param endPage   The end page of the range (inclusive). Use -1 for an open-ended range.
     */
    public PageRange(int startPage, int endPage) {
        this.startPage = startPage;
        this.endPage = endPage;
    }

    /**
     * Gets the start page of the range.
     *
     * @return The start page.
     */
    public int getStartPage() {
        return startPage;
    }

    /**
     * Gets the end page of the range.
     *
     * @return The end page.
     */
    public int getEndPage() {
        return endPage;
    }

    /**
     * Checks if a given page number is within the specified range.
     *
     * @param page The page number to check.
     * @return `true` if the page is within the range, otherwise `false`.
     */
    public boolean isPageInRange(int page) {
        if (endPage == -1) {
            return page >= startPage;
        } else {
            return page >= startPage && page <= endPage;
        }
    }
}