package com.vision.shoppingmall.category.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CategoryHasProductsException extends RuntimeException {
  public CategoryHasProductsException(){
    super("해당 카테고리에 상품이 존재합니다.");
  }
}
