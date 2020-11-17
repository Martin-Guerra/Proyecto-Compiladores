package SyntacticTree;

import AssemblerGenerator.RegisterContainer;
import SymbolTable.Attribute;

public class SyntacticTreeCALL extends SyntacticTree{
    public SyntacticTreeCALL(SyntacticTree left, SyntacticTree rigth, Attribute attribute) {
        super(left, rigth, attribute);
    }

    public SyntacticTreeCALL(SyntacticTree left, SyntacticTree rigth) {
        super(left, rigth);
    }

    public SyntacticTreeCALL(SyntacticTree left, Attribute attribute) {
        super(left, attribute);
    }

    @Override
    public String generateAssemblerCode(RegisterContainer resgisterContainer) {
        String assembler = "";
        //assembler += "MOV "
        assembler += "CALL " + this.getAttribute().getScope();
        return assembler;
    }
}
