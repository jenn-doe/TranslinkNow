package ca.ubc.cs.cpsc210.translink.parsers;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.model.StopManager;
import ca.ubc.cs.cpsc210.translink.parsers.exception.StopDataMissingException;
import ca.ubc.cs.cpsc210.translink.providers.DataProvider;
import ca.ubc.cs.cpsc210.translink.providers.FileDataProvider;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * A parser for the data returned by Translink stops query
 */
public class StopParser {

    private String filename;
    private Integer exceptionTracker;

    public StopParser(String filename) {
        this.filename = filename;
        exceptionTracker = 0;
    }
    /**
     * Parse stop data from the file and add all stops to stop manager.
     *
     */
    public void parse() throws IOException, StopDataMissingException, JSONException{
        DataProvider dataProvider = new FileDataProvider(filename);
        parseStops(dataProvider.dataSourceToString());
    }


    /**
     * Parse stop information from JSON response produced by Translink.
     * Stores all stops and routes found in the StopManager and RouteManager.
     *
     * @param  jsonResponse    string encoding JSON data to be parsed
     * @throws JSONException when:
     * <ul>
     *     <li>JSON data does not have expected format (JSON syntax problem)</li>
     *     <li>JSON data is not an array</li>
     * </ul>
     * If a JSONException is thrown, no stops should be added to the stop manager
     * @throws StopDataMissingException when
     * <ul>
     *  <li> JSON data is missing Name, StopNo, Routes or location (Latitude or Longitude) elements for any stop</li>
     * </ul>
     * If a StopDataMissingException is thrown, all correct stops are first added to the stop manager.
     */

    public void parseStops(String jsonResponse)
            throws JSONException, StopDataMissingException {
        // TODO: Task 4: Implement this method

        JSONArray jArray = new JSONArray(jsonResponse);

        for (int i = 0; i < jArray.length(); i++) {
            try {
                parseOneStop(jArray.getJSONObject(i));
            } catch (JSONException e) {
                System.out.println("Caught JSON Exception");
            }
        }
        if (exceptionTracker != 0){
    throw new StopDataMissingException();
        }

    }

    /**
     * parses single JSON object passed by parseStops
     * @param currentStop
     * @throws JSONException
     * @throws StopDataMissingException
     */

    public void parseOneStop(JSONObject currentStop) throws JSONException, StopDataMissingException {
        //List<RoutePattern> patterns = new ArrayList<>();
        Stop oneStop;
        LatLon lln;
        try {
            String name = currentStop.getString("Name");
            Integer stopNo = currentStop.getInt("StopNo");
            Double lat = currentStop.getDouble("Latitude");
            Double lon = currentStop.getDouble("Longitude");
            String routes = currentStop.getString("Routes");
            String[] routesList = routes.split(",");

            lln = new LatLon(lat, lon);

            oneStop = StopManager.getInstance().getStopWithNumber(stopNo, name, lln);

            for (int i = 0; i < routesList.length; i++) {

                Route route = RouteManager.getInstance().getRouteWithNumber(routesList[i]);
                oneStop.addRoute(route);

            }
        } catch (JSONException e){
            System.out.println("JSON Exception caught");
            exceptionTracker++;
        }





    }
}
