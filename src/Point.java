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
 * The Point class creates an object of type Point. If the Point is an
 * ordered pair, it holds x- and y-coordinates. If the Point is an
 * ordered triplet, it holds x-, y- and z-coordinates. Each Point holds
 * a Boolean that indicates whether it is an ordered pair or triplet.
 * 
 * No default constructor has been defined because instantiating a
 * Point object with coordinates equal to 0 (a Point with coordinates
 * (0, 0) or (0, 0, 0) would skew the data set, therefore no default
 * constructor should ever be called.
 * NOTE: TA (Kyle) confirmed defining a default constructor was not
 * something to worry about as I initially had it throwing an Exception.
 * 
 * Constructor creates a Point object that takes doubles as parameters
 * to initialize the coordinates. If two doubles are passed in,
 * isOrderedPair is set to true. If three doubles are passed in,
 * isOrderd Pair is set to false to indicate it is an ordered triplet.
 * 
 * Copy constructor takes a Point object as a parameter and creates a
 * deep copy of the Point object.
 * 
 * Methods allow for coordinate values to be retrieved and set, printing
 * of a Point and determining if two Points are equal.
 *
 */

public class Point {
	// Private members are the coordinates of Point and a Boolean
	// that indicates if the Point is an ordered pair or triplet.
	private double xCoord;										// x-coordinate
	private double yCoord;										// y-coordinate
	private double zCoord;										// z-coordinate
	Boolean isOrderedPair;										// Indicates if pair or triplet
	
	/**
	 * Constructor: Creates a Point object and takes in two doubles
	 * as parameters to initialize the private members xCoord and
	 * yCoord. isOrderedPair is set to true.
	 * @param xValue
	 * @param yValue
	 */
	public Point(double xValue, double yValue) {
		xCoord = xValue;										// Initialize xCoord
		yCoord = yValue;										// Initialize yCoord
		isOrderedPair = true;									// This is an ordered pair
	}
	
	/**
	 * Constructor: Creates a Point object and takes in three doubles
	 * as parameters to initialize the private members xCoord, yCoord
	 * and zCoord. isOrderedPair is set to false.
	 * @param xValue
	 * @param yValue
	 * @param zValue
	 */
	public Point(double xValue, double yValue, double zValue) {
		xCoord = xValue;										// Initialize xCoord
		yCoord = yValue;										// Initialize yCoord
		zCoord = zValue;										// Initialize zCoord
		isOrderedPair = false;									// This is an ordered triplet
	}
	
	/**
	 * Copy Constructor: Creates a Point object and takes in a Point
	 * as a parameter to initialize the coordinates and isOrderedPair
	 * value.
	 * @param pointToCopy
	 */
	public Point(Point pointToCopy) {
		xCoord = pointToCopy.xCoord;							// Initialize xCoord
		yCoord = pointToCopy.yCoord;							// Initialize yCoord
		isOrderedPair = true;									// Initialize as ordered pair
		if (!pointToCopy.isOrderedPair)	{						// If this is an ordered triplet
			zCoord = pointToCopy.zCoord;						// Initialize zCoord
			isOrderedPair = false;								// Indicate ordered triplet
		}
	}
	
	/**
	 * Getter: Returns the x-coordinate of a Point.
	 * @return xCoord
	 */
	public double getXCoord() { return xCoord; }				// Return x-coordinate
	
	/**
	 * Getter: Returns the y-coordinate of a Point.
	 * @return yCoord
	 */
	public double getYCoord() { return yCoord; }				// Return y-coordinate
	
	/**
	 * Getter: Returns the z-coordinate of a Point.
	 * @return zCoord
	 */
	public double getZCoord() { return zCoord; }				// Return z-coordinate
	
	/**
	 * Setter: Sets the xCoord to x-coordinate passed in.
	 * @param xValue
	 */
	public void setXCoord(double xValue) { xCoord = xValue; }	// Set x-coordinate
	
	/**
	 * Setter: Sets the yCoord to y-coordinate passed in.
	 * @param yValue
	 */
	public void setYCoord(double yValue) { yCoord = yValue; }	// Set y-coordinate
	
	/**
	 * Setter: Sets the zCoord to z-coordinate passed in.
	 * @param zValue
	 */
	public void setZCoord(double zValue) { zCoord = zValue; }	// Set z-coordinate
	
	/**
	 * This method will print out the coordinates of a Point in
	 * (0, 0) format for an ordered pair and (0, 0, 0) for an
	 * ordered triplet.
	 * @param none
	 * Source: http://docs.oracle.com/javase/tutorial/java/data/
	 * numberformat.html
	 */
	public void printPoint() {
		// All numeric values are output to two decimal places
		System.out.format("(%.2f", xCoord);						// Format xCoord & print (xCoord		
		System.out.format(", %.2f", yCoord);					// Format yCoord & print , yCoord
		if (!this.isOrderedPair)								// If ordered triplet
			System.out.format(", %.2f", zCoord);				// Format zCoord & print , zCoord
		System.out.print(") ");									// Print )
	}
	
	/**
	 * This method takes two Point objects and compares the
	 * coordinate values. If they are equal, returns true.
	 * Otherwise, false is returned.
	 * @param second
	 * @return isEqual
	 */
	public Boolean isEqual(Point second) {
		Boolean isEqual = false;								// Set isEqual to false
		
		// If x- and y-coordinates of both Points are equal
		if ((this.xCoord == second.xCoord) && (this.yCoord == second.yCoord))
			isEqual = true;
			
			// If this is an ordered triplet
			if ((!this.isOrderedPair) && (!second.isOrderedPair)) {
				if (this.zCoord != second.zCoord)				// If z-coordinates are equal
					isEqual = false;							// Set isEqual to true
			}
		
		return isEqual;											// Return isEqual
	}
}