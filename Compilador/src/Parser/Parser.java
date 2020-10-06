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






//#line 1 "G08 - Gramatica.y"


package Parser;

import Lexer.LexerAnalyzer;
import java.util.ArrayList;
import java.util.List;
import Lexer.Token;


//#line 28 "Parser.java"




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
            0,    1,    1,    1,    2,    2,    3,    3,    3,    3,
            4,    4,    4,    4,    4,    4,   12,   12,    6,    6,
            5,    5,    7,    7,    7,    7,    7,    7,    7,   17,
            17,   17,   17,   15,   15,   15,   18,   14,   14,   14,
            8,    8,    8,    8,    8,    8,   19,   20,   20,   20,
            23,   23,    9,    9,    9,   24,   24,   24,   24,   25,
            25,   10,   10,   10,   10,   10,   10,   13,   13,   13,
            16,   11,   11,   21,   21,   21,   21,   21,   27,   27,
            27,   27,   27,   28,   28,   28,   28,   26,   26,   22,
            22,   22,   22,   22,   22,
    };
    final static short yylen[] = {                            2,
            1,    1,    2,    2,    2,    1,    2,    1,    2,    2,
            2,    2,    2,    2,    2,    2,    4,    3,    1,    3,
            1,    1,    9,    8,    7,    6,    5,    4,    3,    1,
            2,    1,    2,    1,    3,    5,    2,    1,    3,    5,
            8,    6,    6,    8,    5,    7,    3,    3,    1,    3,
            1,    2,   11,   10,    2,    3,    3,    2,    2,    2,
            2,    4,    4,    3,    3,    4,    2,    4,    4,    3,
            3,    3,    3,    3,    3,    1,    3,    3,    3,    3,
            1,    3,    3,    1,    1,    2,    1,    3,    1,    1,
            1,    1,    1,    1,    1,
    };
    final static short yydefred[] = {                         0,
            0,    0,   21,    0,    0,    0,    0,   22,    0,    0,
            2,    0,    6,    0,    8,    0,    0,    0,    0,    0,
            0,    0,   19,    4,    0,    0,    0,    0,   55,    0,
            67,    0,    0,    0,    0,    3,    5,   10,    0,    0,
            11,   12,   13,   14,   15,   16,    0,    0,   88,    0,
            18,    0,    0,   85,   84,    0,    0,    0,   87,    0,
            81,    0,   64,   65,    0,    0,   29,    0,   70,    0,
            73,    0,   20,    0,   17,   86,    0,   94,   93,   92,
            95,    0,    0,   90,   91,    0,    0,    0,    0,   63,
            62,   66,   28,    0,    0,    0,    0,   69,   68,    0,
            45,    0,   49,    0,    0,   77,    0,   78,    0,    0,
            82,   79,   83,   80,    0,    0,    0,   37,   27,    0,
            0,    0,   51,    0,   43,    0,   42,    0,    0,    0,
            26,    0,    0,   40,   50,   48,   52,   46,    0,    0,
            0,    0,    0,   71,   30,    0,    0,   25,    0,    0,
            44,   41,    0,   59,   58,    0,   24,   31,    0,    0,
            36,   57,    0,    0,    0,    0,   23,   60,   61,   54,
            0,   53,
    };
    final static short yydgoto[] = {                          9,
            10,  145,   12,   13,   14,   25,   15,   16,   17,   18,
            19,   20,   21,   52,   96,  117,  147,   97,   57,  105,
            58,   86,  124,  143,  166,   22,   60,   61,
    };
    final static short yysindex[] = {                      -147,
            -56,  -25,    0,   41,    1,  -40, -251,    0,    0, -132,
            0,  -17,    0,   43,    0,  -15,    8,   29,   54,   62,
            64,  -30,    0,    0,   61, -142,   -1,   -9,    0, -129,
            0,   88,  -39,   20, -127,    0,    0,    0,  -38,   61,
            0,    0,    0,    0,    0,    0,  -36, -121,    0,   98,
            0,  102, -126,    0,    0, -130,  108,   27,    0,   72,
            0,   89,    0,    0,   -2, -105,    0,   -3,    0,   37,
            0,   47,    0, -104,    0,    0, -119,    0,    0,    0,
            0,  -34,  -28,    0,    0,   -9,  -26,  -19, -124,    0,
            0,    0,    0, -120, -102,    7,  118,    0,    0,  119,
            0,  101,    0,  112, -233,    0,   72,    0,   72,   47,
            0,    0,    0,    0,  105,  106,   51,    0,    0, -244,
            -237,  -89,    0,   87,    0,  -98,    0,  -44, -103, -132,
            0, -115,  132,    0,    0,    0,    0,    0, -229,   46,
            56,  -77,  121,    0,    0,    0,  -72,    0, -132, -237,
            0,    0,  -11,    0,    0, -212,    0,    0,    0,  -60,
            0,    0,   47,  -96,  -95,   15,    0,    0,    0,    0,
            -86,    0,
    };
    final static short yyrindex[] = {                         0,
            0,   89,    0,    0,    0,    0,    0,    0,    0,  189,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,  131,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,  135,
            0,    0,    0,    0,    0,    0,    0,    0,    0,  161,
            0,    0,    4,    0,    0,    0,    0,    0,    0,    9,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,  147,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,   45,    0,    0,  167,
            0,    0,    0,    0,    0,    0,   14,    0,   34,  168,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,   50,    0,    0,    0,    0,    0,    0,   55,
            0,    0,    0,    0,    0,   63,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,   75,    0,
            0,    0,  151,    0,    0,    0,    0,    0,    0,    0,
            0,    0,
    };
    final static short yygindex[] = {                         0,
            0,   10,    0,  276,  280,  197, -125,    0,    0,    0,
            0,    0,    0,    0,    0,   94,   66, -107,    0, -113,
            290,  -33,    0,    0,    0,  308,    2,   16,
    };
    final static int YYTABLESIZE=461;
    static short yytable[];
    static { yytable();}
    static void yytable(){
        yytable = new short[]{                         33,
                56,   66,   24,  102,  146,   34,   56,  149,   56,   11,
                56,  131,  139,  133,   27,   84,   56,   85,   56,   36,
                3,  159,  125,  146,  102,   56,  151,  126,  127,    8,
                47,  116,  152,   56,  159,   56,  102,   94,   91,   51,
                30,   37,  161,   41,   89,   89,   89,  120,   89,   76,
                89,   76,  157,   76,   74,  171,   74,  172,   74,   68,
                164,  165,   89,   89,  167,   89,   42,   76,   76,   82,
                76,   83,   74,   74,   75,   74,   75,   99,   75,   82,
                28,   83,   39,  107,  109,   34,   84,   43,   85,   82,
                35,   83,   75,   75,  142,   75,   89,   89,   82,   89,
                83,   89,  112,  114,   48,   84,  153,   85,    1,    2,
                3,    4,   44,   87,   49,    5,    6,    7,   88,    8,
                45,    8,   46,   35,    2,    3,    4,   62,   63,   23,
                5,    6,    7,    8,    8,   73,  101,    2,    3,    4,
                148,   74,   75,    5,    6,   26,   76,    8,   77,   89,
                92,   39,  100,  115,  118,  116,  158,  138,    2,    3,
                4,  121,  122,  128,    5,    6,  129,  134,    8,  158,
                2,    3,    4,  130,  144,  150,    5,    6,  155,  156,
                8,  168,  169,   35,    2,    3,    4,   32,    1,    9,
                5,    6,    7,    7,    8,   35,    2,    3,    4,   33,
                23,   38,    5,    6,    7,   72,    8,   39,   47,   56,
                40,  136,  140,  132,  160,   31,   64,   69,   53,   71,
                53,  106,   53,   78,   79,   80,   81,  108,   53,  111,
                53,    0,   54,   55,   32,   65,  113,   53,   54,   55,
                54,   55,   54,   55,  162,   53,   26,   53,   54,   55,
                54,   55,   93,   90,    3,   50,   29,   54,   55,   89,
                0,    0,  119,    8,   76,   54,   55,   54,   55,   74,
                170,   89,   89,   89,   89,   67,   76,   76,   76,   76,
                0,   74,   74,   74,   74,    0,    0,    0,    0,   75,
                0,    0,   98,    0,   78,   79,   80,   81,   38,   23,
                34,   75,   75,   75,   75,   35,    0,    0,    0,    0,
                89,  154,    0,   78,   79,   80,   81,   26,   32,   32,
                32,   32,    0,    0,    0,   32,   32,   32,   70,   32,
                33,   33,   33,   33,    0,   59,   72,   33,   33,   33,
                0,   33,  135,    2,    3,    4,   59,   95,    0,    5,
                6,    0,  103,    8,   59,    0,  104,    2,    3,    4,
                0,    0,    0,    5,    6,    0,    0,    8,    0,    0,
                0,    0,    0,    0,    0,  110,    0,  123,    0,    0,
                0,  104,    0,    0,    0,    0,    0,    0,    0,   59,
                59,    0,    0,   59,   59,   59,    0,    0,    0,  137,
                95,  103,    0,  104,    0,  104,    0,    0,    0,    0,
                0,    0,    0,    0,    0,    0,    0,  141,    0,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,   95,
                0,    0,    0,    0,    0,   59,    0,    0,    0,    0,
                0,    0,  163,    0,    0,    0,  103,    0,    0,    0,
                104,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                59,
        };
    }
    static short yycheck[];
    static { yycheck(); }
    static void yycheck() {
        yycheck = new short[] {                         40,
                45,   41,   59,  123,  130,  257,   45,  123,   45,    0,
                45,  256,  126,  121,   40,   60,   45,   62,   45,   10,
                258,  147,  256,  149,  123,   45,  256,  261,  262,  267,
                61,  276,  262,   45,  160,   45,  123,   41,   41,   41,
                40,   59,  150,   59,   41,   42,   43,   41,   45,   41,
                47,   43,  125,   45,   41,   41,   43,  171,   45,   40,
                273,  274,   59,   60,  125,   62,   59,   59,   60,   43,
                62,   45,   59,   60,   41,   62,   43,   41,   45,   43,
                40,   45,   40,   82,   83,   41,   60,   59,   62,   43,
                41,   45,   59,   60,  128,   62,   42,   43,   43,   45,
                45,   47,   87,   88,   44,   60,  140,   62,  256,  257,
                258,  259,   59,   42,  257,  263,  264,  265,   47,  267,
                59,   59,   59,  256,  257,  258,  259,  257,   41,  257,
                263,  264,  265,   59,  267,  257,  256,  257,  258,  259,
                256,   44,   41,  263,  264,  272,  277,  267,   41,   61,
                256,   40,  257,  278,  257,  276,  147,  256,  257,  258,
                259,   44,   44,   59,  263,  264,   61,  257,  267,  160,
                257,  258,  259,  123,  278,   44,  263,  264,  256,   59,
                267,  278,  278,  256,  257,  258,  259,  125,    0,   59,
                263,  264,  265,   59,  267,  256,  257,  258,  259,  125,
                257,   41,  263,  264,  265,   59,  267,   41,   41,   59,
                14,  125,  257,  120,  149,  256,  256,  256,  257,  256,
                257,  256,  257,  268,  269,  270,  271,  256,  257,  256,
                257,   -1,  277,  278,  275,  275,  256,  257,  277,  278,
                277,  278,  277,  278,  256,  257,  272,  257,  277,  278,
                277,  278,  256,  256,  258,  257,  256,  277,  278,  256,
                -1,   -1,  256,  267,  256,  277,  278,  277,  278,  256,
                256,  268,  269,  270,  271,  256,  268,  269,  270,  271,
                -1,  268,  269,  270,  271,   -1,   -1,   -1,   -1,  256,
                -1,   -1,  256,   -1,  268,  269,  270,  271,  256,  257,
                256,  268,  269,  270,  271,  256,   -1,   -1,   -1,   -1,
                256,  256,   -1,  268,  269,  270,  271,  272,  256,  257,
                258,  259,   -1,   -1,   -1,  263,  264,  265,   39,  267,
                256,  257,  258,  259,   -1,   28,   47,  263,  264,  265,
                -1,  267,  256,  257,  258,  259,   39,   68,   -1,  263,
                264,   -1,   77,  267,   47,   -1,   77,  257,  258,  259,
                -1,   -1,   -1,  263,  264,   -1,   -1,  267,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   86,   -1,  102,   -1,   -1,
                -1,  102,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   82,
                83,   -1,   -1,   86,   87,   88,   -1,   -1,   -1,  124,
                121,  126,   -1,  124,   -1,  126,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,  128,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  150,
                -1,   -1,   -1,   -1,   -1,  128,   -1,   -1,   -1,   -1,
                -1,   -1,  153,   -1,   -1,   -1,  171,   -1,   -1,   -1,
                171,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                153,
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
            "sent_declarativa : error lista_variables",
            "sent_declarativa : tipo error",
            "sent_ejecutable : sentencia_if ';'",
            "sent_ejecutable : sentencia_control ';'",
            "sent_ejecutable : imprimir ';'",
            "sent_ejecutable : asignacion ';'",
            "sent_ejecutable : llamado_PROC ';'",
            "sent_ejecutable : conversion_explicita ';'",
            "llamado_PROC : ID '(' parametro_invocacion ')'",
            "llamado_PROC : ID '(' ')'",
            "lista_variables : ID",
            "lista_variables : lista_variables ',' ID",
            "tipo : ULONGINT",
            "tipo : DOUBLE",
            "procedimiento : PROC ID '(' lista_parametros ')' asignacion_NA '{' cuerpo_procedimiento '}'",
            "procedimiento : PROC ID '(' ')' asignacion_NA '{' cuerpo_procedimiento '}'",
            "procedimiento : PROC ID '(' lista_parametros ')' asignacion_NA error",
            "procedimiento : PROC ID '(' lista_parametros ')' error",
            "procedimiento : PROC ID '(' lista_parametros error",
            "procedimiento : PROC ID '(' error",
            "procedimiento : PROC ID error",
            "cuerpo_procedimiento : sentencia",
            "cuerpo_procedimiento : cuerpo_procedimiento sentencia",
            "cuerpo_procedimiento : procedimiento",
            "cuerpo_procedimiento : cuerpo_procedimiento procedimiento",
            "lista_parametros : parametro",
            "lista_parametros : parametro ',' parametro",
            "lista_parametros : parametro ',' parametro ',' parametro",
            "parametro : tipo ID",
            "parametro_invocacion : ID",
            "parametro_invocacion : ID ',' ID",
            "parametro_invocacion : ID ',' ID ',' ID",
            "sentencia_if : IF '(' condicion_IF ')' bloque ELSE bloque END_IF",
            "sentencia_if : IF '(' condicion_IF ')' bloque END_IF",
            "sentencia_if : IF '(' condicion_IF ')' bloque error",
            "sentencia_if : IF '(' condicion_IF ')' bloque ELSE bloque error",
            "sentencia_if : IF '(' condicion_IF ')' error",
            "sentencia_if : IF '(' condicion_IF ')' bloque ELSE error",
            "condicion_IF : expresion comparador expresion",
            "bloque : '{' cuerpo_ejecutable '}'",
            "bloque : sent_ejecutable",
            "bloque : '{' cuerpo_ejecutable error",
            "cuerpo_ejecutable : sent_ejecutable",
            "cuerpo_ejecutable : cuerpo_ejecutable sent_ejecutable",
            "sentencia_control : FOR '(' ID '=' NRO_ULONGINT ';' condicion_FOR ';' incr_decr ')' bloque",
            "sentencia_control : FOR '(' ID '=' NRO_ULONGINT ';' condicion_FOR ';' incr_decr error",
            "sentencia_control : FOR error",
            "condicion_FOR : ID comparador expresion",
            "condicion_FOR : ID comparador error",
            "condicion_FOR : comparador error",
            "condicion_FOR : expresion error",
            "incr_decr : UP NRO_ULONGINT",
            "incr_decr : DOWN NRO_ULONGINT",
            "imprimir : OUT '(' CADENA ')'",
            "imprimir : OUT '(' CADENA error",
            "imprimir : OUT CADENA ')'",
            "imprimir : OUT '(' error",
            "imprimir : OUT '(' ')' error",
            "imprimir : OUT error",
            "conversion_explicita : tipo '(' expresion ')'",
            "conversion_explicita : tipo '(' expresion error",
            "conversion_explicita : tipo '(' error",
            "asignacion_NA : NA '=' NRO_ULONGINT",
            "asignacion : tipo_ID '=' expresion",
            "asignacion : tipo_ID '=' error",
            "expresion : expresion '+' termino",
            "expresion : expresion '-' termino",
            "expresion : termino",
            "expresion : expresion '+' error",
            "expresion : expresion '-' error",
            "termino : termino '*' factor",
            "termino : termino '/' factor",
            "termino : factor",
            "termino : termino '*' error",
            "termino : termino '/' error",
            "factor : NRO_ULONGINT",
            "factor : NRO_DOUBLE",
            "factor : '-' NRO_DOUBLE",
            "factor : tipo_ID",
            "tipo_ID : ID PUNTO_PUNTO ID",
            "tipo_ID : ID",
            "comparador : '<'",
            "comparador : '>'",
            "comparador : IGUAL",
            "comparador : MAYOR_IGUAL",
            "comparador : MENOR_IGUAL",
            "comparador : DISTINTO",
    };

//#line 224 "G08 - Gramatica.y"


    private LexerAnalyzer la;
    private List<String> errors;
    private List<String> rules;

    public Parser(LexerAnalyzer la){
        this.errors = new ArrayList<String>();
        this.rules = new ArrayList<String>();
        this.la = la;
    }

    public int yylex(){
        Token token = this.la.getNextToken();
        yylval = new ParserVal(token.getLexema());
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
    //#line 485 "Parser.java"
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
                case 7:
//#line 36 "G08 - Gramatica.y"
                {addRule("Linea "+ la.getNroLinea() +": Sentencia declarativa - Variable/s."); }
                break;
                case 8:
//#line 37 "G08 - Gramatica.y"
                {addRule("Linea "+ la.getNroLinea() +": Sentencia declarativa - Procedimiento."); }
                break;
                case 9:
//#line 39 "G08 - Gramatica.y"
                {addError("Linea "+ la.getNroLinea() +": falta definir el tipo de la/s variables.");}
                break;
                case 10:
//#line 40 "G08 - Gramatica.y"
                {addError("Linea "+ la.getNroLinea() +": falta definir la/s variable/s."); }
                break;
                case 17:
//#line 51 "G08 - Gramatica.y"
                { addRule("Linea "+ la.getNroLinea() +": LLAMADO A PROCEDIMIENTO CON PARÁMETROS.");}
                break;
                case 18:
//#line 52 "G08 - Gramatica.y"
                { addRule("Linea "+ la.getNroLinea() +": LLAMADO A PROCEDIMIENTO SIN PARÁMETROS.");}
                break;
                case 23:
//#line 69 "G08 - Gramatica.y"
                { addRule("Linea "+ la.getNroLinea() +": PROCEDIMIENTO con PARÁMETROS.");}
                break;
                case 24:
//#line 70 "G08 - Gramatica.y"
                { addRule("Linea "+ la.getNroLinea() +": PROCEDIMIENTO sin PARÁMETROS.");}
                break;
                case 25:
//#line 72 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": faltan llaves");}
                break;
                case 26:
//#line 74 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": falta asignacion_NA");}
                break;
                case 27:
//#line 76 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": falta ')' ");}
                break;
                case 28:
//#line 78 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": falta ')' ");}
                break;
                case 29:
//#line 79 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": falta '(' ");}
                break;
                case 41:
//#line 108 "G08 - Gramatica.y"
                {addRule("Linea "+ la.getNroLinea() +": Sentencia IF ELSE.");}
                break;
                case 42:
//#line 109 "G08 - Gramatica.y"
                {addRule("Linea "+ la.getNroLinea() +": Sentencia IF");}
                break;
                case 43:
//#line 112 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": falta END_IF.");}
                break;
                case 44:
//#line 113 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": falta END_IF.");}
                break;
                case 45:
//#line 114 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": falta bloque.");}
                break;
                case 46:
//#line 115 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": falta bloque ELSE.");}
                break;
                case 47:
//#line 117 "G08 - Gramatica.y"
                {addRule("Linea "+ la.getNroLinea() +": CONDICION IF.");}
                break;
                case 48:
//#line 123 "G08 - Gramatica.y"
                { addRule("Linea "+ la.getNroLinea() +": BLOQUE DE SENTENCIAS.");}
                break;
                case 50:
//#line 126 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": falta '}' del bloque. ");}
                break;
                case 53:
//#line 136 "G08 - Gramatica.y"
                {addRule("Linea "+ la.getNroLinea() +": Sentencia FOR.");}
                break;
                case 54:
//#line 138 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": falta ')' ");}
                break;
                case 55:
//#line 140 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": error en sentencia FOR ");}
                break;
                case 56:
//#line 143 "G08 - Gramatica.y"
                {addRule("Linea "+ la.getNroLinea() +": CONDICION FOR.");}
                break;
                case 57:
//#line 146 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera una expresion luego del operador de comparacion."); }
                break;
                case 58:
//#line 147 "G08 - Gramatica.y"
                { addError("Error Sintáctico en línea "+ la.getNroLinea() +": Se espera la condición antes del operador de comparación."); }
                break;
                case 59:
//#line 148 "G08 - Gramatica.y"
                { addError("Error Sintáctico en línea "+ la.getNroLinea() +": Condicion definida incorrectamente."); }
                break;
                case 62:
//#line 157 "G08 - Gramatica.y"
                {System.out.println(val_peek(1).sval);addRule("Linea "+ la.getNroLinea() +": Sentencia OUT.");}
                break;
                case 63:
//#line 159 "G08 - Gramatica.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera ')'."); }
                break;
                case 64:
//#line 160 "G08 - Gramatica.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera '('."); }
                break;
                case 65:
//#line 161 "G08 - Gramatica.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera una cadena de caracteres luego de '('."); }
                break;
                case 66:
//#line 162 "G08 - Gramatica.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": falta cadena"); }
                break;
                case 67:
//#line 163 "G08 - Gramatica.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera '('."); }
                break;
                case 68:
//#line 168 "G08 - Gramatica.y"
                {addRule("Linea "+ la.getNroLinea() +": CONVERSION EXPLICITA.");}
                break;
                case 69:
//#line 170 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera ')'."); }
                break;
                case 70:
//#line 171 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera expresion."); }
                break;
                case 72:
//#line 179 "G08 - Gramatica.y"
                {addRule("Linea "+ la.getNroLinea() +": ASIGNACION.");}
                break;
                case 73:
//#line 180 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() + ": Expresion invalida en asignacion.");}
                break;
                case 77:
//#line 190 "G08 - Gramatica.y"
                { addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Se espera un termino luego del '+'."); }
                break;
                case 78:
//#line 191 "G08 - Gramatica.y"
                { addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Se espera un termino luego del '-'."); }
                break;
                case 82:
//#line 198 "G08 - Gramatica.y"
                {addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Se espera un factor * ");}
                break;
                case 83:
//#line 199 "G08 - Gramatica.y"
                {addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Se espera un factor");}
                break;
                case 86:
//#line 204 "G08 - Gramatica.y"
                {
                    String lexeme ='-'+ val_peek(0).sval;
                    this.la.addSymbolTable(lexeme, "NRO_DOUBLE");
                }
                break;
//#line 817 "Parser.java"
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
