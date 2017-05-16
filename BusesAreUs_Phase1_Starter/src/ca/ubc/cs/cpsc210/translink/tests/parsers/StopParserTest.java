package ca.ubc.cs.cpsc210.translink.tests.parsers;

import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.model.StopManager;
import ca.ubc.cs.cpsc210.translink.parsers.StopParser;
import ca.ubc.cs.cpsc210.translink.parsers.exception.StopDataMissingException;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the StopParser
 */

// TODO: Write more tests

public class StopParserTest {
    @Before
    public void setup() {
        StopManager.getInstance().clearStops();
    }

    @Test
    public void testStopParserNormal() throws StopDataMissingException, JSONException, IOException {
        StopParser p = new StopParser("stops.json");
        p.parse();
        assertEquals(8524, StopManager.getInstance().getNumStops());
    }

    @Test
    public void testStopParserFirstStop() throws StopDataMissingException, JSONException, IOException {
        StopParser p = new StopParser("stops.json");
        p.parse();
        Stop s = StopManager.getInstance().getStopWithNumber(50001);

        LatLon lln = new LatLon(49.28646, -123.14043);


        assertEquals(50001, s.getNumber());
        assertEquals("WB DAVIE ST FS BIDWELL ST", s.getName());
        assertEquals(lln, s.getLocn());

    }



    //tests for parsing through routePattern

    @Test
    public void testStopParserLastStop() throws StopDataMissingException, JSONException, IOException{
        StopParser p = new StopParser("stops.json");
        p.parse();
        Stop s = StopManager.getInstance().getStopWithNumber(61990);

        LatLon lln = new LatLon(49.24369, -123.06233);


        assertEquals(61990, s.getNumber());
        assertEquals("EB KINGSWAY NS GLADSTONE ST", s.getName());
        assertEquals(lln, s.getLocn());

    }

    //tests for exceptions

    @Test (expected = StopDataMissingException.class)
    public void testStopParserStopDataMissing() throws StopDataMissingException, JSONException, IOException{
        StopParser p = new StopParser("stopsmissingexample.json");
        try {
            p.parse();
        }
        finally {assertEquals(8518, StopManager.getInstance().getNumStops());
        }

    }

    @Test (expected = JSONException.class)
    public void testRouteParserJSONException() throws StopDataMissingException, JSONException, IOException{
        StopParser p = new StopParser("notarealjson.json");
        try {p.parse();}
        finally {
            assertEquals(0, StopManager.getInstance().getNumStops());

        }

    }

}
