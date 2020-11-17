package SyntacticTree;

import AssemblerGenerator.RegisterContainer;
import SymbolTable.Attribute;
import SymbolTable.Use;

public class SyntacticTreeASIG extends SyntacticTree{
    public SyntacticTreeASIG(SyntacticTree left, SyntacticTree rigth, Attribute attribute) {
        super(left, rigth, attribute);
    }

    public SyntacticTreeASIG(SyntacticTree left, SyntacticTree rigth) {
        super(left, rigth);
    }

    public SyntacticTreeASIG(SyntacticTree left, Attribute attribute) {
        super(left, attribute);
    }

    @Override
    public String generateAssemblerCode(RegisterContainer resgisterContainer) {

        String assembler = "";
        Attribute attribute = null;
        String register = "";

        if (checkChildrenUse()) {
            register = resgisterContainer.getRegister();
            //attribute = new Attribute(register, Use.registro);
            assembler += "MOV " + register + ", _" + this.getRight().getAttribute().getLexeme() + '\n';
            assembler += "MOV _" + this.getLeft().getAttribute().getLexeme() + ", " + register + '\n';
            resgisterContainer.setAverableRegister(register);
        }else {
            if (this.getLeft().getAttribute().getUse().equals(Use.registro) &&
                this.getRight().getAttribute().getUse().equals(Use.registro)) {
                assembler += "MOV " + this.getLeft().getAttribute().getLexeme() + ", " + this.getRight().getAttribute().getLexeme() + '\n';
                resgisterContainer.setAverableRegister(this.getRight().getAttribute().getLexeme());
                resgisterContainer.setAverableRegister(this.getLeft().getAttribute().getLexeme());
            } else if (this.getLeft().getAttribute().getUse().equals(Use.registro)) {
                assembler += "MOV " + this.getLeft().getAttribute().getLexeme() + ", _" + this.getRight().getAttribute().getLexeme() + '\n';
                resgisterContainer.setAverableRegister(this.getLeft().getAttribute().getLexeme());
            } else {
                if (this.getRight().getAttribute().getUse().equals(Use.registro)) {
                    assembler += "MOV _" + this.getLeft().getAttribute().getLexeme() + ", " + this.getRight().getAttribute().getLexeme() + '\n';
                    resgisterContainer.setAverableRegister(this.getRight().getAttribute().getLexeme());
                }
            }
        }

        this.deleteChildren(this);
        //this.replaceRoot(this, attribute);
        return assembler;
    }
}
