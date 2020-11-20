package SyntacticTree;

import AssemblerGenerator.RegisterContainer;
import SymbolTable.Attribute;
import SymbolTable.Parameter;

import java.util.ArrayList;
import java.util.List;

public class SyntacticTreeCALL extends SyntacticTree{

    private List<Parameter> formalParameters = new ArrayList<>();

    public SyntacticTreeCALL(SyntacticTree left, SyntacticTree rigth, Attribute attribute) {
        super(left, rigth, attribute);
    }

    public SyntacticTreeCALL(SyntacticTree left, Attribute attribute, List<Parameter> formalParameters) {
        super(left, attribute);
        this.formalParameters = formalParameters;
    }



    public SyntacticTreeCALL(SyntacticTree left, SyntacticTree rigth) {
        super(left, rigth);
    }

    public SyntacticTreeCALL(SyntacticTree left, Attribute attribute) {
        super(left, attribute);
    }

    @Override
    public String generateAssemblerCode(RegisterContainer resgisterContainer) {
        String assembler = "";

        List<Parameter> realParameters = this.getAttribute().getParameters();
        for(int i=0; i < realParameters.size(); i++){
            assembler += "MOV _" + this.formalParameters.get(i).getLexeme() +
                        ", _" + realParameters.get(i).getLexeme() + '\n';
        }

        assembler += "CALL " + this.getAttribute().getScope() + '\n';
        return assembler;
    }
}
