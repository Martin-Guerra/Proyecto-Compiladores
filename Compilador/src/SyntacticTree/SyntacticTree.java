package SyntacticTree;

import SymbolTable.Attribute;

public class SyntacticTree {
    private SyntacticTree left = null;
    private SyntacticTree rigth = null;
    private String lexeme;
    private Attribute attribute = null;
    private int cant = 0;

    public SyntacticTree(SyntacticTree left, SyntacticTree rigth, String lexeme, Attribute attribute) {
        this.left = left;
        this.rigth = rigth;
        this.lexeme = lexeme;
        this.attribute = attribute;
    }

    public SyntacticTree(SyntacticTree left, SyntacticTree rigth, String lexema) {
        this.left = left;
        this.rigth = rigth;
        this.lexeme = lexema;
    }

    public SyntacticTree(SyntacticTree left, String lexema) {
        this.left = left;
        this.lexeme = lexema;
    }

    public SyntacticTree getRoot() {
        return this;
    }

    public void setRoot(){

    }

    public SyntacticTree getLeft() {
        return left;
    }

    public void setLeft(SyntacticTree left) {
        this.left = left;
    }

    public SyntacticTree getRight() {
        return rigth;
    }

    public void setRigth(SyntacticTree rigth) {
        this.rigth = rigth;
    }

    public String getLexeme() {
        return lexeme;
    }

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

    public void printTree(SyntacticTree node) {
        if (node != null) {
            if (node.isLeaf()) {
                tab(cant, node.getLexeme());
                return;
            }
            tab(cant, node.getLexeme()); // mostrar datos del nodo
            cant++;
            printTree(node.getLeft()); //recorre subarbol izquierdo
            printTree(node.getRight()); //recorre subarbol derecho
            cant--;
        }
    }

    public boolean isLeaf(){
        return (this.getLeft() == null & this.getRight() == null);
    }

    private void tab(int cant, String lexeme){
        for(int i=cant; i>0; i--){
            lexeme = '\t' + lexeme;
        }
        System.out.print(lexeme + '\n');
    }
}
