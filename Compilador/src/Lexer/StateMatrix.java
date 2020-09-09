package Lexer;

import java.util.HashMap;
import semantic_Actions.*;

public class StateMatrix {
	private State matrix[][];
	private static final int ROW = 20;
	private static final int COLUMN =  28;
	private HashMap mapofcaracters;
	
	public StateMatrix () {
		
		State matrix[][] = new State[ROW][COLUMN];
		
		mapofcaracters = new HashMap();
		
		for(int i = 0; i < ROW; i++) {
			for(int j = 0; j < COLUMN; j++) {
				if( i == 0 || i == 1 || i == 5 || i == 6 || i == 9 || i == 11 || i == 12 || i == 16 
					|| i == 17 || i == 18 || i == 19 )
					matrix[i][j] = new State(-1);
				if(i == 2 || i == 3 || i == 4 || i == 7 || i == 8 || i == 10)
					matrix[i][j] = new State(0);
				if(i == 13 || i == 14)
					matrix[i][j] = new State(13);
				if(i == 15)
					matrix[i][j] = new State(15);
			}
		}
	}
	
	public void cargar() {
		//Fila 0
		matrix[0][0].setState(1);
		matrix[0][1].setState(2);
		matrix[0][2].setState(1);
		matrix[0][3].setState(1);
		matrix[0][4].setState(1);
		matrix[0][5].setState(10);
		matrix[0][6].setState(0);
		matrix[0][7].setState(6);
		matrix[0][8].setState(12);
		matrix[0][9].setState(0);
		matrix[0][10].setState(15);
		matrix[0][20].setState(16);
		matrix[0][21].setState(17);
		matrix[0][22].setState(18);
		matrix[0][23].setState(19);
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
		matrix[2][7].setState(6);
		matrix[2][27].setState(-1);
		
		//Fila 3		
		matrix[3][3].setState(4);
		matrix[3][27].setState(-1);
		
		//Fila 4
		matrix[4][4].setState(5);
		matrix[4][27].setState(-1);
		
		//Fila 5 ya cargada toda con estado final
		
		//Fila 6		
		matrix[6][1].setState(6);
		matrix[6][2].setState(7);
		
		//Fila 7
		matrix[7][11].setState(8);
		matrix[7][19].setState(8);
		matrix[7][27].setState(-1);
		
		//Fila 8
		matrix[8][1].setState(9);
		matrix[8][27].setState(-1);
		
		//Fila 9
		matrix[9][1].setState(9);
		
		//Fila 10
		matrix[10][5].setState(10);
		matrix[10][6].setState(11); 
		matrix[10][27].setState(-1);
		
		//Fila 11
		matrix[11][5].setState(11);
		
		//Fila 12
		matrix[12][9].setState(13);
		
		//Fila 13
		matrix[13][9].setState(14);
		matrix[13][27].setState(-1);
		
		//Fila 14
		matrix[14][8].setState(0);
		matrix[14][9].setState(14);
		matrix[14][27].setState(-1);
		
		//Fila 15
		matrix[15][10].setState(-1);
		matrix[15][26].setState(0);
		matrix[15][27].setState(-1);
		

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
		
		/*ERR_ID errId = new ERR_ID();
		ERR_CTN errCte = new ERR_CTN();
		ERR_COM errCom = new ERR_COM();
		ERR_CHAR errChar = new ERR_CHAR();*/
		
		for(int i = 0; i < ROW; i++) {
			for(int j = 0; j < COLUMN - 1; j++) {
				if(i == 0)
					matrix[i][j].setSemanticAction(sa3);
				if(i == 1)
					matrix[i][j].setSemanticAction(sa5);
				if(i == 2 || i == 3 || i == 4 || i == 7 || i == 8 || i == 10)
					matrix[i][j].setSemanticAction(sa1);
				if(i == 5)
					matrix[i][j].setSemanticAction(sa6);
				if(i == 6)
					matrix[i][j].setSemanticAction(sa13);
				if(i == 9)
					matrix[i][j].setSemanticAction(sa10);
				if(i == 11)
					matrix[i][j].setSemanticAction(sa7);
				if(i == 12 || i == 16 || i == 17 || i ==18 || i == 19)
					matrix[i][j].setSemanticAction(sa11);
				if(i == 13 || i == 14 || i == 15)
					matrix[i][j].setSemanticAction(sa4);
			}
		}
		
		//Fila 0
		matrix[0][9].setSemanticAction(sa1);
		for(int j = 11; j <= 19; j++) {
			matrix[0][j].setSemanticAction(null);
		}
		matrix[0][24].setSemanticAction(sa1);
		matrix[0][25].setSemanticAction(sa2);
		matrix[0][26].setSemanticAction(sa2);
		
		//Fila 1
		for(int j = 0; j <= 4; j++) {
			matrix[1][j].setSemanticAction(sa4);
		}
		matrix[1][6].setSemanticAction(sa4);
		
		//Fila 2
		matrix[2][1].setSemanticAction(sa4);
		matrix[2][6].setSemanticAction(sa9); 
		matrix[2][7].setSemanticAction(sa10);
		
		//Fila 3		
		matrix[3][3].setSemanticAction(sa4);
		
		//Fila 4
		matrix[4][4].setSemanticAction(sa4); 
		
		//Fila 5 ya cargada
		
		//Fila 6
		matrix[6][1].setSemanticAction(sa4); 
		matrix[6][2].setSemanticAction(sa4); 
		
		//Fila 7
		matrix[7][11].setSemanticAction(sa4);
		matrix[7][19].setSemanticAction(sa4);
		
		//Fila 8
		matrix[8][1].setSemanticAction(sa4);
		
		//Fila 9
		matrix[9][1].setSemanticAction(sa4);
		
		//Fila 10
		matrix[10][5].setSemanticAction(sa5);
		
		//Fila 11
		matrix[11][5].setSemanticAction(sa4);
		
		//Fila 12
		matrix[12][9].setSemanticAction(sa4);
		
		//Fila 13 ya cargada
		
		//Fila 14
		matrix[14][8].setSemanticAction(sa8);
		
		//Fila 15
		matrix[15][18].setSemanticAction(sa6); 
		matrix[15][26].setSemanticAction(sa1);
		
		
		//Fila 16
		for(int i = 16; i < ROW; i++) {
			matrix[i][22].setSemanticAction(sa12);
		}
		for(int i = 0; i < ROW; i++) {
			matrix[i][27].setSemanticAction(null);
		}
	}
	
}