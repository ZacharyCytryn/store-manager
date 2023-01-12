package departmentStoreSimulator;

/**
 * This class contains the methods to create and use an ItemInfoNode
 * 
 * @author Zachary Cytryn
 *
 */
public class ItemInfoNode {
	
	private ItemInfo info;
	private ItemInfoNode prev;
	private ItemInfoNode next;
	
	/**
	 * Constructor for item info node
	 * 
	 * @param info
	 * ItemInfo
	 */
	public ItemInfoNode(ItemInfo info) {
		this.info = info;
		prev = null;
		next = null;
	}
	
	/**
	 * Setter for info
	 * 
	 * @param info
	 */
	public void setInfo(ItemInfo info) {
		this.info = info;
	}
	
	/**
	 * Getter for info
	 * 
	 * @return
	 * info
	 */
	public ItemInfo getInfo() {
		return info;
	}
	
	/**
	 * Setter for next node
	 * 
	 * @param node
	 */
	public void setNext(ItemInfoNode node) {
		next = node;
	}
	
	/**
	 * Setter for previous node
	 * 
	 * @param node
	 */
	public void setPrev(ItemInfoNode node) {
		prev = node;
	}
	
	/**
	 * Getter for next node
	 * 
	 * @return
	 * next
	 */
	public ItemInfoNode getNext() {
		return next;
	}
	
	/**
	 * Getter for previous node
	 * 
	 * @return
	 * prev
	 */
	public ItemInfoNode getPrev() {
		return prev;
	}
	
	
}
