package com.ibm.ph.edm.paging;

import com.google.common.collect.Lists;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.Iterator;
import java.util.List;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

public class PageInfo<T> {

    private boolean hasNextPage;

    private long totalElements;

    private int currentPage;

    private int pageSize;

    private List<T> results;

    private List<SortInfo> sort;

    /**
     * Creates a PageInfo object.
     *
     * @param paged The Page source
     * @param list  The converted result list
     * @param <Y>   ?
     * @param <Z>   ?
     * @return ?
     */
    public static <Y, Z> PageInfo<Z> newPageInfo(Page<Y> paged, List<Z> list) {

        return new PageInfo<Z>().setResults(list)
                .setCurrentPage(paged.getNumber())
                .setTotalElements(paged.getTotalElements())
                .setHasNextPage(paged.hasNext())
                .setPageSize(paged.getSize())
                .setSort(buildSortInfo(paged.getSort()));
    }

    private static List<SortInfo> buildSortInfo(Sort sort) {

        if (sort == null) {
            return null;
        }

        List<SortInfo> sortInfoList = Lists.newArrayList();
        Iterator<Sort.Order> iterator = sort.iterator();
        while(iterator.hasNext()) {
            Sort.Order order = iterator.next();
            sortInfoList.add(new SortInfo()
                    .setField(order.getProperty())
                    .setDirection(order.getDirection().toString().toLowerCase()));
        }

        return sortInfoList;
    }

    public static <Z> PageInfo<Z> newPageInfo(List<Z> list) {

        return new PageInfo<Z>().setResults(list)
                .setCurrentPage(0)
                .setTotalElements(list.size())
                .setHasNextPage(false)
                .setPageSize(list.size());
    }

    public long getCurrentPage() {

        return currentPage;
    }

    public PageInfo<T> setCurrentPage(int currentPage) {

        this.currentPage = currentPage;
        return this;
    }

    public int getPageSize() {

        return pageSize;
    }

    public PageInfo<T> setPageSize(int pageSize) {

        this.pageSize = pageSize;
        return this;
    }

    public boolean isHasNextPage() {

        return hasNextPage;
    }

    public PageInfo<T> setHasNextPage(boolean hasNextPage) {

        this.hasNextPage = hasNextPage;
        return this;
    }

    public long getTotalElements() {

        return totalElements;
    }

    public PageInfo<T> setTotalElements(long totalElements) {

        this.totalElements = totalElements;
        return this;
    }

    public List<T> getResults() {

        return results;
    }

    public PageInfo<T> setResults(List<T> results) {

        this.results = results;
        return this;
    }

    public List<SortInfo> getSort() {

        return sort;
    }

    public PageInfo<T> setSort(List<SortInfo> sort) {

        this.sort = sort;
        return this;
    }
}