package me.trouper.yessirbox.utils;

import java.time.Duration;
import java.time.LocalDateTime;

public record TimeStamp(int year, int month, int day, int hour, int minute, int second) {
    public TimeStamp(LocalDateTime now) {
        this(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), now.getHour(),now.getMinute(),now.getSecond());
    }

    public LocalDateTime toLocalDate() {
        return LocalDateTime.of(year,month,day,hour,minute,second);
    }
    public long secondsBetween(TimeStamp to) {
        return Duration.between(this.toLocalDate(),to.toLocalDate()).getSeconds();
    }
    public static TimeStamp now() {
        return new TimeStamp(LocalDateTime.now());
    }
}
