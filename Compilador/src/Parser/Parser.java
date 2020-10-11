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
import SyntacticTree.SyntacticTree;
import SymbolTable.Attribute;


//#line 29 "Parser.java"




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
            8,    8,    8,    8,    8,   19,   20,   20,   20,   21,
            21,   24,   24,    9,    9,    9,   25,   26,   26,   26,
            26,   27,   27,   10,   10,   10,   10,   10,   10,   13,
            13,   13,   16,   11,   11,   22,   22,   22,   22,   22,
            29,   29,   29,   29,   29,   30,   30,   30,   30,   28,
            28,   23,   23,   23,   23,   23,   23,
    };
    final static short yylen[] = {                            2,
            1,    1,    2,    2,    2,    1,    2,    1,    2,    2,
            2,    2,    2,    2,    2,    2,    4,    3,    1,    3,
            1,    1,    9,    8,    7,    6,    5,    4,    3,    1,
            2,    1,    2,    1,    3,    5,    2,    1,    3,    5,
            7,    6,    6,    7,    5,    3,    3,    1,    3,    2,
            2,    1,    2,    9,    8,    2,    3,    3,    3,    2,
            2,    2,    2,    4,    4,    3,    3,    4,    2,    4,
            4,    3,    3,    3,    3,    3,    3,    1,    3,    3,
            3,    3,    1,    3,    3,    1,    1,    2,    1,    3,
            1,    1,    1,    1,    1,    1,    1,
    };
    final static short yydefred[] = {                         0,
            0,    0,   21,    0,    0,    0,    0,   22,    0,    0,
            2,    0,    6,    0,    8,    0,    0,    0,    0,    0,
            0,    0,   19,    4,    0,    0,    0,    0,   56,    0,
            69,    0,    0,    0,    0,    3,    5,   10,    0,    0,
            11,   12,   13,   14,   15,   16,    0,    0,   90,    0,
            18,    0,    0,   87,   86,    0,    0,    0,   89,    0,
            83,    0,    0,   66,   67,    0,    0,   29,    0,   72,
            0,   75,    0,   20,    0,   17,   88,    0,   96,   95,
            94,   97,    0,    0,   92,   93,    0,    0,    0,    0,
            0,   65,   64,   68,   28,    0,    0,    0,    0,   71,
            70,    0,   45,    0,   48,    0,    0,   79,    0,   80,
            0,    0,   84,   81,   85,   82,   57,    0,    0,    0,
            0,    0,    0,   37,   27,    0,    0,    0,   52,    0,
            43,    0,   42,    0,    0,   61,   60,    0,    0,    0,
            26,    0,    0,   40,   49,   47,   53,   51,   50,   44,
            41,   59,    0,    0,    0,    0,   73,   30,    0,    0,
            25,    0,    0,   62,   63,   55,    0,   24,   31,    0,
            0,   36,   54,   23,
    };
    final static short yydgoto[] = {                          9,
            10,  158,   12,   13,   14,   25,   15,   16,   17,   18,
            19,   20,   21,   52,   98,  123,  160,   99,   57,  107,
            134,   58,   87,  130,   63,  121,  156,   22,   60,   61,
    };
    final static short yysindex[] = {                       100,
            -56,  -25,    0,   22,    1,  -40, -190,    0,    0,  112,
            0,   33,    0,   43,    0,   52,   59,   60,   62,   64,
            65,   66,    0,    0,   82, -176,   -1,   -9,    0, -150,
            0,   88,  -39,   20, -129,    0,    0,    0,  -38,   82,
            0,    0,    0,    0,    0,    0,  -36, -126,    0,   86,
            0,   91, -139,    0,    0, -142,   95,   27,    0,   48,
            0,   81,   90,    0,    0,   -2, -110,    0,   -3,    0,
            37,    0,  -31,    0, -107,    0,    0, -119,    0,    0,
            0,    0,  -34,  -28,    0,    0,   -9,  -26,  -19, -131,
            -44,    0,    0,    0,    0, -124, -104,    7,  114,    0,
            0,  115,    0, -154,    0,  123, -232,    0,   48,    0,
            48,  -31,    0,    0,    0,    0,    0,   46,   56,  -81,
            119,  121,   57,    0,    0, -251, -236,  -74,    0,   87,
            0, -102,    0, -219,  -11,    0,    0, -246,  -89,  112,
            0, -115,  146,    0,    0,    0,    0,    0,    0,    0,
            0,    0,  -31,  -84,  -76,   15,    0,    0,    0,  -72,
            0,  112, -236,    0,    0,    0,  -91,    0,    0,    0,
            -60,    0,    0,    0,
    };
    final static short yyrindex[] = {                         0,
            0,  148,    0,    0,    0,    0,    0,    0,    0,  211,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,  156,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,  173,
            0,    0,    0,    0,    0,    0,    0,    0,    0,  220,
            0,    0,    4,    0,    0,    0,    0,    0,    0,    9,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,  203,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,   45,    0,
            0,  240,    0,    0,    0,    0,    0,    0,   14,    0,
            34,  245,    0,    0,    0,    0,    0,   55,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,   50,    0,    0,    0,    0,    0,    0,    0,
            0,    0,  228,    0,    0,    0,    0,    0,   63,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,   75,
            0,    0,    0,    0,
    };
    final static short yygindex[] = {                         0,
            0,   10,    0,   39,   47,  274, -127,    0,    0,    0,
            0,    0,    0,    0,    0,  163,  129, -121,    0, -109,
            0,   73,  -30,    0,    0,    0,    0,  308,   31,   -4,
    };
    final static int YYTABLESIZE=443;
    static short yytable[];
    static { yytable();}
    static void yytable(){
        yytable = new short[]{                         33,
                56,   67,   24,  104,  141,  143,   56,  162,   56,   11,
                56,   83,  159,   84,   27,   85,   56,   86,   56,   36,
                104,    3,  149,  131,  122,   56,  154,  155,  132,  133,
                8,  104,  170,   56,  159,   56,  150,   96,   93,   51,
                30,  172,  151,  170,   91,   91,   91,  126,   91,   78,
                91,   78,  168,   78,   76,  167,   76,  173,   76,   69,
                120,   28,   91,   91,  174,   91,   34,   78,   78,   83,
                78,   84,   76,   76,   77,   76,   77,  101,   77,   83,
                49,   84,   39,  114,  116,   34,   85,  135,   86,   88,
                35,   37,   77,   77,   89,   77,   91,   91,   83,   91,
                84,   91,    2,    3,    4,   85,   62,   86,    5,    6,
                41,   71,    8,  109,  111,   97,  105,   42,   43,   73,
                44,    8,   45,   46,  106,   48,   47,   23,   64,   75,
                74,   76,   26,    8,   77,   78,  103,    2,    3,    4,
                161,   90,  129,    5,    6,   94,  117,    8,   91,  102,
                106,  122,  124,  148,    2,    3,    4,  127,  128,  112,
                5,    6,   39,  119,    8,    2,    3,    4,  147,  169,
                105,    5,    6,   97,  137,    8,  106,  138,  106,  140,
                169,  139,  144,   35,    2,    3,    4,   32,  157,  163,
                5,    6,    7,  164,    8,   35,    2,    3,    4,   33,
                23,  165,    5,    6,    7,  105,    8,  153,   91,   97,
                1,  146,  118,  106,    9,   31,   65,   70,   53,   72,
                53,  108,   53,   79,   80,   81,   82,  110,   53,  113,
                53,    7,   54,   55,   32,   66,  115,   53,   54,   55,
                54,   55,   54,   55,  152,   53,   26,   53,   54,   55,
                54,   55,   95,   92,    3,   50,   29,   54,   55,   91,
                38,   74,  125,    8,   78,   54,   55,   54,   55,   76,
                166,   91,   91,   91,   91,   68,   78,   78,   78,   78,
                39,   76,   76,   76,   76,   46,   58,   40,  142,   77,
                171,    0,  100,    0,   79,   80,   81,   82,   38,   23,
                34,   77,   77,   77,   77,   35,    0,    0,    0,    0,
                91,  136,    0,   79,   80,   81,   82,   26,   32,   32,
                32,   32,    0,    0,    0,   32,   32,   32,    0,   32,
                33,   33,   33,   33,    0,   59,    0,   33,   33,   33,
                0,   33,  145,    2,    3,    4,   59,    0,    0,    5,
                6,    0,    0,    8,   59,    1,    2,    3,    4,    0,
                0,    0,    5,    6,    7,    0,    8,   35,    2,    3,
                4,    0,    0,    0,    5,    6,    7,    0,    8,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                59,   59,    0,    0,   59,   59,   59,    0,   59,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                0,    0,   59,
        };
    }
    static short yycheck[];
    static { yycheck(); }
    static void yycheck() {
        yycheck = new short[] {                         40,
                45,   41,   59,  123,  256,  127,   45,  123,   45,    0,
                45,   43,  140,   45,   40,   60,   45,   62,   45,   10,
                123,  258,  132,  256,  276,   45,  273,  274,  261,  262,
                267,  123,  160,   45,  162,   45,  256,   41,   41,   41,
                40,  163,  262,  171,   41,   42,   43,   41,   45,   41,
                47,   43,  125,   45,   41,   41,   43,  167,   45,   40,
                91,   40,   59,   60,  125,   62,  257,   59,   60,   43,
                62,   45,   59,   60,   41,   62,   43,   41,   45,   43,
                257,   45,   40,   88,   89,   41,   60,  118,   62,   42,
                41,   59,   59,   60,   47,   62,   42,   43,   43,   45,
                45,   47,  257,  258,  259,   60,  257,   62,  263,  264,
                59,   39,  267,   83,   84,   69,   78,   59,   59,   47,
                59,   59,   59,   59,   78,   44,   61,  257,   41,   44,
                257,   41,  272,   59,  277,   41,  256,  257,  258,  259,
                256,   61,  104,  263,  264,  256,  278,  267,   59,  257,
                104,  276,  257,  256,  257,  258,  259,   44,   44,   87,
                263,  264,   40,   91,  267,  257,  258,  259,  130,  160,
                132,  263,  264,  127,  256,  267,  130,   59,  132,  123,
                171,   61,  257,  256,  257,  258,  259,  125,  278,   44,
                263,  264,  265,  278,  267,  256,  257,  258,  259,  125,
                257,  278,  263,  264,  265,  167,  267,  135,   61,  163,
                0,  125,  257,  167,   59,  256,  256,  256,  257,  256,
                257,  256,  257,  268,  269,  270,  271,  256,  257,  256,
                257,   59,  277,  278,  275,  275,  256,  257,  277,  278,
                277,  278,  277,  278,  256,  257,  272,  257,  277,  278,
                277,  278,  256,  256,  258,  257,  256,  277,  278,  256,
                41,   59,  256,  267,  256,  277,  278,  277,  278,  256,
                256,  268,  269,  270,  271,  256,  268,  269,  270,  271,
                41,  268,  269,  270,  271,   41,   59,   14,  126,  256,
                162,   -1,  256,   -1,  268,  269,  270,  271,  256,  257,
                256,  268,  269,  270,  271,  256,   -1,   -1,   -1,   -1,
                256,  256,   -1,  268,  269,  270,  271,  272,  256,  257,
                258,  259,   -1,   -1,   -1,  263,  264,  265,   -1,  267,
                256,  257,  258,  259,   -1,   28,   -1,  263,  264,  265,
                -1,  267,  256,  257,  258,  259,   39,   -1,   -1,  263,
                264,   -1,   -1,  267,   47,  256,  257,  258,  259,   -1,
                -1,   -1,  263,  264,  265,   -1,  267,  256,  257,  258,
                259,   -1,   -1,   -1,  263,  264,  265,   -1,  267,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                83,   84,   -1,   -1,   87,   88,   89,   -1,   91,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,  135,
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
            "sentencia_if : IF '(' condicion_IF ')' bloque bloque_else END_IF",
            "sentencia_if : IF '(' condicion_IF ')' bloque END_IF",
            "sentencia_if : IF '(' condicion_IF ')' bloque error",
            "sentencia_if : IF '(' condicion_IF ')' bloque bloque_else error",
            "sentencia_if : IF '(' condicion_IF ')' error",
            "condicion_IF : expresion comparador expresion",
            "bloque : '{' cuerpo_ejecutable '}'",
            "bloque : sent_ejecutable",
            "bloque : '{' cuerpo_ejecutable error",
            "bloque_else : ELSE bloque",
            "bloque_else : ELSE error",
            "cuerpo_ejecutable : sent_ejecutable",
            "cuerpo_ejecutable : cuerpo_ejecutable sent_ejecutable",
            "sentencia_control : FOR '(' asignacion_FOR ';' condicion_FOR ';' incr_decr ')' bloque",
            "sentencia_control : FOR '(' asignacion_FOR ';' condicion_FOR ';' incr_decr error",
            "sentencia_control : FOR error",
            "asignacion_FOR : ID '=' NRO_ULONGINT",
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

//#line 246 "G08 - Gramatica.y"


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

    public void printSyntacticTree(){
        syntacticTree.printTree(syntacticTree.getRoot());
    }
    //#line 489 "Parser.java"
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
                case 6:
//#line 33 "G08 - Gramatica.y"
                { syntacticTree = val_peek(0).tree;}
                break;
                case 7:
//#line 37 "G08 - Gramatica.y"
                {addRule("Linea "+ la.getNroLinea() +": Sentencia declarativa - Variable/s."); }
                break;
                case 8:
//#line 38 "G08 - Gramatica.y"
                {addRule("Linea "+ la.getNroLinea() +": Sentencia declarativa - Procedimiento."); }
                break;
                case 9:
//#line 40 "G08 - Gramatica.y"
                {addError("Linea "+ la.getNroLinea() +": falta definir el tipo de la/s variables.");}
                break;
                case 10:
//#line 41 "G08 - Gramatica.y"
                {addError("Linea "+ la.getNroLinea() +": falta definir la/s variable/s."); }
                break;
                case 17:
//#line 52 "G08 - Gramatica.y"
                { addRule("Linea "+ la.getNroLinea() +": LLAMADO A PROCEDIMIENTO CON PARÁMETROS.");}
                break;
                case 18:
//#line 53 "G08 - Gramatica.y"
                { addRule("Linea "+ la.getNroLinea() +": LLAMADO A PROCEDIMIENTO SIN PARÁMETROS.");}
                break;
                case 23:
//#line 70 "G08 - Gramatica.y"
                { addRule("Linea "+ la.getNroLinea() +": PROCEDIMIENTO con PARÁMETROS.");}
                break;
                case 24:
//#line 71 "G08 - Gramatica.y"
                { addRule("Linea "+ la.getNroLinea() +": PROCEDIMIENTO sin PARÁMETROS.");}
                break;
                case 25:
//#line 73 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": faltan llaves");}
                break;
                case 26:
//#line 75 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": falta asignacion_NA");}
                break;
                case 27:
//#line 77 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": falta ')' ");}
                break;
                case 28:
//#line 79 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": falta ')' ");}
                break;
                case 29:
//#line 80 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": falta '(' ");}
                break;
                case 41:
//#line 109 "G08 - Gramatica.y"
                {addRule("Linea "+ la.getNroLinea() +": Sentencia IF ELSE.");}
                break;
                case 42:
//#line 110 "G08 - Gramatica.y"
                {addRule("Linea "+ la.getNroLinea() +": Sentencia IF");}
                break;
                case 43:
//#line 113 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": falta END_IF.");}
                break;
                case 44:
//#line 114 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": falta END_IF.");}
                break;
                case 45:
//#line 115 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": falta bloque.");}
                break;
                case 46:
//#line 119 "G08 - Gramatica.y"
                {addRule("Linea "+ la.getNroLinea() +": CONDICION IF.");}
                break;
                case 47:
//#line 123 "G08 - Gramatica.y"
                { addRule("Linea "+ la.getNroLinea() +": BLOQUE DE SENTENCIAS.");}
                break;
                case 49:
//#line 126 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": falta '}' del bloque. ");}
                break;
                case 51:
//#line 130 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": falta bloque ELSE.");}
                break;
                case 54:
//#line 140 "G08 - Gramatica.y"
                {addRule("Linea "+ la.getNroLinea() +": Sentencia FOR.");}
                break;
                case 55:
//#line 142 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": falta ')' ");}
                break;
                case 56:
//#line 144 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": error en sentencia FOR ");}
                break;
                case 58:
//#line 150 "G08 - Gramatica.y"
                {addRule("Linea "+ la.getNroLinea() +": CONDICION FOR.");}
                break;
                case 59:
//#line 153 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera una expresion luego del operador de comparacion."); }
                break;
                case 60:
//#line 154 "G08 - Gramatica.y"
                { addError("Error Sintáctico en línea "+ la.getNroLinea() +": Se espera la condición antes del operador de comparación."); }
                break;
                case 61:
//#line 155 "G08 - Gramatica.y"
                { addError("Error Sintáctico en línea "+ la.getNroLinea() +": Condicion definida incorrectamente."); }
                break;
                case 64:
//#line 164 "G08 - Gramatica.y"
                {	System.out.println(val_peek(1).sval);
                    addRule("Linea "+ la.getNroLinea() +": Sentencia OUT.");
                }
                break;
                case 65:
//#line 168 "G08 - Gramatica.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera ')'."); }
                break;
                case 66:
//#line 169 "G08 - Gramatica.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera '('."); }
                break;
                case 67:
//#line 170 "G08 - Gramatica.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera una cadena de caracteres luego de '('."); }
                break;
                case 68:
//#line 171 "G08 - Gramatica.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": falta cadena"); }
                break;
                case 69:
//#line 172 "G08 - Gramatica.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera '('."); }
                break;
                case 70:
//#line 177 "G08 - Gramatica.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": CONVERSION EXPLICITA.");
                }
                break;
                case 71:
//#line 181 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera ')'."); }
                break;
                case 72:
//#line 182 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera expresion."); }
                break;
                case 73:
//#line 187 "G08 - Gramatica.y"
                {
                    yyval.tree  = new SyntacticTree(null, null, val_peek(2).sval);
                    yyval.tree  = new SyntacticTree(null, null, val_peek(0).sval);
                    yyval.tree  = new SyntacticTree(val_peek(2).tree, val_peek(0).tree, val_peek(1).sval);
                }
                break;
                case 74:
//#line 194 "G08 - Gramatica.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": ASIGNACION.");
                    yyval.tree = new SyntacticTree(val_peek(2).tree, val_peek(0).tree, val_peek(1).sval);
                }
                break;
                case 75:
//#line 200 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() + ": Expresion invalida en asignacion.");}
                break;
                case 76:
//#line 206 "G08 - Gramatica.y"
                {yyval.tree = new SyntacticTree(val_peek(2).tree, val_peek(0).tree, val_peek(1).sval);}
                break;
                case 77:
//#line 207 "G08 - Gramatica.y"
                {yyval.tree = new SyntacticTree(val_peek(2).tree, val_peek(0).tree, val_peek(1).sval);}
                break;
                case 79:
//#line 210 "G08 - Gramatica.y"
                { addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Se espera un termino luego del '+'."); }
                break;
                case 80:
//#line 211 "G08 - Gramatica.y"
                { addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Se espera un termino luego del '-'."); }
                break;
                case 81:
//#line 214 "G08 - Gramatica.y"
                {yyval.tree = new SyntacticTree(val_peek(2).tree, val_peek(0).tree, val_peek(1).sval);}
                break;
                case 82:
//#line 215 "G08 - Gramatica.y"
                {yyval.tree = new SyntacticTree(val_peek(2).tree, val_peek(0).tree, val_peek(1).sval);}
                break;
                case 84:
//#line 218 "G08 - Gramatica.y"
                {addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Se espera un factor * ");}
                break;
                case 85:
//#line 219 "G08 - Gramatica.y"
                {addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Se espera un factor");}
                break;
                case 86:
//#line 222 "G08 - Gramatica.y"
                {yyval.tree = new SyntacticTree(null, null, val_peek(0).sval);}
                break;
                case 87:
//#line 223 "G08 - Gramatica.y"
                {yyval.tree = new SyntacticTree(null, null, val_peek(0).sval);}
                break;
                case 88:
//#line 224 "G08 - Gramatica.y"
                {
                    String lexeme ='-'+ val_peek(0).sval;
                    Attribute attribute = new Attribute("NRO_DOUBLE", "DOUBLE");
                    la.addSymbolTable(lexeme, attribute);
                    yyval.tree = new SyntacticTree(null, null, val_peek(0).sval);
                }
                break;
                case 91:
//#line 234 "G08 - Gramatica.y"
                {yyval.tree  = new SyntacticTree(null, null, val_peek(0).sval);}
                break;
//#line 870 "Parser.java"
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
