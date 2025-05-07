package com.se.income.dto;

public class SummaryDTO {
  long thisWeek;
  long thisMonth;
  long thisYear;

  long lastWeek;
  long lastMonth;
  long lastYear;

  public SummaryDTO(long thisWeek, long thisMonth, long thisYear, long lastWeek, long lastMonth, long lastYear) {
    this.thisWeek = thisWeek;
    this.thisMonth = thisMonth;
    this.thisYear = thisYear;
    this.lastWeek = lastWeek;
    this.lastMonth = lastMonth;
    this.lastYear = lastYear;
  }

  public long getThisWeek() {
    return thisWeek;
  }

  public void setThisWeek(long thisWeek) {
    this.thisWeek = thisWeek;
  }

  public long getThisMonth() {
    return thisMonth;
  }

  public void setThisMonth(long thisMonth) {
    this.thisMonth = thisMonth;
  }

  public long getThisYear() {
    return thisYear;
  }

  public void setThisYear(long thisYear) {
    this.thisYear = thisYear;
  }

  public long getLastWeek() {
    return lastWeek;
  }

  public void setLastWeek(long lastWeek) {
    this.lastWeek = lastWeek;
  }

  public long getLastMonth() {
    return lastMonth;
  }

  public void setLastMonth(long lastMonth) {
    this.lastMonth = lastMonth;
  }

  public long getLastYear() {
    return lastYear;
  }

  public void setLastYear(long lastYear) {
    this.lastYear = lastYear;
  }

  @Override
  public String toString() {
    return "SummaryDTO [thisWeek=" + thisWeek + ", thisMonth=" + thisMonth + ", thisYear=" + thisYear + ", lastWeek="
        + lastWeek + ", lastMonth=" + lastMonth + ", lastYear=" + lastYear + "]";
  }

}
