package br.edu.utfpr.pb.pw44s.server.controller;

import br.edu.utfpr.pb.pw44s.server.model.Category;
import br.edu.utfpr.pb.pw44s.server.model.Product;
import br.edu.utfpr.pb.pw44s.server.repository.ProductRepository;
import br.edu.utfpr.pb.pw44s.server.service.IProductsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {
    private final IProductsService productsService;

    public ProductController(IProductsService productsService) {
        this.productsService = productsService;
    }

    @PostMapping
    public ResponseEntity<Product> save(@RequestBody Product product) {
        productsService.save(product);
        URI location= ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(product.getId()).toUri();
        return ResponseEntity.created(location).body(product);
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok(this.productsService.findAll());
    }

    @GetMapping("page")
    public ResponseEntity<Page<Product>> findAll(@RequestParam int page,
                                                  @RequestParam int size,
                                                  @RequestParam(required = false) String order,
                                                  @RequestParam(required = false) Boolean asc) {
        PageRequest pageRequest = PageRequest.of(page, size);
        if (order != null && asc != null) {
            pageRequest = PageRequest.of(page,
                    size,
                    asc ? Sort.Direction.ASC : Sort.Direction.DESC,
                    order);
        }

        return ResponseEntity.ok(this.productsService.findAll(pageRequest));
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> findByI(@PathVariable Long id){
        Product product = this.productsService.findById(id);

        if (product == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(this.productsService.findById(id));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        this.productsService.deletedById(id);
    }
}
