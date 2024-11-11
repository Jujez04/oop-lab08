package it.unibo.deathnote.impl;

import java.util.*;
import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImplementation implements DeathNote{

    private final Map<String, Death> blackList = new HashMap<>();
    private final static long LIMIT_CAUSE = 40L;
    private final static long LIMIT_DETAILS = 6040L;
    private String lastNameWritten;
    private Death lastDeath;

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
        blackList.put(name, new Death());
        lastNameWritten = name;
    }

    @Override
    public boolean writeDeathCause(String cause) {
        if(cause.isEmpty() || blackList.isEmpty()) {
            throw new IllegalStateException();
        }
        long startTime = System.currentTimeMillis();
        final Death newDeath = new Death(cause);
        newDeath.setTimeElapsed(System.currentTimeMillis() - startTime);
        if(newDeath.getTimeLapse() < LIMIT_CAUSE) {
            blackList.put(lastNameWritten, newDeath);
            lastDeath = newDeath;
            return true;
        }
        return false;
    }

    @Override
    public boolean writeDetails(String details) {
        long startTime = System.currentTimeMillis();
        lastDeath.setDetails(details);
        lastDeath.setTimeElapsed(System.currentTimeMillis() - startTime);
        if(lastDeath.getTimeLapse() < LIMIT_DETAILS) {
            blackList.put(lastNameWritten, lastDeath);
            return true;
        }
        return false;
    }

    @Override
    public String getDeathCause(String name) {
        return blackList.get(name).getDeathCause();
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
        private final static String STD_DEATH = "Earth Attack";
        private final static String NO_DETAILS = "";

        private String deathCause;
        private String detailsDeath;
        private long timeLapse;
        
        Death(String deathCause, String details, long timeElapsed) {
            this.deathCause = deathCause;
            this.timeLapse = timeElapsed;
            this.detailsDeath = details;
        }

        Death(String deathCause, long timeElapsed) {
            this(deathCause, NO_DETAILS , timeElapsed);
        }

        Death(String deathCause) {
            this(deathCause, 0L);
        }

        Death() {
            this(STD_DEATH);
        }

        public String getDeathCause() {
            return this.deathCause;
        }
        
        public long getTimeLapse() {
            return this.timeLapse;
        }

        public String getDetails() {
            return this.detailsDeath;
        }

        public void setTimeElapsed(long lapse) {
            this.timeLapse = lapse;
        }
        
        public void setDetails(String details) {
            this.detailsDeath = details;
        }
        
        @SuppressWarnings("unused")
        public void setDeathCause(String cause) {
            this.deathCause = cause;
        }
    }
    
}