package ca.ubc.cs.cpsc210.translink.parsers;

import ca.ubc.cs.cpsc210.translink.model.Arrival;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.parsers.exception.ArrivalsDataMissingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A parser for the data returned by the Translink arrivals at a stop query
 */
public class ArrivalsParser {
    private static Integer arrivals = 0;


    /**
     * Parse arrivals from JSON response produced by TransLink query.  All parsed arrivals are
     * added to the given stop assuming that corresponding JSON object has a RouteNo: and an
     * array of Schedules:
     * Each schedule must have an ExpectedCountdown, ScheduleStatus, and Destination.  If
     * any of the aforementioned elements is missing, the arrival is not added to the stop.
     *
     *@param stop             stop to which parsed arrivals are to be added
     *@param jsonResponse    the JSON response produced by Translink  @throws JSONException  when:
     * <ul>
     *     <li>JSON response does not have expected format (JSON syntax problem)</li>
     *     <li>JSON response is not an array</li>
     * </ul>
     * @throws ArrivalsDataMissingException  when no arrivals are found in the reply !!!NOTE THIS! ONLY WHEN NONE FOUND
     */
    public static void parseArrivals(Stop stop, String jsonResponse)
            throws JSONException, ArrivalsDataMissingException {
        // TODO: Task 4: Implement this method
        JSONArray jArray = new JSONArray(jsonResponse);


        for (int i = 0; i < jArray.length(); i++) {
            try {
                parseOneArrival(jArray.getJSONObject(i), stop);
            } catch (JSONException e) {
                System.out.println("Caught JSON exception");
            }
        }
        if (arrivals == 0){
            throw new ArrivalsDataMissingException();
        }
    }

    /**
     *  parses single json object and stop passed from parseArrivals
     * @param currentArrival
     * @param stop
     * @throws JSONException
     * @throws ArrivalsDataMissingException
     */

    public static void parseOneArrival(JSONObject currentArrival, Stop stop) throws JSONException, ArrivalsDataMissingException {


            String routeNo = currentArrival.getString("RouteNo");

        try{
            JSONArray arrivalArray = currentArrival.getJSONArray("Schedules");


        for (int j = 0; j <  arrivalArray.length(); j++) {
            try{ Integer expectedCountdown = arrivalArray.getJSONObject(j).getInt("ExpectedCountdown");
            String destination = arrivalArray.getJSONObject(j).getString("Destination");
            String scheduleStatus = arrivalArray.getJSONObject(j).getString("ScheduleStatus");


            Arrival oneArrival = new Arrival(expectedCountdown, destination, RouteManager.getInstance().getRouteWithNumber(routeNo));
            oneArrival.setStatus(scheduleStatus);
            stop.addArrival(oneArrival);
                arrivals++;

            }
            catch (JSONException e){
                System.out.println("Caught JSON Exception in schedules");



            }
        }
        } catch (JSONException e){
            System.out.println("JSON Exception caught");



        }
    }


}
