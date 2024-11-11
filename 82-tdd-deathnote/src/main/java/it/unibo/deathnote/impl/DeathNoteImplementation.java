package it.unibo.deathnote.impl;

import java.util.*;
import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImplementation implements DeathNote{

    private final Map<String, String> blackList = new HashMap<>();

    @Override
    public String getRule(int ruleNumber) {
        if(ruleNumber < 1 && ruleNumber > RULES.size()) {
            throw new IllegalArgumentException();
        }
        return RULES.get(ruleNumber - 1);
    }

    @Override
    public void writeName(String name) {
        if(name.isBlank()) {
            throw new NullPointerException();
        }
        blackList.put(name, "");
    }

    @Override
    public boolean writeDeathCause(String cause) {
        if(cause.isEmpty() || blackList.isEmpty()) {
            throw new IllegalStateException();
        }
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0L;
        for(String key : blackList.keySet()){
            if(blackList.get(key).isBlank()){
                blackList.put(key, cause);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean writeDetails(String details) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'writeDetails'");
    }

    @Override
    public String getDeathCause(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDeathCause'");
    }

    @Override
    public String getDeathDetails(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDeathDetails'");
    }

    @Override
    public boolean isNameWritten(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isNameWritten'");
    }

    
}