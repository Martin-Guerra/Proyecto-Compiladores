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
    public final static short YYERRCODE=256;
    final static short yylhs[] = {                           -1,
            0,    1,    1,    1,    2,    2,    3,    3,    3,    3,
            4,    4,    4,    4,    4,    4,    6,    6,    5,    5,
            5,    7,    7,    7,    7,    7,    7,    7,   15,   15,
            15,   15,   13,   13,   13,   16,   11,   11,   11,    8,
            8,    8,    8,    8,    8,    8,    8,    8,   17,   17,
            17,   17,   18,   18,   18,   18,   21,   21,    9,    9,
            9,    9,    9,    9,   23,   23,   23,   23,   24,   24,
            10,   10,   10,   10,   10,   10,   12,   12,   12,   14,
            22,   22,   22,   19,   19,   19,   19,   19,   25,   25,
            25,   25,   25,   26,   26,   26,   20,   20,   20,   20,
            20,   20,
    };
    final static short yylen[] = {                            2,
            1,    1,    2,    2,    2,    1,    2,    1,    2,    2,
            2,    2,    2,    5,    4,    2,    1,    3,    1,    1,
            2,    9,    8,    7,    6,    5,    4,    3,    1,    2,
            1,    2,    1,    3,    5,    2,    1,    3,    5,    8,
            6,    6,    8,    5,    7,    4,    2,    3,    3,    3,
            2,    2,    3,    1,    3,    2,    1,    2,   11,   11,
            10,    9,    8,    2,    3,    3,    2,    2,    2,    2,
            4,    4,    3,    3,    4,    2,    4,    4,    3,    3,
            3,    3,    2,    3,    3,    1,    3,    3,    3,    3,
            3,    3,    1,    1,    3,    1,    1,    1,    1,    1,
            1,    1,
    };
    final static short yydefred[] = {                         0,
            0,    0,   19,    0,    0,    0,    0,   20,    0,    0,
            0,    2,    0,    6,    0,    8,    0,    0,    0,    0,
            17,    4,    0,    0,   47,    0,   64,    0,   76,    0,
            0,    0,   21,    0,    3,    5,   10,    0,    0,   11,
            12,   13,   16,    0,    0,    0,    0,   48,    0,   94,
            101,  100,   99,  102,   97,   98,    0,    0,    0,    0,
            93,    0,    0,    0,   73,   74,    0,    0,   28,    0,
            79,    0,   18,    0,   15,    0,    0,   46,    0,   52,
            0,    0,    0,   51,    0,    0,    0,    0,    0,   72,
            71,   75,   27,    0,    0,    0,    0,   78,   77,    0,
            14,   95,   44,    0,   54,    0,    0,   87,    0,   88,
            0,   50,    0,   91,   89,   92,   90,   82,   81,    0,
            0,    0,    0,    0,    0,   36,   26,    0,    0,    0,
            56,   57,    0,   42,    0,   41,    0,   68,   67,    0,
            0,    0,   25,    0,    0,   39,   55,   53,   58,   45,
            0,   66,    0,    0,    0,    0,   80,   29,    0,    0,
            24,    0,    0,   43,   40,   69,   70,   63,    0,   23,
            30,    0,    0,   35,   62,    0,   22,   61,    0,   60,
            59,
    };
    final static short yydgoto[] = {                         10,
            11,  158,   13,   14,   15,   23,   16,   17,   18,   19,
            47,   20,   96,  125,  160,   97,   57,  107,   58,   59,
            133,   64,  123,  156,   60,   61,
    };
    final static short yysindex[] = {                        55,
            -53,    7,    0,  -32,  -27,  -40, -207,    0, -192,    0,
            67,    0,   27,    0,  -29,    0,   40,   44,   51,   70,
            0,    0,   64,  -23,    0,  146,    0,   -9,    0,   54,
            -39,  -14,    0, -122,    0,    0,    0, -142,   64,    0,
            0,    0,    0, -117,   99,  100,  116,    0, -110,    0,
            0,    0,    0,    0,    0,    0,    5,   -2,  -95,   15,
            0,  104, -174,  108,    0,    0,   24,  -83,    0,   14,
            0,   -5,    0,  -85,    0,  115,  -79,    0,   79,    0,
            -139, -125, -119,    0, -111, -103, -233,   53,  187,    0,
            0,    0,    0,  -97,  -77,   29,  137,    0,    0,  138,
            0,    0,    0,  142,    0,  143, -188,    0,   15,    0,
            15,    0,   53,    0,    0,    0,    0,    0,    0,   98,
            18,  -72,  126,  125,   65,    0,    0, -184,  -12,  -67,
            0,    0,   97,    0,  118,    0,  -87,    0,    0, -153,
            -66,   67,    0, -106,  147,    0,    0,    0,    0,    0,
            -149,    0,   53,  -65,  -64,   36,    0,    0,    0,   31,
            0,   67,  -12,    0,    0,    0,    0,    0, -104,    0,
            0,    0,   43,    0,    0,  130,    0,    0, -105,    0,
            0,
    };
    final static short yyrindex[] = {                         0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            195,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,  139,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,  140,    0,
            0,    0,    0,    0,  155,    0,    0,    0,  -38,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,  -31,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,  141,    0,    0,
            0,    0,    0,    0,    0,    0,   41,    0,    0,  156,
            0,    0,    0,    0,    0,    0,    0,    0,  -11,    0,
            -6,    0,   48,    0,    0,    0,    0,    0,    0,   59,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,   49,    0,    0,    0,    0,    0,
            0,    0,  144,    0,    0,    0,    0,    0,  -44,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,   19,    0,    0,    0,    0,    0,    0,    0,    0,
            0,
    };
    final static short yygindex[] = {                         0,
            0,   16,    0,  -10,    1,  186,  -51,    0,    0,    0,
            0,    0,    0,   77,   47,  -84,    0,  -91,    4,    8,
            0,    0,    0,    0,   45,   63,
    };
    final static int YYTABLESIZE=458;
    static short yytable[];
    static { yytable();}
    static void yytable(){
        yytable = new short[]{                         31,
                31,   68,   96,   96,   96,   22,   96,   26,   96,   86,
                38,   86,   28,   86,    8,   12,  162,   46,  176,  181,
                96,   96,  118,   96,  119,   70,   35,   86,   86,   84,
                86,   84,    9,   84,   85,   99,   85,   81,   85,   82,
                81,   72,   82,  151,  145,   79,   24,   84,   84,   32,
                84,   63,   85,   85,   94,   85,   85,   55,    9,   56,
                81,   86,   82,   32,   91,   83,   88,  134,  105,  128,
                95,  143,  135,  136,   33,    9,  169,    8,  174,  106,
                31,   33,   49,   50,  179,   36,  113,    9,   49,   34,
                159,  124,  121,  132,   65,   81,  122,   82,   40,    9,
                96,   96,   41,   96,  106,   96,  164,   44,  172,   42,
                159,    9,  165,   71,   49,   50,  108,   49,   50,  154,
                155,  172,  149,    9,  105,  109,  111,  137,   43,   95,
                110,   49,   50,  106,   21,  106,  112,   49,   50,   73,
                153,    9,   74,   32,  114,   49,   50,  115,  117,  161,
                180,  175,  116,   49,   50,  170,   76,   55,   75,   56,
                84,   77,    9,   95,   87,  105,   89,  177,  152,   49,
                50,  100,   92,  101,    9,  171,  106,  102,  124,  126,
                129,  130,   38,  139,  140,  141,    9,  142,  171,  146,
                163,  157,  166,  167,    1,   37,   38,    9,    7,   83,
                39,  104,   65,   21,  144,   55,    0,   56,  173,    0,
                0,   31,   31,   31,   31,   29,   66,   96,   31,   31,
                31,  148,   31,   25,   86,    0,   37,   21,   27,   96,
                96,   96,   96,   45,   30,   67,   86,   86,   86,   86,
                104,   69,    0,    0,   84,    3,   55,   62,   56,   85,
                98,    0,  104,   80,    8,    0,   84,   84,   84,   84,
                78,   85,   85,   85,   85,   51,   52,   53,   54,   93,
                0,    3,    0,  138,   32,   32,   32,   32,    0,   90,
                8,   32,   32,   32,  127,   32,   34,    2,    3,    4,
                0,  168,    0,    5,    6,    7,   33,    8,   34,    2,
                3,    4,    0,   49,   34,    5,    6,    7,    0,    8,
                1,    2,    3,    4,   96,    0,    0,    5,    6,    7,
                0,    8,   34,    2,    3,    4,    0,    0,    0,    5,
                6,    7,    0,    8,  103,    2,    3,    4,    0,    0,
                0,    5,    6,    0,    0,    8,    0,    0,    0,    0,
                0,    0,  147,    2,    3,    4,    0,    0,    0,    5,
                6,    0,    0,    8,    0,   51,   52,   53,   54,   77,
                0,    0,    0,  150,    2,    3,    4,    0,    0,    0,
                5,    6,    0,    0,    8,  178,    2,    3,    4,    0,
                0,    0,    5,    6,    0,    0,    8,  131,    2,    3,
                4,   48,   49,   50,    5,    6,    0,    0,    8,    0,
                0,    0,    0,   51,   52,   53,   54,    0,    0,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                0,    0,    0,  120,   50,    0,    0,    0,    0,    0,
                0,    0,    0,    0,   51,   52,   53,   54,
        };
    }
    static short yycheck[];
    static { yycheck(); }
    static void yycheck() {
        yycheck = new short[] {                         40,
                45,   41,   41,   42,   43,   59,   45,   40,   47,   41,
                40,   43,   40,   45,   59,    0,  123,   41,  123,  125,
                59,   60,  256,   62,  258,   40,   11,   59,   60,   41,
                62,   43,   45,   45,   41,   41,   43,   43,   45,   45,
                43,   38,   45,  135,  129,   41,   40,   59,   60,  257,
                62,   61,   59,   60,   41,   62,   42,   60,   45,   62,
                43,   47,   45,   45,   41,   58,   63,  256,   79,   41,
                70,  256,  261,  262,  267,   45,   41,   59,  163,   79,
                125,   41,  257,  258,  176,   59,   83,   45,   41,   41,
                142,  276,   89,  104,   41,   43,   89,   45,   59,   45,
                42,   43,   59,   45,  104,   47,  256,   44,  160,   59,
                162,   45,  262,  256,  257,  258,  256,  257,  258,  273,
                274,  173,  133,   45,  135,   81,   82,  120,   59,  129,
                256,  257,  258,  133,  257,  135,  256,  257,  258,  257,
                137,   45,   44,  125,  256,  257,  258,   85,   86,  256,
                256,  256,  256,  257,  258,  125,   41,   60,   59,   62,
                256,  272,   45,  163,   61,  176,   59,  125,  256,  257,
                258,  257,  256,   59,   45,  160,  176,  257,  276,  257,
                44,   44,   40,  256,   59,   61,   45,  123,  173,  257,
                44,  258,  258,  258,    0,   41,   41,   59,   59,   59,
                15,  123,   59,  257,  128,   60,   -1,   62,  162,   -1,
                -1,  256,  257,  258,  259,  256,  256,  256,  263,  264,
                265,  125,  267,  256,  256,   -1,  256,  257,  256,  268,
                269,  270,  271,  257,  275,  275,  268,  269,  270,  271,
                123,  256,   -1,   -1,  256,  258,   60,  257,   62,  256,
                256,   -1,  123,  256,  267,   -1,  268,  269,  270,  271,
                256,  268,  269,  270,  271,  268,  269,  270,  271,  256,
                -1,  258,   -1,  256,  256,  257,  258,  259,   -1,  256,
                267,  263,  264,  265,  256,  267,  256,  257,  258,  259,
                -1,  256,   -1,  263,  264,  265,  256,  267,  256,  257,
                258,  259,   -1,  256,  256,  263,  264,  265,   -1,  267,
                256,  257,  258,  259,  256,   -1,   -1,  263,  264,  265,
                -1,  267,  256,  257,  258,  259,   -1,   -1,   -1,  263,
                264,  265,   -1,  267,  256,  257,  258,  259,   -1,   -1,
                -1,  263,  264,   -1,   -1,  267,   -1,   -1,   -1,   -1,
                -1,   -1,  256,  257,  258,  259,   -1,   -1,   -1,  263,
                264,   -1,   -1,  267,   -1,  268,  269,  270,  271,  272,
                -1,   -1,   -1,  256,  257,  258,  259,   -1,   -1,   -1,
                263,  264,   -1,   -1,  267,  256,  257,  258,  259,   -1,
                -1,   -1,  263,  264,   -1,   -1,  267,  256,  257,  258,
                259,  256,  257,  258,  263,  264,   -1,   -1,  267,   -1,
                -1,   -1,   -1,  268,  269,  270,  271,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,  257,  258,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,  268,  269,  270,  271,
        };
    }
    final static short YYFINAL=10;
    final static short YYMAXTOKEN=276;
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
            "DISTINTO","PUNTO_PUNTO","UP","DOWN","CADENA","NA",
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
            "sent_ejecutable : ID '(' parametro_invocacion ')' ';'",
            "sent_ejecutable : ID '(' ')' ';'",
            "sent_ejecutable : conversion_explicita ';'",
            "lista_variables : ID",
            "lista_variables : lista_variables ',' ID",
            "tipo : ULONGINT",
            "tipo : DOUBLE",
            "tipo : '-' DOUBLE",
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
            "sentencia_if : IF '(' condicion_IF error",
            "sentencia_if : IF error",
            "sentencia_if : IF '(' error",
            "condicion_IF : expresion comparador expresion",
            "condicion_IF : expresion comparador error",
            "condicion_IF : comparador error",
            "condicion_IF : expresion error",
            "bloque : '{' cuerpo_ejecutable '}'",
            "bloque : sent_ejecutable",
            "bloque : '{' cuerpo_ejecutable error",
            "bloque : '{' error",
            "cuerpo_ejecutable : sent_ejecutable",
            "cuerpo_ejecutable : cuerpo_ejecutable sent_ejecutable",
            "sentencia_control : FOR '(' asignacion_for ';' condicion_FOR ';' incr_decr ')' '{' bloque '}'",
            "sentencia_control : FOR '(' asignacion_for ';' condicion_FOR ';' incr_decr ')' '{' bloque error",
            "sentencia_control : FOR '(' asignacion_for ';' condicion_FOR ';' incr_decr ')' '{' error",
            "sentencia_control : FOR '(' asignacion_for ';' condicion_FOR ';' incr_decr ')' error",
            "sentencia_control : FOR '(' asignacion_for ';' condicion_FOR ';' incr_decr error",
            "sentencia_control : FOR error",
            "condicion_FOR : ID comparador expresion",
            "condicion_FOR : ID comparador error",
            "condicion_FOR : comparador error",
            "condicion_FOR : expresion error",
            "incr_decr : UP ULONGINT",
            "incr_decr : DOWN ULONGINT",
            "imprimir : OUT '(' CADENA ')'",
            "imprimir : OUT '(' CADENA error",
            "imprimir : OUT CADENA ')'",
            "imprimir : OUT '(' error",
            "imprimir : OUT '(' ')' error",
            "imprimir : OUT error",
            "conversion_explicita : tipo '(' expresion ')'",
            "conversion_explicita : tipo '(' expresion error",
            "conversion_explicita : tipo '(' error",
            "asignacion_NA : NA '=' ULONGINT",
            "asignacion_for : ID '=' ULONGINT",
            "asignacion_for : ID '=' error",
            "asignacion_for : '=' expresion",
            "expresion : expresion '+' termino",
            "expresion : expresion '-' termino",
            "expresion : termino",
            "expresion : expresion '+' error",
            "expresion : expresion '-' error",
            "termino : termino '*' factor",
            "termino : termino '/' factor",
            "termino : termino '*' error",
            "termino : termino '/' error",
            "termino : factor",
            "factor : ULONGINT",
            "factor : ID PUNTO_PUNTO ID",
            "factor : ID",
            "comparador : '<'",
            "comparador : '>'",
            "comparador : IGUAL",
            "comparador : MAYOR_IGUAL",
            "comparador : MENOR_IGUAL",
            "comparador : DISTINTO",
    };

//#line 228 "G08 - Gramatica.y"


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
    //#line 493 "Parser.java"
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
//#line 35 "G08 - Gramatica.y"
                {addRule("Linea "+ la.getNroLinea() +": Sentencia declarativa - Variable/s."); }
                break;
                case 8:
//#line 36 "G08 - Gramatica.y"
                {addRule("Linea "+ la.getNroLinea() +": Sentencia declarativa - Procedimiento."); }
                break;
                case 9:
//#line 38 "G08 - Gramatica.y"
                {addError("Linea "+ la.getNroLinea() +": falta definir el tipo de la/s variables.");}
                break;
                case 10:
//#line 39 "G08 - Gramatica.y"
                {addError("Linea "+ la.getNroLinea() +": falta definir la/s variable/s."); }
                break;
                case 21:
//#line 59 "G08 - Gramatica.y"
                {
                    String lexeme ='-'+ val_peek(0).sval;
                    this.la.addSymbolTable(lexeme, "DOUBLE");
                }
                break;
                case 22:
//#line 67 "G08 - Gramatica.y"
                { addRule("Linea "+ la.getNroLinea() +": PROCEDIMIENTO con PARÁMETROS.");}
                break;
                case 23:
//#line 68 "G08 - Gramatica.y"
                { addRule("Linea "+ la.getNroLinea() +": PROCEDIMIENTO sin PARÁMETROS.");}
                break;
                case 24:
//#line 70 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": faltan llaves");}
                break;
                case 25:
//#line 72 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": falta asignacion_NA");}
                break;
                case 26:
//#line 74 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": falta ')' ");}
                break;
                case 27:
//#line 76 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": falta ')' ");}
                break;
                case 28:
//#line 77 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": falta '(' ");}
                break;
                case 40:
//#line 106 "G08 - Gramatica.y"
                {addRule("Linea "+ la.getNroLinea() +": Sentencia IF ELSE.");}
                break;
                case 41:
//#line 107 "G08 - Gramatica.y"
                {addRule("Linea "+ la.getNroLinea() +": Sentencia IF");}
                break;
                case 42:
//#line 110 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": falta END_IF.");}
                break;
                case 43:
//#line 111 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": falta END_IF.");}
                break;
                case 44:
//#line 112 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": falta bloque.");}
                break;
                case 45:
//#line 113 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": falta bloque ELSE.");}
                break;
                case 46:
//#line 114 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": falta ')'.");}
                break;
                case 47:
//#line 115 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": error en condicion");}
                break;
                case 48:
//#line 116 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": falta ')'");}
                break;
                case 49:
//#line 119 "G08 - Gramatica.y"
                {addRule("Linea "+ la.getNroLinea() +": CONDICION IF.");}
                break;
                case 50:
//#line 122 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera una expresion luego del operador de comparacion."); }
                break;
                case 51:
//#line 124 "G08 - Gramatica.y"
                { addError("Error Sintáctico en línea "+ la.getNroLinea() +": Se espera la condición antes del operador de comparación."); }
                break;
                case 52:
//#line 125 "G08 - Gramatica.y"
                { addError("Error Sintáctico en línea "+ la.getNroLinea() +": Condicion definida incorrectamente."); }
                break;
                case 53:
//#line 127 "G08 - Gramatica.y"
                { addRule("Linea "+ la.getNroLinea() +": BLOQUE DE SENTENCIAS.");}
                break;
                case 55:
//#line 130 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": falta '}' del bloque  . ");}
                break;
                case 56:
//#line 131 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": error conj ejecutable . ");}
                break;
                case 59:
//#line 141 "G08 - Gramatica.y"
                {addRule("Linea "+ la.getNroLinea() +": Sentencia FOR.");}
                break;
                case 60:
//#line 143 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera '}'.");}
                break;
                case 61:
//#line 145 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": falta '}'");}
                break;
                case 62:
//#line 147 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": faltan llaves");}
                break;
                case 63:
//#line 149 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": falta ')' ");}
                break;
                case 64:
//#line 151 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": error en sentencia FOR ");}
                break;
                case 65:
//#line 154 "G08 - Gramatica.y"
                {addRule("Linea "+ la.getNroLinea() +": CONDICION FOR.");}
                break;
                case 66:
//#line 157 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera una expresion luego del operador de comparacion."); }
                break;
                case 67:
//#line 158 "G08 - Gramatica.y"
                { addError("Error Sintáctico en línea "+ la.getNroLinea() +": Se espera la condición antes del operador de comparación."); }
                break;
                case 68:
//#line 159 "G08 - Gramatica.y"
                { addError("Error Sintáctico en línea "+ la.getNroLinea() +": Condicion definida incorrectamente."); }
                break;
                case 71:
//#line 168 "G08 - Gramatica.y"
                {System.out.println(val_peek(1).sval);addRule("Linea "+ la.getNroLinea() +": Sentencia OUT.");}
                break;
                case 72:
//#line 170 "G08 - Gramatica.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera ')'."); }
                break;
                case 73:
//#line 171 "G08 - Gramatica.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera '('."); }
                break;
                case 74:
//#line 172 "G08 - Gramatica.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera una cadena de caracteres luego de '('."); }
                break;
                case 75:
//#line 173 "G08 - Gramatica.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": falta cadena"); }
                break;
                case 76:
//#line 174 "G08 - Gramatica.y"
                { addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera '('."); }
                break;
                case 77:
//#line 179 "G08 - Gramatica.y"
                {addRule("Linea "+ la.getNroLinea() +": CONVERSION EXPLICITA.");}
                break;
                case 78:
//#line 181 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera ')'."); }
                break;
                case 79:
//#line 182 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera expresion."); }
                break;
                case 81:
//#line 190 "G08 - Gramatica.y"
                {addRule("Linea "+ la.getNroLinea() +":ASIGNACION FOR.");}
                break;
                case 82:
//#line 192 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() + ": Expresion invalida en asignacion.");}
                break;
                case 83:
//#line 193 "G08 - Gramatica.y"
                {addError("Error Sintactico en linea "+ la.getNroLinea() + ": Falta lado izquierdo en la asignación");}
                break;
                case 87:
//#line 202 "G08 - Gramatica.y"
                { addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Se espera un termino luego del '+'."); }
                break;
                case 88:
//#line 203 "G08 - Gramatica.y"
                { addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Se espera un termino luego del '-'."); }
                break;
                case 91:
//#line 209 "G08 - Gramatica.y"
                {addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Se espera un factor * ");}
                break;
                case 92:
//#line 210 "G08 - Gramatica.y"
                {addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Se espera un factor");}
                break;
//#line 861 "Parser.java"
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
