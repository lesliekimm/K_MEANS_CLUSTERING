import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class KMeansClusteringUnitTest {

	@Test
	public void pointClassTest() {									// Test Point class
		Point firstPairTest = new Point(2, 5);						// Instantiate 4 ordered pairs
		Point secondPairTest = new Point(-3, 1);
		Point thirdPairTest = new Point(7, 4);
		Point fourthPairTest = new Point(secondPairTest);			// Use copy constructor
		Point firstTripletTest = new Point(1, 0, 7);				// Instantiate 4 ordered triplets
		Point secondTripletTest = new Point(2, 10, 4);
		Point thirdTripletTest = new Point(8, -5, 1);
		Point fourthTripletTest = new Point(thirdTripletTest);		// Use copy constructor
		
		assertFalse(firstPairTest.isEqual(firstTripletTest));		// Test isEqual function
		assertTrue(thirdTripletTest.isEqual(fourthTripletTest));	// Test isEqual function
		
		fourthPairTest.setXCoord(1);
		fourthPairTest.setYCoord(-2);
		
		secondTripletTest.setZCoord(4);
		
		assertEquals(fourthPairTest.getXCoord(), 1, 0);				// Test getXCoord function
		assertEquals(firstTripletTest.getYCoord(), 0, 0);			// Test getYCoord function
		assertEquals(secondTripletTest.getZCoord(), 4, 0);			// Test getZCoord function
		
		// Testing printPoint method
		PrintStream originalOutput = System.out;					// Name "standard" PrintStream
		OutputStream newOutput = new ByteArrayOutputStream();		// Instantiate new OutputStream
		PrintStream newPrintStream = new PrintStream(newOutput);	// Instantiate new PrintStream
		System.setOut(newPrintStream);								// Reassign "standard" output
	
		thirdPairTest.printPoint();
		assertEquals("(7.00, 4.00) ", newOutput.toString());		// Test printPoint		
		System.setOut(originalOutput);								// Reassign "standard" output
	}
	
	@Test
	public void pointListTest() {									// Test PointList Class
		PointList firstPairTestList = new PointList();
		Point one = new Point(6, 1);								// Instantiate 4 pair Points
		Point two = new Point(-3, 5);
		Point three = new Point(11, 4);
		Point four = new Point(23, 0);
		
		firstPairTestList.addPoint(one);							// Add 4 Points to PointList
		firstPairTestList.addPoint(two);
		firstPairTestList.addPoint(three);
		firstPairTestList.addPoint(four);
		
		// Test copy constructor and isEqual method
		PointList secondPairTestList = new PointList(firstPairTestList);
		assertTrue(firstPairTestList.isEqual(secondPairTestList));
		
		// Test deletePoint and getNumOfPoints method
		secondPairTestList.deletePoint(secondPairTestList.dataPointList.get(3));
		assertEquals(secondPairTestList.getNumOfPoints(), 3, 0);
		
		PointList firstTripletTestList = new PointList();
		Point five = new Point(6, 2, 0);							// Instantiate 4 triplet Points 
		Point six = new Point(-3, -1, 9);
		Point seven = new Point(9, 0, 12);
		Point eight = new Point(6, 7, 5);
		
		firstTripletTestList.addPoint(five);						// Add 4 Points to PointList
		firstTripletTestList.addPoint(six);
		firstTripletTestList.addPoint(seven);
		firstTripletTestList.addPoint(eight);
		
		// Test deletePoint and printList method
		firstTripletTestList.deletePoint(firstTripletTestList.dataPointList.get(0));
		firstTripletTestList.deletePoint(firstTripletTestList.dataPointList.get(1));
		firstTripletTestList.deletePoint(firstTripletTestList.dataPointList.get(1));		
		
		// Testing printList method
		PrintStream originalOutput = System.out;					// Name "standard" PrintStream
		OutputStream newOutput = new ByteArrayOutputStream();		// Instantiate new OutputStream
		PrintStream newPrintStream = new PrintStream(newOutput);	// Instantiate new PrintStream
		System.setOut(newPrintStream);								// Reassign "standard" output
	
		firstTripletTestList.printList();							// Test printList	
		assertEquals("(-3.00, -1.00, 9.00) " + "\n", newOutput.toString());	
		System.setOut(originalOutput);								// Reassign "standard" output
		
		PointList secondTripletTestList = new PointList();
		Point nine = new Point(-3, -1, 9);
		secondTripletTestList.addPoint(nine);
		
		assertTrue(secondTripletTestList.isEqual(firstTripletTestList));
		
		firstTripletTestList.clearList();							// Test clearList method
		assertEquals(firstTripletTestList.getNumOfPoints(), 0, 0);
	}
	
	@Test
	public void clusterTest() {										// Test Cluster class
		Cluster firstPairTestCluster = new Cluster(new Point(5, 1));// Instantiate Cluster
		Point one = new Point(6, 1);								// Instantiate 4 pair Points
		Point two = new Point(-3, 5);
		Point three = new Point(11, 4);
		Point four = new Point(23, 0);
		
		firstPairTestCluster.addPoint(one);							// Add 4 Points to PointList
		firstPairTestCluster.addPoint(two);
		firstPairTestCluster.addPoint(three);
		firstPairTestCluster.addPoint(four);
		
		Cluster firstTripletTestCluster = new Cluster(new Point(0, 1, 6));
		Point five = new Point(6, 2, 0);							// Instantiate 4 triplet Points 
		Point six = new Point(-3, -1, 9);
		Point seven = new Point(9, 0, 12);
		Point eight = new Point(6, 7, 5);
		
		firstTripletTestCluster.addPoint(five);						// Add 4 Points to PointList
		firstTripletTestCluster.addPoint(six);						// Tests overloaded addPoint
		firstTripletTestCluster.addPoint(seven);
		firstTripletTestCluster.addPoint(eight);
		
		// Copy constructor and isEqual method tests
		Cluster secondTripletCluster = new Cluster(firstTripletTestCluster);
		assertTrue(secondTripletCluster.isEqual(firstTripletTestCluster));
		
		// Test setMaxIntraDistance and getMaIntraDistance
		assertEquals(firstTripletTestCluster.getMaxIntraDistance(), 13.08, 0.1);
		
		// Test calculateMeanCluster
		assertEquals(9.25, firstPairTestCluster.calculateMeanOfCluster().getXCoord(), 0.1);
		assertEquals(2.50, firstPairTestCluster.calculateMeanOfCluster().getYCoord(), 0.1);
	}
	
	@Test
	public void clusterListTest() {									// Test ClusterList class
		// Instantiate Cluster
		Cluster firstPairTestCluster = new Cluster(new Point(5, 1));
		Point one = new Point(6, 1);								// Instantiate 4 pair Points
		Point two = new Point(-3, 5);
		Point three = new Point(11, 4);
		Point four = new Point(23, 0);
		firstPairTestCluster.addPoint(one);							// Add 4 Points to PointList
		firstPairTestCluster.addPoint(two);
		firstPairTestCluster.addPoint(three);
		firstPairTestCluster.addPoint(four);
		
		// Instantiate Cluster
		Cluster secondPairTestCluster = new Cluster(new Point(3, 2));
		Point five = new Point(1, 4);								// Instantiate 4 pair Points
		Point six = new Point(0, 3);
		Point seven = new Point(-1, 14);
		Point eight = new Point(17, 11);
		secondPairTestCluster.addPoint(five);						// Add 4 Points to PointList
		secondPairTestCluster.addPoint(six);
		secondPairTestCluster.addPoint(seven);
		secondPairTestCluster.addPoint(eight);
		
		ClusterList firstPairTestClusterList = new ClusterList();	// Instantiate ClusterList
		firstPairTestClusterList.addCluster(firstPairTestCluster);	// Test addCluster
		// Test getNumOfClustersInList
		assertEquals(firstPairTestClusterList.getNumOfClustersInList(), 1, 0);
		firstPairTestClusterList.addCluster(secondPairTestCluster);
		
		// Copy constructor and isEqual test
		ClusterList secondPairTestClusterList = new ClusterList(firstPairTestClusterList);
		assertTrue(secondPairTestClusterList.isEqual(firstPairTestClusterList));
		
		// Test deleteCluster
		secondPairTestClusterList.deleteCluster(secondPairTestClusterList.getKClusterList().get(1));
		assertEquals(secondPairTestClusterList.getNumOfClustersInList(), 1, 0);
		
		// Instantiate Cluster
		Cluster firstTripletTestCluster = new Cluster(new Point(5, 1, 4));
		Point nine = new Point(6, 1, 0);							// Instantiate 4 pair Points
		Point ten = new Point(-3, 5, 1);
		Point eleven = new Point(11, 4, -2);
		Point twelve = new Point(23, 0, 3);
		firstTripletTestCluster.addPoint(nine);						// Add 4 Points to PointList
		firstTripletTestCluster.addPoint(ten);
		firstTripletTestCluster.addPoint(eleven);
		firstTripletTestCluster.addPoint(twelve);
		
		// Instantiate Cluster
		Cluster secondTripletTestCluster = new Cluster(new Point(5, 1, 2));
		Point thirteen = new Point(1, 4);							// Instantiate 4 pair Points
		Point fourteen = new Point(0, 3);
		Point fifteen = new Point(-1, 14);
		Point sixteen = new Point(17, 11);
		secondTripletTestCluster.addPoint(thirteen);				// Add 4 Points to PointList
		secondTripletTestCluster.addPoint(fourteen);
		secondTripletTestCluster.addPoint(fifteen);
		secondTripletTestCluster.addPoint(sixteen);
		
		ClusterList firstTripletTestClusterList = new ClusterList();
		firstTripletTestClusterList.addCluster(firstTripletTestCluster);
		firstTripletTestClusterList.addCluster(secondTripletTestCluster);
		assertEquals(5, firstTripletTestClusterList.getKClusterList().get(1).getCentroid().getXCoord(), 0);
		assertEquals(1, firstTripletTestClusterList.getKClusterList().get(1).getCentroid().getYCoord(), 0);
		assertEquals(2, firstTripletTestClusterList.getKClusterList().get(1).getCentroid().getZCoord(), 0);
		
		assertEquals(firstTripletTestClusterList.getNumOfClustersInList(), 2, 0);
	}
	
	@Test
	public void KMeansClusteringTest() {
		// Initiate args to be array of Strings.
		// NOTE: First String is particular to my testing environment, replace with pathfile
		//       to data file
		String[] args = {"/Users/lesliekimm/Desktop/ADVANCED_PROGRAMMING/PROJECTS/PROJECT1/COSC150Project1/src/Data2.txt", "3", "5"};
		
		try {
			// Instantiate new KMeansClustering
			KMeansClustering algorithmTest = new KMeansClustering(args);
			assertFalse(algorithmTest.runIteration(true));			// Test runIteration method
		}
		catch (IOException error) {
			System.out.println(error);
		}
	}
}