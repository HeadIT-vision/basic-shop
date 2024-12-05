package com.vision.shoppingmall.category.controller;

import com.vision.shoppingmall.category.model.exception.CategoryNameDuplicationException;
import com.vision.shoppingmall.category.model.request.CreateCategoryRequest;
import com.vision.shoppingmall.category.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;

  @GetMapping("")
  public String getCategoryList() {
    return "category/list";
  }

  @GetMapping("/new-category")
  public String createCategoryForm(Model model) {
    model.addAttribute("modalTitle", "카테고리 등록하기");
    return "category/category-form";
  }

  @PostMapping("/new-category")
  public String createCategory(
    @ModelAttribute @Valid CreateCategoryRequest request,
    BindingResult bindingResult)
  {
    if (bindingResult.hasErrors())
      throw new IllegalArgumentException(
          bindingResult.getAllErrors().getFirst().getDefaultMessage()
      );

    categoryService.createCategory(request);
    return "redirect:/categories";
  }

}
