/*
 * TCSS 305 - Astonishing Race
 */
package control;

import java.awt.Component;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import model.ObserableRaceControls;
import model.ParticipantsContainer;
import model.RaceParticipant;

/**
 * Object of this class represent the JPanel 
 * for Toggole Racer.
 *    
 * @author Hop Pham
 * @version Jan 29 2018
 */
public class RacerCheckBoxController {
    
    /** name of the checkall box.*/
    private static final String ALL = "ALL";
    /** The Observable. */
    private final ObserableRaceControls myRace;

    /**
     * Construct the checkbox for each racer.
     * @param thePract list of racer
     * @param theOutput the panel
     * @param theRace the Obserable
     */
    public RacerCheckBoxController(final ParticipantsContainer thePract,
                                   final JPanel theOutput,
                                   final ObserableRaceControls theRace) {
        super();
        myRace = theRace;
        buildRacerCheckBox(thePract, theOutput);
        
    }
    
    /**
     * Helper method to build check box.
     * @param thePract a map which contain racer's information
     * @param theOutput the panel
     */
    private void buildRacerCheckBox(final ParticipantsContainer thePract,
                                    final JPanel theOutput) {
        final List<RaceParticipant> racers = thePract.getParticipants();
        //myRacerOutput = new JPanel(new GridLayout(thePract.size(), 2));
        final JCheckBox checkAll = new JCheckBox("Select All");
        checkAll.setSelected(true);
        checkAll.setName(ALL);
        checkAll.addActionListener(theEvent -> checkAll(checkAll));
        for (final RaceParticipant racer:racers) {
            final JCheckBox pra = new JCheckBox(racer.getName());
            pra.setSelected(true);
            pra.setName(Integer.toString(racer.getNumber()));                
            pra.addActionListener(theEvent -> setPracticipant(pra));
            theOutput.add(pra);
        }
        theOutput.add(checkAll);
    }
    
    /**
     * Call toggle method to toggle Racer.
     * @param theCheckBox the checkbox
     */
    private void setPracticipant(final JCheckBox theCheckBox) {
        myRace.toggleParticipant(Integer.parseInt(theCheckBox.getName()),
                                 theCheckBox.isSelected());
    }
    
    /**
     * Helper method to toggle all Racer.
     * @param theCheckAll the checkbox
     */
    private void checkAll(final JCheckBox theCheckAll) {
        for (final Component com:theCheckAll.getParent().getComponents()) {
            if (!((JCheckBox) com).getName().equals(ALL)) {
                ((JCheckBox) com).setSelected(theCheckAll.isSelected());
                myRace.toggleParticipant(Integer.parseInt(((JCheckBox) com).getName()),
                                         theCheckAll.isSelected());
            }
        }
    } 
}
