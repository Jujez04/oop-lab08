package it.unibo.deathnote.impl;

import java.util.*;
import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImplementation implements DeathNote{

    private final Map<String, Death> blackList = new HashMap<>();
    private final static long LIMIT_CAUSE = 40L;
    private final static long LIMIT_DETAILS = 6040L;
    private String lastNameWritten;
    private Death lastDeath;


    private Death getDeath(String name) {
        return blackList.get(name);
    }

    @Override
    public String getRule(int ruleNumber) {
        if(ruleNumber < 1 || ruleNumber > RULES.size()) {
            throw new IllegalArgumentException();
        }
        return RULES.get(ruleNumber - 1);
    }

    @Override
    public void writeName(String name) {
        if(name.isBlank()) {
            throw new NullPointerException();
        }
        blackList.put(name, new Death());
        lastNameWritten = name;
        lastDeath = blackList.get(name);
    }

    @Override
    public boolean writeDeathCause(String cause) {
        if(cause.isBlank() || blackList.isEmpty()) {
            throw new IllegalStateException();
        }
        final Death newDeath = new Death(cause, getDeath(lastNameWritten).getStartTimeCause());
        if((System.currentTimeMillis() - newDeath.getStartTimeCause()) < LIMIT_CAUSE) {
            blackList.put(lastNameWritten, newDeath);
            lastDeath = newDeath;
            return true;
        }
        return false;
    }

    @Override
    public boolean writeDetails(String details) {
        if(System.currentTimeMillis() - lastDeath.getStartTimeCause() < LIMIT_DETAILS) {
            lastDeath.setDetails(details);
            blackList.put(lastNameWritten, lastDeath);
            return true;
        }
        return false;
    }

    @Override
    public String getDeathCause(String name) {
        return blackList.get(name).getCause();
    }

    @Override
    public String getDeathDetails(String name) {
        return blackList.get(name).getDetails();
    }

    @Override
    public boolean isNameWritten(String name) {
        return blackList.containsKey(name);
    }

    private class Death {
        private final static String STD_DEATH = "Heart attack";
        private final static String NO_DETAILS = "";

        private String deathCause;
        private String detailsDeath;
        private long startTimeCause;
        
        Death(String deathCause, String details, long timeElapsed) {
            this.deathCause = deathCause;
            this.startTimeCause = timeElapsed;
            this.detailsDeath = details;
        }

        Death(String deathCause, long startingTime) {
            this(deathCause, NO_DETAILS , startingTime);
        }

        Death() {
            this(STD_DEATH, System.currentTimeMillis());
        }

        public String getCause() {
            return this.deathCause;
        }
        
        public long getStartTimeCause() {
            return this.startTimeCause;
        }

        public String getDetails() {
            return this.detailsDeath;
        }
        
        public void setDetails(String details) {
            this.detailsDeath = details;
        }
    }
    
}