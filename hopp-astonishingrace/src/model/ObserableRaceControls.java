/*
 * TCSS 305 - Astonishing Race
 */
package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Queue;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents default behavior for Race subclasses.
 * @author Hop Pham
 * @version Feb 6 2018
 */
public class ObserableRaceControls extends Observable implements RaceControls {    
    
    /** The default starting time. */
    public static final int DEFAULT_START_TIME = 0;
    /** The position to get Lap information. */
    public static final int LAP = 4;
    
    /** The position to get Lap information. */
    public static final int DISTANCE = 3;
    
    /** The regex to get TimeStamp. */
    private static final Pattern PATTERN = Pattern.compile("\\d+");
    
    /** The separator for formatted. */
    private static final String SEPARATOR = ":";
    
    /** The amount of message to load before informing the user.  */
    private static final int INFORM_USER_COUNT = 4;
    
    /** The total time of the race. */
    private int myTotal;

    /** Racers who are particing.*/
    private final List<Integer> myPartipants;
  
    /**
     * Store data.
     */
    private final SortedMap<Integer, ArrayList<String>> myRaceData;
   
    /** The current TimeStamp which is filling in myRaceData. */
    private int myCurrentFillTimeStamp;
    
    /**
     * Store the Object time (the current time).
     */
    private int myTimeStamp;
    
    /**
     * Construct a race object.
     */
    public ObserableRaceControls() {
        super();
        myCurrentFillTimeStamp = -1;
        myTimeStamp = 0;
        myTotal = 0;
        myPartipants = new ArrayList<Integer>();
        myRaceData = new TreeMap<Integer, ArrayList<String>>();

    }
    
    /**
     * @author Charles Bryan
     * {@inheritDoc}
     */
    @Override
    public void advance() {
        advance(1);
    }
    
    /**
     * @author Charles Bryan
     * {@inheritDoc}
     */
    @Override
    public void advance(final int theMillisecond) {
        changeTime(myTimeStamp + theMillisecond);
    }
    /**
     * @author Charles Bryan
     * {@inheritDoc}
     */
    @Override
    public void moveTo(final int theMillisecond) {
        if (theMillisecond < 0) {
            throw new IllegalArgumentException("Error");
        }
        changeTime(theMillisecond);
    }
    
    /**
     * Helper method to change the value of time and notify observers. 
     * Functional decomposition. 
     * @author Charles Bryan
     * @param theMillisecond the time to change to
     */
    private void changeTime(final int theMillisecond) {
        final ArrayList<String> message = myRaceData.get(myTimeStamp);           
        if (message != null) {
            for (final String key : message) {
                final String[] messageLine = key.split(SEPARATOR);
                if (myPartipants.contains(Integer.parseInt(messageLine[2]))) {
                    sendData(messageLine, key);
                }
            }   
        }
        myTimeStamp = theMillisecond;
    }
    
    /**
     * Helper method to send data.
     * @param theData the data to send
     * @param theMes the message
     */
    private void sendData(final String[] theData, final String theMes) {
        final char prifix = theData[0].charAt(1);
        switch (prifix) {
            case 'T':
                setChanged();
                notifyObservers(new TelemetryImplement(Integer.parseInt(theData[2]),
                                Double.parseDouble(theData[DISTANCE]),
                                Integer.parseInt(theData[LAP])));
                break;
            case 'L':
                setChanged();
                notifyObservers(new LeaderBoardImplement(copyArray(theData)));
                break;
            default:
                break;                
        }  
        setChanged();
        notifyObservers(theMes);
    }
    
    /**
     * Copy the source to new array, but skip the first element.
     * @param theSource the source
     * @return the copy
     */
    private int[] copyArray(final String[] theSource) {
        final int[] destination = new int[theSource.length - 2];
        for (int i = 0; i < destination.length; i++) {
            destination[i] = Integer.parseInt(theSource[i + 2]);
        }
        return destination;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void toggleParticipant(final int theParticpantID, final boolean theToggle) {
        if (theToggle) {
            myPartipants.add(theParticpantID);
        } else {
            myPartipants.remove((Object) theParticpantID);
        }
//        setChanged();
//        notifyObservers(myPartipants);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void loadRace(final File theRaceFile) throws IOException {
        final BufferedReader input;
        final FileReader reader = new FileReader(theRaceFile);
        input = new BufferedReader(reader);        
        broadcastMessage("Load file: Start - This may take a while. Please be patient.");
        loadHeader(input);
        //notify changed header
        broadcastMessage("Load file: Race information loaded.");
        broadcastMessage("Load file: Loading telemetry information...");
        loadTelemetry(input);

        broadcastMessage("Load complete!"); 
        
        input.close();
        
    }
    
    /**
     * Helper to load header. 
     * 
     * @param theInput input file buffer
     * @throws IOException if the file is in the incorrect format
     */
    private void loadHeader(final BufferedReader theInput) throws IOException {
        final Queue<String> regex = getRules();
        String regexString = regex.poll();
        String inputLine = theInput.readLine();
        final Map<String, String> header = new TreeMap<String, String>();
        while (regexString != null) {
            if (inputLine.matches(regexString)) {
                final String[] arrayData = inputLine.split(SEPARATOR);
                header.put(arrayData[0].substring(1), arrayData[1]);
            } else {
                throw new IOException("Incorrect file format: " + inputLine);
            }
            regexString = regex.poll();
            if (regexString != null) {
                inputLine = theInput.readLine();
            }
        }
        myTotal = Integer.parseInt(header.get("TIME"));
        setChanged();
        notifyObservers(new RaceInformationImplement(header));
    }
    
    /**
     * Helper to load telemetry. 
     * 
     * @param theInput input file buffer
     * @throws IOException if the file is in the incorrect format
     */
    private void loadTelemetry(final BufferedReader theInput) throws IOException {
        String inputLine = theInput.readLine();
        final int loading = myTotal / INFORM_USER_COUNT; 
        final List<RaceParticipant> racer = new ArrayList<RaceParticipant>();
        while (inputLine != null) {
            if (myRaceData.size() > 0 && myRaceData.size() % loading == 0) {
                broadcastMessage("Load file: Still loading...");
            }
            if (inputLine.charAt(0) == '#') {
                racer.add(buildRacer(inputLine.split(SEPARATOR)));                
            } else if (inputLine.charAt(0) == '$') {
                fillData(inputLine);
            }            
            inputLine = theInput.readLine();
        }
        setChanged();
        notifyObservers(new ParticipantsContainerImplement(racer));
    }
    
    /**
     * This method will make the rules for race file.
     * 
     * @return a queue
     */
    private Queue<String> getRules() {
        final Queue<String> listRules = new LinkedList<String>();
        listRules.add("^(#RACE:)[^\n]+");        
        listRules.add("^(#TRACK:)[^\n]+");
        listRules.add("^(#WIDTH:)[\\d^\n]+");
        listRules.add("^(#HEIGHT:)[\\d^\n]+");
        listRules.add("^(#DISTANCE:)[\\d^\n]+");
        listRules.add("^(#TIME:)[\\d^\n]+");
        listRules.add("^(#PARTICIPANTS:)[\\d^\n]+");       
        return listRules;
    }    
   
    /**
     * Helper method to initialize the racer.
     * @param theRacerInfo the racer's information
     * @return return a racer
     */
    private RaceParticipant buildRacer(final String[] theRacerInfo) {  
        final int racerID = Integer.parseInt(theRacerInfo[0].substring(1));
        myPartipants.add(racerID);
        return new RaceParticipantImplement(racerID,
                   theRacerInfo[1], (int) Double.parseDouble(theRacerInfo[2]));
    }
    
    /**
     * Helper method to fill data into a map.
     * @param theMessage the data of a line
     */
    private void fillData(final String theMessage) {      
        final Matcher matcher = PATTERN.matcher(theMessage);
        matcher.find();
        final int timeStamp = Integer.parseInt(matcher.group());
        
        // have at least 2 timeStamp in a row 
        if (myCurrentFillTimeStamp == timeStamp) {            
            myRaceData.get(timeStamp).add(theMessage);
        } else {
            final ArrayList<String> data = new ArrayList<String>();
            data.add(theMessage);
            myRaceData.put(timeStamp, data);
            myCurrentFillTimeStamp = timeStamp;
        }
    }
    
    /**
     * Broadcast a message to Observers. 
     * @author Charles Bryan
     * @param theMessage the message to deliver
     */
    private void broadcastMessage(final String theMessage) {
        setChanged();
        notifyObservers(theMessage);
    }   
    
}
