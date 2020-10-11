package SymbolTable;

import SyntacticTree.SyntacticTree;

public class Attribute {
    private String type;
    private String use;
    private String id;
    private SyntacticTree tree;

    public Attribute(String type, String use, String id, SyntacticTree tree) {
        this.type = type;
        this.use = use;
        this.id = id;
        this.tree = tree;
    }

    public Attribute(String id) {
        this.id = id;
    }

    public Attribute(String id, String type) {
        this.type = type;
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
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
