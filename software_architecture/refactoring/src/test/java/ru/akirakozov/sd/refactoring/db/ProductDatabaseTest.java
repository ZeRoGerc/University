package ru.akirakozov.sd.refactoring.db;

import com.sun.istack.internal.NotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(JUnit4.class)
public class ProductDatabaseTest {

    @NotNull
    private final ProductDatabase productDatabase = ProductDatabase.getInstance("jdbc:sqlite:testing.db");

    @Before
    public void setUp() throws Exception {
        productDatabase.clear();
    }

    @Test
    public void addProducts_shouldAdd() throws Exception {
        productDatabase.addProduct(new Product("name", 1));
        assertThat(productDatabase.getCount()).isOne();

        Product product = productDatabase.getProducts().get(0);
        assertThat(product.getName()).isEqualTo("name");
        assertThat(product.getPrice()).isEqualTo(1);
    }

    @Test
    public void clear_shouldClear() throws Exception {
        productDatabase.addProduct(new Product("name", 1));
        assertThat(productDatabase.getCount()).isOne();

        productDatabase.clear();
        assertThat(productDatabase.getCount()).isZero();
    }

    @Test
    public void getProducts_returnEmptyIfEmpty() throws Exception {
        assertThat(productDatabase.getProducts().toArray()).isEmpty();
    }

    @Test
    public void getProducts_returnProducts() throws Exception {
        Product product1 = new Product("name1", 1);
        Product product2 = new Product("name2", 2);

        productDatabase.addProduct(product1);
        productDatabase.addProduct(product2);

        List<Product> products = productDatabase.getProducts();

        assertThat(products.size()).isEqualTo(2);
        assertThat(products.toArray(new Product[0])).containsExactly(
                product1,
                product2
        );
    }

    @Test
    public void getMax_returnEmptyOnEmpty() throws Exception {
        assertThat(productDatabase.getMax().isPresent()).isFalse();
    }

    @Test
    public void getMax_returnMax() throws Exception {
        Product product1 = new Product("name1", 1);
        Product product2 = new Product("name2", 2);

        productDatabase.addProduct(product1);
        productDatabase.addProduct(product2);

        assertThat(productDatabase.getMax().get()).isEqualToComparingFieldByField(product2);
    }

    @Test
    public void getMin_returnEmptyOnEmpty() throws Exception {
        assertThat(productDatabase.getMin().isPresent()).isFalse();
    }

    @Test
    public void getMin_returnMin() throws Exception {
        Product product1 = new Product("name1", 1);
        Product product2 = new Product("name2", 2);

        productDatabase.addProduct(product1);
        productDatabase.addProduct(product2);

        assertThat(productDatabase.getMin().get()).isEqualToComparingFieldByField(product1);
    }

    @Test
    public void getSum_returnZeroOnEmpty() throws Exception {
        assertThat(productDatabase.getSum()).isZero();
    }

    @Test
    public void getSum_returnsSum() throws Exception {
        Product product1 = new Product("name1", 1);
        Product product2 = new Product("name2", 2);

        productDatabase.addProduct(product1);
        productDatabase.addProduct(product2);

        assertThat(productDatabase.getSum()).isEqualTo(3);
    }

    @Test
    public void getCount_returnZeroOnEmpty() throws Exception {
        assertThat(productDatabase.getCount()).isZero();
    }

    @Test
    public void getCount_returnsCount() throws Exception {
        Product product1 = new Product("name1", 1);
        Product product2 = new Product("name2", 2);

        productDatabase.addProduct(product1);
        productDatabase.addProduct(product2);

        assertThat(productDatabase.getCount()).isEqualTo(2);
    }
}
