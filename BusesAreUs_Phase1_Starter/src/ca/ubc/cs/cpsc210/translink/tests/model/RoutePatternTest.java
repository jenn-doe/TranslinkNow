package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RoutePattern;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test RoutePattern
 */
public class RoutePatternTest {

    Route r;
    RoutePattern rp;
    List<LatLon> path;

    @Before
    public void setUp(){
        r = new Route("43");
        rp = new RoutePattern("RoutePattern", "UBC", "West", r);
        path = new ArrayList<>();
    }

    @Test
    public void testConstructor(){
        assertEquals("RoutePattern", rp.getName());
        assertEquals("UBC", rp.getDestination());
        assertEquals("West", rp.getDirection());
        assertTrue(path.isEmpty());

        //assertEquals(r, rp.getRoute()); //!!! do we need this getter

    }
    //tests for overrideEquals

    @Test
    public void testOverrideEqualsSameNameSameStuff(){
        RoutePattern rp1 = new RoutePattern("RoutePattern", "UBC", "West", r);
        assertTrue(rp.equals(rp1));

        rp1.setDestination("Home");
        assertEquals("Home", rp1.getDestination());
        assertTrue(rp.equals(rp1));

        rp1.setDirection("East");
        assertEquals("East", rp1.getDirection());
        assertTrue(rp.equals(rp1));

    }

    @Test
    public void testOverrideEqualsSameNameDiffStuff(){
        Route r2 = new Route("99");
        RoutePattern rp2 = new RoutePattern("RoutePattern", "Home", "East", r2);
        assertTrue(rp.equals(rp2));

    }
    @Test
    public void testOverrideEqualsDiffNameSameStuff(){
        RoutePattern rp3 = new RoutePattern("RoutePattern3", "UBC", "West", r);
        assertFalse(rp.equals(rp3));

    }
    @Test
    public void testOverrideEqualsDiffNameDiffStuff(){
        Route r2 = new Route("99");
        RoutePattern rp4 = new RoutePattern("RoutePattern4", "Friends", "East", r2);
        assertFalse(rp.equals(rp4));
    }

    //tests for overrideHashCode

    @Test
    public void testOverrideHashCodeSameNameSameStuff(){
        RoutePattern rp1 = new RoutePattern("RoutePattern", "UBC", "West", r);
        assertTrue(rp.hashCode() == rp1.hashCode());

        rp1.setDestination("Home");
        assertEquals("Home", rp1.getDestination());
        assertTrue(rp.hashCode() == rp1.hashCode());

        rp1.setDirection("East");
        assertEquals("East", rp1.getDirection());
        assertTrue(rp.hashCode() == rp1.hashCode());

    }

    @Test
    public void testOverrideHashCodeSameNameDiffStuff(){
        Route r2 = new Route("99");
        RoutePattern rp2 = new RoutePattern("RoutePattern", "Home", "East", r2);
        assertTrue(rp.hashCode() == rp2.hashCode());

    }
    @Test
    public void testOverrideHashCodeDiffNameSameStuff(){
        RoutePattern rp3 = new RoutePattern("RoutePattern3", "UBC", "West", r);
        assertFalse(rp.hashCode() == rp3.hashCode());

    }
    @Test
    public void testOverrideHashCodeDiffNameDiffStuff(){
        Route r2 = new Route("99");
        RoutePattern rp4 = new RoutePattern("RoutePattern4", "Friends", "East", r2);
        assertFalse(rp.hashCode() == rp4.hashCode());
    }

    //tests for getpath, setpath

    @Test
    public void testGetPath(){
        List<LatLon> expected = new ArrayList<>();
        LatLon lln = new LatLon(10.0, 20.0);

        path.add(lln);
        expected.add(lln);
        rp.setPath(expected);
        assertEquals(expected, rp.getPath());
    }

    @Test
    public void testSetPath(){
        List<LatLon> expected = new ArrayList<>();
        LatLon lln = new LatLon(10.0, 20.0);
        expected.add(lln);
        rp.setPath(expected);

        assertEquals(expected, rp.getPath());

    }





}
