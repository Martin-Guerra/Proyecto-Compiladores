package SymbolTable;

import SyntacticTree.SyntacticTree;
import org.w3c.dom.Attr;

import java.util.List;

public class Attribute {
    private String scope;
    private Type type;
    private Use use;
    private String id;
    private SyntacticTree tree;
    private int amount;
    private boolean declared;

    public Attribute(String lexeme, Type type, Use use, String id, SyntacticTree tree) {
        this.scope = lexeme;
        this.type = type;
        this.use = use;
        this.id = id;
        this.tree = tree;
        this.amount = 1;
        this.declared = false;
    }

    public Attribute(String lexeme, String id) {
        this.scope = lexeme;
        this.id = id;
        this.amount = 1;
        this.declared = false;
    }

    public Attribute(String lexeme, String id, Type type) {
        this.scope = lexeme;
        this.type = type;
        this.id = id;
        this.amount = 1;
        this.declared = false;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        if(scope.equals(""))
            this.scope = this.scope + scope;
        else
            this.scope = this.scope + scope;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    /*public static void setType(List<Attribute> entries, Type type) {
        for (Attribute entry : entries) {
            entry.setType(type);
        }
    }*/

    public Use getUse() {
        return use;
    }

    public void setUse(Use use) {
        this.use = use;
    }

    /*public static void setUse(List<Attribute> entries, Use use) {
        for (Attribute entry : entries) {
            entry.setUse(use);
        }
    }*/

    public String getId() {
        return id;
    }

    public void increaseAmount(){
        this.amount++;
    }

    public void decreaseAmount(){
        this.amount--;
    }

    public int getAmount(){
        return this.amount;
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

    public void setDeclared(){
        this.declared = true;
    }

    public void setDeclaredFalse(){
        this.declared = false;
    }

    public boolean isDeclared(){
        return this.declared;
    }
}
