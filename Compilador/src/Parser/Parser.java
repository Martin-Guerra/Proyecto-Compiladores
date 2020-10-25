//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 1 "G08 - Gramatica - 21102020.y"


package Parser;

import Lexer.LexerAnalyzer;
import java.util.ArrayList;
import java.util.List;
import Lexer.Token;
import SymbolTable.Attribute;
import SymbolTable.Type;
import SymbolTable.Use;
import SyntacticTree.SyntacticTree;


//#line 32 "Parser.java"




public class Parser
{

    boolean yydebug;        //do I want debug output?
    int yynerrs;            //number of errors so far
    int yyerrflag;          //was there an error?
    int yychar;             //the current working character

    //########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
    void debug(String msg)
    {
        if (yydebug)
            System.out.println(msg);
    }

    //########## STATE STACK ##########
    final static int YYSTACKSIZE = 500;  //maximum stack size
    int statestk[] = new int[YYSTACKSIZE]; //state stack
    int stateptr;
    int stateptrmax;                     //highest index of stackptr
    int statemax;                        //state when highest index reached
    //###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
    final void state_push(int state)
    {
        try {
            stateptr++;
            statestk[stateptr]=state;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            int oldsize = statestk.length;
            int newsize = oldsize * 2;
            int[] newstack = new int[newsize];
            System.arraycopy(statestk,0,newstack,0,oldsize);
            statestk = newstack;
            statestk[stateptr]=state;
        }
    }
    final int state_pop()
    {
        return statestk[stateptr--];
    }
    final void state_drop(int cnt)
    {
        stateptr -= cnt;
    }
    final int state_peek(int relative)
    {
        return statestk[stateptr-relative];
    }
    //###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
    final boolean init_stacks()
    {
        stateptr = -1;
        val_init();
        return true;
    }
    //###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
    void dump_stacks(int count)
    {
        int i;
        System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
        for (i=0;i<count;i++)
            System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
        System.out.println("======================");
    }


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


    String   yytext;//user variable to return contextual strings
    ParserVal yyval; //used to return semantic vals from action routines
    ParserVal yylval;//the 'lval' (result) I got from yylex()
    ParserVal valstk[];
    int valptr;
    //###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
    void val_init()
    {
        valstk=new ParserVal[YYSTACKSIZE];
        yyval=new ParserVal();
        yylval=new ParserVal();
        valptr=-1;
    }
    void val_push(ParserVal val)
    {
        if (valptr>=YYSTACKSIZE)
            return;
        valstk[++valptr]=val;
    }
    ParserVal val_pop()
    {
        if (valptr<0)
            return new ParserVal();
        return valstk[valptr--];
    }
    void val_drop(int cnt)
    {
        int ptr;
        ptr=valptr-cnt;
        if (ptr<0)
            return;
        valptr = ptr;
    }
    ParserVal val_peek(int relative)
    {
        int ptr;
        ptr=valptr-relative;
        if (ptr<0)
            return new ParserVal();
        return valstk[ptr];
    }
    final ParserVal dup_yyval(ParserVal val)
    {
        ParserVal dup = new ParserVal();
        dup.ival = val.ival;
        dup.dval = val.dval;
        dup.sval = val.sval;
        dup.obj = val.obj;
        return dup;
    }
    //#### end semantic value section ####
    public final static short ID=257;
    public final static short ULONGINT=258;
    public final static short IF=259;
    public final static short THEN=260;
    public final static short ELSE=261;
    public final static short END_IF=262;
    public final static short FOR=263;
    public final static short OUT=264;
    public final static short PROC=265;
    public final static short RETURN=266;
    public final static short DOUBLE=267;
    public final static short MENOR_IGUAL=268;
    public final static short MAYOR_IGUAL=269;
    public final static short IGUAL=270;
    public final static short DISTINTO=271;
    public final static short PUNTO_PUNTO=272;
    public final static short UP=273;
    public final static short DOWN=274;
    public final static short CADENA=275;
    public final static short NA=276;
    public final static short NRO_DOUBLE=277;
    public final static short NRO_ULONGINT=278;
    public final static short YYERRCODE=256;
    final static short yylhs[] = {                           -1,
            0,    1,    1,    1,    2,    2,    3,    3,    3,    4,
            4,    4,    4,    4,    6,    6,    5,    5,    7,    7,
            7,    7,    7,    7,    7,    7,   15,   15,   15,   15,
            14,   14,   14,   13,   13,   13,   16,   16,   16,   17,
            17,   17,   12,   12,   12,    8,    8,    8,    8,   19,
            19,   18,   18,   18,   18,   18,   18,   18,   18,   18,
            18,   18,   18,   18,   18,   18,   18,   18,   18,   18,
            18,   18,   20,   20,   20,   20,   21,   21,   24,   24,
            24,   24,   24,   23,   23,   10,   10,   10,   10,    9,
            9,    9,    9,   27,   27,   27,   29,   29,   29,   29,
            26,   26,   26,   26,   28,   28,   28,   28,   28,   28,
            28,   28,   28,   28,   28,   28,   28,   28,   28,   28,
            28,   28,   28,   30,   30,   30,   30,   30,   11,   11,
            11,   11,   11,   11,   31,   31,   31,   22,   22,   22,
            22,   22,   22,   22,   22,   32,   32,   32,   32,   32,
            32,   32,   33,   33,   33,   33,   25,   25,   25,
    };
    final static short yylen[] = {                            2,
            1,    1,    2,    2,    2,    1,    2,    1,    3,    2,
            1,    2,    2,    2,    1,    3,    1,    1,    9,    8,
            9,    9,    9,    6,    5,    3,    1,    2,    1,    2,
            3,    3,    3,    1,    3,    5,    2,    2,    2,    1,
            3,    5,    4,    3,    4,    4,    4,    3,    2,    2,
            1,    5,    5,    5,    5,    5,    5,    5,    5,    5,
            5,    5,    5,    5,    5,    5,    5,    5,    5,    3,
            2,    1,    3,    1,    3,    2,    2,    2,    4,    1,
            4,    3,    2,    1,    2,    3,    3,    3,    3,    5,
            4,    3,    2,    3,    3,    3,    3,    3,    3,    3,
            3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
            3,    3,    3,    3,    3,    3,    2,    3,    3,    3,
            3,    3,    3,    2,    2,    2,    2,    2,    4,    4,
            3,    3,    4,    2,    4,    4,    4,    3,    3,    1,
            1,    3,    3,    3,    3,    3,    3,    1,    3,    3,
            3,    3,    1,    1,    2,    1,    3,    3,    1,
    };
    final static short yydefred[] = {                         0,
            0,    0,   17,    0,    0,    0,    0,   18,    0,    0,
            2,    0,    6,    0,    8,    0,   11,    0,    0,    0,
            0,    4,    0,    0,    0,    0,    0,    0,   93,    0,
            134,    0,    0,    0,    0,    3,    5,    0,   15,    0,
            10,   12,   13,   14,    0,    0,    0,    0,  154,  153,
            0,    0,    0,  156,  141,    0,  148,  158,  157,    0,
            44,    0,    0,    0,    0,    0,   74,    0,    0,    0,
            0,    0,  131,  132,    0,    0,   26,    0,    9,    0,
            0,    0,    0,    0,    0,    0,    0,  155,    0,    0,
            0,    0,    0,    0,   45,   43,   70,    0,    0,    0,
            0,    0,    0,    0,   84,    0,   47,   46,    0,   50,
            0,    0,    0,   91,    0,  130,  129,  133,    0,    0,
            0,    0,    0,   16,    0,    0,    0,  151,  152,    0,
            0,    0,    0,    0,    0,  149,  146,  150,  147,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,   73,   85,    0,   77,  104,  103,  102,
            101,    0,    0,   90,    0,   39,    0,    0,   38,   37,
            25,    0,    0,  137,  136,  135,    0,   68,   62,   56,
            67,   61,   55,   66,   60,   54,   69,   63,   57,   64,
            58,   52,   65,   59,   53,    0,    0,    0,    0,    0,
            0,    0,  117,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,   24,    0,    0,   42,    0,    0,
            0,    0,    0,    0,    0,   96,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,   95,   94,   33,   32,   31,   27,    0,    0,    0,
            0,    0,  128,    0,  127,  124,  126,  125,    0,    0,
            20,   28,    0,    0,    0,    0,   36,    0,   80,   98,
            99,    0,   97,   23,   22,    0,   19,    0,    0,    0,
            0,   81,   79,
    };
    final static short yydgoto[] = {                          9,
            10,  247,   12,   13,   52,   40,  248,   16,   17,   18,
            19,   20,  122,  168,  249,  123,   62,   28,   68,   69,
            110,   53,  106,  270,   54,   72,  164,  165,  226,  227,
            55,   56,   57,
    };
    final static short yysindex[] = {                       542,
            -22,  -32,    0,  -31,  -27,  -37, -230,    0,    0,  562,
            0,   11,    0, -104,    0,   22,    0,   47,   49,   53,
            -39,    0,   73,  -91,  -16,    0,   85, -102,    0,  -73,
            0,   21,  -36,  -12,   34,    0,    0,   67,    0,  105,
            0,    0,    0,    0,   73,   88,  126, -113,    0,    0,
            -99,  140,  113,    0,    0,   13,    0,    0,    0,  169,
            0,  -34,  126,  516,   34, -143,    0, -147,  -74,  138,
            12,  -49,    0,    0,   18,  -61,    0,  -35,    0,  -52,
            113,  126,  113,  -30,  -30,  -21,  -21,    0,   91,  146,
            198,  204,  209,  -33,    0,    0,    0,  103,  115,  119,
            127,  131,  143,   34,    0,  443,    0,    0,  -67,    0,
            20,  186, -226,    0,  -71,    0,    0,    0,    9,   52,
            -45,   36,  427,    0,  132,   13,   13,    0,    0,   43,
            -25,  132,   13,  132,   13,    0,    0,    0,    0,  435,
            538,  -10,  801,    2,  808,    3,  815,    8,  822,   48,
            829,   60,   34,    0,    0,   34,    0,    0,    0,    0,
            0,  294,  396,    0,  -42,    0,  383,  357,    0,    0,
            0, -222, -180,    0,    0,    0,  226,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,   73,   73,   73,   73, -199,
            73,   73,    0,  155,  158,  161,  173,  185,  190, -199,
            -199,  206, -220,  562,    0, -111,  455,    0,  113,  113,
            113,  113,  -41, -215, -214,    0,   63,  113,  113,  126,
            113,  126,  113,  126,  113,  126,  113,  126,  113,  126,
            113,    0,    0,    0,    0,    0,    0,    0,  334,  562,
            574, -180,    0,  362,    0,    0,    0,    0,  362,  473,
            0,    0,    0,  413,  -29,  426,    0, -132,    0,    0,
            0,   34,    0,    0,    0,   34,    0,   34,  533,   34,
            -40,    0,    0,
    };
    final static short yyrindex[] = {                         0,
            0,  384,    0,    0,    0,    0,    0,    0,    0,  491,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,  -55,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,  437,
            0,    0,    0,    0,    0,    0,    0,  447,    0,    0,
            0,    0,  442,    0,    0,  452,    0,    0,    0,   70,
            0,    0,  510,    0,  445,    0,    0,    0, -116,   14,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            446,  449,  451,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0, -164,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,   76,    0,    0,  457,  477,    0,    0,    0,
            0,  482,  487,  507,  512,    0,    0,    0,    0,   78,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0, -162,    0,    0, -115,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,   81,    0,    6,    7,
            64,   83,    0,    0,    0,    0,    0,  108,  111,  118,
            122,  123,  134,  135,  139,  151,  298,  367,  368,  375,
            394,    0,    0,    0,    0,    0,    0,  347,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,  378,    0,    0,    0,    0,    0,    0,    0,
            0,   26,    0,    0,    0,  390,    0,   38,    0,   50,
            0,    0,    0,
    };
    final static short yygindex[] = {                         0,
            0,   61,    0,  -26,   72,    0,   69,    0,    0,    0,
            0,    0,    0,  341,  219, -150,    0,    0,    0,  412,
            0,  714,  256,  214,    1,    0,    0,    0,  267,    0,
            0,   44,   51,
    };
    final static int YYTABLESIZE=923;
    static short yytable[];
    static { yytable();}
    static void yytable(){
        yytable = new short[]{                        254,
                21,   67,   33,   49,   76,  120,   96,   25,   27,  115,
                21,  251,   30,   92,   51,  176,  211,   90,  283,   91,
                66,   46,  217,   51,   61,  100,   34,   78,   21,  160,
                180,   23,   90,  215,   91,  245,   22,   83,   23,  105,
                255,  257,  183,  186,   90,   90,   91,   91,  189,   82,
                90,  161,   91,  167,   92,   66,  223,  246,  117,   93,
                11,   73,  256,  258,  122,  121,   21,   72,   15,   37,
                36,   14,  113,  224,  225,  119,  172,    3,   15,  155,
                41,   14,   67,  174,   86,   84,    8,   85,  192,   87,
                90,   76,   91,   75,   23,  275,   76,   76,   75,   75,
                195,  267,   90,  260,   91,   42,   21,   43,  107,   21,
                40,   44,  104,    2,  108,    4,   34,   51,   41,    5,
                6,   35,  120,  278,    2,   79,    4,  126,  127,   51,
                5,    6,   51,  133,  135,   51,  128,  129,   92,   51,
                78,  123,  137,  139,  250,   51,   78,   51,   80,  121,
                100,   38,   39,   65,    2,   90,    4,   91,   24,   51,
                5,    6,   83,   51,   58,   59,  118,   86,   84,  119,
                85,   51,   87,   86,   82,   51,  115,   88,   87,   89,
                109,  114,   70,   71,  162,  163,  109,   51,  156,    2,
                51,    4,  108,  113,  118,    5,    6,  107,  111,   51,
                72,   72,   51,   72,  124,   51,  114,   72,   72,  116,
                169,  170,   94,  210,   21,  282,   45,   51,   31,   74,
                119,   95,    3,  140,   26,  125,   48,  269,   29,   51,
                175,    8,  269,  269,   51,   48,  253,   32,   75,   24,
                60,  105,   51,   77,  121,  179,   49,   50,   51,   21,
                21,   21,  155,   51,   21,   49,   50,  182,  185,   21,
                21,  122,  121,  188,   21,  166,   21,  112,   21,   92,
                92,   92,   92,  116,   92,   92,   92,   92,   92,   21,
                92,  100,  100,  100,  100,   14,  100,  100,  100,  100,
                100,  171,  100,   83,   83,   83,   83,  158,   83,   83,
                83,   83,   83,  191,   83,   82,   82,   82,   82,  262,
                82,   82,   82,   82,   82,  194,   82,  263,  259,  120,
                14,   14,   14,  121,  262,   40,  262,  167,   47,   48,
                3,   34,  263,   41,  263,   14,   35,   14,  123,    8,
                63,   48,    3,   82,   48,    3,  130,   48,    3,   49,
                50,    8,  200,  201,    8,  202,  110,    8,  141,   48,
                3,   49,   50,  118,   49,   50,  119,   49,   50,    8,
                143,   48,    3,  115,  145,   48,    3,  109,  114,   49,
                50,    8,  147,   48,    3,    8,  149,   48,    3,  108,
                113,   49,   50,    8,  107,   49,   50,    8,  151,   48,
                3,  132,   48,   49,   50,    8,  116,   49,   50,    8,
                230,   48,    3,  232,   48,    3,  234,   48,    3,   49,
                50,    8,   49,   50,    8,  111,  105,    8,  236,   48,
                3,   49,   50,  112,   49,   50,    8,   49,   50,    8,
                238,   48,    3,  213,  159,  240,   48,    3,   21,   49,
                50,    8,  106,  134,   48,  208,    8,  209,  261,  136,
                48,   49,   50,  159,  138,   48,   49,   50,  264,  266,
                173,   29,  271,  273,   49,   50,  242,  243,  177,  214,
                49,   50,  218,  244,  268,   49,   50,  159,  159,  159,
                1,  159,  140,  159,  140,    7,  140,  143,  252,  143,
                89,  143,   30,   48,   88,  159,  159,   87,  159,   86,
                140,  140,  216,  140,   21,  143,  143,  145,  143,  145,
                157,  145,  142,  279,  142,    0,  142,  138,    0,  138,
                0,  138,    0,    0,    0,  145,  145,  274,  145,    0,
                142,  142,    0,  142,    0,  138,  138,  144,  138,  144,
                277,  144,  139,  110,  139,    0,  139,    0,   90,    0,
                91,  196,  197,  198,  199,  144,  144,  154,  144,    0,
                139,  139,    0,  139,    0,  102,    0,  103,  178,   86,
                84,    0,   85,    0,   87,    0,    0,    0,    0,   35,
                2,    3,    4,    0,    0,  268,    5,    6,    7,    0,
                8,    0,   29,   29,   29,   29,    0,    0,    0,   29,
                29,   29,    0,   29,    0,    0,    0,   35,    2,    0,
                4,    0,  111,  105,    5,    6,    0,    0,    0,    0,
                112,    0,   71,   30,   30,   30,   30,    0,  212,  159,
                30,   30,   30,    0,   30,   21,   21,   21,   21,  106,
                0,  203,   21,   21,   21,    0,   21,  281,    0,    0,
                0,    0,    0,  204,  205,  206,  207,    0,   35,    2,
                3,    4,    0,    0,    0,    5,    6,    7,    0,    8,
                0,  276,    2,    3,    4,    0,    0,    0,    5,    6,
                7,    0,    8,    0,    0,    0,    0,    0,  153,    2,
                0,    4,  159,    0,    0,    5,    6,  140,    0,    0,
                0,    0,  143,    0,  159,  159,  159,  159,    0,  140,
                140,  140,  140,    0,  143,  143,  143,  143,  272,    2,
                0,    4,  145,    0,    0,    5,    6,  142,    0,    0,
                64,    0,  138,    0,  145,  145,  145,  145,    0,  142,
                142,  142,  142,    0,  138,  138,  138,  138,   81,   83,
                0,    0,  144,    0,    0,   71,   71,  139,   71,    0,
                0,   97,   71,   71,  144,  144,  144,  144,    0,  139,
                139,  139,  139,   98,   99,  100,  101,    0,  280,    2,
                0,    4,    0,    0,    0,    5,    6,    1,    2,    3,
                4,    0,  131,    0,    5,    6,    7,    0,    8,    0,
                0,  142,  144,  146,  148,  150,  152,   35,    2,    3,
                4,    0,    0,    0,    5,    6,    7,    0,    8,  265,
                2,    3,    4,    0,    0,    0,    5,    6,    7,    0,
                8,  181,   86,   84,    0,   85,    0,   87,  184,   86,
                84,    0,   85,    0,   87,  187,   86,   84,    0,   85,
                0,   87,  190,   86,   84,    0,   85,    0,   87,  193,
                86,   84,    0,   85,    0,   87,    0,    0,    0,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,  219,
                220,  221,  222,    0,  228,  229,    0,  231,  233,  235,
                237,  239,  241,
        };
    }
    static short yycheck[];
    static { yycheck(); }
    static void yycheck() {
        yycheck = new short[] {                         41,
                0,   28,   40,   59,   41,   41,   41,   40,   40,   59,
                10,  123,   40,    0,   45,   41,   59,   43,   59,   45,
                123,   61,  173,   45,   41,    0,  257,   40,   28,  256,
                41,   61,   43,  256,   45,  256,   59,    0,   61,   66,
                256,  256,   41,   41,   43,   43,   45,   45,   41,    0,
                43,  278,   45,  276,   42,  123,  256,  278,   41,   47,
                0,   41,  278,  278,   59,   59,   66,  123,    0,   59,
                10,    0,   61,  273,  274,  256,   41,  258,   10,  106,
                59,   10,  109,   41,   42,   43,  267,   45,   41,   47,
                43,  256,   45,  256,   61,  125,  261,  262,  261,  262,
                41,  252,   43,   41,   45,   59,  106,   59,  256,  109,
                41,   59,  256,  257,  262,  259,   41,   45,   41,  263,
                264,   41,   59,  256,  257,   59,  259,   84,   85,   45,
                263,  264,   45,   90,   91,   45,   86,   87,  125,  256,
                256,   59,   92,   93,  256,  262,  262,   45,   44,   78,
                125,  256,  257,  256,  257,   43,  259,   45,  272,   45,
                263,  264,  125,   45,  256,  257,   59,   42,   43,   59,
                45,   45,   47,   42,  125,   45,   59,  277,   47,   40,
                59,   59,  256,  257,  256,  257,  261,   45,  256,  257,
                45,  259,   59,   59,  256,  263,  264,   59,   61,   45,
                256,  257,   45,  259,  257,   45,  256,  263,  264,   59,
                256,  257,   44,  256,  214,  256,  256,   45,  256,  256,
                256,  256,  258,  257,  256,  256,  257,  254,  256,   45,
                256,  267,  259,  260,   45,  257,  278,  275,  275,  272,
                257,  268,   45,  256,  173,  256,  277,  278,   45,  249,
                250,  251,  279,   45,  254,  277,  278,  256,  256,  259,
                260,  256,  256,  256,  264,  257,  266,  256,  268,  256,
                257,  258,  259,  256,  261,  262,  263,  264,  265,  279,
                267,  256,  257,  258,  259,  214,  261,  262,  263,  264,
                265,  256,  267,  256,  257,  258,  259,  278,  261,  262,
                263,  264,  265,  256,  267,  256,  257,  258,  259,  249,
                261,  262,  263,  264,  265,  256,  267,  249,  256,  256,
                249,  250,  251,  252,  264,  256,  266,  276,  256,  257,
                258,  256,  264,  256,  266,  264,  256,  266,  256,  267,
                256,  257,  258,  256,  257,  258,  256,  257,  258,  277,
                278,  267,   59,   60,  267,   62,   59,  267,  256,  257,
                258,  277,  278,  256,  277,  278,  256,  277,  278,  267,
                256,  257,  258,  256,  256,  257,  258,  256,  256,  277,
                278,  267,  256,  257,  258,  267,  256,  257,  258,  256,
                256,  277,  278,  267,  256,  277,  278,  267,  256,  257,
                258,  256,  257,  277,  278,   59,  256,  277,  278,  267,
                256,  257,  258,  256,  257,  258,  256,  257,  258,  277,
                278,  267,  277,  278,  267,   59,   59,  267,  256,  257,
                258,  277,  278,   59,  277,  278,   59,  277,  278,  267,
                256,  257,  258,   61,   61,  256,  257,  258,   59,  277,
                278,  267,   59,  256,  257,   60,  267,   62,  125,  256,
                257,  277,  278,  278,  256,  257,  277,  278,  250,  251,
                44,  125,  259,  260,  277,  278,  210,  211,   44,  123,
                277,  278,  257,  278,  123,  277,  278,   41,   42,   43,
                0,   45,   41,   47,   43,   59,   45,   41,   44,   43,
                59,   45,  125,   59,   59,   59,   60,   59,   62,   59,
                59,   60,  172,   62,  125,   59,   60,   41,   62,   43,
                109,   45,   41,  268,   43,   -1,   45,   41,   -1,   43,
                -1,   45,   -1,   -1,   -1,   59,   60,  125,   62,   -1,
                59,   60,   -1,   62,   -1,   59,   60,   41,   62,   43,
                125,   45,   41,  256,   43,   -1,   45,   -1,   43,   -1,
                45,  268,  269,  270,  271,   59,   60,  125,   62,   -1,
                59,   60,   -1,   62,   -1,   60,   -1,   62,   41,   42,
                43,   -1,   45,   -1,   47,   -1,   -1,   -1,   -1,  256,
                257,  258,  259,   -1,   -1,  123,  263,  264,  265,   -1,
                267,   -1,  256,  257,  258,  259,   -1,   -1,   -1,  263,
                264,  265,   -1,  267,   -1,   -1,   -1,  256,  257,   -1,
                259,   -1,  256,  256,  263,  264,   -1,   -1,   -1,   -1,
                256,   -1,  123,  256,  257,  258,  259,   -1,  256,  256,
                263,  264,  265,   -1,  267,  256,  257,  258,  259,  256,
                -1,  256,  263,  264,  265,   -1,  267,  125,   -1,   -1,
                -1,   -1,   -1,  268,  269,  270,  271,   -1,  256,  257,
                258,  259,   -1,   -1,   -1,  263,  264,  265,   -1,  267,
                -1,  256,  257,  258,  259,   -1,   -1,   -1,  263,  264,
                265,   -1,  267,   -1,   -1,   -1,   -1,   -1,  256,  257,
                -1,  259,  256,   -1,   -1,  263,  264,  256,   -1,   -1,
                -1,   -1,  256,   -1,  268,  269,  270,  271,   -1,  268,
                269,  270,  271,   -1,  268,  269,  270,  271,  256,  257,
                -1,  259,  256,   -1,   -1,  263,  264,  256,   -1,   -1,
                27,   -1,  256,   -1,  268,  269,  270,  271,   -1,  268,
                269,  270,  271,   -1,  268,  269,  270,  271,   45,   46,
                -1,   -1,  256,   -1,   -1,  256,  257,  256,  259,   -1,
                -1,  256,  263,  264,  268,  269,  270,  271,   -1,  268,
                269,  270,  271,  268,  269,  270,  271,   -1,  256,  257,
                -1,  259,   -1,   -1,   -1,  263,  264,  256,  257,  258,
                259,   -1,   89,   -1,  263,  264,  265,   -1,  267,   -1,
                -1,   98,   99,  100,  101,  102,  103,  256,  257,  258,
                259,   -1,   -1,   -1,  263,  264,  265,   -1,  267,  256,
                257,  258,  259,   -1,   -1,   -1,  263,  264,  265,   -1,
                267,   41,   42,   43,   -1,   45,   -1,   47,   41,   42,
                43,   -1,   45,   -1,   47,   41,   42,   43,   -1,   45,
                -1,   47,   41,   42,   43,   -1,   45,   -1,   47,   41,
                42,   43,   -1,   45,   -1,   47,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  196,
                197,  198,  199,   -1,  201,  202,   -1,  204,  205,  206,
                207,  208,  209,
        };
    }
    final static short YYFINAL=9;
    final static short YYMAXTOKEN=278;
    final static String yyname[] = {
            "end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
            "'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
            "'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
            "'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,"ID","ULONGINT","IF","THEN","ELSE","END_IF",
            "FOR","OUT","PROC","RETURN","DOUBLE","MENOR_IGUAL","MAYOR_IGUAL","IGUAL",
            "DISTINTO","PUNTO_PUNTO","UP","DOWN","CADENA","NA","NRO_DOUBLE","NRO_ULONGINT",
    };
    final static String yyrule[] = {
            "$accept : programa",
            "programa : lista_sentencias",
            "lista_sentencias : sentencia",
            "lista_sentencias : lista_sentencias sentencia",
            "lista_sentencias : error ';'",
            "sentencia : sent_declarativa ';'",
            "sentencia : sent_ejecutable",
            "sent_declarativa : tipo lista_variables",
            "sent_declarativa : procedimiento",
            "sent_declarativa : tipo error ';'",
            "sent_ejecutable : sentencia_if ';'",
            "sent_ejecutable : sentencia_control",
            "sent_ejecutable : asignacion ';'",
            "sent_ejecutable : imprimir ';'",
            "sent_ejecutable : llamado_PROC ';'",
            "lista_variables : ID",
            "lista_variables : lista_variables ',' ID",
            "tipo : ULONGINT",
            "tipo : DOUBLE",
            "procedimiento : PROC ID '(' lista_parametros ')' asignacion_NA '{' cuerpo_procedimiento '}'",
            "procedimiento : PROC ID '(' ')' asignacion_NA '{' cuerpo_procedimiento '}'",
            "procedimiento : PROC ID '(' lista_parametros ')' asignacion_NA '{' cuerpo_procedimiento error",
            "procedimiento : PROC ID '(' lista_parametros ')' asignacion_NA '{' error '}'",
            "procedimiento : PROC ID '(' lista_parametros ')' asignacion_NA error cuerpo_procedimiento '}'",
            "procedimiento : PROC ID '(' lista_parametros ')' error",
            "procedimiento : PROC ID '(' lista_parametros error",
            "procedimiento : PROC ID error",
            "cuerpo_procedimiento : sentencia",
            "cuerpo_procedimiento : cuerpo_procedimiento sentencia",
            "cuerpo_procedimiento : procedimiento",
            "cuerpo_procedimiento : cuerpo_procedimiento procedimiento",
            "asignacion_NA : NA '=' NRO_ULONGINT",
            "asignacion_NA : NA '=' error",
            "asignacion_NA : NA error NRO_ULONGINT",
            "lista_parametros : parametro",
            "lista_parametros : parametro ',' parametro",
            "lista_parametros : parametro ',' parametro ',' parametro",
            "parametro : tipo ID",
            "parametro : tipo error",
            "parametro : error ID",
            "parametro_invocacion : ID",
            "parametro_invocacion : ID ',' ID",
            "parametro_invocacion : ID ',' ID ',' ID",
            "llamado_PROC : ID '(' parametro_invocacion ')'",
            "llamado_PROC : ID '(' ')'",
            "llamado_PROC : ID '(' parametro_invocacion error",
            "sentencia_if : IF condicion_IF cuerpo END_IF",
            "sentencia_if : IF condicion_IF cuerpo error",
            "sentencia_if : IF condicion_IF error",
            "sentencia_if : IF error",
            "cuerpo : bloque_IF bloque_else",
            "cuerpo : bloque_IF",
            "condicion_IF : '(' expresion '<' expresion ')'",
            "condicion_IF : '(' expresion '>' expresion ')'",
            "condicion_IF : '(' expresion IGUAL expresion ')'",
            "condicion_IF : '(' expresion MAYOR_IGUAL expresion ')'",
            "condicion_IF : '(' expresion MENOR_IGUAL expresion ')'",
            "condicion_IF : '(' expresion DISTINTO expresion ')'",
            "condicion_IF : '(' expresion '<' expresion error",
            "condicion_IF : '(' expresion '>' expresion error",
            "condicion_IF : '(' expresion IGUAL expresion error",
            "condicion_IF : '(' expresion MAYOR_IGUAL expresion error",
            "condicion_IF : '(' expresion MENOR_IGUAL expresion error",
            "condicion_IF : '(' expresion DISTINTO expresion error",
            "condicion_IF : '(' expresion '<' error ')'",
            "condicion_IF : '(' expresion '>' error ')'",
            "condicion_IF : '(' expresion IGUAL error ')'",
            "condicion_IF : '(' expresion MAYOR_IGUAL error ')'",
            "condicion_IF : '(' expresion MENOR_IGUAL error ')'",
            "condicion_IF : '(' expresion DISTINTO error ')'",
            "condicion_IF : '(' expresion error",
            "condicion_IF : '(' error",
            "condicion_IF : error",
            "bloque_IF : '{' cuerpo_ejecutable '}'",
            "bloque_IF : sent_ejecutable",
            "bloque_IF : '{' cuerpo_ejecutable error",
            "bloque_IF : '{' error",
            "bloque_else : ELSE bloque_IF",
            "bloque_else : ELSE error",
            "bloque_FOR : '{' cuerpo_ejecutable '}' ';'",
            "bloque_FOR : sent_ejecutable",
            "bloque_FOR : '{' cuerpo_ejecutable '}' error",
            "bloque_FOR : '{' cuerpo_ejecutable error",
            "bloque_FOR : '{' error",
            "cuerpo_ejecutable : sent_ejecutable",
            "cuerpo_ejecutable : cuerpo_ejecutable sent_ejecutable",
            "asignacion : tipo_ID '=' expresion",
            "asignacion : tipo_ID '=' error",
            "asignacion : tipo_ID error expresion",
            "asignacion : error '=' expresion",
            "sentencia_control : FOR '(' asignacion_FOR ';' condicion_FOR_dos",
            "sentencia_control : FOR '(' asignacion_FOR error",
            "sentencia_control : FOR '(' error",
            "sentencia_control : FOR error",
            "condicion_FOR_dos : comparacion_FOR ';' condicion_FOR_tres",
            "condicion_FOR_dos : comparacion_FOR error condicion_FOR_tres",
            "condicion_FOR_dos : error ';' condicion_FOR_tres",
            "condicion_FOR_tres : incr_decr ')' bloque_FOR",
            "condicion_FOR_tres : error ')' bloque_FOR",
            "condicion_FOR_tres : incr_decr error bloque_FOR",
            "condicion_FOR_tres : incr_decr ')' error",
            "asignacion_FOR : ID '=' NRO_ULONGINT",
            "asignacion_FOR : ID '=' error",
            "asignacion_FOR : ID error NRO_ULONGINT",
            "asignacion_FOR : error '=' NRO_ULONGINT",
            "comparacion_FOR : ID '<' expresion",
            "comparacion_FOR : ID '>' expresion",
            "comparacion_FOR : ID IGUAL expresion",
            "comparacion_FOR : ID MAYOR_IGUAL expresion",
            "comparacion_FOR : ID MENOR_IGUAL expresion",
            "comparacion_FOR : ID DISTINTO expresion",
            "comparacion_FOR : ID '<' error",
            "comparacion_FOR : ID '>' error",
            "comparacion_FOR : ID IGUAL error",
            "comparacion_FOR : ID MAYOR_IGUAL error",
            "comparacion_FOR : ID MENOR_IGUAL error",
            "comparacion_FOR : ID DISTINTO error",
            "comparacion_FOR : ID error",
            "comparacion_FOR : error '<' expresion",
            "comparacion_FOR : error '>' expresion",
            "comparacion_FOR : error IGUAL expresion",
            "comparacion_FOR : error MAYOR_IGUAL expresion",
            "comparacion_FOR : error MENOR_IGUAL expresion",
            "comparacion_FOR : error DISTINTO expresion",
            "incr_decr : UP NRO_ULONGINT",
            "incr_decr : DOWN NRO_ULONGINT",
            "incr_decr : DOWN error",
            "incr_decr : UP error",
            "incr_decr : error NRO_ULONGINT",
            "imprimir : OUT '(' CADENA ')'",
            "imprimir : OUT '(' CADENA error",
            "imprimir : OUT CADENA ')'",
            "imprimir : OUT '(' error",
            "imprimir : OUT '(' ')' error",
            "imprimir : OUT error",
            "conversion_explicita : tipo '(' expresion ')'",
            "conversion_explicita : tipo '(' expresion error",
            "conversion_explicita : tipo '(' error ')'",
            "expresion : expresion '+' termino",
            "expresion : expresion '-' termino",
            "expresion : termino",
            "expresion : conversion_explicita",
            "expresion : expresion '+' error",
            "expresion : error '+' termino",
            "expresion : expresion '-' error",
            "expresion : error '-' termino",
            "termino : termino '*' factor",
            "termino : termino '/' factor",
            "termino : factor",
            "termino : termino '*' error",
            "termino : termino '/' error",
            "termino : error '*' factor",
            "termino : error '/' factor",
            "factor : NRO_ULONGINT",
            "factor : NRO_DOUBLE",
            "factor : '-' NRO_DOUBLE",
            "factor : tipo_ID",
            "tipo_ID : ID PUNTO_PUNTO ID",
            "tipo_ID : ID PUNTO_PUNTO error",
            "tipo_ID : ID",
    };

//#line 574 "G08 - Gramatica - 21102020.y"


    private LexerAnalyzer la;
    private List<String> errors;
    private List<String> rules;
    private SyntacticTree syntacticTree;

    public Parser(LexerAnalyzer la){
        this.errors = new ArrayList<String>();
        this.rules = new ArrayList<String>();
        this.la = la;
    }

    public int yylex(){
        Token token = this.la.getNextToken();
        yylval = new ParserVal(la.getAttribute(token.getLexema()));
        return token.getId();
    }

    public void yyerror(String error){

    }

    public int yyparser(){
        return yyparse();
    }

    private void addError(String msg) {
        errors.add(msg);
    }

    private void addRule(String msg) {
        rules.add(msg);
    }

    public List<String> getRules(){
        List<String> rules = new ArrayList<>(this.rules);
        return rules;
    }

    public List<String> getErrors(){
        List<String> errors = new ArrayList<>(this.errors);
        return errors;
    }

    public void printSyntacticTree(){
        this.syntacticTree.printTree(this.syntacticTree);
    }
    //#line 699 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
    void yylexdebug(int state,int ch)
    {
        String s=null;
        if (ch < 0) ch=0;
        if (ch <= YYMAXTOKEN) //check index bounds
            s = yyname[ch];    //now get it
        if (s==null)
            s = "illegal-symbol";
        debug("state "+state+", reading "+ch+" ("+s+")");
    }





    //The following are now global, to aid in error reporting
    int yyn;       //next next thing to do
    int yym;       //
    int yystate;   //current parsing state from state table
    String yys;    //current token string


    //###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
    int yyparse()
    {
        boolean doaction;
        init_stacks();
        yynerrs = 0;
        yyerrflag = 0;
        yychar = -1;          //impossible char forces a read
        yystate=0;            //initial state
        state_push(yystate);  //save it
        val_push(yylval);     //save empty value
        while (true) //until parsing is done, either correctly, or w/error
        {
            doaction=true;
            if (yydebug) debug("loop");
            //#### NEXT ACTION (from reduction table)
            for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
            {
                if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
                if (yychar < 0)      //we want a char?
                {
                    yychar = yylex();  //get next token
                    if (yydebug) debug(" next yychar:"+yychar);
                    //#### ERROR CHECK ####
                    if (yychar < 0)    //it it didn't work/error
                    {
                        yychar = 0;      //change it to default string (no -1!)
                        if (yydebug)
                            yylexdebug(yystate,yychar);
                    }
                }//yychar<0
                yyn = yysindex[yystate];  //get amount to shift by (shift index)
                if ((yyn != 0) && (yyn += yychar) >= 0 &&
                        yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
                {
                    if (yydebug)
                        debug("state "+yystate+", shifting to state "+yytable[yyn]);
                    //#### NEXT STATE ####
                    yystate = yytable[yyn];//we are in a new state
                    state_push(yystate);   //save it
                    val_push(yylval);      //push our lval as the input for next rule
                    yychar = -1;           //since we have 'eaten' a token, say we need another
                    if (yyerrflag > 0)     //have we recovered an error?
                        --yyerrflag;        //give ourselves credit
                    doaction=false;        //but don't process yet
                    break;   //quit the yyn=0 loop
                }

                yyn = yyrindex[yystate];  //reduce
                if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
                        yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
                {   //we reduced!
                    if (yydebug) debug("reduce");
                    yyn = yytable[yyn];
                    doaction=true; //get ready to execute
                    break;         //drop down to actions
                }
                else //ERROR RECOVERY
                {
                    if (yyerrflag==0)
                    {
                        yyerror("syntax error");
                        yynerrs++;
                    }
                    if (yyerrflag < 3) //low error count?
                    {
                        yyerrflag = 3;
                        while (true)   //do until break
                        {
                            if (stateptr<0)   //check for under & overflow here
                            {
                                yyerror("stack underflow. aborting...");  //note lower case 's'
                                return 1;
                            }
                            yyn = yysindex[state_peek(0)];
                            if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
                            {
                                if (yydebug)
                                    debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
                                yystate = yytable[yyn];
                                state_push(yystate);
                                val_push(yylval);
                                doaction=false;
                                break;
                            }
                            else
                            {
                                if (yydebug)
                                    debug("error recovery discarding state "+state_peek(0)+" ");
                                if (stateptr<0)   //check for under & overflow here
                                {
                                    yyerror("Stack underflow. aborting...");  //capital 'S'
                                    return 1;
                                }
                                state_pop();
                                val_pop();
                            }
                        }
                    }
                    else            //discard this token
                    {
                        if (yychar == 0)
                            return 1; //yyabort
                        if (yydebug)
                        {
                            yys = null;
                            if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
                            if (yys == null) yys = "illegal-symbol";
                            debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
                        }
                        yychar = -1;  //read another
                    }
                }//end error recovery
            }//yyn=0 loop
            if (!doaction)   //any reason not to proceed?
                continue;      //skip action
            yym = yylen[yyn];          //get count of terminals on rhs
            if (yydebug)
                debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
            if (yym>0)                 //if count of rhs not 'nil'
                yyval = val_peek(yym-1); //get current semantic value
            yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
            switch(yyn)
            {
//########## USER-SUPPLIED ACTIONS ##########
                case 1:
//#line 27 "G08 - Gramatica - 21102020.y"
                {
                    syntacticTree = val_peek(0).tree;
                }
                break;
                case 2:
//#line 33 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 3:
//#line 37 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = new SyntacticTree(val_peek(1).tree, val_peek(0).tree, "LISTA SENTENCIAS");
                }
                break;
                case 5:
//#line 44 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = val_peek(1).tree;
                }
                break;
                case 6:
//#line 48 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 7:
//#line 54 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia declarativa - Variable/s.");
                    yyval = val_peek(0);
                    yyval.attribute.setUse(yyval.attributes, Use.variable);
                    yyval.attribute.setType(yyval.attributes, new Type(val_peek(1).type.getName()));
                }
                break;
                case 8:
//#line 61 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia declarativa - Procedimiento.");
                    yyval.tree = val_peek(0).tree;
                    yyval = val_peek(0);
                }
                break;
                case 9:
//#line 67 "G08 - Gramatica - 21102020.y"
                {addError("Linea "+ la.getNroLinea() +": falta definir la/s variable/s."); }
                break;
                case 10:
//#line 71 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = val_peek(1).tree;
                }
                break;
                case 11:
//#line 75 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 12:
//#line 79 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = new SyntacticTree(val_peek(1).tree, "ASIGNACION");
                }
                break;
                case 13:
//#line 83 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = val_peek(1).tree;
                }
                break;
                case 14:
//#line 87 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = val_peek(1).tree;
                }
                break;
                case 15:
//#line 95 "G08 - Gramatica - 21102020.y"
                {
                    yyval.attributes = new ArrayList<>();
                    yyval.attributes.add(val_peek(0).attribute);

                }
                break;
                case 16:
//#line 101 "G08 - Gramatica - 21102020.y"
                {
                    yyval = val_peek(2);
                    yyval.attributes.add(val_peek(2).attribute);
                }
                break;
                case 17:
//#line 108 "G08 - Gramatica - 21102020.y"
                {
                    yyval.type = Type.ULONGINT;
                }
                break;
                case 18:
//#line 112 "G08 - Gramatica - 21102020.y"
                {
                    yyval.type = Type.DOUBLE;
                }
                break;
                case 19:
//#line 121 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": PROCEDIMIENTO con PARÁMETROS.");
                    yyval.tree = new SyntacticTree(val_peek(3).tree, val_peek(1).tree,"PROCEDIMIENTO CON PARAMETROS");
                    yyval.attribute = val_peek(7).attribute;
                    yyval.attribute.setUse(Use.nombre_procedimiento);
                    System.out.println("Uso: " + yyval.attribute.getUse());
                }
                break;
                case 20:
//#line 129 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": PROCEDIMIENTO sin PARÁMETROS.");
                    yyval.tree = new SyntacticTree(val_peek(3).tree, val_peek(1).tree,"PROCEDIMIENTO SIN PARAMETROS");
                    yyval.attribute = val_peek(6).attribute;
                    yyval.attribute.setUse(Use.nombre_procedimiento);
                    System.out.println("Uso: " + yyval.attribute.getUse());
                }
                break;
                case 21:
//#line 137 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera }");}
                break;
                case 22:
//#line 138 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera CUERPO DE PROCEDIMIENTO");}
                break;
                case 23:
//#line 139 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera {");}
                break;
                case 24:
//#line 140 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": falta asignacion_NA");}
                break;
                case 25:
//#line 141 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": falta ')' ");}
                break;
                case 26:
//#line 142 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": falta '(' ");}
                break;
                case 27:
//#line 146 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = new SyntacticTree(val_peek(0).tree, "CUERPO PROCEDIMIENTO SENTENCIA");
                }
                break;
                case 28:
//#line 150 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = new SyntacticTree(val_peek(1).tree, val_peek(0).tree, "CUERPO PROCEDIMIENTO SENTENCIA COMPUESTA");
                }
                break;
                case 31:
//#line 158 "G08 - Gramatica - 21102020.y"
                {
                    /*$$.tree = new SyntacticTree(new SyntacticTree(null, null, $3.attribute.getLexeme()), "NA");
                     */
                    val_peek(0).attribute.setUse(Use.numero_anidamiento);
                }
                break;
                case 32:
//#line 163 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera NRO_ULONGINT en ASIGNACION NA"); }
                break;
                case 33:
//#line 164 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera = en ASIGNACION NA"); }
                break;
                case 37:
//#line 174 "G08 - Gramatica - 21102020.y"
                {
                    yyval = val_peek(0);
                    yyval.attribute.setUse(Use.nombre_parametro);
                    yyval.attribute.setType(new Type(val_peek(1).type.getName()));
                }
                break;
                case 38:
//#line 180 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera ID en PARAMETRO"); }
                break;
                case 39:
//#line 181 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera TIPO en PARAMETRO"); }
                break;
                case 40:
//#line 185 "G08 - Gramatica - 21102020.y"
                {
                    /*$$.tree = new SyntacticTree(new SyntacticTree(null, null, $1.attribute.getLexeme()), "PARAMETROS INVOCACION");
                     */
                }
                break;
                case 41:
//#line 189 "G08 - Gramatica - 21102020.y"
                {
                    /*$$.tree = new SyntacticTree(new SyntacticTree(null, null, $1.attribute.getLexeme()), new SyntacticTree(null, null, $1.attribute.getLexeme()), "PARAMETROS INVOCACION");
                     */
                }
                break;
                case 43:
//#line 197 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": LLAMADO A PROCEDIMIENTO CON PARÁMETROS.");
                    yyval.tree = new SyntacticTree(new SyntacticTree(null, null, val_peek(3).attribute.getLexeme()), val_peek(1).tree,"LLAMADO PROC SIN PAR");
                }
                break;
                case 44:
//#line 202 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": LLAMADO A PROCEDIMIENTO SIN PARÁMETROS.");
                    yyval.tree = new SyntacticTree(new SyntacticTree(null, null, val_peek(2).attribute.getLexeme()), "LLAMADO PROC SIN PAR");
                }
                break;
                case 45:
//#line 207 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera )"); }
                break;
                case 46:
//#line 213 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia IF.");
                    yyval.tree = new SyntacticTree(val_peek(2).tree, val_peek(1).tree, "IF");
                }
                break;
                case 47:
//#line 218 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera END_IF"); }
                break;
                case 48:
//#line 219 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera CUERPO sentencia IF"); }
                break;
                case 49:
//#line 220 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera CONDICION IF"); }
                break;
                case 50:
//#line 224 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": CUERPO IF ELSE.");
                    yyval.tree = new SyntacticTree(val_peek(1).tree, val_peek(0).tree, "CUERPO_IF_ELSE");
                }
                break;
                case 51:
//#line 229 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": CUERPO IF.");
                    yyval.tree = new SyntacticTree(val_peek(0).tree, "CUERPO_IF");
                }
                break;
                case 52:
//#line 237 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": CONDICION IF MENOR.");
                    yyval.tree = new SyntacticTree(new SyntacticTree(val_peek(3).tree, val_peek(1).tree, "<"), "COND");
                }
                break;
                case 53:
//#line 242 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": CONDICION IF MAYOR.");
                    yyval.tree = new SyntacticTree(new SyntacticTree(val_peek(3).tree, val_peek(1).tree, ">"), "COND");
                }
                break;
                case 54:
//#line 247 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": CONDICION IF IGUAL.");
                    yyval.tree = new SyntacticTree(new SyntacticTree(val_peek(3).tree, val_peek(1).tree, "=="), "COND");
                }
                break;
                case 55:
//#line 252 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": CONDICION IF MAYOR_IGUAL.");
                    yyval.tree = new SyntacticTree(new SyntacticTree(val_peek(3).tree, val_peek(1).tree, ">="), "COND");
                }
                break;
                case 56:
//#line 257 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": CONDICION IF MENOR_IGUAL.");
                    yyval.tree = new SyntacticTree(new SyntacticTree(val_peek(3).tree, val_peek(1).tree, "<="), "COND");
                }
                break;
                case 57:
//#line 262 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": CONDICION IF DISTINTO.");
                    yyval.tree = new SyntacticTree(new SyntacticTree(val_peek(3).tree, val_peek(1).tree, "!="), "COND");
                }
                break;
                case 58:
//#line 267 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera )"); }
                break;
                case 59:
//#line 268 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera )"); }
                break;
                case 60:
//#line 269 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera )"); }
                break;
                case 61:
//#line 270 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera )"); }
                break;
                case 62:
//#line 271 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera )"); }
                break;
                case 63:
//#line 272 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera )"); }
                break;
                case 64:
//#line 274 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera EXPRESION DERECHA CONDICION IF"); }
                break;
                case 65:
//#line 275 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera EXPRESION DERECHA CONDICION IF"); }
                break;
                case 66:
//#line 276 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera EXPRESION DERECHA CONDICION IF"); }
                break;
                case 67:
//#line 277 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera EXPRESION DERECHA CONDICION IF"); }
                break;
                case 68:
//#line 278 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera EXPRESION DERECHA CONDICION IF"); }
                break;
                case 69:
//#line 279 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera EXPRESION DERECHA CONDICION IF"); }
                break;
                case 70:
//#line 281 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera COMPARADOR CONDICION IF"); }
                break;
                case 71:
//#line 283 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera EXPRESION IZQUIERDA CONDICION IF"); }
                break;
                case 72:
//#line 285 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera ("); }
                break;
                case 73:
//#line 290 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": BLOQUE DE SENTENCIAS IF.");
                    yyval.tree = new SyntacticTree(val_peek(1).tree, "BLOQUE_IF");
                }
                break;
                case 74:
//#line 295 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 75:
//#line 299 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera } finalizacion BLOQUE IF");}
                break;
                case 76:
//#line 300 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera cuerpo_ejecutable BLOQUE IF");}
                break;
                case 77:
//#line 304 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": BLOQUE DE SENTENCIAS ELSE.");
                    yyval.tree = new SyntacticTree(val_peek(0).tree, "BLOQUE_ELSE");
                }
                break;
                case 78:
//#line 309 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera BLOQUE_IF");}
                break;
                case 79:
//#line 313 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": BLOQUE DE SENTENCIAS FOR.");
                    /*$$.tree = new SyntacticTree($2.tree, "BLOQUE_IF");
                     */
                }
                break;
                case 80:
//#line 318 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 81:
//#line 322 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera ; finalizacion BLOQUE FOR");}
                break;
                case 82:
//#line 323 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera } finalizacion BLOQUE FOR");}
                break;
                case 83:
//#line 324 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera cuerpo_ejecutable BLOQUE FOR");}
                break;
                case 84:
//#line 328 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 85:
//#line 332 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = new SyntacticTree(val_peek(1).tree, val_peek(0).tree, "SENTENCIA");
                }
                break;
                case 86:
//#line 338 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": ASIGNACION.");
                    yyval.tree = new SyntacticTree(val_peek(2).tree, val_peek(0).tree, "=");
                }
                break;
                case 87:
//#line 343 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera EXPRESION en ASIGNACION");}
                break;
                case 88:
//#line 344 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera =");}
                break;
                case 89:
//#line 345 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera ID en ASIGNACION");}
                break;
                case 90:
//#line 351 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia FOR.");
                    yyval.tree = new SyntacticTree(val_peek(2).tree, val_peek(0).tree, "FOR_ASIGNACION");
                }
                break;
                case 91:
//#line 356 "G08 - Gramatica - 21102020.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera ;."); }
                break;
                case 92:
//#line 357 "G08 - Gramatica - 21102020.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera asignacion FOR"); }
                break;
                case 93:
//#line 358 "G08 - Gramatica - 21102020.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera ("); }
                break;
                case 94:
//#line 362 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = new SyntacticTree(new SyntacticTree(val_peek(2).tree, "COMPARACION"), new SyntacticTree(val_peek(0).tree, "CUERPO"), "FOR");
                }
                break;
                case 95:
//#line 365 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera ;"); }
                break;
                case 96:
//#line 366 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera ;"); }
                break;
                case 97:
//#line 370 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = new SyntacticTree(val_peek(2).tree, val_peek(0).tree, "SENTENCIAS_FOR");
                }
                break;
                case 98:
//#line 373 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera UP/DOWN NRO_ULONGINT"); }
                break;
                case 99:
//#line 374 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera )"); }
                break;
                case 100:
//#line 375 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera bloque_FOR"); }
                break;
                case 101:
//#line 380 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": ASIGNACION_FOR");

                    yyval.tree = new SyntacticTree(new SyntacticTree(null, null, val_peek(2).attribute.getLexeme()), new SyntacticTree(null, null, val_peek(0).attribute.getLexeme()), "=");
                }
                break;
                case 102:
//#line 386 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera NRO_ULONGINT"); }
                break;
                case 103:
//#line 387 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera ="); }
                break;
                case 104:
//#line 388 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera ID en ASIGNACION FOR"); }
                break;
                case 105:
//#line 392 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": CONDICION FOR MENOR.");
                    yyval.tree = new SyntacticTree(new SyntacticTree(null, null, val_peek(2).attribute.getLexeme()), val_peek(0).tree, "<");
                }
                break;
                case 106:
//#line 397 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": CONDICION FOR MAYOR.");
                    yyval.tree = new SyntacticTree(new SyntacticTree(null, null, val_peek(2).attribute.getLexeme()), val_peek(0).tree, ">");
                }
                break;
                case 107:
//#line 402 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": CONDICION FOR IGUAL.");
                    yyval.tree = new SyntacticTree(new SyntacticTree(null, null, val_peek(2).attribute.getLexeme()), val_peek(0).tree, "==");
                }
                break;
                case 108:
//#line 407 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": CONDICION FOR MAYOR_IGUAL.");
                    yyval.tree = new SyntacticTree(new SyntacticTree(null, null, val_peek(2).attribute.getLexeme()), val_peek(0).tree, ">=");
                }
                break;
                case 109:
//#line 412 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": CONDICION FOR MENOR_IGUAL.");
                    yyval.tree = new SyntacticTree(new SyntacticTree(null, null, val_peek(2).attribute.getLexeme()), val_peek(0).tree, "<=");
                }
                break;
                case 110:
//#line 417 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": CONDICION FOR DISTINTO.");
                    yyval.tree = new SyntacticTree(new SyntacticTree(null, null, val_peek(2).attribute.getLexeme()), val_peek(0).tree, "!=");
                }
                break;
                case 111:
//#line 422 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera EXPRESION en COMPARACION"); }
                break;
                case 112:
//#line 423 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera EXPRESION en COMPARACION"); }
                break;
                case 113:
//#line 424 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera EXPRESION en COMPARACION"); }
                break;
                case 114:
//#line 425 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera EXPRESION en COMPARACION"); }
                break;
                case 115:
//#line 426 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera EXPRESION en COMPARACION"); }
                break;
                case 116:
//#line 427 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera EXPRESION en COMPARACION"); }
                break;
                case 117:
//#line 429 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera COMPARADOR y EXPRESION"); }
                break;
                case 118:
//#line 431 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera ID en COMPARACION"); }
                break;
                case 119:
//#line 432 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera ID en COMPARACION"); }
                break;
                case 120:
//#line 433 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera ID en COMPARACION"); }
                break;
                case 121:
//#line 434 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera ID en COMPARACION"); }
                break;
                case 122:
//#line 435 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera ID en COMPARACION"); }
                break;
                case 123:
//#line 436 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera ID en COMPARACION"); }
                break;
                case 124:
//#line 440 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree  = new SyntacticTree(new SyntacticTree(null, null, val_peek(0).attribute.getLexeme()), "UP");
                }
                break;
                case 125:
//#line 444 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree  = new SyntacticTree(new SyntacticTree(null, null, val_peek(0).attribute.getLexeme()), "DOWN");
                }
                break;
                case 126:
//#line 448 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera NRO_ULONGINT"); }
                break;
                case 127:
//#line 449 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera NRO_ULONGINT"); }
                break;
                case 128:
//#line 450 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera UP/DOWN"); }
                break;
                case 129:
//#line 456 "G08 - Gramatica - 21102020.y"
                {
                    System.out.println(val_peek(1).attribute.getLexeme());
                    addRule("Linea "+ la.getNroLinea() +": Sentencia OUT.");
                    yyval.tree  = new SyntacticTree(new SyntacticTree(null, null, val_peek(1).attribute.getLexeme()), "IMPRIMIR");
                }
                break;
                case 130:
//#line 462 "G08 - Gramatica - 21102020.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera ')'."); }
                break;
                case 131:
//#line 463 "G08 - Gramatica - 21102020.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera '('."); }
                break;
                case 132:
//#line 464 "G08 - Gramatica - 21102020.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera una cadena de caracteres luego de '('."); }
                break;
                case 133:
//#line 465 "G08 - Gramatica - 21102020.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": falta cadena"); }
                break;
                case 134:
//#line 466 "G08 - Gramatica - 21102020.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera '('."); }
                break;
                case 135:
//#line 472 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": CONVERSION EXPLICITA.");
                    yyval.tree  = new SyntacticTree(val_peek(3).tree, val_peek(1).tree, "CONVERSION");
                }
                break;
                case 136:
//#line 477 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera ')'."); }
                break;
                case 137:
//#line 478 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera expresion.");}
                break;
                case 138:
//#line 484 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": SUMA");
                    yyval.tree = new SyntacticTree(val_peek(2).tree, val_peek(0).tree, "+");
                }
                break;
                case 139:
//#line 489 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": RESTA.");
                    yyval.tree = new SyntacticTree(val_peek(2).tree, val_peek(0).tree, "-");
                }
                break;
                case 140:
//#line 494 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 141:
//#line 498 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 142:
//#line 502 "G08 - Gramatica - 21102020.y"
                { addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Se espera un termino luego del '+'."); }
                break;
                case 143:
//#line 503 "G08 - Gramatica - 21102020.y"
                { addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Se espera una expresion antes del '+'."); }
                break;
                case 144:
//#line 504 "G08 - Gramatica - 21102020.y"
                { addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Se espera un termino luego del '-'."); }
                break;
                case 145:
//#line 505 "G08 - Gramatica - 21102020.y"
                { addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Se espera una expresion antes del '-'."); }
                break;
                case 146:
//#line 509 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = new SyntacticTree(val_peek(2).tree, val_peek(0).tree, "*");
                }
                break;
                case 147:
//#line 513 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = new SyntacticTree(val_peek(2).tree, val_peek(0).tree, "/");
                }
                break;
                case 148:
//#line 517 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 149:
//#line 521 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Se espera un factor luego de * ");}
                break;
                case 150:
//#line 522 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Se espera un factor luego de /");}
                break;
                case 151:
//#line 523 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Se espera un termino antes de * ");}
                break;
                case 152:
//#line 524 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Se espera un termino antes de /");}
                break;
                case 153:
//#line 528 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": NRO_ULONGINT.");
                    yyval.tree = new SyntacticTree(null, null, val_peek(0).attribute.getLexeme());
                }
                break;
                case 154:
//#line 533 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": NRO_DOUBLE.");
                    yyval.tree = new SyntacticTree(null, null, val_peek(0).attribute.getLexeme());
                }
                break;
                case 155:
//#line 537 "G08 - Gramatica - 21102020.y"
                {
                    String lexeme ='-'+ val_peek(0).attribute.getLexeme();
                    boolean check = la.checkNegativeDouble(lexeme);
                    if(check){
                        addError("Error Sintáctico en línea "+ la.getNroLinea() +": DOUBLE fuera de rango.");
                    }else{
                        addRule("Linea "+ la.getNroLinea() +": NRO_DOUBLE negativo.");
                        Attribute attribute = new Attribute(lexeme,"NRO_DOUBLE", Type.DOUBLE);
                        la.addSymbolTable(lexeme, attribute);
                        val_peek(0).attribute.decreaseAmount();
                        int amount = la.getAttribute(val_peek(0).attribute.getLexeme()).getAmount();
                        if(amount == 0){
                            la.getSt().deleteSymbolTableEntry(val_peek(0).attribute.getLexeme());
                        }
                    }
                }
                break;
                case 156:
//#line 554 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 157:
//#line 560 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": ID PUNTO_PUNTO ID.");
                    yyval.tree  = new SyntacticTree(val_peek(2).tree, val_peek(0).tree, "::");
                }
                break;
                case 158:
//#line 565 "G08 - Gramatica - 21102020.y"
                {addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Se espera un ID luego de ::");}
                break;
                case 159:
//#line 568 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": ID.");
                    yyval.tree  = new SyntacticTree(null, null, val_peek(0).attribute.getLexeme());
                }
                break;
//#line 1661 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
            }//switch
            //#### Now let's reduce... ####
            if (yydebug) debug("reduce");
            state_drop(yym);             //we just reduced yylen states
            yystate = state_peek(0);     //get new state
            val_drop(yym);               //corresponding value drop
            yym = yylhs[yyn];            //select next TERMINAL(on lhs)
            if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
            {
                if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
                yystate = YYFINAL;         //explicitly say we're done
                state_push(YYFINAL);       //and save it
                val_push(yyval);           //also save the semantic value of parsing
                if (yychar < 0)            //we want another character?
                {
                    yychar = yylex();        //get next character
                    if (yychar<0) yychar=0;  //clean, if necessary
                    if (yydebug)
                        yylexdebug(yystate,yychar);
                }
                if (yychar == 0)          //Good exit (if lex returns 0 ;-)
                    break;                 //quit the loop--all DONE
            }//if yystate
            else                        //else not done yet
            {                         //get next state and push, for next yydefred[]
                yyn = yygindex[yym];      //find out where to go
                if ((yyn != 0) && (yyn += yystate) >= 0 &&
                        yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
                    yystate = yytable[yyn]; //get new state
                else
                    yystate = yydgoto[yym]; //else go to new defred
                if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
                state_push(yystate);     //going again, so push state & val...
                val_push(yyval);         //for next action
            }
        }//main loop
        return 0;//yyaccept!!
    }
//## end of method parse() ######################################



//## run() --- for Thread #######################################
    /**
     * A default run method, used for operating this parser
     * object in the background.  It is intended for extending Thread
     * or implementing Runnable.  Turn off with -Jnorun .
     */
    public void run()
    {
        yyparse();
    }
//## end of method run() ########################################



//## Constructors ###############################################
    /**
     * Default constructor.  Turn off with -Jnoconstruct .

     */
    public Parser()
    {
        //nothing to do
    }


    /**
     * Create a parser, setting the debug to true or false.
     * @param debugMe true for debugging, false for no debug.
     */
    public Parser(boolean debugMe)
    {
        yydebug=debugMe;
    }
//###############################################################



}
//################### END OF CLASS ##############################
