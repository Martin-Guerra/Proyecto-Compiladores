package SyntacticTree;

import AssemblerGenerator.RegisterContainer;
import SymbolTable.Attribute;
import SymbolTable.Use;

public class SyntacticTreeDIV extends SyntacticTree{
    public SyntacticTreeDIV(SyntacticTree left, SyntacticTree rigth, Attribute attribute) {
        super(left, rigth, attribute);
    }

    public SyntacticTreeDIV(SyntacticTree left, SyntacticTree rigth) {
        super(left, rigth);
    }

    public SyntacticTreeDIV(SyntacticTree left, Attribute attribute) {
        super(left, attribute);
    }

    @Override
    public String generateAssemblerCode(RegisterContainer resgisterContainer) {

        //if(el divisor es cero) then activo flag (saltar a fin de programa)
        String assembler = "";
        String register = "";
        String aux = "";
        if(this.getRight().getAttribute().getUse().equals(Use.constante)) {
            register = resgisterContainer.forceRegister();
            assembler += "MOV " + register + ", _" + this.getRight().getAttribute().getLexeme() + '\n';
            aux = "DIV " + register + '\n';
            resgisterContainer.setAverableRegister(register);
        }else
            aux = "DIV _" + this.getRight().getAttribute().getLexeme() + '\n';

        if(!this.getLeft().getAttribute().getLexeme().equals("EAX")) {
            resgisterContainer.setNotAverableRegister(0);
            assembler += "MOV EAX" + ", _" + this.getLeft().getAttribute().getLexeme() + '\n';
            if(this.getLeft().getAttribute().getUse().equals(Use.registro))
                resgisterContainer.setAverableRegister(this.getLeft().getAttribute().getLexeme());
        }

        assembler += "MOV EDX, 0" + '\n';
        resgisterContainer.setNotAverableRegister(3);

        assembler += aux;

        resgisterContainer.setAverableRegister("EDX");

        this.deleteChildren(this);
        Attribute attribute = new Attribute("EAX", Use.registro);
        this.replaceRoot(this, attribute);
        return assembler;
    }
}
