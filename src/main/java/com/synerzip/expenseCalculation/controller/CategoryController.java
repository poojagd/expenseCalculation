package com.synerzip.expenseCalculation.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.synerzip.expenseCalculation.service.CategoryService;

@RestController
@RequestMapping(path = "/category/names")
public class CategoryController {

  @Autowired
  CategoryService categoryService;

  @GetMapping
  public List<String> getCategoryNames() {
    return categoryService.getAllCategories();

  }
}
