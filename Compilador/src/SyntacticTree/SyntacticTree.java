package SyntacticTree;

import AssemblerGenerator.RegisterContainer;
import SymbolTable.Attribute;
import SymbolTable.Type;
import SymbolTable.Use;

public abstract class SyntacticTree {
    private SyntacticTree left = null;
    private SyntacticTree rigth = null;
    private Attribute attribute = null;
    private int cant = 0;

    public SyntacticTree(SyntacticTree left, SyntacticTree rigth, Attribute attribute) {
        this.left = left;
        this.rigth = rigth;
        this.attribute = attribute;
    }

    public SyntacticTree(SyntacticTree left, SyntacticTree rigth) {
        this.left = left;
        this.rigth = rigth;
    }

    public SyntacticTree(SyntacticTree left, Attribute attribute) {
        this.left = left;
        this.attribute = attribute;
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
        return attribute.getLexeme();
    }

    public void setLexeme(String lexeme) {
        this.attribute.setLexeme(lexeme);
    }

    public Attribute getAttribute(){
        return this.attribute;
    }

    public Attribute setAttribute(Attribute attribute){
        this.attribute = attribute;
        return this.attribute;
    }

    public void printTree(SyntacticTree node) {
        if (node != null) {
            if (node.isLeaf()) {
                tab(cant, node.attribute.getLexeme(), node.attribute.getType());
                return;
            }
            tab(cant, node.getLexeme(), node.getType()); // mostrar datos del nodo
            cant++;
            printTree(node.getLeft()); //recorre subarbol izquierdo
            printTree(node.getRight()); //recorre subarbol derecho
            cant--;
        }
    }

    public void setType(Type type) {
        this.attribute.setType(type);
    }

    public Type getType() {
        return this.attribute.getType();
    }

    public boolean isLeaf(){
        return (this.getLeft() == null && this.getRight() == null);
    }

    private void tab(int cant, String lexeme, Type type){
        for(int i=cant; i>0; i--){
            lexeme = '\t' + lexeme;
        }
        System.out.print(lexeme + " Type " + type +'\n');
    }

    public boolean checkType(SyntacticTree root){
        if (root != null && !root.isLeaf()) {
            if(root.left.attribute.getType().getName().equals(root.rigth.attribute.getType().getName())){
                root.attribute.setType(root.left.attribute.getType());
                return true; //Tipos compatibles
            }else{
                root.attribute.setType(Type.ERROR);
                return false; //Tipos incompatibles
            }
        }
        if(root == null) {
            root.attribute.setType(Type.ERROR);
            return false;
        }else
            return true; //Tipos compatibles
    }

    public void deleteChildren(SyntacticTree root){
        root.setLeft(null);
        root.setRigth(null);
    }

    public void replaceRoot(SyntacticTree root, Attribute attribute){
        root.setAttribute(attribute);
    }

    public boolean checkChildrenUse() {
        if(this.getLeft().getAttribute().getFlag() == 1 && this.getRight().getAttribute().getFlag() == 1)
                return true;
        return false;
    }

    public abstract String generateAssemblerCode(RegisterContainer resgisterContainer);
}
