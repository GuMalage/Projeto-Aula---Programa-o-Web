package br.edu.utfpr.pb.pw44s.server.service;

import br.edu.utfpr.pb.pw44s.server.model.Category;
import br.edu.utfpr.pb.pw44s.server.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductsService {
    List<Product> findAll();
    Page<Product> findAll(Pageable pageable);
    Product save(Product product);
    Product findById(Long id);
    void deletedById(Long id);
    boolean existsById(Long id);
    long count();

}
