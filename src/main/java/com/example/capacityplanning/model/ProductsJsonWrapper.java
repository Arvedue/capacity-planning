package com.example.capacityplanning.model;

import java.util.List;

public class ProductsJsonWrapper {
    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Product JSON Wrapper {" +
                "products: " + products +
                "}";
    }
}
