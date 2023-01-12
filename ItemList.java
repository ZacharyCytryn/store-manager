package departmentStoreSimulator;

/**
 * This class provides the constructor and methods to create a linked list
 * 
 * @author Zachary Cytryn
 *
 */
public class ItemList {
	private ItemInfoNode head;
	private ItemInfoNode tail;
	private ItemInfoNode cursor;
	private int size;
	
	/**
	 * This is the constructor for the ItemList
	 */
	public ItemList() {
		head = null;
		tail = null;
		cursor = null;
		size = 0;
	}
	
	//O(1) because it just returns a counter.
	/**
	 * Getter method for size
	 * 
	 * @return
	 * size of linked list
	 */
	public int getSize() {
		return size;
	}
	
	//Best Case: O(1) | Worst Case: O(n) because if the list is empty, it is just one operation, but if not,
	//it might have to cycle through the list n times.
	/**
	 * Inserts info into the ItemList
	 * 
	 * @param name
	 * name of item
	 * @param rfidTag
	 * tag of item
	 * @param price
	 * price of item
	 * @param initPosition
	 * original position of item
	 */
	public void insertInfo(String name, String rfidTag, double price, String initPosition) {
		if (initPosition.toLowerCase().substring(0,1).equals("s")) {
			try {
				Integer.parseInt(initPosition.substring(1,6));
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Please enter five numbers 0-9 after the first letter of the original location");
			}
		}
		
		if(price < 0) {
			throw new IllegalArgumentException("Please enter a positive number for the price.");
		}
		else if (rfidTag.length() != 9) {
			throw new IllegalArgumentException("Please enter an RFID tag number that is 9 characters long.");
		}
		else if (!ItemInfo.checkRFID(rfidTag)) {
			throw new IllegalArgumentException("Please only use numbers 0-9 and letters A-F");
		}
		else if (initPosition.length() != 6) {
			throw new IllegalArgumentException("Please enter a location that is 6 characters long.");
		}
		else if (!initPosition.toLowerCase().substring(0,1).equals("s")) {
			throw new IllegalArgumentException("Original location must be on a shelf (please enter \"s\" in front of your location)");
		}
		
		if (size == 0)
		{
			try {
				head = new ItemInfoNode(new ItemInfo(name, price, rfidTag, initPosition));
			}
			catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
			tail = head;
			cursor = head;
			size++;
		}
		
		else {
			cursor = head;
			long hex = ItemInfo.convertHexToDecimal(rfidTag);
			for (int i = 0; i < size; i++) {
				if (hex > ItemInfo.convertHexToDecimal(cursor.getInfo().getRFIDTagNumber())) {
					if (cursor.getNext() == null) {
						cursor.setNext(new ItemInfoNode(new ItemInfo(name, price, rfidTag, initPosition)));
						cursor.getNext().setPrev(cursor);
						cursor = cursor.getNext();
						tail = cursor;
						size++;
						break;
					}
					else {
						cursor = cursor.getNext();
					}
				}
				else if (hex == ItemInfo.convertHexToDecimal(cursor.getInfo().getRFIDTagNumber())) {
					if (cursor.getNext() == null) {
						cursor.setNext(new ItemInfoNode(new ItemInfo(name, price, rfidTag, initPosition)));
						cursor.getNext().setPrev(cursor);
						cursor = cursor.getNext();
						tail = cursor;
					}
					else {
						cursor.getNext().setPrev(new ItemInfoNode(new ItemInfo(name, price, rfidTag, initPosition)));
						cursor.getNext().getPrev().setNext(cursor.getNext());
						cursor.setNext(cursor.getNext().getPrev());
						cursor.getNext().setPrev(cursor);
						cursor = cursor.getNext();
					}
					size++;
					break;
				}
				else if (hex < ItemInfo.convertHexToDecimal(cursor.getInfo().getRFIDTagNumber())) {
					if (cursor.getPrev() == null) {
						cursor.setPrev(new ItemInfoNode(new ItemInfo(name, price, rfidTag, initPosition)));
						cursor.getPrev().setNext(cursor);
						cursor = cursor.getPrev();
						head = cursor;
						
					}
					else {
						cursor.getPrev().setNext(new ItemInfoNode(new ItemInfo(name, price, rfidTag, initPosition)));
						cursor.getPrev().getNext().setPrev(cursor.getPrev());
						cursor.setPrev(cursor.getPrev().getNext());
						cursor.getPrev().setNext(cursor);
					}
					size++;
					break;
				}
			}
		}
	}
	
	//Best Case: O(1) | Worst Case: O(n) because it could be O(1) if there is only one purchased item
	//in the list, but O(n) if the item is in the back of the list.
	/**
	 * Removes all purchased items from item list
	 * 
	 * @throws EmptyListException
	 * throws if list is empty
	 */
	public void removeAllPurchased() throws EmptyListException {
		if (size == 0) {
			throw new EmptyListException("There are no items in the store!");
		}
		ItemList removed = new ItemList();
		cursor = tail;
		for (int i = size - 1; i >= 0; i--) {
			if (cursor.getInfo().getCurrentLocation().equals("out")) {
				removed.insertInfo(cursor.getInfo().getName(), cursor.getInfo().getRFIDTagNumber(), cursor.getInfo().getPrice(), cursor.getInfo().getOriginalLocation());
				if (cursor == head && cursor == tail) {
					cursor = null;
					head = null;
					tail = null;
					size = 0;
				}
				else if (cursor == head) {
					cursor = cursor.getNext();
					cursor.getPrev().setNext(null);
					cursor.setPrev(null);
					head = cursor;
					size--;
				}
				else if (cursor == tail) {
					cursor = cursor.getPrev();
					cursor.getNext().setPrev(null);
					cursor.setNext(null);
					tail = cursor;
					size--;
				}
				else {
					cursor.getPrev().setNext(cursor.getNext());
					cursor = cursor.getNext();
					cursor.setPrev(cursor.getPrev().getPrev());
					size--;
				}
			}
			if (head != null && cursor.getPrev() != null){
				cursor = cursor.getPrev();
			}
		}
		if (removed.getSize() == 0) {
			System.out.println("Nothing has been removed!");
		}
		else {
			System.out.println("The following item(s) have been removed from the system:\n");
			System.out.println(removed.toString());
		}
	}
	
	//Best Case: O(1) | Worst Case: O(n) it could be O(1) if item is first, but O(n) if it is last; it has to cycle
	//through a list
	/**
	 * Moves item from source location to a different location (shelf or cart)
	 * 
	 * @param rfidTag
	 * RFID tag of item
	 * @param source
	 * Original location of item
	 * @param dest
	 * Destination of item
	 * @return
	 * Returns true if found, false if not.
	 * @throws EmptyListException
	 * if list is empty
	 * @throws IllegalArgumentException
	 * if there is an illegal parameter
	 */
	public boolean moveItem(String rfidTag, String source, String dest) throws EmptyListException, IllegalArgumentException {
		dest.toLowerCase();
		if (size == 0) {
			throw new EmptyListException("There are no items in the store!");
		}
		if (dest.equals("out")) {
			throw new IllegalArgumentException("You can't move an item out!");
		}
		else if (dest.substring(0, 1).equals("s") || dest.substring(0, 1).equals("c")) {
			if (dest.substring(0, 1).equals("s") && dest.length() != 6) {
				
				throw new IllegalArgumentException("Please enter a valid destination.");
			}
			else if (dest.substring(0, 1).equals("c") && dest.length() != 4) {
				throw new IllegalArgumentException("Please enter a valid destination.");
			}
			try {
				Integer.parseInt(dest.substring(1));
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Please enter a valid destination.");
			}
			cursor = head;
			for (int i = 0; i < size; i++) {
				if (cursor.getInfo().getRFIDTagNumber().equals(rfidTag) && cursor.getInfo().getCurrentLocation().equals(source)) {
					cursor.getInfo().setCurrentLocation(dest);
					return true;
				}
				else if (cursor.getNext() != null){
					cursor = cursor.getNext();
				}
			}
			return false;
		}
		else {
			throw new IllegalArgumentException("Please enter a valid destination");
		}
	}
	
	//O(1) because it just calls a method
	/**
	 * Prints table of items
	 */
	public void printAll() {
		System.out.println(toString());
	}
	
	//O(n) because it cycles through store to check if location matches then prints
	/**
	 * Prints table of items based on location
	 * 
	 * @param location
	 * location of printed items
	 */
	public void printByLocation(String location) {
		String storeString = String.format("%-30s %-15s %-6s", "", "Original", "Current");
		storeString += "\n";
		storeString += String.format("%-18s %-11s %-15s %-12s %-4s", "Item Name", "RFID", "Location", "Location", "Price");
		storeString += "\n";
		storeString += String.format("%-16s %-13s %-15s %-11s %-5s", "---------", "---------", "---------", "---------", "------");
		cursor = head;
		for(int i = 0; i < size; i++) {
			if (cursor.getInfo().getCurrentLocation().equals(location)) {
				storeString += "\n";
				storeString += String.format("%-16s %-15s %-14s %-12s %-4s", cursor.getInfo().getName(), 
						cursor.getInfo().getRFIDTagNumber(), cursor.getInfo().getOriginalLocation(), 
						cursor.getInfo().getCurrentLocation(), cursor.getInfo().getPrice());
			}
			if (cursor.getNext() != null) {
				cursor = cursor.getNext();
			}
		}
		System.out.println(storeString);
	}
	
	//O(n) because it cycles through store n times to see which needs to be moved back
	/**
	 * Moves items back to original position
	 * 
	 * @throws EmptyListException
	 * throws if list is empty
	 */
	public void cleanStore() throws EmptyListException {
		if (size == 0) {
			throw new EmptyListException("There are no items in the store.");
		}
		else {
			String storeString = String.format("%-30s %-15s %-6s", "", "Original", "Current");
			storeString += "\n";
			storeString += String.format("%-18s %-11s %-15s %-12s %-4s", "Item Name", "RFID", "Location", "Location", "Price");
			storeString += "\n";
			storeString += String.format("%-16s %-13s %-15s %-11s %-5s", "---------", "---------", "---------", "---------", "------");
			cursor = head;
			for (int i = 0; i < size; i++) {
				if (!cursor.getInfo().getCurrentLocation().equals(cursor.getInfo().getOriginalLocation())) {
					storeString += "\n";
					storeString += String.format("%-16s %-15s %-14s %-12s %-4s", cursor.getInfo().getName(), 
							cursor.getInfo().getRFIDTagNumber(), cursor.getInfo().getOriginalLocation(), 
							cursor.getInfo().getCurrentLocation(), cursor.getInfo().getPrice());
					cursor.getInfo().setCurrentLocation(cursor.getInfo().getOriginalLocation());
				}
				if (cursor.getNext() != null) {
					cursor = cursor.getNext();
				}
			}
			System.out.println("The following item(s) have been moved back to their original locations:\n");
			System.out.println(storeString);
		}
	}
	
	//O(n) because it cycles through the loop n times to find the appropriate items and then prints it
	/**
	 * Changes current location to "out" to represent purchased items
	 * 
	 * @param cartNumber
	 * number of cart
	 * @return
	 * price of items
	 * @throws EmptyListException
	 * If list is empty
	 * @throws IllegalArgumentException
	 * if cart number is incorrectly inputted
	 */
	public double checkOut(String cartNumber) throws EmptyListException, IllegalArgumentException {
		if (cartNumber.toLowerCase().substring(0, 1).equals("c")) {
			if (cartNumber.length() != 4) {
				throw new IllegalArgumentException("Invalid cart.");
			}
			else {
				try {
					Integer.parseInt(cartNumber.substring(1,4));
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("Please enter five numbers 0-9 after the first letter of the current location");
				}
			}
		}
		if (size == 0) {
			throw new EmptyListException("There are no items in the store.");
		}
		else if (!cartNumber.toLowerCase().substring(0,1).equals("c")) {
			throw new IllegalArgumentException("Please enter a valid cart number.");
		}
		else {
			String storeString = String.format("%-30s %-15s %-6s", "", "Original", "Current");
			storeString += "\n";
			storeString += String.format("%-18s %-11s %-15s %-12s %-4s", "Item Name", "RFID", "Location", "Location", "Price");
			storeString += "\n";
			storeString += String.format("%-16s %-13s %-15s %-11s %-5s", "---------", "---------", "---------", "---------", "------");
			cursor = head;
			double cost = 0;
			for (int i = 0; i < size; i++) {
				if (cursor.getInfo().getCurrentLocation().equals(cartNumber)) {
					storeString += "\n";
					storeString += String.format("%-16s %-15s %-14s %-12s %-4s", cursor.getInfo().getName(), 
							cursor.getInfo().getRFIDTagNumber(), cursor.getInfo().getOriginalLocation(), 
							cursor.getInfo().getCurrentLocation(), cursor.getInfo().getPrice());
					cursor.getInfo().setCurrentLocation(cursor.getInfo().getOriginalLocation());
					cost += cursor.getInfo().getPrice();
					cursor.getInfo().setCurrentLocation("out");
				}
				if (cursor.getNext() != null) {
					cursor = cursor.getNext();
				}
			}
			System.out.println(storeString);
			System.out.println("\nThe total cost for all merchandise in cart " + cartNumber + " was $" + cost);
			return cost;
		}
		
	}
	
	/**
	 * Prints items based on RFID number
	 * 
	 * @param rfidTag
	 * RFID number
	 * @throws IllegalArgumentException
	 * if rfid tag is inputted incorrectly
	 */
	//O(n) because it cycles through the loop n times to print it
	public void printByRFID(String rfidTag) throws IllegalArgumentException {
		if (rfidTag.length() != 9) {
			throw new IllegalArgumentException("Please enter an RFID tag number that is 9 characters long.");
		}
		else if (!ItemInfo.checkRFID(rfidTag)) {
			throw new IllegalArgumentException("Please only use numbers 0-9 and letters A-F");
		}
		String storeString = String.format("%-30s %-15s %-6s", "", "Original", "Current");
		storeString += "\n";
		storeString += String.format("%-18s %-11s %-15s %-12s %-4s", "Item Name", "RFID", "Location", "Location", "Price");
		storeString += "\n";
		storeString += String.format("%-16s %-13s %-15s %-11s %-5s", "---------", "---------", "---------", "---------", "------");
		cursor = head;
		for(int i = 0; i < size; i++) {
			if (cursor.getInfo().getRFIDTagNumber().equals(rfidTag)) {
				storeString += "\n";
				storeString += String.format("%-16s %-15s %-14s %-12s %-4s", cursor.getInfo().getName(),
						cursor.getInfo().getRFIDTagNumber(), cursor.getInfo().getOriginalLocation(), 
						cursor.getInfo().getCurrentLocation(), cursor.getInfo().getPrice());
			}
			if (cursor.getNext() != null) {
				cursor = cursor.getNext();
			}
		}
		System.out.println(storeString);
	}
	
	//O(n) because it cycles through the loop n times to print it
	/**
	 * Converts store to a tabular string
	 */
	public String toString() {
		String storeString = String.format("%-30s %-15s %-6s", "", "Original", "Current");
		storeString += "\n";
		storeString += String.format("%-18s %-11s %-15s %-12s %-4s", "Item Name", "RFID", "Location", "Location", "Price");
		storeString += "\n";
		storeString += String.format("%-16s %-13s %-15s %-11s %-5s", "---------", "---------", "---------", "---------", "------");
		cursor = head;
		for(int i = 0; i < size; i++) {
			storeString += "\n";
			double cents = cursor.getInfo().getPrice() % 1.0;
			if (cents < 0.11 || cents == 0.2 || cents == 0.3 || cents == 0.4 || cents == 0.5 || cents == 0.6
					|| cents == 0.7 || cents == 0.8 || cents == 0.9) {
				storeString += String.format("%-16s %-15s %-14s %-12s %-4s", cursor.getInfo().getName(),
						cursor.getInfo().getRFIDTagNumber(), cursor.getInfo().getOriginalLocation(), 
						cursor.getInfo().getCurrentLocation(), cursor.getInfo().getPrice() + "0");
			}
			else {
				storeString += String.format("%-16s %-15s %-14s %-12s %-4s", cursor.getInfo().getName(),
						cursor.getInfo().getRFIDTagNumber(), cursor.getInfo().getOriginalLocation(), 
						cursor.getInfo().getCurrentLocation(), cursor.getInfo().getPrice());
			}
			if (cursor.getNext() != null) {
				cursor = cursor.getNext();
			}
		}
		return storeString;
	}
}
