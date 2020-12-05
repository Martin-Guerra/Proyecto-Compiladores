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
            0,    1,    1,    2,    2,    3,    3,    3,    3,    7,
            7,    7,    8,    8,    8,    8,   10,   10,   11,   11,
            11,   11,   12,   12,   12,    9,    9,    9,   14,   14,
            14,   14,   13,   13,    4,    4,    4,    4,    4,    4,
            4,    4,    6,    6,    5,    5,   19,   19,   19,   19,
            19,   19,   19,   19,   15,   15,   15,   21,   21,   21,
            21,   20,   20,   20,   20,   20,   20,   20,   20,   20,
            20,   20,   20,   20,   20,   20,   20,   20,   20,   20,
            20,   22,   22,   22,   22,   23,   23,   23,   23,   26,
            26,   26,   26,   26,   25,   25,   17,   17,   17,   17,
            16,   16,   16,   16,   16,   16,   16,   16,   16,   28,
            28,   28,   28,   29,   29,   29,   29,   29,   29,   29,
            29,   29,   29,   29,   29,   29,   29,   29,   29,   29,
            29,   29,   30,   30,   30,   30,   30,   18,   18,   18,
            18,   18,   18,   31,   31,   31,   31,   24,   24,   24,
            24,   24,   24,   24,   32,   32,   32,   32,   32,   32,
            32,   33,   33,   33,   33,   33,   27,   27,   27,
    };
    final static short yylen[] = {                            2,
            2,    1,    2,    1,    2,    3,    2,    3,    3,    2,
            2,    2,    3,    3,    3,    3,    2,    2,    3,    5,
            7,    2,    3,    3,    3,    3,    3,    3,    1,    2,
            1,    2,    2,    2,    2,    1,    2,    2,    2,    2,
            2,    2,    1,    3,    1,    1,    4,    4,    6,    6,
            8,    8,    3,    3,    3,    3,    3,    3,    2,    3,
            2,    5,    5,    5,    5,    5,    5,    5,    5,    5,
            5,    5,    5,    5,    5,    5,    5,    5,    5,    3,
            2,    3,    1,    3,    2,    4,    2,    4,    3,    4,
            1,    4,    3,    2,    1,    2,    3,    3,    3,    3,
            9,    9,    9,    9,    9,    9,    9,    9,    9,    3,
            3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
            3,    3,    3,    3,    3,    2,    3,    3,    3,    3,
            3,    3,    2,    2,    2,    2,    2,    4,    4,    3,
            3,    4,    2,    4,    4,    4,    4,    3,    3,    1,
            3,    3,    3,    3,    3,    3,    1,    3,    3,    3,
            3,    1,    1,    2,    1,    1,    3,    3,    1,
    };
    final static short yydefred[] = {                         0,
            0,   45,    0,   46,    0,    0,    2,    0,    0,    0,
            0,    0,   43,    0,    0,    0,   12,    0,   18,   17,
            0,    0,    0,    0,    0,    0,    3,    4,    0,   36,
            0,    0,    0,    0,    0,    0,    7,    0,   10,    0,
            0,   31,   29,    0,    0,   22,    0,    0,    0,    8,
            0,    0,   16,    0,    0,    0,    0,    0,    0,    0,
            0,    0,  143,    0,    0,    0,    5,   35,   38,   37,
            40,   39,   42,   41,    0,    0,    9,    6,   15,   14,
            13,   28,   32,   30,   34,   33,   19,    0,    0,   26,
            44,    0,    0,    0,    0,    0,    0,  163,  162,    0,
            0,    0,  165,  166,    0,  157,  168,  167,    0,   53,
            0,   83,   57,    0,    0,    0,    0,   55,    0,    0,
            0,    0,    0,  140,  141,    0,    0,    0,    0,    0,
            0,    0,   25,   24,   23,   48,    0,    0,    0,    0,
            0,    0,  164,    0,    0,    0,    0,    0,   47,    0,
            0,   95,    0,   61,    0,   59,    0,   80,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,  139,  138,  142,   54,   20,    0,    0,    0,    0,
            0,    0,  160,  161,    0,    0,    0,    0,    0,    0,
            0,    0,  155,    0,  156,    0,    0,   82,   96,    0,
            87,   60,   58,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,  113,  112,  111,  110,    0,
            0,    0,    0,    0,    0,    0,    0,   50,    0,  147,
            146,  145,  144,   49,    0,    0,    0,   78,   72,   66,
            77,   71,   65,   76,   70,   64,   79,   73,   67,   74,
            68,   62,   75,   69,   63,    0,    0,    0,    0,    0,
            0,  126,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,   21,    0,    0,   88,   86,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,   52,   51,  137,
            136,  133,  135,  134,    0,    0,    0,    0,    0,    0,
            0,    0,    0,   91,  109,  108,  107,  106,  105,  104,
            103,    0,  101,    0,    0,    0,    0,   92,   90,
    };
    final static short yydgoto[] = {                          5,
            6,   26,   42,  324,  101,   16,    9,   10,   17,   11,
            18,   53,   48,   44,   29,   30,   31,   32,   33,   60,
            113,  114,  157,  102,  153,  325,  103,  121,  222,  301,
            104,  105,  106,
    };
    final static short yysindex[] = {                       323,
            -9,    0, -146,    0,    0,  578,    0, -123,   78,  -96,
            -22,  578,    0,  298,  578,   69,    0, -253,    0,    0,
            -4,  -29,  -12,    1,  -36,  590,    0,    0,   86,    0,
            -43,  -37,  -35,   79,  100,   98,    0,  578,    0, -253,
            -182,    0,    0,  -75, -227,    0, -172,  114,  350,    0,
            -88,  109,    0,  171,  -40,  -80,   -7,  385,  -32,  401,
            -42,  242,    0,  158,  -33,   68,    0,    0,    0,    0,
            0,    0,    0,    0,  -40,   64,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,  -71,   -4,    0,
            0,  -27, -219,  122,    0,  898,  -23,    0,    0,  190,
            215,   17,    0,    0,  164,    0,    0,    0,  166,    0,
            599,    0,    0, -198,  898,   36,   68,    0,  285,  139,
            413,  132,  -34,    0,    0,   46,  220,   -1,   17,  898,
            17,  391,    0,    0,    0,    0,  226,   67,   67,   70,
            70,  -40,    0,   93,   96,   99,  111,  123,    0,  231,
            68,    0,  468,    0,  410,    0, -186,    0,  126,  129,
            141,  153,  156,  159,  216,  233, -210,  348,  348,  348,
            354,    0,    0,    0,    0,    0,  -71,  519,  442,  164,
            164,  481,    0,    0,  485,  844,  -26,  442,  164,  442,
            164,  481,    0,  481,    0,  554,   68,    0,    0,  610,
            0,    0,    0,  852,    6,  860,   24,  868,   39,  876,
            52,  884,   77,  892,   80,    0,    0,    0,    0,  234,
            362,  470,  479,  490,  332,   41,  499,    0,  294,    0,
            0,    0,    0,    0,  302,    5,  498,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,  -40,  -40,  -40,  -40,  -40,
            -40,    0,  183,  188,  191,  196,  223,  229, -157, -157,
            -157, -157, -157, -120,    0,  529,  535,    0,    0,   17,
            17,   17,   17,   17,   17,  898,   17,  898,   17,  898,
            17,  898,   17,  898,   17,  898,   17,  311, -200, -195,
            580,  583,  584,  585,  586,  -38,   47,    0,    0,    0,
            0,    0,    0,    0,  423,  423,  423,  423,  423,  423,
            423,  435,  619,    0,    0,    0,    0,    0,    0,    0,
            0,   68,    0,   68,  568,   68,   76,    0,    0,
    };
    final static short yyrindex[] = {                         0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,  360,    0,    0,    0,  634,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,  576,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,  577,    0,
            0,    0,    0,    0,  -47,    0,   30,    0,    0,    0,
            0,   84,    0,    0,  477,    0,    0,    0,    0,    0,
            0,    0,    0,    0,  445,    0,  587,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,   87,  102,
            103,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            -130,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,  482,
            502,    0,    0,    0,    0,    0,    0,  507,  512,  532,
            537,  450,    0,  472,    0,    0,  -59,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0, -155,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,  105,
            106,  108,  116,  119,  146,  149,  195,  260,  279,  366,
            383,  384,  397,  398,  400,  403,  405,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    2,    0,   14,    0,   26,    0,    0,    0,
    };
    final static short yygindex[] = {                         0,
            0,    0,   42,   -5,  784,  629,    0,    0,  628,    0,
            632,  579,  -53,  635,    0,    0,    0,    0,    0,    0,
            591,    0,    0,  553, -171,  630,   -6,  593,  -67,  313,
            0,   34,  330,
    };
    final static int YYTABLESIZE=961;
    static short yytable[];
    static { yytable();}
    static void yytable(){
        yytable = new short[]{                         34,
                28,  102,  320,   65,  100,   34,   43,  127,   34,   43,
                57,   54,  100,   94,  233,   70,  145,   14,  146,   34,
                67,   72,   52,   74,  171,   93,   15,   59,  237,   85,
                14,   34,   43,  110,  132,   54,  134,   34,   84,  175,
                62,    7,   34,   84,  128,  218,  240,   27,  145,   82,
                146,   34,  112,   34,  112,  311,   55,  154,  135,  145,
                313,  146,  155,  156,  243,   55,  145,  219,  146,  202,
                169,  169,  169,   80,  169,  203,  169,  312,  145,  246,
                146,  145,  314,  146,   86,   83,  173,  322,  169,  169,
                83,  169,  249,   52,  145,  163,  146,  164,  298,  274,
                89,  223,  224,  226,   34,  152,   89,  128,  100,   19,
                20,  100,   51,   15,  100,  299,  300,  252,   15,  145,
                255,  146,  145,  227,  146,   85,  102,   50,   55,  278,
                85,   85,   35,   13,  339,  306,   37,  100,   94,   76,
                100,   51,  100,  100,   68,   99,   34,  199,   34,  201,
                93,  335,  299,  300,   87,  100,   78,   88,   77,   38,
                98,   97,  136,  131,  130,  137,  129,  100,   91,   93,
                100,  180,  181,  100,  132,  107,  108,  127,  189,  191,
                21,   22,    2,   23,   45,  100,    2,   24,   25,    3,
                169,    4,  165,   34,  152,    4,   84,  100,  124,  167,
                100,   84,   84,  100,  128,  147,  149,  124,   54,  150,
                148,   95,   69,  119,  120,   96,   97,    2,   71,   63,
                73,  170,  125,  115,   97,    2,    4,  100,   22,  232,
                34,  199,  100,   40,    4,  100,   98,   99,   64,  310,
                100,  126,   56,   58,   98,   99,   12,   13,   56,  109,
                133,   12,   13,  118,  144,   94,   61,  102,  102,  102,
                102,  239,  102,  102,  102,  102,  102,  100,  102,   94,
                94,   94,   94,  100,   94,   94,   94,   94,   94,  242,
                94,   93,   93,   93,   93,  169,   93,   93,   93,   93,
                93,  158,   93,  260,  245,  261,  273,  169,  169,  169,
                169,  172,  321,  159,  160,  161,  162,  248,   34,   34,
                34,   34,   34,   34,   34,   34,   34,  152,  123,  130,
                97,    2,  179,   97,    2,  182,   97,    2,   34,  199,
                4,  338,  251,    4,   75,  254,    4,  117,   46,  100,
                98,   99,   99,   98,   99,  165,   98,   99,  186,   97,
                2,  188,   97,    2,  190,   97,    2,   98,   97,    4,
                131,  130,    4,  129,   92,    4,  192,   97,    2,   98,
                99,  132,   98,   99,  127,   98,   99,    4,  194,   97,
                2,  204,   97,    2,  206,   97,    2,   98,   99,    4,
                272,  260,    4,  261,  166,    4,  208,   97,    2,   98,
                99,  128,   98,   99,  124,   98,   99,    4,  210,   97,
                2,  212,   97,    2,  214,   97,    2,   98,   99,    4,
                169,  267,    4,  268,  122,    4,   45,   94,    2,   98,
                99,  176,   98,   99,  177,   98,   99,    4,  286,   97,
                2,  116,  125,  288,   97,    2,  290,   97,    2,    4,
                118,  292,   97,    2,    4,  119,  120,    4,  114,   98,
                99,  121,    4,  115,   98,   99,  143,   98,   99,  183,
                184,  168,   98,   99,   90,  174,  193,  195,  294,   97,
                2,  142,  178,  140,  296,   97,    2,  196,  141,    4,
                158,  158,  158,  216,  158,    4,  158,  122,  120,   98,
                99,  256,  257,  258,  259,   98,   99,  111,  158,  158,
                217,  158,  159,  159,  159,  123,  159,  150,  159,  150,
                142,  150,  152,  111,  152,  230,  152,  145,  269,  146,
                159,  159,  200,  159,  117,  150,  150,  270,  150,  275,
                152,  152,  154,  152,  154,  323,  154,  151,  271,  151,
                276,  151,  148,   45,  148,    2,  148,  323,  277,  228,
                154,  154,  229,  154,    4,  151,  151,   81,  151,  308,
                148,  148,  153,  148,  153,  309,  153,  149,    1,  149,
                2,  149,  302,  303,  304,  305,  307,    3,  310,    4,
                153,  153,  198,  153,  234,  149,  149,  235,  149,  256,
                257,  258,  259,  220,  221,   89,   22,    2,   23,  225,
                221,  116,   24,   25,    3,  169,    4,  262,   79,   81,
                315,  122,  279,  316,  317,  318,  319,  129,  131,  263,
                264,  265,  266,    1,   11,   27,   36,   39,  116,  125,
                66,   22,   41,   23,    0,   56,    0,   24,   25,   49,
                118,    0,  119,  120,  123,  114,  117,   22,  121,   23,
                115,    0,    0,   24,   25,   66,   22,    0,   23,    0,
                0,    0,   24,   25,    0,    0,    0,    0,   66,   22,
                0,   23,    0,    0,    0,   24,   25,    0,    0,    0,
                332,   22,  337,   23,  185,    0,  187,   24,   25,    0,
                81,   81,    0,   81,    0,  158,    0,   81,   81,    0,
                0,  205,  207,  209,  211,  213,  215,  158,  158,  158,
                158,    0,    0,  197,   22,    0,   23,  159,    0,    0,
                24,   25,  150,    0,    0,    0,    0,  152,    0,  159,
                159,  159,  159,    0,  150,  150,  150,  150,    0,  152,
                152,  152,  152,   66,   22,    0,   23,  154,    0,    0,
                24,   25,  151,    0,    0,    0,    0,  148,    0,  154,
                154,  154,  154,    0,  151,  151,  151,  151,    0,  148,
                148,  148,  148,    8,    0,    0,    0,  153,    0,    8,
                0,    0,  149,    0,    0,    8,    0,   47,    8,  153,
                153,  153,  153,    0,  149,  149,  149,  149,  280,  281,
                282,  283,  284,  285,    0,  287,  289,  291,  293,  295,
                297,    8,    0,  336,   22,    0,   23,    8,    0,    0,
                24,   25,    8,   21,   22,    2,   23,   47,    0,    0,
                24,   25,    3,    0,    4,   66,   22,    0,   23,    0,
                0,    0,   24,   25,  151,   22,    0,   23,    0,    0,
                0,   24,   25,    0,    0,  236,   22,    0,   23,    0,
                0,   47,   24,   25,  334,   22,    0,   23,    0,    0,
                0,   24,   25,  142,  231,  140,  138,    0,  139,    0,
                141,  142,  238,  140,  138,    0,  139,    0,  141,  142,
                241,  140,  138,    0,  139,    0,  141,  142,  244,  140,
                138,    0,  139,    0,  141,  142,  247,  140,  138,    0,
                139,    0,  141,  142,  250,  140,  138,    0,  139,    0,
                141,  142,  253,  140,  138,    0,  139,  142,  141,  140,
                138,    0,  139,    0,  141,  326,  327,  328,  329,  330,
                331,  333,    0,    0,    0,    0,    0,    0,    0,    0,
                47,
        };
    }
    static short yycheck[];
    static { yycheck(); }
    static void yycheck() {
        yycheck = new short[] {                          6,
                6,    0,   41,   40,   45,   12,   12,   41,   15,   15,
                40,   59,   45,    0,   41,   59,   43,   40,   45,   26,
                26,   59,  276,   59,   59,    0,  123,   40,  200,  257,
                40,   38,   38,   41,   88,   40,  256,   44,   44,   41,
                40,    0,   49,   49,   40,  256,   41,    6,   43,  125,
                45,   58,   58,   60,   60,  256,   61,  256,  278,   43,
                256,   45,  261,  262,   41,   61,   43,  278,   45,  256,
                41,   42,   43,  256,   45,  262,   47,  278,   43,   41,
                45,   43,  278,   45,  257,   44,   41,   41,   59,   60,
                49,   62,   41,  276,   43,   60,   45,   62,  256,   59,
                256,  169,  170,  171,  111,  111,  262,   40,   45,  256,
                257,   45,   44,  123,   45,  273,  274,   41,  123,   43,
                41,   45,   43,  177,   45,  256,  125,   59,   61,  125,
                261,  262,  256,  257,   59,  256,   59,   45,  125,   61,
                45,   44,   59,   45,   59,   59,  153,  153,  155,  155,
                125,  323,  273,  274,   41,   45,   59,   44,   59,  256,
                59,   59,   41,   59,   59,   44,   59,   45,  257,   61,
                45,  138,  139,   45,   59,  256,  257,   59,  145,  146,
                256,  257,  258,  259,  256,   45,  258,  263,  264,  265,
                59,  267,   61,  200,  200,  267,  256,   45,   41,   61,
                45,  261,  262,   45,   59,   42,   41,   59,  256,   44,
                47,   41,  256,  256,  257,  256,  257,  258,  256,  256,
                256,  256,  256,  256,  257,  258,  267,   45,  276,  256,
                237,  237,   45,  256,  267,   45,  277,  278,  275,  278,
                45,  275,  272,  256,  277,  278,  256,  257,  272,  257,
                278,  256,  257,   59,   40,  257,  256,  256,  257,  258,
                259,  256,  261,  262,  263,  264,  265,   45,  267,  256,
                257,  258,  259,   45,  261,  262,  263,  264,  265,  256,
                267,  256,  257,  258,  259,  256,  261,  262,  263,  264,
                265,  256,  267,   60,  256,   62,  256,  268,  269,  270,
                271,  256,  256,  268,  269,  270,  271,  256,  315,  316,
                317,  318,  319,  320,  321,  322,  323,  323,   59,  256,
                257,  258,  256,  257,  258,  256,  257,  258,  335,  335,
                267,  256,  256,  267,  256,  256,  267,   59,   41,  256,
                277,  278,  256,  277,  278,   61,  277,  278,  256,  257,
                258,  256,  257,  258,  256,  257,  258,  256,  256,  267,
                256,  256,  267,  256,  256,  267,  256,  257,  258,  277,
                278,  256,  277,  278,  256,  277,  278,  267,  256,  257,
                258,  256,  257,  258,  256,  257,  258,  277,  278,  267,
                59,   60,  267,   62,  256,  267,  256,  257,  258,  277,
                278,  256,  277,  278,  256,  277,  278,  267,  256,  257,
                258,  256,  257,  258,  256,  257,  258,  277,  278,  267,
                61,   60,  267,   62,   59,  267,  256,  257,  258,  277,
                278,   41,  277,  278,   44,  277,  278,  267,  256,  257,
                258,   59,   59,  256,  257,  258,  256,  257,  258,  267,
                256,  256,  257,  258,  267,   59,   59,  267,   59,  277,
                278,   59,  267,   59,  277,  278,  277,  277,  278,  140,
                141,   59,  277,  278,  125,  256,  147,  148,  256,  257,
                258,   40,  257,   42,  256,  257,  258,  257,   47,  267,
                41,   42,   43,  278,   45,  267,   47,  256,  257,  277,
                278,  268,  269,  270,  271,  277,  278,  123,   59,   60,
                278,   62,   41,   42,   43,  256,   45,   41,   47,   43,
                40,   45,   41,  123,   43,   41,   45,   43,   59,   45,
                59,   60,  123,   62,  256,   59,   60,   59,   62,   41,
                59,   60,   41,   62,   43,  123,   45,   41,   59,   43,
                257,   45,   41,  256,   43,  258,   45,  123,  257,   41,
                59,   60,   44,   62,  267,   59,   60,  123,   62,   41,
                59,   60,   41,   62,   43,   41,   45,   41,  256,   43,
                258,   45,  270,  271,  272,  273,  274,  265,  278,  267,
                59,   60,  125,   62,   41,   59,   60,   44,   62,  268,
                269,  270,  271,  256,  257,  256,  257,  258,  259,  256,
                257,   59,  263,  264,  265,  256,  267,  256,   40,   41,
                41,  256,  125,   41,   41,   41,   41,   75,   76,  268,
                269,  270,  271,    0,   59,   59,    8,   10,  256,  256,
                256,  257,   11,  259,   -1,   59,   -1,  263,  264,   15,
                60,   -1,  256,  256,   62,  256,  256,  257,  256,  259,
                256,   -1,   -1,  263,  264,  256,  257,   -1,  259,   -1,
                -1,   -1,  263,  264,   -1,   -1,   -1,   -1,  256,  257,
                -1,  259,   -1,   -1,   -1,  263,  264,   -1,   -1,   -1,
                256,  257,  125,  259,  142,   -1,  144,  263,  264,   -1,
                256,  257,   -1,  259,   -1,  256,   -1,  263,  264,   -1,
                -1,  159,  160,  161,  162,  163,  164,  268,  269,  270,
                271,   -1,   -1,  256,  257,   -1,  259,  256,   -1,   -1,
                263,  264,  256,   -1,   -1,   -1,   -1,  256,   -1,  268,
                269,  270,  271,   -1,  268,  269,  270,  271,   -1,  268,
                269,  270,  271,  256,  257,   -1,  259,  256,   -1,   -1,
                263,  264,  256,   -1,   -1,   -1,   -1,  256,   -1,  268,
                269,  270,  271,   -1,  268,  269,  270,  271,   -1,  268,
                269,  270,  271,    0,   -1,   -1,   -1,  256,   -1,    6,
                -1,   -1,  256,   -1,   -1,   12,   -1,   14,   15,  268,
                269,  270,  271,   -1,  268,  269,  270,  271,  256,  257,
                258,  259,  260,  261,   -1,  263,  264,  265,  266,  267,
                268,   38,   -1,  256,  257,   -1,  259,   44,   -1,   -1,
                263,  264,   49,  256,  257,  258,  259,   54,   -1,   -1,
                263,  264,  265,   -1,  267,  256,  257,   -1,  259,   -1,
                -1,   -1,  263,  264,  256,  257,   -1,  259,   -1,   -1,
                -1,  263,  264,   -1,   -1,  256,  257,   -1,  259,   -1,
                -1,   88,  263,  264,  256,  257,   -1,  259,   -1,   -1,
                -1,  263,  264,   40,   41,   42,   43,   -1,   45,   -1,
                47,   40,   41,   42,   43,   -1,   45,   -1,   47,   40,
                41,   42,   43,   -1,   45,   -1,   47,   40,   41,   42,
                43,   -1,   45,   -1,   47,   40,   41,   42,   43,   -1,
                45,   -1,   47,   40,   41,   42,   43,   -1,   45,   -1,
                47,   40,   41,   42,   43,   -1,   45,   40,   47,   42,
                43,   -1,   45,   -1,   47,  316,  317,  318,  319,  320,
                321,  322,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                177,
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
            "lista_sentencias_ejecutables : sent_ejecutable",
            "lista_sentencias_ejecutables : lista_sentencias_ejecutables sent_ejecutable",
            "sent_declarativa : tipo lista_variables ';'",
            "sent_declarativa : procedimiento ';'",
            "sent_declarativa : error lista_variables ';'",
            "sent_declarativa : tipo error ';'",
            "procedimiento : encabezado cuerpo_procedimiento",
            "procedimiento : encabezado error",
            "procedimiento : error cuerpo_procedimiento",
            "encabezado : encabezado_PROC parametro_PROC asignacion_NA",
            "encabezado : encabezado_PROC parametro_PROC error",
            "encabezado : encabezado_PROC error asignacion_NA",
            "encabezado : error parametro_PROC asignacion_NA",
            "encabezado_PROC : PROC ID",
            "encabezado_PROC : PROC error",
            "parametro_PROC : '(' parametro ')'",
            "parametro_PROC : '(' parametro ',' parametro ')'",
            "parametro_PROC : '(' parametro ',' parametro ',' parametro ')'",
            "parametro_PROC : '(' ')'",
            "asignacion_NA : NA '=' NRO_ULONGINT",
            "asignacion_NA : NA '=' error",
            "asignacion_NA : NA error NRO_ULONGINT",
            "cuerpo_procedimiento : '{' bloque_procedimiento '}'",
            "cuerpo_procedimiento : '{' bloque_procedimiento error",
            "cuerpo_procedimiento : error bloque_procedimiento '}'",
            "bloque_procedimiento : sent_ejecutable",
            "bloque_procedimiento : bloque_procedimiento sent_ejecutable",
            "bloque_procedimiento : sent_declarativa",
            "bloque_procedimiento : bloque_procedimiento sent_declarativa",
            "parametro : tipo ID",
            "parametro : error ID",
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
            "llamado_PROC : error '(' ID ')'",
            "llamado_PROC : ID '(' ID ',' ID ')'",
            "llamado_PROC : error '(' ID ',' ID ')'",
            "llamado_PROC : ID '(' ID ',' ID ',' ID ')'",
            "llamado_PROC : error '(' ID ',' ID ',' ID ')'",
            "llamado_PROC : ID '(' ')'",
            "llamado_PROC : error '(' ')'",
            "sentencia_if : IF condicion_IF cuerpo",
            "sentencia_if : IF condicion_IF error",
            "sentencia_if : IF error cuerpo",
            "cuerpo : bloque_IF bloque_else END_IF",
            "cuerpo : bloque_IF END_IF",
            "cuerpo : bloque_IF bloque_else error",
            "cuerpo : bloque_IF error",
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
            "bloque_else : ELSE '{' error '}'",
            "bloque_else : ELSE '{' error",
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
            "sentencia_control : FOR '(' asignacion_FOR ';' comparacion_FOR ';' incr_decr ')' error",
            "sentencia_control : FOR '(' asignacion_FOR ';' comparacion_FOR ';' incr_decr error bloque_FOR",
            "sentencia_control : FOR '(' asignacion_FOR ';' comparacion_FOR ';' error ')' bloque_FOR",
            "sentencia_control : FOR '(' asignacion_FOR ';' comparacion_FOR error incr_decr ')' bloque_FOR",
            "sentencia_control : FOR '(' asignacion_FOR ';' error ';' incr_decr ')' bloque_FOR",
            "sentencia_control : FOR '(' asignacion_FOR error comparacion_FOR ';' incr_decr ')' bloque_FOR",
            "sentencia_control : FOR '(' error ';' comparacion_FOR ';' incr_decr ')' bloque_FOR",
            "sentencia_control : FOR error asignacion_FOR ';' comparacion_FOR ';' incr_decr ')' bloque_FOR",
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
            "conversion_explicita : error '(' expresion ')'",
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

//#line 1089 "G08 - Gramatica - 25102020.y"


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
        if(this.syntacticTree != null){
            this.syntacticTree.printTree(this.syntacticTree);
            return this.syntacticTree.getPrintTree();
        }
        return "";
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
            if(attribute.getUse() != null){
                if(attribute.getUse().equals(use)){
                    aux.add(attribute);
                }
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
    //#line 953 "Parser.java"
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
//#line 41 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 5:
//#line 45 "G08 - Gramatica - 25102020.y"
                {
                    Attribute attribute = new Attribute("LISTA SENTENCIAS");
                    yyval.tree = new SyntacticTreeSentence(val_peek(1).tree, val_peek(0).tree, attribute);
                }
                break;
                case 6:
//#line 53 "G08 - Gramatica - 25102020.y"
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
                case 7:
//#line 90 "G08 - Gramatica - 25102020.y"
                {
                    this.decreaseScope();
                    this.counter--;
                    this.PROCtreesAux.get(this.counter).setLeft(val_peek(1).tree);
                    yyval.tree = null;

                    this.PROCtrees.add(this.PROCtreesAux.get(this.PROCtreesAux.size()-1));
                    this.PROCtreesAux.remove(this.PROCtreesAux.size()-1);

                }
                break;
                case 8:
//#line 101 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia declarativa - Falta definir el tipo de la/s VARIABLE/S."); }
                break;
                case 9:
//#line 103 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia declarativa - Falta definir la/s VARIABLE/S."); }
                break;
                case 10:
//#line 108 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Procedimiento");
                    this.sa.deleteNA();
                    Attribute attribute = new Attribute("INICIO PROCEDIMIENTO");
                    attribute.setUse(Use.cuerpo_procedimiento);
                    yyval.tree = new SyntacticTreePROCHEAD(val_peek(0).tree, attribute);
                }
                break;
                case 11:
//#line 116 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Procedimiento - Se espera CUERPO del PROCEDIMIENTO"); }
                break;
                case 12:
//#line 117 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Procedimiento - Se espera ENCABEZADO del PROCEDIMIENTO"); }
                break;
                case 13:
//#line 121 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Encabezado procedimiento");
                }
                break;
                case 14:
//#line 125 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Procedimiento - Se espera ASIGNACION NA"); }
                break;
                case 15:
//#line 126 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Procedimiento - Se esperan '(' parametro ')' "); }
                break;
                case 16:
//#line 127 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Procedimiento - Se espera PROC ID"); }
                break;
                case 17:
//#line 131 "G08 - Gramatica - 25102020.y"
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
                case 18:
//#line 151 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Procedimiento - Se espera ID"); }
                break;
                case 19:
//#line 155 "G08 - Gramatica - 25102020.y"
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
                case 20:
//#line 172 "G08 - Gramatica - 25102020.y"
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
                case 21:
//#line 197 "G08 - Gramatica - 25102020.y"
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
                case 23:
//#line 228 "G08 - Gramatica - 25102020.y"
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
                case 24:
//#line 241 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Asignacion NA procedimiento - Se espera NRO_ULONGINT"); }
                break;
                case 25:
//#line 242 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Asignacion NA procedimiento - Se espera ="); }
                break;
                case 26:
//#line 246 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(1).tree;
                }
                break;
                case 27:
//#line 250 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Cuerpo procedimiento - Se espera }"); }
                break;
                case 28:
//#line 251 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Cuerpo procedimiento - Se espera }"); }
                break;
                case 29:
//#line 255 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 30:
//#line 259 "G08 - Gramatica - 25102020.y"
                {
                    Attribute attribute = new Attribute("SENTENCIA EJECUTABLE");
                    attribute.setUse(Use.cuerpo_procedimiento);
                    yyval.tree = new SyntacticTreeBODY(val_peek(1).tree, val_peek(0).tree, attribute);
                }
                break;
                case 31:
//#line 265 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 32:
//#line 269 "G08 - Gramatica - 25102020.y"
                {
                    Attribute attribute = new Attribute("SENTENCIA DECLARATIVA");
                    attribute.setUse(Use.cuerpo_procedimiento);
                    yyval.tree = new SyntacticTreeBODY(val_peek(1).tree, val_peek(0).tree, attribute);
                }
                break;
                case 33:
//#line 278 "G08 - Gramatica - 25102020.y"
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
                case 34:
//#line 291 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Parametros - Se espera tipo parametro procedimiento"); }
                break;
                case 35:
//#line 296 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(1).tree;
                }
                break;
                case 36:
//#line 301 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 37:
//#line 306 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(1).tree;
                }
                break;
                case 38:
//#line 309 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia ejecutable asignacion - Falta ;"); }
                break;
                case 39:
//#line 312 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(1).tree;
                }
                break;
                case 40:
//#line 315 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia ejecutable OUT - Falta ;"); }
                break;
                case 41:
//#line 318 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(1).tree;
                }
                break;
                case 42:
//#line 322 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia ejecutable llamado a procedimiento - Falta ;"); }
                break;
                case 43:
//#line 329 "G08 - Gramatica - 25102020.y"
                {
                    yyval.attributesSetteable = new ArrayList<>();
                    String lexeme = val_peek(0).attributes.get(0).getLexeme();
                    val_peek(0).attributes.get(val_peek(0).attributes.size()-1).setFlag();
                    yyval.attributesSetteable.add(lexeme);
                }
                break;
                case 44:
//#line 337 "G08 - Gramatica - 25102020.y"
                {
                    yyval = val_peek(2);
                    String lexeme = val_peek(0).attributes.get(0).getLexeme();
                    val_peek(0).attributes.get(val_peek(0).attributes.size()-1).setFlag();
                    yyval.attributesSetteable.add(lexeme);
                }
                break;
                case 45:
//#line 346 "G08 - Gramatica - 25102020.y"
                {
                    yyval.type = Type.ULONGINT;
                    Attribute attribute = new Attribute("ULONGINT");
                    attribute.setType(Type.ULONGINT);
                    yyval.tree  = new SyntacticTreeCONV(null, null, attribute);

                }
                break;
                case 46:
//#line 354 "G08 - Gramatica - 25102020.y"
                {
                    yyval.type = Type.DOUBLE;
                    Attribute attribute = new Attribute("DOUBLE");
                    attribute.setType(Type.DOUBLE);
                    yyval.tree = new SyntacticTreeCONV(null, null, attribute);
                }
                break;
                case 47:
//#line 364 "G08 - Gramatica - 25102020.y"
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
                case 48:
//#line 396 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia ejecutable llamado a procedimiento - Falta nombre procedimiento"); }
                break;
                case 49:
//#line 399 "G08 - Gramatica - 25102020.y"
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
                case 50:
//#line 433 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia ejecutable llamado a procedimiento - Falta nombre procedimiento"); }
                break;
                case 51:
//#line 436 "G08 - Gramatica - 25102020.y"
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
                case 52:
//#line 476 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia ejecutable llamado a procedimiento - Falta nombre procedimiento"); }
                break;
                case 53:
//#line 479 "G08 - Gramatica - 25102020.y"
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
                case 54:
//#line 497 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia ejecutable llamado a procedimiento - Falta nombre procedimiento"); }
                break;
                case 55:
//#line 503 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia IF");
                    Attribute IF = new Attribute("IF");
                    yyval.tree = new SyntacticTreeIF(val_peek(1).tree, val_peek(0).tree, IF);
                }
                break;
                case 56:
//#line 509 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF - Se espera cuerpo"); }
                break;
                case 57:
//#line 510 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF - Se espera condicion"); }
                break;
                case 58:
//#line 514 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia IF ELSE - Cuerpo");
                    Attribute CUERPO_IF_ELSE = new Attribute("CUERPO_IF_ELSE");
                    yyval.tree = new SyntacticTreeIFBODY(val_peek(2).tree, val_peek(1).tree, CUERPO_IF_ELSE);
                }
                break;
                case 59:
//#line 521 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia IF - Cuerpo");
                    Attribute CUERPO_IF = new Attribute("CUERPO_IF");
                    yyval.tree = new SyntacticTreeIFBODY(val_peek(1).tree, CUERPO_IF);
                }
                break;
                case 60:
//#line 527 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF ELSE bloque - Se espera END_IF"); }
                break;
                case 61:
//#line 528 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF bloque - Se espera END_IF"); }
                break;
                case 62:
//#line 533 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia IF - Condicion <");

                    Attribute MENOR = new Attribute("<");

                    yyval.tree = new SyntacticTreeIFCMP(val_peek(3).tree, val_peek(1).tree, MENOR);

                    if(!this.checkType(yyval.tree)){
                        addError("Error Semántico en linea "+ la.getNroLinea() +": Condicion IF < - Incompatibilidad de tipos");
                    }
                }
                break;
                case 63:
//#line 546 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +":Sentencia IF - Condicion >.");

                    Attribute MAYOR = new Attribute(">");

                    yyval.tree = new SyntacticTreeIFCMP(val_peek(3).tree, val_peek(1).tree, MAYOR);

                    if(!this.checkType(yyval.tree)){
                        addError("Error Semántico en linea "+ la.getNroLinea() +": Condicion IF > - Incompatibilidad de tipos");
                    }
                }
                break;
                case 64:
//#line 558 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia IF - Condicion ==.");

                    Attribute IGUAL = new Attribute("==");

                    yyval.tree = new SyntacticTreeIFCMP(val_peek(3).tree, val_peek(1).tree, IGUAL);

                    if(!this.checkType(yyval.tree)){
                        addError("Error Semántico en linea "+ la.getNroLinea() +": Condicion IF == - Incompatibilidad de tipos");
                    }
                }
                break;
                case 65:
//#line 570 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia IF - Condicion >=.");

                    Attribute MAYOR_IGUAL = new Attribute(">=");

                    yyval.tree = new SyntacticTreeIFCMP(val_peek(3).tree, val_peek(1).tree, MAYOR_IGUAL);

                    if(!this.checkType(yyval.tree)){
                        addError("Error Semántico en linea "+ la.getNroLinea() +": Condicion IF >= - Incompatibilidad de tipos");
                    }
                }
                break;
                case 66:
//#line 582 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia IF - Condicion <=.");

                    Attribute MENOR_IGUAL = new Attribute("<=");

                    yyval.tree = new SyntacticTreeIFCMP(val_peek(3).tree, val_peek(1).tree, MENOR_IGUAL);

                    if(!this.checkType(yyval.tree)){
                        addError("Error Semántico en linea "+ la.getNroLinea() +": Condicion IF <= - Incompatibilidad de tipos");
                    }
                }
                break;
                case 67:
//#line 594 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia IF - Condicion !=.");

                    Attribute DISTINTO = new Attribute("!=");

                    yyval.tree = new SyntacticTreeIFCMP(val_peek(3).tree, val_peek(1).tree, DISTINTO);

                    if(!this.checkType(yyval.tree)){
                        addError("Error Semántico en linea "+ la.getNroLinea() +": Condicion IF != - Incompatibilidad de tipos");
                    }
                }
                break;
                case 68:
//#line 606 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera )"); }
                break;
                case 69:
//#line 607 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera )"); }
                break;
                case 70:
//#line 608 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera )"); }
                break;
                case 71:
//#line 609 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera )"); }
                break;
                case 72:
//#line 610 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera )"); }
                break;
                case 73:
//#line 611 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera )"); }
                break;
                case 74:
//#line 613 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera expresion derecha"); }
                break;
                case 75:
//#line 614 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera expresion derecha"); }
                break;
                case 76:
//#line 615 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera expresion derecha"); }
                break;
                case 77:
//#line 616 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera expresion derecha"); }
                break;
                case 78:
//#line 617 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera expresion derecha"); }
                break;
                case 79:
//#line 618 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera expresion derecha"); }
                break;
                case 80:
//#line 620 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera comparador"); }
                break;
                case 81:
//#line 622 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera condicion"); }
                break;
                case 82:
//#line 627 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia IF - Bloque de sentencias");
                    Attribute attribute = new Attribute("BLOQUE THEN");
                    yyval.tree = new SyntacticTreeIFTHEN(val_peek(1).tree, attribute);
                }
                break;
                case 83:
//#line 635 "G08 - Gramatica - 25102020.y"
                {
                    Attribute attribute = new Attribute("BLOQUE THEN");
                    yyval.tree = new SyntacticTreeIFTHEN(val_peek(0).tree, attribute);
                }
                break;
                case 84:
//#line 640 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF bloque - Se espera } finalizacion BLOQUE IF");}
                break;
                case 85:
//#line 641 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF bloque - Se espera cuerpo_ejecutable");}
                break;
                case 86:
//#line 645 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia IF ELSE - bloque de sentencias ELSE");
                    Attribute attribute = new Attribute("BLOQUE ELSE");
                    yyval.tree = new SyntacticTreeIFELSE(val_peek(1).tree, attribute);
                }
                break;
                case 87:
//#line 651 "G08 - Gramatica - 25102020.y"
                {
                    Attribute attribute = new Attribute("BLOQUE ELSE");
                    yyval.tree = new SyntacticTreeIFELSE(val_peek(0).tree, attribute);
                }
                break;
                case 88:
//#line 656 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF ELSE - Se espera bloque de sentencias");}
                break;
                case 89:
//#line 657 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF ELSE - Se espera }");}
                break;
                case 90:
//#line 661 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia FOR - Bloque de sentencias");
                    yyval.tree = val_peek(2).tree;
                }
                break;
                case 91:
//#line 666 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 92:
//#line 670 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR bloque - Se espera ;");}
                break;
                case 93:
//#line 671 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR bloque - Se espera } ");}
                break;
                case 94:
//#line 672 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR bloque - Se espera cuerpo_ejecutable ");}
                break;
                case 95:
//#line 676 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 96:
//#line 680 "G08 - Gramatica - 25102020.y"
                {
                    Attribute attribute = new Attribute("SENTENCIA");
                    yyval.tree = new SyntacticTreeBODY(val_peek(1).tree, val_peek(0).tree, attribute);
                }
                break;
                case 97:
//#line 688 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Asignacion");
                    Attribute attribute = new Attribute("=");

                    yyval.tree = new SyntacticTreeASIG(val_peek(2).tree, val_peek(0).tree, attribute);

                    if(!this.checkType(yyval.tree)){
                        addError("Error Semántico en linea "+ la.getNroLinea() +": Asignacion - Incompatibilidad de tipos");
                    }



                }
                break;
                case 98:
//#line 702 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Asignacion - Se espera expresion lado derecho");}
                break;
                case 99:
//#line 703 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Asignacion - Se espera =");}
                break;
                case 100:
//#line 704 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Asignacion - Se espera ID lado izquierdo");}
                break;
                case 101:
//#line 709 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia FOR");
                    Attribute headFor = new Attribute("INICIO FOR");
                    Attribute FOR = new Attribute("FOR");
                    Attribute bodyFor = new Attribute("CUERPO FOR");
                    SyntacticTree node = new SyntacticTreeFORBODY(val_peek(0).tree, val_peek(2).tree, bodyFor);
                    yyval.tree = new SyntacticTreeFORHEAD(val_peek(6).tree, new SyntacticTreeFOR(val_peek(4).tree, node, FOR), headFor);
                }
                break;
                case 102:
//#line 718 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": FOR - Se espera bloque de sentencias");}
                break;
                case 103:
//#line 719 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": FOR - Se espera ')'");}
                break;
                case 104:
//#line 720 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": FOR - Se espera incremento/decremento");}
                break;
                case 105:
//#line 721 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": FOR - Se espera ';' entre la comparacion y el incremento/decremento");}
                break;
                case 106:
//#line 722 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": FOR - Se espera comparación");}
                break;
                case 107:
//#line 723 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": FOR - Se espera ';' entre la asignacion y la comparacion");}
                break;
                case 108:
//#line 724 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": FOR - Se espera asignacion");}
                break;
                case 109:
//#line 725 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": FOR - Se espera '('");}
                break;
                case 110:
//#line 729 "G08 - Gramatica - 25102020.y"
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
                case 111:
//#line 745 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR asignacion - Se espera NRO_ULONGINT lado derecho"); }
                break;
                case 112:
//#line 746 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR asignacion - Se espera ="); }
                break;
                case 113:
//#line 747 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR asignacion - Se espera ID lado izquierdo"); }
                break;
                case 114:
//#line 751 "G08 - Gramatica - 25102020.y"
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
                case 115:
//#line 768 "G08 - Gramatica - 25102020.y"
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
                case 116:
//#line 785 "G08 - Gramatica - 25102020.y"
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
                case 117:
//#line 802 "G08 - Gramatica - 25102020.y"
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
                case 118:
//#line 819 "G08 - Gramatica - 25102020.y"
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
                case 119:
//#line 835 "G08 - Gramatica - 25102020.y"
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
                case 120:
//#line 851 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera expresion lado derecho comparacion"); }
                break;
                case 121:
//#line 852 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera expresion lado derecho comparacion"); }
                break;
                case 122:
//#line 853 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera expresion lado derecho comparacion"); }
                break;
                case 123:
//#line 854 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera expresion lado derecho comparacion"); }
                break;
                case 124:
//#line 855 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera expresion lado derecho comparacion"); }
                break;
                case 125:
//#line 856 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera expresion lado derecho comparacion"); }
                break;
                case 126:
//#line 858 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera comparador"); }
                break;
                case 127:
//#line 860 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera ID lado izquierdo comparacion"); }
                break;
                case 128:
//#line 861 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera ID lado izquierdo comparacion"); }
                break;
                case 129:
//#line 862 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera ID lado izquierdo comparacion"); }
                break;
                case 130:
//#line 863 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera ID lado izquierdo comparacion"); }
                break;
                case 131:
//#line 864 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera ID lado izquierdo comparacion"); }
                break;
                case 132:
//#line 865 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera ID lado izquierdo comparacion"); }
                break;
                case 133:
//#line 869 "G08 - Gramatica - 25102020.y"
                {
                    Attribute attribute = new Attribute("UP");
                    yyval.tree  = new SyntacticTreeFORUP(new SyntacticTreeLeaf(null, null, val_peek(0).attributes.get(0)), attribute);
                }
                break;
                case 134:
//#line 874 "G08 - Gramatica - 25102020.y"
                {
                    Attribute attribute = new Attribute("DOWN");
                    yyval.tree  = new SyntacticTreeFORDOWN(new SyntacticTreeLeaf(null, null, val_peek(0).attributes.get(0)), attribute);
                }
                break;
                case 135:
//#line 879 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR decremento - Se espera NRO_ULONGINT"); }
                break;
                case 136:
//#line 880 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR incremento - Se espera NRO_ULONGINT"); }
                break;
                case 137:
//#line 881 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR incre/decre - Se espera UP/DOWN"); }
                break;
                case 138:
//#line 887 "G08 - Gramatica - 25102020.y"
                {
                    System.out.println(val_peek(1).attributes.get(0).getScope());
                    addRule("Linea "+ la.getNroLinea() +": Sentencia OUT");
                    val_peek(1).attributes.get(0).setFlag();
                    Attribute cadena = val_peek(1).attributes.get(0);
                    Attribute OUT = new Attribute("IMPRIMIR");
                    yyval.tree  = new SyntacticTreeOUT(new SyntacticTreeLeaf(null, null, cadena), OUT);
                }
                break;
                case 139:
//#line 896 "G08 - Gramatica - 25102020.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia OUT - Se espera ')'."); }
                break;
                case 140:
//#line 897 "G08 - Gramatica - 25102020.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia OUT - Se espera '('."); }
                break;
                case 141:
//#line 898 "G08 - Gramatica - 25102020.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia OUT - Se espera una cadena de caracteres luego de '('."); }
                break;
                case 142:
//#line 899 "G08 - Gramatica - 25102020.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia OUT - falta cadena"); }
                break;
                case 143:
//#line 900 "G08 - Gramatica - 25102020.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia OUT - Se espera '('."); }
                break;
                case 144:
//#line 906 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Conversion explicita");

                    Attribute attribute = new Attribute("CONVERSION");
                    yyval.tree = new SyntacticTreeCONV(val_peek(1).tree, attribute);

                    if(!this.checkType(val_peek(1).tree)){
                        addError("Error Semántico en linea "+ la.getNroLinea() +": Conversion explicita - Incompatibilidad de tipos");
                        yyval.tree.getAttribute().setType(Type.ERROR);
                    }else{
                        if(!val_peek(3).tree.getType().getName().equals(val_peek(1).tree.getType().getName()))
                            yyval.tree.getAttribute().setType(val_peek(3).tree.getType());
                        else{
                            addError("Error Semántico en linea "+ la.getNroLinea() +": Conversion explicita - Se quiere convertir a un mismo tipo");
                            yyval.tree.getAttribute().setType(Type.ERROR);
                        }
                    }
                }
                break;
                case 145:
//#line 925 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Conversion explicita - Se espera ')'."); }
                break;
                case 146:
//#line 926 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Conversion explicita - Se espera expresion.");}
                break;
                case 147:
//#line 927 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Conversion explicita - Se espera tipo.");}
                break;
                case 148:
//#line 933 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Suma");
                    Attribute attribute = new Attribute("+");
                    yyval.tree = new SyntacticTreeADD(val_peek(2).tree, val_peek(0).tree, attribute);
                    if(!this.checkType(yyval.tree)){
                        addError("Error Semántico en linea "+ la.getNroLinea() +": Suma - Incompatibilidad de tipos");
                    }
                }
                break;
                case 149:
//#line 942 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Resta");
                    Attribute attribute = new Attribute("-");
                    yyval.tree = new SyntacticTreeSUB(val_peek(2).tree, val_peek(0).tree, attribute);
                    if(!this.checkType(yyval.tree)){
                        addError("Error Semántico en linea "+ la.getNroLinea() +": Resta - Incompatibilidad de tipos");
                    }
                }
                break;
                case 150:
//#line 951 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 151:
//#line 955 "G08 - Gramatica - 25102020.y"
                { addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Suma - Se espera un termino luego del '+'."); }
                break;
                case 152:
//#line 956 "G08 - Gramatica - 25102020.y"
                { addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Suma - Se espera una expresion antes del '+'."); }
                break;
                case 153:
//#line 957 "G08 - Gramatica - 25102020.y"
                { addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Resta - Se espera un termino luego del '-'."); }
                break;
                case 154:
//#line 958 "G08 - Gramatica - 25102020.y"
                { addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Resta - Se espera una expresion antes del '-'."); }
                break;
                case 155:
//#line 962 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Multiplicacion");
                    Attribute attribute = new Attribute("*");
                    yyval.tree = new SyntacticTreeMUL(val_peek(2).tree, val_peek(0).tree, attribute);
                    if(!this.checkType(yyval.tree)){
                        addError("Error Semántico en linea "+ la.getNroLinea() +": Multiplicacion - Incompatibilidad de tipos");
                    }
                }
                break;
                case 156:
//#line 971 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Division");
                    Attribute attribute = new Attribute("/");
                    yyval.tree = new SyntacticTreeDIV(val_peek(2).tree, val_peek(0).tree, attribute);
                    if(!this.checkType(yyval.tree)){
                        addError("Error Semántico en linea "+ la.getNroLinea() +": Division - Incompatibilidad de tipos");
                    }
                }
                break;
                case 157:
//#line 981 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 158:
//#line 985 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Multiplicacion - Se espera un factor luego de * ");}
                break;
                case 159:
//#line 986 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Division - Se espera un factor luego de /");}
                break;
                case 160:
//#line 987 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Multiplicacion - Se espera un termino antes de * ");}
                break;
                case 161:
//#line 988 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Division - Se espera un termino antes de /");}
                break;
                case 162:
//#line 992 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": NRO_ULONGINT.");
                    val_peek(0).attributes.get(val_peek(0).attributes.size()-1).setFlag();
                    yyval.tree  = new SyntacticTreeLeaf(null, null, val_peek(0).attributes.get(val_peek(0).attributes.size()-1));
                }
                break;
                case 163:
//#line 998 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": NRO_DOUBLE.");
                    val_peek(0).attributes.get(val_peek(0).attributes.size()-1).setFlag();
                    yyval.tree  = new SyntacticTreeLeaf(null, null, val_peek(0).attributes.get(val_peek(0).attributes.size()-1));
                }
                break;
                case 164:
//#line 1005 "G08 - Gramatica - 25102020.y"
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
                case 165:
//#line 1027 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 166:
//#line 1032 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 167:
//#line 1038 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": ID PUNTO_PUNTO ID");

                    String lexemeIz = val_peek(2).attributes.get(0).getLexeme();

                    String lexemeDer = val_peek(0).attributes.get(0).getLexeme();

                    val_peek(2).attributes.get(val_peek(2).attributes.size()-1).setUse(Use.llamado_procedimiento_variable);

                    List<Parameter> parameters = this.checkIDPROC(val_peek(2).attributes, lexemeIz);

                    this.deleteSTEntry(lexemeIz, Use.llamado_procedimiento_variable);

                    Attribute attribute = this.checkID(val_peek(0).attributes, lexemeDer);

                    String scopePROC = val_peek(2).attributes.get(val_peek(2).attributes.size()-1).getScope();
                    List<Attribute> aux = getListUse(val_peek(0).attributes, Use.variable);
                    aux.addAll(getListUse(val_peek(0).attributes, Use.nombre_parametro));

                    Type type = this.checkIDdospuntosID(scopePROC, lexemeDer, aux);

                    if(attribute == null){
                        attribute = new Attribute(lexemeDer);
                        attribute.setType(Type.ERROR);
                    }

                    attribute.setFlag();
                    yyval.tree  = new SyntacticTreeLeaf(null, null, attribute);
                }
                break;
                case 168:
//#line 1068 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Tipo ID - Se espera ID luego de ::");}
                break;
                case 169:
//#line 1071 "G08 - Gramatica - 25102020.y"
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
//#line 2441 "Parser.java"
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
