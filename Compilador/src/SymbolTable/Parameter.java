package SymbolTable;

public class Parameter {

    private String scope;
    private Type type;

    public Parameter(String lexeme, Type type) {
        this.scope = lexeme;
        this.type = type;
    }

    public String getLexeme() {
        return this.scope;
    }

    public void setLexeme(String lexeme) {
        this.scope = lexeme;
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
