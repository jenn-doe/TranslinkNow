package ca.ubc.cs.cpsc210.translink.parsers;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.RoutePattern;
import ca.ubc.cs.cpsc210.translink.parsers.exception.RouteDataMissingException;
import ca.ubc.cs.cpsc210.translink.providers.DataProvider;
import ca.ubc.cs.cpsc210.translink.providers.FileDataProvider;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Parse route information in JSON format.
 */
public class RouteParser {
    private String filename;
    private Integer exceptionTracker;
    private Integer patternTracker;

    public RouteParser(String filename) {
        this.filename = filename;
        exceptionTracker = 0;
        patternTracker = 0;
    }
    /**
     * Parse route data from the file and add all route to the route manager.
     *
     */
    public void parse() throws IOException, RouteDataMissingException, JSONException{
        DataProvider dataProvider = new FileDataProvider(filename);

        parseRoutes(dataProvider.dataSourceToString());
    }
    /**
     * Parse route information from JSON response produced by Translink.
     * Stores all routes and route patterns found in the RouteManager.
     *
     * @param  jsonResponse    string encoding JSON data to be parsed
     * @throws JSONException   when:
     * <ul>
     *     <li>JSON data does not have expected format (JSON syntax problem)
     *     <li>JSON data is not an array
     * </ul>
     * If a JSONException is thrown, no stops should be added to the stop manager
     *
     * @throws RouteDataMissingException when
     * <ul>
     *  <li>JSON data is missing RouteNo, Name, or Patterns element for any route</li>
     *  <li>The value of the Patterns element is not an array for any route</li>
     *  <li>JSON data is missing PatternNo, Destination, or Direction element for any route pattern</li>
     * </ul>
     * If a RouteDataMissingException is thrown, all correct routes are first added to the route manager.
     */

    public void parseRoutes(String jsonResponse)
            throws JSONException, RouteDataMissingException {
        // TODO: Task 4: Implement this method

        JSONArray jArray = new JSONArray(jsonResponse);

        for(int i = 0 ; i < jArray.length(); i ++) {
            try {
                parseOneRoute(jArray.getJSONObject(i));
                }
                catch (JSONException e){
                    System.out.println("Caught JSON Exception");
            }
        }

        if ((exceptionTracker != 0) || (patternTracker != 0)){
            throw new RouteDataMissingException();
        }
        //go into the loop, because now jsonResponse has been turned into a json array. loop through the array, every next is a route
        //each item in json array is a json object, and need to parse that object.
    }

    /**
     * parses single JSON object passed by parseRoutes
     * @param currentRoute
     * @throws JSONException
     * @throws RouteDataMissingException
     */

    public void parseOneRoute(JSONObject currentRoute) throws JSONException, RouteDataMissingException {
        //List<RoutePattern> patterns = new ArrayList<>();

        Route oneRoute;
        try {
            String routeNo = currentRoute.getString("RouteNo");
            String routeName = currentRoute.getString("Name");
            oneRoute = RouteManager.getInstance().getRouteWithNumber(routeNo, routeName);

            JSONArray myArray = currentRoute.getJSONArray("Patterns");


            for (int j = 0; j < myArray.length(); j++) {
                try {String patternNo = myArray.getJSONObject(j).getString("PatternNo");
                String destination = myArray.getJSONObject(j).getString("Destination");
                String direction = myArray.getJSONObject(j).getString("Direction");


                RoutePattern onePattern = new RoutePattern(patternNo, destination, direction, oneRoute);
                oneRoute.addPattern(onePattern);}
                catch (JSONException e){
                    System.out.println("Caught JSON Exception in patterns");
                    patternTracker++;
                }
            }

        }
        catch (JSONException e){
            System.out.println("JSON Exception ");
            exceptionTracker++;
        }
    }



}
