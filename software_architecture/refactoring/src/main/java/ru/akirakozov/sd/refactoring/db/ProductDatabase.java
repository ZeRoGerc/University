package ru.akirakozov.sd.refactoring.db;

import com.sun.istack.internal.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDatabase {

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS PRODUCT" +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            " NAME           TEXT    NOT NULL, " +
            " PRICE          INT     NOT NULL)";

    private static final String CLEAR_TABLE = "DELETE FROM PRODUCT";

    private static final String ADD_PRODUCT = "INSERT INTO PRODUCT (NAME, PRICE) VALUES (\"%s\",%s)";

    private static final String GET_PRODUCTS = "SELECT * FROM PRODUCT";

    private static final String GET_MAX = "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1";

    private static final String GET_MIN = "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1";

    private static final String GET_SUM = "SELECT SUM(price) FROM PRODUCT";

    private static final String GET_COUNT = "SELECT COUNT(*) FROM PRODUCT";

    @NotNull
    private final String dbName;

    @NotNull
    public static ProductDatabase getInstance(@NotNull String dbName) {
        return new ProductDatabase(dbName);
    }

    private ProductDatabase(@NotNull String dbName) {
        this.dbName = dbName;
        executeUpdate(CREATE_TABLE);
    }

    public void addProduct(@NotNull Product product) {
        executeUpdate(String.format(ADD_PRODUCT, product.getName(), product.getPrice()));
    }

    @NotNull
    public void clear() {
        executeUpdate(CLEAR_TABLE);
    }

    @NotNull
    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        executeQuery(GET_PRODUCTS, resultSet -> products.add(extractProduct(resultSet)));
        return products;
    }

    @NotNull
    public Optional<Product> getMax() {
        return executeQuery(GET_MAX, this::extractProduct);
    }

    @NotNull
    public Optional<Product> getMin() {
        return executeQuery(GET_MIN, this::extractProduct);
    }

    public int getSum() {
        return executeQuery(GET_SUM, (resultSet -> resultSet.getInt(1)))
                .orElse(0);
    }

    public int getCount() {
        return executeQuery(GET_COUNT, (resultSet -> resultSet.getInt(1)))
                .orElse(0);
    }

    private void executeUpdate(@NotNull String sql) {
        try (Connection c = DriverManager.getConnection(dbName)) {
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    private <T> Optional<T> executeQuery(@NotNull String query, MyFunctionalInterface<T> function) {
        try (Connection c = DriverManager.getConnection(dbName)) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            T result = null;
            while (rs.next()) {
                result = function.accept(rs);
            }

            rs.close();
            stmt.close();

            if (result == null) {
                return Optional.empty();
            } else {
                return Optional.of(result);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    private Product extractProduct(@NotNull ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        int price = resultSet.getInt("price");
        return new Product(name, price);
    }

    public interface MyFunctionalInterface<T> {
        T accept(ResultSet t) throws Exception;
    }
}

