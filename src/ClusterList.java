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
 * The ClusterList class is an ArrayList of Clusters and holds the 
 * number of Clusters and overall max intra-distance in each object.
 * 
 * Default constructor initiates a new ArrayList of Clusters and sets
 * numOfClustersInList and overallMaxIntraDistance to zero.
 * 
 * Copy Constructor takes a ClusterList object as a parameter and creates
 * a deep copy of the ClusterList object.
 * 
 * Methods include retrieval of kClusterList, numOfClustersInList and
 * overallMaxIntraDistance, adding and deleting Clusters, clearing the
 * ArrayList of Clusters, printing the ArrayList of Clusters, finding
 * the maximum intra-distance across all Clusters and determining if two
 * ClusterLists are equal.
 *
 */

import java.util.ArrayList;

public class ClusterList {
	// Private members are the ArrayList containing the Clusters, the
	// number of Clusters in each ClusterList object and the overall
	// maximum intra-distance of all Clusters.
	private ArrayList<Cluster> kClusterList;				// ArrayList of Clusters
	private int numOfClustersInList;						// Number of Clusters in ArrayList
	private double overallMaxIntraDistance;					// Overall maximum intra-distance
	
	/**
	 * Default Constructor: Instantiates an empty ArrayList<Cluster>
	 * and initializes numOfClustersInList and overallMaxIntraDistance
	 * to zero.
	 */
	public ClusterList() {
		kClusterList = new ArrayList<Cluster>();			// Instantiate new ArrayList
		numOfClustersInList = 0;							// Initialize numOfClustersInList
		overallMaxIntraDistance = 0.00;						// Initialize overallMaxIntraDistance
	}
	
	/**
	 * Copy Constructor: Creates a ClusterList object and takes in a
	 * ClusterList as a parameter to create a deep copy.
	 * @param clusterListToCopy
	 */
	public ClusterList(ClusterList clusterListToCopy) {
		kClusterList = new ArrayList<Cluster>();			// Instantiate new ArrayList
		numOfClustersInList = 0;							// Initialize numOfClustersInList
		// Initialize overallMaxIntraDistance
		overallMaxIntraDistance = clusterListToCopy.overallMaxIntraDistance;
		
		// Iterate through clusterListToCopy and create a deep copy of the list
		for (int index = 0; index < clusterListToCopy.numOfClustersInList; index++) {
			// Instantiate a new Cluster of Cluster object from clusterListToCopy
			Cluster clusterToAdd = new Cluster(clusterListToCopy.kClusterList.get(index));
			// Add new Cluster to this.kClusterList
			addCluster(clusterToAdd);
		}
	}
	
	/**
	 * Getter: Returns the ArrayList of Clusters.
	 * @return kClusterList
	 */
	public ArrayList<Cluster> getKClusterList() {
		return kClusterList;								// Return the ArrayList of Clusters
	}
	
	/**
	 * Getter: Returns the number of Clusters in the ArrayList
	 * @return numOfClustersInList
	 */
	public int getNumOfClustersInList() {
		return numOfClustersInList;							// Return the number of Clusters
	}
	
	/** 
	 * Getter: Returns the overall maximum intra-distance of
	 * all Clusters.
	 * @return overallMaxIntraDistance
	 */
	public double getOverallMaxIntraDistance() {
		return overallMaxIntraDistance;						// Return max overall intra-distance
	}
	
	/**
	 * This method adds a Cluster to the ArrayList and increments
	 * numOfClustersInList.
	 * @param clusterToAdd
	 */
	public void addCluster(Cluster clusterToAdd) {
		kClusterList.add(clusterToAdd);						// Add Cluster to ArrayList
		numOfClustersInList++;								// Increment numOfClustersInList 
	}
	
	/**
	 * This method deletes a Cluster from the ArrayList and decrements
	 * the numOfClustersInList.
	 * @param clusterToDelete
	 */
	public void deleteCluster(Cluster clusterToDelete) {
		// Iterate through the ArrayList
		for (int index = 0; index < numOfClustersInList; index++) {
			
			// Find out if Cluster at index equals Cluster passed in
			if (kClusterList.get(index) == clusterToDelete) {
				kClusterList.remove(index);					// Delete Cluster from ArrayList
				numOfClustersInList--;						// Decrement numOfClustersInList
			}
		}
	}
	
	/**
	 * This method clears all Clusters from the ArrayList.
	 * @param none
	 */
	public void clearClusterList() {
		// Iterate through the ArrayList from last to first
		for (int index = numOfClustersInList - 1; index >= 0; index--)
			deleteCluster(kClusterList.get(index));			// Remove Cluster from ArrayList
	}
	
	/**
	 * This method prints all Clusters from the ArrayList.
	 * @param none
	 */
	public void printClusterList() {
		System.out.println("Clusters:\n");					// Output header
		
		// Iterate through list of Clusters
		for (int index = 0; index < numOfClustersInList; index++) {
			// Output centroid
			System.out.print("Centroid: ");
			kClusterList.get(index).getCentroid().printPoint();
			// Output Points in Cluster
			System.out.print("\nPoints in cluster: ");
			kClusterList.get(index).printList();	
			// Set maximum intra-distance for Cluster and output
			double maxIntraDistanceToPrint = kClusterList.get(index).getMaxIntraDistance();
			System.out.format("Intra-cluster distance: %.2f", maxIntraDistanceToPrint);
			System.out.println("\n");
		}
		
		// Find overall maximum intra-distance and output
		findOverallMaxIntraDistance();						
		System.out.format("Maximum intra-cluster distance: %.2f", overallMaxIntraDistance);
		System.out.println("\n");
	}
	
	/**
	 * This method finds the overall maximum intra-distance across
	 * all Clusters to measure the KMeansClustering Quality.
	 * @param none
	 */
	public void findOverallMaxIntraDistance() {
		overallMaxIntraDistance = 0.00;
		
		// Iterate through all Clusters in kClusterList
		for (Cluster getMaxFromCluster : kClusterList) {
			// Retrieve maxIntraDistance of Cluster
			double tempMaxIntraDistance = getMaxFromCluster.getMaxIntraDistance();
			
			// If retrieved maxIntraDistance is larger than overallMaxIntraDistance
			if (tempMaxIntraDistance > overallMaxIntraDistance)
				// Set overallMaxIntraDistance to retrieve maxIntraDistance
				overallMaxIntraDistance = tempMaxIntraDistance;
		}
	}

	/**
	 * This method takes two ClusterList objects and compares the
	 * kClusterList, numOfClustersInList and overallMaxIntraDistance.
	 * If they are equal, returns true. Otherwise, false is returned.
	 * @param second
	 * @return isEqual
	 */
	public Boolean isEqual(ClusterList second) {
		Boolean isEqual = false;						// Set isEqual to false
		
		// If numOfClustersInList and overallMaxIntraDistance are equal for both ClusterList
		if ((this.numOfClustersInList == second.numOfClustersInList)
				&& (this.overallMaxIntraDistance == second.overallMaxIntraDistance)) {
			isEqual = true;								// Set isEqual to true
			
			// Iterate through all Clusters of both ClusterLists
			for (int index = 0; index < numOfClustersInList; index++) {
				
				// If Cluster at index doesn't equal for both ClusterList
				if (!((this.kClusterList.get(index)).isEqual(second.kClusterList.get(index))))
					isEqual = false;					// Set isEqual to false
			}
		}
			
		return isEqual;
	}
}