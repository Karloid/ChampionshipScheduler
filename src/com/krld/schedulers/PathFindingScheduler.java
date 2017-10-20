package com.krld.schedulers;

import com.krld.Util;
import com.krld.models.Championship;
import com.krld.models.Match;
import com.krld.models.Tour;

import java.util.ArrayList;
import java.util.List;

public class PathFindingScheduler implements Scheduler {
    private Step currentStep;
    private Championship c;
    private List<Match> allMatches;
    private List<Step> openSteps = new ArrayList<>();
    private List<Step> closedSteps = new ArrayList<>();
    private boolean solved;
    private int maxDeep;

    @Override
    public Championship schedule(Championship c) {
        this.c = c;
        allMatches = new ArrayList<>(c.matches);
        currentStep = new Step(c.tours.get(0), c.matches.get(0), null);

        while (currentStep != null) {
            addToClosed(currentStep);

            if (solved) {
                return c;
            }

            Step nextStep = getNextStep();


            setCurrentStep(nextStep);
        }

        return c;
    }

    private void setCurrentStep(Step nextStep) {
        if (!currentStep.children.contains(nextStep)) {
            //TODO optimize
            Step tmp = currentStep;
            while (tmp != null) {
                undo(tmp);
                tmp = tmp.parent;
            }

            List<Step> parents = nextStep.getAllParents();
            for (int i = 0; i < parents.size(); i++) {
                Step parent = parents.get(i);
                apply(parent);
            }

        }
        currentStep = nextStep;
    }

    private Step getNextStep() {
        Step nextStep = null;

        {
            for (int i = 0; i < openSteps.size(); i++) {
                Step openStep = openSteps.get(i);
                if (nextStep == null) {
                    nextStep = openStep;
                    continue;
                }

                if (nextStep.deep < openStep.deep
                        || (nextStep.deep == openStep.deep
                        && currentStep.children.contains(openStep))) {
                    nextStep = openStep;
                    continue;
                }
            }
        }
        return nextStep;
    }

    private void undo(Step step) {
        step.tour.matches.remove(step.match);
       // println("Undo: " + step.toString());
    }

    private void addToClosed(Step step) {
       // println("Add to closed deep: " + step.deep);
        apply(step);

        if (step.deep > maxDeep) {
            maxDeep = step.deep;
            Util.print("Deepest step" + step.toString());
            Util.println(step.getAllMatches().toString());
        }
        openSteps.remove(step);

        List<Match> usedMatches = step.getAllMatches();

        int delta = usedMatches.size() - allMatches.size();
        if (delta == 0) {
            solved = true;
            return;
        }


        Tour tour = getNextTour(step.tour);


        for (int i = 0; i < allMatches.size(); i++) {
            Match match = allMatches.get(i);
            if (usedMatches.contains(match)) {
                continue;
            }

            if (tour.isValid(match)) {
                Step newStep = step.addChild(tour, match);
                openSteps.add(newStep);
                if (delta == -1) {
                    apply(newStep);
                    solved = true;
                    return;
                }
            }
        }
        //println("Added children count: " + step.children.size());

    }

    private void apply(Step step) {
        step.tour.matches.add(step.match);
        //println("Apply: " + step.toString());
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

    public static class Step {
        public int deep;
        public Tour tour;
        public Match match;
        public Step parent;
        public List<Step> children = new ArrayList<>(0);

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

        public List<Step> getAllParents() {
            ArrayList<Step> steps = new ArrayList<>();
            Step tmp = this.parent;
            while (tmp != null) {
                steps.add(tmp);
                tmp = tmp.parent;
            }
            return steps;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Step{");
            sb.append("deep=").append(deep);
            sb.append("childrenCount=").append(children.size());
            sb.append('}');
            return sb.toString();
        }
    }
}
