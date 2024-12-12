package com.vision.shoppingmall.product.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException {
  public ProductNotFoundException() {
    super("해당 제품을 찾을 수 없습니다.");
  }
}
