package game;
 
import static org.junit.jupiter.api.Assertions.*;
 
import java.awt.Color;
import java.util.concurrent.atomic.AtomicInteger;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class InventoryTest {

    private static final int CAPACITY = 3;
    private static final AtomicInteger ITEM_COUNTER = new AtomicInteger();
 
    private Inventory inventory;
    private Item a;
    private Item b;
    private Item c;
 
    private static Item newItem() {
        int n = ITEM_COUNTER.incrementAndGet();
        return new Item('i', Color.WHITE, "item" + n, "an item");
    }

    @BeforeEach
    void setUp() {
        inventory = new Inventory(CAPACITY);
        a = newItem();
        b = newItem();
        c = newItem();
    }

    @AfterEach
    void tearDown() {
        inventory = new Inventory(CAPACITY);
        a = null;
        b = null;
        c = null;
    }

    // ------------------------- constructor / getters

    @Test
    @DisplayName("new inventory has the requested number of slots, all empty")
    void constructorCreatesEmptySlots() {
        Item[] items = inventory.getItems();
        assertEquals(CAPACITY, items.length);
        for (Item slot : items) {
            assertNull(slot);
        }
    }

    @Test
    @DisplayName("negative capacity throws NegativeArraySizeException")
    void constructorRejectsNegativeCapacity() {
        assertThrows(NegativeArraySizeException.class, () -> new Inventory(-1));
    }

    @Test
    @DisplayName("get(i) returns the item stored in slot i")
    void getReturnsItemAtIndex() {
        inventory.add(a);
        inventory.add(b);
        assertSame(a, inventory.get(0));
        assertSame(b, inventory.get(1));
        assertNull(inventory.get(2));
    }

    @Test
    @DisplayName("get with an out-of-range index throws")
    void getOutOfRangeThrows() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> inventory.get(CAPACITY));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> inventory.get(-1));
    }

    @Test
    @DisplayName("getItems reflects the current contents")
    void getItemsReflectsContents() {
        inventory.add(a);
        Item[] items = inventory.getItems();
        assertSame(a, items[0]);
        assertNull(items[1]);
        assertNull(items[2]);
    }

    // ------------------------------ add

    @Test
    @DisplayName("add places items into slots in order")
    void addFillsSlotsInOrder() {
        inventory.add(a);
        inventory.add(b);
        inventory.add(c);
        assertSame(a, inventory.get(0));
        assertSame(b, inventory.get(1));
        assertSame(c, inventory.get(2));
    }

    
    @Test
    @DisplayName("add fills the first gap left by a removal")
    void addReusesFirstEmptySlot() {
        inventory.add(a);
        inventory.add(b);
        inventory.add(c);
        inventory.remove(b);

        Item d = newItem();
        inventory.add(d);

        assertSame(a, inventory.get(0));
        assertSame(d, inventory.get(1));
        assertSame(c, inventory.get(2));
    }

    @Test
    @DisplayName("add on a full inventory is silently ignored")
    void addWhenFullIsIgnored() {
        inventory.add(a);
        inventory.add(b);
        inventory.add(c);

        Item extra = newItem();
        inventory.add(extra);

        assertFalse(inventory.contains(extra));
        assertSame(a, inventory.get(0));
        assertSame(b, inventory.get(1));
        assertSame(c, inventory.get(2));
    }

   
    @Test
    @DisplayName("add on a zero-capacity inventory does nothing and does not throw")
    void addToZeroCapacity() {
        Inventory empty = new Inventory(0);
        assertDoesNotThrow(() -> empty.add(a));
        assertEquals(0, empty.getItems().length);
        assertFalse(empty.contains(a));
    }

    // ------------------------------------- remove

    @Test
    @DisplayName("remove clears the item's slot and leaves other slots alone")
    void removeExistingItem() {
        inventory.add(a);
        inventory.add(b);
        inventory.add(c);

        inventory.remove(b);

        assertSame(a, inventory.get(0));
        assertNull(inventory.get(1));
        assertSame(c, inventory.get(2));
        assertFalse(inventory.contains(b));
    }

    @Test
    @DisplayName("remove of an item that isn't present changes nothing")
    void removeMissingItemDoesNothing() {
        inventory.add(a);
        inventory.add(b);
        inventory.add(c);

        inventory.remove(newItem());

        assertSame(a, inventory.get(0));
        assertSame(b, inventory.get(1));
        assertSame(c, inventory.get(2));
    }

    @Test
    @DisplayName("remove only clears the first occurrence of a duplicated item")
    void removeOnlyFirstOccurrence() {
        inventory.add(a);
        inventory.add(a);

        inventory.remove(a);

        assertNull(inventory.get(0));
        assertSame(a, inventory.get(1));
        assertTrue(inventory.contains(a));
    }

    // ----------------------------------- isFull

    @Test
    @DisplayName("isFull is false for an empty inventory")
    void isFullFalseWhenEmpty() {
        assertFalse(inventory.isFull());
    }

    @Test
    @DisplayName("isFull is false for a partially filled inventory")
    void isFullFalseWhenPartial() {
        inventory.add(a);
        inventory.add(b);
        assertFalse(inventory.isFull());
    }

    @Test
    @DisplayName("isFull is true when every slot is occupied")
    void isFullTrueWhenAllSlotsUsed() {
        inventory.add(a);
        inventory.add(b);
        inventory.add(c);
        assertTrue(inventory.isFull());
    }

    @Test
    @DisplayName("a zero-capacity inventory is always full")
    void zeroCapacityIsFull() {
        assertTrue(new Inventory(0).isFull());
    }

    // ---------------------------- contains

    @Test
    @DisplayName("contains is true for an added item")
    void containsAddedItem() {
        inventory.add(a);
        inventory.add(b);
        assertTrue(inventory.contains(b));
    }

    @Test
    @DisplayName("contains is false for an item never added")
    void containsMissingItem() {
        inventory.add(a);
        assertFalse(inventory.contains(b));
    }

    @Test
    @DisplayName("contains is false after the item is removed")
    void containsFalseAfterRemove() {
        inventory.add(a);
        inventory.remove(a);
        assertFalse(inventory.contains(a));
    }

    @Test
    @DisplayName("contains finds an item in the last slot of a full inventory")
    void containsItemInLastSlot() {
        inventory.add(a);
        inventory.add(b);
        inventory.add(c);
        assertTrue(inventory.contains(c));
    }

    @Test
    @DisplayName("add(null) leaves the inventory unchanged")
    void addNullOccupiesNothing() {
        inventory.add(null);
        assertFalse(inventory.isFull());
        inventory.add(a);
        assertSame(a, inventory.get(0));
    }

  
}
