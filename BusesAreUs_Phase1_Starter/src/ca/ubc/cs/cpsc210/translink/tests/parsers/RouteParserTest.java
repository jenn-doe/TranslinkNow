package ca.ubc.cs.cpsc210.translink.tests.parsers;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.RoutePattern;
import ca.ubc.cs.cpsc210.translink.parsers.RouteParser;
import ca.ubc.cs.cpsc210.translink.parsers.exception.RouteDataMissingException;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the RouteParser
 */
// TODO: Write more tests

public class RouteParserTest {
    @Before
    public void setup() {
        RouteManager.getInstance().clearRoutes();
    }

    @Test
    public void testRouteParserNormal() throws RouteDataMissingException, JSONException, IOException {
        RouteParser p = new RouteParser("allroutes.json");
        p.parse();
        assertEquals(229, RouteManager.getInstance().getNumRoutes());
    }

    //tests for parsing through route

    @Test
    public void testRouteParserFirstRoute() throws RouteDataMissingException, JSONException, IOException {
        RouteParser p = new RouteParser("allroutes.json");
        p.parse();
        Route r = RouteManager.getInstance().getRouteWithNumber("002");
        assertEquals("002", r.getNumber());

        RoutePattern rp = new RoutePattern("EB3", "BURRARD STN", "EAST", r);
        assertEquals(rp, r.getPattern("EB3"));
        assertEquals(5, r.getPatterns().size());


    }
    @Test
    public void testRouteParserLastRoute() throws RouteDataMissingException, JSONException, IOException{
        RouteParser p = new RouteParser("allroutes.json");
        p.parse();
        Route r = RouteManager.getInstance().getRouteWithNumber("N9");
        assertEquals("N9", r.getNumber());
        RoutePattern rp = new RoutePattern("EB1", "COQ STN NIGHTBUS", "EAST", r);
        assertEquals(rp, r.getPattern("EB1"));
        assertEquals(2, r.getPatterns().size());


    }

    //tests for parsing through routePattern

    @Test
    public void testRouteParserFirstPattern() throws RouteDataMissingException, JSONException, IOException{
        RouteParser p = new RouteParser("allroutes.json");
        p.parse();
        Route r = RouteManager.getInstance().getRouteWithNumber("002");
        assertEquals("002", r.getNumber());
        RoutePattern rp = new RoutePattern("EB3", "BURRARD STN", "EAST", r);
        assertEquals(rp, r.getPattern("EB3"));
        assertEquals(5, r.getPatterns().size());

        assertEquals("EB3", rp.getName());
        assertEquals("EAST", rp.getDirection());
        assertEquals("BURRARD STN", rp.getDestination());


    }

    @Test
    public void testRouteParserLastPattern() throws RouteDataMissingException, JSONException, IOException{
        RouteParser p = new RouteParser("allroutes.json");
        p.parse();
        Route r = RouteManager.getInstance().getRouteWithNumber("002");
        assertEquals("002", r.getNumber());
        RoutePattern rp = new RoutePattern("WB2", "MACDONALD - 16 AVE", "WEST", r);
        assertEquals(rp, r.getPattern("WB2"));
        assertEquals(5, r.getPatterns().size());

        assertEquals("WB2", rp.getName());
        assertEquals("WEST", rp.getDirection());
        assertEquals("MACDONALD - 16 AVE", rp.getDestination());



    }

    @Test (expected = RouteDataMissingException.class)
    public void testRouteParserRouteDataMissing() throws RouteDataMissingException, JSONException, IOException{
        RouteParser p = new RouteParser("allroutesmissingex.json");
        try {
            p.parse();
        }
        finally {assertEquals(227, RouteManager.getInstance().getNumRoutes());
        }

    }

    @Test (expected = RouteDataMissingException.class)
    public void testRouteParserRoutePatternMissing() throws RouteDataMissingException, JSONException, IOException{
        RouteParser p = new RouteParser("allroutesmissingpatternno.json");
        try {
            p.parse();
        }
        finally {assertEquals(229, RouteManager.getInstance().getNumRoutes());
            assertEquals(4, RouteManager.getInstance().getRouteWithNumber("002").getPatterns().size());
        }

    }
    @Test (expected = JSONException.class)
    public void testRouteParserJSONException() throws RouteDataMissingException, JSONException, IOException{
        RouteParser p = new RouteParser("notarealjson.json");
        try {p.parse();}
        finally {
            assertEquals(0, RouteManager.getInstance().getNumRoutes());

        }

    }


}
