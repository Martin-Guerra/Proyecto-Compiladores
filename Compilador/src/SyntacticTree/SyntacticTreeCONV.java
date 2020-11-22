package SyntacticTree;

import AssemblerGenerator.RegisterContainer;
import SymbolTable.Attribute;

public class SyntacticTreeCONV extends SyntacticTree{
    public SyntacticTreeCONV(SyntacticTree left, SyntacticTree rigth, Attribute attribute) {
        super(left, rigth, attribute);
    }

    public SyntacticTreeCONV(SyntacticTree left, SyntacticTree rigth) {
        super(left, rigth);
    }

    public SyntacticTreeCONV(SyntacticTree left, Attribute attribute) {
        super(left, attribute);
    }

    @Override
    public String generateAssemblerCodeRegister(RegisterContainer resgisterContainer) {
        String assembler = "";
        Attribute attribute = this.getLeft().getAttribute();
        this.deleteLeftChildren(this);
        this.replaceRoot(this, attribute);
        return assembler;
    }

    @Override
    public String generateAssemblerCodeVariable(RegisterContainer resgisterContainer) {
        String assembler = "";
        //FILD -> de entero a punto flotante
        return assembler;
    }
}
