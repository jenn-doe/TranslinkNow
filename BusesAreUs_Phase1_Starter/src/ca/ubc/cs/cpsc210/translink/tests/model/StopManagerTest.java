package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.model.StopManager;
import ca.ubc.cs.cpsc210.translink.model.exception.StopException;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.junit.Before;
import org.junit.Test;

import static ca.ubc.cs.cpsc210.translink.model.StopManager.RADIUS;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test the StopManager
 */
public class StopManagerTest {

    @Before
    public void setup() {

        StopManager.getInstance().clearStops();

    }

    @Test
    public void testBoring() {
        Stop s9999 = new Stop(9999, "My house", new LatLon(-49.2, 123.2));
        Stop r = StopManager.getInstance().getStopWithNumber(9999);
        assertEquals(s9999, r);
    }

    @Test
    public void testConstructor(){
        assertNull(StopManager.getInstance().getSelected());
        assertEquals(0, StopManager.getInstance().getNumStops());
    }


    //tests for getStopWithNumber, just number, default empty string and location

    @Test
    public void testGetStopWithNumberAlreadyCreated(){
        Stop s9999 = new Stop(9999, "My house", new LatLon(-49.2, 123));
        assertEquals(s9999, StopManager.getInstance().getStopWithNumber(9999));
        assertEquals(s9999, StopManager.getInstance().getStopWithNumber(9999));

    }

    @Test
    public void testGetStopWithNumberNotCreated(){
        assertEquals((new Stop(6666, " ", new LatLon(-49.2, 123))), StopManager.getInstance().getStopWithNumber(6666));

    }

    //tests for getStopWithNumber, with number, name and locn


    @Test
    public void testGetStopWithNumberAlreadyCreatedInfo(){
        Stop s9999 = new Stop(9999, "My house", new LatLon(-49.2, 123));
        assertEquals(s9999, StopManager.getInstance().getStopWithNumber(9999, "My house", new LatLon(-49.2, 123)));
        assertEquals(s9999, StopManager.getInstance().getStopWithNumber(9999, "My house", new LatLon(-49.2, 123)));

    }

    @Test
    public void testGetStopWithNumberNotCreatedInfo(){
        assertEquals((new Stop(6666, "UBC", new LatLon(-49.2, 123))), StopManager.getInstance().getStopWithNumber(6666, "UBC", new LatLon(-49.2, 123)));

    }

    //tests for clearselected, setselected

    @Test
    public void testClearSelectedExists(){
        Stop s9999 = new Stop(9999, "My house", new LatLon(-49.2, 123));
        assertEquals(s9999, StopManager.getInstance().getStopWithNumber(9999));

        try {
            StopManager.getInstance().setSelected(s9999);
        } catch (StopException e) {
            e.printStackTrace();
            fail("Shouldn't have thrown exception");
        }
        StopManager.getInstance().clearSelectedStop();
        assertNull(StopManager.getInstance().getSelected());
        assertEquals(1, StopManager.getInstance().getNumStops());


    }
    @Test (expected = StopException.class)
    public void testClearSelectedDoesNotExist() throws StopException{
        Stop s9999 = new Stop(9999, "My house", new LatLon(-49.2, 123));
        StopManager.getInstance().setSelected(s9999);

        StopManager.getInstance().clearSelectedStop();
        assertNull(StopManager.getInstance().getSelected());
        assertEquals(0, StopManager.getInstance().getNumStops());

    }

    //tests for clearstops

    @Test
    public void testClearStopsAlreadyEmpty(){
        StopManager.getInstance().clearStops();
        assertEquals(0, StopManager.getInstance().getNumStops());

    }

    @Test
    public void testClearStopsOne(){
        Stop s9999 = new Stop(9999, "My house", new LatLon(-49.2, 123));
        assertEquals(s9999, StopManager.getInstance().getStopWithNumber(9999));
        assertEquals(1, StopManager.getInstance().getNumStops());

        StopManager.getInstance().clearStops();
        assertEquals(0, StopManager.getInstance().getNumStops());

    }
    @Test
    public void testClearStopsMulti(){
        Stop s9999 = new Stop(9999, "My house", new LatLon(-49.2, 123));
        assertEquals(s9999, StopManager.getInstance().getStopWithNumber(9999));
        Stop s1111 = new Stop(1111, "UBC", new LatLon(-49.2, 123));
        assertEquals(s1111, StopManager.getInstance().getStopWithNumber(1111));
        assertEquals(2, StopManager.getInstance().getNumStops());

        StopManager.getInstance().clearStops();
        assertEquals(0, StopManager.getInstance().getNumStops());

    }

    //tests for findnearestto

    @Test
    public void testFindNearestToOutOfRangeLat(){
        Stop sTooFar = new Stop(2222, "Far", new LatLon(12000, 123));
        assertEquals(sTooFar, StopManager.getInstance().getStopWithNumber(2222, "Far", new LatLon(12000, 123)));
        LatLon pt = new LatLon(-49.2, 123);
        assertNull(StopManager.getInstance().findNearestTo(pt));


    }
    @Test
    public void testFindNearestToOutOfRangeLon(){
        Stop sTooFar = new Stop(3333, "Far", new LatLon(-49.2, 12000));
        assertEquals(sTooFar, StopManager.getInstance().getStopWithNumber(3333,"Far", new LatLon(-49.2, 12000)));
        LatLon pt = new LatLon(-49.2, 123);
        assertNull(StopManager.getInstance().findNearestTo(pt));

    }

    @Test
    public void testFindNearestToInRange(){
        Stop sInRange = new Stop(4444, "Close", new LatLon(-49.2, 123));
        assertEquals(sInRange, StopManager.getInstance().getStopWithNumber(4444,"Close", new LatLon(-49.2, 123)));
        LatLon pt = new LatLon(-49.2, 123);
        assertEquals(sInRange, StopManager.getInstance().findNearestTo(pt));

    }

    @Test
    public void testFindNearestToOnRangeBoundary(){
        Stop sInRange = new Stop(5555, "Close", new LatLon(-49.2 + RADIUS, 123+RADIUS));
        assertEquals(sInRange, StopManager.getInstance().getStopWithNumber(5555, "Close", new LatLon(-49.2 + RADIUS, 123+RADIUS)));
        LatLon pt = new LatLon(-49.2, 123);
        assertNull(StopManager.getInstance().findNearestTo(pt));

    }

    @Test
    public void testFindNearestCloseStops(){
        Stop sCloser = new Stop(6666, "Closer", new LatLon(-49.19999999999, 123.000000001));
        assertEquals(sCloser, StopManager.getInstance().getStopWithNumber(6666, "Closer", new LatLon(-49.19999999999, 123.000000001)));
        Stop sClose = new Stop(7777, "Close", new LatLon(-50.0, 125));
        assertEquals(sClose, StopManager.getInstance().getStopWithNumber(7777, "Close", new LatLon(-50.0, 125)));

        LatLon pt = new LatLon(-49.2, 123);
        assertEquals(sCloser, StopManager.getInstance().findNearestTo(pt));

    }



}
