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
 * The KMeansClustering class creates an object that runs the K-Means
 * Clustering algorithm. To run the program, one or three command
 * line arguments should be passed in. The first command line argument
 * is a filename that contains rectangular coordinates as data points.
 * (Optional: The second and third command line arguments are the number
 * of Clusters (K) and iterations the algorithm will have and run. If
 * these arguments are not entered, by default, there will be K = 3
 * Clusters and 5 iterations.) The program reads in a file of rectangular
 * coordinates as Point objects and stores them in a PointList object.
 * K Points are selected at random and a Cluster object is instantiated
 * with each random Point as it's centroid. Each Point from the file is
 * assigned to a Cluster by calculating the distance from each centroid
 * and assigning it to the Cluster with the closest centroid. For each
 * Cluster, the intra-cluster distance is calculated between all sets of
 * two Points. The largest distance measures the quality of that particular
 * Cluster and is assigned to the maxIntraDistance of that Cluster. The
 * overallMaxIntraDistance is used to assess the quality of the entire
 * algorithm by taking the largest maxIntraDistance among all Clusters.
 * The mean of each Cluster is calculated and Clusters are created with 
 * the new centroids. Each Point is then re-assigned to the Cluster with
 * the closest centroid. This process repeats for the number of iterations
 * specified or until convergence occurs (an iteration produces the same
 * Clusters as the previous iteration).
 * 
 * Methods include retrieval of kClusterList, numOfClustersInList and
 * overallMaxIntraDistance, adding and deleting Clusters, clearing the
 * ArrayList of Clusters, printing the ArrayList of Clusters and finding
 * the maximum intra-distance across all Clusters.
 * 
 * INSTRUCTIONS:
 * One or three command line arguments must be input. The first is the
 * filename, followed by the number of Clusters and last is the number
 * of iterations to run. The second and third command line arguments
 * are option. If only filename is input, by default, there will be
 * K = 3 Clusters and 5 iterations.
 *
 */

import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class KMeansClustering {
	// Private members are the filename, number of Clusters, number of
	// Iterations, a PointList of the original data read in and a
	// ClusterList of K Clusters.
	private String filename;								// Filename
	private int numOfClusters;								// Number of Clusters
	private int numOfIterations;							// Number of iterations
	private PointList originalData;							// Original data of Points
	private ClusterList kClusters;							// List of all Clusters
	private PointList centroidList;							// List of all centroids
	private Boolean convergence;							// States if convergence has happened
	private Boolean isOrderedPair;							// To indicate if pair or triplet
	
	/**
	 * Constructor instantiates a KMeansClustering object and reads in
	 * the filename (optionally, the number of Clusters and iterations
	 * as arguments as well, but will be set to 3 and 5 respectively if
	 * arguments are not passed in). originalData is initialized as an
	 * empty PointList and kClusters is initialized as an empty
	 * ClusterList. An IOException will be thrown if there is an error
	 * with extracting command line arguments.
	 * @param args
	 * @throws IOException
	 */
	public KMeansClustering(String[] args) throws IOException {
		numOfClusters = 3;									// By default, K = 3 Clusters
		numOfIterations = 5;								// By default, 5 iterations
		originalData = new PointList();						// Initialize empty originalData
		kClusters = new ClusterList();						// Initialize empty kClusters
		centroidList = new PointList();						// Initialize empty centroidList
		convergence = false;								// Initialize convergence to false
		isOrderedPair = true;								// Initialize isOrderedPair to true
		
		// Check to see if correct number of arguments were passed in and parse arguments
		if ((args.length == 1) || (args.length == 3)) {			// Must be 1 argument or 3
			filename = args[0];								// Read in filename
			
			if (args.length == 3) {							// If 3 arguments are passed in
				numOfClusters = Integer.parseInt(args[1]);	// Read in number of Clusters
				numOfIterations = Integer.parseInt(args[2]);// Read in number of iterations
			}
			
			// Read in data as Points from file and append Points to originalData PointList
			// If Points are ordered triplets, isOrderdPair will be set to false
			isOrderedPair = readInData(filename);
				
		}
		
		// Throw FileNotFoundException if command line arguments are entered incorrectly
		else {
			throw new FileNotFoundException("Please enter the filename as the first command "
					+ "line argument\nor filename, number of Clusters and number of iterations.");
		}	
	}
	
	/**
	 * This method is called by the constructor to read in the data from
	 * the filename and instantiate a Point object for each rectangular
	 * coordinate. Each Point is appended to the originalData PointList.
	 * If there is an IO error, an IOException will be caught. If there
	 * is an error reading in coordinates, a NumberFormatException will 
	 * be caught.
	 * @param filename
	 * Source: http://stackoverflow.com/questions/2864117/read-data-from-
	 * a-text-file-using-java
	 */
	private Boolean readInData(String filename) {
		Boolean orderedPairs = true;
		
		// Try/catch block to handle IOException or NumberFormatEception
		try {
			File inputFile = new File(filename);			// Instantiate a File object
			Scanner scannedFile = new Scanner(inputFile);	// Instantiate a Scanner of File
			
			// Read in each line and add to orignalData
			while (scannedFile.hasNextLine()) {				
				String line = scannedFile.nextLine();		// Read in each line
				
				// Figure out if data file contains ordered pairs or triplets
				int commas = 0;								// Used to count commas in each line
				
				// Count commas in each line - iterate through the chars of each line
				for (int index = 0; index < line.length(); index++) {
					if (line.charAt(index) == ',')			// Increment commas if char is comma
						commas++;
				}
				
				// If only one comma, data contains ordered pairs
				if (commas == 1) {
					String[] coordinate = line.split(",");	// Create array of coordinate values
					// Parse for the x- and y-coordinate
					double xValue = Double.parseDouble(coordinate[0]);
					double yValue = Double.parseDouble(coordinate[1]);
					// Instantiate Point and add to originalData
					Point pointToAdd = new Point(xValue, yValue);
					originalData.addPoint(pointToAdd);
				}
				
				// If two commas, data contains ordered triplets
				else if (commas == 2) {
					String[] coordinate = line.split(",");	// Create array of coordinate values
					// Parse for the x-, y- and z-coordinate
					double xValue = Double.parseDouble(coordinate[0]);
					double yValue = Double.parseDouble(coordinate[1]);
					double zValue = Double.parseDouble(coordinate[2]);
					// Instantiate Point and add to originalData
					Point pointToAdd = new Point(xValue, yValue, zValue);
					originalData.addPoint(pointToAdd);
				}
			}
			
			scannedFile.close();							// Close scanned file
		}
		
		// Catch IOException error if there is an error reading in data
		catch (IOException error) {
			System.out.println("Error opening file: " + error);
		}
		
		// Catch NumberFormatException if there is an error reading in coordinates
		catch (NumberFormatException error) {
			System.out.println("Error reading in coordinate: " + error);
		}
		
		return orderedPairs;								// Return orderedPairs
	}
	
	/**
	 * Getter: Returns the filename used for input data.
	 * @return filename
	 */
	public String getFilename() { return filename; }		// Return filename
	
	/**
	 * Getter: Returns the number of Clusters indicated by user.
	 * @return numOfClusters
	 */
	public int getNumOfClusters() { return numOfClusters; }	// Return numOfClusters
	
	/**
	 * Getter: Returns the number of Iterations indicated by user.
	 * @return numOfIterations
	 */
	// Return numOfIterations
	public int getNumOfIterations() { return numOfIterations; }
	
	/**
	 * Getter: Returns the originalData PointList.
	 * @return originalData
	 */
	// Return originalData
	public PointList getOriginalData() { return originalData; }
	
	/**
	 * Getter: Returns the kClusters ClusterList.
	 * @return kClusters
	 */
	public ClusterList getKClusters() { return kClusters; }	// Return kClusters
	
	/**
	 * Getter: Returns the list of centroids.
	 * @return centroidList
	 */
	// Return centroidList
	public PointList getCentroidList() { return centroidList; }
	
	/**
	 * Getter: Returns whether or not convergence occurred.
	 * @return convergence
	 */
	public Boolean getConvergence() { return convergence; }	// Return convergence
	
	/**
	 * Getter: Returns whether data set contains ordered pairs.
	 * @return isOrderedPair
	 */	
	// Return isOrderedPair
	public Boolean getIsOrderedPair() { return isOrderedPair; }
	
	/**
	 * This method runs one iteration of the K-Means Clustering algorithm.
	 * If it is the first iteration, K random Points will be selected to use
	 * as centroids for K clusters. If it is not the first iteration, the mean
	 * of each exisiting cluster will be calculated to use as new centroids
	 * for K clusters. Each Point in the data set is assigned to one of the
	 * Clusters. This method takes in a Boolean value that indicates if it
	 * is the first iteration. It also returns a Boolean value that indicates
	 * whether it is still the first iteration.
	 * @param isFirstIteration
	 * @return firstIteration
	 */
	public Boolean runIteration(Boolean isFirstIteration) {
		Boolean firstIteration = isFirstIteration;			// Indicates if running first iteration
		ClusterList copy = new ClusterList(this.kClusters);

		// If running first iteration, select K random Points for centroids and assign
		// each Point to a Cluster
		if (isFirstIteration) {
			selectKRandomCentroids();						// Select K random centroids
			createKClusters();								// Use centroids to create Clusters
			firstIteration = false;							// No longer first iteration
		}
		
		// Else, calculate mean of each existing Cluster and reassign all Points to new
		// Clusters with mean as new centroid
		else {
			centroidList.clearList();						// Clear the centroidList
			
			// Calculate new means to use as new centroids
			for (int index = 0; index < kClusters.getNumOfClustersInList(); index++) {
				Point newCentroid = new Point(kClusters.getKClusterList().get(index).calculateMeanOfCluster());
				centroidList.addPoint(newCentroid);
			}

			kClusters.clearClusterList();					// Clear kClusters
			createKClusters();								// Use new centroids for Clusters
			
			// If the previous iteration is equal to current iteration
			if (copy.isEqual(this.kClusters))
				this.convergence = true;					// Set convergence to true
		}
		
		return firstIteration;								// Return whether it is first iteration
	}
	
	/**
	 * This method selects K random Points from the originalData
	 * PointList to use as centroids for K Clusters. This method is
	 * only called once for the first iteration.
	 * @param none
	 * Source: http://stackoverflow.com/questions/5034370/retrieving-
	 * a-random-item-from-arraylist
	 */
	private void selectKRandomCentroids() {
		// Copy originalData to select K centroids from as to not alter originalData
		PointList originalDataCopy = new PointList(originalData);
		
		// Iterate numOfClusters times
		for (int clusterIndex = 0; clusterIndex < numOfClusters; clusterIndex++) {
			// Instantiate Random object
			Random randomCentroidSelector = new Random();	
			// Get random index to use get a random Point to use as a centroid
			int index = randomCentroidSelector.nextInt(originalDataCopy.numOfPoints);
			// Use index to get Point from originalDataCopy
			Point centroidToUse = new Point(originalDataCopy.dataPointList.get(index));
			// Delete Point from originalDataCopy so it cannot be selected again
			originalDataCopy.deletePoint(originalDataCopy.getDataPointList().get(index));
			// Add centroid to centroidList
			centroidList.addPoint(centroidToUse);
			
			// Iterate through originalDataCopy and delete any Points that are the same as
			// the randomly selected centroid
			for (int dataIndex = originalDataCopy.numOfPoints - 1; dataIndex >= 0; dataIndex--) {
				// Create new Point that will be used to compare with centroidToAdd to see if
				// they are the same
				Point isSame = new Point(originalDataCopy.getDataPointList().get(dataIndex));
				
				// If isSame x- and y-coordinates are equal to centroidToUse x- and y-coordinates
				if ((isSame.getXCoord() == centroidToUse.getXCoord()) 
						&& (isSame.getYCoord() == centroidToUse.getYCoord()))
					// Delete the Point from originalDataCopy
					originalDataCopy.deletePoint(originalDataCopy.dataPointList.get(dataIndex));
			}	
		}
	}

	/**
	 * This method creates K Clusters using the Points in centroidList
	 * as centroids for each Cluster and assigns each Point in
	 * originalData to whichever Cluster is closest.
	 * @param none
	 */
	private void createKClusters() {
		// Create a Cluster for each centroid and add to kClusters
		for (int clusterIndex = 0; clusterIndex < numOfClusters; clusterIndex++) {
			Cluster clusterToAdd = new Cluster(centroidList.dataPointList.get(clusterIndex));
			kClusters.addCluster(clusterToAdd);
		}
		
		assignPointsToClusters();							// Assign points to Clusters
	}

	/**
	 * This method takes the originalData Points and assigns it to the
	 * correct Cluster by calculating the distance between each Point
	 * and each centroid, and assigning the Point to the closest
	 * Cluster.
	 * @param none
	 */
	public void assignPointsToClusters() {
		// Iterate through originalData
		for (int index = 0; index < originalData.numOfPoints; index++) {
			// Get each Point
			Point pointToAssign = new Point(originalData.dataPointList.get(index));
			// Initial centroidList Index is set to zero and get first centroid
			int centroidIndex = 0;
			Point centroidToUse = new Point(centroidList.dataPointList.get(centroidIndex));
			// Calculate the distance between the Point and first centroid
			double distance = calculateDistance(pointToAssign, centroidToUse);
			
			// Iterate through each Cluster
			for (int centroidListIndex = 1; centroidListIndex < centroidList.numOfPoints;
					centroidListIndex++) {
				// Get centroidToUse
				centroidToUse = centroidList.dataPointList.get(centroidListIndex);
				// Calculate the distance between the Point and next centroid
				double currentDistance = calculateDistance(pointToAssign, centroidToUse);
				
				// If currentDistance is less than the previous distance
				if (currentDistance < distance) {
					distance = currentDistance;				// Update distance
					centroidIndex = centroidListIndex;		// Update centroidListIndex
				}
			}
		
			// Add Point to correct Cluster
			kClusters.getKClusterList().get(centroidIndex).addPoint(pointToAssign);
		}
	}
	
	/**
	 * This method takes in two Points as parameters. The distance is calculated
	 * using the Eculidean Distance formula (distance = sqrt((x1 - x2)^2
	 * + (y1 - y2)^2)) and returned.
	 * @param one
	 * @param two
	 * @return distance
	 */
	private double calculateDistance(Point one, Point two)  {
		double xDifferenceSqred = Math.pow((one.getXCoord() - two.getXCoord()), 2);
		double yDifferenceSqred = Math.pow((one.getYCoord() - two.getYCoord()), 2);
		return Math.sqrt(xDifferenceSqred + yDifferenceSqred);
	}
	
	/**
	 * MAIN PROGRAM
	 * @param args
	 */
	public static void main(String[] args) {
		// Checks if running first iteration - only first iteration will select centroids from
		// originalData PointList & all remaining iterations will use the mean of existing
		// Clusters to calculate centroids
		Boolean isFirstIteration = true;					
		int currentIteration = 1;							// Stores current iteration number
		int totalIterationsRun = 0;							// Stores total iterations run
		
		// Try/catch block to handle IOException in instantiating KMeansClustering object
		try {
			// Instantiate KMeansClustering object, read in data from file and populate
			// originalData PointList
			KMeansClustering program = new KMeansClustering(args);
			
			// Print Points in originalData PointList and diagnostics of file read in
			System.out.println("Original data:");
			program.originalData.printList();
			System.out.println("Total number of Points: " + program.originalData.numOfPoints);
			System.out.println("K: " + program.numOfClusters);
			System.out.println("Number of iterations to run: " + program.numOfIterations);
			System.out.println("\n");
			
			// Run first iteration - select K random centroids and assign each Point to a Cluster
			isFirstIteration = program.runIteration(isFirstIteration);
			isFirstIteration = false;						// No longer first iteration
			currentIteration++;								// Current iteration number
			totalIterationsRun++;							// Total iterations run
			
			// Print out diagnostics
			System.out.println("Iteration " + totalIterationsRun);
			program.kClusters.printClusterList();
			System.out.println("");
	
			// Run remainder iterations as long as convergence has not yet occurred
			while ((currentIteration <= program.numOfIterations) && (program.convergence == false)) {
				// Run an iteration
				isFirstIteration = program.runIteration(isFirstIteration);
				currentIteration++;							// Increment currentIteration
				totalIterationsRun++;						// Increment totalIterationsRun
				
				// Print out diagnostics		
				System.out.println("Iteration: " + totalIterationsRun);
				program.kClusters.printClusterList();
				System.out.println("");
			}
			
			// If convergence occurred, output number of iterations run and convergence occurred
			if (program.convergence == true)
				System.out.println("Convergence occurred after " + totalIterationsRun
						+ " iterations.");
			// Else, output total number of iterations run
			else 
				System.out.println(totalIterationsRun + " iterations run. Convergence did not"
						+ " occur.");
		}
		catch (IOException error) {
			System.out.println(error);
		}
	}
}