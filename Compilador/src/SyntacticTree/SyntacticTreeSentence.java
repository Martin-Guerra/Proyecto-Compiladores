package SyntacticTree;

import AssemblerGenerator.RegisterContainer;
import SymbolTable.Attribute;

public class SyntacticTreeSentence extends SyntacticTree{


    public SyntacticTreeSentence(SyntacticTree left, SyntacticTree rigth, Attribute attribute) {
        super(left, rigth, attribute);
    }

    public SyntacticTreeSentence(SyntacticTree left, SyntacticTree rigth) {
        super(left, rigth);
    }

    public SyntacticTreeSentence(SyntacticTree left, Attribute attribute) {
        super(left, attribute);
    }

    @Override
    public String generateAssemblerCode(RegisterContainer resgisterContainer) {
        String assembler = "";
        return assembler;
    }
}
