package com.se.expense.dto;

public class CategoryTotalDTO {
  private String category;
  private Long total;

  public CategoryTotalDTO(String category, Long total) {
    this.category = category;
    this.total = total;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public Long getTotal() {
    return total;
  }

  public void setTotal(Long total) {
    this.total = total;
  }

}
