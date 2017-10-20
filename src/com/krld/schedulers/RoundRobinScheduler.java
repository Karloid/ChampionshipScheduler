package com.krld.schedulers;

import com.krld.Util;
import com.krld.models.Championship;
import com.krld.models.Match;
import com.krld.models.Team;

import java.util.Collections;
import java.util.List;

public class RoundRobinScheduler implements Scheduler {

    private int iter;

    @Override
    public Championship schedule(Championship c) {

        List<Team> teams = c.teams;
        Util.println("teams: " + teams);

        List<Team> shiftArea = teams.subList(1, teams.size());

        int half = teams.size() / 2;
        List<Team> teamA = teams.subList(0, half);
        List<Team> teamB = teams.subList(half, teams.size());

        Util.println("teamA " + teamA + "\nteamB " + teamB);

        int i = 0;
        while (i < teams.size() - 1) {

            Util.println("teams: " + teams);
            Util.println("teamA " + teamA + "\nteamB " + teamB);

            for (int j = 0; j < half; j++) {

                Team a = teamA.get(j);
                Team b = teamB.get(teamB.size() - j - 1);
                for (Match match : c.matches) {
                    if (match.contains(a, b)) {
                        c.tours.get(i).matches.add(match);
                        Util.print(", " + match);
                        break;
                    }
                }
            }
            Util.println("");

            Collections.rotate(shiftArea, 1);
            i++;
        }

        return c;
    }

    @Override
    public int getIterations() {
        return iter;
    }

    @Override
    public String getName() {
        return "Round Robin";
    }
}
