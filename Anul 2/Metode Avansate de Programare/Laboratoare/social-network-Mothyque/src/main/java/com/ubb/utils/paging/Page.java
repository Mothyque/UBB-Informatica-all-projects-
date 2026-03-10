package com.ubb.utils.paging;

public class Page<E>
{
    private Iterable<E> elementsOnPage;
    private int totalElements;

    public Page(Iterable<E> elementsOnPage, int totalElements)
    {
        this.elementsOnPage = elementsOnPage;
        this.totalElements = totalElements;
    }

    public Iterable<E> getElementsOnPage()
    {
        return elementsOnPage;
    }

    public int getTotalElements()
    {
        return totalElements;
    }
}
