package SyntacticTree;

import AssemblerGenerator.RegisterContainer;
import SymbolTable.Attribute;

public class SyntacticTreeIFTHEN extends SyntacticTree{

    private static int counter = 0;

    public SyntacticTreeIFTHEN(SyntacticTree left, SyntacticTree rigth, Attribute attribute) {
        super(left, rigth, attribute);
    }

    public SyntacticTreeIFTHEN(SyntacticTree left, SyntacticTree rigth) {
        super(left, rigth);
    }

    public SyntacticTreeIFTHEN(SyntacticTree left, Attribute attribute) {
        super(left, attribute);
    }

    @Override
    public String generateAssemblerCode(RegisterContainer resgisterContainer) {
        String assembler = "";
        String label = "IF_THEN" + ++counter;
        assembler += "JMP " + label + '\n';
        assembler += SyntacticTreeIF.jLabel.pop() + ":" + '\n';
        SyntacticTreeIF.jLabel.push(label);
        return assembler;
    }
}
