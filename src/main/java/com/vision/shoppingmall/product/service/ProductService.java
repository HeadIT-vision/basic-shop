package com.vision.shoppingmall.product.service;

import com.vision.shoppingmall.category.model.entity.Category;
import com.vision.shoppingmall.category.model.exception.CategoryNotFoundException;
import com.vision.shoppingmall.category.repository.CategoryRepository;
import com.vision.shoppingmall.category.service.CategoryService;
import com.vision.shoppingmall.product.model.entity.Product;
import com.vision.shoppingmall.product.model.entity.ProductStatus;
import com.vision.shoppingmall.product.model.exception.ProductNotFoundException;
import com.vision.shoppingmall.product.model.request.CreateProductRequest;
import com.vision.shoppingmall.product.model.request.UpdateProductRequest;
import com.vision.shoppingmall.product.model.response.ProductListResponse;
import com.vision.shoppingmall.product.model.response.ProductResponse;
import com.vision.shoppingmall.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;

  public void createProduct(CreateProductRequest request) {
    // 해당 카테고리가 있는지 확인 및 가져오기
    Category category =
        categoryRepository.findById(request.getCategoryId())
            .orElseThrow(CategoryNotFoundException::new);

    Product product = Product.create(request, category);
    productRepository.save(product);
  }

  public Page<ProductListResponse> getProducts(int page) {
    PageRequest pageReq = PageRequest.of(page, 10);
    Page<Product> products
        = productRepository.findByProductStatus(ProductStatus.ACTIVE, pageReq);

    return products
        .map(ProductListResponse::from);
  }

  public ProductResponse getProductById(Long id) {
    //1. 제품 가져오기
    Product product = productRepository.findById(id)
        .orElseThrow(ProductNotFoundException::new);

    return ProductResponse.from(product);
  }

  public void updateProduct(Long id, UpdateProductRequest request) {
    //1. 해당 제품 있는지 확인
    Product product = productRepository.findById(id)
        .orElseThrow(ProductNotFoundException::new);
    //2. 카테고리 존재하는지 확인
    Category category = request.getCategoryId() != null
        ? categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new)
        : null;

    //3. 제품 수정
    product.update(request, category);
    productRepository.save(product);
  }

  public void deleteProduct(Long id) {
    //1. 제품 삭제상태로 변경
    Product product = productRepository.findById(id)
        .orElseThrow(ProductNotFoundException::new);

    product.delete();
    productRepository.save(product);
  }

  public void removeCategory(List<Product> products) {
    products.forEach(product -> {
      product.setCategory(null);
      productRepository.save(product);
    });
  }
}
