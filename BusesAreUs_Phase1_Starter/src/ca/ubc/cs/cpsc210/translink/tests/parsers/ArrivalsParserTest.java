package ca.ubc.cs.cpsc210.translink.tests.parsers;

import ca.ubc.cs.cpsc210.translink.model.Arrival;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.model.StopManager;
import ca.ubc.cs.cpsc210.translink.parsers.ArrivalsParser;
import ca.ubc.cs.cpsc210.translink.parsers.exception.ArrivalsDataMissingException;
import ca.ubc.cs.cpsc210.translink.providers.FileDataProvider;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test the ArrivalsParser
 */
public class ArrivalsParserTest {
    @Before
    public void setup() {

    }

    @Test
    public void testArrivalsParserNormal() throws JSONException, ArrivalsDataMissingException {
        Stop s = StopManager.getInstance().getStopWithNumber(51479);
        s.clearArrivals();
        String data = "";
        try {
            data = new FileDataProvider("arrivals43.json").dataSourceToString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't read the arrivals data");
        }
        ArrivalsParser.parseArrivals(s, data);
        int count = 0;
        for (Arrival a : s) {
            assertTrue(a.getTimeToStopInMins() <= 120);
            count++;
        }
        assertEquals(6, count);



        Arrival arrival0 = s.getArrivals().get(0);
        assertEquals("043", arrival0.getRoute().getNumber());

        assertEquals("JOYCE STN", arrival0.getDestination());
        assertEquals(1, arrival0.getTimeToStopInMins());
        assertEquals("-", arrival0.getStatus());

        Arrival arrivalLast = s.getArrivals().get(5);
        assertEquals("043", arrivalLast.getRoute().getNumber());

        assertEquals("JOYCE STN", arrivalLast.getDestination());
        assertEquals(48, arrivalLast.getTimeToStopInMins());
        assertEquals(" ", arrivalLast.getStatus());

    }
//
//    @Test
//    public void testArrivalsParserNormalTestFirstArrival() throws JSONException, ArrivalsDataMissingException {
//        Stop s = StopManager.getInstance().getStopWithNumber(51479);
//        s.clearArrivals();
//        String data = "";
//        try {
//            data = new FileDataProvider("arrivals43.json").dataSourceToString();
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Can't read the arrivals data");
//        }
//        ArrivalsParser.parseArrivals(s, data);
//        int count = 0;
//        for (Arrival a : s) {
//            assertTrue(a.getTimeToStopInMins() <= 120);
//            count++;
//        }
//        assertEquals(6, count);
//
//    }

    //tests for exceptions

    //test for stop info missing

    @Test(expected = ArrivalsDataMissingException.class)
    public void testArrivalsParserStopNoMissing() throws ArrivalsDataMissingException, JSONException, IOException {
        Stop s = StopManager.getInstance().getStopWithNumber(51479);
        String data;
        data = new FileDataProvider("arrivals43missingno.json").dataSourceToString();

        try {ArrivalsParser.parseArrivals(s, data);
        }
        finally {
            int count = 0;
            for (Arrival a : s) {
                assertTrue(a.getTimeToStopInMins() <= 120);
                count++;
            }
            assertEquals(0, count);
        }

    }
    @Test(expected = ArrivalsDataMissingException.class)
    public void testArrivalsParserStopSchedMissing() throws ArrivalsDataMissingException, JSONException, IOException {
        Stop s = StopManager.getInstance().getStopWithNumber(51479);
        String data;
        data = new FileDataProvider("arrivals43missingscheduleinfo.json").dataSourceToString();

        try {ArrivalsParser.parseArrivals(s, data);
        }
        finally {
            int count = 0;
            for (Arrival a : s) {
                assertTrue(a.getTimeToStopInMins() <= 120);
                count++;
            }
            assertEquals(0, count);
        }

    }

    @Test(expected = ArrivalsDataMissingException.class)
    public void testArrivalsParserSchedInfoMissing() throws ArrivalsDataMissingException, JSONException, IOException {
        Stop s = StopManager.getInstance().getStopWithNumber(51479);
        String data;
        data = new FileDataProvider("arrivals43missingscheduleinnerinfo.json").dataSourceToString();

        try {ArrivalsParser.parseArrivals(s, data);
        }
        finally {
            int count = 0;
            for (Arrival a : s) {
                assertTrue(a.getTimeToStopInMins() <= 120);
                count++;
            }
            assertEquals(0, count);
        }

    }



}
