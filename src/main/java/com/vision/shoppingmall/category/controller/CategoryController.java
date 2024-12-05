package com.vision.shoppingmall.category.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
public class CategoryController {

  @GetMapping("")
  public String getCategoryList() {
    return "category/list";
  }

  @GetMapping("/new-category")
  public String createCategoryForm(Model model) {
    model.addAttribute("modalTitle", "카테고리 등록하기");
    return "category/category-form";
  }

}
