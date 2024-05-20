package Services;

import DAO.*;
import Model.Authtoken;
import Model.Event;
import Response.EventResponse;
import Response.EventsResponse;

/**
 * Used to retrieve a single event's (or multiple event's) latitude, longitude, city, country, etc.
 */
public class EventService {

    /**
     * Used to get a single event using that event's ID
     * @param eventID specific eventID
     * @return the specific event
     */
    public EventResponse getSingleEvent(String eventID, String authtoken) throws DataAccessException {

        //Create Database object
        Database db = new Database();
        try {
            //Open Database
            db.openConnection();
            //create DAOs to use for getting, setting, and clearing
            EventDAO eDAO = new EventDAO(db.getConnection());
            AuthtokenDAO aDAO = new AuthtokenDAO(db.getConnection());
            //make sure authtoken is exists using ".find"
            if (aDAO.find(authtoken) == null) {
                throw new DataAccessException("Invalid Authtoken");
            }
            //make sure eventID exists
            if (eDAO.find(eventID) == null) {
                throw new DataAccessException("Invalid PersonID");
            }
            Authtoken authResult = aDAO.find(authtoken);
            Event eventResult = eDAO.find(eventID);
            //after getting person make sure that username from authtoken and associatedusername is same
            if (!authResult.getUsername().equals(eventResult.getAssociatedUsername())) {
                throw new DataAccessException("Event not associated with user");
            }
            //Close and commit transaction
            db.closeConnection(true);
            //Create and return successful event response object
            EventResponse eRes = new EventResponse(eventResult.getEventID(), eventResult.getAssociatedUsername(), eventResult.getPersonID(), eventResult.getLatitude(),
                    eventResult.getLongitude(), eventResult.getCountry(), eventResult.getCity(), eventResult.getEventType(), eventResult.getYear(), true);
            return eRes;
        }
        catch (DataAccessException e) {

            e.printStackTrace();

            //Close and rollback transaction
            db.closeConnection(false);

            //Create and return failure event response object
            EventResponse eRes = new EventResponse("Error: " + e.getMessage(), false);
            return eRes;
        }
    }

    /**
     * Used to get multiple events using an array of event IDs
     * @param authtoken authtoken used to verify the user and access their username
     * @return the array of specific events
     */
    public EventsResponse getEvents(String authtoken) {

        //Create Database object
        Database db = new Database();

        try {
            //open Database
            db.openConnection();

            //Create DAOs that I will use
            EventDAO eDAO = new EventDAO(db.getConnection());
            AuthtokenDAO aDAO = new AuthtokenDAO(db.getConnection());
            if (aDAO.find(authtoken) == null) {
                throw new DataAccessException("Invalid authtoken");
            }
            //Getting the username from the authtoken
            String associatedUsername = aDAO.find(authtoken).getUsername();
            //Save the events in an array
            Event [] eventsResult = eDAO.findAll(associatedUsername);
            //close the connection and make response
            db.closeConnection(true);
            EventsResponse eRes = new EventsResponse(eventsResult,true);
            return eRes;
        }
        catch (DataAccessException e) {
            e.printStackTrace();

            //Close and rollback transaction
            db.closeConnection(false);

            //Create and return failure event response object
            EventsResponse eRes = new EventsResponse("Error: " + e.getMessage(), false);
            return eRes;
        }
    }
}
