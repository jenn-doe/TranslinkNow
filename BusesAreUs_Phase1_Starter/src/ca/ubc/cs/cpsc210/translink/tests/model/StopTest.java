package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.Arrival;
import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for stop
 */
public class StopTest {
    Stop stop;
    List<Arrival> arrivals;
    Set<Route> routes;
    LatLon locn;
    Route route;

    @Before
    public void setUp(){
        locn = new LatLon(10.0, 10.0);
        stop = new Stop(1, "Stop 1", locn);
        arrivals = new ArrayList<>();
        routes = new HashSet<>();
        route = new Route("RouteNo");
    }

    //test stop constructor
    @Test
    public void testConstructor(){
        assertEquals(1, stop.getNumber());
        assertEquals("Stop 1", stop.getName());
        assertEquals(locn, stop.getLocn());
        assertTrue(arrivals.isEmpty());
        assertTrue(routes.isEmpty());
    }

    //tests for addRoute

    @Test
    public void testAddRouteToEmpty(){
        Set<Route> expected = new HashSet<>();
        List<Stop> expectedStops = new ArrayList<>();

        Route r1 = new Route("99");
        stop.addRoute(r1);
        expected.add(r1);

        r1.addStop(stop);
        assertTrue(r1.hasStop(stop));
        expectedStops.add(stop);
        assertEquals(expectedStops, r1.getStops());

        routes.add(r1);

        assertTrue(routes.contains(r1));
        assertEquals(expected, stop.getRoutes());


    }
    @Test
    public void testAddRouteToHasOne(){
        Set<Route> expected = new HashSet<>();
        List<Stop> expectedStops = new ArrayList<>();

        Route r1 = new Route("99");
        stop.addRoute(r1);
        expected.add(r1);
        routes.add(r1);
        r1.addStop(stop);

        expectedStops.add(stop);
        assertEquals(expectedStops, r1.getStops());


        assertTrue(routes.contains(r1));
        assertEquals(expected, stop.getRoutes());

        Route r2 = new Route("84");
        stop.addRoute(r2);
        expected.add(r2);
        routes.add(r2);

        assertTrue(routes.contains(r2));
        assertEquals(expected, stop.getRoutes());


        assertTrue(r1.hasStop(stop));
    }

    //tests for removeroute

    @Test
    public void testRemoveRouteNoneLeft(){
        Set<Route> expected = new HashSet<>();
        Route r1 = new Route("99");

        stop.addRoute(r1);
        routes.add(r1);
        expected.add(r1);

        assertTrue(routes.contains(r1));
        assertEquals(expected, stop.getRoutes());

        stop.removeRoute(r1);
        routes.remove(r1);
        expected.remove(r1);
        assertFalse(routes.contains(r1));
        assertEquals(expected, stop.getRoutes());

    }

    @Test
    public void testRemoveRouteSomeLeft(){
        Set<Route> expected = new HashSet<>();
        Route r1 = new Route("99");
        Route r2 = new Route("84");

        stop.addRoute(r1);
        expected.add(r1);
        routes.add(r1);
        stop.addRoute(r2);
        expected.add(r2);
        routes.add(r2);

        assertTrue(routes.contains(r1));
        assertTrue(routes.contains(r2));
        assertEquals(expected, stop.getRoutes());

        stop.removeRoute(r1);
        expected.remove(r1);
        routes.remove(r1);

        stop.removeRoute(r2);
        expected.remove(r2);
        routes.remove(r2);

        assertFalse(routes.contains(r1));
        assertFalse(routes.contains(r2));
        assertEquals(expected, stop.getRoutes());

    }

    //tests for onRoute

    @Test
    public void testOnRouteFalseOnlyOne(){
        Route r1 = new Route("99");
        assertFalse(stop.onRoute(r1));


    }

    @Test
    public void testOnRouteTrueOnlyOne(){
        Route r1 = new Route("99");
        stop.addRoute(r1);
        assertTrue(stop.onRoute(r1));
    }

    @Test
    public void testOnRouteFalseOneOfMany(){
        Route r1 = new Route("99");
        Route r2 = new Route("84");
        Route r3 = new Route("3");
        stop.addRoute(r1);
        stop.addRoute(r2);
        assertFalse(stop.onRoute(r3));
    }

    @Test
    public void testOnRouteTrueOneOfMany(){
        Route r1 = new Route("99");
        Route r2 = new Route("84");
        Route r3 = new Route("3");
        stop.addRoute(r1);
        stop.addRoute(r2);
        stop.addRoute(r3);
        assertTrue(stop.onRoute(r3));

    }

    //tests for addArrival

    @Test
    public void testAddArrivalSingle(){
        Route r1 = new Route("99");
        Arrival a0 = new Arrival(0, "Home", r1);

        stop.addArrival(a0);
        arrivals.add(a0);
        assertTrue(arrivals.contains(a0));


    }
    @Test
    public void testAddArrivalMulti(){
        Route r1 = new Route("99");
        Arrival a0 = new Arrival(0, "Home", r1);
        Arrival a1 = new Arrival(1, "Home", r1);
        Arrival a2 = new Arrival(3, "Home", r1);


        stop.addArrival(a0);
        stop.addArrival(a1);
        stop.addArrival(a2);
        arrivals.add(a0);
        arrivals.add(a1);
        arrivals.add(a2);
        assertTrue(arrivals.contains(a0));
        assertTrue(arrivals.contains(a1));
        assertTrue(arrivals.contains(a2));

    }
    //tests for clear arrivals

    @Test
    public void testClearArrivalsSingle(){
        Route r1 = new Route("99");
        Arrival a0 = new Arrival(0, "Home", r1);

        stop.addArrival(a0);
        stop.clearArrivals();
        assertFalse(arrivals.contains(a0));
        assertTrue(arrivals.isEmpty());
    }

    @Test
    public void testClearArrivalsMulti(){
        Route r1 = new Route("99");
        Arrival a0 = new Arrival(0, "Home", r1);
        Arrival a1 = new Arrival(1, "Home", r1);

        stop.addArrival(a0);
        stop.addArrival(a1);
        stop.clearArrivals();
        assertFalse(arrivals.contains(a0));
        assertFalse(arrivals.contains(a1));
        assertTrue(arrivals.isEmpty());
    }

    //tests for equals

    @Test
    public void testOverrideEqualsNotEqual(){
        Stop stop2 = new Stop(2, "Stop 2", locn);
        assertFalse(stop.equals(stop2));

    }

    @Test
    public void testOverrideEqualsIsEqual(){
        Stop stop1 = new Stop(1, "Stop 2", locn);
        assertTrue(stop.equals(stop1));
    }

    //tests for hashcode

    @Test
    public void testOverrideHashcodeNotEqual(){
        Stop stop2 = new Stop(2, "Stop 2", locn);
        assertFalse(stop.hashCode() == stop2.hashCode());

    }
    @Test
    public void testOverrideHashcodeIsEqual(){
        Stop stop1 = new Stop(1, "Stop 2", locn);
        assertTrue(stop.hashCode() == stop1.hashCode());

    }

    //tests for setname and setlocation

    @Test
    public void testSetName(){
        stop.setName("STOP!");
        assertEquals("STOP!", stop.getName());
    }
    @Test
    public void testSetLocn(){
        LatLon ll2 = new LatLon(100.0, 49.0);
        stop.setLocn(ll2);
        assertEquals(ll2, stop.getLocn());
    }


}
