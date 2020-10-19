package SymbolTable;

import SyntacticTree.SyntacticTree;
import org.w3c.dom.Attr;

import java.util.List;

public class Attribute {
    private Type type;
    private Use use;
    private String id;
    private SyntacticTree tree;

    public Attribute(Type type, Use use, String id, SyntacticTree tree) {
        this.type = type;
        this.use = use;
        this.id = id;
        this.tree = tree;
    }

    public Attribute(String id) {
        this.id = id;
    }

    public Attribute(String id, Type type) {
        this.type = type;
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        type = type;
    }

    public static void setType(List<Attribute> entries, Type type) {
        for (Attribute entry : entries) {
            entry.setType(type);
        }
    }

    public Use getUse() {
        return use;
    }

    public void setUse(Use use) {
        use = use;
    }

    public static void setUse(List<Attribute> entries, Use use) {
        for (Attribute entry : entries) {
            entry.setUse(use);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SyntacticTree getTree() {
        return tree;
    }

    public void setTree(SyntacticTree tree) {
        this.tree = tree;
    }
}
