package Lexer;

import semantic_Actions.*;

public class StateMatrix {
	private State matrix[][];
	private static final int ROW = 19;
	private static final int COLUMN =  28;
	
	public StateMatrix () {
		State matrix[][] = new State[ROW][COLUMN];
		
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
		SemanticAction as8 = new SemanticAction8();
		SemanticAction as9 = new SemanticAction9();
		SemanticAction sa10 = new SemanticAction10();
		SemanticAction sa11 = new SemanticAction11();
		SemanticAction sa12 = new SemanticAction12();
		SemanticAction sa13 = new SemanticAction13();
		
		/*ERR_ID errId = new ERR_ID();
		ERR_CTN errCte = new ERR_CTN();
		ERR_COM errCom = new ERR_COM();
		ERR_CHAR errChar = new ERR_CHAR();*/
		
		for(int i = 0; i < ROW; i++) {
			for(int j = 0; j < COLUMN; j++) {
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
		matrix[0][]
		matrix[0][] = as0;
		matrix[0][] = as0;
		matrix[0][] = as0;
		
		//Fila 1
		matrix[0][4] = as0;
		matrix[0][5] = as0;
		matrix[0][6] = as6;
		matrix[0][7] = as6;
		matrix[0][8] = as6;
		matrix[0][9] = as6;
		matrix[0][10] = as0;
		matrix[0][11] = as6;
		matrix[0][12] = as0;
		matrix[0][13] = as0;
		matrix[0][14] = as0;
		matrix[0][15] = as4;
		matrix[0][16] = as4;
		matrix[0][17] = as6;
		matrix[0][18] = as6;
		matrix[0][19] = as6;
		matrix[0][20] = as6;
		matrix[0][21] = as6;
		matrix[0][22] = as6;
		matrix[0][23] = as4;
		matrix[0][24] = as4;
		matrix[0][25] = errChar;
		matrix[0][26] = as4;
		matrix[0][27] = as6;
		matrix[1][0] = errId;
		matrix[1][1] = as0;
		matrix[1][2] = as0;
		matrix[1][3] = as0;
		matrix[1][4] = as0;
		matrix[1][5] = as0;
		matrix[1][6] = errId;
		matrix[1][7] = errId;
		matrix[1][8] = errId;
		matrix[1][9] = errId;
		matrix[1][10] = errId;
		matrix[1][11] = errId;
		matrix[1][12] = errId;
		matrix[1][13] = errId;
		matrix[1][14] = errId;
		matrix[1][15] = errId;
		matrix[1][16] = errId;
		matrix[1][17] = errId;
		matrix[1][18] = errId;
		matrix[1][19] = errId;
		matrix[1][20] = errId;
		matrix[1][21] = errId;
		matrix[1][22] = errId;
		matrix[1][23] = errId;
		matrix[1][24] = errId;
		matrix[1][25] = errChar;
		matrix[1][26] = errId;
		matrix[1][27] = errId;
		matrix[2][0] = as1;
		matrix[2][1] = as0;
		matrix[2][2] = as0;
		matrix[2][3] = as0;
		matrix[2][4] = as0;
		matrix[2][5] = as0;
		matrix[2][6] = as1;
		matrix[2][7] = as1;
		matrix[2][8] = as1;
		matrix[2][9] = as1;
		matrix[2][10] = as1;
		matrix[2][11] = as1;
		matrix[2][12] = as1;
		matrix[2][13] = as1;
		matrix[2][14] = as1;
		matrix[2][15] = as1;
		matrix[2][16] = as1;
		matrix[2][17] = as1;
		matrix[2][18] = as1;
		matrix[2][19] = as1;
		matrix[2][20] = as1;
		matrix[2][21] = as1;
		matrix[2][22] = as1;
		matrix[2][23] = as1;
		matrix[2][24] = as1;
		matrix[2][25] = as1;
		matrix[2][26] = as1;
		matrix[2][27] = as1;
		matrix[3][0] = as0;
		matrix[3][1] = errCte;
		matrix[3][2] = errCte;
		matrix[3][3] = errCte;
		matrix[3][4] = errCte;
		matrix[3][5] = as0;
		matrix[3][6] = errCte;
		matrix[3][7] = errCte;
		matrix[3][8] = errCte;
		matrix[3][9] = errCte;
		matrix[3][10] = errCte;
		matrix[3][11] = errCte;
		matrix[3][12] = errCte;
		matrix[3][13] = errCte;
		matrix[3][14] = errCte;
		matrix[3][15] = errCte;
		matrix[3][16] = errCte;
		matrix[3][17] = errCte;
		matrix[3][18] = errCte;
		matrix[3][19] = errCte;
		matrix[3][20] = errCte;
		matrix[3][21] = errCte;
		matrix[3][22] = errCte;
		matrix[3][23] = errCte;
		matrix[3][24] = errCte;
		matrix[3][25] = errChar;
		matrix[3][26] = errCte;
		matrix[3][27] = errCte;
		matrix[4][0] = errCte;
		matrix[4][1] = errCte;
		matrix[4][2] = as0;
		matrix[4][3] = errCte;
		matrix[4][4] = as2;
		matrix[4][5] = errCte;
		matrix[4][6] = errCte;
		matrix[4][7] = errCte;
		matrix[4][8] = errCte;
		matrix[4][9] = errCte;
		matrix[4][10] = errCte;
		matrix[4][11] = errCte;
		matrix[4][12] = errCte;
		matrix[4][13] = errCte;
		matrix[4][14] = errCte;
		matrix[4][15] = errCte;
		matrix[4][16] = errCte;
		matrix[4][17] = errCte;
		matrix[4][18] = errCte;
		matrix[4][19] = errCte;
		matrix[4][20] = errCte;
		matrix[4][21] = errCte;
		matrix[4][22] = errCte;
		matrix[4][23] = errCte;
		matrix[4][24] = errCte;
		matrix[4][25] = errChar;
		matrix[4][26] = errCte;
		matrix[4][27] = errCte;
		matrix[5][0] = errCte;
		matrix[5][1] = errCte;
		matrix[5][2] = errCte;
		matrix[5][3] = as2;
		matrix[5][4] = errCte;
		matrix[5][5] = errCte;
		matrix[5][6] = errCte;
		matrix[5][7] = errCte;
		matrix[5][8] = errCte;
		matrix[5][9] = errCte;
		matrix[5][10] = errCte;
		matrix[5][11] = errCte;
		matrix[5][12] = errCte;
		matrix[5][13] = errCte;
		matrix[5][14] = errCte;
		matrix[5][15] = errCte;
		matrix[5][16] = errCte;
		matrix[5][17] = errCte;
		matrix[5][18] = errCte;
		matrix[5][19] = errCte;
		matrix[5][20] = errCte;
		matrix[5][21] = errCte;
		matrix[5][22] = errCte;
		matrix[5][23] = errCte;
		matrix[5][24] = errCte;
		matrix[5][25] = errChar;
		matrix[5][26] = errCte;
		matrix[5][27] = errCte;
		matrix[6][0] = as3;
		matrix[6][1] = as0;
		matrix[6][2] = as0;
		matrix[6][3] = as0;
		matrix[6][4] = as0;
		matrix[6][5] = as3;
		matrix[6][6] = as3;
		matrix[6][7] = as3;
		matrix[6][8] = as3;
		matrix[6][9] = as3;
		matrix[6][10] = as3;
		matrix[6][11] = as3;
		matrix[6][12] = as3;
		matrix[6][13] = as3;
		matrix[6][14] = as3;
		matrix[6][15] = as3;
		matrix[6][16] = as3;
		matrix[6][17] = as3;
		matrix[6][18] = as3;
		matrix[6][19] = as3;
		matrix[6][20] = as3;
		matrix[6][21] = as3;
		matrix[6][22] = as3;
		matrix[6][23] = as3;
		matrix[6][24] = as3;
		matrix[6][25] = as3;
		matrix[6][26] = as3;
		matrix[6][27] = as3;
		matrix[7][0] = as4;
		matrix[7][1] = as4;
		matrix[7][2] = as4;
		matrix[7][3] = as4;
		matrix[7][4] = as4;
		matrix[7][5] = as4;
		matrix[7][6] = as4;
		matrix[7][7] = as4;
		matrix[7][8] = as4;
		matrix[7][9] = as4;
		matrix[7][10] = as4;
		matrix[7][11] = as4;
		matrix[7][12] = as4;
		matrix[7][13] = as4;
		matrix[7][14] = as4;
		matrix[7][15] = as4;
		matrix[7][16] = as4;
		matrix[7][17] = as4;
		matrix[7][18] = as4;
		matrix[7][19] = as4;
		matrix[7][20] = as4;
		matrix[7][21] = as4;
		matrix[7][22] = as4;
		matrix[7][23] = as4;
		matrix[7][24] = as4;
		matrix[7][25] = as4;
		matrix[7][26] = as4;
		matrix[7][27] = errCom;
		matrix[8][0] = as3;
		matrix[8][1] = as3;
		matrix[8][2] = as3;
		matrix[8][3] = as3;
		matrix[8][4] = as3;
		matrix[8][5] = as3;
		matrix[8][6] = as3;
		matrix[8][7] = as3;
		matrix[8][8] = as3;
		matrix[8][9] = as3;
		matrix[8][10] = as3;
		matrix[8][11] = as0;
		matrix[8][12] = as3;
		matrix[8][13] = as3;
		matrix[8][14] = as3;
		matrix[8][15] = as3;
		matrix[8][16] = as3;
		matrix[8][17] = as3;
		matrix[8][18] = as3;
		matrix[8][19] = as3;
		matrix[8][20] = as3;
		matrix[8][21] = as3;
		matrix[8][22] = as3;
		matrix[8][23] = as3;
		matrix[8][24] = as3;
		matrix[8][25] = as3;
		matrix[8][26] = as3;
		matrix[8][27] = as3;
		matrix[9][0] = as3;
		matrix[9][1] = as3;
		matrix[9][2] = as3;
		matrix[9][3] = as3;
		matrix[9][4] = as3;
		matrix[9][5] = as3;
		matrix[9][6] = as3;
		matrix[9][7] = as3;
		matrix[9][8] = as3;
		matrix[9][9] = as3;
		matrix[9][10] = as3;
		matrix[9][11] = as0;
		matrix[9][12] = as3;
		matrix[9][13] = as3;
		matrix[9][14] = as3;
		matrix[9][15] = as3;
		matrix[9][16] = as3;
		matrix[9][17] = as3;
		matrix[9][18] = as3;
		matrix[9][19] = as3;
		matrix[9][20] = as3;
		matrix[9][21] = as3;
		matrix[9][22] = as3;
		matrix[9][23] = as3;
		matrix[9][24] = as3;
		matrix[9][25] = as3;
		matrix[9][26] = as3;
		matrix[9][27] = as3;
		matrix[10][0] = as3;
		matrix[10][1] = as3;
		matrix[10][2] = as3;
		matrix[10][3] = as3;
		matrix[10][4] = as3;
		matrix[10][5] = as3;
		matrix[10][6] = as3;
		matrix[10][7] = as3;
		matrix[10][8] = as3;
		matrix[10][9] = as3;
		matrix[10][10] = as3;
		matrix[10][11] = as0;
		matrix[10][12] = as3;
		matrix[10][13] = as3;
		matrix[10][14] = as3;
		matrix[10][15] = as3;
		matrix[10][16] = as3;
		matrix[10][17] = as3;
		matrix[10][18] = as3;
		matrix[10][19] = as3;
		matrix[10][20] = as3;
		matrix[10][21] = as3;
		matrix[10][22] = as3;
		matrix[10][23] = as3;
		matrix[10][24] = as3;
		matrix[10][25] = as3;
		matrix[10][26] = as3;
		matrix[10][27] = as3;
		matrix[11][0] = errChar;
		matrix[11][1] = errChar;
		matrix[11][2] = errChar;
		matrix[11][3] = errChar;
		matrix[11][4] = errChar;
		matrix[11][5] = errChar;
		matrix[11][6] = errChar;
		matrix[11][7] = errChar;
		matrix[11][8] = errChar;
		matrix[11][9] = errChar;
		matrix[11][10] = errChar;
		matrix[11][11] = as0;
		matrix[11][12] = errChar;
		matrix[11][13] = errChar;
		matrix[11][14] = errChar;
		matrix[11][15] = errChar;
		matrix[11][16] = errChar;
		matrix[11][17] = errChar;
		matrix[11][18] = errChar;
		matrix[11][19] = errChar;
		matrix[11][20] = errChar;
		matrix[11][21] = errChar;
		matrix[11][22] = errChar;
		matrix[11][23] = errChar;
		matrix[11][24] = errChar;
		matrix[11][25] = errChar;
		matrix[11][26] = errChar;
		matrix[11][27] = errChar;
		matrix[12][0] = errChar;
		matrix[12][1] = errChar;
		matrix[12][2] = errChar;
		matrix[12][3] = errChar;
		matrix[12][4] = errChar;
		matrix[12][5] = errChar;
		matrix[12][6] = errChar;
		matrix[12][7] = errChar;
		matrix[12][8] = errChar;
		matrix[12][9] = errChar;
		matrix[12][10] = errChar;
		matrix[12][11] = as0;
		matrix[12][12] = errChar;
		matrix[12][13] = errChar;
		matrix[12][14] = errChar;
		matrix[12][15] = errChar;
		matrix[12][16] = errChar;
		matrix[12][17] = errChar;
		matrix[12][18] = errChar;
		matrix[12][19] = errChar;
		matrix[12][20] = errChar;
		matrix[12][21] = errChar;
		matrix[12][22] = errChar;
		matrix[12][23] = errChar;
		matrix[12][24] = errChar;
		matrix[12][25] = errChar;
		matrix[12][26] = errChar;
		matrix[12][27] = errChar;
		matrix[13][0] = as0;
		matrix[13][1] = as0;
		matrix[13][2] = as0;
		matrix[13][3] = as0;
		matrix[13][4] = as0;
		matrix[13][5] = as0;
		matrix[13][6] = as0;
		matrix[13][7] = as0;
		matrix[13][8] = as0;
		matrix[13][9] = as0;
		matrix[13][10] = as0;
		matrix[13][11] = as0;
		matrix[13][12] = as0;
		matrix[13][13] = as0;
		matrix[13][14] = as0;
		matrix[13][15] = as0;
		matrix[13][16] = as5;
		matrix[13][17] = as0;
		matrix[13][18] = as0;
		matrix[13][19] = as0;
		matrix[13][20] = as0;
		matrix[13][21] = as0;
		matrix[13][22] = as0;
		matrix[13][23] = errCte;
		matrix[13][24] = as0;
		matrix[13][25] = as0;
		matrix[13][26] = as0;
		matrix[13][27] = errCte;
	}
	
	

}
