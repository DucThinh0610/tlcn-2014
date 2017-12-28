package com.tlcn.mvpapplication.mvp.chart.dto;

public class LineItem {
    int count;
    double level;
    String part;
    int type;

    LineItem(String part, int type) {
        count = 0;
        level = 0;
        this.part = part;
        this.type = type;
    }

    LineItem() {
        count = 0;
        level = 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        } else {
            LineItem item = (LineItem) obj;
            return item.type == this.type;
        }
    }
}
