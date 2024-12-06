package com.vision.shoppingmall.category.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CategoryNotFoundException extends RuntimeException {
  public CategoryNotFoundException() {
    super("카테고리가 존재하지 않습니다.");
  }
}
