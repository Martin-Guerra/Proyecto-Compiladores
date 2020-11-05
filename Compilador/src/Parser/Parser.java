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
import SyntacticTree.SyntacticTree;


//#line 33 "Parser.java"




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
            4,    4,    4,    4,    4,    4,    4,    7,    7,   13,
            15,   16,   16,   16,   16,   14,   14,   14,   19,   19,
            19,   19,   17,   17,   17,   18,   20,   20,   20,    6,
            6,    5,    5,   12,   12,   12,    8,    8,    8,    8,
            22,   22,   21,   21,   21,   21,   21,   21,   21,   21,
            21,   21,   21,   21,   21,   21,   21,   21,   21,   21,
            21,   21,   21,   23,   23,   23,   23,   24,   24,   27,
            27,   27,   27,   27,   26,   26,   10,   10,   10,   10,
            9,    9,    9,    9,   30,   30,   30,   32,   32,   32,
            32,   29,   29,   29,   29,   31,   31,   31,   31,   31,
            31,   31,   31,   31,   31,   31,   31,   31,   31,   31,
            31,   31,   31,   31,   33,   33,   33,   33,   33,   11,
            11,   11,   11,   11,   11,   34,   34,   34,   25,   25,
            25,   25,   25,   25,   25,   25,   35,   35,   35,   35,
            35,   35,   35,   36,   36,   36,   36,   28,   28,   28,
    };
    final static short yylen[] = {                            2,
            1,    1,    2,    2,    2,    1,    2,    1,    3,    2,
            1,    2,    2,    2,    2,    2,    2,    2,    2,    3,
            2,    3,    5,    7,    2,    3,    3,    2,    1,    2,
            1,    2,    3,    3,    3,    2,    1,    3,    5,    1,
            3,    1,    1,    4,    3,    4,    4,    4,    3,    2,
            2,    1,    5,    5,    5,    5,    5,    5,    5,    5,
            5,    5,    5,    5,    5,    5,    5,    5,    5,    5,
            3,    2,    1,    3,    1,    3,    2,    2,    2,    4,
            1,    4,    3,    2,    1,    2,    3,    3,    3,    3,
            5,    4,    3,    2,    3,    3,    3,    3,    3,    3,
            3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
            3,    3,    3,    3,    3,    3,    3,    2,    3,    3,
            3,    3,    3,    3,    2,    2,    2,    2,    2,    4,
            4,    3,    3,    4,    2,    4,    4,    4,    3,    3,
            1,    1,    3,    3,    3,    3,    3,    3,    1,    3,
            3,    3,    3,    1,    1,    2,    1,    3,    3,    1,
    };
    final static short yydefred[] = {                         0,
            0,    0,   42,    0,    0,    0,    0,   43,    0,    0,
            2,    0,    6,    0,    8,    0,   11,    0,    0,    0,
            0,    0,    0,    4,    0,    0,    0,    0,    0,    0,
            94,    0,  135,    0,    0,   21,    0,    3,    5,    0,
            40,    0,   10,   13,   12,   15,   14,   17,   16,   19,
            0,   18,    0,    0,    0,    0,    0,    0,  155,  154,
            0,    0,    0,  157,  142,    0,  149,  159,  158,    0,
            45,    0,    0,    0,    0,    0,   75,    0,    0,    0,
            0,    0,  132,  133,    0,    0,    9,    0,    0,   29,
            0,    0,   25,    0,    0,    0,   20,    0,    0,    0,
            0,    0,    0,    0,  156,    0,    0,    0,    0,    0,
            0,   46,   44,   71,    0,    0,    0,    0,    0,    0,
            0,   85,    0,   48,   47,    0,   51,    0,    0,    0,
            92,    0,  131,  130,  134,   41,    0,   26,   30,    0,
            36,   22,    0,    0,    0,    0,    0,    0,  152,  153,
            0,    0,    0,    0,    0,    0,  150,  147,  151,  148,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,   74,   86,    0,   78,  105,  104,
            103,  102,    0,    0,   91,    0,    0,   35,   34,   33,
            138,  137,  136,    0,   69,   63,   57,   68,   62,   56,
            67,   61,   55,   70,   64,   58,   65,   59,   53,   66,
            60,   54,    0,    0,    0,    0,    0,    0,    0,  118,
            0,    0,    0,    0,    0,    0,    0,    0,   23,    0,
            39,    0,    0,    0,    0,    0,    0,    0,   97,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,   96,   95,    0,  129,    0,  128,
            125,  127,  126,    0,    0,   24,    0,   81,   99,  100,
            0,   98,    0,    0,    0,    0,   82,   80,
    };
    final static short yydgoto[] = {                          9,
            10,   11,   12,   13,   62,   42,   15,   16,   17,   18,
            19,   20,   21,   52,   22,   54,   97,   95,   92,   72,
            30,   78,   79,  127,   63,  123,  269,   64,   82,  185,
            186,  239,  240,   65,   66,   67,
    };
    final static short yysindex[] = {                       285,
            26,  -31,    0,  -27,  -26,  -40, -249,    0,    0,  318,
            0,  -30,    0, -195,    0,   -9,    0,  -53,  -39,   -4,
            -105,   57,  300,    0,  -35, -107,   -6,    0,   -8, -102,
            0,  -87,    0,   -1,  -37,    0,   30,    0,    0,    4,
            0,   77,    0,    0,    0,    0,    0,    0,    0,    0,
            560,    0,  -11, -163,  -35,   63,  137, -155,    0,    0,
            -158,  120,   64,    0,    0,   17,    0,    0,    0,  121,
            0,  -36,  137,  478,   30, -134,    0, -183,  -98,  106,
            405,    6,    0,    0,    5,  -79,    0,  -76,   30,    0,
            0,  -68,    0,  -70,   95,  416,    0,   64,  137,   64,
            -23,  -23,  -33,  -33,    0,   66,  138,  165,  168,  170,
            -57,    0,    0,    0,   69,   81,   93,   96,   99,  111,
            30,    0,  541,    0,    0,  359,    0,  -93,  -64, -224,
            0,  -84,    0,    0,    0,    0,   30,    0,    0,    0,
            0,    0, -214,  -60, -222,  100,   17,   17,    0,    0,
            58,    2,  100,   17,  100,   17,    0,    0,    0,    0,
            158,  612,   25,  787,   37,  794,   43,  801,   49,  808,
            61,  834,   75,   30,    0,    0,   30,    0,    0,    0,
            0,    0,  -43,  302,    0,   72,  102,    0,    0,    0,
            0,    0,    0,  -51,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,  -35,  -35,  -35,  -35, -232,  -35,  -35,    0,
            123,  126,  129,  141,  153,  156, -232, -232,    0, -214,
            0,   64,   64,   64,   64,  -38, -220, -218,    0,    8,
            64,   64,  137,   64,  137,   64,  137,   64,  137,   64,
            137,   64,  137,   64,    0,    0,  216,    0,  472,    0,
            0,    0,    0,  472,  516,    0,  304,    0,    0,    0,
            30,    0,   30,  551,   30,   73,    0,    0,
    };
    final static short yyrindex[] = {                         0,
            0,  430,    0,    0,    0,    0,    0,    0,    0,  263,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,  -52,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,  208,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,  394,    0,    0,
            0,    0,   76,    0,    0,  414,    0,    0,    0,   31,
            0,    0,  528,    0,  381,    0,    0,    0, -181,   15,
            0,    0,    0,    0,    0,    0,    0,    0,  333,    0,
            346,    0,    0,    0,    0,    0,    0,   78,   86,   89,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            -128,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,  373,    0,    0,  494,
            0,    0,    0,    0,    0,    0,  419,  424,    0,    0,
            0,    0,  444,  449,  454,  474,    0,    0,    0,    0,
            55,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0, -103,    0,    0, -173,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,  116,  119,  135,  276,    0,    0,    0,    0,    0,
            306,  343,  358,  369,  370,  379,  385,  390,  391,  392,
            393,  402,  404,  411,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            27,    0,   39,    0,   51,    0,    0,    0,
    };
    final static short yygindex[] = {                         0,
            0,   18,    0,  -28,   23,    0,  -25,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0, -118,    0,    0,
            0,    0,  342,    0,  648,  205,  -72,    1,    0,    0,
            0,  -19,    0,    0,  423,  484,
    };
    final static int YYTABLESIZE=881;
    static short yytable[];
    static { yytable();}
    static void yytable(){
        yytable = new short[]{                         35,
                23,   77,  259,   86,  113,   45,   50,   36,   27,   61,
                23,   61,   29,   32,   93,  217,  218,   51,  219,   47,
                76,   61,   14,  236,  187,   91,  101,   38,   39,   93,
                23,  181,   14,  189,   71,  260,   61,  262,   84,   83,
                237,  238,  193,    3,  107,  134,  108,  122,  265,   43,
                83,   23,    8,  182,   49,  190,  138,  261,  109,  263,
                40,   41,   87,  110,  132,  197,  140,  107,   90,  108,
                73,   37,  124,   14,   52,   94,   23,  200,  125,  107,
                52,  108,   79,  203,   24,  107,   25,  108,   79,  206,
                25,  107,   23,  108,  176,   38,   53,   77,  191,  103,
                101,  209,  102,  107,  104,  108,  107,   61,  108,  139,
                61,  257,   96,   61,   14,  212,   26,  107,  105,  108,
                88,  121,    2,   23,    4,   61,   23,   77,    5,    6,
                228,  278,   77,   77,   90,  142,   89,   61,  143,   93,
                61,  103,  229,   61,   88,  230,  104,   87,   68,   69,
                50,  101,   76,   75,    2,   61,    4,   76,   76,  106,
                5,    6,  126,   84,  111,   94,  128,   61,   80,   81,
                61,  183,  184,   61,  123,   83,  135,  122,  103,  101,
                136,  102,   61,  104,  179,   61,  141,  137,    2,    3,
                4,  270,  272,  121,    5,    6,    7,   61,    8,  161,
                61,  194,   44,   73,   73,  231,   73,  255,  256,   61,
                73,   73,   61,  180,   61,   33,   46,  188,   84,  112,
                57,   58,    3,   58,  213,  214,  215,  216,   28,   31,
                268,    8,  146,   58,   34,  268,  268,   85,  122,  258,
                26,   59,   60,   59,   60,  176,    3,   73,   58,    3,
                70,   48,   94,   59,   60,    8,  266,  192,    8,   23,
                133,  131,    1,  264,   23,   23,    7,   23,   59,   60,
                93,   93,   93,   93,   23,   93,   93,   93,   93,   93,
                196,   93,  101,  101,  101,  101,   37,  101,  101,  101,
                101,  101,  199,  101,   84,   84,   84,   84,  202,   84,
                84,   84,   84,   84,  205,   84,   83,   83,   83,   83,
                38,   83,   83,   83,   83,   83,  208,   83,   99,   58,
                3,  151,   58,    3,  162,   58,    3,  227,  277,    8,
                211,   90,    8,   89,  124,    8,  164,   58,    3,   59,
                60,   88,   59,   60,   87,   59,   60,    8,  166,   58,
                3,  168,   58,    3,  170,   58,    3,   59,   60,    8,
                56,  225,    8,  226,  119,    8,  172,   58,    3,   59,
                60,  123,   59,   60,  122,   59,   60,    8,  243,   58,
                3,  245,   58,    3,  247,   58,    3,   59,   60,    8,
                121,   28,    8,  153,   58,    8,  249,   58,    3,   59,
                60,  120,   59,   60,    8,   59,   60,    8,  251,   58,
                3,  253,   58,    3,   59,   60,  116,   59,   60,    8,
                155,   58,    8,  157,   58,  159,   58,  110,  115,   59,
                60,   27,   59,   60,  160,  160,  160,  109,  160,   49,
                160,   59,   60,  114,   59,   60,   59,   60,  108,  117,
                111,  112,  160,  160,  141,  160,  141,   28,  141,  144,
                106,  144,  113,  144,  146,  130,  146,  178,  146,  107,
                31,  274,  141,  141,    0,  141,  145,  144,  144,    0,
                144,   76,  146,  146,  143,  146,  143,    0,  143,  139,
                160,  139,    0,  139,  145,    0,  145,   27,  145,    0,
                0,    0,  143,  143,    0,  143,    0,  139,  139,    0,
                139,    0,  145,  145,  140,  145,  140,    0,  140,    0,
                107,    0,  108,  147,  148,    0,    0,    0,    0,  154,
                156,  124,  140,  140,    0,  140,    0,  119,    0,  120,
                1,    2,    3,    4,    0,    0,    0,    5,    6,    7,
                0,    8,    8,    0,    0,   55,    0,  220,    0,  273,
                2,  119,    4,    0,    0,    0,    5,    6,    0,  221,
                222,  223,  224,   37,    2,    3,    4,    0,    0,    0,
                5,    6,    7,    0,    8,    0,  149,  150,   28,   28,
                28,   28,  158,  160,  267,   28,   28,   28,  120,   28,
                0,   31,   31,   31,   31,    0,    0,    0,   31,   31,
                31,    0,   31,  116,  177,    2,    0,    4,   32,    0,
                0,    5,    6,    0,  110,  115,    0,    0,   27,   27,
                27,   27,    0,    0,  109,   27,   27,   27,  267,   27,
                114,    0,    0,    0,    0,  108,  117,  111,  112,  160,
                72,    0,  195,  103,  101,    0,  102,  106,  104,  113,
                129,  160,  160,  160,  160,  175,  107,    0,    0,  141,
                0,  144,    0,    0,  144,  276,   74,    0,    0,  146,
                0,  141,  141,  141,  141,  160,  144,  144,  144,  144,
                0,  146,  146,  146,  146,    0,    0,    0,    0,  143,
                0,    0,   98,  100,  139,    0,    0,    0,    0,  145,
                0,  143,  143,  143,  143,    0,  139,  139,  139,  139,
                0,  145,  145,  145,  145,    0,    0,   37,    2,  140,
                4,    0,    0,  114,    5,    6,    0,    0,    0,    0,
                0,  140,  140,  140,  140,  115,  116,  117,  118,   32,
                32,   32,   32,  152,    0,    0,   32,   32,   32,    0,
                32,    0,  163,  165,  167,  169,  171,  173,    0,    0,
                0,  271,    2,    0,    4,    0,    0,    0,    5,    6,
                0,    0,    0,   72,   72,    0,   72,    0,    0,    0,
                72,   72,    0,    0,    0,    0,  174,    2,    0,    4,
                0,    0,    0,    5,    6,    0,  275,    2,    0,    4,
                0,    0,    0,    5,    6,   89,    2,    3,    4,    0,
                0,    0,    5,    6,    7,    0,    8,  198,  103,  101,
                0,  102,    0,  104,  201,  103,  101,    0,  102,    0,
                104,  204,  103,  101,    0,  102,    0,  104,  207,  103,
                101,    0,  102,    0,  104,    0,    0,    0,    0,    0,
                232,  233,  234,  235,    0,  241,  242,    0,  244,  246,
                248,  250,  252,  254,  210,  103,  101,    0,  102,    0,
                104,
        };
    }
    static short yycheck[];
    static { yycheck(); }
    static void yycheck() {
        yycheck = new short[] {                         40,
                0,   30,   41,   41,   41,   59,   59,  257,   40,   45,
                10,   45,   40,   40,    0,   59,   60,  123,   62,   59,
                123,   45,    0,  256,  143,   51,    0,   10,   59,   41,
                30,  256,   10,  256,   41,  256,   45,  256,    0,   41,
                273,  274,   41,  258,   43,   41,   45,   76,   41,   59,
                0,   51,  267,  278,   59,  278,  125,  278,   42,  278,
                256,  257,   59,   47,   59,   41,   92,   43,   51,   45,
                123,   41,  256,   51,  256,   53,   76,   41,  262,   43,
                262,   45,  256,   41,   59,   43,   61,   45,  262,   41,
                61,   43,   92,   45,  123,   41,   40,  126,   41,   42,
                43,   41,   45,   43,   47,   45,   43,   45,   45,   92,
                45,  230,  276,   45,   92,   41,  272,   43,  277,   45,
                44,  256,  257,  123,  259,   45,  126,  256,  263,  264,
                59,   59,  261,  262,   59,   41,   59,   45,   44,  125,
                45,   42,   41,   45,   59,   44,   47,   59,  256,  257,
                256,  125,  256,  256,  257,   45,  259,  261,  262,   40,
                263,  264,  261,  125,   44,  143,   61,   45,  256,  257,
                45,  256,  257,   45,   59,  125,  256,   59,   42,   43,
                257,   45,   45,   47,  278,   45,  257,  256,  257,  258,
                259,  264,  265,   59,  263,  264,  265,   45,  267,  257,
                45,   44,  256,  256,  257,  257,  259,  227,  228,   45,
                263,  264,   45,  278,   45,  256,  256,  278,  256,  256,
                256,  257,  258,  257,  268,  269,  270,  271,  256,  256,
                259,  267,  256,  257,  275,  264,  265,  275,  267,  278,
                272,  277,  278,  277,  278,  274,  258,  256,  257,  258,
                257,  256,  230,  277,  278,  267,   41,  256,  267,  259,
                256,  256,    0,  256,  264,  265,   59,  267,  277,  278,
                256,  257,  258,  259,  274,  261,  262,  263,  264,  265,
                256,  267,  256,  257,  258,  259,  256,  261,  262,  263,
                264,  265,  256,  267,  256,  257,  258,  259,  256,  261,
                262,  263,  264,  265,  256,  267,  256,  257,  258,  259,
                256,  261,  262,  263,  264,  265,  256,  267,  256,  257,
                258,  256,  257,  258,  256,  257,  258,  256,  256,  267,
                256,  256,  267,  256,   59,  267,  256,  257,  258,  277,
                278,  256,  277,  278,  256,  277,  278,  267,  256,  257,
                258,  256,  257,  258,  256,  257,  258,  277,  278,  267,
                61,   60,  267,   62,   59,  267,  256,  257,  258,  277,
                278,  256,  277,  278,  256,  277,  278,  267,  256,  257,
                258,  256,  257,  258,  256,  257,  258,  277,  278,  267,
                256,   59,  267,  256,  257,  267,  256,  257,  258,  277,
                278,   59,  277,  278,   59,  277,  278,  267,  256,  257,
                258,  256,  257,  258,  277,  278,   59,  277,  278,  267,
                256,  257,  267,  256,  257,  256,  257,   59,   59,  277,
                278,   59,  277,  278,   41,   42,   43,   59,   45,   59,
                47,  277,  278,   59,  277,  278,  277,  278,   59,   59,
                59,   59,   59,   60,   41,   62,   43,  125,   45,   41,
                59,   43,   59,   45,   41,   61,   43,  126,   45,   59,
                125,  267,   59,   60,   -1,   62,   61,   59,   60,   -1,
                62,  123,   59,   60,   41,   62,   43,   -1,   45,   41,
                61,   43,   -1,   45,   41,   -1,   43,  125,   45,   -1,
                -1,   -1,   59,   60,   -1,   62,   -1,   59,   60,   -1,
                62,   -1,   59,   60,   41,   62,   43,   -1,   45,   -1,
                43,   -1,   45,  101,  102,   -1,   -1,   -1,   -1,  107,
                108,  256,   59,   60,   -1,   62,   -1,   60,   -1,   62,
                256,  257,  258,  259,   -1,   -1,   -1,  263,  264,  265,
                -1,  267,   59,   -1,   -1,  256,   -1,  256,   -1,  256,
                257,  256,  259,   -1,   -1,   -1,  263,  264,   -1,  268,
                269,  270,  271,  256,  257,  258,  259,   -1,   -1,   -1,
                263,  264,  265,   -1,  267,   -1,  103,  104,  256,  257,
                258,  259,  109,  110,  123,  263,  264,  265,  256,  267,
                -1,  256,  257,  258,  259,   -1,   -1,   -1,  263,  264,
                265,   -1,  267,  256,  256,  257,   -1,  259,  125,   -1,
                -1,  263,  264,   -1,  256,  256,   -1,   -1,  256,  257,
                258,  259,   -1,   -1,  256,  263,  264,  265,  123,  267,
                256,   -1,   -1,   -1,   -1,  256,  256,  256,  256,  256,
                123,   -1,   41,   42,   43,   -1,   45,  256,   47,  256,
                256,  268,  269,  270,  271,  125,  256,   -1,   -1,  256,
                -1,  256,   -1,   -1,  256,  125,   29,   -1,   -1,  256,
                -1,  268,  269,  270,  271,  256,  268,  269,  270,  271,
                -1,  268,  269,  270,  271,   -1,   -1,   -1,   -1,  256,
                -1,   -1,   55,   56,  256,   -1,   -1,   -1,   -1,  256,
                -1,  268,  269,  270,  271,   -1,  268,  269,  270,  271,
                -1,  268,  269,  270,  271,   -1,   -1,  256,  257,  256,
                259,   -1,   -1,  256,  263,  264,   -1,   -1,   -1,   -1,
                -1,  268,  269,  270,  271,  268,  269,  270,  271,  256,
                257,  258,  259,  106,   -1,   -1,  263,  264,  265,   -1,
                267,   -1,  115,  116,  117,  118,  119,  120,   -1,   -1,
                -1,  256,  257,   -1,  259,   -1,   -1,   -1,  263,  264,
                -1,   -1,   -1,  256,  257,   -1,  259,   -1,   -1,   -1,
                263,  264,   -1,   -1,   -1,   -1,  256,  257,   -1,  259,
                -1,   -1,   -1,  263,  264,   -1,  256,  257,   -1,  259,
                -1,   -1,   -1,  263,  264,  256,  257,  258,  259,   -1,
                -1,   -1,  263,  264,  265,   -1,  267,   41,   42,   43,
                -1,   45,   -1,   47,   41,   42,   43,   -1,   45,   -1,
                47,   41,   42,   43,   -1,   45,   -1,   47,   41,   42,
                43,   -1,   45,   -1,   47,   -1,   -1,   -1,   -1,   -1,
                213,  214,  215,  216,   -1,  218,  219,   -1,  221,  222,
                223,  224,  225,  226,   41,   42,   43,   -1,   45,   -1,
                47,
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
            "sent_ejecutable : asignacion error",
            "sent_ejecutable : imprimir ';'",
            "sent_ejecutable : imprimir error",
            "sent_ejecutable : llamado_PROC ';'",
            "sent_ejecutable : llamado_PROC error",
            "procedimiento : encabezado cuerpo_procedimiento",
            "procedimiento : encabezado error",
            "encabezado : encabezado_PROC parametro_PROC asignacion_NA",
            "encabezado_PROC : PROC ID",
            "parametro_PROC : '(' parametro ')'",
            "parametro_PROC : '(' parametro ',' parametro ')'",
            "parametro_PROC : '(' parametro ',' parametro ',' parametro ')'",
            "parametro_PROC : '(' ')'",
            "cuerpo_procedimiento : '{' bloque_procedimiento '}'",
            "cuerpo_procedimiento : '{' bloque_procedimiento error",
            "cuerpo_procedimiento : '{' error",
            "bloque_procedimiento : sentencia",
            "bloque_procedimiento : bloque_procedimiento sentencia",
            "bloque_procedimiento : procedimiento",
            "bloque_procedimiento : bloque_procedimiento procedimiento",
            "asignacion_NA : NA '=' NRO_ULONGINT",
            "asignacion_NA : NA '=' error",
            "asignacion_NA : NA error NRO_ULONGINT",
            "parametro : tipo ID",
            "parametro_invocacion : ID",
            "parametro_invocacion : ID ',' ID",
            "parametro_invocacion : ID ',' ID ',' ID",
            "lista_variables : ID",
            "lista_variables : lista_variables ',' ID",
            "tipo : ULONGINT",
            "tipo : DOUBLE",
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

//#line 754 "G08 - Gramatica - 25102020.y"


    private LexerAnalyzer la;
    private SemanticAnalyzer sa;
    private List<String> errors;
    private List<String> rules;
    private SyntacticTree syntacticTree;
    private String globalScope = "";

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

    public void printSyntacticTree(){
        this.syntacticTree.printTree(this.syntacticTree);
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
        Attribute attributeParam;
        if(!this.globalScope.isEmpty() || !(attributes.size() == 1)) {
            if (attributes.size() == 1) {
                attributes.get(attributes.size() - 1).setDeclared();
                found = true;
            }else{
                attributeParam = attributes.get(attributes.size() - 2);
                if (sa.isRedeclared(this.globalScope, lexeme, attributeParam)) {
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

    public void deleteSTEntry(String lexeme){
        List<Attribute> listAttributes = la.getSymbolTable().getSymbolTable().get(lexeme);
        for (int i=0; i<listAttributes.size(); i++){
            if(listAttributes.get(i).getUse().equals(Use.variable_en_uso)){
                listAttributes.remove(i);
                i--;
            }
        }
        la.getSymbolTable().getSymbolTable().replace(lexeme, listAttributes);
    }

    //#line 763 "Parser.java"
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
//#line 28 "G08 - Gramatica - 25102020.y"
                {
                    syntacticTree = val_peek(0).tree;
                }
                break;
                case 2:
//#line 34 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 3:
//#line 38 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = new SyntacticTree(val_peek(1).tree, val_peek(0).tree, "LISTA SENTENCIAS");
                }
                break;
                case 5:
//#line 45 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(1).tree;

                }
                break;
                case 6:
//#line 50 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 7:
//#line 56 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia declarativa - Variable/s.");
                    yyval = val_peek(0);
                    Type type = new Type(val_peek(1).type.getName());
                    for(String lexeme : yyval.attributesSetteable){
                        List<Attribute> attributes = la.getAttribute(lexeme);
                        attributes.get(attributes.size() - 1).setUse(Use.variable);
                        attributes.get(attributes.size() - 1).setType(type);

                        attributes = getListUse(attributes, Use.variable);
                        this.isRedeclared(attributes, lexeme, "Sentencia declarativa - Redefinicion de  VARIABLE/S");

                        attributes.get(attributes.size()-1).setScope(this.globalScope);
                    }

                }
                break;
                case 8:
//#line 74 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                    yyval = val_peek(0);
                    this.decreaseScope();
                }
                break;
                case 9:
//#line 80 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia declarativa - Falta definir la/s VARIABLE/S."); }
                break;
                case 10:
//#line 84 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(1).tree;
                }
                break;
                case 11:
//#line 90 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 12:
//#line 95 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = new SyntacticTree(val_peek(1).tree, "ASIGNACION");
                }
                break;
                case 13:
//#line 98 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia ejecutable asignacion - Falta ;"); }
                break;
                case 14:
//#line 101 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(1).tree;
                }
                break;
                case 15:
//#line 104 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia ejecutable OUT - Falta ;"); }
                break;
                case 16:
//#line 107 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(1).tree;
                }
                break;
                case 17:
//#line 111 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia ejecutable llamado a procedimiento - Falta ;"); }
                break;
                case 18:
//#line 116 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Procedimiento");
                    this.sa.deleteNA();
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 19:
//#line 121 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Procedimiento - Se espera CUERPO del PROCEDIMIENTO"); }
                break;
                case 20:
//#line 125 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Encabezado procedimiento");
                }
                break;
                case 21:
//#line 131 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": PROC ID");

                    yyval.attributes = val_peek(0).attributes;
                    String[] scope = val_peek(0).attributes.get(0).getScope().split("@");

                    List<Attribute> attributes = la.getAttribute(scope[0]);
                    attributes.get(attributes.size() - 1).setUse(Use.nombre_procedimiento);
                    attributes.get(attributes.size() - 1).setScope(this.globalScope);

                    val_peek(0).attributes = getListUse(val_peek(0).attributes, Use.nombre_procedimiento);
                    this.isRedeclared(val_peek(0).attributes, scope[0], "Sentencia declarativa - Redefinicion de ID procedimiento");

                    this.globalScope += "@" + scope[0];
                }
                break;
                case 22:
//#line 149 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Procedimiento - un parametro");

                    this.setScopeProcParam(val_peek(1).attributesSetteable);
                    val_peek(1).attributes = getListUse(val_peek(1).attributes, Use.nombre_parametro);
                    String[] scope = val_peek(1).attributes.get(0).getScope().split("@");
                    this.isRedeclared(val_peek(1).attributes, scope[0], "Sentencia declarativa - Redefinicion de parametro");
                }
                break;
                case 23:
//#line 158 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Procedimiento - dos parametros");

                    this.setScopeProcParam(val_peek(3).attributesSetteable);
                    val_peek(3).attributes = getListUse(val_peek(3).attributes, Use.nombre_parametro);
                    String[] scope = val_peek(3).attributes.get(0).getScope().split("@");
                    this.isRedeclared(val_peek(3).attributes, scope[0], "Sentencia declarativa - Redefinicion de parametro");

                    this.setScopeProcParam(val_peek(1).attributesSetteable);
                    val_peek(1).attributes = getListUse(val_peek(1).attributes, Use.nombre_parametro);
                    scope = val_peek(1).attributes.get(0).getScope().split("@");
                    this.isRedeclared(val_peek(1).attributes, scope[0], "Sentencia declarativa - Redefinicion de parametro");

                }
                break;
                case 24:
//#line 173 "G08 - Gramatica - 25102020.y"
                {
                    this.setScopeProcParam(val_peek(5).attributesSetteable);
                    val_peek(5).attributes = getListUse(val_peek(5).attributes, Use.nombre_parametro);
                    String[] scope = val_peek(5).attributes.get(0).getScope().split("@");
                    this.isRedeclared(val_peek(5).attributes, scope[0], "Sentencia declarativa - Redefinicion de parametro");

                    this.setScopeProcParam(val_peek(3).attributesSetteable);
                    val_peek(3).attributes = getListUse(val_peek(3).attributes, Use.nombre_parametro);
                    scope = val_peek(3).attributes.get(0).getScope().split("@");
                    this.isRedeclared(val_peek(3).attributes, scope[0], "Sentencia declarativa - Redefinicion de parametro");

                    this.setScopeProcParam(val_peek(1).attributesSetteable);
                    val_peek(1).attributes = getListUse(val_peek(1).attributes, Use.nombre_parametro);
                    scope = val_peek(1).attributes.get(0).getScope().split("@");
                    this.isRedeclared(val_peek(1).attributes, scope[0], "Sentencia declarativa - Redefinicion de parametro");
                }
                break;
                case 26:
//#line 193 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(1).tree;
                }
                break;
                case 27:
//#line 197 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Cuerpo procedimiento - Se espera }"); }
                break;
                case 28:
//#line 198 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Cuerpo procedimiento - Se espera bloque cuerpo procedimiento"); }
                break;
                case 29:
//#line 203 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = new SyntacticTree(val_peek(0).tree, "CUERPO PROCEDIMIENTO SENTENCIA");
                    yyval = val_peek(0);
                }
                break;
                case 30:
//#line 208 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = new SyntacticTree(val_peek(1).tree, val_peek(0).tree, "CUERPO PROCEDIMIENTO SENTENCIA COMPUESTA");
                    yyval = val_peek(1);
                }
                break;
                case 31:
//#line 213 "G08 - Gramatica - 25102020.y"
                {
                    yyval = val_peek(0);
                }
                break;
                case 32:
//#line 217 "G08 - Gramatica - 25102020.y"
                {
                    yyval = val_peek(1);
                }
                break;
                case 33:
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
                case 34:
//#line 236 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Asignacion NA procedimiento - Se espera NRO_ULONGINT"); }
                break;
                case 35:
//#line 237 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Asignacion NA procedimiento - Se espera ="); }
                break;
                case 36:
//#line 242 "G08 - Gramatica - 25102020.y"
                {
                    yyval = val_peek(0);

                    yyval.attributesSetteable = new ArrayList<>();
                    String scope = val_peek(0).attributes.get(0).getScope();
                    String[] lexeme = scope.split("@");
                    yyval.attributesSetteable.add(lexeme[0]);

                    Type type = new Type(val_peek(1).type.getName());
                    val_peek(0).attributes.get(val_peek(0).attributes.size()-1).setType(type);
                    val_peek(0).attributes.get(val_peek(0).attributes.size()-1).setUse(Use.nombre_parametro);
                }
                break;
                case 37:
//#line 257 "G08 - Gramatica - 25102020.y"
                {
                }
                break;
                case 38:
//#line 260 "G08 - Gramatica - 25102020.y"
                {
                }
                break;
                case 40:
//#line 268 "G08 - Gramatica - 25102020.y"
                {
                    yyval.attributesSetteable = new ArrayList<>();
                    String scope = val_peek(0).attributes.get(0).getScope();
                    String[] lexeme = scope.split("@");
                    yyval.attributesSetteable.add(lexeme[0]);

                }
                break;
                case 41:
//#line 276 "G08 - Gramatica - 25102020.y"
                {
                    yyval = val_peek(2);
                    String scope = val_peek(0).attributes.get(0).getScope();
                    String[] lexeme = scope.split("@");
                    yyval.attributesSetteable.add(lexeme[0]);

                }
                break;
                case 42:
//#line 286 "G08 - Gramatica - 25102020.y"
                {
                    yyval.type = Type.ULONGINT;
                    yyval.tree  = new SyntacticTree(null, null, "ULONGINT");

                }
                break;
                case 43:
//#line 292 "G08 - Gramatica - 25102020.y"
                {
                    yyval.type = Type.DOUBLE;
                    yyval.tree  = new SyntacticTree(null, null, "DOUBLE");
                }
                break;
                case 44:
//#line 300 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Llamado a procedimiento con parametros");
                    /*$$.tree = new SyntacticTree(new SyntacticTree(null, null, $1.attribute.getLexeme()), $3.tree,"LLAMADO PROC SIN PAR");
                     */

                }
                break;
                case 45:
//#line 306 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Llamado a procedimiento sin parametros");
                    /*$$.tree = new SyntacticTree(new SyntacticTree(null, null, $1.attributes.get(0).getScope()), "LLAMADO PROC SIN PAR");
                     */

                    val_peek(2).attributes.get(val_peek(2).attributes.size()-1).setUse(Use.llamado_procedimiento);
                    val_peek(2).attributes = getListUse(val_peek(2).attributes, Use.nombre_procedimiento);
                    boolean encontro = false;
                    if(val_peek(2).attributes.isEmpty())
                        encontro = false;
                    else {
                        String[] scope = val_peek(2).attributes.get(0).getScope().split("@");
                        String lexeme = scope[0];

                        for (Attribute attribute : val_peek(2).attributes) {
                            encontro = sa.isRedeclared(this.globalScope, lexeme, attribute);
                            if (encontro) {
                                break;
                            }
                        }
                    }
                    if(!encontro){
                        addError("Error Semántico en línea "+ la.getNroLinea() +": No se encuentra declaración de procedimiento al alcance");
                    }
                }
                break;
                case 46:
//#line 331 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Llamado a procedimiento - Se espera )"); }
                break;
                case 47:
//#line 337 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia IF");
                    yyval.tree = new SyntacticTree(val_peek(2).tree, val_peek(1).tree, "IF");
                }
                break;
                case 48:
//#line 342 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF - Se espera END_IF"); }
                break;
                case 49:
//#line 343 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF - Se espera cuerpo"); }
                break;
                case 50:
//#line 344 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF - Se espera condicion"); }
                break;
                case 51:
//#line 348 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia IF ELSE - Cuerpo");
                    yyval.tree = new SyntacticTree(val_peek(1).tree, val_peek(0).tree, "CUERPO_IF_ELSE");
                }
                break;
                case 52:
//#line 353 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia IF - Cuerpo");
                    yyval.tree = new SyntacticTree(val_peek(0).tree, "CUERPO_IF");
                }
                break;
                case 53:
//#line 361 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia IF - Condicion <.");
                    yyval.tree = new SyntacticTree(new SyntacticTree(val_peek(3).tree, val_peek(1).tree, "<"), "COND");
                }
                break;
                case 54:
//#line 366 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +":Sentencia IF - Condicion >.");
                    yyval.tree = new SyntacticTree(new SyntacticTree(val_peek(3).tree, val_peek(1).tree, ">"), "COND");
                }
                break;
                case 55:
//#line 371 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia IF - Condicion ==.");
                    yyval.tree = new SyntacticTree(new SyntacticTree(val_peek(3).tree, val_peek(1).tree, "=="), "COND");
                }
                break;
                case 56:
//#line 376 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia IF - Condicion >=.");
                    yyval.tree = new SyntacticTree(new SyntacticTree(val_peek(3).tree, val_peek(1).tree, ">="), "COND");
                }
                break;
                case 57:
//#line 381 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia IF - Condicion <=.");
                    yyval.tree = new SyntacticTree(new SyntacticTree(val_peek(3).tree, val_peek(1).tree, "<="), "COND");
                }
                break;
                case 58:
//#line 386 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia IF - Condicion !=.");
                    yyval.tree = new SyntacticTree(new SyntacticTree(val_peek(3).tree, val_peek(1).tree, "!="), "COND");
                }
                break;
                case 59:
//#line 391 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera )"); }
                break;
                case 60:
//#line 392 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera )"); }
                break;
                case 61:
//#line 393 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera )"); }
                break;
                case 62:
//#line 394 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera )"); }
                break;
                case 63:
//#line 395 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera )"); }
                break;
                case 64:
//#line 396 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera )"); }
                break;
                case 65:
//#line 398 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera expresion derecha"); }
                break;
                case 66:
//#line 399 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera expresion derecha"); }
                break;
                case 67:
//#line 400 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera expresion derecha"); }
                break;
                case 68:
//#line 401 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera expresion derecha"); }
                break;
                case 69:
//#line 402 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera expresion derecha"); }
                break;
                case 70:
//#line 403 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera expresion derecha"); }
                break;
                case 71:
//#line 405 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera comparador"); }
                break;
                case 72:
//#line 407 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera expresion izquierda"); }
                break;
                case 73:
//#line 409 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera ("); }
                break;
                case 74:
//#line 414 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia IF - Bloque de sentencias");
                    yyval.tree = new SyntacticTree(val_peek(1).tree, "BLOQUE_IF");
                }
                break;
                case 75:
//#line 419 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 76:
//#line 423 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF bloque - Se espera } finalizacion BLOQUE IF");}
                break;
                case 77:
//#line 424 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF bloque - Se espera cuerpo_ejecutable");}
                break;
                case 78:
//#line 428 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia IF ELSE - bloque de sentencias ELSE");
                    yyval.tree = new SyntacticTree(val_peek(0).tree, "BLOQUE_ELSE");
                }
                break;
                case 79:
//#line 433 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF ELSE - Se espera bloque de sentencias");}
                break;
                case 80:
//#line 437 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia FOR - Bloque de sentencias");
                    yyval.tree = new SyntacticTree(val_peek(2).tree, "BLOQUE_FOR");
                }
                break;
                case 81:
//#line 442 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 82:
//#line 446 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR bloque - Se espera ;");}
                break;
                case 83:
//#line 447 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR bloque - Se espera } ");}
                break;
                case 84:
//#line 448 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR bloque - Se espera cuerpo_ejecutable ");}
                break;
                case 85:
//#line 452 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 86:
//#line 456 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = new SyntacticTree(val_peek(1).tree, val_peek(0).tree, "SENTENCIA");
                }
                break;
                case 87:
//#line 462 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Asignacion");
                    yyval.tree = new SyntacticTree(val_peek(2).tree, val_peek(0).tree, "=");
                }
                break;
                case 88:
//#line 467 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Asignacion - Se espera expresion lado derecho");}
                break;
                case 89:
//#line 468 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Asignacion - Se espera =");}
                break;
                case 90:
//#line 469 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Asignacion - Se espera ID lado izquierdo");}
                break;
                case 91:
//#line 475 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia FOR");
                    yyval.tree = new SyntacticTree(val_peek(2).tree, val_peek(0).tree, "FOR_ASIGNACION");
                }
                break;
                case 92:
//#line 480 "G08 - Gramatica - 25102020.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR - Se espera ;."); }
                break;
                case 93:
//#line 481 "G08 - Gramatica - 25102020.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR - Se espera asignacion"); }
                break;
                case 94:
//#line 482 "G08 - Gramatica - 25102020.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR - Se espera ("); }
                break;
                case 95:
//#line 486 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = new SyntacticTree(new SyntacticTree(val_peek(2).tree, "COMPARACION"), new SyntacticTree(val_peek(0).tree, "CUERPO"), "FOR");
                }
                break;
                case 96:
//#line 489 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera ;"); }
                break;
                case 97:
//#line 490 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera condicion antes de ;"); }
                break;
                case 98:
//#line 494 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = new SyntacticTree(val_peek(2).tree, val_peek(0).tree, "SENTENCIAS_FOR");
                }
                break;
                case 99:
//#line 497 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera UP/DOWN NRO_ULONGINT"); }
                break;
                case 100:
//#line 498 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera )"); }
                break;
                case 101:
//#line 499 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera bloque_FOR"); }
                break;
                case 102:
//#line 504 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": ASIGNACION_FOR");

                    yyval.tree = new SyntacticTree(new SyntacticTree(null, null, val_peek(2).attributes.get(0).getScope()), new SyntacticTree(null, null, val_peek(0).attributes.get(0).getScope()), "=");
                }
                break;
                case 103:
//#line 510 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR asignacion - Se espera NRO_ULONGINT lado derecho"); }
                break;
                case 104:
//#line 511 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR asignacion - Se espera ="); }
                break;
                case 105:
//#line 512 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR asignacion - Se espera ID lado izquierdo"); }
                break;
                case 106:
//#line 516 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia FOR - Condicion <.");
                    /*$$.tree = new SyntacticTree(new SyntacticTree(null, null, $1.attributes.get(0).getScope()), $3.tree, "<");
                     */
                }
                break;
                case 107:
//#line 521 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia FOR - Condicion >");
                    /*$$.tree = new SyntacticTree(new SyntacticTree(null, null, $1.attributes.get(0).getScope()), $3.tree, ">");
                     */
                }
                break;
                case 108:
//#line 526 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia FOR - Condicion ==");
                    /*$$.tree = new SyntacticTree(new SyntacticTree(null, null, $1.attributes.get(0).getScope()), $3.tree, "==");
                     */
                }
                break;
                case 109:
//#line 531 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia FOR - Condicion >=");
                    /*$$.tree = new SyntacticTree(new SyntacticTree(null, null, $1.attributes.get(0).getScope()), $3.tree, ">=");
                     */
                }
                break;
                case 110:
//#line 536 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia FOR - Condicion <=");
                    /*$$.tree = new SyntacticTree(new SyntacticTree(null, null, $1.attributes.get(0).getScope()), $3.tree, "<=");
                     */
                }
                break;
                case 111:
//#line 541 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia FOR - Condicion !=");
                    yyval.tree = new SyntacticTree(new SyntacticTree(null, null, val_peek(2).attributes.get(0).getScope()), val_peek(0).tree, "!=");
                }
                break;
                case 112:
//#line 546 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera expresion lado derecho comparacion"); }
                break;
                case 113:
//#line 547 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera expresion lado derecho comparacion"); }
                break;
                case 114:
//#line 548 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera expresion lado derecho comparacion"); }
                break;
                case 115:
//#line 549 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera expresion lado derecho comparacion"); }
                break;
                case 116:
//#line 550 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera expresion lado derecho comparacion"); }
                break;
                case 117:
//#line 551 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera expresion lado derecho comparacion"); }
                break;
                case 118:
//#line 553 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera comparador"); }
                break;
                case 119:
//#line 555 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera ID lado izquierdo comparacion"); }
                break;
                case 120:
//#line 556 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera ID lado izquierdo comparacion"); }
                break;
                case 121:
//#line 557 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera ID lado izquierdo comparacion"); }
                break;
                case 122:
//#line 558 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera ID lado izquierdo comparacion"); }
                break;
                case 123:
//#line 559 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera ID lado izquierdo comparacion"); }
                break;
                case 124:
//#line 560 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera ID lado izquierdo comparacion"); }
                break;
                case 125:
//#line 564 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree  = new SyntacticTree(new SyntacticTree(null, null, val_peek(0).attributes.get(0).getScope()), "UP");
                }
                break;
                case 126:
//#line 568 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree  = new SyntacticTree(new SyntacticTree(null, null, val_peek(0).attributes.get(0).getScope()), "DOWN");
                }
                break;
                case 127:
//#line 572 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR decremento - Se espera NRO_ULONGINT"); }
                break;
                case 128:
//#line 573 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR incremento - Se espera NRO_ULONGINT"); }
                break;
                case 129:
//#line 574 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR incre/decre - Se espera UP/DOWN"); }
                break;
                case 130:
//#line 580 "G08 - Gramatica - 25102020.y"
                {
                    System.out.println(val_peek(1).attributes.get(0).getScope());
                    addRule("Linea "+ la.getNroLinea() +": Sentencia OUT");
                    yyval.tree  = new SyntacticTree(new SyntacticTree(null, null, val_peek(1).attributes.get(0).getScope()), "IMPRIMIR");
                }
                break;
                case 131:
//#line 586 "G08 - Gramatica - 25102020.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia OUT - Se espera ')'."); }
                break;
                case 132:
//#line 587 "G08 - Gramatica - 25102020.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia OUT - Se espera '('."); }
                break;
                case 133:
//#line 588 "G08 - Gramatica - 25102020.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia OUT - Se espera una cadena de caracteres luego de '('."); }
                break;
                case 134:
//#line 589 "G08 - Gramatica - 25102020.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia OUT - falta cadena"); }
                break;
                case 135:
//#line 590 "G08 - Gramatica - 25102020.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia OUT - Se espera '('."); }
                break;
                case 136:
//#line 596 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Conversion explicita");
                    yyval.tree  = new SyntacticTree(val_peek(3).tree, val_peek(1).tree, "CONVERSION");
                }
                break;
                case 137:
//#line 601 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Conversion explicita - Se espera ')'."); }
                break;
                case 138:
//#line 602 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Conversion explicita - Se espera expresion.");}
                break;
                case 139:
//#line 608 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Suma");
                    yyval.tree = new SyntacticTree(val_peek(2).tree, val_peek(0).tree, "+");
                }
                break;
                case 140:
//#line 613 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Resta");
                    yyval.tree = new SyntacticTree(val_peek(2).tree, val_peek(0).tree, "-");
                }
                break;
                case 141:
//#line 618 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 142:
//#line 622 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 143:
//#line 626 "G08 - Gramatica - 25102020.y"
                { addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Suma - Se espera un termino luego del '+'."); }
                break;
                case 144:
//#line 627 "G08 - Gramatica - 25102020.y"
                { addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Suma - Se espera una expresion antes del '+'."); }
                break;
                case 145:
//#line 628 "G08 - Gramatica - 25102020.y"
                { addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Resta - Se espera un termino luego del '-'."); }
                break;
                case 146:
//#line 629 "G08 - Gramatica - 25102020.y"
                { addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Resta - Se espera una expresion antes del '-'."); }
                break;
                case 147:
//#line 633 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Multiplicacion");
                    yyval.tree = new SyntacticTree(val_peek(2).tree, val_peek(0).tree, "*");
                }
                break;
                case 148:
//#line 638 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Division");
                    yyval.tree = new SyntacticTree(val_peek(2).tree, val_peek(0).tree, "/");
                }
                break;
                case 149:
//#line 643 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 150:
//#line 647 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Multiplicacion - Se espera un factor luego de * ");}
                break;
                case 151:
//#line 648 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Division - Se espera un factor luego de /");}
                break;
                case 152:
//#line 649 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Multiplicacion - Se espera un termino antes de * ");}
                break;
                case 153:
//#line 650 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Division - Se espera un termino antes de /");}
                break;
                case 154:
//#line 654 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": NRO_ULONGINT.");

                    yyval.tree  = new SyntacticTree(null, null, val_peek(0).attributes.get(0).getScope());
                }
                break;
                case 155:
//#line 660 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree  = new SyntacticTree(null, null, val_peek(0).attributes.get(0).getScope());

                    addRule("Linea "+ la.getNroLinea() +": NRO_DOUBLE.");
                    yyval.tree = new SyntacticTree(null, null, val_peek(0).attributes.get(0).getScope());
                }
                break;
                case 156:
//#line 666 "G08 - Gramatica - 25102020.y"
                {
                    String lexeme = val_peek(0).attributes.get(0).getScope();
                    boolean check = la.checkNegativeDouble(lexeme);
                    if(check){
                        addError("Error Sintáctico en línea "+ la.getNroLinea() +": DOUBLE fuera de rango.");
                    }else{
                        addRule("Linea "+ la.getNroLinea() +": NRO_DOUBLE negativo.");
                        Attribute attribute = new Attribute(lexeme,"NRO_DOUBLE", Type.DOUBLE);
                        la.addSymbolTable(lexeme, attribute);
                        val_peek(0).attributes.get(0).decreaseAmount();
                        int amount = la.getAttribute(lexeme).get(0).getAmount();
                        if(amount == 0){
                            la.getSt().deleteSymbolTableEntry(lexeme);
                        }
                    }
                }
                break;
                case 157:
//#line 683 "G08 - Gramatica - 25102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 158:
//#line 689 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": ID PUNTO_PUNTO ID");

                    String[] scopeIz = val_peek(2).attributes.get(0).getScope().split("@");
                    String lexemeIz = scopeIz[0];

                    String[] scopeDer = val_peek(0).attributes.get(0).getScope().split("@");
                    String lexemeDer = scopeDer[0];

                    yyval.tree  = new SyntacticTree(new SyntacticTree(null, null, lexemeIz), new SyntacticTree(null, null, lexemeDer), "::");
                }
                break;
                case 159:
//#line 701 "G08 - Gramatica - 25102020.y"
                {addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Tipo ID - Se espera ID luego de ::");}
                break;
                case 160:
//#line 704 "G08 - Gramatica - 25102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": ID");

                    String[] scope = val_peek(0).attributes.get(0).getScope().split("@");
                    String lexeme = scope[0];

                    yyval.tree  = new SyntacticTree(null, null, lexeme);

                    val_peek(0).attributes.get(val_peek(0).attributes.size()-1).setUse(Use.variable_en_uso);
                    List<Attribute> variables = getListUse(val_peek(0).attributes, Use.variable);
                    List<Attribute> parametros = getListUse(val_peek(0).attributes, Use.nombre_parametro);

                    boolean encontro = false;

                    if(variables.size() > 0){
                        if(parametros.size() > 0){
                            parametros.addAll(variables);
                            val_peek(0).attributes = parametros;
                        }else{
                            val_peek(0).attributes = variables;
                        }
                    }else{
                        if(parametros.size() > 0) {
                            val_peek(0).attributes = parametros;
                        }else {
                            val_peek(0).attributes = null;
                        }
                    }

                    if(val_peek(0).attributes == null){
                        encontro = false;
                    }else{
                        for (Attribute attribute : val_peek(0).attributes) {
                            encontro = sa.isRedeclared(this.globalScope, lexeme, attribute);
                            if (encontro) {

                                break;
                            }
                        }
                    }

                    if(!encontro){
                        addError("Error Semántico en línea "+ la.getNroLinea() +": No se encuentra variable al alcance");
                    }

                    this.deleteSTEntry(lexeme);
                }
                break;
//#line 1904 "Parser.java"
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
