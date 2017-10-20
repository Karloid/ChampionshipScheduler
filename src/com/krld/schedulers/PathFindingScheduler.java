package com.krld.schedulers;

import com.krld.models.Championship;
import com.krld.models.Match;
import com.krld.models.Tour;

import java.util.ArrayList;
import java.util.List;

public class PathFindingScheduler implements Scheduler {
    private Step currentStep;
    private Championship c;
    private List<Match> allMatches;

    @Override
    public Championship schedule(Championship c) {
        this.c = c;
        allMatches = new ArrayList<>(c.matches);
        currentStep = new Step(c.tours.get(0), c.matches.get(0), null);
        addToClosed(currentStep);

        return c;
    }

    private void addToClosed(Step step) {
        Tour tour = getNextTour(step.tour);

        List<Match> usedMatches = step.getAllMatches();

        for (int i = 0; i < allMatches.size(); i++) {
            Match match = allMatches.get(i);
            if (usedMatches.contains(match)) {
                continue;
            }

            if (tour.isValid(match)) {
                step.addChild(tour, match);
            }
        }

    }

    private Tour getNextTour(Tour tour) {
        int foundI = -1;
        for (int i = 0; i < c.tours.size(); i++) {
            if (c.tours.get(i) == tour) {
                foundI = i;
                break;
            }
        }

        foundI++;
        if (foundI == c.tours.size()) {
            foundI = 0;
        }

        return c.tours.get(foundI);
    }

    @Override
    public int getIterations() {
        return 0;
    }

    @Override
    public String getName() {
        return "PathFinding";
    }

    private static class Step {
        private int deep;
        Tour tour;
        Match match;
        Step parent;
        List<Step> children = new ArrayList<>(0);

        public Step(Tour tour, Match match, Step parent) {
            this.tour = tour;
            this.match = match;
            this.parent = parent;

            if (parent == null) {
                deep = 0;
            } else {
                deep = parent.deep + 1;
            }
        }

        public List<Match> getAllMatches() {
            List<Match> o = new ArrayList<>();
            Step current = this;
            while (current != null) {
                o.add(current.match);
                current = current.parent;
            }

            return o;
        }

        public Step addChild(Tour tour, Match match) {
            Step child = new Step(tour, match, this);
            children.add(child);
            return child;
        }
    }
}
