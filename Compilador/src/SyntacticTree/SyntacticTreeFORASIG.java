package SyntacticTree;

import AssemblerGenerator.RegisterContainer;
import SymbolTable.Attribute;
import SymbolTable.Use;

public class SyntacticTreeFORASIG extends SyntacticTree{

    private static int counter = 0;


    public SyntacticTreeFORASIG(SyntacticTree left, SyntacticTree rigth, Attribute attribute) {
        super(left, rigth, attribute);
    }

    public SyntacticTreeFORASIG(SyntacticTree left, SyntacticTree rigth) {
        super(left, rigth);
    }

    public SyntacticTreeFORASIG(SyntacticTree left, Attribute attribute) {
        super(left, attribute);
    }

    @Override
    public String generateAssemblerCodeRegister(RegisterContainer resgisterContainer) {

        String assembler = "";

        Attribute attribute = null;
        assembler += "MOV _" + this.getLeft().getAttribute().getScope() + ", _" + this.getRight().getAttribute().getScope()  + '\n';

        String label = "FOR_INICIO" + ++counter;
        SyntacticTreeFOR.jLabel.push(label);

        assembler += label + ":" + '\n';

        SyntacticTreeFOR.ID = this.getLeft().getAttribute();

        this.deleteChildren(this);
        this.replaceRoot(this, attribute);
        return assembler;
    }

    @Override
    public String generateAssemblerCodeVariable(RegisterContainer resgisterContainer) {
        String assembler = "";
        return assembler;
    }
}
