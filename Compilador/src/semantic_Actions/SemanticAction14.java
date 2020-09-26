package semantic_Actions;

import Lexer.LexerAnalyzer;
import Lexer.State;

public class SemanticAction14 implements SemanticAction {

    private SemanticAction sa8;
    private SemanticAction sa12;

    public SemanticAction14() {
        SemanticAction sa8 = new SemanticAction8();
        SemanticAction sa12 = new SemanticAction12();
    }

    @Override
    public void execute(char character, LexerAnalyzer la) {

        sa8.execute(character, la);
        sa12.execute(character, la);
        la.setLexeme("");

    }

}
