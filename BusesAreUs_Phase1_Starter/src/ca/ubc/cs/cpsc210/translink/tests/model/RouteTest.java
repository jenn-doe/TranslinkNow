package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RoutePattern;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test Route
 */
public class RouteTest {
    Route r;
    Route r1;
    Route rSame;
    List<RoutePattern> routePatterns;
    List<Stop> stops;
    RoutePattern rp;
    RoutePattern rp1;
    Stop s1;
    Stop s2;
    Stop s3;
    LatLon ll1;
    LatLon ll2;
    LatLon ll3;


    @Before
    public void setUp() {
        r = new Route("43");
        r1 = new Route("99");
        rSame = new Route ("43");
        routePatterns = new ArrayList<>();
        stops = new ArrayList<>();
        rp = new RoutePattern("Route Pattern", "UBC", "West", r);
        rp1 = new RoutePattern("Route Pattern1", "Home", "East", r1);
        ll1 = new LatLon(0.0, 0.0);
        ll2 = new LatLon(0.0, 0.0);
        ll3 = new LatLon(10.0, 10.0);
        s1 = new Stop(1, "Stop 1", ll1);
        s2 = new Stop(2, "Stop 2", ll2);
        s3 = new Stop(3, "Stop 3", ll3);

    }

    @Test
    public void testRouteConstructor() {
        assertEquals("43", r.getNumber());
        assertEquals(" ", r.getName());
        assertTrue(routePatterns.isEmpty());
        assertTrue(stops.isEmpty());
        assertEquals("Route 43", r.toString());
    }

    //tests for addPattern
    @Test
    public void testAddPatternToEmptyRoute() {
        List<RoutePattern> expected = new ArrayList<>();

        r.setName("RouteName");
        r.addPattern(rp);
        expected.add(rp);

        assertEquals(rp, r.getPattern("Route Pattern"));
        assertEquals(expected, r.getPatterns());

    }

    @Test
    public void testAddPatternToRouteSame() {
        List<RoutePattern> expected = new ArrayList<>();

        r.addPattern(rp);
        expected.add(rp);

        assertEquals(rp, r.getPattern("Route Pattern"));
        assertEquals(expected, r.getPatterns());

        r.addPattern(rp);
        assertEquals(rp, r.getPattern("Route Pattern"));
        assertEquals(expected, r.getPatterns());

    }

    @Test
    public void testAddPatternToRouteDifferent() {
        List<RoutePattern> expected = new ArrayList<>();

        r.addPattern(rp);
        expected.add(rp);

        r.addPattern(rp1);
        expected.add(rp1);

        assertEquals(rp, r.getPattern("Route Pattern"));
        assertEquals(rp1, r.getPattern("Route Pattern1"));
        assertEquals(expected, r.getPatterns());

    }

    //tests for addStop

    @Test
    public void testAddStopSingle() {
        List<Stop> expected = new ArrayList<>();
        Route route = new Route("84");
        route.addStop(s1);
        expected.add(s1);

        assertTrue(route.hasStop(s1));
        assertEquals(expected, route.getStops());
    }

    @Test
    public void testAddStopMultiSame() {
        List<Stop> expected = new ArrayList<>();
        r.addStop(s1);
        expected.add(s1);
        r.addStop(s1);

        assertTrue(r.hasStop(s1));
        assertEquals(expected, r.getStops());
    }

    @Test
    public void testAddStopMultiDif() {
        List<Stop> expected = new ArrayList<>();
        r.addStop(s1);
        expected.add(s1);
        r.addStop(s2);
        expected.add(s2);

        assertTrue(r.hasStop(s1));
        assertTrue(r.hasStop(s2));
        assertEquals(expected, r.getStops());
    }

    //Tests for removestop
    @Test
    public void testRemoveStopNoneLeft() {
        List<Stop> expected = new ArrayList<>();
        r.addStop(s1);
        expected.add(s1);

        assertTrue(r.hasStop(s1));
        assertEquals(expected, r.getStops());

        r.removeStop(s1);
        expected.remove(s1);
        assertFalse(r.hasStop(s1));
       assertEquals(expected, r.getStops());

    }

    @Test
    public void testRemoveStopSomeLeft() {
        List<Stop> expected = new ArrayList<>();
        r.addStop(s1);
        expected.add(s1);
        r.addStop(s2);
        expected.add(s2);

        assertTrue(r.hasStop(s1));
        assertTrue(r.hasStop(s2));
        assertEquals(expected, r.getStops());

        r.removeStop(s2);
        expected.remove(s2);
        assertTrue(r.hasStop(s1));
        assertFalse(r.hasStop(s2));
        assertEquals(expected, r.getStops());

    }

    //tests for getpattern(patternname)

    @Test
    public void testGetPatternExistsNameOnly() {
        assertEquals(rp, r.getPattern("Route Pattern"));

    }

    @Test
    public void testGetPatternDoesNotExistNameOnly() {
        assertEquals((new RoutePattern("Route Pattern 5", " ", " ", r)), r.getPattern("Route Pattern 5"));

    }

    //tests for getpattern update info

    @Test
    public void testGetPatternExistsNoUpdate() {
        assertEquals(rp, r.getPattern("Route Pattern", "UBC", "West"));

    }

    @Test
    public void testGetPatternExistsUpdate() {
        r.getPattern("Route Pattern", "Home", "East");
        rp.setDestination("Home");
        rp.setDirection("East");
        assertEquals(rp, r.getPattern("Route Pattern", "Home", "East"));
    }

    @Test
    public void testGetPatternDoesNotExist() {
        //r.getPattern("Route Pattern Six", "Home", "East");
        //rp.setDestination("Home");
        //rp.setDirection("East");
        assertEquals((new RoutePattern("Route Pattern Six", "Home", "East", r)), r.getPattern("Route Pattern Six", "Home", "East"));
    }

    //tests for override equals

    @Test
    public void testEqualsSameNumberSameInfo() {
        assertTrue(r.equals(rSame));

    }


    @Test
    public void testEqualsDiffNumberSameInfo() {
        assertFalse(r.equals(r1));
    }


    //tests for override hashcode

    @Test
    public void testHashCodeSameNumberSameInfo() {
        assertTrue(r.hashCode() == rSame.hashCode());

    }

    @Test
    public void testHashCodeDiffNumberSameInfo() {
        assertFalse(r.hashCode() == r1.hashCode());

        //tests for override iterator
    }

}
