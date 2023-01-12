package departmentStoreSimulator;
import java.lang.Math;

/**
 * Creates and uses methods to handle items and their information
 * 
 * @author Zachary Cytryn
 *
 */
public class ItemInfo {
	
	private String name;
	private double price; //Has to be > 0
	private String rfidTagNumber;
	private String originalLocation;
	private String currentLocation;
	

	/**
	 * Constructor for ItemInfo
	 * 
	 * @param name
	 * name of item
	 * @param price
	 * price of item
	 * @param rfidTagNumber
	 * rfid of item
	 * @param originalLocation
	 * original location of item
	 * @throws IllegalArgumentException
	 * if any of the parameters are incorrectly inputted
	 */
	public ItemInfo(String name, double price, String rfidTagNumber, String originalLocation) throws IllegalArgumentException {
		if (originalLocation.toLowerCase().substring(0,1).equals("s")) {
			try {
				Integer.parseInt(originalLocation.substring(1,6));
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Please enter five numbers 0-9 after the first letter of the original location");
			}
		}
		
		if(price < 0) {
			throw new IllegalArgumentException("Please enter a positive number for the price.");
		}
		else if (rfidTagNumber.length() != 9) {
			throw new IllegalArgumentException("Please enter an RFID tag number that is 9 characters long.");
		}
		else if (!checkRFID(rfidTagNumber)) {
			throw new IllegalArgumentException("Please only use numbers 0-9 and letters A-F");
		}
		else if (originalLocation.length() != 6) {
			throw new IllegalArgumentException("Please enter a location that is 6 characters long.");
		}
		else if (!originalLocation.toLowerCase().substring(0,1).equals("s")) {
			throw new IllegalArgumentException("Original location must be on a shelf (please enter \"s\" in front of your location)");
		}
		else {
			this.name = name;
			this.price = price;
			this.rfidTagNumber = rfidTagNumber.toUpperCase();
			this.originalLocation = originalLocation.toLowerCase();
			currentLocation = originalLocation.toLowerCase();
		}
	}
	
	/**
	 * getter for name 
	 * 
	 * @return
	 * name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * getter for price
	 * 
	 * @return
	 * price
	 */
	public double getPrice() {
		return price;
	}
	
	
	/**
	 * getter for RFID tag number
	 * 
	 * @return
	 * rfid tag number
	 */
	public String getRFIDTagNumber() {
		return rfidTagNumber;
	}
	
	
	/**
	 * Getter for original location
	 * 
	 * @return
	 * original location
	 */
	public String getOriginalLocation() {
		return originalLocation;
	}
	
	/**
	 * Getter for current location
	 * 
	 * @return
	 * current location
	 */
	public String getCurrentLocation() {
		return currentLocation;
	}
	
	/**
	 * Setter for name
	 * 
	 * @param name
	 * desired name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Setter for price
	 * 
	 * @param price
	 * desired price
	 * @throws IllegalArgumentException
	 * if price is inputted wrong
	 */
	public void setPrice(double price) throws IllegalArgumentException {
		if (price < 0) {
			throw new IllegalArgumentException("Please enter a positive number for the price.");
		}
		else {
			this.price = price;
		}
	}
	
	/**
	 * Sets RFID tag number
	 * 
	 * @param rfidTagNumber
	 * rfid tag number
	 * 
	 * @throws IllegalArgumentException
	 * if rfid inputted wrong
	 */
	public void setRFIDTagNumber(String rfidTagNumber) throws IllegalArgumentException {
		if (rfidTagNumber.length() != 9) {
			throw new IllegalArgumentException("Please enter an RFID tag number that is 9 characters long.");
		}
		else if (!checkRFID(rfidTagNumber)) {
			throw new IllegalArgumentException("Please enter an RFID tag number that is 9 characters long.");
		}
		else {
			this.rfidTagNumber = rfidTagNumber;
		}
	}
	
	/**
	 * Setter for original location
	 * 
	 * @param originalLocation
	 * original location
	 * @throws IllegalArgumentException
	 * if original location inputted wrong
	 */
	public void setOriginalLocation(String originalLocation) throws IllegalArgumentException {
		if (originalLocation.length() != 6) {
			throw new IllegalArgumentException("Please enter a location that is 6 characters long.");
		}
		else if (!originalLocation.toLowerCase().substring(0,1).equals("s")) {
			throw new IllegalArgumentException("Original location must be on a shelf (please enter \"s\" in front of your location)");
		}
		else if (originalLocation.toLowerCase().substring(0,1).equals("s")) {
			try {
				Integer.parseInt(originalLocation.substring(1,6));
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Please enter five numbers 0-9 after the first letter of the original location");
			}
		}
		else {
			this.originalLocation = originalLocation;
		}
	}
	
	/**
	 * Setter for current location
	 * 
	 * @param currentLocation
	 * current location
	 * @throws IllegalArgumentException
	 * if current location is inputted wrong
	 */
	public void setCurrentLocation(String currentLocation) throws IllegalArgumentException {
		if (currentLocation.toLowerCase().substring(0,1).equals("s")) {
			try {
				Integer.parseInt(originalLocation.substring(1,6));
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Please enter five numbers 0-9 after the first letter of the current location");
			}
		}
		
		else if (currentLocation.toLowerCase().substring(0,1).equals("c")) {
			try {
				Integer.parseInt(originalLocation.substring(1,4));
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Please enter five numbers 0-9 after the first letter of the current location");
			}
		}
		else if (currentLocation.toLowerCase().equals("out")) {;}
		else {
			throw new IllegalArgumentException("Invalid Location.");
		}
		this.currentLocation = currentLocation.toLowerCase();
	}
	
	/**
	 * Checker to see if RFID is valid
	 * @param rfidTagNumber
	 * rfid
	 * @return
	 * returns true if valid
	 * @throws IllegalArgumentException
	 * throws if invalid
	 */
	public static boolean checkRFID(String rfidTagNumber) throws IllegalArgumentException {
		String x = rfidTagNumber;
		if (x.contains("G") || x.contains("H") || x.contains("I") || x.contains("J") || x.contains("K")
				|| x.contains("L") || x.contains("M") || x.contains("N") || x.contains("O") || x.contains("P")
				|| x.contains("Q") || x.contains("R") || x.contains("S") || x.contains("T") || x.contains("U")
				|| x.contains("V") || x.contains("W") || x.contains("X") || x.contains("Y") || x.contains("Z")) {
			throw new IllegalArgumentException("Invalid RFID");
		}
		else {
			return true;
		}
	}
	
	/**
	 * Converts RFID's to hex numbers
	 * 
	 * @param hex
	 * RFID
	 * 
	 * @return
	 * Hex number
	 * @throws IllegalArgumentException
	 * if RFID invalid
	 */
	public static long convertHexToDecimal(String hex) throws IllegalArgumentException {
		//Length of hex is always 9 in this case
		if (!checkRFID(hex)) {
			throw new IllegalArgumentException("Invalid RFID.");
		}
		String sub = "";
		long answer = 0;
		int counter = 8;
		for (int i = 0; i < 9; i++) {
			try {
				answer += Integer.parseInt(hex.substring(i, i + 1)) * Math.pow(16, counter);
				counter--;
			} catch (NumberFormatException e) {
				sub = hex.substring(i, i + 1);
				switch(sub.toUpperCase()) {
				
				case "A":
					answer += 10 * Math.pow(16, counter);
					break;
					
				case "B":
					answer += 11 * Math.pow(16, counter);
					break;
					
				case "C":
					answer += 12 * Math.pow(16, counter);
					break;
				
				case "D":
					answer += 13 * Math.pow(16, counter);
					break;
					
				case "E":
					answer += 14 * Math.pow(16, counter);
					break;
					
				case "F":
					answer += 15 * Math.pow(16, counter);
					break;
					
				default:
					//Shouldn't happen due to other exceptions in other methods stopping this.
					System.out.println("Invalid RFID");
					break;
				}
				counter--;
			}

			
		}
		return answer;
	}
	
}
