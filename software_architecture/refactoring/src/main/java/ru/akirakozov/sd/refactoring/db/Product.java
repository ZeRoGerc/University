package ru.akirakozov.sd.refactoring.db;

import com.sun.istack.internal.NotNull;

public class Product {

    @NotNull
    private final String name;

    private final int price;

    public Product(@NotNull String name, int price) {
        this.name = name;
        this.price = price;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        return getPrice() == product.getPrice() && (getName() != null
                ? getName().equals(product.getName())
                : product.getName() == null);
    }
}
