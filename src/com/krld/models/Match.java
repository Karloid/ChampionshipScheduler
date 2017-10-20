package com.krld.models;

public class Match {
    public Team t1;
    public Team t2;

    public Match(Team a, Team b) {
        t1 = a;
        t2 = b;
    }

    public boolean contains(Team a, Team b) {
        return (a == t1 && b == t2) || (a == t2 && b == t1);
    }

    @Override
    public String toString() {
        return t1.name + t2.name;
    }

    public boolean contains(Match match) {
        return t1 == match.t1 || t2 == match.t1 || t1 == match.t2 || t2 == match.t2;
    }

    public int countTeams(Match match) {
        int i = 0;
        if (match.t1 == t1 || match.t1 == t2) {
            i++;
        }

        if (match.t2 == t1 || match.t2 == t2) {
            i++;
        }

        return i;
    }
}
