package ru.applmath.dbondin.corbatictactoeserver.engine;

public class GameEngine {

	public static enum Cell {
		EMPTY, CROSS, ZERO;
	}

	public static enum WhosMove {
		PLAYER_1, PLAYER_2;
	}

	private static final int[][][] WINNER_COMBINATIONS = { //
			{ { 0, 0 }, { 1, 0 }, { 2, 0 } }, //
			{ { 0, 1 }, { 1, 1 }, { 2, 1 } }, //
			{ { 0, 2 }, { 1, 2 }, { 2, 2 } }, //
			{ { 0, 0 }, { 0, 1 }, { 0, 1 } }, //
			{ { 1, 0 }, { 1, 1 }, { 1, 2 } }, //
			{ { 2, 0 }, { 2, 1 }, { 2, 2 } }, //
			{ { 0, 0 }, { 1, 1 }, { 2, 2 } }, //
			{ { 0, 2 }, { 1, 1 }, { 2, 0 } } //
	};

	private WhosMove whosMove;

	private Cell[][] field;

	public GameEngine() {
		whosMove = WhosMove.PLAYER_1;
		field = new Cell[3][3];
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				field[i][j] = Cell.EMPTY;
			}
		}
	}

	public Cell[][] field() {
		return field;
	}

	public boolean move(int x, int y) {
		if (x < 0 || x >= 3 || y < 0 || y >= 3) {
			return false;
		}
		if (field[x][y] != Cell.EMPTY) {
			return false;
		}
		field[x][y] = (whosMove == WhosMove.PLAYER_1) ? Cell.CROSS : Cell.ZERO;
		whosMove = (whosMove == WhosMove.PLAYER_1) ? WhosMove.PLAYER_2 : WhosMove.PLAYER_1;
		return true;
	}

	public WhosMove whoWin() {
		for (int i = 0; i < WINNER_COMBINATIONS.length; ++i) {
			int p1 = 0;
			int p2 = 0;
			for (int j = 0; j < WINNER_COMBINATIONS[i].length; j++) {
				switch (field[WINNER_COMBINATIONS[i][j][0]][WINNER_COMBINATIONS[i][j][1]]) {
				case CROSS:
					++p1;
					break;
				case ZERO:
					++p2;
					break;
				case EMPTY:
					break;
				default:
					break;
				}
			}
			if (3 == p1) {
				return WhosMove.PLAYER_1;
			} else if (3 == p2) {
				return WhosMove.PLAYER_2;
			}
		}
		return null;
	}
}
