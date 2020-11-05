package SemanticAnalyzer;

import SymbolTable.Attribute;
import SymbolTable.SymbolTable;

import java.util.ArrayList;
import java.util.List;

public class SemanticAnalyzer {

    private List<Integer> NA;

    public SemanticAnalyzer() {
        this.NA = new ArrayList<Integer>();
    }

    /*hola -> hola@main AMOUNT 2/ hola@main@z@c

    DOUBLE hola;
    PROC z (DOUBLE g) NA = 5_ul {
        PROC c (DOUBLE x) NA = 0_ul {
            DOUBLE hola;
        };
        DOUBLE hola;
    };

     //Si está declarada para usarse
    //Es alcanzable? */



    public boolean isRedeclared(String scope, String lexeme, Attribute attribute) {
        scope = lexeme + "@main" + scope;
        String[] scopeDiv = scope.split("@");
        while (scopeDiv.length > 1) {
            if (attribute.getScope().equals(scope))
                return true;
            else {
                scope = "";
                for (int i = 0; i < scopeDiv.length - 1; i++) {
                    if (i == 0)
                        scope += scopeDiv[i];
                    else
                        scope += "@" + scopeDiv[i];
                }
                scopeDiv = scope.split("@");
            }
        }
        return false;
    }

    public void addNA(int NA){
        this.NA.add(NA);
    }

    public void deleteNA(){
        this.NA.remove(this.NA.size()-1);
    }

    public int checkNA(String globalScope){
        String[] scope = globalScope.split("@");
        for(int i=0; i < this.NA.size(); i++){
            if(this.NA.get(i) < (scope.length - 2) - i){ //scope.length - 2 -> vacio del split y GS entra con sigo mismo
                return i;
            }
        }
        return -1;
    }

    public String errorNA(int pos, String globalScope){
        String[] scope = globalScope.split("@");
        return scope[pos+1];
    }


}
