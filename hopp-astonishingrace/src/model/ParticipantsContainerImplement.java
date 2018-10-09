/*
 * TCSS 305 - Astonishing Race
 */
package model;

import java.util.List;

/**
 * Participants for Race.
 * @author Hop Pham
 * @version Feb 6 2018
 */
public class ParticipantsContainerImplement implements ParticipantsContainer {

    /** The list of racer who are particing.*/
    private final List<RaceParticipant> myParticipants;
    
    /**
     * Constructs a list of racer.
     * @param theRaceParticipant the list of racer.
     */
    public ParticipantsContainerImplement(final List<RaceParticipant> theRaceParticipant) {
        myParticipants = theRaceParticipant;
    }
    @Override
    public List<RaceParticipant> getParticipants() {        
        return myParticipants;
    }

}
