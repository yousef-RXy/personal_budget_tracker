package com.se.expense.dto;

import java.time.LocalDate;

import com.se.expense.model.RepetitionPeriod;

public class EntryDTO {
  private String name;
  private long amount;
  private String category;
  private Long userId;
  private RepetitionPeriod repetitionPeriod;
  private LocalDate date;

  public void setDate(LocalDate date) {
    this.date = date;
  }

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

  public void setRepetitionPeriod(RepetitionPeriod repetitionPeriod) {
    this.repetitionPeriod = repetitionPeriod;
  }

  public RepetitionPeriod getRepetitionPeriod() {
    return this.repetitionPeriod;
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
