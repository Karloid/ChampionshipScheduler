package com.krld;

import java.util.ArrayList;
import java.util.List;

public class Tour {
    public List<Match> matches = new ArrayList<>();

    public boolean isValid() {
        for (int i = 0; i < matches.size(); i++) {
            for (int j = i + 1; j < matches.size(); j++) {
                if (matches.get(i).contains(matches.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public void printMatches() {
        for (Match match : matches) {
            Util.print(match.toString() + ", ");
        }
        Util.println("");
    }
}
