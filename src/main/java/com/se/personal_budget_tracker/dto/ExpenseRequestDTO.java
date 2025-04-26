package com.se.personal_budget_tracker.dto;

import java.time.LocalDate;

import com.se.personal_budget_tracker.model.RepitionPeriod;

public class ExpenseRequestDTO {
  private String name;
  private long amount;
  private String category;
  private boolean isRepetitive;
  private Long userId;
  LocalDate date;
  public void setDate(LocalDate date) {
    this.date = date;
  }
  private RepitionPeriod repitionPeriod;
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getAmount() {
    return amount;
  }

  public void setAmount(long amount) {
    this.amount = amount;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public boolean isRepetitive() {
    return isRepetitive;
  }

  public void setRepetitive(boolean isRepetitive) {
    this.isRepetitive = isRepetitive;
  }
   public void setRepitionPeriod(RepitionPeriod repitionPeriod){
      this.repitionPeriod = repitionPeriod;
    }
    public RepitionPeriod  getRepitionPeriod(){
      return this.repitionPeriod ;
    }
  public Long getUserId() {
    return userId;
  }
      public LocalDate getDate() {
      return date;
  }
  public void setUserId(Long userId) {
    this.userId = userId;
  }

}
