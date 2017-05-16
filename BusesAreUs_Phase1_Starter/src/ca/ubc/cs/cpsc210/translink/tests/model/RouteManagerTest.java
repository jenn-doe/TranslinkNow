package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test the RouteManager
 */
public class RouteManagerTest {

    @Before
    public void setup() {
        RouteManager.getInstance().clearRoutes();
    }


    @Test
    public void testBoring() {
        Route r43 = new Route("43");
        Route r = RouteManager.getInstance().getRouteWithNumber("43");
        assertEquals(r43, r);
    }

    //test constructor

    @Test
    public void testConstructor(){
        assertEquals(0, RouteManager.getInstance().getNumRoutes());
    }

    //tests for getRouteWithNumber, number and empty string name

    @Test
    public void testGetRouteWithNumberAlreadyCreated(){
        Route r43 = new Route("43");
        r43.setName("Route 43");
        assertEquals(r43, RouteManager.getInstance().getRouteWithNumber("43"));
        assertEquals(r43, RouteManager.getInstance().getRouteWithNumber("43"));

    }

    @Test
    public void testGetRouteWithNumberCreate(){
        assertEquals(new Route("43"), RouteManager.getInstance().getRouteWithNumber("43", " "));
        assertEquals(new Route("43"), RouteManager.getInstance().getRouteWithNumber("43", " "));

    }

    //tests for getRouteWithNumber, number and name

    @Test
    public void testGetRouteWithNumberNameAlreadyCreated(){
        Route r43 = new Route("43");
        r43.setName("Route 43");
        assertEquals(r43, RouteManager.getInstance().getRouteWithNumber("43"));
        assertEquals(r43, RouteManager.getInstance().getRouteWithNumber("43"));

    }

    @Test
    public void testGetRouteWithNumberNameCreate(){
        assertEquals(new Route("43"), RouteManager.getInstance().getRouteWithNumber("43", "Route 43"));
        assertEquals(new Route("43"), RouteManager.getInstance().getRouteWithNumber("43","Route 43"));

    }

    //tests for clearRoutes/ getNumRoutes

    @Test
    public void testClearRoutesAlreadyEmpty(){
        RouteManager.getInstance().clearRoutes();
        assertEquals(0, RouteManager.getInstance().getNumRoutes());

    }

    @Test
    public void testClearRoutesOne(){
        Route r43 = new Route("43");
        assertEquals(r43, RouteManager.getInstance().getRouteWithNumber("43"));
        assertEquals(1, RouteManager.getInstance().getNumRoutes());

        RouteManager.getInstance().clearRoutes();
        assertEquals(0, RouteManager.getInstance().getNumRoutes());

    }
    @Test
    public void testClearRoutesMulti(){
        Route r43 = new Route("43");
        assertEquals(r43, RouteManager.getInstance().getRouteWithNumber("43"));
        Route r99 = new Route("99");
        assertEquals(r99, RouteManager.getInstance().getRouteWithNumber("99"));
        assertEquals(2, RouteManager.getInstance().getNumRoutes());

        RouteManager.getInstance().clearRoutes();
        assertEquals(0, RouteManager.getInstance().getNumRoutes());

    }



}
