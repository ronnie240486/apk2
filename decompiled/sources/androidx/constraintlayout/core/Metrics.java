package androidx.constraintlayout.core;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class Metrics {
    public long additionalMeasures;
    public long barrierConnectionResolved;
    public long bfs;
    public long centerConnectionResolved;
    public long chainConnectionResolved;
    public long constraints;
    public long determineGroups;
    public long errors;
    public long extravariables;
    public long fullySolved;
    public long graphOptimizer;
    public long graphSolved;
    public long grouping;
    public long infeasibleDetermineGroups;
    public long iterations;
    public long lastTableSize;
    public long layouts;
    public long linearSolved;
    public long matchConnectionResolved;
    public long maxRows;
    public long maxTableSize;
    public long maxVariables;
    public long measuredMatchWidgets;
    public long measuredWidgets;
    public long measures;
    public long measuresLayoutDuration;
    public long measuresWidgetsDuration;
    public long measuresWrap;
    public long measuresWrapInfeasible;
    public long minimize;
    public long minimizeGoal;
    public long nonresolvedWidgets;
    public long oldresolvedWidgets;
    public long optimize;
    public long pivots;
    public ArrayList<String> problematicLayouts = new ArrayList<>();
    public long resolutions;
    public long resolvedWidgets;
    public long simpleconstraints;
    public long slackvariables;
    public long tableSizeIncrease;
    public long variables;
    public long widgets;

    public void reset() {
        this.measures = 0L;
        this.widgets = 0L;
        this.additionalMeasures = 0L;
        this.resolutions = 0L;
        this.tableSizeIncrease = 0L;
        this.maxTableSize = 0L;
        this.lastTableSize = 0L;
        this.maxVariables = 0L;
        this.maxRows = 0L;
        this.minimize = 0L;
        this.minimizeGoal = 0L;
        this.constraints = 0L;
        this.simpleconstraints = 0L;
        this.optimize = 0L;
        this.iterations = 0L;
        this.pivots = 0L;
        this.bfs = 0L;
        this.variables = 0L;
        this.errors = 0L;
        this.slackvariables = 0L;
        this.extravariables = 0L;
        this.fullySolved = 0L;
        this.graphOptimizer = 0L;
        this.graphSolved = 0L;
        this.resolvedWidgets = 0L;
        this.oldresolvedWidgets = 0L;
        this.nonresolvedWidgets = 0L;
        this.centerConnectionResolved = 0L;
        this.matchConnectionResolved = 0L;
        this.chainConnectionResolved = 0L;
        this.barrierConnectionResolved = 0L;
        this.problematicLayouts.clear();
    }

    public String toString() {
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("\n*** Metrics ***\nmeasures: ");
        sbM26m.append(this.measures);
        sbM26m.append("\nmeasuresWrap: ");
        sbM26m.append(this.measuresWrap);
        sbM26m.append("\nmeasuresWrapInfeasible: ");
        sbM26m.append(this.measuresWrapInfeasible);
        sbM26m.append("\ndetermineGroups: ");
        sbM26m.append(this.determineGroups);
        sbM26m.append("\ninfeasibleDetermineGroups: ");
        sbM26m.append(this.infeasibleDetermineGroups);
        sbM26m.append("\ngraphOptimizer: ");
        sbM26m.append(this.graphOptimizer);
        sbM26m.append("\nwidgets: ");
        sbM26m.append(this.widgets);
        sbM26m.append("\ngraphSolved: ");
        sbM26m.append(this.graphSolved);
        sbM26m.append("\nlinearSolved: ");
        sbM26m.append(this.linearSolved);
        sbM26m.append("\n");
        return sbM26m.toString();
    }
}
