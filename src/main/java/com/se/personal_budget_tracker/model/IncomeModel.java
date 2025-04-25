package com.se.personal_budget_tracker.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "incomes")
public class IncomeModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "amount", nullable = false)
  private long amount = 0;

  @Column(name = "category", nullable = false)
  private String category;

  @Column(name = "date", nullable = false)
  private LocalDate date;

  @Column(name = "isRepetitive", nullable = false)
  private boolean isRepetitive;
  @Column(name = "repitionPeriod",nullable =false)
  private RepitionPeriod repitionPeriod= RepitionPeriod.None;
  @ManyToOne
  @JoinColumn(name = "user_id")
  @JsonBackReference
  private UserModel user;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }
  public void setRepitionPeriod(RepitionPeriod repitionPeriod){
    this.repitionPeriod = repitionPeriod;
  }
  public RepitionPeriod  getRepitionPeriod(){
    return this.repitionPeriod ;
  }
  public boolean isRepetitive() {
    return isRepetitive;
  }

  public void setRepetitive(boolean isRepetitive) {
    this.isRepetitive = isRepetitive;
  }

  public UserModel getUser() {
    return user;
  }

  public void setUser(UserModel user) {
    this.user = user;
  }
}
