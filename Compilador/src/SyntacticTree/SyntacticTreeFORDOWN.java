package SyntacticTree;

import AssemblerGenerator.RegisterContainer;
import SymbolTable.Attribute;

public class SyntacticTreeFORDOWN extends SyntacticTree{

    public SyntacticTreeFORDOWN(SyntacticTree left, SyntacticTree rigth, Attribute attribute) {
        super(left, rigth, attribute);
    }

    public SyntacticTreeFORDOWN(SyntacticTree left, SyntacticTree rigth) {
        super(left, rigth);
    }

    public SyntacticTreeFORDOWN(SyntacticTree left, Attribute attribute) {
        super(left, attribute);
    }

    @Override
    public String generateAssemblerCode(RegisterContainer resgisterContainer) {
        String assembler = "";
        String register = resgisterContainer.getRegister();
        assembler += "MOV " + register + ", _" + SyntacticTreeFOR.ID.getLexeme();
        assembler += "SUB " + register + ", _" + this.getLeft().getLexeme();
        assembler += "MOV _" + SyntacticTreeFOR.ID.getLexeme() + ", " + register;
        resgisterContainer.setAverableRegister(register);

        return assembler;
    }
}
