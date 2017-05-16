package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.Arrival;
import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test Arrivals
 */
public class ArrivalsTest {
    Route r;
    Arrival a;
    Arrival a0 = new Arrival(0, "Home", r);
    Arrival a1 = new Arrival(23, "Friends", r);
    Arrival a2 = new Arrival(25, "Home", r);

    @Before
    public void setup() {
        r = RouteManager.getInstance().getRouteWithNumber("43");
        a = new Arrival(23, "Home", r);
    }

    @Test
    public void testConstructor() {
        assertEquals(23, a.getTimeToStopInMins());
        assertEquals(r, a.getRoute());
        assertEquals("Home", a.getDestination());
        assertEquals(" ", a.getStatus());
    }

    //tests for getTimeToStop

    @Test
    public void testGetTimeToStopInMinsNotArrived(){
        assertEquals(23, a.getTimeToStopInMins());
    }
    @Test
    public void testGetTimeToStopInMinsArrived(){
        assertEquals(0, a0.getTimeToStopInMins());
    }

    //Tests for getDestination

    @Test
    public void testGetDestination(){
        assertEquals("Home", a.getDestination());
    }

    //Tests for setStatus and getStatus
    @Test
    public void testSetStatusScheduled(){
        assertEquals(" ", a.getStatus());
        a.setStatus("*");
        assertEquals("*", a.getStatus());
    }
    @Test
    public void testSetStatusOnTime(){
        assertEquals(" ", a.getStatus());
        a.setStatus("+");
        assertEquals("+", a.getStatus());
    }
    @Test
    public void testSetStatusLate(){
        assertEquals(" ", a.getStatus());
        a.setStatus("-");
        assertEquals("-", a.getStatus());
    }

    //tests for compareTo

    @Test
    public void testCompareToSameArrival(){
        assertEquals(0, a.compareTo(a1));
    }
    @Test
    public void testCompareToEarlierArrival(){
        assertEquals(23, a.compareTo(a0));
    }
    @Test
    public void testCompareToLaterArrival(){
        assertEquals(-2, a.compareTo(a2));

    }



}
