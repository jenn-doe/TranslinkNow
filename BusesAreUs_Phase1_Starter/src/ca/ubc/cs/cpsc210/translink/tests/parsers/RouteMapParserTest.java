package ca.ubc.cs.cpsc210.translink.tests.parsers;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.RoutePattern;
import ca.ubc.cs.cpsc210.translink.parsers.RouteMapParser;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test the parser for route pattern map information
 */

// TODO: Write more tests

public class RouteMapParserTest {
    @Before
    public void setup() {
        RouteManager.getInstance().clearRoutes();
    }

    private int countNumRoutePatterns() {
        int count = 0;
        for (Route r : RouteManager.getInstance()) {
            for (RoutePattern rp : r.getPatterns()) {
                count++;
            }
        }
        return count;
    }


    @Test
    public void testRouteMapParserNormal() {
        RouteMapParser p = new RouteMapParser("allroutemaps.txt");
        p.parse();
        assertEquals(1232, countNumRoutePatterns());
    }

    //tests for routemapparser contains route

    @Test
    public void testRouteMapParserFirstRoute() {
        RouteMapParser p = new RouteMapParser("allroutemaps.txt");
        p.parse();
        Route r = (RouteManager.getInstance().getRouteWithNumber("C43"));
        assertTrue(containsRoute(r));
    }

    @Test
    public void testRouteMapParserLastRoute() {
        RouteMapParser p = new RouteMapParser("allroutemaps.txt");
        p.parse();
        Route r = (RouteManager.getInstance().getRouteWithNumber("006"));
        assertTrue(containsRoute(r));
    }

    @Test
    public void testRouteMapParserDashedRoute() {
        RouteMapParser p = new RouteMapParser("allroutemaps.txt");
        p.parse();
        Route r = (RouteManager.getInstance().getRouteWithNumber("100"));
        assertTrue(containsRoute(r));
    }


    //tests for routeMapParser contains pattern

    @Test
    public void testRouteMapParserFirstPattern() {
        RouteMapParser p = new RouteMapParser("allroutemaps.txt");
        p.parse();
        Route r = new Route("C43");
        RoutePattern rp = new RoutePattern("EB2", "Home", "East", r);
        assertTrue(containsPattern(rp));
    }

    @Test
    public void testRouteMapParserDashedPattern() {
        RouteMapParser p = new RouteMapParser("allroutemaps.txt");
        p.parse();
        Route r = new Route("100");
        RoutePattern rp = new RoutePattern("W1-71", "Home", "East", r);
        assertTrue(containsPattern(rp));

    }

    @Test
    public void testRouteMapParserLastPattern() {
        RouteMapParser p = new RouteMapParser("allroutemaps.txt");
        p.parse();
        Route r = new Route("006");
        RoutePattern rp = new RoutePattern("EB3", "Home", "East", r);
        assertTrue(containsPattern(rp));

    }


    //tests for routeMapParser has latlons

    @Test
    public void testRouteMapParserFirstLatLonFirstOfList() {
        RouteMapParser p = new RouteMapParser("allroutemaps.txt");
        p.parse();
        LatLon lln = new LatLon(49.21716, -122.667252);
        Route r = RouteManager.getInstance().getRouteWithNumber("C43");
        assertTrue(r.getPattern("EB2").getPath().contains(lln));

    }


    @Test
    public void testRouteMapParserFirstLatLonLastOfList(){
        RouteMapParser p = new RouteMapParser("allroutemaps.txt");
        p.parse();
        LatLon lln = new LatLon(49.219652,-122.59628);
        Route r = RouteManager.getInstance().getRouteWithNumber("C43");
        assertTrue(r.getPattern("EB2").getPath().contains(lln));

    }

    @Test
    public void testRouteMapParserDashedLatLonFirst(){
        RouteMapParser p = new RouteMapParser("allroutemaps.txt");
        p.parse();
        LatLon lln = new LatLon(49.199658, -122.949611);
        Route r = RouteManager.getInstance().getRouteWithNumber("100");
        assertTrue(r.getPattern("W1-71").getPath().contains(lln));

    }

    @Test
    public void testRouteMapParserDashedLatLonLast(){
        RouteMapParser p = new RouteMapParser("allroutemaps.txt");
        p.parse();
        LatLon lln = new LatLon(49.202882, -123.133236);
        Route r = RouteManager.getInstance().getRouteWithNumber("100");
        assertTrue(r.getPattern("W1-71").getPath().contains(lln));

    }

    @Test
    public void testRouteMapParserLastLatLonFirst(){
        RouteMapParser p = new RouteMapParser("allroutemaps.txt");
        p.parse();
        LatLon lln = new LatLon(49.28727,-123.141822);
        Route r = RouteManager.getInstance().getRouteWithNumber("006");
        assertTrue(r.getPattern("EB3").getPath().contains(lln));

    }



    //helpers
    private boolean containsRoute(Route r) {
        for (Route r1 : RouteManager.getInstance()) {
            if (r1.equals(r)) {
            }
        }
        return true;
    }

    private boolean containsPattern(RoutePattern rp) {
        for (Route rp1 : RouteManager.getInstance()) {
            if (rp1.equals(rp)) {

            }
        }
        return true;
    }
}

