package com.njxm.smart.utils;

import java.util.Date;

/**
 * @author huangxin
 * @date 2021/8/15
 */
public class DateRange extends DateSuperRange {
    protected Date start;
    private Date end;

    public DateRange(Date start, Date end) {
        super(start);
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    boolean includes(Date arg) {
        return this.start.equals(arg) || end.equals(arg) || arg.after(this.start) && arg.before(end);
    }
}
