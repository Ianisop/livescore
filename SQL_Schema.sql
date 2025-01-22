
-- Create Teams Table
CREATE TABLE Teams (
    TeamID INT IDENTITY(1,1) PRIMARY KEY,
    TeamName NVARCHAR(100) NOT NULL
);

-- Create Matches Table
CREATE TABLE Matches (
    MatchID INT IDENTITY(1,1) PRIMARY KEY,
    Team1ID INT,
    Team2ID INT,
    MatchTime DATETIME NOT NULL,
    FOREIGN KEY (Team1ID) REFERENCES Teams(TeamID),
    FOREIGN KEY (Team2ID) REFERENCES Teams(TeamID)
);

-- Create Goals Table
CREATE TABLE Goals (
    GoalID INT IDENTITY(1,1) PRIMARY KEY,
    MatchID INT,
    TeamID INT,
    GoalTime TIME NOT NULL,
    FOREIGN KEY (MatchID) REFERENCES Matches(MatchID),
    FOREIGN KEY (TeamID) REFERENCES Teams(TeamID)
);

-- Create Penalties Table
CREATE TABLE Penalties (
    PenaltyID INT IDENTITY(1,1) PRIMARY KEY,
    MatchID INT,
    TeamID INT,
    PenaltyTime TIME NOT NULL,
    FOREIGN KEY (MatchID) REFERENCES Matches(MatchID),
    FOREIGN KEY (TeamID) REFERENCES Teams(TeamID)
);
