package com.krld.models;

import com.krld.Util;

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

    public boolean isValid(Match fm) {
        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < matches.size(); i++) {
                if (matches.get(i).contains(fm)) {
                    return false;
            }
        }
        return true;
    }

    public void sort() {
        //noinspection ComparatorCombinators
        matches.sort((o1, o2) -> o1.toString().compareTo(o2.toString()));
    }
}
