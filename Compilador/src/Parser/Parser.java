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
    4,    4,    4,    4,    4,    4,    4,    6,    6,    5,
    5,    7,    7,    7,    7,    7,    7,    7,   16,   16,
   16,   16,   14,   14,   14,   17,   12,   12,   12,    8,
    8,    8,    8,    8,    8,    8,   18,   19,   19,   19,
   22,   22,    9,    9,    9,    9,    9,    9,   23,   23,
   23,   23,   24,   24,   10,   10,   10,   10,   10,   10,
   13,   13,   13,   15,   11,   11,   20,   20,   20,   20,
   20,   25,   25,   25,   25,   25,   26,   26,   26,   26,
   26,   21,   21,   21,   21,   21,   21,
};
final static short yylen[] = {                            2,
    1,    1,    2,    2,    2,    1,    2,    1,    2,    2,
    2,    2,    2,    2,    5,    4,    2,    1,    3,    1,
    1,    9,    8,    7,    6,    5,    4,    3,    1,    2,
    1,    2,    1,    3,    5,    2,    1,    3,    5,    8,
    6,    6,    8,    5,    7,    2,    3,    3,    1,    3,
    1,    2,   13,   13,   12,   11,   10,    2,    3,    3,
    2,    2,    2,    2,    4,    4,    3,    3,    4,    2,
    4,    4,    3,    3,    3,    3,    3,    3,    1,    3,
    3,    3,    3,    1,    3,    3,    1,    1,    2,    3,
    1,    1,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,   20,    0,    0,    0,    0,   21,    0,    0,
    2,    0,    6,    0,    8,    0,    0,    0,    0,    0,
   18,    4,    0,    0,    0,   46,    0,   58,    0,   70,
    0,    0,    0,    0,    3,    5,   10,    0,    0,   11,
   12,   13,   14,   17,    0,    0,    0,    0,   76,    0,
   88,   87,    0,    0,    0,   84,    0,    0,    0,   67,
   68,    0,    0,   28,    0,   73,    0,   19,    0,   16,
    0,    0,   89,    0,    0,    0,    0,    0,   96,   95,
   94,   97,   92,   93,    0,    0,   66,   65,   69,   27,
    0,    0,    0,    0,   72,   71,    0,   15,   90,   80,
    0,   81,    0,   85,   82,   86,   83,   44,    0,   49,
    0,    0,    0,    0,    0,    0,   36,   26,    0,    0,
    0,   51,    0,   42,    0,   41,    0,    0,    0,   25,
    0,    0,   39,   50,   48,   52,   45,    0,    0,    0,
    0,    0,   74,   29,    0,    0,   24,    0,    0,   43,
   40,    0,   62,   61,    0,   23,   30,    0,    0,   35,
   60,    0,    0,    0,    0,   22,   63,   64,   57,    0,
   56,    0,   55,    0,   54,   53,
};
final static short yydgoto[] = {                          9,
   10,  144,   12,   13,   14,   23,   15,   16,   17,   18,
   19,   48,   20,   93,  116,  146,   94,   57,  112,   54,
   85,  123,  142,  165,   55,   56,
};
final static short yysindex[] = {                      -140,
  -56,  -17,    0,    1,   20,  -40, -245,    0,    0,  -60,
    0,  -32,    0,   43,    0,   -1,    8,   26,   29,   36,
    0,    0,   18,  -10,  -38,    0,   -9,    0, -232,    0,
   62,  -39,   25, -215,    0,    0,    0,  -36,   18,    0,
    0,    0,    0,    0, -142,   85,   71,   92,    0, -137,
    0,    0, -141,   64,  -18,    0,  101,   27,   86,    0,
    0,   -2, -110,    0,   -3,    0,   37,    0, -108,    0,
   91, -105,    0,  -34,  -28,  -26,  -19, -119,    0,    0,
    0,    0,    0,    0,   -9, -120,    0,    0,    0,    0,
 -117,  -97,    7,  119,    0,    0,  126,    0,    0,    0,
  -18,    0,  -18,    0,    0,    0,    0,    0,  127,    0,
  131, -151,   64,  113,  115,   58,    0,    0, -236, -230,
  -79,    0,  107,    0, -102,    0,  -44,  -99,  -60,    0,
 -115,  136,    0,    0,    0,    0,    0, -172,   46,   56,
  -74,  124,    0,    0,    0,  -72,    0,  -60, -230,    0,
    0,  -11,    0,    0, -160,    0,    0,    0,   87,    0,
    0,   64,  -89,  -88,   15,    0,    0,    0,    0, -113,
    0,  -90,    0, -103,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  194,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  143,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  147,    0,
    0,    0,    0,    0,    0,  167,    0,    0,    0,    4,
    0,    0,    0,  150,    9,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   45,    0,    0,  169,    0,    0,    0,
   14,    0,   34,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  170,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   50,    0,    0,    0,    0,    0,    0,   55,    0,
    0,    0,    0,    0,   63,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   75,    0,    0,
    0,  155,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    5,    0,    3,  258,  201, -116,    0,    0,    0,
    0,    0,    0,    0,  137,  114, -114,    0, -111,  297,
  -35,    0,    0,    0,   57,   44,
};
final static int YYTABLESIZE=449;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         32,
   53,   63,   22,  109,   11,  132,   53,  148,   53,  172,
   53,   33,  145,  138,   35,   83,   53,   84,   53,  130,
  109,  176,   24,   76,   59,   53,   36,    3,   77,  158,
   47,  145,  109,   53,  160,   53,    8,   91,   88,  115,
   27,   21,  158,   25,   91,   91,   91,  119,   91,   79,
   91,   79,  156,   79,   77,  170,   77,   40,   77,   29,
  174,   45,   91,   91,   65,   91,   41,   79,   79,   74,
   79,   75,   77,   77,   78,   77,   78,   96,   78,   74,
  110,   75,   38,  150,   42,   33,   83,   43,   84,  151,
   34,  141,   78,   78,   44,   78,   91,   91,   74,   91,
   75,   91,   60,  152,  124,   83,   74,   84,   75,  125,
  126,  122,  163,  164,   68,    1,    2,    3,    4,  105,
  107,    8,    5,    6,    7,  136,    8,  110,   69,   70,
  101,  103,   71,    8,   72,   73,  108,    2,    3,    4,
  147,   78,  171,    5,    6,   89,   86,    8,   97,   98,
  157,   99,  175,  137,    2,    3,    4,  114,  115,  117,
    5,    6,  120,  157,    8,  173,    2,    3,    4,  121,
   38,  127,    5,    6,  110,  128,    8,  133,  143,  149,
  129,  154,  155,   34,    2,    3,    4,   31,  167,  168,
    5,    6,    7,    1,    8,   34,    2,    3,    4,   32,
   21,    9,    5,    6,    7,    7,    8,   37,   75,   38,
   47,  166,  139,   59,   39,   30,   61,   49,   50,   66,
   50,  100,   50,   79,   80,   81,   82,  102,   50,  104,
   50,  135,   51,   52,   31,   62,  106,   50,   51,   52,
   51,   52,   51,   52,  161,   50,   46,   50,   51,   52,
   51,   52,   90,   87,    3,  131,   26,   51,   52,   91,
    0,  159,  118,    8,   79,   51,   52,   51,   52,   77,
  169,   91,   91,   91,   91,   28,   79,   79,   79,   79,
   64,   77,   77,   77,   77,    0,    0,    0,    0,   78,
    0,    0,   95,    0,   79,   80,   81,   82,   37,   21,
   33,   78,   78,   78,   78,   34,    0,    0,    0,    0,
   91,  153,    0,   79,   80,   81,   82,   72,   31,   31,
   31,   31,   92,   58,    0,   31,   31,   31,    0,   31,
   32,   32,   32,   32,   67,  111,    0,   32,   32,   32,
    0,   32,   34,    2,    3,    4,    0,    0,    0,    5,
    6,    7,    0,    8,    0,    0,    0,    0,    0,    0,
    0,    0,  134,    2,    3,    4,  111,    0,    0,    5,
    6,    0,    0,    8,    0,    0,    0,   92,    0,    0,
  111,  113,  111,    2,    3,    4,    0,    0,    0,    5,
    6,    0,    0,    8,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   92,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  140,    0,    0,    0,    0,    0,  111,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  162,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   45,   41,   59,  123,    0,  120,   45,  123,   45,  123,
   45,  257,  129,  125,   10,   60,   45,   62,   45,  256,
  123,  125,   40,   42,  257,   45,   59,  258,   47,  146,
   41,  148,  123,   45,  149,   45,  267,   41,   41,  276,
   40,  257,  159,   61,   41,   42,   43,   41,   45,   41,
   47,   43,  125,   45,   41,   41,   43,   59,   45,   40,
  172,   44,   59,   60,   40,   62,   59,   59,   60,   43,
   62,   45,   59,   60,   41,   62,   43,   41,   45,   43,
   78,   45,   40,  256,   59,   41,   60,   59,   62,  262,
   41,  127,   59,   60,   59,   62,   42,   43,   43,   45,
   45,   47,   41,  139,  256,   60,   43,   62,   45,  261,
  262,  109,  273,  274,  257,  256,  257,  258,  259,   76,
   77,   59,  263,  264,  265,  123,  267,  125,   44,   59,
   74,   75,   41,   59,  272,  277,  256,  257,  258,  259,
  256,   41,  256,  263,  264,  256,   61,  267,  257,   59,
  146,  257,  256,  256,  257,  258,  259,  278,  276,  257,
  263,  264,   44,  159,  267,  256,  257,  258,  259,   44,
   40,   59,  263,  264,  172,   61,  267,  257,  278,   44,
  123,  256,   59,  256,  257,  258,  259,  125,  278,  278,
  263,  264,  265,    0,  267,  256,  257,  258,  259,  125,
  257,   59,  263,  264,  265,   59,  267,   41,   59,   41,
   41,  125,  257,   59,   14,  256,  256,  256,  257,  256,
  257,  256,  257,  268,  269,  270,  271,  256,  257,  256,
  257,  125,  277,  278,  275,  275,  256,  257,  277,  278,
  277,  278,  277,  278,  256,  257,  257,  257,  277,  278,
  277,  278,  256,  256,  258,  119,  256,  277,  278,  256,
   -1,  148,  256,  267,  256,  277,  278,  277,  278,  256,
  256,  268,  269,  270,  271,  256,  268,  269,  270,  271,
  256,  268,  269,  270,  271,   -1,   -1,   -1,   -1,  256,
   -1,   -1,  256,   -1,  268,  269,  270,  271,  256,  257,
  256,  268,  269,  270,  271,  256,   -1,   -1,   -1,   -1,
  256,  256,   -1,  268,  269,  270,  271,  272,  256,  257,
  258,  259,   65,   27,   -1,  263,  264,  265,   -1,  267,
  256,  257,  258,  259,   38,   78,   -1,  263,  264,  265,
   -1,  267,  256,  257,  258,  259,   -1,   -1,   -1,  263,
  264,  265,   -1,  267,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  256,  257,  258,  259,  109,   -1,   -1,  263,
  264,   -1,   -1,  267,   -1,   -1,   -1,  120,   -1,   -1,
  123,   85,  125,  257,  258,  259,   -1,   -1,   -1,  263,
  264,   -1,   -1,  267,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  149,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  127,   -1,   -1,   -1,   -1,   -1,  172,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  152,
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
"sent_ejecutable : ID '(' parametro_invocacion ')' ';'",
"sent_ejecutable : ID '(' ')' ';'",
"sent_ejecutable : conversion_explicita ';'",
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
"sentencia_if : IF error",
"condicion_IF : expresion comparador expresion",
"bloque : '{' cuerpo_ejecutable '}'",
"bloque : sent_ejecutable",
"bloque : '{' cuerpo_ejecutable error",
"cuerpo_ejecutable : sent_ejecutable",
"cuerpo_ejecutable : cuerpo_ejecutable sent_ejecutable",
"sentencia_control : FOR '(' ID '=' NRO_ULONGINT ';' condicion_FOR ';' incr_decr ')' '{' bloque '}'",
"sentencia_control : FOR '(' ID '=' NRO_ULONGINT ';' condicion_FOR ';' incr_decr ')' '{' bloque error",
"sentencia_control : FOR '(' ID '=' NRO_ULONGINT ';' condicion_FOR ';' incr_decr ')' '{' error",
"sentencia_control : FOR '(' ID '=' NRO_ULONGINT ';' condicion_FOR ';' incr_decr ')' error",
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
"asignacion : ID '=' expresion",
"asignacion : ID '=' error",
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
"factor : ID PUNTO_PUNTO ID",
"factor : ID",
"comparador : '<'",
"comparador : '>'",
"comparador : IGUAL",
"comparador : MAYOR_IGUAL",
"comparador : MENOR_IGUAL",
"comparador : DISTINTO",
};

//#line 227 "G08 - Gramatica.y"


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
//#line 483 "Parser.java"
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
{addError("Error Sintactico en linea "+ la.getNroLinea() +": error en condicion");}
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
{addError("Error Sintactico en linea "+ la.getNroLinea() +": falta '}' del bloque  . ");}
break;
case 53:
//#line 136 "G08 - Gramatica.y"
{addRule("Linea "+ la.getNroLinea() +": Sentencia FOR.");}
break;
case 54:
//#line 138 "G08 - Gramatica.y"
{addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera '}'.");}
break;
case 55:
//#line 140 "G08 - Gramatica.y"
{addError("Error Sintactico en linea "+ la.getNroLinea() +": falta '}'");}
break;
case 56:
//#line 142 "G08 - Gramatica.y"
{addError("Error Sintactico en linea "+ la.getNroLinea() +": faltan llaves");}
break;
case 57:
//#line 144 "G08 - Gramatica.y"
{addError("Error Sintactico en linea "+ la.getNroLinea() +": falta ')' ");}
break;
case 58:
//#line 146 "G08 - Gramatica.y"
{addError("Error Sintactico en linea "+ la.getNroLinea() +": error en sentencia FOR ");}
break;
case 59:
//#line 149 "G08 - Gramatica.y"
{addRule("Linea "+ la.getNroLinea() +": CONDICION FOR.");}
break;
case 60:
//#line 152 "G08 - Gramatica.y"
{addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera una expresion luego del operador de comparacion."); }
break;
case 61:
//#line 153 "G08 - Gramatica.y"
{ addError("Error Sintáctico en línea "+ la.getNroLinea() +": Se espera la condición antes del operador de comparación."); }
break;
case 62:
//#line 154 "G08 - Gramatica.y"
{ addError("Error Sintáctico en línea "+ la.getNroLinea() +": Condicion definida incorrectamente."); }
break;
case 65:
//#line 163 "G08 - Gramatica.y"
{System.out.println(val_peek(1).sval);addRule("Linea "+ la.getNroLinea() +": Sentencia OUT.");}
break;
case 66:
//#line 165 "G08 - Gramatica.y"
{ addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera ')'."); }
break;
case 67:
//#line 166 "G08 - Gramatica.y"
{ addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera '('."); }
break;
case 68:
//#line 167 "G08 - Gramatica.y"
{ addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera una cadena de caracteres luego de '('."); }
break;
case 69:
//#line 168 "G08 - Gramatica.y"
{ addError("Error Sintactico en linea "+ la.getNroLinea() +": falta cadena"); }
break;
case 70:
//#line 169 "G08 - Gramatica.y"
{ addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera '('."); }
break;
case 71:
//#line 174 "G08 - Gramatica.y"
{addRule("Linea "+ la.getNroLinea() +": CONVERSION EXPLICITA.");}
break;
case 72:
//#line 176 "G08 - Gramatica.y"
{addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera ')'."); }
break;
case 73:
//#line 177 "G08 - Gramatica.y"
{addError("Error Sintactico en linea "+ la.getNroLinea() +": Se espera expresion."); }
break;
case 75:
//#line 185 "G08 - Gramatica.y"
{addRule("Linea "+ la.getNroLinea() +": ASIGNACION.");}
break;
case 76:
//#line 186 "G08 - Gramatica.y"
{addError("Error Sintactico en linea "+ la.getNroLinea() + ": Expresion invalida en asignacion.");}
break;
case 80:
//#line 196 "G08 - Gramatica.y"
{ addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Se espera un termino luego del '+'."); }
break;
case 81:
//#line 197 "G08 - Gramatica.y"
{ addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Se espera un termino luego del '-'."); }
break;
case 85:
//#line 204 "G08 - Gramatica.y"
{addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Se espera un factor * ");}
break;
case 86:
//#line 205 "G08 - Gramatica.y"
{addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Se espera un factor");}
break;
case 89:
//#line 210 "G08 - Gramatica.y"
{
										String lexeme ='-'+ val_peek(0).sval;
										this.la.addSymbolTable(lexeme, "NRO_DOUBLE");
									}
break;
//#line 823 "Parser.java"
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
