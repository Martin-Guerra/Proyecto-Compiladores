package SyntacticTree;

import AssemblerGenerator.RegisterContainer;
import SymbolTable.Attribute;
import SymbolTable.Use;

public class SyntacticTreeFORCMP extends SyntacticTree{

    private static int counter = 0;

    public SyntacticTreeFORCMP(SyntacticTree left, SyntacticTree rigth, Attribute attribute) {
        super(left, rigth, attribute);
    }

    public SyntacticTreeFORCMP(SyntacticTree left, SyntacticTree rigth) {
        super(left, rigth);
    }

    public SyntacticTreeFORCMP(SyntacticTree left, Attribute attribute) {
        super(left, attribute);
    }

    @Override
    public String generateAssemblerCode(RegisterContainer resgisterContainer) {
        String assembler = "";

        if(this.getRight().getAttribute().getUse().equals(Use.registro)) {
            assembler += "CMP _" + this.getLeft().getAttribute().getScope() + ", " + this.getRight().getAttribute().getLexeme() + '\n';
            resgisterContainer.setAverableRegister(this.getRight().getAttribute().getLexeme());
        }else
                assembler += "CMP _" + this.getLeft().getAttribute().getScope() + ", _" + this.getRight().getAttribute().getScope() + '\n';

        String label = "FOR_CMP" + ++counter;
        assembler += getAssemblerFORCondition() + label + '\n';
        SyntacticTreeFOR.jLabel.push(label);
        return assembler;
    }

    public String getAssemblerFORCondition(){
        String assembler;
        switch(this.getLexeme())
        {
            case "<" : assembler = "JAE ";
                break;
            case ">" : assembler = "JBE ";
                break;
            case "==" : assembler = "JNE ";
                break;
            case ">=" : assembler = "JA ";
                break;
            case "<=" : assembler = "JB ";
                break;
            case "!=" : assembler = "JE ";
                break;
            default : assembler = "";
        }
        return assembler;
    }
}
