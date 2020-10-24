package SymbolTable;

import java.util.Objects;

public class Type {
    private String name;
    public static final Type DOUBLE = new Type("DOUBLE");
    public static final Type ULONGINT = new Type("ULONGINT");

    public Type(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Type type = (Type) o;
        return name.equals(type.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Type(" +
                this.name + ")";
    }
}

