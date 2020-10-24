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
            0,    1,    1,    1,    2,    2,    3,    3,    4,    4,
            4,    4,    4,    4,    6,    6,    5,    5,    7,    7,
            16,   16,   16,   16,   15,   14,   14,   14,   17,   18,
            18,   18,   13,   13,    8,   20,   20,   19,   19,   19,
            19,   19,   19,   21,   21,   22,   24,   24,   10,    9,
            27,   29,   26,   28,   28,   28,   28,   28,   28,   30,
            30,   11,   12,   23,   23,   23,   31,   31,   31,   32,
            32,   32,   32,   25,   25,
    };
    final static short yylen[] = {                            2,
            1,    1,    2,    2,    2,    1,    2,    1,    2,    2,
            2,    2,    2,    2,    1,    3,    1,    1,    9,    8,
            1,    2,    1,    2,    3,    1,    3,    5,    2,    1,
            3,    5,    4,    3,    4,    2,    1,    5,    5,    5,
            5,    5,    5,    3,    1,    2,    1,    2,    3,    5,
            3,    3,    3,    3,    3,    3,    3,    3,    3,    2,
            2,    4,    4,    3,    3,    1,    3,    3,    1,    1,
            1,    2,    1,    3,    1,
    };
    final static short yydefred[] = {                         0,
            0,    0,   17,    0,    0,    0,    0,   18,    0,    0,
            2,    0,    6,    0,    8,    0,    0,    0,    0,    0,
            0,    0,    4,    0,    0,    0,    0,    0,    0,    0,
            3,    5,   15,    0,    0,    9,   10,   11,   12,   13,
            14,    0,   74,    0,   34,    0,    0,   71,   70,    0,
            0,   73,    0,   69,    0,   45,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,   33,   72,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,   47,
            0,   35,    0,   36,    0,    0,   62,    0,    0,    0,
            0,   63,   16,    0,    0,    0,    0,    0,    0,    0,
            0,    0,   67,   68,   44,   48,   46,   53,    0,   50,
            0,    0,    0,   29,    0,    0,    0,   42,   41,   40,
            43,   38,   39,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,   32,    0,    0,    0,    0,    0,
            0,    0,    0,   51,    0,   25,   21,    0,    0,    0,
            0,   60,   61,    0,   20,   22,    0,    0,   28,   52,
            19,
    };
    final static short yydgoto[] = {                          9,
            10,  147,   12,   13,   14,   35,   15,   16,   17,   18,
            19,   20,   21,   90,  113,  149,   91,   46,   27,   58,
            59,   84,   51,   81,   52,   61,  110,  111,  144,  145,
            53,   54,
    };
    final static short yysindex[] = {                      -172,
            -41,  -38,    0,   -2,    4,   32, -190,    0,    0, -161,
            0,   18,    0,    1,    0,   20,   21,   24,   30,   35,
            40,   39,    0, -156,  -30,  -45, -107, -152, -160,   86,
            0,    0,    0,  -45,   75,    0,    0,    0,    0,    0,
            0,  -45,    0,   84,    0,  103, -127,    0,    0, -131,
            -15,    0,    8,    0, -146,    0,  107, -114, -112,   92,
            95,  114,   -6,   28,  -99,   25,  -98,    0,    0,  -45,
            -45,  -45,  -45,  -45,  -45,  -45,  -45,  -45,  -45,    0,
            -49,    0, -107,    0, -117,  -95,    0, -113,  -93,  125,
            123,    0,    0,  124,   79,   82,   88,   91,    8,    8,
            94,   97,    0,    0,    0,    0,    0,    0,  -56,    0,
            113,  112,   51,    0, -113, -209,  -73,    0,    0,    0,
            0,    0,    0,  -45,  -45,  -45,  -45,  -45,  -45, -222,
            -90, -161,   63,  145,    0,   25,   25,   25,   25,   25,
            25,  -86,  -85,    0,  149,    0,    0,    0,  -88, -161,
            -209,    0,    0, -107,    0,    0,    0,  -62,    0,    0,
            0,
    };
    final static short yyrindex[] = {                         0,
            0,  137,    0,    0,    0,    0,    0,    0,    0,  199,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,  141,    0,    0,    0,    0,    0,
            0,    0,    0,  163,    0,    0,  -33,    0,    0,    0,
            0,    0,  -40,    0,    0,    0,    0,    0,  -55,    0,
            0,    0,    0,    0,    0,  147,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            170,    0,    0,  172,    0,    0,    0,    0,  -28,  -20,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,  175,    0,  158,  160,  161,  162,  180,
            185,    0,    0,    0,    0,    0,    0,    5,    0,    0,
            0,    0,    0,    0,    0,    0,   16,    0,    0,    0,
            0,
    };
    final static short yygindex[] = {                         0,
            0,   36,    0,   26,   27,    0,  -84,    0,    0,    0,
            0,    0,    0,    0,  130,   72,  -92,    0,    0,    0,
            -76,    0,  223,    0,   33,    0,    0,    0,    0,    0,
            -18,  -17,
    };
    final static int YYTABLESIZE=352;
    static short yytable[];
    static { yytable();}
    static void yytable(){
        yytable = new short[]{                         50,
                66,   25,   66,  124,   66,  125,  107,   75,   75,   75,
                45,   75,   64,   75,   64,   55,   64,   23,   66,   66,
                65,   66,   65,  134,   65,   75,   75,   74,   75,   75,
                64,   64,   22,   64,   88,   11,  155,   26,   65,   65,
                34,   65,   22,   28,   76,   31,   77,  148,    3,   78,
                142,  143,   56,   57,   79,   99,  100,    8,  159,   22,
                103,  104,  161,    8,  157,  148,   30,   74,   92,   75,
                74,   29,   75,  157,    8,  105,   32,  160,   36,   37,
                80,   57,   38,    1,    2,    3,    4,   22,   39,   89,
                5,    6,    7,   40,    8,    2,    3,    4,   41,   42,
                43,    5,    6,    7,   60,    8,  106,   57,   56,   57,
                2,    3,    4,   22,   62,   22,    5,    6,   65,  118,
                8,   74,  119,   75,   74,   63,   75,   67,  120,   23,
                74,  121,   75,   74,  122,   75,   74,  123,   75,   74,
                24,   75,   89,   68,   24,   69,   34,   82,   83,    2,
                3,    4,   85,   86,   87,    5,    6,   93,   94,    8,
                108,  109,  112,  114,   22,  115,  116,  117,    2,    3,
                4,  130,  131,  132,    5,    6,    7,   89,    8,   56,
                57,   22,   22,  135,  156,  150,   22,  146,  151,  154,
                22,  152,  153,  156,    2,    3,    4,   75,    1,    7,
                5,    6,    7,   30,    8,   49,   37,    2,    3,    4,
                26,   47,   31,    5,    6,   27,   54,    8,   55,   56,
                57,  158,  126,  127,  128,  129,   44,   66,   66,   66,
                66,   48,   49,   24,   75,   75,   75,   75,   58,   64,
                64,   64,   64,   59,  133,    0,    0,   65,   65,   65,
                65,    3,   70,   71,   72,   73,   64,   33,    0,    0,
                8,   23,   23,   23,   66,    0,    0,   23,   23,   23,
                0,   23,   24,   24,   24,    0,    0,    0,   24,   24,
                24,    0,   24,    0,    0,    0,    0,    0,    0,    0,
                0,    0,   95,   96,   97,   98,    0,    0,  101,  102,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                0,    0,    0,    0,    0,    0,  136,  137,  138,  139,
                140,  141,
        };
    }
    static short yycheck[];
    static { yycheck(); }
    static void yycheck() {
        yycheck = new short[] {                         45,
                41,   40,   43,   60,   45,   62,   83,   41,   42,   43,
                41,   45,   41,   47,   43,  123,   45,   59,   59,   60,
                41,   62,   43,  116,   45,   59,   60,   43,   62,   45,
                59,   60,    0,   62,   41,    0,  125,   40,   59,   60,
                40,   62,   10,   40,   60,   10,   62,  132,  258,   42,
                273,  274,   27,   27,   47,   74,   75,  267,  151,   27,
                78,   79,  125,   59,  149,  150,  257,   43,   41,   45,
                43,   40,   45,  158,   59,  125,   59,  154,   59,   59,
                55,   55,   59,  256,  257,  258,  259,   55,   59,   63,
                263,  264,  265,   59,  267,  257,  258,  259,   59,   61,
                257,  263,  264,  265,  257,  267,   81,   81,   83,   83,
                257,  258,  259,   81,  275,   83,  263,  264,   44,   41,
                267,   43,   41,   45,   43,   40,   45,   44,   41,  125,
                43,   41,   45,   43,   41,   45,   43,   41,   45,   43,
                125,   45,  116,   41,  272,  277,   40,  262,  261,  257,
                258,  259,   61,   59,   41,  263,  264,  257,  257,  267,
                278,  257,  276,  257,  132,   41,   44,   44,  257,  258,
                259,   59,   61,  123,  263,  264,  265,  151,  267,  154,
                154,  149,  150,  257,  149,  123,  154,  278,   44,   41,
                158,  278,  278,  158,  257,  258,  259,   61,    0,   59,
                263,  264,  265,   41,  267,   59,  262,  257,  258,  259,
                41,  257,   41,  263,  264,   41,   59,  267,   59,   59,
                59,  150,  279,  280,  281,  282,  257,  268,  269,  270,
                271,  277,  278,  272,  268,  269,  270,  271,   59,  268,
                269,  270,  271,   59,  115,   -1,   -1,  268,  269,  270,
                271,  258,  268,  269,  270,  271,   34,  257,   -1,   -1,
                267,  257,  258,  259,   42,   -1,   -1,  263,  264,  265,
                -1,  267,  257,  258,  259,   -1,   -1,   -1,  263,  264,
                265,   -1,  267,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   70,   71,   72,   73,   -1,   -1,   76,   77,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,  124,  125,  126,  127,
                128,  129,
        };
    }
    final static short YYFINAL=9;
    final static short YYMAXTOKEN=282;
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
            "\"==\"","\">=\"","\"<=\"","\"!=\"",
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
            "sent_ejecutable : sentencia_if ';'",
            "sent_ejecutable : sentencia_control ';'",
            "sent_ejecutable : asignacion ';'",
            "sent_ejecutable : imprimir ';'",
            "sent_ejecutable : conversion_explicita ';'",
            "sent_ejecutable : llamado_PROC ';'",
            "lista_variables : ID",
            "lista_variables : lista_variables ',' ID",
            "tipo : ULONGINT",
            "tipo : DOUBLE",
            "procedimiento : PROC ID '(' lista_parametros ')' asignacion_NA '{' cuerpo_procedimiento '}'",
            "procedimiento : PROC ID '(' ')' asignacion_NA '{' cuerpo_procedimiento '}'",
            "cuerpo_procedimiento : sentencia",
            "cuerpo_procedimiento : cuerpo_procedimiento sentencia",
            "cuerpo_procedimiento : procedimiento",
            "cuerpo_procedimiento : cuerpo_procedimiento procedimiento",
            "asignacion_NA : NA '=' NRO_ULONGINT",
            "lista_parametros : parametro",
            "lista_parametros : parametro ',' parametro",
            "lista_parametros : parametro ',' parametro ',' parametro",
            "parametro : tipo ID",
            "parametro_invocacion : ID",
            "parametro_invocacion : ID ',' ID",
            "parametro_invocacion : ID ',' ID ',' ID",
            "llamado_PROC : ID '(' parametro_invocacion ')'",
            "llamado_PROC : ID '(' ')'",
            "sentencia_if : IF condicion_IF cuerpo END_IF",
            "cuerpo : bloque bloque_else",
            "cuerpo : bloque",
            "condicion_IF : '(' expresion '<' expresion ')'",
            "condicion_IF : '(' expresion '>' expresion ')'",
            "condicion_IF : '(' expresion IGUAL expresion ')'",
            "condicion_IF : '(' expresion MAYOR_IGUAL expresion ')'",
            "condicion_IF : '(' expresion MENOR_IGUAL expresion ')'",
            "condicion_IF : '(' expresion DISTINTO expresion ')'",
            "bloque : '{' cuerpo_ejecutable '}'",
            "bloque : sent_ejecutable",
            "bloque_else : ELSE bloque",
            "cuerpo_ejecutable : sent_ejecutable",
            "cuerpo_ejecutable : cuerpo_ejecutable sent_ejecutable",
            "asignacion : tipo_ID '=' expresion",
            "sentencia_control : FOR '(' asignacion_FOR ';' condicion_FOR_dos",
            "condicion_FOR_dos : comparacion_FOR ';' condicion_FOR_tres",
            "condicion_FOR_tres : incr_decr ')' bloque",
            "asignacion_FOR : ID '=' NRO_ULONGINT",
            "comparacion_FOR : ID '<' expresion",
            "comparacion_FOR : ID '>' expresion",
            "comparacion_FOR : ID \"==\" expresion",
            "comparacion_FOR : ID \">=\" expresion",
            "comparacion_FOR : ID \"<=\" expresion",
            "comparacion_FOR : ID \"!=\" expresion",
            "incr_decr : UP NRO_ULONGINT",
            "incr_decr : DOWN NRO_ULONGINT",
            "imprimir : OUT '(' CADENA ')'",
            "conversion_explicita : tipo '(' expresion ')'",
            "expresion : expresion '+' termino",
            "expresion : expresion '-' termino",
            "expresion : termino",
            "termino : termino '*' factor",
            "termino : termino '/' factor",
            "termino : factor",
            "factor : NRO_ULONGINT",
            "factor : NRO_DOUBLE",
            "factor : '-' NRO_DOUBLE",
            "factor : tipo_ID",
            "tipo_ID : ID PUNTO_PUNTO ID",
            "tipo_ID : ID",
    };

//#line 458 "G08 - Gramatica - 21102020.y"


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
    //#line 450 "Parser.java"
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
//#line 69 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = val_peek(1).tree;
                }
                break;
                case 10:
//#line 73 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = val_peek(1).tree;
                }
                break;
                case 11:
//#line 77 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = new SyntacticTree(val_peek(1).tree, "ASIGNACION");
                }
                break;
                case 12:
//#line 81 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = val_peek(1).tree;
                }
                break;
                case 13:
//#line 85 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = val_peek(1).tree;
                }
                break;
                case 14:
//#line 89 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = val_peek(1).tree;
                }
                break;
                case 15:
//#line 97 "G08 - Gramatica - 21102020.y"
                {
                    yyval.attributes = new ArrayList<>();
                    yyval.attributes.add(val_peek(0).attribute);

                }
                break;
                case 16:
//#line 103 "G08 - Gramatica - 21102020.y"
                {
                    yyval = val_peek(2);
                    yyval.attributes.add(val_peek(2).attribute);
                }
                break;
                case 17:
//#line 110 "G08 - Gramatica - 21102020.y"
                {
                    yyval.type = Type.ULONGINT;
                }
                break;
                case 18:
//#line 114 "G08 - Gramatica - 21102020.y"
                {
                    yyval.type = Type.DOUBLE;
                }
                break;
                case 19:
//#line 123 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": PROCEDIMIENTO con PARÁMETROS.");
                    yyval.tree = new SyntacticTree(val_peek(3).tree, val_peek(1).tree,"PROCEDIMIENTO CON PARAMETROS");
                    yyval.attribute = val_peek(7).attribute;
                    yyval.attribute.setUse(Use.nombre_procedimiento);
                    System.out.println("Uso: " + yyval.attribute.getUse());
                }
                break;
                case 20:
//#line 131 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": PROCEDIMIENTO sin PARÁMETROS.");
                    yyval.tree = new SyntacticTree(val_peek(3).tree, val_peek(1).tree,"PROCEDIMIENTO SIN PARAMETROS");
                    yyval.attribute = val_peek(6).attribute;
                    yyval.attribute.setUse(Use.nombre_procedimiento);
                    System.out.println("Uso: " + yyval.attribute.getUse());
                }
                break;
                case 21:
//#line 142 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = new SyntacticTree(val_peek(0).tree, "CUERPO PROCEDIMIENTO SENTENCIA");
                }
                break;
                case 22:
//#line 146 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = new SyntacticTree(val_peek(1).tree, val_peek(0).tree, "CUERPO PROCEDIMIENTO SENTENCIA COMPUESTA");
                }
                break;
                case 25:
//#line 154 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = new SyntacticTree(new SyntacticTree(null, null, val_peek(0).attribute.getLexeme()), "NA");
                    val_peek(0).attribute.setUse(Use.numero_anidamiento);
                }
                break;
                case 29:
//#line 167 "G08 - Gramatica - 21102020.y"
                {
                    yyval = val_peek(0);
                    yyval.attribute.setUse(Use.nombre_parametro);
                    yyval.attribute.setType(new Type(val_peek(1).type.getName()));
                }
                break;
                case 30:
//#line 175 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = new SyntacticTree(new SyntacticTree(null, null, val_peek(0).attribute.getLexeme()), "PARAMETROS INVOCACION");
                }
                break;
                case 31:
//#line 179 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = new SyntacticTree(new SyntacticTree(null, null, val_peek(2).attribute.getLexeme()), new SyntacticTree(null, null, val_peek(2).attribute.getLexeme()), "PARAMETROS INVOCACION");
                }
                break;
                case 33:
//#line 187 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": LLAMADO A PROCEDIMIENTO CON PARÁMETROS.");
                    yyval.tree = new SyntacticTree(new SyntacticTree(null, null, val_peek(3).attribute.getLexeme()), val_peek(1).tree,"LLAMADO PROC SIN PAR");
                }
                break;
                case 34:
//#line 192 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": LLAMADO A PROCEDIMIENTO SIN PARÁMETROS.");
                    yyval.tree = new SyntacticTree(new SyntacticTree(null, null, val_peek(2).attribute.getLexeme()), "LLAMADO PROC SIN PAR");
                }
                break;
                case 35:
//#line 201 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia IF ELSE.");
                    yyval.tree = new SyntacticTree(val_peek(2).tree, val_peek(1).tree, "IF");
                }
                break;
                case 36:
//#line 208 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": CUERPO IF ELSE.");
                    yyval.tree = new SyntacticTree(val_peek(1).tree, val_peek(0).tree, "CUERPO_IF_ELSE");
                }
                break;
                case 37:
//#line 213 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": CUERPO IF.");
                    yyval.tree = new SyntacticTree(val_peek(0).tree, "CUERPO_IF");
                }
                break;
                case 38:
//#line 221 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": CONDICION IF MENOR.");
                    yyval.tree = new SyntacticTree(new SyntacticTree(val_peek(3).tree, val_peek(1).tree, "<"), "COND");
                }
                break;
                case 39:
//#line 226 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": CONDICION IF MAYOR.");
                    yyval.tree = new SyntacticTree(new SyntacticTree(val_peek(3).tree, val_peek(1).tree, ">"), "COND");
                }
                break;
                case 40:
//#line 231 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": CONDICION IF IGUAL.");
                    yyval.tree = new SyntacticTree(new SyntacticTree(val_peek(3).tree, val_peek(1).tree, "=="), "COND");
                }
                break;
                case 41:
//#line 236 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": CONDICION IF MAYOR_IGUAL.");
                    yyval.tree = new SyntacticTree(new SyntacticTree(val_peek(3).tree, val_peek(1).tree, ">="), "COND");
                }
                break;
                case 42:
//#line 241 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": CONDICION IF MENOR_IGUAL.");
                    yyval.tree = new SyntacticTree(new SyntacticTree(val_peek(3).tree, val_peek(1).tree, "<="), "COND");
                }
                break;
                case 43:
//#line 246 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": CONDICION IF DISTINTO.");
                    yyval.tree = new SyntacticTree(new SyntacticTree(val_peek(3).tree, val_peek(1).tree, "!="), "COND");
                }
                break;
                case 44:
//#line 254 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": BLOQUE DE SENTENCIAS.");
                    yyval.tree = new SyntacticTree(val_peek(1).tree, "BLOQUE_IF");
                }
                break;
                case 45:
//#line 259 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 46:
//#line 266 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": BLOQUE DE SENTENCIAS ELSE.");
                    yyval.tree = new SyntacticTree(val_peek(0).tree, "BLOQUE_ELSE");
                }
                break;
                case 47:
//#line 274 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 48:
//#line 278 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = new SyntacticTree(val_peek(1).tree, val_peek(0).tree, "SENTENCIA");
                }
                break;
                case 49:
//#line 284 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": ASIGNACION.");
                    yyval.tree = new SyntacticTree(val_peek(2).tree, val_peek(0).tree, "=");
                }
                break;
                case 50:
//#line 293 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": Sentencia FOR.");
                    yyval.tree = new SyntacticTree(val_peek(2).tree, val_peek(0).tree, "FOR_ASIGNACION");

                }
                break;
                case 51:
//#line 301 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = new SyntacticTree(new SyntacticTree(val_peek(2).tree, "COMPARACION"), new SyntacticTree(val_peek(0).tree, "CUERPO"), "FOR");
                }
                break;
                case 52:
//#line 306 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = new SyntacticTree(val_peek(2).tree, val_peek(0).tree, "SENTENCIAS_FOR");
                }
                break;
                case 53:
//#line 312 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": ASIGNACION_FOR");

                    yyval.tree = new SyntacticTree(new SyntacticTree(null, null, val_peek(2).attribute.getLexeme()), new SyntacticTree(null, null, val_peek(0).attribute.getLexeme()), "=");
                }
                break;
                case 54:
//#line 320 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": CONDICION FOR MENOR.");
                    yyval.tree = new SyntacticTree(new SyntacticTree(null, null, val_peek(2).attribute.getLexeme()), val_peek(0).tree, "<");
                }
                break;
                case 55:
//#line 325 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": CONDICION FOR MAYOR.");
                    yyval.tree = new SyntacticTree(new SyntacticTree(null, null, val_peek(2).attribute.getLexeme()), val_peek(0).tree, ">");
                }
                break;
                case 56:
//#line 330 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": CONDICION FOR IGUAL.");
                    yyval.tree = new SyntacticTree(new SyntacticTree(null, null, val_peek(2).attribute.getLexeme()), val_peek(0).tree, "==");
                }
                break;
                case 57:
//#line 335 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": CONDICION FOR MAYOR_IGUAL.");
                    yyval.tree = new SyntacticTree(new SyntacticTree(null, null, val_peek(2).attribute.getLexeme()), val_peek(0).tree, ">=");
                }
                break;
                case 58:
//#line 340 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": CONDICION FOR MENOR_IGUAL.");
                    yyval.tree = new SyntacticTree(new SyntacticTree(null, null, val_peek(2).attribute.getLexeme()), val_peek(0).tree, "<=");
                }
                break;
                case 59:
//#line 345 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": CONDICION FOR DISTINTO.");
                    yyval.tree = new SyntacticTree(new SyntacticTree(null, null, val_peek(2).attribute.getLexeme()), val_peek(0).tree, "!=");
                }
                break;
                case 60:
//#line 352 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree  = new SyntacticTree(new SyntacticTree(null, null, val_peek(0).attribute.getLexeme()), "UP");
                }
                break;
                case 61:
//#line 356 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree  = new SyntacticTree(new SyntacticTree(null, null, val_peek(0).attribute.getLexeme()), "DOWN");
                }
                break;
                case 62:
//#line 364 "G08 - Gramatica - 21102020.y"
                {
                    System.out.println(val_peek(1).attribute.getLexeme());
                    addRule("Linea "+ la.getNroLinea() +": Sentencia OUT.");
                    yyval.tree  = new SyntacticTree(new SyntacticTree(null, null, val_peek(1).attribute.getLexeme()), "IMPRIMIR");
                }
                break;
                case 63:
//#line 374 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": CONVERSION EXPLICITA.");
                    yyval.tree  = new SyntacticTree(val_peek(3).tree, val_peek(1).tree, "CONVERSION");
                }
                break;
                case 64:
//#line 382 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": SUMA");
                    yyval.tree = new SyntacticTree(val_peek(2).tree, val_peek(0).tree, "+");
                }
                break;
                case 65:
//#line 387 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": RESTA.");
                    yyval.tree = new SyntacticTree(val_peek(2).tree, val_peek(0).tree, "-");
                }
                break;
                case 66:
//#line 391 "G08 - Gramatica - 21102020.y"
                {yyval.tree = val_peek(0).tree;}
                break;
                case 67:
//#line 395 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = new SyntacticTree(val_peek(2).tree, val_peek(0).tree, "*");
                }
                break;
                case 68:
//#line 399 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = new SyntacticTree(val_peek(2).tree, val_peek(0).tree, "/");
                }
                break;
                case 69:
//#line 403 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 70:
//#line 409 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": NRO_ULONGINT.");
                    yyval.tree = new SyntacticTree(null, null, val_peek(0).attribute.getLexeme());
                }
                break;
                case 71:
//#line 414 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": NRO_DOUBLE.");
                    yyval.tree = new SyntacticTree(null, null, val_peek(0).attribute.getLexeme());
                }
                break;
                case 72:
//#line 418 "G08 - Gramatica - 21102020.y"
                {
                    String lexeme ='-'+ val_peek(0).attribute.getLexeme();
                    System.out.println("Lexeme: " + lexeme);
                    boolean check = la.checkNegativeDouble(lexeme);
                    System.out.println("Check: " + check);
                    if(check){
                        addError("Error Sintáctico en línea "+ la.getNroLinea() +": DOUBLE fuera de rango.");
                    }else{
                        addRule("Linea "+ la.getNroLinea() +": NRO_DOUBLE negativo.");
                        Attribute attribute = new Attribute(lexeme,"NRO_DOUBLE", Type.DOUBLE);
                        la.addSymbolTable(lexeme, attribute);
                        System.out.println("Lexeme: " + la.getAttribute(val_peek(0).attribute.getLexeme()).getLexeme());
                        System.out.println("Amount antes decremento: " + la.getAttribute(val_peek(0).attribute.getLexeme()).getAmount());
                        val_peek(0).attribute.decreaseAmount();
                        System.out.println("Amount desp decremento: " + la.getAttribute(val_peek(0).attribute.getLexeme()).getAmount());
                        int amount = la.getAttribute(val_peek(0).attribute.getLexeme()).getAmount();
                        System.out.println("Amount: " + la.getAttribute(val_peek(0).attribute.getLexeme()).getAmount());
                        if(amount == 0){
                            la.deleteSymbolTableEntry(val_peek(0).attribute.getLexeme());
                        }
                    }
                }
                break;
                case 73:
//#line 441 "G08 - Gramatica - 21102020.y"
                {
                    yyval.tree = val_peek(0).tree;
                }
                break;
                case 74:
//#line 447 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": ID PUNTO_PUNTO ID.");
                    yyval.tree  = new SyntacticTree(val_peek(2).tree, val_peek(0).tree, "::");
                }
                break;
                case 75:
//#line 452 "G08 - Gramatica - 21102020.y"
                {
                    addRule("Linea "+ la.getNroLinea() +": ID.");
                    yyval.tree  = new SyntacticTree(null, null, val_peek(0).attribute.getLexeme());
                }
                break;
//#line 1076 "Parser.java"
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
