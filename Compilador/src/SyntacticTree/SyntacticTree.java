package SyntacticTree;

public class SyntacticTree {
    private SyntacticTree left;
    private SyntacticTree rigth;
    private String lexeme;

    public SyntacticTree(SyntacticTree left, SyntacticTree rigth, String lexema) {
        this.left = left;
        this.rigth = rigth;
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

    public SyntacticTree getRigth() {
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

    public void printTree(SyntacticTree node){
        if (node.isLeaf())
            return;
        System.out.print(node.getLexeme() + '\n'); // mostrar datos del nodo
        printTree(node.getLeft()); //recorre subarbol izquierdo
        printTree(node.getRigth()); //recorre subarbol derecho
    }

     public boolean isLeaf(){
        return (this.getLeft() == null & this.getRigth() == null);
    }
}
