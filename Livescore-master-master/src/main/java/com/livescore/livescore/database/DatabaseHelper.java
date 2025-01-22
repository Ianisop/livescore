package com.livescore.livescore;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {

    public static void addTeam(String teamName) throws SQLException {
        String query = "INSERT INTO Teams (TeamName) VALUES (?)";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, teamName);
            stmt.executeUpdate();
        }
    }

    public static ObservableList<String> getTeamNames() throws SQLException {
        String query = "SELECT TeamName FROM Teams";
        ObservableList<String> teamNames = FXCollections.observableArrayList();
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                teamNames.add(rs.getString("TeamName"));
            }
        }
        return teamNames;
    }

    public static ObservableList<Team> getTeams() throws SQLException {
        String query = "SELECT TeamName FROM Teams";
        ObservableList<Team> teams = FXCollections.observableArrayList();
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                teams.add(new Team(rs.getString("TeamName")));
            }
        }
        return teams;
    }

    public static int addMatch(String homeTeam, String awayTeam, String matchDate, String matchTime) throws SQLException {
        String getTeamIdQuery = "SELECT TeamID FROM Teams WHERE TeamName = ?";
        String insertMatchQuery = "INSERT INTO Matches (HomeTeamID, AwayTeamID, MatchDate, MatchTime) VALUES (?, ?, ?, ?)";
        int matchID = -1;
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement getHomeTeamStmt = connection.prepareStatement(getTeamIdQuery);
             PreparedStatement getAwayTeamStmt = connection.prepareStatement(getTeamIdQuery);
             PreparedStatement insertMatchStmt = connection.prepareStatement(insertMatchQuery, Statement.RETURN_GENERATED_KEYS)) {
            getHomeTeamStmt.setString(1, homeTeam);
            ResultSet homeTeamRs = getHomeTeamStmt.executeQuery();
            if (!homeTeamRs.next()) {
                throw new SQLException("Home team not found: " + homeTeam);
            }
            int homeTeamID = homeTeamRs.getInt("TeamID");

            getAwayTeamStmt.setString(1, awayTeam);
            ResultSet awayTeamRs = getAwayTeamStmt.executeQuery();
            if (!awayTeamRs.next()) {
                throw new SQLException("Away team not found: " + awayTeam);
            }
            int awayTeamID = awayTeamRs.getInt("TeamID");

            insertMatchStmt.setInt(1, homeTeamID);
            insertMatchStmt.setInt(2, awayTeamID);
            insertMatchStmt.setString(3, matchDate);
            insertMatchStmt.setString(4, matchTime);
            insertMatchStmt.executeUpdate();

            ResultSet generatedKeys = insertMatchStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                matchID = generatedKeys.getInt(1);
            }
        }
        return matchID;
    }

    public static void recordGoal(int matchID, int teamID, String goalTime) throws SQLException {
        String query = "INSERT INTO Goals (MatchID, TeamID, GoalTime) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, matchID);
            statement.setInt(2, teamID);
            statement.setString(3, goalTime);
            statement.executeUpdate();
        }
    }

    public static void deleteMostRecentGoal(int matchID, int teamID) throws SQLException {
        String query = "DELETE FROM Goals WHERE GoalID = (SELECT TOP 1 GoalID FROM Goals WHERE MatchID = ? AND TeamID = ? ORDER BY GoalTime DESC)";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, matchID);
            statement.setInt(2, teamID);
            statement.executeUpdate();
        }
    }

    public static void recordPenalty(int matchID, int teamID, String penaltyTime) throws SQLException {
        String query = "INSERT INTO Penalties (MatchID, TeamID, PenaltyTime) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, matchID);
            statement.setInt(2, teamID);
            statement.setString(3, penaltyTime);
            statement.executeUpdate();
        }
    }

    public static void recordFreeThrow(int matchID, int teamID, String freeThrowTime) throws SQLException {
        String query = "INSERT INTO FreeThrows (MatchID, TeamID, FreeThrowTime) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, matchID);
            statement.setInt(2, teamID);
            statement.setString(3, freeThrowTime);
            statement.executeUpdate();
        }
    }

    public static void updateStandings(int homeTeamID, int awayTeamID, int homeScore, int awayScore) throws SQLException {
        String updatePlayedQuery = "UPDATE Standings SET Played = Played + 1 WHERE TeamID = ? OR TeamID = ?";
        String updateWinQuery = "UPDATE Standings SET Wins = Wins + 1, Points = Points + 3 WHERE TeamID = ?";
        String updateLossQuery = "UPDATE Standings SET Losses = Losses + 1 WHERE TeamID = ?";
        String updateDrawQuery = "UPDATE Standings SET Draws = Draws + 1, Points = Points + 1 WHERE TeamID = ?";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement updatePlayedStmt = connection.prepareStatement(updatePlayedQuery);
             PreparedStatement updateWinStmt = connection.prepareStatement(updateWinQuery);
             PreparedStatement updateLossStmt = connection.prepareStatement(updateLossQuery);
             PreparedStatement updateDrawStmt = connection.prepareStatement(updateDrawQuery)) {
            updatePlayedStmt.setInt(1, homeTeamID);
            updatePlayedStmt.setInt(2, awayTeamID);
            updatePlayedStmt.executeUpdate();

            if (homeScore > awayScore) {
                updateWinStmt.setInt(1, homeTeamID);
                updateWinStmt.executeUpdate();
                updateLossStmt.setInt(1, awayTeamID);
                updateLossStmt.executeUpdate();
            } else if (homeScore < awayScore) {
                updateWinStmt.setInt(1, awayTeamID);
                updateWinStmt.executeUpdate();
                updateLossStmt.setInt(1, homeTeamID);
                updateLossStmt.executeUpdate();
            } else {
                updateDrawStmt.setInt(1, homeTeamID);
                updateDrawStmt.executeUpdate();
                updateDrawStmt.setInt(2, awayTeamID);
                updateDrawStmt.executeUpdate();
            }
        }
    }

    public static int getTeamID(String teamName) throws SQLException {
        String query = "SELECT TeamID FROM Teams WHERE TeamName = ?";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, teamName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("TeamID");
            } else {
                throw new SQLException("Team not found: " + teamName);
            }
        }
    }

    public static void addTeamToStandings(int teamID) throws SQLException {
        String query = "INSERT INTO Standings (TeamID, Played, Wins, Losses, Draws, Points) VALUES (?, 0, 0, 0, 0, 0)";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, teamID);
            stmt.executeUpdate();
        }
    }

    public static ObservableList<TeamStanding> getLeagueStandings() throws SQLException {
        String query = "SELECT s.TeamID, t.TeamName, s.Played, s.Wins, s.Losses, s.Draws, s.Points " +
                       "FROM Standings s JOIN Teams t ON s.TeamID = t.TeamID ORDER BY s.Points DESC";
        ObservableList<TeamStanding> standings = FXCollections.observableArrayList();
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            int rank = 1;
            while (rs.next()) {
                standings.add(new TeamStanding(
                        rank++,
                        rs.getInt("TeamID"),
                        rs.getString("TeamName"),
                        rs.getInt("Played"),
                        rs.getInt("Wins"),
                        rs.getInt("Losses"),
                        rs.getInt("Draws"),
                        rs.getInt("Points")
                ));
            }
        }
        return standings;
    }

    public static ObservableList<Match> getMatches() throws SQLException {
        String query = "SELECT MatchID, HomeTeamID, AwayTeamID FROM Matches";
        ObservableList<Match> matches = FXCollections.observableArrayList();
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int homeTeamID = rs.getInt("HomeTeamID");
                int awayTeamID = rs.getInt("AwayTeamID");
                String homeTeamName = getTeamNameByID(homeTeamID);
                String awayTeamName = getTeamNameByID(awayTeamID);
                matches.add(new Match(rs.getInt("MatchID"), homeTeamName + " Vs. " + awayTeamName));
            }
        }
        return matches;
    }

    private static String getTeamNameByID(int teamID) throws SQLException {
        String query = "SELECT TeamName FROM Teams WHERE TeamID = ?";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, teamID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("TeamName");
            } else {
                throw new SQLException("Team not found for ID: " + teamID);
            }
        }
    }

    public static void deleteGoalsByMatchID(int matchID) throws SQLException {
        String query = "DELETE FROM Goals WHERE MatchID = ?";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, matchID);
            stmt.executeUpdate();
        }
    }

    public static void deletePenaltiesByMatchID(int matchID) throws SQLException {
        String query = "DELETE FROM Penalties WHERE MatchID = ?";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, matchID);
            stmt.executeUpdate();
        }
    }

    public static void deleteFreeThrowsByMatchID(int matchID) throws SQLException {
        String query = "DELETE FROM FreeThrows WHERE MatchID = ?";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, matchID);
            stmt.executeUpdate();
        }
    }

    public static void deleteMatch(int matchID) throws SQLException {
        deleteGoalsByMatchID(matchID);
        deletePenaltiesByMatchID(matchID);
        deleteFreeThrowsByMatchID(matchID);
        String query = "DELETE FROM Matches WHERE MatchID = ?";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, matchID);
            stmt.executeUpdate();
        }
    }

    public static void deleteTeam(String teamName) throws SQLException {
        int teamID = getTeamID(teamName);
        deleteTeamStandings(teamID);
        String query = "DELETE FROM Teams WHERE TeamName = ?";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, teamName);
            stmt.executeUpdate();
        }
    }

    private static void deleteTeamStandings(int teamID) throws SQLException {
        String query = "DELETE FROM Standings WHERE TeamID = ?";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, teamID);
            stmt.executeUpdate();
        }
    }
}