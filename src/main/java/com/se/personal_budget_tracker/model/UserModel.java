package com.se.personal_budget_tracker.model;

import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class UserModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "username", nullable = false, unique = true)
  private String username;  

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Transient
  private String password; 

  @JsonIgnore
  @Column(name = "password_hash",nullable = false)
  private String passwordHash;

  @Column(name = "balance", nullable = false)
  @Builder.Default
  private long balance = 0;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  @JsonManagedReference
  @Builder.Default
  private List<IncomeModel> incomes = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  @JsonManagedReference
  @Builder.Default
  private List<ExpenseModel> expenses = new ArrayList<>();
}



