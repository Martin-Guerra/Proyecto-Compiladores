package Lexer;

import java.util.HashMap;
import semantic_Actions.*;

public class StateMatrix {
	private static final int EOF = 9999999;
	private State[][] matrix;
	private static final int ROW = 19;
	private static final int COLUMN =  28;
	private HashMap mapofcaracters;
	
	public StateMatrix () {
		this.matrix = new State[ROW][COLUMN];
		
	
		for(int i = 0; i < ROW; i++) {
			for(int j = 0; j < COLUMN; j++) {
				if( i == 0 || i == 1 || i == 5 || i == 8 || i == 10 || i == 11 || i == 15
					|| i == 16 || i == 17 || i == 18 ) {
					this.matrix[i][j] = new State(-1);
				}
				if(i == 2 || i == 3 || i == 4 || i == 6 || i == 7 || i == 9)
					this.matrix[i][j] = new State(0);
				
				if(i == 12 || i == 13)
					this.matrix[i][j] = new State(12);
				if(i == 14)
					this.matrix[i][j] = new State(14);
			}
		}
		
		//Fila 0
		matrix[0][0].setState(1);
		matrix[0][1].setState(2);
		matrix[0][2].setState(1);
		matrix[0][3].setState(1);
		matrix[0][4].setState(1);
		matrix[0][5].setState(9);
		matrix[0][6].setState(0);
		matrix[0][7].setState(5);
		matrix[0][8].setState(11);
		matrix[0][9].setState(0);
		matrix[0][10].setState(14);
		matrix[0][20].setState(15);
		matrix[0][21].setState(16);
		matrix[0][22].setState(17);
		matrix[0][23].setState(18);
		matrix[0][24].setState(0);
		matrix[0][25].setState(0);
		matrix[0][26].setState(0);
		
		//Fila 1
		matrix[1][0].setState(1);
		matrix[1][1].setState(1);
		matrix[1][2].setState(1);
		matrix[1][3].setState(1);
		matrix[1][4].setState(1);
		matrix[1][6].setState(1);
		
		//Fila 2
		matrix[2][1].setState(2);
		matrix[2][6].setState(3);
		matrix[2][7].setState(5);
		
		
		//Fila 3		
		matrix[3][3].setState(4);
		
		
		//Fila 4
		matrix[4][4].setState(-1);
		
		
		//Fila 5
		matrix[5][1].setState(5);
		matrix[5][2].setState(6);
		
		//Fila 6		
		matrix[6][11].setState(7);
		matrix[6][19].setState(7);
		
		//Fila 7
		matrix[7][1].setState(8);
		
		//Fila 8
		matrix[8][1].setState(8);
		
		//Fila 9
		matrix[9][1].setState(9);
		matrix[9][6].setState(10);
		
		//Fila 10
		matrix[10][5].setState(10);
		
		//Fila 11
		matrix[11][9].setState(12);
		
		//Fila 12
		matrix[12][9].setState(13);
		
		//Fila 13
		matrix[13][8].setState(0);
		matrix[13][9].setState(3);
		
		
		//Fila 14
		matrix[14][18].setState(-1);
		matrix[14][26].setState(0);
		
		
		
		
		

		// Semantic Action Matrix
		SemanticAction sa1 = new SemanticAction1();
		SemanticAction sa2 = new SemanticAction2();
		SemanticAction sa3 = new SemanticAction3();
		SemanticAction sa4 = new SemanticAction4();
		SemanticAction sa5 = new SemanticAction5();
		SemanticAction sa6 = new SemanticAction6();
		SemanticAction sa7 = new SemanticAction7();
		SemanticAction sa8 = new SemanticAction8();
		SemanticAction sa9 = new SemanticAction9();
		SemanticAction sa10 = new SemanticAction10();
		SemanticAction sa11 = new SemanticAction11();
		SemanticAction sa12 = new SemanticAction12();
		SemanticAction sa13 = new SemanticAction13();
		SemanticAction sa14 = new SemanticAction13();
		SemanticAction sa15 = new SemanticAction13();
		
		/*ERR_ID errId = new ERR_ID();
		ERR_CTN errCte = new ERR_CTN();
		ERR_COM errCom = new ERR_COM();
		ERR_CHAR errChar = new ERR_CHAR();*/
		
		for(int i = 0; i < ROW; i++) {
			for(int j = 0; j < COLUMN - 1; j++) {
				if(i == 0)
					matrix[i][j].setSemanticAction(sa5);
				if(i == 1)
					matrix[i][j].setSemanticAction(sa2);
				if(i == 2 || i == 3 || i == 4 || i == 6 || i == 7 || i == 9)
					matrix[i][j].setSemanticAction(sa12);
				if(i == 5)
					matrix[i][j].setSemanticAction(sa6);
				if(i == 8)
					matrix[i][j].setSemanticAction(sa3);
				if(i == 10)
					matrix[i][j].setSemanticAction(sa4);
				if(i == 11)
					matrix[i][j].setSemanticAction(sa1);
				if(i == 12 || i == 13 || i == 14 )
					matrix[i][j].setSemanticAction(sa5);
				if(i == 15 || i == 16 || i == 17 || i == 18)
					matrix[i][j].setSemanticAction(sa1);
			}
		}
		
		//Fila 0
		for(int j = 0; j <= 5; j++) {
			matrix[0][j].setSemanticAction(sa5);
		}
		matrix[0][6].setSemanticAction(sa12);
		matrix[0][7].setSemanticAction(sa5);
		matrix[0][8].setSemanticAction(sa5);
		matrix[0][9].setSemanticAction(sa12);
		matrix[0][24].setSemanticAction(sa12);
		matrix[0][25].setSemanticAction(sa13);
		matrix[0][26].setSemanticAction(sa13);
		
		
		//Fila 1
		for(int j = 0; j <= 4; j++) {
			matrix[1][j].setSemanticAction(sa5);
		}
		matrix[1][5].setSemanticAction(sa2);
		matrix[1][6].setSemanticAction(sa5);
		
		
		//Fila 2
		matrix[2][1].setSemanticAction(sa5);
		matrix[2][6].setSemanticAction(sa5); 
		matrix[2][7].setSemanticAction(sa5);
		
		//Fila 3		
		matrix[3][3].setSemanticAction(sa5);
		
		//Fila 4
		matrix[4][4].setSemanticAction(sa7); 
		
		//Fila 5 ya cargada
		matrix[5][1].setSemanticAction(sa5); 
		matrix[5][2].setSemanticAction(sa5); 
		
		//Fila 6
		matrix[6][11].setSemanticAction(sa5); 
		matrix[6][19].setSemanticAction(sa5); 
		
		//Fila 7
		matrix[7][1].setSemanticAction(sa5);
		
		
		//Fila 8
		matrix[8][1].setSemanticAction(sa5);
		
		//Fila 9
		matrix[9][5].setSemanticAction(sa5);
		matrix[9][6].setSemanticAction(sa5);
		
		//Fila 10
		matrix[10][5].setSemanticAction(sa5);
		
		//Fila 11
		matrix[11][9].setSemanticAction(sa5);
		
		//file 12 cargada
		
		//Fila 13 ya cargada
		matrix[13][8].setSemanticAction(sa9);
		
		//Fila 14
		matrix[14][10].setSemanticAction(sa11);
		matrix[14][26].setSemanticAction(sa14);
		
			
		//Fila 15 en adelante
		for(int i = 15; i < ROW; i++) {
			matrix[i][22].setSemanticAction(sa10);
		}
		
		for(int i = 0; i < ROW; i++) {
			matrix[i][27].setNextstate(EOF);
			matrix[i][27].setSemanticAction(sa15);
		}
		
		mapofcaracters = new HashMap();
		
		this.mapofcaracters.put("minuscula", 0);//problema
		this.mapofcaracters.put("digito", 1);//problema
		this.mapofcaracters.put("d", 2);
		this.mapofcaracters.put("u", 3);
		this.mapofcaracters.put("l", 4);
		this.mapofcaracters.put("mayuscula", 5);//problema
		this.mapofcaracters.put("_", 6);
		this.mapofcaracters.put(".", 7);
		this.mapofcaracters.put("/", 8);
		this.mapofcaracters.put("%", 9);
		this.mapofcaracters.put("'", 10);
		this.mapofcaracters.put("+", 11);
		this.mapofcaracters.put("*", 12);
		this.mapofcaracters.put("{", 13);
		this.mapofcaracters.put("}", 14);
		this.mapofcaracters.put("(", 15);
		this.mapofcaracters.put(")", 16);
		this.mapofcaracters.put(";", 17);
		this.mapofcaracters.put(",", 18);
		this.mapofcaracters.put("-", 19);
		this.mapofcaracters.put("<", 20);
		this.mapofcaracters.put(">", 21);
		this.mapofcaracters.put("=", 22);
		this.mapofcaracters.put("!", 23);
		this.mapofcaracters.put("C", 24);//cualquier caracter del universo//problema
		this.mapofcaracters.put("\t", 25);//problema
		this.mapofcaracters.put("\n", 26);//problema
		this.mapofcaracters.put("$", 27);
		
	
	}
	
	public int getColumn(char a) {
		Character aux;
		String key;

		aux=new Character(a);
			if(aux.isLetter(aux)) {
				if(aux.isUpperCase(aux)) {
					 key="mayuscula";
					 return (int) this.mapofcaracters.get(key);
					}
				else
					if(aux.equals("u") || aux.equals("l") || aux.equals("d") ) {
						key=aux.toString();
						return (int) this.mapofcaracters.get(key);
					}
					else {
						key="minuscula";
						return (int) this.mapofcaracters.get(key);}
			}
			else
				if(aux.isDigit(aux)) {
					key="digito";
					return (int) this.mapofcaracters.get(key);
				}
				else{
					key=aux.toString();
					return (int) this.mapofcaracters.get(key);
				}
			
		}
	
	public State getState(int row,int col) {
		int stateAux=this.matrix[row][col].getNextstate();
		SemanticAction sa=this.matrix[row][col].getSemanticaction();
		State state= new State(stateAux,sa);
		return state;
	}
		
		
	
	
	
}
