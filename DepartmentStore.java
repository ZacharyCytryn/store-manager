/**
 * Zachary Cytryn
 * ID: 114283379
 * Email: zachary.cytryn@stonybrook.edu
 * Homework #2
 * CSE 214 Recitation 30
 */

package departmentStoreSimulator;

import java.util.Scanner;

/**
 * Contains main method for the simulator
 * 
 * @author zacharycytryn
 *
 */
public class DepartmentStore {

	/**
	 * main method
	 * 
	 * @param args
	 * command line arguments
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome!\n");
		ItemList store = new ItemList();
		String answer = "";
		while (!answer.toUpperCase().equals("Q")) {
			System.out.println("\nC - Clean Store"
					+ "\nI - Insert an item into the list"
					+ "\nL - List by location"
					+ "\nM - Move an item in the store"
					+ "\nO - Checkout"
					+ "\nP - Print all items in store"
					+ "\nR - Print by RFID tag number"
					+ "\nU - Update inventory system"
					+ "\nQ - Exit the program.");
			System.out.println("\nPlease select an option: ");
			answer = sc.nextLine();
			switch(answer.toUpperCase()) {
			case "C":
				try {
					store.cleanStore();
				} catch (EmptyListException e) {
					System.out.println(e.getMessage());
				}
				break;
			
			case "I":
				System.out.println("Enter the name: ");
				String name = sc.nextLine();
				System.out.println("Enter the RFID: ");
				String rfid = sc.nextLine();
				try {
					ItemInfo.checkRFID(rfid);
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				}
				System.out.println("Enter the original location: ");
				String originalLocation = sc.nextLine();
				System.out.println("Enter the price: ");
				double price = -1;
				try {
					price = Double.parseDouble(sc.nextLine());
				}
				catch (NumberFormatException e) {
					System.out.println("Please only enter a number for the price.");
				}
				try {
					store.insertInfo(name, rfid, price, originalLocation);
				} catch(IllegalArgumentException e) {
					System.out.println(e.getMessage());
				}
				break;
				
			case "L":
				System.out.println("Enter the location: ");
				String location = sc.nextLine();
				store.printByLocation(location);
				break;
				
			case "M":
				System.out.println("Enter the RFID: ");
				String rfidTag = sc.nextLine();
				System.out.println("Enter the original location: ");
				String origLocation = sc.nextLine();
				System.out.println("Enter the new location: ");
				String newLocation = sc.nextLine();
				try {
					store.moveItem(rfidTag, origLocation, newLocation);
				}
				catch (EmptyListException e) {
					System.out.println(e.getMessage());
				}
				catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				}
				break;
				
			case "O":
				System.out.println("Enter the cart number: ");
				String cartNum = sc.nextLine();
				try {
					store.checkOut(cartNum);
				}
				catch (EmptyListException e) {
					System.out.println(e.getMessage());
				}
				catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				}
				break;
				
			case "P":
				store.printAll();
				break;
				
			case "R":
				System.out.println("Enter the RFID: ");
				String rfidSearch = sc.nextLine();
				try {
					store.printByRFID(rfidSearch);
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				}
				break;
				
			case "U":
				try {
					store.removeAllPurchased();
				} catch (EmptyListException e) {
					System.out.println(e.getMessage());
				}
				break;
				
			case "Q":
				System.out.println("Goodbye!");
				break;
				
			default:
				System.out.println("Invalid option.");
				break;
			}
		}
		sc.close();
	}
}
