/**
 * Project 1
 * Name: Leslie Kim
 * E-mail: lk584@georgetown.edu
 * Instructor: Singh
 * COSC 150
 * 
 * In accordance with the class policies and Georetown's Honor Code,
 * I certify that, with the exceptions of the lecture notes and those
 * items noted below, I have neither given nor received any assistance
 * on this project.
 * 
 * DESCRIPTION:
 * The Cluster class is an extension of the PointList class. It
 * inherits the private members dataPointList and numOfPoints from the
 * PointList class and also contains a centroid and the maximum intra-
 * distance of each Cluster object.
 * 
 * No default constructor has been defined because instantiating a
 * Cluster object with a centroid (0, 0) or (0, 0, 0) and no Points
 * would skew the data set, therefore no default constructor should
 * ever be called.
 * NOTE: TA (Kyle) confirmed defining a default constructor was not
 * something to worry about as I initially had it throwing an Exception.
 *  
 * Constructor creates a Cluster object that takes a centroid (Point) as
 * a parameter and initializes the centroid, the ArrayList of Points, the
 * number of Points and the maximum intra-distance between all Points.
 * 
 * Copy Constructor takes a Cluster object as a parameter and creates a
 * deep copy of the Cluster object.
 * 
 * Methods allow for retrieval and setting of the centroid and maximum
 * intra-distance, calculating the intra-distance (using the
 * Eculidean Distance formula) and determining if two Clusters are equal.
 *
 */

import java.util.ArrayList;

public class Cluster extends PointList {
	// Private members are the centroid and the maximum intra-distance.
	private Point centroid;										// Centroid
	private double maxIntraDistance;							// Maximum intra-distance
	
	/**
	 * Constructor: Creates a Cluster object and takes in a Point as
	 * parameters to initialize the centroid. The ArrayList, number of
	 * Points and maximum intra-distance members are also initialized.
	 * @param newCentroid
	 */
	public Cluster(Point newCentroid) {
		centroid = new Point(newCentroid);						// Initialize centroid
		dataPointList = new ArrayList<Point>();					// Initialize dataPointList
		numOfPoints = 0;										// Initialize numOfPoints
		maxIntraDistance = 0.00;								// Initialize maxIntraDistance
	}
	
	/**
	 * Copy Constructor: Creates a Cluster object and takes in a Cluster
	 * as a parameter to initialize private members.
	 * @param clusterToCopy
	 */
	public Cluster(Cluster clusterToCopy) {
		centroid = new Point(clusterToCopy.centroid);			// Initialize centroid
		dataPointList = new ArrayList<Point>();					// Initialize dataPointList
		numOfPoints = 0;										// Initialize numOfPoints
		maxIntraDistance = 0.00;								// Initialize maxIntraDistance
		
		// Iterate through clusterToCopy and create a deep copy
		for (int index = 0; index < clusterToCopy.numOfPoints; index++) {
			// Instantiate a new Point object of Point from clusterToCopy
			Point pointToAdd = new Point(clusterToCopy.dataPointList.get(index));
			// Add Point to this.dataPointList
			addPoint(pointToAdd);
		}
	}
	
	/**
	 * Getter: Returns Point centroid.
	 * @return centroid
	 */
	public Point getCentroid() { return centroid; }				// Return centroid
	
	/**
	 * Getter: Returns the maximum intra-distance.
	 * @return maxIntraDistance
	 */
	// Return maximum intra-distance
	public double getMaxIntraDistance() { return maxIntraDistance; }
	
	/**
	 * Setter: Sets the centroid to Point passed in as parameter.
	 * @param centroidToSet
	 */
	public void setCentroid(Point centroidToSet) {
		centroid.setXCoord(centroidToSet.getXCoord());			// Set x-coordinate of centroid
		centroid.setYCoord(centroidToSet.getYCoord());			// Set y-coordinate of centroid
		if (!centroid.isOrderedPair)							// If ordered triplet
			centroid.setZCoord(centroidToSet.getZCoord());		// Set z-coordinate of centroid
	}
	
	/**
	 * This method is called by the findMaxIntraDistance() method
	 * and takes in two Points as parameters. The distance is calculated
	 * using the Eculidean Distance formula (distance = sqrt((x1 - x2)^2
	 * + (y1 - y2)^2) or distance = sqrt((x1 - x2)^2 + (y1 - y2) ^2)
	 * + (z1 - z2)^2)) and returned.
	 * @param firstPoint
	 * @param secondPoint
	 * @return distance
	 */
	private double calculateIntraDistance(Point firstPoint, Point secondPoint) {
		double zDifferenceSqred = 0.00;							// Set to 0 for ordered pairs
		
		// Square the difference of the two x-coordinates
		double xDifferenceSqred = Math.pow((firstPoint.getXCoord() - secondPoint.getXCoord()), 2);
		// Square the difference of the two y-coordinates
		double yDifferenceSqred = Math.pow((firstPoint.getYCoord() - secondPoint.getYCoord()), 2);
		// If calculating distance of ordered triplets
		if ((!firstPoint.isOrderedPair) && (!secondPoint.isOrderedPair))
			// Square the difference of the two z-coordinates
			zDifferenceSqred = Math.pow((firstPoint.getZCoord() - secondPoint.getZCoord()), 2);
		
		// Return the square root of the two previous calculations
		return Math.sqrt(xDifferenceSqred + yDifferenceSqred + zDifferenceSqred);
	}
	
	/**
	 * Setter: Finds the maximum intra-distance in the cluster and
	 * sets the private member variable maxIntraDistance.
	 * @param none
	 */
	public void setMaxIntraDistance() {
		// Initialize a temp variable that will be used to compare intra-distances between all
		// sets of two Points in a Cluster
		maxIntraDistance = 0.00;
		double tempIntraDistance = 0.00;						

		// Iterate through all Points in the dataPointList
		for (int outIndex = 0; outIndex < numOfPoints; outIndex++) {
			
			// Iterate through all Points after the outerIndex in dataPointList
			for (int inIndex = outIndex + 1; inIndex < numOfPoints; inIndex++) {
				// Get first Point
				Point firstPoint = new Point(dataPointList.get(outIndex));
				// Get second Point
				Point secondPoint = new Point(dataPointList.get(inIndex));
				// Calculate the intra-distance between the two Points
				tempIntraDistance = calculateIntraDistance(firstPoint, secondPoint);
				
				// If the tempIntraDistance is larger than the maxIntraDistance, set
				// the maxIntraDistance to the tempIntraDistance value
				if (tempIntraDistance > maxIntraDistance)
					maxIntraDistance = tempIntraDistance;
			}
		}
	}
	
	/**
	 * This method overrides addPoint(Point) method from PointList class.
	 * It adds a Point to the ArrayList and increments the private member
	 * numOfPoints as well as calculating the maximum intra-distance.
	 * @param pointToAdd
	 */
	public void addPoint(Point pointToAdd) {
		dataPointList.add(pointToAdd);							// Append Point to ArrayList
		numOfPoints++;											// Increment numOfPoints
		setMaxIntraDistance();									// Set maximum intra-distance
	}
	
	/**
	 * This method calculates the mean of a Cluster. The x-coordinate
	 * is calculated by adding all x-coordinate values and dividing
	 * by the total number of Points in the Cluster. The y-coordinate
	 * is calculated by adding all y-coordinate values and dividing
	 * by the total number of Points in the Cluster. For ordered triplets
	 * the z-coordinate is calculated by adding all the z-coordinate
	 * values and dividing by the total number of Points in the Cluster.
	 * This will set the centroid of the Cluster object.
	 * @return newMean
	 */
	public Point calculateMeanOfCluster() {
		double newXValue = 0.00;								// Initialize new x-coordinate
		double newYValue = 0.00;								// Initialize new y-coordinate
		double newZValue = 0.00;								// Initialize new z-coordinate
		Point newMean;
		
		if (centroid.isOrderedPair) {							// If Cluster of ordered pairs
			newMean = new Point(0, 0);							// Initialize newMean
			
			// If a Cluster has no Points in it, new centroid remains the same
			if (numOfPoints == 0) {
				newMean.setXCoord(centroid.getXCoord());		// Set x-coordinate
				newMean.setYCoord(centroid.getYCoord());		// Set y-coordinate
			}
			
			// If a Cluster has Points in it, calculate new centroid
			else {
				// Iterate through Cluster
				for (int index = 0; index < numOfPoints; index++) {
					// Add x-coordinate to newXValue
					newXValue += dataPointList.get(index).getXCoord();
					// Add y-coordinate to newYValue
					newYValue += dataPointList.get(index).getYCoord();
					}
			
				newXValue /= numOfPoints;						// Divide by number of Points
				newYValue /= numOfPoints;						// Divide by number of Points
				newMean.setXCoord(newXValue);					// Set x-coordinate
				newMean.setYCoord(newYValue);					// Set y-coordinate
			}
		}
		
		else {													// If Cluster of ordered triplets
			newMean = new Point(0, 0, 0);
			
			// If a Cluster has no Points in it, new centroid remains the same
			if (numOfPoints == 0) {
				newMean.setXCoord(centroid.getXCoord());		// Set x-coordinate
				newMean.setYCoord(centroid.getYCoord());		// Set y-coordinate
				newMean.setZCoord(centroid.getZCoord());		// Set z-coordinate
			}
			
			// If a Cluster has Points in it, calculate new centroid
			else {
				// Iterate through Cluster
				for (int index = 0; index < numOfPoints; index++) {
					// Add x-coordinate to newXValue
					newXValue += dataPointList.get(index).getXCoord();
					// Add y-coordinate to newYValue
					newYValue += dataPointList.get(index).getYCoord();
					// Add z-coordinate to newYValue
					newZValue += dataPointList.get(index).getZCoord();
					}
			
				newXValue /= numOfPoints;						// Divide by number of Points
				newYValue /= numOfPoints;						// Divide by number of Points
				newZValue /= numOfPoints;						// Divide by number of Points
				newMean.setXCoord(newXValue);					// Set x-coordinate
				newMean.setYCoord(newYValue);					// Set y-coordinate
				newMean.setZCoord(newZValue);					// Set z-coordinate
			}
		}
		
		return newMean;
	}
	
	/**
	 * This method takes two Cluster objects and compares the
	 * dataPointLists, numOfPoints, centroid and maxIntraDistance
	 * variables. If they are equal, returns true. Otherwise,
	 * false is returned.
	 * @param second
	 * @return isEqual
	 */
	public Boolean isEqual(Cluster second) {
		Boolean isEqual = false;								// Set isEqual to false
		
		// If numOfPoints, centroid and maxIntraDistance is equal for both Clusters
		if ((this.numOfPoints == second.numOfPoints) && ((this.centroid).isEqual(second.centroid))
				&& (this.maxIntraDistance == second.maxIntraDistance)) {
			isEqual = true;										// Set isEqual to true
			
			// Iterate through all Points of both Clusters
			for (int index = 0; index < numOfPoints; index++) {
				
				// If Point at index doesn't equal for both Cluster
				if (!((this.dataPointList.get(index)).isEqual(second.dataPointList.get(index))))
					isEqual = false;							// Set isEqual to false
			}
		}
			
		return isEqual;
	}
}