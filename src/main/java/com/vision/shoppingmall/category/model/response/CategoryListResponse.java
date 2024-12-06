package com.vision.shoppingmall.category.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CategoryListResponse {
  private Long id;
  private String categoryName;
}
