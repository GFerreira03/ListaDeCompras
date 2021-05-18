package br.com.gabrielferreira.listadecompras;

public class Item {
    public final int quantity;
    public final String name;

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public Item(int quantity, String name) {
        this.quantity = quantity;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Item{" +
                "quantity='" + quantity + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
