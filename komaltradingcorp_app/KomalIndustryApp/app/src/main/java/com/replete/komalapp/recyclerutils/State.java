package com.replete.komalapp.recyclerutils;

/**
 * Created by MR JOSHI on 14-Jul-16.
 */
public class State {
    private int stateId;
    private String stateName;


    public State(int stateId, String stateName) {
        this.stateId = stateId;
        this.stateName = stateName;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}
