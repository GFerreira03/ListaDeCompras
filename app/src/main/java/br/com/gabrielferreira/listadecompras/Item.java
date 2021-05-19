package br.com.gabrielferreira.listadecompras;

public class Item {
    private final int quantity;
    private final String name;
    private final int hash;

    public int getHash() {
        return hash;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    //Gera um hash baseado na quantidade e nome do item.
    public Item(int quantity, String name) {
        this.quantity = quantity;
        this.name = name;
        this.hash = (quantity + name).hashCode();
    }

    @Override
    public String toString() {
        return quantity + " | " + name;
    }
}
