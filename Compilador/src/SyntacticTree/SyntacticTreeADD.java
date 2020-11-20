package SyntacticTree;

import AssemblerGenerator.RegisterContainer;
import SymbolTable.Attribute;
import SymbolTable.Type;
import SymbolTable.Use;

public class SyntacticTreeADD extends SyntacticTree{
    public SyntacticTreeADD(SyntacticTree left, SyntacticTree rigth, Attribute attribute) {
        super(left, rigth, attribute);
    }

    public SyntacticTreeADD(SyntacticTree left, SyntacticTree rigth) {
        super(left, rigth);
    }

    public SyntacticTreeADD(SyntacticTree left, Attribute attribute) {
        super(left, attribute);
    }

    @Override
    public String generateAssemblerCode(RegisterContainer resgisterContainer) {

        String assembler = "";
        String register = "";
        Attribute attribute = null;
        if(checkChildrenUse()) {
            register = resgisterContainer.getRegister();
            attribute = new Attribute(register, Use.registro);
            assembler += "MOV " + register + ", _" + this.getLeft().getAttribute().getScope() + '\n';
            assembler += "ADD " + register + ", _" + this.getRight().getAttribute().getScope() + '\n';
        }

        if(this.getLeft().getAttribute().getUse().equals(Use.registro) &&
        this.getRight().getAttribute().getUse().equals(Use.registro)) {
            assembler += "ADD " + this.getLeft().getAttribute().getLexeme() + ", " + this.getRight().getAttribute().getLexeme() + '\n';
            resgisterContainer.setAverableRegister(this.getRight().getAttribute().getLexeme());
            attribute = this.getLeft().getAttribute();
        }else
            if(this.getLeft().getAttribute().getUse().equals(Use.registro)) {
                assembler += "ADD " + this.getLeft().getAttribute().getLexeme() + ", _" + this.getRight().getAttribute().getScope() + '\n';
                attribute = this.getLeft().getAttribute();
            }else{
                if(this.getRight().getAttribute().getUse().equals(Use.registro)){
                    assembler += "ADD " + this.getRight().getAttribute().getLexeme() + ", _" + this.getLeft().getAttribute().getScope() + '\n';
                    attribute = this.getRight().getAttribute();
                }
            }

        this.deleteChildren(this);
        this.replaceRoot(this, attribute);
        return assembler;
    }
}
