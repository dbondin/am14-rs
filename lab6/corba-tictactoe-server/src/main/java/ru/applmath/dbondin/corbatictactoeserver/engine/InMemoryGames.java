package ru.applmath.dbondin.corbatictactoeserver.engine;

import java.util.HashMap;
import java.util.Map;

import ru.applmath.dbondin.corbatictactoeserver.engine.GameEngine.Cell;

public class InMemoryGames {

	private static class GameView {
		public String player1Name = null;
		public String player2Name = null;
		public GameEngine gameEngine = new GameEngine();
	}

	Map<String, GameView> games = new HashMap<String, InMemoryGames.GameView>();

	private String upcomingGame = null;

	public static class JoinStruct {
		public JoinStruct(String key, Cell sign, GameEngine engine) {
			this.key = key;
			this.sign = sign;
			this.engine = engine;
		}

		public String key;
		public Cell sign;
		public GameEngine engine;
	}

	public JoinStruct join(String name) {
		JoinStruct result = null;
		if (name != null) {
			if (upcomingGame == null) {
				GameView gameView = new GameView();
				gameView.player1Name = name;
				upcomingGame = generateNewGameKey();
				result = new JoinStruct(upcomingGame, Cell.CROSS, gameView.gameEngine);
			} else {
				GameView gameView = games.get(upcomingGame);
				if (gameView != null) {
					if (!name.equals(gameView.player1Name)) {
						gameView.player2Name = name;
						result = new JoinStruct(upcomingGame, Cell.ZERO, gameView.gameEngine);
						upcomingGame = null;
					}
				}
			}
		}
		return result;
	}

	private String generateNewGameKey() {
		String s = null;
		while (s != null) {
			s = "GAME-" + Math.abs(this.hashCode()) + "-" + ((int) (Math.random() * Integer.MAX_VALUE));
			if (games.keySet().contains(s)) {
				s = null;
			}
		}
		return s;
	}
}
