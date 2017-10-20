package com.krld.models;


import java.util.ArrayList;
import java.util.List;

import static com.krld.Util.println;

public class Championship {

    public List<Team> teams = new ArrayList<>();
    public List<Match> matches = new ArrayList<>();
    public List<Tour> tours = new ArrayList<>();

    public Championship() {
    }

    public Championship(Championship c) {
        teams = new ArrayList<>(c.teams);
        matches = new ArrayList<>(c.matches);
        tours = new ArrayList<>(c.tours); // dirty
    }

    public void addTeams(int count) {

        String abc = "qwertyuiopasdfghjklzxcvbnm";
        for (int i = 0; i < count; i++) {
            addTeam(new Team(abc.charAt(i) + ""));
        }
    }

    public void printAllTeams() {
        String str = "";
        for (Team team : teams) {
            str += team.name + ", ";
        }
        println(str);
    }

    public void addTeam(Team team) {
        teams.add(team);
    }

    public void createAllMatches() {
        for (Team a : teams) {
            for (Team b : teams) {
                if (a == b) {
                    continue;
                }
                boolean isNew = true;

                for (Match match : matches) {
                    if (match.contains(a, b)) {
                        isNew = false;
                        break;
                    }
                }

                if (isNew) {
                    matches.add(new Match(a, b));
                }
            }
        }
    }

    public void printAllMatches() {
        String s = "";

        for (Match match : matches) {
            s += match + ", ";
        }
        println(s + " qty: " + matches.size());
    }

    public void createTours() {
        tours = new ArrayList<>();

        int tourCount = teams.size() - 1;

        for (int i = 0; i < tourCount; i++) {
            tours.add(new Tour());
        }
    }

    public boolean isValid() {
        int matchesInTour = teams.size() / 2;
        for (Tour tour : tours) {
            if (!tour.isValid() || tour.matches.size() < matchesInTour) {
                return false;
            }
        }
        return true;
    }

    public void printTours() {
        for (Tour tour : tours) {
            tour.printMatches();
        }
    }

    public void sortTours() {
        for (Tour tour : tours) {
            tour.sort();
        }
    }
}
