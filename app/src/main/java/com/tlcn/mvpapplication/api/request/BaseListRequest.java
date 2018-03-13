package com.tlcn.mvpapplication.api.request;

/**
 * Created by apple on 3/6/18.
 */

public class BaseListRequest {
    private int page;

    private int limit;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
