package SyntacticTree;

import AssemblerGenerator.RegisterContainer;
import SymbolTable.Attribute;
import SymbolTable.Use;

public class SyntacticTreeIFCMP extends SyntacticTree{

    private static int counter = 0;

    public SyntacticTreeIFCMP(SyntacticTree left, SyntacticTree rigth, Attribute attribute) {
        super(left, rigth, attribute);
    }

    public SyntacticTreeIFCMP(SyntacticTree left, SyntacticTree rigth) {
        super(left, rigth);
    }

    public SyntacticTreeIFCMP(SyntacticTree left, Attribute attribute) {
        super(left, attribute);
    }

    @Override
    public String generateAssemblerCode(RegisterContainer resgisterContainer) {
        String assembler = "";
        String register = "";
        Attribute attribute = null;
        if(this.getLeft().getAttribute().getUse().equals(Use.constante)) {
            register = resgisterContainer.getRegister();
            attribute = new Attribute(register, Use.registro);
            assembler += "MOV " + register + ", _" + this.getLeft().getAttribute().getLexeme() + '\n';
            this.replaceRoot(this.getLeft(), attribute);
        }

        if((this.getLeft().getAttribute().getUse().equals(Use.registro) &&
            this.getRight().getAttribute().getUse().equals(Use.registro))) {
                assembler += "CMP " + this.getLeft().getAttribute().getLexeme() + ", " + this.getRight().getAttribute().getLexeme() + '\n';
                resgisterContainer.setAverableRegister(this.getLeft().getAttribute().getLexeme());
                resgisterContainer.setAverableRegister(this.getRight().getAttribute().getLexeme());
        }else {
            if (this.getLeft().getAttribute().getUse().equals(Use.registro)) {
                assembler += "CMP " + this.getLeft().getAttribute().getLexeme() + ", _" + this.getRight().getAttribute().getLexeme() + '\n';
                resgisterContainer.setAverableRegister(this.getLeft().getAttribute().getLexeme());
            } else {
                if (this.getRight().getAttribute().getUse().equals(Use.registro)) {
                    assembler += "CMP " + register + ", " + this.getLeft().getAttribute().getLexeme() + '\n';
                    resgisterContainer.setAverableRegister(this.getRight().getAttribute().getLexeme());
                } else {
                    if ((this.getLeft().getAttribute().getUse().equals(Use.variable) &&
                            this.getRight().getAttribute().getUse().equals(Use.variable))) {
                        assembler += "CMP _" + this.getLeft().getAttribute().getLexeme() + ", _" + this.getRight().getAttribute().getLexeme() + '\n';
                    }else{
                        if ((this.getLeft().getAttribute().getUse().equals(Use.variable) &&
                            this.getRight().getAttribute().getUse().equals(Use.constante))) {
                            assembler += "CMP _" + this.getLeft().getAttribute().getLexeme() + ", _" + this.getRight().getAttribute().getLexeme() + '\n';
                        }
                    }
                }
            }
        }

        String label = "IF_CMP" + ++counter;
        assembler += getAssemblerIfCondition() + label + '\n';
        SyntacticTreeIF.jLabel.push(label);
        return assembler;
    }

    public String getAssemblerIfCondition(){
        String assembler = "";
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
