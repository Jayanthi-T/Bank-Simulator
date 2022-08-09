package com.batonsystems.banksimulator.entity;

//This SortOrder class is required to get the sort order input from user and display branches as per
// the received sort order.
public class SortOrder {
    private String sortOrder;
    public static final String ASC = "asc";
    public static final String DESC = "desc";

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}
