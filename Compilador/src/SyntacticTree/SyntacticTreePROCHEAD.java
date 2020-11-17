package SyntacticTree;

import AssemblerGenerator.RegisterContainer;
import SymbolTable.Attribute;

public class SyntacticTreePROCHEAD extends SyntacticTree{
    public SyntacticTreePROCHEAD(SyntacticTree left, SyntacticTree rigth, Attribute attribute) {
        super(left, rigth, attribute);
    }

    public SyntacticTreePROCHEAD(SyntacticTree left, SyntacticTree rigth) {
        super(left, rigth);
    }

    public SyntacticTreePROCHEAD(SyntacticTree left, Attribute attribute) {
        super(left, attribute);
    }

    @Override
    public String generateAssemblerCode(RegisterContainer resgisterContainer) {
        String assembler = "";
        return assembler;
    }
}
