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






//#line 1 "G08 - Gramatica - 25102020.y"


package Parser;

import Lexer.LexerAnalyzer;
import SemanticAnalyzer.SemanticAnalyzer;
import java.util.ArrayList;
import java.util.List;
import Lexer.Token;
import SymbolTable.Attribute;
import SymbolTable.Type;
import SymbolTable.Use;
import SymbolTable.Parameter;
import SymbolTable.Parameter;
import SyntacticTree.*;


//#line 35 "Parser.java"




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
            0,    1,    1,    1,    2,    2,    2,    3,    3,    3,
            7,    7,    8,   10,   11,   11,   11,   11,   12,   12,
            12,    9,    9,   14,   14,   14,   14,   13,    4,    4,
            4,    4,    4,    4,    4,    4,    6,    6,    5,    5,
            19,   19,   19,   19,   15,   15,   15,   15,   21,   21,
            20,   20,   20,   20,   20,   20,   20,   20,   20,   20,
            20,   20,   20,   20,   20,   20,   20,   20,   20,   20,
            22,   22,   22,   22,   23,   23,   23,   26,   26,   26,
            26,   26,   25,   25,   17,   17,   17,   17,   16,   28,
            28,   28,   28,   29,   29,   29,   29,   29,   29,   30,
            30,   30,   30,   30,   18,   18,   18,   18,   18,   18,
            31,   31,   31,   24,   24,   24,   24,   24,   24,   24,
            32,   32,   32,   32,   32,   32,   32,   33,   33,   33,
            33,   33,   27,   27,   27,
    };
    final static short yylen[] = {                            2,
            2,    1,    2,    2,    1,    2,    2,    3,    2,    2,
            2,    2,    3,    2,    3,    5,    7,    2,    3,    3,
            3,    3,    3,    1,    2,    1,    2,    2,    2,    1,
            2,    2,    2,    2,    2,    2,    1,    3,    1,    1,
            4,    6,    8,    3,    4,    4,    3,    2,    2,    1,
            5,    5,    5,    5,    5,    5,    5,    5,    5,    5,
            5,    5,    5,    5,    5,    5,    5,    5,    3,    2,
            3,    1,    3,    2,    4,    2,    2,    4,    1,    4,
            3,    2,    1,    2,    3,    3,    3,    3,    9,    3,
            3,    3,    3,    3,    3,    3,    3,    3,    3,    2,
            2,    2,    2,    2,    4,    4,    3,    3,    4,    2,
            4,    4,    4,    3,    3,    1,    3,    3,    3,    3,
            3,    3,    1,    3,    3,    3,    3,    1,    1,    2,
            1,    1,    3,    3,    1,
    };
    final static short yydefred[] = {                         0,
            0,   39,    0,   40,    0,    0,    2,    0,    0,    0,
            0,    4,   14,    0,    0,    0,    0,    0,    0,    3,
            5,    0,   30,    0,    0,    0,    0,   10,   37,    0,
            9,   12,    0,   11,    0,    0,    7,    0,    0,    0,
            48,    0,    0,    0,  110,    0,    0,    0,    6,   29,
            32,   31,   34,   33,   36,   35,    0,    0,    8,    0,
            26,   24,    0,   18,    0,    0,    0,   13,    0,    0,
            129,  128,    0,    0,    0,  131,  132,    0,  123,  134,
            133,    0,   44,    0,    0,    0,    0,   72,    0,    0,
            0,    0,    0,  107,  108,    0,    0,    0,    0,    0,
            38,    0,   22,   27,   25,   28,   15,    0,    0,    0,
            0,    0,    0,    0,  130,    0,    0,    0,    0,    0,
            41,    0,   69,    0,    0,    0,    0,    0,    0,    0,
            83,    0,   46,   45,    0,   49,    0,    0,    0,    0,
            106,  105,  109,    0,   21,   20,   19,    0,    0,    0,
            126,  127,    0,    0,    0,    0,    0,    0,  124,  121,
            125,  122,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,   71,   84,    0,    0,
            76,   93,   92,   91,   90,    0,    0,   16,    0,  113,
            112,  111,   42,    0,   67,   61,   55,   66,   60,   54,
            65,   59,   53,   68,   62,   56,   63,   57,   51,   64,
            58,   52,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,   75,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,   17,   43,  104,  103,  100,  102,  101,
            0,    0,   79,   89,    0,    0,    0,    0,   80,   78,
    };
    final static short yydgoto[] = {                          5,
            6,   19,    7,  131,   74,   30,    9,   10,   34,   11,
            36,   68,   66,   63,   22,   23,   24,   25,   26,   43,
            89,   90,  136,   75,  132,  244,   76,   93,  187,  233,
            77,   78,   79,
    };
    final static short yysindex[] = {                      -187,
            -23,    0, -202,    0,    0, -148,    0, -152,    8, -107,
            39,    0,    0,  -33,  -22,  -37,   47,  -40,  309,    0,
            0,   54,    0,  -54,  -51,  -49,   41,    0,    0,   -6,
            0,    0,  326,    0,  144, -178,    0,  -31, -113,  -34,
            0,  -26, -111,   70,    0,   83,  -35,   76,    0,    0,
            0,    0,    0,    0,    0,    0,  -31,   25,    0, -106,
            0,    0,  216,    0,  -84,  127,   51,    0,  423,  -93,
            0,    0,  -94,  150,  114,    0,    0,   35,    0,    0,
            0,  134,    0,  423,  343,   76,  341,    0, -166,  -66,
            143,   68,  147,    0,    0,  -32,  -46,  114,  423,  114,
            0,   76,    0,    0,    0,    0,    0, -195,  -64, -233,
            28,   28,  -24,  -24,    0,   31,   43,   55,   58,   61,
            0,  -29,    0,   73,   87,   90,  102,  105,  117,   76,
            0,  282,    0,    0,  247,    0,  -56,   45, -231,   85,
            0,    0,    0,  136,    0,    0,    0,  119,   35,   35,
            0,    0,   80,  -11,  119,   35,  119,   35,    0,    0,
            0,    0,  156,  113,   -8,  451,   -1,  584,    5,  591,
            11,  604,   17,  633,   23,   76,    0,    0,   76,  309,
            0,    0,    0,    0,    0,  231,  170,    0, -195,    0,
            0,    0,    0,   92,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,  291,  -31,  -31,  -31,  -31,  -31,  -31, -199,
            208,  215,    0,  114,  114,  114,  114,  114,  114,   59,
            -229, -213,  311,    0,    0,    0,    0,    0,    0,    0,
            264,  359,    0,    0,   76,  300,   76,  -48,    0,    0,
    };
    final static short yyrindex[] = {                         0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,   78,    0,    0,    0,  353,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,  122,
            0,    0,    0,    0,   34,    0,    0,  129,    0,    0,
            0,    0,    0,  273,    0,  296,    0,    0,    0, -142,
            0,    0,    0,    0,    0,    0,    0,   38,   40,   48,
            0,  297,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0, -167,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,  153,  158,
            0,    0,    0,    0,  372,  392,  397,  402,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0, -120,    0,    0, -122,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,  307,  312,  317,  318,  322,  330,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    1,    0,   13,    0,    0,    0,
    };
    final static short yygindex[] = {                         0,
            0,    0,   18,   -2,  406,    0,    0,    0,    0,    0,
            0,    0,  -88,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,  362, -158,    0,   -4,    0,    0,    0,
            0,   75,  -28,
    };
    final static int YYTABLESIZE=680;
    static short yytable[];
    static { yytable();}
    static void yytable(){
        yytable = new short[]{                         47,
                82,   27,   42,   21,   52,   97,   83,   54,  142,   56,
                250,   87,   81,   73,   27,   33,   49,   40,   73,  144,
                73,  213,  146,   20,  184,   37,  237,   38,   27,  192,
                62,  117,  197,  118,  117,   12,  118,   60,   27,  200,
                88,  117,  239,  118,  147,  203,  185,  117,  238,  118,
                61,  206,   59,  117,   13,  118,  230,  209,   27,  117,
                105,  118,    2,  212,  240,  117,   31,  118,    1,   73,
                2,    4,   73,  231,  232,   73,  119,    3,   35,    4,
                104,  120,   27,  246,  151,  152,   44,   73,   74,  133,
                160,  162,   88,   74,   74,  134,   87,   67,   86,   73,
                221,   58,   73,   28,   29,   73,   85,   14,   15,    2,
                16,  110,   50,   50,   17,   18,    3,   73,    4,   50,
                190,  113,  111,   94,  112,   82,  114,   27,  139,  178,
                27,   73,  181,   77,   73,   73,   38,   81,  135,   77,
                73,   73,   80,   81,   86,   15,   73,   16,   32,   73,
                101,   17,   18,  195,  113,  111,  117,  112,  118,  114,
                113,   73,  135,  135,  135,  114,  135,  107,  135,  116,
                108,  116,  106,  116,  121,   27,  188,  122,   39,  189,
                135,  135,  115,  135,   64,  149,  150,  116,  116,  116,
                116,  156,  158,  118,  135,  118,  193,  118,  120,  194,
                120,   51,  120,  137,   53,  140,   55,  249,   27,  143,
                178,  118,  118,  145,  118,   45,  120,  120,   41,  120,
                95,  182,   82,  141,   69,   70,    2,  163,  220,   84,
                70,    2,   70,    2,   46,    4,   27,   27,  243,   96,
                4,   27,    4,  178,  191,   71,   72,  196,  234,   39,
                71,   72,   71,   72,  199,  235,   82,   82,   82,   82,
                202,   82,   82,   82,   82,   82,  205,   82,   81,   81,
                81,   81,  208,   81,   81,   81,   81,   81,  211,   81,
                99,   70,    2,  148,   70,    2,  153,   70,    2,   88,
                218,    4,  219,   87,    4,   86,   57,    4,  155,   70,
                2,   71,   72,   85,   71,   72,  109,   71,   72,    4,
                157,   70,    2,  159,   70,    2,  161,   70,    2,   71,
                72,    4,  183,  138,    4,   91,   92,    4,  164,   70,
                2,   71,   72,  135,   71,   72,  236,   71,   72,    4,
                103,  186,  166,   70,    2,  168,   70,    2,  222,   71,
                72,  241,    1,    4,   47,   23,    4,  170,   70,    2,
                172,   70,    2,   71,   72,   98,   71,   72,    4,  180,
                97,    4,  174,   70,    2,   96,   99,  135,   71,   72,
                94,   71,   72,    4,  116,  117,  242,  118,   95,  135,
                135,  135,  135,   71,   72,   70,  116,  116,  116,  116,
                0,    2,  128,   85,  129,    8,  177,    0,  118,    0,
                4,    8,  117,  120,  117,  223,  117,    0,   98,  100,
                118,  118,  118,  118,  248,  120,  120,  120,  120,    0,
                117,  117,  114,  117,  114,    0,  114,  119,    8,  119,
                65,  119,  115,    0,  115,    0,  115,    0,    0,    0,
                114,  114,    0,  114,    0,  119,  119,    0,  119,    0,
                115,  115,    0,  115,  113,  111,    0,  112,    8,  114,
                0,  102,   15,    2,   16,    0,    0,  154,   17,   18,
                3,    0,    4,    0,    0,  165,  167,  169,  171,  173,
                175,  198,  113,  111,    0,  112,    0,  114,  214,  215,
                216,  217,  179,   15,    0,   16,    0,    0,    0,   17,
                18,    0,    0,   65,    0,    0,    0,    0,    0,   48,
                15,    0,   16,    0,    0,    0,   17,   18,   70,   70,
                0,   70,    0,    0,    0,   70,   70,  176,   15,    0,
                16,    0,    0,    0,   17,   18,   48,   15,    0,   16,
                0,    0,    0,   17,   18,  247,   15,    0,   16,    0,
                0,    0,   17,   18,   48,   15,    0,   16,    0,    0,
                0,   17,   18,    0,    0,  224,  225,  226,  227,  228,
                229,   48,   15,    2,   16,    0,    0,    0,   17,   18,
                3,    0,    4,    0,   65,    0,  130,   15,  123,   16,
                0,    0,    0,   17,   18,    0,    0,    0,    0,    0,
                124,  125,  126,  127,  245,   15,    0,   16,    0,    0,
                0,   17,   18,    0,  201,  113,  111,  117,  112,    0,
                114,  204,  113,  111,    0,  112,    0,  114,    0,  117,
                117,  117,  117,    0,  207,  113,  111,  114,  112,    0,
                114,    0,  119,    0,    0,    0,    0,  115,    0,  114,
                114,  114,  114,    0,  119,  119,  119,  119,    0,  115,
                115,  115,  115,  210,  113,  111,    0,  112,    0,  114,
        };
    }
    static short yycheck[];
    static { yycheck(); }
    static void yycheck() {
        yycheck = new short[] {                         40,
                0,    6,   40,    6,   59,   41,   41,   59,   41,   59,
                59,  123,    0,   45,   19,  123,   19,   40,   45,  108,
                45,  180,  256,    6,  256,   59,  256,   61,   33,   41,
                33,   43,   41,   45,   43,   59,   45,   44,   43,   41,
                43,   43,  256,   45,  278,   41,  278,   43,  278,   45,
                33,   41,   59,   43,  257,   45,  256,   41,   63,   43,
                63,   45,  258,   41,  278,   43,   59,   45,  256,   45,
                258,  267,   45,  273,  274,   45,   42,  265,   40,  267,
                63,   47,   87,  242,  113,  114,   40,   45,  256,  256,
                119,  120,   59,  261,  262,  262,   59,  276,   59,   45,
                189,   61,   45,  256,  257,   45,   59,  256,  257,  258,
                259,   61,   59,  256,  263,  264,  265,   45,  267,  262,
                41,   42,   43,   41,   45,  125,   47,  132,   61,  132,
                135,   45,  135,  256,   45,  256,   61,  125,   61,  262,
                261,  262,  256,  257,  256,  257,   45,  259,  256,   45,
                257,  263,  264,   41,   42,   43,   43,   45,   45,   47,
                42,   45,   41,   42,   43,   47,   45,   41,   47,   41,
                44,   43,  257,   45,   41,  180,   41,   44,  272,   44,
                59,   60,  277,   62,   41,  111,  112,   59,   60,   40,
                62,  117,  118,   41,  261,   43,   41,   45,   41,   44,
                43,  256,   45,   61,  256,   59,  256,  256,  213,  256,
                213,   59,   60,  278,   62,  256,   59,   60,  256,   62,
                256,  278,  257,  256,  256,  257,  258,  257,   59,  256,
                257,  258,  257,  258,  275,  267,  241,  242,  241,  275,
                267,  246,  267,  246,  256,  277,  278,  256,   41,  272,
                277,  278,  277,  278,  256,   41,  256,  257,  258,  259,
                256,  261,  262,  263,  264,  265,  256,  267,  256,  257,
                258,  259,  256,  261,  262,  263,  264,  265,  256,  267,
                256,  257,  258,  256,  257,  258,  256,  257,  258,  256,
                60,  267,   62,  256,  267,  256,  256,  267,  256,  257,
                258,  277,  278,  256,  277,  278,  256,  277,  278,  267,
                256,  257,  258,  256,  257,  258,  256,  257,  258,  277,
                278,  267,  278,  256,  267,  256,  257,  267,  256,  257,
                258,  277,  278,  256,  277,  278,  278,  277,  278,  267,
                125,  257,  256,  257,  258,  256,  257,  258,  257,  277,
                278,   41,    0,  267,   59,   59,  267,  256,  257,  258,
                256,  257,  258,  277,  278,   59,  277,  278,  267,  123,
                59,  267,  256,  257,  258,   59,   59,  256,  277,  278,
                59,  277,  278,  267,  256,   43,  123,   45,   59,  268,
                269,  270,  271,  277,  278,  123,  268,  269,  270,  271,
                -1,  258,   60,   42,   62,    0,  125,   -1,  256,   -1,
                267,    6,   41,  256,   43,  125,   45,   -1,   57,   58,
                268,  269,  270,  271,  125,  268,  269,  270,  271,   -1,
                59,   60,   41,   62,   43,   -1,   45,   41,   33,   43,
                35,   45,   41,   -1,   43,   -1,   45,   -1,   -1,   -1,
                59,   60,   -1,   62,   -1,   59,   60,   -1,   62,   -1,
                59,   60,   -1,   62,   42,   43,   -1,   45,   63,   47,
                -1,  256,  257,  258,  259,   -1,   -1,  116,  263,  264,
                265,   -1,  267,   -1,   -1,  124,  125,  126,  127,  128,
                129,   41,   42,   43,   -1,   45,   -1,   47,  268,  269,
                270,  271,  256,  257,   -1,  259,   -1,   -1,   -1,  263,
                264,   -1,   -1,  108,   -1,   -1,   -1,   -1,   -1,  256,
                257,   -1,  259,   -1,   -1,   -1,  263,  264,  256,  257,
                -1,  259,   -1,   -1,   -1,  263,  264,  256,  257,   -1,
                259,   -1,   -1,   -1,  263,  264,  256,  257,   -1,  259,
                -1,   -1,   -1,  263,  264,  256,  257,   -1,  259,   -1,
                -1,   -1,  263,  264,  256,  257,   -1,  259,   -1,   -1,
                -1,  263,  264,   -1,   -1,  214,  215,  216,  217,  218,
                219,  256,  257,  258,  259,   -1,   -1,   -1,  263,  264,
                265,   -1,  267,   -1,  189,   -1,  256,  257,  256,  259,
                -1,   -1,   -1,  263,  264,   -1,   -1,   -1,   -1,   -1,
                268,  269,  270,  271,  256,  257,   -1,  259,   -1,   -1,
                -1,  263,  264,   -1,   41,   42,   43,  256,   45,   -1,
                47,   41,   42,   43,   -1,   45,   -1,   47,   -1,  268,
                269,  270,  271,   -1,   41,   42,   43,  256,   45,   -1,
                47,   -1,  256,   -1,   -1,   -1,   -1,  256,   -1,  268,
                269,  270,  271,   -1,  268,  269,  270,  271,   -1,  268,
                269,  270,  271,   41,   42,   43,   -1,   45,   -1,   47,
        };
    }
    final static short YYFINAL=5;
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
            "programa : lista_sentencias_declarativas lista_sentencias_ejecutables",
            "lista_sentencias_declarativas : sent_declarativa",
            "lista_sentencias_declarativas : lista_sentencias_declarativas sent_declarativa",
            "lista_sentencias_declarativas : error ';'",
            "lista_sentencias_ejecutables : sent_ejecutable",
            "lista_sentencias_ejecutables : lista_sentencias_ejecutables sent_ejecutable",
            "lista_sentencias_ejecutables : error ';'",
            "sent_declarativa : tipo lista_variables ';'",
            "sent_declarativa : procedimiento ';'",
            "sent_declarativa : tipo error",
            "procedimiento : encabezado cuerpo_procedimiento",
            "procedimiento : encabezado error",
            "encabezado : encabezado_PROC parametro_PROC asignacion_NA",
            "encabezado_PROC : PROC ID",
            "parametro_PROC : '(' parametro ')'",
            "parametro_PROC : '(' parametro ',' parametro ')'",
            "parametro_PROC : '(' parametro ',' parametro ',' parametro ')'",
            "parametro_PROC : '(' ')'",
            "asignacion_NA : NA '=' NRO_ULONGINT",
            "asignacion_NA : NA '=' error",
            "asignacion_NA : NA error NRO_ULONGINT",
            "cuerpo_procedimiento : '{' bloque_procedimiento '}'",
            "cuerpo_procedimiento : '{' bloque_procedimiento error",
            "bloque_procedimiento : sent_ejecutable",
            "bloque_procedimiento : bloque_procedimiento sent_ejecutable",
            "bloque_procedimiento : sent_declarativa",
            "bloque_procedimiento : bloque_procedimiento sent_declarativa",
            "parametro : tipo ID",
            "sent_ejecutable : sentencia_if ';'",
            "sent_ejecutable : sentencia_control",
            "sent_ejecutable : asignacion ';'",
            "sent_ejecutable : asignacion error",
            "sent_ejecutable : imprimir ';'",
            "sent_ejecutable : imprimir error",
            "sent_ejecutable : llamado_PROC ';'",
            "sent_ejecutable : llamado_PROC error",
            "lista_variables : ID",
            "lista_variables : lista_variables ',' ID",
            "tipo : ULONGINT",
            "tipo : DOUBLE",
            "llamado_PROC : ID '(' ID ')'",
            "llamado_PROC : ID '(' ID ',' ID ')'",
            "llamado_PROC : ID '(' ID ',' ID ',' ID ')'",
            "llamado_PROC : ID '(' ')'",
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
            "bloque_IF : '{' cuerpo_ejecutable '}'",
            "bloque_IF : sent_ejecutable",
            "bloque_IF : '{' cuerpo_ejecutable error",
            "bloque_IF : '{' error",
            "bloque_else : ELSE '{' cuerpo_ejecutable '}'",
            "bloque_else : ELSE sent_ejecutable",
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
            "sentencia_control : FOR '(' asignacion_FOR ';' comparacion_FOR ';' incr_decr ')' bloque_FOR",
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
            "factor : conversion_explicita",
            "tipo_ID : ID PUNTO_PUNTO ID",
            "tipo_ID : ID PUNTO_PUNTO error",
            "tipo_ID : ID",
    };

//#line 1038 "G08 - Gramatica - 25102020.y"


    private LexerAnalyzer la;
    private SemanticAnalyzer sa;
    private List<String> errors;
    private List<String> rules;
    private SyntacticTree syntacticTree;
    private String globalScope = "";
    private String PROCscope = "";
    private List<SyntacticTree> PROCtrees = new ArrayList<>();
    private List<SyntacticTree> PROCtreesAux = new ArrayList<>();
    private int counter = 0;

    public Parser(LexerAnalyzer la){
        this.errors = new ArrayList<String>();
        this.rules = new ArrayList<String>();
        this.la = la;
        this.sa = new SemanticAnalyzer();
    }

    public int yylex(){
        Token token = this.la.getNextToken();
        List<Attribute> attributes = la.getAttribute(token.getLexema());
        yylval = new ParserVal(attributes);
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

    public String printSyntacticTree(){
        this.syntacticTree.printTree(this.syntacticTree);
        return this.syntacticTree.getPrintTree();
    }

    public SyntacticTree returnTree(){
        return this.syntacticTree;
    }

    public boolean checkType(SyntacticTree root){
        return root.checkType(root);
    }

    public void setScopeProcID(Attribute attribute){
        attribute.setUse(Use.nombre_procedimiento);
        attribute.setScope(globalScope);
    }

    public void setScopeProcParam (List<String> list){
        for(String lexeme : list){
            List<Attribute> attributes = la.getAttribute(lexeme);
            attributes.get(attributes.size()-1).setScope(globalScope);

        }
    }

    public void decreaseScope(){
        String [] array = this.globalScope.split("\\@");
        String aux = "";
        for(int i=0; i<array.length-1; i++){
            if(i == 0)
                aux = array[i];
            else
                aux = aux + "@" + array[i];
        }
        this.globalScope = aux;
    }

    public List<Attribute> getListUse(List<Attribute> list, Use use){
        List<Attribute> aux = new ArrayList<>();
        for(Attribute attribute : list){
            if(attribute.getUse().equals(use)){
                aux.add(attribute);
            }
        }
        return aux;
    }

    public void isRedeclared(List<Attribute> attributes, String lexeme, String error){
        boolean found = false;
        if(!this.globalScope.isEmpty() || !(attributes.size() == 1)) {
            if (attributes.size() == 1) {
                attributes.get(attributes.size() - 1).setDeclared();
                found = true;
            }else{
                if (sa.isRedeclared(this.globalScope, lexeme, attributes)) {
                    attributes.get(attributes.size() - 1).decreaseAmount();
                    addError("Error Semantico en linea " + la.getNroLinea() + ":" + error);
                    la.getSt().deleteLastElement(lexeme);
                    found = true;
                }
            }
        }
        if(!found){
            attributes.get(attributes.size()-1).setDeclared();
        }
    }

    //Elimina de la tabla de símbolos los atributos de un lexema, que coinciden con el uso pasado por parámetro
    public void deleteSTEntry(String lexeme, Use use){
        List<Attribute> listAttributes = la.getSymbolTable().getSymbolTable().get(lexeme);
        for (int i=0; i<listAttributes.size(); i++){
            if(listAttributes.get(i).getUse().equals(use)){
                listAttributes.remove(i);
                i--;
            }
        }
        la.getSymbolTable().getSymbolTable().replace(lexeme, listAttributes);
    }

    //Chequea si existe una variable al alcance
    public Attribute checkID(List<Attribute> attributes, String lexeme){

        attributes.remove(attributes.size()-1);
        List<Attribute> variables = getListUse(attributes, Use.variable);
        List<Attribute> parametros = getListUse(attributes, Use.nombre_parametro);

        Attribute att = null;

        boolean encontro = false;

        if(variables.size() > 0){
            if(parametros.size() > 0){
                parametros.addAll(variables);
                attributes = parametros;
            }else{
                attributes = variables;
            }
        }else{
            if(parametros.size() > 0) {
                attributes = parametros;
            }else {
                attributes = null;
            }
        }

        if(attributes == null){
            encontro = false;
        }else{
            for(Attribute attribute : attributes) {
                encontro = sa.isReachable(this.globalScope, lexeme, attribute);
                if(encontro){
                    att = attribute;
                    break;
                }
            }
        }

        if(!encontro){
            addError("Error Semántico en línea "+ la.getNroLinea() +": No se encuentra variable al alcance");
        }

        return att;

    }

    //Chequea si existe un procedimiento al alcance para ser llamado
    public List<Parameter> checkIDPROC(List<Attribute> attributes, String lexeme){
        attributes = getListUse(attributes, Use.nombre_procedimiento);
        List<Parameter> parameters = new ArrayList<>();
        boolean encontro = false;
        if(attributes.isEmpty())
            encontro = false;
        else {
            for(Attribute attribute : attributes) {
                encontro = sa.isReachable(this.globalScope, lexeme, attribute);
                if(encontro){
                    this.PROCscope = attribute.getScope();
                    parameters = attribute.getParameters();
                    break;
                }

            }
        }
        if(!encontro){
            addError("Error Semántico en línea "+ la.getNroLinea() +": No se encuentra declaración de procedimiento al alcance");
        }

        return parameters;
    }


    //Chequear que exista una variable en el ámbito del procedimiento
    public Type checkIDdospuntosID(String scopePROC, String lexeme, List<Attribute> attributes){
        String[] scopeSplit = scopePROC.split("@");
        String scope = lexeme + "@";
        for(int i = 1; i < scopeSplit.length; i++)
            scope += scopeSplit[i] + "@";
        scope += scopeSplit[0];
        boolean encontro = false;
        for(int i = attributes.size()-1; i >= 0; i--){
            if (attributes.get(i).getScope().equals(scope))
                return attributes.get(i).getType();
        }

        if(!encontro)
            addError("Error Semántico en línea "+ la.getNroLinea() +": No se encuentra variable al alcance (ID::ID)");

        return Type.ERROR;

    }


    public void checkParameters(List<Parameter> formalParameters, List<Type> types){
        if(formalParameters.isEmpty() && !types.isEmpty() || !formalParameters.isEmpty() && types.isEmpty()){
            addError("Error Semantico en linea "+ la.getNroLinea() +": Llamado a procedimiento con numero erroneo de parametros");
        }else{
            if(types.size() != formalParameters.size())
                addError("Error Semantico en linea "+ la.getNroLinea() +": Llamado a procedimiento con distinto numero de parametros");
            else{
                if(!formalParameters.isEmpty() && !types.isEmpty()){
                    for(int i=0; i < formalParameters.size(); i++){
                        if(!formalParameters.get(i).getType().getName().equals(types.get(i).getName())){
                            addError("Error Semantico en linea "+ la.getNroLinea() +": Llamado a procedimiento con parametro cuyo tipo no coincide");
                            break;
                        }
                    }
                }
            }
        }
    }


    public List<SyntacticTree> getPROCtreeList(){
        List<SyntacticTree> list = new ArrayList<>(this.PROCtrees);
        return list;
    }

    public String printPROCtree(){
        String procTree = "";
        for(SyntacticTree node : this.PROCtrees){
            node.printTree(node);
            procTree += node.getPrintTree() + '\n';
        }
        return procTree;
    }
    //#line 823 "Parser.java"
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
//#line 30 "G08 - Gramatica - 25102020.y"
                {
                    syntacticTree = val_peek(0).tree;
                }
                break;
                case 4:
//#line 38 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": se espera sentencia/lista de sentencias");}
                break;
                case 5:
//#line 42 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 6:
//#line 46 "G08 - Gramatica - 25102020.y"
                {
                    Attribute attribute = new Attribute("LISTA SENTENCIAS");
                    yyval.tree = new SyntacticTreeSentence(val_peek(1).tree, val_peek(0).tree, attribute);
                }
                break;
                case 7:
//#line 51 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": se espera sentencia/lista de sentencias");}
                break;
                case 8:
//#line 56 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia declarativa - Variable/s.");
                    yyval = val_peek(1);
                    Type type = new Type(val_peek(2).type.getName());
                    for(String lexeme : yyval.attributesSetteable){
                        List<Attribute> attributes = la.getAttribute(lexeme);
                        attributes.get(attributes.size() - 1).setUse(Use.variable);
                        attributes.get(attributes.size() - 1).setType(type);

                        List<Attribute> variables = getListUse(attributes, Use.variable);
                        List<Attribute> parametros = getListUse(attributes, Use.nombre_parametro);

                        boolean encontro = false;

                        if(variables.size() > 0){
                            if(parametros.size() > 0){
                                parametros.addAll(variables);
                                attributes = parametros;
                            }else{
                                attributes = variables;
                            }
                        }else{
                            if(parametros.size() > 0) {
                                attributes = parametros;
                            }else {
                                attributes = null;
                            }
                        }

                        this.isRedeclared(attributes, lexeme, "Sentencia declarativa - Redefinicion de  VARIABLE/S");

                        attributes.get(attributes.size()-1).setScope(this.globalScope);
                    }
                    yyval = val_peek(1);
                }
                break;
                case 9:
//#line 93 "G08 - Gramatica - 25102020.y"
                {
                    this.decreaseScope();
                    this.counter--;
                    this.PROCtreesAux.get(this.counter).setLeft(val_peek(1).tree);
                    yyval.tree = null;
                    if(this.counter == 0){
                        this.PROCtrees.addAll(this.PROCtreesAux);
                        this.PROCtreesAux.clear();
                    }

                }
                break;
                case 10:
//#line 105 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia declarativa - Falta definir la/s VARIABLE/S."); }
                break;
                case 11:
//#line 110 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Procedimiento");
                    this.sa.deleteNA();
                    Attribute attribute = new Attribute("INICIO PROCEDIMIENTO");
                    yyval.tree = new SyntacticTreePROCHEAD(val_peek(0).tree, attribute);
                }
                break;
                case 12:
//#line 116 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Procedimiento - Se espera CUERPO del PROCEDIMIENTO"); }
                break;
                case 13:
//#line 120 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Encabezado procedimiento");
                }
                break;
                case 14:
//#line 126 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": PROC ID");

                    yyval.attributes = val_peek(0).attributes;
                    String lexeme = val_peek(0).attributes.get(0).getLexeme();

                    List<Attribute> attributes = la.getAttribute(lexeme);
                    attributes.get(attributes.size() - 1).setUse(Use.nombre_procedimiento);
                    attributes.get(attributes.size() - 1).setScope(this.globalScope);

                    val_peek(0).attributes = getListUse(val_peek(0).attributes, Use.nombre_procedimiento);
                    this.isRedeclared(val_peek(0).attributes, lexeme, "Sentencia declarativa - Redefinicion de ID procedimiento");

                    this.globalScope += "@" + lexeme;

                    Attribute attribute = val_peek(0).attributes.get(val_peek(0).attributes.size()-1);
                    SyntacticTree root = new SyntacticTreePROCHEAD(null, attribute);
                    this.PROCtreesAux.add(root);
                    this.counter++;

                }
                break;
                case 15:
//#line 150 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Procedimiento - un parametro");

                    this.setScopeProcParam(val_peek(1).attributesSetteable);

                    String[] scope = this.globalScope.split("@");
                    String lexeme = scope[scope.length - 1];
                    List<Attribute> attributes = getListUse(la.getSt().getSymbolTable().get(lexeme), Use.nombre_procedimiento);

                    List<Parameter> parameters = new ArrayList<>();

                    parameters.add(new Parameter(val_peek(1).attributes.get(val_peek(1).attributes.size()-1).getScope(), val_peek(1).attributes.get(val_peek(1).attributes.size()-1).getType()));

                    attributes.get(attributes.size()-1).setParameters(parameters);
                }
                break;
                case 16:
//#line 167 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Procedimiento - dos parametros");

                    this.setScopeProcParam(val_peek(3).attributesSetteable);
                    this.setScopeProcParam(val_peek(1).attributesSetteable);
                    String lexemeParam1 = val_peek(3).attributes.get(0).getLexeme();
                    String lexemeParam2 = val_peek(1).attributes.get(0).getLexeme();

                    if(lexemeParam1.equals(lexemeParam2)){
                        addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia declarativa - Redefinicion de parametro");
                    }

                    String[] scope = this.globalScope.split("@");
                    String lexeme = scope[scope.length - 1];
                    List<Attribute> attributes = getListUse(la.getSt().getSymbolTable().get(lexeme), Use.nombre_procedimiento);

                    List<Parameter> parameters = new ArrayList<>();

                    parameters.add(new Parameter(val_peek(3).attributes.get(val_peek(3).attributes.size()-1).getScope(), val_peek(3).attributes.get(val_peek(3).attributes.size()-1).getType()));
                    parameters.add(new Parameter(val_peek(1).attributes.get(val_peek(1).attributes.size()-1).getScope(), val_peek(1).attributes.get(val_peek(1).attributes.size()-1).getType()));

                    attributes.get(attributes.size()-1).setParameters(parameters);

                }
                break;
                case 17:
//#line 192 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Procedimiento - dos parametros");

                    this.setScopeProcParam(val_peek(5).attributesSetteable);
                    this.setScopeProcParam(val_peek(3).attributesSetteable);
                    this.setScopeProcParam(val_peek(1).attributesSetteable);
                    String lexemeParm1 = val_peek(5).attributes.get(0).getLexeme();
                    String lexemeParm2 = val_peek(3).attributes.get(0).getLexeme();
                    String lexemeParm3 = val_peek(1).attributes.get(0).getLexeme();

                    if(lexemeParm1.equals(lexemeParm2) || lexemeParm1.equals(lexemeParm3) || lexemeParm2.equals(lexemeParm3)){
                        addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia declarativa - Redefinicion de parametro");
                    }

                    String[] scope = this.globalScope.split("@");
                    String lexeme = scope[scope.length - 1];
                    List<Attribute> attributes = getListUse(la.getSt().getSymbolTable().get(lexeme), Use.nombre_procedimiento);

                    List<Parameter> parameters = new ArrayList<>();

                    parameters.add(new Parameter(val_peek(5).attributes.get(val_peek(5).attributes.size()-1).getScope(), val_peek(5).attributes.get(val_peek(5).attributes.size()-1).getType()));
                    parameters.add(new Parameter(val_peek(3).attributes.get(val_peek(3).attributes.size()-1).getScope(), val_peek(3).attributes.get(val_peek(3).attributes.size()-1).getType()));
                    parameters.add(new Parameter(val_peek(1).attributes.get(val_peek(1).attributes.size()-1).getScope(), val_peek(1).attributes.get(val_peek(1).attributes.size()-1).getType()));

                    attributes.get(attributes.size()-1).setParameters(parameters);
                }
                break;
                case 19:
//#line 223 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Procedimiento - Asignacion NA");

                    int pos = sa.checkNA(this.globalScope);
                    if(pos != -1){
                        String ID_PROC = sa.errorNA(pos, this.globalScope);
                        addError("Error Semántico en linea "+ la.getNroLinea() +": Sentencia declarativa - Se supera numero de anidamiento en PROC " + ID_PROC);
                    }

                    sa.addNA(Integer.valueOf(val_peek(0).attributes.get(0).getScope()));

                }
                break;
                case 20:
//#line 236 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Asignacion NA procedimiento - Se espera NRO_ULONGINT"); }
                break;
                case 21:
//#line 237 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Asignacion NA procedimiento - Se espera ="); }
                break;
                case 22:
//#line 241 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(1).tree;
                }
                break;
                case 23:
//#line 245 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Cuerpo procedimiento - Se espera }"); }
                break;
                case 24:
//#line 249 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 25:
//#line 253 "G08 - Gramatica - 25102020.y"
                {
                    Attribute attribute = new Attribute("SENTENCIA EJECUTABLE");
                    yyval.tree = new SyntacticTreeBODY(val_peek(1).tree, val_peek(0).tree, attribute);
                }
                break;
                case 26:
//#line 258 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 27:
//#line 262 "G08 - Gramatica - 25102020.y"
                {
                    Attribute attribute = new Attribute("SENTENCIA DECLARATIVA");
                    yyval.tree = new SyntacticTreeBODY(val_peek(1).tree, val_peek(0).tree, attribute);
                }
                break;
                case 28:
//#line 272 "G08 - Gramatica - 25102020.y"
                {
                    yyval = val_peek(0);

                    yyval.attributesSetteable = new ArrayList<>();
                    String lexeme = val_peek(0).attributes.get(0).getLexeme();
                    yyval.attributesSetteable.add(lexeme);

                    Type type = new Type(val_peek(1).type.getName());
                    val_peek(0).attributes.get(val_peek(0).attributes.size()-1).setType(type);
                    val_peek(0).attributes.get(val_peek(0).attributes.size()-1).setUse(Use.nombre_parametro);
                    val_peek(0).attributes.get(val_peek(0).attributes.size()-1).setFlag();
                }
                break;
                case 29:
//#line 288 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(1).tree;
                }
                break;
                case 30:
//#line 293 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 31:
//#line 298 "G08 - Gramatica - 25102020.y"
                {
                    /*Attribute attribute = new Attribute("ASIGNACION");
                     */
                    /*$$.tree = new SyntacticTreeASIG($1.tree, attribute);
                     */
                    yyval.tree = val_peek(1).tree;
                }
                break;
                case 32:
//#line 303 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia ejecutable asignacion - Falta ;"); }
                break;
                case 33:
//#line 306 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(1).tree;
                }
                break;
                case 34:
//#line 309 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia ejecutable OUT - Falta ;"); }
                break;
                case 35:
//#line 312 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(1).tree;
                }
                break;
                case 36:
//#line 316 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia ejecutable llamado a procedimiento - Falta ;"); }
                break;
                case 37:
//#line 323 "G08 - Gramatica - 25102020.y"
                {
                    yyval.attributesSetteable = new ArrayList<>();
                    String lexeme = val_peek(0).attributes.get(0).getLexeme();
                    val_peek(0).attributes.get(val_peek(0).attributes.size()-1).setFlag();
                    yyval.attributesSetteable.add(lexeme);

                }
                break;
                case 38:
//#line 331 "G08 - Gramatica - 25102020.y"
                {
                    yyval = val_peek(2);
                    String lexeme = val_peek(0).attributes.get(0).getLexeme();
                    val_peek(0).attributes.get(val_peek(0).attributes.size()-1).setFlag();
                    yyval.attributesSetteable.add(lexeme);
                }
                break;
                case 39:
//#line 340 "G08 - Gramatica - 25102020.y"
                {
                    yyval.type = Type.ULONGINT;
                    Attribute attribute = new Attribute("ULONGINT");
                    attribute.setType(Type.ULONGINT);
                    yyval.tree  = new SyntacticTreeCONV(null, null, attribute);

                }
                break;
                case 40:
//#line 348 "G08 - Gramatica - 25102020.y"
                {
                    yyval.type = Type.DOUBLE;
                    Attribute attribute = new Attribute("DOUBLE");
                    attribute.setType(Type.DOUBLE);
                    yyval.tree = new SyntacticTreeCONV(null, null, attribute);
                }
                break;
                case 41:
//#line 358 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Llamado a procedimiento con un parametro");
                    String lexeme = val_peek(3).attributes.get(0).getLexeme();
                    String lexemeParam = val_peek(1).attributes.get(0).getLexeme();

                    val_peek(3).attributes.get(val_peek(3).attributes.size()-1).setUse(Use.llamado_procedimiento);

                    List<Parameter> formalParameters = checkIDPROC(val_peek(3).attributes, lexeme);

                    String scope = val_peek(3).attributes.get(0).getLexeme() + this.globalScope;
                    val_peek(3).attributes.get(val_peek(3).attributes.size()-1).setScopePROC(scope);

                    List<Type> types = new ArrayList<>();

                    lexeme = val_peek(1).attributes.get(0).getLexeme();

                    Attribute attribute = this.checkID(val_peek(1).attributes, lexeme);
                    Parameter ID1 = new Parameter(attribute.getScope(), attribute.getType());
                    if(attribute != null)
                        types.add(attribute.getType());

                    this.checkParameters(formalParameters, types);

                    List<Parameter> parameters = new ArrayList<>();
                    parameters.add(ID1);
                    val_peek(3).attributes.get(val_peek(3).attributes.size()-1).setParameters(parameters);

                    Attribute ID = val_peek(3).attributes.get(val_peek(3).attributes.size()-1);
                    ID.setScopePROC(this.PROCscope);
                    yyval.tree = new SyntacticTreeCALL(val_peek(3).tree, ID, formalParameters);

                }
                break;
                case 42:
//#line 391 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Llamado a procedimiento con parametros");
                    String lexeme = val_peek(5).attributes.get(0).getLexeme();

                    val_peek(5).attributes.get(val_peek(5).attributes.size()-1).setUse(Use.llamado_procedimiento);

                    List<Parameter> formalParameters = checkIDPROC(val_peek(5).attributes, lexeme);

                    String scope = val_peek(5).attributes.get(0).getLexeme() + this.globalScope;
                    val_peek(5).attributes.get(val_peek(5).attributes.size()-1).setScopePROC(scope);

                    List<Type> types = new ArrayList<>();

                    Attribute attribute = this.checkID(val_peek(3).attributes, val_peek(3).attributes.get(0).getLexeme());
                    Parameter ID1 = new Parameter(attribute.getScope(), attribute.getType());
                    if(attribute != null)
                        types.add(attribute.getType());

                    attribute = this.checkID(val_peek(1).attributes, val_peek(1).attributes.get(0).getLexeme());
                    Parameter ID2 = new Parameter(attribute.getScope(), attribute.getType());
                    if(attribute != null)
                        types.add(attribute.getType());

                    this.checkParameters(formalParameters, types);

                    List<Parameter> parameters = new ArrayList<>();
                    parameters.add(ID1);
                    parameters.add(ID2);
                    val_peek(5).attributes.get(val_peek(5).attributes.size()-1).setParameters(parameters);
                    Attribute ID = val_peek(5).attributes.get(val_peek(5).attributes.size()-1);
                    ID.setScopePROC(this.PROCscope);
                    yyval.tree = new SyntacticTreeCALL(val_peek(5).tree, ID, formalParameters);
                }
                break;
                case 43:
//#line 426 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Llamado a procedimiento con parametros");
                    String lexeme = val_peek(7).attributes.get(0).getLexeme();

                    val_peek(7).attributes.get(val_peek(7).attributes.size()-1).setUse(Use.llamado_procedimiento);
                    List<Parameter> formalParameters = checkIDPROC(val_peek(7).attributes, lexeme);

                    String scope = val_peek(7).attributes.get(0).getLexeme() + this.globalScope;
                    val_peek(7).attributes.get(val_peek(7).attributes.size()-1).setScopePROC(scope);

                    List<Type> types = new ArrayList<>();

                    Attribute attribute = this.checkID(val_peek(5).attributes, val_peek(5).attributes.get(0).getLexeme());
                    Parameter ID1 = new Parameter(attribute.getScope(), attribute.getType());
                    if(attribute != null)
                        types.add(attribute.getType());

                    attribute = this.checkID(val_peek(3).attributes, val_peek(3).attributes.get(0).getLexeme());
                    Parameter ID2 = new Parameter(attribute.getScope(), attribute.getType());
                    if(attribute != null)
                        types.add(attribute.getType());

                    attribute = this.checkID(val_peek(1).attributes, val_peek(1).attributes.get(0).getLexeme());
                    Parameter ID3 = new Parameter(attribute.getScope(), attribute.getType());
                    if(attribute != null)
                        types.add(attribute.getType());

                    this.checkParameters(formalParameters, types);

                    List<Parameter> parameters = new ArrayList<>();
                    parameters.add(ID1);
                    parameters.add(ID2);
                    parameters.add(ID3);
                    val_peek(7).attributes.get(val_peek(7).attributes.size()-1).setParameters(parameters);
                    Attribute ID = val_peek(7).attributes.get(val_peek(7).attributes.size()-1);
                    ID.setScopePROC(this.PROCscope);

                    yyval.tree = new SyntacticTreeCALL(val_peek(7).tree, ID, formalParameters);

                }
                break;
                case 44:
//#line 467 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Llamado a procedimiento sin parametros");
                    String lexeme = val_peek(2).attributes.get(0).getLexeme();

                    val_peek(2).attributes.get(val_peek(2).attributes.size()-1).setUse(Use.llamado_procedimiento);
                    List<Parameter> formalParameters = checkIDPROC(val_peek(2).attributes, lexeme);

                    String scope = val_peek(2).attributes.get(0).getLexeme() + this.globalScope;
                    val_peek(2).attributes.get(val_peek(2).attributes.size()-1).setScopePROC(scope);

                    List<Type> types = new ArrayList<>();

                    this.checkParameters(formalParameters, types);

                    Attribute ID = val_peek(2).attributes.get(val_peek(2).attributes.size()-1);
                    ID.setScopePROC(this.PROCscope);
                    yyval.tree = new SyntacticTreeCALL(val_peek(2).tree, ID);
                }
                break;
                case 45:
//#line 490 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia IF");
                    Attribute IF = new Attribute("IF");
                    yyval.tree = new SyntacticTreeIF(val_peek(2).tree, val_peek(1).tree, IF);
                }
                break;
                case 46:
//#line 496 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF - Se espera END_IF"); }
                break;
                case 47:
//#line 497 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF - Se espera cuerpo"); }
                break;
                case 48:
//#line 498 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF - Se espera condicion"); }
                break;
                case 49:
//#line 502 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia IF ELSE - Cuerpo");
                    Attribute CUERPO_IF_ELSE = new Attribute("CUERPO_IF_ELSE");
                    yyval.tree = new SyntacticTreeIFBODY(val_peek(1).tree, val_peek(0).tree, CUERPO_IF_ELSE);
                }
                break;
                case 50:
//#line 508 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia IF - Cuerpo");
                    Attribute CUERPO_IF = new Attribute("CUERPO_IF");
                    yyval.tree = new SyntacticTreeIFBODY(val_peek(0).tree, CUERPO_IF);
                }
                break;
                case 51:
//#line 517 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia IF - Condicion <");

                    Attribute MENOR = new Attribute("<");

                    yyval.tree = new SyntacticTreeIFCMP(val_peek(3).tree, val_peek(1).tree, MENOR);

                    if(!this.checkType(yyval.tree)){
                        addError("Error Semántico en linea "+ la.getNroLinea() +": Condicion IF < - Incompatibilidad de tipos");
                    }
                }
                break;
                case 52:
//#line 529 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +":Sentencia IF - Condicion >.");

                    Attribute MAYOR = new Attribute(">");

                    yyval.tree = new SyntacticTreeIFCMP(val_peek(3).tree, val_peek(1).tree, MAYOR);

                    if(!this.checkType(yyval.tree)){
                        addError("Error Semántico en linea "+ la.getNroLinea() +": Condicion IF > - Incompatibilidad de tipos");
                    }
                }
                break;
                case 53:
//#line 541 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia IF - Condicion ==.");

                    Attribute IGUAL = new Attribute("==");

                    yyval.tree = new SyntacticTreeIFCMP(val_peek(3).tree, val_peek(1).tree, IGUAL);

                    if(!this.checkType(yyval.tree)){
                        addError("Error Semántico en linea "+ la.getNroLinea() +": Condicion IF == - Incompatibilidad de tipos");
                    }
                }
                break;
                case 54:
//#line 553 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia IF - Condicion >=.");

                    Attribute MAYOR_IGUAL = new Attribute(">=");

                    yyval.tree = new SyntacticTreeIFCMP(val_peek(3).tree, val_peek(1).tree, MAYOR_IGUAL);

                    if(!this.checkType(yyval.tree)){
                        addError("Error Semántico en linea "+ la.getNroLinea() +": Condicion IF >= - Incompatibilidad de tipos");
                    }
                }
                break;
                case 55:
//#line 565 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia IF - Condicion <=.");

                    Attribute MENOR_IGUAL = new Attribute("<=");

                    yyval.tree = new SyntacticTreeIFCMP(val_peek(3).tree, val_peek(1).tree, MENOR_IGUAL);

                    if(!this.checkType(yyval.tree)){
                        addError("Error Semántico en linea "+ la.getNroLinea() +": Condicion IF <= - Incompatibilidad de tipos");
                    }
                }
                break;
                case 56:
//#line 577 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia IF - Condicion !=.");

                    Attribute DISTINTO = new Attribute("!=");

                    yyval.tree = new SyntacticTreeIFCMP(val_peek(3).tree, val_peek(1).tree, DISTINTO);

                    if(!this.checkType(yyval.tree)){
                        addError("Error Semántico en linea "+ la.getNroLinea() +": Condicion IF != - Incompatibilidad de tipos");
                    }
                }
                break;
                case 57:
//#line 589 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera )"); }
                break;
                case 58:
//#line 590 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera )"); }
                break;
                case 59:
//#line 591 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera )"); }
                break;
                case 60:
//#line 592 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera )"); }
                break;
                case 61:
//#line 593 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera )"); }
                break;
                case 62:
//#line 594 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera )"); }
                break;
                case 63:
//#line 596 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera expresion derecha"); }
                break;
                case 64:
//#line 597 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera expresion derecha"); }
                break;
                case 65:
//#line 598 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera expresion derecha"); }
                break;
                case 66:
//#line 599 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera expresion derecha"); }
                break;
                case 67:
//#line 600 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera expresion derecha"); }
                break;
                case 68:
//#line 601 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera expresion derecha"); }
                break;
                case 69:
//#line 603 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera comparador"); }
                break;
                case 70:
//#line 605 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera expresion izquierda"); }
                break;
                case 71:
//#line 610 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia IF - Bloque de sentencias");
                    Attribute attribute = new Attribute("BLOQUE THEN");
                    yyval.tree = new SyntacticTreeIFTHEN(val_peek(1).tree, attribute);
                }
                break;
                case 72:
//#line 616 "G08 - Gramatica - 25102020.y"
                {
                    Attribute attribute = new Attribute("BLOQUE THEN");
                    yyval.tree = new SyntacticTreeIFTHEN(val_peek(0).tree, attribute);
                }
                break;
                case 73:
//#line 621 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF bloque - Se espera } finalizacion BLOQUE IF");}
                break;
                case 74:
//#line 622 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF bloque - Se espera cuerpo_ejecutable");}
                break;
                case 75:
//#line 626 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia IF ELSE - bloque de sentencias ELSE");
                    Attribute attribute = new Attribute("BLOQUE ELSE");
                    yyval.tree = new SyntacticTreeIFELSE(val_peek(1).tree, attribute);
                }
                break;
                case 76:
//#line 632 "G08 - Gramatica - 25102020.y"
                {
                    Attribute attribute = new Attribute("BLOQUE ELSE");
                    yyval.tree = new SyntacticTreeIFELSE(val_peek(0).tree, attribute);
                }
                break;
                case 77:
//#line 637 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF ELSE - Se espera bloque de sentencias");}
                break;
                case 78:
//#line 641 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia FOR - Bloque de sentencias");
                    yyval.tree = val_peek(2).tree;
                }
                break;
                case 79:
//#line 646 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 80:
//#line 650 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR bloque - Se espera ;");}
                break;
                case 81:
//#line 651 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR bloque - Se espera } ");}
                break;
                case 82:
//#line 652 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR bloque - Se espera cuerpo_ejecutable ");}
                break;
                case 83:
//#line 656 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 84:
//#line 660 "G08 - Gramatica - 25102020.y"
                {
                    Attribute attribute = new Attribute("SENTENCIA");
                    yyval.tree = new SyntacticTreeBODY(val_peek(1).tree, val_peek(0).tree, attribute);
                }
                break;
                case 85:
//#line 668 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Asignacion");
                    Attribute attribute = new Attribute("=");
                    yyval.tree = new SyntacticTreeASIG(val_peek(2).tree, val_peek(0).tree, attribute);
                    if(!this.checkType(yyval.tree)){
                        addError("Error Semántico en linea "+ la.getNroLinea() +": Asignacion - Incompatibilidad de tipos");
                    }

                }
                break;
                case 86:
//#line 678 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Asignacion - Se espera expresion lado derecho");}
                break;
                case 87:
//#line 679 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Asignacion - Se espera =");}
                break;
                case 88:
//#line 680 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Asignacion - Se espera ID lado izquierdo");}
                break;
                case 89:
//#line 685 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia FOR");
                    Attribute headFor = new Attribute("INICIO FOR");
                    Attribute FOR = new Attribute("FOR");
                    Attribute bodyFor = new Attribute("CUERPO FOR");
                    SyntacticTree node = new SyntacticTreeFORBODY(val_peek(0).tree, val_peek(2).tree, bodyFor);
                    yyval.tree = new SyntacticTreeFORHEAD(val_peek(6).tree, new SyntacticTreeFOR(val_peek(4).tree, node, FOR), headFor);
                }
                break;
                case 90:
//#line 696 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": ASIGNACION_FOR");
                    String lexeme = val_peek(2).attributes.get(0).getLexeme();

                    Attribute ID = this.checkID(val_peek(2).attributes, lexeme);

                    Attribute NRO_ULONGINT = val_peek(0).attributes.get(val_peek(0).attributes.size()-1);
                    Attribute ASIGNACION = new Attribute("=");

                    yyval.tree = new SyntacticTreeFORASIG(new SyntacticTreeLeaf(null, null, ID), new SyntacticTreeLeaf(null, null, NRO_ULONGINT), ASIGNACION);

                    if(!this.checkType(yyval.tree)){
                        addError("Error Semántico en linea "+ la.getNroLinea() +": Asignacion - Incompatibilidad de tipos");
                    }
                }
                break;
                case 91:
//#line 712 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR asignacion - Se espera NRO_ULONGINT lado derecho"); }
                break;
                case 92:
//#line 713 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR asignacion - Se espera ="); }
                break;
                case 93:
//#line 714 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR asignacion - Se espera ID lado izquierdo"); }
                break;
                case 94:
//#line 718 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia FOR - Condicion <.");
                    String lexeme = val_peek(2).attributes.get(0).getLexeme();

                    Attribute attribute = this.checkID(val_peek(2).attributes, lexeme);

                    Attribute ID = val_peek(2).attributes.get(val_peek(2).attributes.size()-1);
                    ID.setFlag();
                    Attribute MENOR = new Attribute("<");

                    yyval.tree = new SyntacticTreeFORCMP(new SyntacticTreeLeaf(null, null, ID), val_peek(0).tree, MENOR);

                    if(!this.checkType(yyval.tree)){
                        addError("Error Semántico en linea "+ la.getNroLinea() +": Condicion FOR < - Incompatibilidad de tipos");
                    }
                }
                break;
                case 95:
//#line 735 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia FOR - Condicion >");
                    String lexeme = val_peek(2).attributes.get(0).getLexeme();

                    Attribute attribute = this.checkID(val_peek(2).attributes, lexeme);

                    Attribute ID = val_peek(2).attributes.get(val_peek(2).attributes.size()-1);
                    ID.setFlag();
                    Attribute MAYOR = new Attribute(">");

                    yyval.tree = new SyntacticTreeFORCMP(new SyntacticTreeLeaf(null, null, ID), val_peek(0).tree, MAYOR);

                    if(!this.checkType(yyval.tree)){
                        addError("Error Semántico en linea "+ la.getNroLinea() +": Condicion FOR > - Incompatibilidad de tipos");
                    }
                }
                break;
                case 96:
//#line 752 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia FOR - Condicion ==");
                    String lexeme = val_peek(2).attributes.get(0).getLexeme();

                    Attribute attribute = this.checkID(val_peek(2).attributes, lexeme);

                    Attribute ID = val_peek(2).attributes.get(val_peek(2).attributes.size()-1);
                    ID.setFlag();
                    Attribute MENOR_IGUAL = new Attribute("==");

                    yyval.tree = new SyntacticTreeFORCMP(new SyntacticTreeLeaf(null, null, ID), val_peek(0).tree, MENOR_IGUAL);

                    if(!this.checkType(yyval.tree)){
                        addError("Error Semántico en linea "+ la.getNroLinea() +": Condicion FOR == - Incompatibilidad de tipos");
                    }
                }
                break;
                case 97:
//#line 769 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia FOR - Condicion >=");
                    String lexeme = val_peek(2).attributes.get(0).getLexeme();

                    Attribute attribute = this.checkID(val_peek(2).attributes, lexeme);

                    Attribute ID = val_peek(2).attributes.get(val_peek(2).attributes.size()-1);
                    ID.setFlag();
                    Attribute MENOR_IGUAL = new Attribute(">=");

                    yyval.tree = new SyntacticTreeFORCMP(new SyntacticTreeLeaf(null, null, ID), val_peek(0).tree, MENOR_IGUAL);

                    if(!this.checkType(yyval.tree)){
                        addError("Error Semántico en linea "+ la.getNroLinea() +": Condicion FOR >= - Incompatibilidad de tipos");
                    }
                }
                break;
                case 98:
//#line 786 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia FOR - Condicion <=");
                    String lexeme = val_peek(2).attributes.get(0).getLexeme();

                    Attribute attribute = this.checkID(val_peek(2).attributes, lexeme);

                    Attribute ID = val_peek(2).attributes.get(val_peek(2).attributes.size()-1);
                    ID.setFlag();
                    Attribute MENOR_IGUAL = new Attribute("<=");

                    yyval.tree = new SyntacticTreeFORCMP(new SyntacticTreeLeaf(null, null, ID), val_peek(0).tree, MENOR_IGUAL);
                    if(!this.checkType(yyval.tree)){
                        addError("Error Semántico en linea "+ la.getNroLinea() +": Condicion FOR <= - Incompatibilidad de tipos");
                    }
                }
                break;
                case 99:
//#line 802 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia FOR - Condicion !=");
                    String lexeme = val_peek(2).attributes.get(0).getLexeme();

                    Attribute attribute = this.checkID(val_peek(2).attributes, lexeme);

                    Attribute ID = val_peek(2).attributes.get(val_peek(2).attributes.size()-1);
                    ID.setFlag();
                    Attribute DISTINTO = new Attribute("!=");

                    yyval.tree = new SyntacticTreeFORCMP(new SyntacticTreeLeaf(null, null, ID), val_peek(0).tree, DISTINTO);
                    if(!this.checkType(yyval.tree)){
                        addError("Error Semántico en linea "+ la.getNroLinea() +": Condicion FOR != - Incompatibilidad de tipos");
                    }
                }
                break;
                case 100:
//#line 820 "G08 - Gramatica - 25102020.y"
                {
                    Attribute attribute = new Attribute("UP");
                    yyval.tree  = new SyntacticTreeFORUP(new SyntacticTreeLeaf(null, null, val_peek(0).attributes.get(0)), attribute);
                }
                break;
                case 101:
//#line 825 "G08 - Gramatica - 25102020.y"
                {
                    Attribute attribute = new Attribute("DOWN");
                    yyval.tree  = new SyntacticTreeFORDOWN(new SyntacticTreeLeaf(null, null, val_peek(0).attributes.get(0)), attribute);
                }
                break;
                case 102:
//#line 830 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR decremento - Se espera NRO_ULONGINT"); }
                break;
                case 103:
//#line 831 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR incremento - Se espera NRO_ULONGINT"); }
                break;
                case 104:
//#line 832 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR incre/decre - Se espera UP/DOWN"); }
                break;
                case 105:
//#line 838 "G08 - Gramatica - 25102020.y"
                {
                    System.out.println(val_peek(1).attributes.get(0).getScope());
                    addRule("Linea "+ la.getNroLinea() +": Sentencia OUT");
                    val_peek(1).attributes.get(0).setFlag();
                    Attribute cadena = new Attribute(val_peek(1).attributes.get(0).getLexeme());
                    Attribute OUT = new Attribute("IMPRIMIR");
                    yyval.tree  = new SyntacticTreeOUT(new SyntacticTreeLeaf(null, null, cadena), OUT);
                }
                break;
                case 106:
//#line 847 "G08 - Gramatica - 25102020.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia OUT - Se espera ')'."); }
                break;
                case 107:
//#line 848 "G08 - Gramatica - 25102020.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia OUT - Se espera '('."); }
                break;
                case 108:
//#line 849 "G08 - Gramatica - 25102020.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia OUT - Se espera una cadena de caracteres luego de '('."); }
                break;
                case 109:
//#line 850 "G08 - Gramatica - 25102020.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia OUT - falta cadena"); }
                break;
                case 110:
//#line 851 "G08 - Gramatica - 25102020.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia OUT - Se espera '('."); }
                break;
                case 111:
//#line 857 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Conversion explicita");

                    if(!this.checkType(val_peek(1).tree)){
                        addError("Error Semántico en linea "+ la.getNroLinea() +": Conversion explicita - Incompatibilidad de tipos");
                        yyval.tree.getAttribute().setType(Type.ERROR);
                    }else{
                        if(!val_peek(3).tree.getType().getName().equals(val_peek(1).tree.getType().getName())){
                            Attribute attribute = new Attribute("CONVERSION");
                            /*$$.tree = new SyntacticTreeCONV($3.tree, $3.tree.getAttribute());
                             */
                            yyval.tree = new SyntacticTreeCONV(val_peek(1).tree, attribute);
                            yyval.tree.getAttribute().setType(val_peek(3).tree.getType());
                        }else{
                            addError("Error Semántico en linea "+ la.getNroLinea() +": Conversion explicita - Se quiere convertir a un mismo tipo");
                            yyval.tree.getAttribute().setType(Type.ERROR);
                        }
                    }
                    /*attribute.setUse($3.tree.getAttribute().getUse());
                     */
                    /*attribute.setFlag();
                     */
                }
                break;
                case 112:
//#line 878 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Conversion explicita - Se espera ')'."); }
                break;
                case 113:
//#line 879 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Conversion explicita - Se espera expresion.");}
                break;
                case 114:
//#line 885 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Suma");
                    Attribute attribute = new Attribute("+");
                    yyval.tree = new SyntacticTreeADD(val_peek(2).tree, val_peek(0).tree, attribute);
                    if(!this.checkType(yyval.tree)){
                        addError("Error Semántico en linea "+ la.getNroLinea() +": Suma - Incompatibilidad de tipos");
                    }
                }
                break;
                case 115:
//#line 894 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Resta");
                    Attribute attribute = new Attribute("-");
                    yyval.tree = new SyntacticTreeSUB(val_peek(2).tree, val_peek(0).tree, attribute);
                    if(!this.checkType(yyval.tree)){
                        addError("Error Semántico en linea "+ la.getNroLinea() +": Resta - Incompatibilidad de tipos");
                    }
                }
                break;
                case 116:
//#line 903 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 117:
//#line 907 "G08 - Gramatica - 25102020.y"
                { addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Suma - Se espera un termino luego del '+'."); }
                break;
                case 118:
//#line 908 "G08 - Gramatica - 25102020.y"
                { addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Suma - Se espera una expresion antes del '+'."); }
                break;
                case 119:
//#line 909 "G08 - Gramatica - 25102020.y"
                { addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Resta - Se espera un termino luego del '-'."); }
                break;
                case 120:
//#line 910 "G08 - Gramatica - 25102020.y"
                { addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Resta - Se espera una expresion antes del '-'."); }
                break;
                case 121:
//#line 914 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Multiplicacion");
                    Attribute attribute = new Attribute("*");
                    yyval.tree = new SyntacticTreeMUL(val_peek(2).tree, val_peek(0).tree, attribute);
                    if(!this.checkType(yyval.tree)){
                        addError("Error Semántico en linea "+ la.getNroLinea() +": Multiplicacion - Incompatibilidad de tipos");
                    }
                }
                break;
                case 122:
//#line 923 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Division");
                    Attribute attribute = new Attribute("/");
                    yyval.tree = new SyntacticTreeDIV(val_peek(2).tree, val_peek(0).tree, attribute);
                    if(!this.checkType(yyval.tree)){
                        addError("Error Semántico en linea "+ la.getNroLinea() +": Division - Incompatibilidad de tipos");
                    }
                }
                break;
                case 123:
//#line 933 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 124:
//#line 937 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Multiplicacion - Se espera un factor luego de * ");}
                break;
                case 125:
//#line 938 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Division - Se espera un factor luego de /");}
                break;
                case 126:
//#line 939 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Multiplicacion - Se espera un termino antes de * ");}
                break;
                case 127:
//#line 940 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Division - Se espera un termino antes de /");}
                break;
                case 128:
//#line 944 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": NRO_ULONGINT.");
                    val_peek(0).attributes.get(val_peek(0).attributes.size()-1).setFlag();
                    yyval.tree  = new SyntacticTreeLeaf(null, null, val_peek(0).attributes.get(val_peek(0).attributes.size()-1));
                }
                break;
                case 129:
//#line 950 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": NRO_DOUBLE.");
                    val_peek(0).attributes.get(val_peek(0).attributes.size()-1).setFlag();
                    yyval.tree  = new SyntacticTreeLeaf(null, null, val_peek(0).attributes.get(val_peek(0).attributes.size()-1));
                }
                break;
                case 130:
//#line 957 "G08 - Gramatica - 25102020.y"
                {
                    String lexeme = "-" + val_peek(0).attributes.get(0).getLexeme();
                    Attribute attribute = new Attribute(lexeme, lexeme,"NRO_DOUBLE", Type.DOUBLE, Use.constante);
                    attribute.setFlag();
                    boolean check = la.checkNegativeDouble(lexeme);
                    if(check){
                        addError("Error Sintáctico en línea "+ la.getNroLinea() +": DOUBLE fuera de rango.");
                    }else{
                        addRule("Linea "+ la.getNroLinea() +": NRO_DOUBLE negativo.");
                        la.addSymbolTable(lexeme, attribute);
                        val_peek(0).attributes.get(0).decreaseAmount();
                        String positiveLexeme = val_peek(0).attributes.get(0).getLexeme();
                        int amount = la.getAttribute(positiveLexeme).get(0).getAmount();
                        if(amount == 0){
                            la.getSt().deleteSymbolTableEntry(positiveLexeme);
                        }
                    }

                    yyval.tree  = new SyntacticTreeLeaf(null, null, attribute);
                }
                break;
                case 131:
//#line 979 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 132:
//#line 984 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 133:
//#line 990 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": ID PUNTO_PUNTO ID");

                    String lexemeIz = val_peek(2).attributes.get(0).getLexeme();

                    String lexemeDer = val_peek(0).attributes.get(0).getLexeme();

                    val_peek(2).attributes.get(val_peek(2).attributes.size()-1).setUse(Use.llamado_procedimiento_variable);

                    List<Parameter> parameters = this.checkIDPROC(val_peek(2).attributes, lexemeIz);
                    this.deleteSTEntry(lexemeIz, Use.llamado_procedimiento_variable);
                    Attribute attribute = this.checkID(val_peek(0).attributes, lexemeDer);

                    String scopePROC = val_peek(2).attributes.get(val_peek(2).attributes.size()-1).getScope();
                    val_peek(0).attributes = getListUse(val_peek(0).attributes, Use.variable);

                    Type type = this.checkIDdospuntosID(scopePROC, lexemeDer, val_peek(0).attributes);

                    if(attribute == null){
                        attribute = new Attribute(lexemeDer);
                        attribute.setType(Type.ERROR);
                    }

                    attribute.setFlag();
                    yyval.tree  = new SyntacticTreeLeaf(null, null, attribute);
                }
                break;
                case 134:
//#line 1017 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Tipo ID - Se espera ID luego de ::");}
                break;
                case 135:
//#line 1020 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": ID");

                    String lexeme = val_peek(0).attributes.get(0).getLexeme();

                    Attribute attribute = this.checkID(val_peek(0).attributes, lexeme);

                    if(attribute == null){
                        attribute = new Attribute(lexeme);
                        attribute.setType(Type.ERROR);
                    }

                    attribute.setFlag();
                    yyval.tree  = new SyntacticTreeLeaf(null, null, attribute);
                }
                break;
//#line 2174 "Parser.java"
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
