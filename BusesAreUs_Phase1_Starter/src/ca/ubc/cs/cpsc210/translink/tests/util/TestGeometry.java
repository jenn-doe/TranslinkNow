package ca.ubc.cs.cpsc210.translink.tests.util;

import ca.ubc.cs.cpsc210.translink.util.Geometry;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test the Geometry class
 */

// TODO: Write more tests

public class TestGeometry {
    LatLon northWest, southEast;
    LatLon inside = new LatLon(49.25000, -122.75000);
    LatLon outside = new LatLon(48.25000, -122.75000);
    LatLon outside2 = new LatLon(47.25000, -122.75000);
    LatLon borderNW = new LatLon(49.50000, -123.00000);
    LatLon borderSE = new LatLon(49.00000, -122.50000);
    LatLon intersect1 = new LatLon(48.25000, -122.75000);
    LatLon intersect2 = new LatLon(49.75000, -122.75000);

    @Before
    public void setup() {
        northWest = new LatLon(49.50000, -123.00000);
        southEast = new LatLon(49.00000, -122.50000);
    }

    //tests for point

    @Test
    public void testPointInRectangle() {

        assertTrue(Geometry.rectangleContainsPoint(northWest, southEast, inside));
    }

    @Test
    public void testPointOutsideOfRectangle() {
        assertEquals(false, Geometry.rectangleContainsPoint(northWest, southEast, outside));
    }
    @Test
    public void testPointBorderNorthOfRectangle() {
        assertEquals(true, Geometry.rectangleContainsPoint(northWest, southEast, borderNW));
        assertEquals(true, Geometry.rectangleContainsPoint(northWest,southEast,borderSE));
    }

    //tests for line

    @Test
    public void testLineInRectangle() {
        assertTrue(Geometry.rectangleIntersectsLine(northWest, southEast, inside, southEast));
    }

    @Test
    public void testLineOutsideOfRectangle() {
        assertEquals(false, Geometry.rectangleIntersectsLine(northWest, southEast, outside, outside2));
    }


    @Test
    public void testLineBorderOfRectangle() {
        assertEquals(true, Geometry.rectangleIntersectsLine(northWest, southEast, outside, borderNW));
        assertEquals(true, Geometry.rectangleIntersectsLine(northWest, southEast, outside, borderSE));
    }

    @Test
    public void testLineSrcInRectangle() {
        assertEquals(true, Geometry.rectangleIntersectsLine(northWest, southEast, outside, inside));
    }
    @Test
    public void testLineDestInRectangle() {
        assertEquals(true, Geometry.rectangleIntersectsLine(northWest, southEast, inside, outside));
    }

    @Test
    public void testLineIntersectsRectangle(){

        assertEquals(true, Geometry.rectangleIntersectsLine(northWest, southEast, intersect1, southEast));

    }

}
