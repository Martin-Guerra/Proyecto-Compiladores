package SyntacticTree;

import AssemblerGenerator.RegisterContainer;
import SymbolTable.Attribute;
import SymbolTable.Type;
import SymbolTable.Use;

public class SyntacticTreeMUL extends SyntacticTree{
    public SyntacticTreeMUL(SyntacticTree left, SyntacticTree rigth, Attribute attribute) {
        super(left, rigth, attribute);
    }

    public SyntacticTreeMUL(SyntacticTree left, SyntacticTree rigth) {
        super(left, rigth);
    }

    public SyntacticTreeMUL(SyntacticTree left, Attribute attribute) {
        super(left, attribute);
    }

    @Override
    public String generateAssemblerCode(RegisterContainer resgisterContainer) {
        String assembler = "";
        String aux = "";
        String register;
        Attribute attribute = null;

        if(this.getRight().getAttribute().getUse().equals(Use.constante)) {
            register = resgisterContainer.forceRegister();
            attribute = new Attribute(register, Use.registro);
            this.getRight().setAttribute(attribute);
            assembler += "MOV " + register + ", _" + this.getRight().getAttribute().getLexeme() + '\n';
            resgisterContainer.setAverableRegister(register);
        }

        if(!this.getLeft().getAttribute().getLexeme().equals("EAX")) {
            resgisterContainer.setNotAverableRegister(0);
            assembler += "MOV EAX" + ", _" + this.getLeft().getAttribute().getLexeme() + '\n';
            if(this.getLeft().getAttribute().getUse().equals(Use.registro))
                resgisterContainer.setAverableRegister(this.getLeft().getAttribute().getLexeme());
        }

        assembler += "MOV EDX, 0" + '\n';

        assembler += "MUL EAX, _" + this.getRight().getAttribute().getLexeme() + '\n';

        resgisterContainer.setAverableRegister("EDX");

        attribute = new Attribute("EAX", Use.registro);
        this.deleteChildren(this);
        this.replaceRoot(this, attribute);
        return assembler;
    }
}
