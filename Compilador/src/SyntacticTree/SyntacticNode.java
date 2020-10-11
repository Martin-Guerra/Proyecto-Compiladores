package SyntacticTree;

public class SyntacticNode {
    private Object left;
    private Object rigth;
    private String lexeme;

    public SyntacticNode(Object left, Object rigth, String lexema) {
        this.left = left;
        this.rigth = rigth;
        this.lexeme = lexema;
    }

    public Object getLeft() {
        return left;
    }

    public void setLeft(Object left) {
        this.left = left;
    }

    public Object getRigth() {
        return rigth;
    }

    public void setRigth(Object rigth) {
        this.rigth = rigth;
    }

    public String getLexeme() {
        return lexeme;
    }

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

    public boolean isLeaf(){
        return (this.getLeft() == null & this.getRigth() == null);
    }

    public void printNode(){
        System.out.println(this.lexeme);
    }
}
