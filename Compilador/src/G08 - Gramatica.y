%{

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


%}

%token	ID ULONGINT IF THEN ELSE END_IF FOR OUT PROC RETURN DOUBLE MENOR_IGUAL MAYOR_IGUAL IGUAL DISTINTO PUNTO_PUNTO UP DOWN CADENA NA NRO_DOUBLE NRO_ULONGINT

%left '+' '-'
%left '*' '/'
%start programa

/* Comienzo del programa */

%%
programa				: lista_sentencias
						{
							syntacticTree = $1.tree;
						}
						; 

lista_sentencias		: sentencia 
						{
							$$.tree = $1.tree;
						}
						| lista_sentencias sentencia 
						{
							$$.tree = new SyntacticTree($1.tree, $2.tree, "LISTA SENTENCIAS");
						}
						| error ';'
						;

sentencia 				:sent_declarativa ';'
						{
							$$.tree = $1.tree;

						}
						|sent_ejecutable 
						{
							$$.tree = $1.tree;
						}
						;

sent_declarativa		: tipo lista_variables
						{
							addRule("Linea "+ la.getNroLinea() +": Sentencia declarativa - Variable/s.");
		                    $$ = $2;
		                    Type type = new Type($1.type.getName());
		                    for(String lexeme : $$.attributesSetteable){
		                        List<Attribute> attributes = la.getAttribute(lexeme);
		                        attributes.get(attributes.size() - 1).setUse(Use.variable);
		                        attributes.get(attributes.size() - 1).setType(type);

		                        attributes = getListUse(attributes, Use.variable);
		                        this.isRedeclared(attributes, lexeme, "Sentencia declarativa - Redefinicion de  VARIABLE/S");

		                        attributes.get(attributes.size()-1).setScope(this.globalScope);
							}
								
						}
						
						| procedimiento
						{
							$$.tree = $1.tree;
							$$ = $1;
							this.decreaseScope();
						}

						| tipo error ';' {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia declarativa - Falta definir la/s VARIABLE/S."); }
						;

sent_ejecutable			:sentencia_if ';'
						{
							$$.tree = $1.tree;
						}
						
						|sentencia_control
						{
							$$.tree = $1.tree;
						}

						|asignacion ';'
						{
							$$.tree = new SyntacticTree($1.tree, "ASIGNACION");
						}
						|asignacion error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia ejecutable asignacion - Falta ;"); }

						|imprimir ';'
						{
							$$.tree = $1.tree;
						}
						|imprimir error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia ejecutable OUT - Falta ;"); }

						|llamado_PROC ';'
						{
							$$.tree = $1.tree;
						}

						|llamado_PROC error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia ejecutable llamado a procedimiento - Falta ;"); }
						;

/* PROCEDIMIENTO */
procedimiento 			:encabezado cuerpo_procedimiento
						{
							addRule("Linea "+ la.getNroLinea() +": Procedimiento");
							this.sa.deleteNA();
							$$.tree = $2.tree;
						}
						|encabezado error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Procedimiento - Se espera CUERPO del PROCEDIMIENTO"); }
						;

encabezado 				:encabezado_PROC parametro_PROC asignacion_NA
						{
							addRule("Linea "+ la.getNroLinea() +": Encabezado procedimiento");
						}
						;

encabezado_PROC			:PROC ID
						{
							addRule("Linea "+ la.getNroLinea() +": PROC ID");

							$$.attributes = $2.attributes;
							String[] scope = $2.attributes.get(0).getScope().split("@");

							List<Attribute> attributes = la.getAttribute(scope[0]);
		                    attributes.get(attributes.size() - 1).setUse(Use.nombre_procedimiento);
		                    attributes.get(attributes.size() - 1).setScope(this.globalScope);

		                    $2.attributes = getListUse($2.attributes, Use.nombre_procedimiento);
							this.isRedeclared($2.attributes, scope[0], "Sentencia declarativa - Redefinicion de ID procedimiento");

		                    this.globalScope += "@" + scope[0];
						}
						;

parametro_PROC			:'(' parametro ')'
						{
							addRule("Linea "+ la.getNroLinea() +": Procedimiento - un parametro");

							this.setScopeProcParam($2.attributesSetteable);
							$2.attributes = getListUse($2.attributes, Use.nombre_parametro);
							String[] scope = $2.attributes.get(0).getScope().split("@");
							this.isRedeclared($2.attributes, scope[0], "Sentencia declarativa - Redefinicion de parametro");
						}
						|'(' parametro ',' parametro ')'
						{
							addRule("Linea "+ la.getNroLinea() +": Procedimiento - dos parametros");

							this.setScopeProcParam($2.attributesSetteable);
							$2.attributes = getListUse($2.attributes, Use.nombre_parametro);
							String[] scope = $2.attributes.get(0).getScope().split("@");
							this.isRedeclared($2.attributes, scope[0], "Sentencia declarativa - Redefinicion de parametro");

							this.setScopeProcParam($4.attributesSetteable);
							$4.attributes = getListUse($4.attributes, Use.nombre_parametro);
							scope = $4.attributes.get(0).getScope().split("@");
							this.isRedeclared($4.attributes, scope[0], "Sentencia declarativa - Redefinicion de parametro");

						}
						|'(' parametro ',' parametro ',' parametro ')'
						{
							this.setScopeProcParam($2.attributesSetteable);
							$2.attributes = getListUse($2.attributes, Use.nombre_parametro);
							String[] scope = $2.attributes.get(0).getScope().split("@");
							this.isRedeclared($2.attributes, scope[0], "Sentencia declarativa - Redefinicion de parametro");

							this.setScopeProcParam($4.attributesSetteable);
							$4.attributes = getListUse($4.attributes, Use.nombre_parametro);
							scope = $4.attributes.get(0).getScope().split("@");
							this.isRedeclared($4.attributes, scope[0], "Sentencia declarativa - Redefinicion de parametro");

							this.setScopeProcParam($6.attributesSetteable);
							$6.attributes = getListUse($6.attributes, Use.nombre_parametro);
							scope = $6.attributes.get(0).getScope().split("@");
							this.isRedeclared($6.attributes, scope[0], "Sentencia declarativa - Redefinicion de parametro");
						}
						| '(' ')'
						;

cuerpo_procedimiento	:'{' bloque_procedimiento '}'
						{
							$$.tree = $2.tree;
						}

						|'{' bloque_procedimiento error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Cuerpo procedimiento - Se espera }"); }
						|'{' error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Cuerpo procedimiento - Se espera bloque cuerpo procedimiento"); }
						; 


bloque_procedimiento	:sentencia 
						{
							$$.tree = new SyntacticTree($1.tree, "CUERPO PROCEDIMIENTO SENTENCIA");
							$$ = $1;
						} 
						|bloque_procedimiento sentencia
						{
							$$.tree = new SyntacticTree($1.tree, $2.tree, "CUERPO PROCEDIMIENTO SENTENCIA COMPUESTA");
							$$ = $1;
						}
						|procedimiento
						{
							$$ = $1;
						}
						|bloque_procedimiento procedimiento
						{
							$$ = $1;
						}
						;

asignacion_NA  			:NA '=' NRO_ULONGINT 
						{
							addRule("Linea "+ la.getNroLinea() +": Procedimiento - Asignacion NA");
							
							int pos = sa.checkNA(this.globalScope);
							if(pos != -1){
								String ID_PROC = sa.errorNA(pos, this.globalScope);
								addError("Error Semántico en linea "+ la.getNroLinea() +": Sentencia declarativa - Se supera numero de anidamiento en PROC " + ID_PROC);
							}

							sa.addNA(Integer.valueOf($3.attributes.get(0).getScope()));

						}

						|NA '=' error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Asignacion NA procedimiento - Se espera NRO_ULONGINT"); }
						|NA error NRO_ULONGINT {addError("Error Sintactico en linea "+ la.getNroLinea() +": Asignacion NA procedimiento - Se espera ="); }
						;

/* PARAMETROS */
parametro 				:tipo ID
						{
							$$ = $2;

							$$.attributesSetteable = new ArrayList<>(); 
							String scope = $2.attributes.get(0).getScope();
							String[] lexeme = scope.split("@");
							$$.attributesSetteable.add(lexeme[0]);

							Type type = new Type($1.type.getName());
							$2.attributes.get($2.attributes.size()-1).setType(type);
							$2.attributes.get($2.attributes.size()-1).setUse(Use.nombre_parametro);
 						}
						;

parametro_invocacion 	: ID 
						{ 
						}
						| ID ',' ID
						{ 
						}
						| ID ',' ID ',' ID
						;

/* Sentencias declarativas */

lista_variables 		: ID
						{ 
							$$.attributesSetteable = new ArrayList<>(); 
							String scope = $1.attributes.get(0).getScope();
							String[] lexeme = scope.split("@");
							$$.attributesSetteable.add(lexeme[0]);

                        }
						| lista_variables ',' ID
						{ 
							$$ = $1; 
							String scope = $3.attributes.get(0).getScope();
							String[] lexeme = scope.split("@");
							$$.attributesSetteable.add(lexeme[0]);

						}
						;

tipo					: ULONGINT 
						{
							$$.type = Type.ULONGINT;
							$$.tree  = new SyntacticTree(null, null, "ULONGINT");

						}					
						| DOUBLE
						{
							$$.type = Type.DOUBLE;
							$$.tree  = new SyntacticTree(null, null, "DOUBLE");
						}
						;

/* Sentencias Ejecutables */
llamado_PROC 			: ID '(' parametro_invocacion ')' 
						{ 
							addRule("Linea "+ la.getNroLinea() +": Llamado a procedimiento con parametros");
							//$$.tree = new SyntacticTree(new SyntacticTree(null, null, $1.attribute.getLexeme()), $3.tree,"LLAMADO PROC SIN PAR");

						}
						| ID '(' ')' 
						{ 
							addRule("Linea "+ la.getNroLinea() +": Llamado a procedimiento sin parametros");
							//$$.tree = new SyntacticTree(new SyntacticTree(null, null, $1.attributes.get(0).getScope()), "LLAMADO PROC SIN PAR");

							$1.attributes.get($1.attributes.size()-1).setUse(Use.llamado_procedimiento);
			                $1.attributes = getListUse($1.attributes, Use.nombre_procedimiento);
			                boolean encontro = false;
			                if($1.attributes.isEmpty())
			                    encontro = false;
			                else {
			                    String[] scope = $1.attributes.get(0).getScope().split("@");
			                    String lexeme = scope[0];

			                    for (Attribute attribute : $1.attributes) {
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

						|ID '(' parametro_invocacion error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Llamado a procedimiento - Se espera )"); }
						;

/* IF */

sentencia_if			:IF condicion_IF cuerpo END_IF
						{
							addRule("Linea "+ la.getNroLinea() +": Sentencia IF");
							$$.tree = new SyntacticTree($2.tree, $3.tree, "IF");
						}

						|IF condicion_IF cuerpo error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF - Se espera END_IF"); }
						|IF condicion_IF error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF - Se espera cuerpo"); }
						|IF error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF - Se espera condicion"); }		
						;

cuerpo 					:bloque_IF bloque_else
						{
							addRule("Linea "+ la.getNroLinea() +": Sentencia IF ELSE - Cuerpo");
							$$.tree = new SyntacticTree($1.tree, $2.tree, "CUERPO_IF_ELSE");
						}
						|bloque_IF
						{
							addRule("Linea "+ la.getNroLinea() +": Sentencia IF - Cuerpo");
							$$.tree = new SyntacticTree($1.tree, "CUERPO_IF");
						}
						;


condicion_IF 			:'(' expresion '<' expresion ')'
						{
							addRule("Linea "+ la.getNroLinea() +": Sentencia IF - Condicion <.");
							$$.tree = new SyntacticTree(new SyntacticTree($2.tree, $4.tree, "<"), "COND");
						}
						|'(' expresion '>' expresion ')'
						{
							addRule("Linea "+ la.getNroLinea() +":Sentencia IF - Condicion >.");
							$$.tree = new SyntacticTree(new SyntacticTree($2.tree, $4.tree, ">"), "COND");
						}
						|'('expresion IGUAL expresion ')'
						{
							addRule("Linea "+ la.getNroLinea() +": Sentencia IF - Condicion ==.");
							$$.tree = new SyntacticTree(new SyntacticTree($2.tree, $4.tree, "=="), "COND");
						}
						|'('expresion MAYOR_IGUAL expresion ')'
						{
							addRule("Linea "+ la.getNroLinea() +": Sentencia IF - Condicion >=.");
							$$.tree = new SyntacticTree(new SyntacticTree($2.tree, $4.tree, ">="), "COND");
						}
						|'('expresion MENOR_IGUAL expresion ')'
						{
							addRule("Linea "+ la.getNroLinea() +": Sentencia IF - Condicion <=.");
							$$.tree = new SyntacticTree(new SyntacticTree($2.tree, $4.tree, "<="), "COND");
						}
						|'('expresion DISTINTO expresion ')'
						{
							addRule("Linea "+ la.getNroLinea() +": Sentencia IF - Condicion !=.");
							$$.tree = new SyntacticTree(new SyntacticTree($2.tree, $4.tree, "!="), "COND");
						}

						|'(' expresion '<' expresion error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera )"); }
						|'(' expresion '>' expresion error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera )"); }
						|'(' expresion IGUAL expresion error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera )"); }
						|'(' expresion MAYOR_IGUAL expresion error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera )"); }
						|'(' expresion MENOR_IGUAL expresion error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera )"); }
						|'(' expresion DISTINTO expresion error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera )"); }

						|'(' expresion '<' error ')' {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera expresion derecha"); }
						|'(' expresion '>' error ')'{addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera expresion derecha"); }
						|'(' expresion IGUAL error ')'{addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera expresion derecha"); }
						|'(' expresion MAYOR_IGUAL error ')'{addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera expresion derecha"); }
						|'(' expresion MENOR_IGUAL error ')'{addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera expresion derecha"); }
						|'(' expresion DISTINTO error ')'{addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera expresion derecha"); }

						|'(' expresion error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera comparador"); }

						|'(' error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera expresion izquierda"); }

						| error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF condicion - Se espera ("); }
						;


bloque_IF 				:'{' cuerpo_ejecutable '}'
						{ 
							addRule("Linea "+ la.getNroLinea() +": Sentencia IF - Bloque de sentencias");
							$$.tree = new SyntacticTree($2.tree, "BLOQUE_IF");
						}
						|sent_ejecutable 
						{
							$$.tree = $1.tree;
						}

						|'{' cuerpo_ejecutable error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF bloque - Se espera } finalizacion BLOQUE IF");}
						|'{' error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF bloque - Se espera cuerpo_ejecutable");}
						;

bloque_else				:ELSE bloque_IF
						{
							addRule("Linea "+ la.getNroLinea() +": Sentencia IF ELSE - bloque de sentencias ELSE");
							$$.tree = new SyntacticTree($2.tree, "BLOQUE_ELSE");
						}

						|ELSE error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia IF ELSE - Se espera bloque de sentencias");}
						;

bloque_FOR 				:'{' cuerpo_ejecutable '}' ';'
						{ 
							addRule("Linea "+ la.getNroLinea() +": Sentencia FOR - Bloque de sentencias");
							$$.tree = new SyntacticTree($2.tree, "BLOQUE_FOR");
						}
						|sent_ejecutable 
						{
							$$.tree = $1.tree;
						}

						|'{' cuerpo_ejecutable '}' error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR bloque - Se espera ;");}
						|'{' cuerpo_ejecutable error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR bloque - Se espera } ");}
						|'{' error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR bloque - Se espera cuerpo_ejecutable ");}
						;

cuerpo_ejecutable 		:sent_ejecutable 
						{
							$$.tree = $1.tree;
						}
						|cuerpo_ejecutable sent_ejecutable
						{
							$$.tree = new SyntacticTree($1.tree, $2.tree, "SENTENCIA");
						}
						;


asignacion 				:tipo_ID '=' expresion {
												addRule("Linea "+ la.getNroLinea() +": Asignacion");
												$$.tree = new SyntacticTree($1.tree, $3.tree, "=");
												}

						|tipo_ID '=' error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Asignacion - Se espera expresion lado derecho");}
						|tipo_ID error expresion {addError("Error Sintactico en linea "+ la.getNroLinea() +": Asignacion - Se espera =");}
						|error '=' expresion {addError("Error Sintactico en linea "+ la.getNroLinea() +": Asignacion - Se espera ID lado izquierdo");}
						;

/* FOR */

sentencia_control 		:FOR '(' asignacion_FOR ';' condicion_FOR_dos
						{
							addRule("Linea "+ la.getNroLinea() +": Sentencia FOR");
							$$.tree = new SyntacticTree($3.tree, $5.tree, "FOR_ASIGNACION");
						}

						|FOR '(' asignacion_FOR error { addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR - Se espera ;."); }
						|FOR '(' error { addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR - Se espera asignacion"); }
						|FOR error { addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR - Se espera ("); }
						;

condicion_FOR_dos		: comparacion_FOR ';' condicion_FOR_tres 
						{
							$$.tree = new SyntacticTree(new SyntacticTree($1.tree, "COMPARACION"), new SyntacticTree($3.tree, "CUERPO"), "FOR");
						}
						|comparacion_FOR error condicion_FOR_tres {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera ;"); }
						|error ';' condicion_FOR_tres {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera condicion antes de ;"); }
						;

condicion_FOR_tres		: incr_decr ')' bloque_FOR
						{
							$$.tree = new SyntacticTree($1.tree, $3.tree, "SENTENCIAS_FOR");
						}
						|error ')' bloque_FOR {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera UP/DOWN NRO_ULONGINT"); }
						|incr_decr error bloque_FOR {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera )"); }
						|incr_decr ')' error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera bloque_FOR"); }
						;


asignacion_FOR 			:ID '=' NRO_ULONGINT
						{
							addRule("Linea "+ la.getNroLinea() +": ASIGNACION_FOR");

							$$.tree = new SyntacticTree(new SyntacticTree(null, null, $1.attributes.get(0).getScope()), new SyntacticTree(null, null, $3.attributes.get(0).getScope()), "=");
						}

						|ID '=' error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR asignacion - Se espera NRO_ULONGINT lado derecho"); }
						|ID error NRO_ULONGINT {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR asignacion - Se espera ="); }
						|error '=' NRO_ULONGINT {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR asignacion - Se espera ID lado izquierdo"); }
						;

comparacion_FOR			:ID '<' expresion
						{
							addRule("Linea "+ la.getNroLinea() +": Sentencia FOR - Condicion <.");
							//$$.tree = new SyntacticTree(new SyntacticTree(null, null, $1.attributes.get(0).getScope()), $3.tree, "<");
						}
						|ID '>' expresion
						{
							addRule("Linea "+ la.getNroLinea() +": Sentencia FOR - Condicion >");
							//$$.tree = new SyntacticTree(new SyntacticTree(null, null, $1.attributes.get(0).getScope()), $3.tree, ">");
						}
						|ID IGUAL expresion
						{
							addRule("Linea "+ la.getNroLinea() +": Sentencia FOR - Condicion ==");
							//$$.tree = new SyntacticTree(new SyntacticTree(null, null, $1.attributes.get(0).getScope()), $3.tree, "==");
						}
						|ID MAYOR_IGUAL expresion
						{
							addRule("Linea "+ la.getNroLinea() +": Sentencia FOR - Condicion >=");
							//$$.tree = new SyntacticTree(new SyntacticTree(null, null, $1.attributes.get(0).getScope()), $3.tree, ">=");
						}
						|ID MENOR_IGUAL expresion
						{
							addRule("Linea "+ la.getNroLinea() +": Sentencia FOR - Condicion <=");
							//$$.tree = new SyntacticTree(new SyntacticTree(null, null, $1.attributes.get(0).getScope()), $3.tree, "<=");
						}
						|ID DISTINTO expresion
						{
							addRule("Linea "+ la.getNroLinea() +": Sentencia FOR - Condicion !=");
							$$.tree = new SyntacticTree(new SyntacticTree(null, null, $1.attributes.get(0).getScope()), $3.tree, "!=");
						}

						|ID '<' error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera expresion lado derecho comparacion"); }
						|ID '>' error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera expresion lado derecho comparacion"); }
						|ID IGUAL error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera expresion lado derecho comparacion"); }
						|ID MAYOR_IGUAL error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera expresion lado derecho comparacion"); }
						|ID MENOR_IGUAL error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera expresion lado derecho comparacion"); }
						|ID DISTINTO error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera expresion lado derecho comparacion"); }

						|ID error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera comparador"); }

						|error '<' expresion {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera ID lado izquierdo comparacion"); }
						|error '>' expresion {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera ID lado izquierdo comparacion"); }
						|error IGUAL expresion {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera ID lado izquierdo comparacion"); }
						|error MAYOR_IGUAL expresion {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera ID lado izquierdo comparacion"); }
						|error MENOR_IGUAL expresion {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera ID lado izquierdo comparacion"); }
						|error DISTINTO expresion {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR condicion - Se espera ID lado izquierdo comparacion"); }
						;

incr_decr				: UP NRO_ULONGINT
						{
							$$.tree  = new SyntacticTree(new SyntacticTree(null, null, $2.attributes.get(0).getScope()), "UP");
						}
						| DOWN NRO_ULONGINT
						{
							$$.tree  = new SyntacticTree(new SyntacticTree(null, null, $2.attributes.get(0).getScope()), "DOWN");
						}

						|DOWN error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR decremento - Se espera NRO_ULONGINT"); }
						|UP error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR incremento - Se espera NRO_ULONGINT"); }
						|error NRO_ULONGINT {addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia FOR incre/decre - Se espera UP/DOWN"); }
						;

/* IMPRIMIR */

imprimir 				: OUT '(' CADENA ')' 
						{	
							System.out.println($3.attributes.get(0).getScope());
							addRule("Linea "+ la.getNroLinea() +": Sentencia OUT");
							$$.tree  = new SyntacticTree(new SyntacticTree(null, null, $3.attributes.get(0).getScope()), "IMPRIMIR");
						}

						| OUT '(' CADENA error  { addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia OUT - Se espera ')'."); }
						| OUT CADENA ')' { addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia OUT - Se espera '('."); }
						| OUT '(' error { addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia OUT - Se espera una cadena de caracteres luego de '('."); }
						| OUT '(' ')' error { addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia OUT - falta cadena"); }
						| OUT error { addError("Error Sintactico en linea "+ la.getNroLinea() +": Sentencia OUT - Se espera '('."); }
						;

/* CONVERSION */

conversion_explicita	:tipo '(' expresion ')'
						{
							addRule("Linea "+ la.getNroLinea() +": Conversion explicita");		
							$$.tree  = new SyntacticTree($1.tree, $3.tree, "CONVERSION");
						}

						|tipo '(' expresion error {addError("Error Sintactico en linea "+ la.getNroLinea() +": Conversion explicita - Se espera ')'."); }
						|tipo '(' error ')' {addError("Error Sintactico en linea "+ la.getNroLinea() +": Conversion explicita - Se espera expresion.");}
						;

/* EXPRESION, TERMINO, FACTOR, CONSTANTE */

expresion 				: expresion '+' termino 
						{
							addRule("Linea "+ la.getNroLinea() +": Suma");
							$$.tree = new SyntacticTree($1.tree, $3.tree, "+");
						}
						| expresion '-' termino 
						{
							addRule("Linea "+ la.getNroLinea() +": Resta");
							$$.tree = new SyntacticTree($1.tree, $3.tree, "-");
						}
						|termino 
						{
							$$.tree = $1.tree;
						}
						|conversion_explicita
						{
							$$.tree = $1.tree;
						}

						| expresion '+' error { addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Suma - Se espera un termino luego del '+'."); }
						| error '+' termino { addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Suma - Se espera una expresion antes del '+'."); }
				        | expresion '-' error  { addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Resta - Se espera un termino luego del '-'."); }
				        | error '-' termino { addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Resta - Se espera una expresion antes del '-'."); }
						;

termino 				:termino '*' factor 
						{
							addRule("Linea "+ la.getNroLinea() +": Multiplicacion");
							$$.tree = new SyntacticTree($1.tree, $3.tree, "*");
						}
						|termino '/' factor 
						{
							addRule("Linea "+ la.getNroLinea() +": Division");
							$$.tree = new SyntacticTree($1.tree, $3.tree, "/");
						}
						|factor 
						{
							$$.tree = $1.tree;
						}

						| termino '*' error {addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Multiplicacion - Se espera un factor luego de * ");}
						| termino '/' error {addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Division - Se espera un factor luego de /");}
						| error '*' factor {addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Multiplicacion - Se espera un termino antes de * ");}
						| error '/' factor {addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Division - Se espera un termino antes de /");}
						;

factor 					:NRO_ULONGINT 
						{
							addRule("Linea "+ la.getNroLinea() +": NRO_ULONGINT.");

							$$.tree  = new SyntacticTree(null, null, $1.attributes.get(0).getScope());
						}
						|NRO_DOUBLE
						{
							$$.tree  = new SyntacticTree(null, null, $1.attributes.get(0).getScope());

							addRule("Linea "+ la.getNroLinea() +": NRO_DOUBLE.");
							$$.tree = new SyntacticTree(null, null, $1.attributes.get(0).getScope());
						}
						|'-' NRO_DOUBLE {
										String lexeme = $2.attributes.get(0).getScope();
										boolean check = la.checkNegativeDouble(lexeme);
										if(check){
											addError("Error Sintáctico en línea "+ la.getNroLinea() +": DOUBLE fuera de rango.");
										}else{
											addRule("Linea "+ la.getNroLinea() +": NRO_DOUBLE negativo.");
											Attribute attribute = new Attribute(lexeme,"NRO_DOUBLE", Type.DOUBLE);
											la.addSymbolTable(lexeme, attribute);
											$2.attributes.get(0).decreaseAmount();
											int amount = la.getAttribute(lexeme).get(0).getAmount();
											if(amount == 0){
												la.getSt().deleteSymbolTableEntry(lexeme);
											}
										}
										}
						| tipo_ID 
						{
							$$.tree = $1.tree;
						}
						;

tipo_ID					:ID PUNTO_PUNTO ID
						{
							addRule("Linea "+ la.getNroLinea() +": ID PUNTO_PUNTO ID");

							String[] scopeIz = $1.attributes.get(0).getScope().split("@");
			                String lexemeIz = scopeIz[0];

							String[] scopeDer = $3.attributes.get(0).getScope().split("@");
			                String lexemeDer = scopeDer[0];

			             	$$.tree  = new SyntacticTree(new SyntacticTree(null, null, lexemeIz), new SyntacticTree(null, null, lexemeDer), "::");
						}

						|ID PUNTO_PUNTO error {addError("Error Sintáctico en linea "+ la.getNroLinea() + ": Tipo ID - Se espera ID luego de ::");}

						|ID 
						{
							addRule("Linea "+ la.getNroLinea() +": ID");

							String[] scope = $1.attributes.get(0).getScope().split("@");
			                String lexeme = scope[0];

			                $$.tree  = new SyntacticTree(null, null, lexeme);

		                    $1.attributes.get($1.attributes.size()-1).setUse(Use.variable_en_uso);
		                    List<Attribute> variables = getListUse($1.attributes, Use.variable);
		                    List<Attribute> parametros = getListUse($1.attributes, Use.nombre_parametro);

		                    boolean encontro = false;

		                    if(variables.size() > 0){
		                    	if(parametros.size() > 0){
		                    		parametros.addAll(variables);
		                    		$1.attributes = parametros;
		                   		}else{
		                   			$1.attributes = variables;
		                    	}
		                    }else{
		                   		if(parametros.size() > 0) {
		                    		$1.attributes = parametros;
		                    	}else {
		                    		$1.attributes = null;
		                    	}
		                    }

		                    if($1.attributes == null){
		                        encontro = false;
		                    }else{	                    
			                    for (Attribute attribute : $1.attributes) {
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
						;

 
%%

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

