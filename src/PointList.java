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
 * The PointList class creates an ArrayList object that holds Point
 * objects. Each PointList object also holds the number of Points in
 * the ArrayList.
 * 
 * Default Constructor creates a PointList object with an empty
 * ArrayList of Points and initializes numOfPoints to zero.
 * 
 * Copy Constructor creates a PointList object that takes a PointList
 * as a parameter and creates a deep copy of the PointList object.
 * 
 * Methods allow for retrieval of the dataPointList and numOfPoints,
 * setting the numOfPoints, adding and deleting Points to/from a
 * PointList, clearing the PointList, printing the PointList and
 * determines whether two PointLists are equal.
 * 
 */

import java.util.ArrayList;

public class PointList {
	// Private members are the ArrayList containing the Points and
	// the number of Points in each PointList object.
	protected ArrayList<Point> dataPointList;					// ArrayList of all Points
	protected int numOfPoints;									// Number of Points in ArrayList
	
	/**
	 * Default Constructor: Instantiates an empty ArrayList<Point>
	 * and initializes numOfPoints to zero.
	 * @param none
	 */
	public PointList() {
		dataPointList = new ArrayList<Point>();					// Instantiate new ArrayList
		numOfPoints = 0;										// Initialize numOfPoints
	}
	
	/**
	 * Copy Constructor: Creates a PointList object and takes in a
	 * PointList as a parameter to initialize the private members
	 * dataPointList and numOfPoints.
	 * @param listToCopy
	 */
	public PointList(PointList listToCopy) {
		dataPointList = new ArrayList<Point>();					// Instantiate new ArrayList
		numOfPoints = 0;										// Initialize numOfPoints
		
		// Iterate through listToCopy and create a deep copy of the list
		for (int index = 0; index < listToCopy.numOfPoints; index++) {	
			// Instantiate new Point of Point object from listToCopy
			Point pointToAdd = new Point(listToCopy.dataPointList.get(index));
			// Add new Point to this.dataPointList
			addPoint(pointToAdd);
		}
	}
	
	/**
	 * Getter: Returns the ArrayList of Points.
	 * @return dataPointList
	 */
	// Return the ArrayList of Points
	public ArrayList<Point> getDataPointList() { return dataPointList; }
	
	/**
	 * Getter: Returns the number of Points in the ArrayList.
	 * @return numOfPoints
	 */
	public int getNumOfPoints() { return numOfPoints; } 		// Return the number of Points
	
	/**
	 * This method adds a Point to the ArrayList and increments
	 * the private member numOfPoints.
	 * @param pointToAdd
	 */
	public void addPoint(Point pointToAdd) {
		dataPointList.add(pointToAdd);							// Append Point to ArrayList
		numOfPoints++;											// Increment numOfPoints
	}
	
	/**
	 * This method deletes a Point from the ArrayList and decrements
	 * the private member numOfPoints.
	 * @param pointToDelete
	 */
	public void deletePoint(Point pointToDelete) {	
		// Iterate through the ArrayList
		for (int index = 0; index < numOfPoints; index++) {	
			
			// Find out if Point at the index equals Point passed in
			if (dataPointList.get(index) == pointToDelete) {	// If equal
				dataPointList.remove(index);					// Remove Point from ArrayList
				numOfPoints--;									// Decrement numOfPoints
			}
		}
	}
	
	/**
	 * This method clears all Points from the ArrayList.
	 * @param none
	 */
	public void clearList() {
		// Iterate through the ArrayList from last to first
		for (int index = numOfPoints - 1; index >= 0; index--)
			deletePoint(dataPointList.get(index));				// Remove Point from ArrayList
	}
	
	/**
	 * This method prints all Points from the ArrayLIst.
	 * @param none
	 */
	public void printList() {
		for (int index = 0; index < numOfPoints; index++)		// Iterate through ArrayList
			dataPointList.get(index).printPoint();				// Print each Point
		System.out.println("");
	}
	
	/**
	 * This method takes two PointList objects and compares the
	 * dataPointLists and numOfPoints variables. If they are equal,
	 * returns true. Otherwise, false is returned.
	 * @param second
	 * @return isEqual
	 */
	public Boolean isEqual(PointList second) {
		Boolean isEqual = false;								// Set isEqual to false
		
		// If numOfPoints is equal for both PointLists
		if (this.numOfPoints == second.numOfPoints) {
			isEqual = true;										// Set isEqual to true
			
			// Iterate through all Points of both lists
			for (int index = 0; index < numOfPoints; index++) {
				
				// If Point at index doesn't equal for both PointLists
				if (this.dataPointList.get(index).isEqual(second.dataPointList.get(index)) == false)
					isEqual = false;							// Set isEqual to false
			}
		}
			
		return isEqual;
	}
}