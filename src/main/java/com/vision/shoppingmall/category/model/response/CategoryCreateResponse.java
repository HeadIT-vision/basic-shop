package com.vision.shoppingmall.category.model.response;

import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
public class CategoryCreateResponse {
  private Long categoryId;
  private String categoryName;
}
