package lol.plucky.hideScoreboard.client;

public class ScoreboardState {
    private static boolean scoreboardVisible = true;
    public static boolean isScoreboardVisible() { return scoreboardVisible; }
    public static void toggleScoreboard() { scoreboardVisible = !scoreboardVisible; }
}
