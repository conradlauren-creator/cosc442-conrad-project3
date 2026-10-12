import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link VendingMachine}.
 *
 * Expected behavior comes from the class's Javadoc (preconditions,
 * postconditions, and documented exceptions), not from the implementation.
 * This file must be in the same package as VendingMachine (here, the default
 * package) so it can call the protected getItem method.
 */
public class VendingMachineAITest {

	private static final double DELTA = 0.0001;

	private VendingMachine vm;
	private VendingMachineItem chips;
	private VendingMachineItem soda;

	@BeforeEach
	void setUp() {
		vm = new VendingMachine();
		chips = new VendingMachineItem("Chips", 1.50);
		soda = new VendingMachineItem("Soda", 2.00);
	}

	// ---------------------------------------------------------------
	// Constructor
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Constructor completes without throwing")
	void constructorDoesNotThrow() {
		assertDoesNotThrow(() -> new VendingMachine());
	}

	@Test
	@DisplayName("New machine has a balance of 0")
	void newMachineHasZeroBalance() {
		assertEquals(0.0, vm.getBalance(), DELTA);
	}

	@Test
	@DisplayName("New machine has all four slots empty")
	void newMachineHasAllSlotsEmpty() {
		assertNull(vm.getItem(VendingMachine.A_CODE));
		assertNull(vm.getItem(VendingMachine.B_CODE));
		assertNull(vm.getItem(VendingMachine.C_CODE));
		assertNull(vm.getItem(VendingMachine.D_CODE));
	}

	// ---------------------------------------------------------------
	// addItem / getItem
	// ---------------------------------------------------------------

	@Test
	@DisplayName("addItem places the item in the requested slot")
	void addItemPlacesItemInSlot() {
		vm.addItem(chips, VendingMachine.A_CODE);
		assertSame(chips, vm.getItem(VendingMachine.A_CODE));
	}

	@Test
	@DisplayName("Each code A-D maps to its own independent slot")
	void eachCodeMapsToDistinctSlot() {
		VendingMachineItem a = new VendingMachineItem("A-item", 1);
		VendingMachineItem b = new VendingMachineItem("B-item", 1);
		VendingMachineItem c = new VendingMachineItem("C-item", 1);
		VendingMachineItem d = new VendingMachineItem("D-item", 1);
		vm.addItem(a, VendingMachine.A_CODE);
		vm.addItem(b, VendingMachine.B_CODE);
		vm.addItem(c, VendingMachine.C_CODE);
		vm.addItem(d, VendingMachine.D_CODE);

		assertSame(a, vm.getItem(VendingMachine.A_CODE));
		assertSame(b, vm.getItem(VendingMachine.B_CODE));
		assertSame(c, vm.getItem(VendingMachine.C_CODE));
		assertSame(d, vm.getItem(VendingMachine.D_CODE));
	}

	@Test
	@DisplayName("Adding to one slot leaves the other slots empty")
	void addItemDoesNotAffectOtherSlots() {
		vm.addItem(chips, VendingMachine.C_CODE);
		assertNull(vm.getItem(VendingMachine.A_CODE));
		assertNull(vm.getItem(VendingMachine.B_CODE));
		assertNull(vm.getItem(VendingMachine.D_CODE));
	}

	@Test
	@DisplayName("Adding to an occupied slot throws and keeps the original item")
	void addItemToOccupiedSlotThrows() {
		vm.addItem(chips, VendingMachine.A_CODE);
		VendingMachineException e = assertThrows(VendingMachineException.class,
				() -> vm.addItem(soda, VendingMachine.A_CODE));
		assertEquals("Slot A already occupied", e.getMessage());
		assertSame(chips, vm.getItem(VendingMachine.A_CODE));
	}

	@Test
	@DisplayName("addItem with an invalid code throws")
	void addItemWithInvalidCodeThrows() {
		VendingMachineException e = assertThrows(VendingMachineException.class,
				() -> vm.addItem(chips, "E"));
		assertEquals("Invalid code for vending machine item", e.getMessage());
	}

	@Test
	@DisplayName("Codes are case-sensitive: lowercase 'a' is invalid")
	void lowercaseCodeIsInvalid() {
		assertThrows(VendingMachineException.class, () -> vm.addItem(chips, "a"));
	}

	@Test
	@DisplayName("getItem with an invalid code throws")
	void getItemWithInvalidCodeThrows() {
		assertThrows(VendingMachineException.class, () -> vm.getItem("Z"));
	}

	// ---------------------------------------------------------------
	// removeItem
	// ---------------------------------------------------------------

	@Test
	@DisplayName("removeItem returns the item and empties the slot")
	void removeItemReturnsItemAndEmptiesSlot() {
		vm.addItem(chips, VendingMachine.B_CODE);
		VendingMachineItem removed = vm.removeItem(VendingMachine.B_CODE);
		assertSame(chips, removed);
		assertNull(vm.getItem(VendingMachine.B_CODE));
	}

	@Test
	@DisplayName("A slot can be refilled after its item is removed")
	void slotCanBeReusedAfterRemoval() {
		vm.addItem(chips, VendingMachine.A_CODE);
		vm.removeItem(VendingMachine.A_CODE);
		vm.addItem(soda, VendingMachine.A_CODE);
		assertSame(soda, vm.getItem(VendingMachine.A_CODE));
	}

	@Test
	@DisplayName("Removing from an empty slot throws")
	void removeFromEmptySlotThrows() {
		VendingMachineException e = assertThrows(VendingMachineException.class,
				() -> vm.removeItem(VendingMachine.D_CODE));
		assertEquals("Slot D is empty -- cannot remove item", e.getMessage());
	}

	@Test
	@DisplayName("Removing the same item twice throws the second time")
	void removeTwiceThrows() {
		vm.addItem(chips, VendingMachine.A_CODE);
		vm.removeItem(VendingMachine.A_CODE);
		assertThrows(VendingMachineException.class, () -> vm.removeItem(VendingMachine.A_CODE));
	}

	@Test
	@DisplayName("removeItem with an invalid code throws")
	void removeItemWithInvalidCodeThrows() {
		assertThrows(VendingMachineException.class, () -> vm.removeItem("AB"));
	}

	// ---------------------------------------------------------------
	// insertMoney / getBalance
	// ---------------------------------------------------------------

	@Test
	@DisplayName("insertMoney adds the amount to the balance")
	void insertMoneyIncreasesBalance() {
		vm.insertMoney(5.00);
		assertEquals(5.00, vm.getBalance(), DELTA);
	}

	@Test
	@DisplayName("Multiple insertions accumulate")
	void insertMoneyAccumulates() {
		vm.insertMoney(1.00);
		vm.insertMoney(2.25);
		assertEquals(3.25, vm.getBalance(), DELTA);
	}

	@Test
	@DisplayName("Inserting 0 is allowed (spec: amount >= 0) and leaves balance unchanged")
	void insertZeroIsAllowed() {
		assertDoesNotThrow(() -> vm.insertMoney(0));
		assertEquals(0.0, vm.getBalance(), DELTA);
	}

	@Test
	@DisplayName("Inserting a fractional amount like 0.25 is allowed")
	void insertFractionalAmountIsAllowed() {
		assertDoesNotThrow(() -> vm.insertMoney(0.25));
		assertEquals(0.25, vm.getBalance(), DELTA);
	}

	@Test
	@DisplayName("Inserting a negative amount throws and leaves balance unchanged")
	void insertNegativeAmountThrows() {
		vm.insertMoney(2.00);
		VendingMachineException e = assertThrows(VendingMachineException.class,
				() -> vm.insertMoney(-1.00));
		assertEquals("Invalid amount.  Amount must be >= 0", e.getMessage());
		assertEquals(2.00, vm.getBalance(), DELTA);
	}

	@Test
	@DisplayName("getBalance does not change the balance")
	void getBalanceIsReadOnly() {
		vm.insertMoney(4.00);
		vm.getBalance();
		assertEquals(4.00, vm.getBalance(), DELTA);
	}

	// ---------------------------------------------------------------
	// makePurchase
	// ---------------------------------------------------------------

	@Test
	@DisplayName("Purchase with enough money succeeds, deducts price, empties slot")
	void purchaseWithEnoughMoneySucceeds() {
		vm.addItem(chips, VendingMachine.A_CODE);
		vm.insertMoney(2.00);

		assertTrue(vm.makePurchase(VendingMachine.A_CODE));
		assertEquals(0.50, vm.getBalance(), DELTA);
		assertNull(vm.getItem(VendingMachine.A_CODE));
	}

	@Test
	@DisplayName("Purchase with exact change succeeds and leaves balance at 0 (boundary)")
	void purchaseWithExactChangeSucceeds() {
		vm.addItem(soda, VendingMachine.B_CODE);
		vm.insertMoney(2.00);

		assertTrue(vm.makePurchase(VendingMachine.B_CODE));
		assertEquals(0.0, vm.getBalance(), DELTA);
	}

	@Test
	@DisplayName("Purchase without enough money fails and changes nothing")
	void purchaseWithoutEnoughMoneyFails() {
		vm.addItem(soda, VendingMachine.C_CODE);
		vm.insertMoney(1.00);

		assertFalse(vm.makePurchase(VendingMachine.C_CODE));
		assertEquals(1.00, vm.getBalance(), DELTA);
		assertSame(soda, vm.getItem(VendingMachine.C_CODE));
	}

	@Test
	@DisplayName("Purchase with zero balance fails")
	void purchaseWithZeroBalanceFails() {
		vm.addItem(chips, VendingMachine.A_CODE);
		assertFalse(vm.makePurchase(VendingMachine.A_CODE));
		assertSame(chips, vm.getItem(VendingMachine.A_CODE));
	}

	@Test
	@DisplayName("Purchase from an empty slot returns false and keeps the balance")
	void purchaseFromEmptySlotFails() {
		vm.insertMoney(5.00);
		assertFalse(vm.makePurchase(VendingMachine.D_CODE));
		assertEquals(5.00, vm.getBalance(), DELTA);
	}

	@Test
	@DisplayName("Buying the same slot twice: second purchase fails")
	void purchaseSameSlotTwiceFailsSecondTime() {
		vm.addItem(chips, VendingMachine.A_CODE);
		vm.insertMoney(5.00);

		assertTrue(vm.makePurchase(VendingMachine.A_CODE));
		assertFalse(vm.makePurchase(VendingMachine.A_CODE));
		assertEquals(3.50, vm.getBalance(), DELTA);
	}

	@Test
	@DisplayName("Purchasing a free (price 0) item succeeds with zero balance")
	void purchaseFreeItemWithZeroBalance() {
		vm.addItem(new VendingMachineItem("Free Sample", 0), VendingMachine.A_CODE);
		assertTrue(vm.makePurchase(VendingMachine.A_CODE));
		assertEquals(0.0, vm.getBalance(), DELTA);
	}

	@Test
	@DisplayName("Purchasing from one slot does not affect other slots")
	void purchaseDoesNotAffectOtherSlots() {
		vm.addItem(chips, VendingMachine.A_CODE);
		vm.addItem(soda, VendingMachine.B_CODE);
		vm.insertMoney(5.00);

		vm.makePurchase(VendingMachine.A_CODE);
		assertSame(soda, vm.getItem(VendingMachine.B_CODE));
	}

	// ---------------------------------------------------------------
	// returnChange
	// ---------------------------------------------------------------

	@Test
	@DisplayName("returnChange returns the balance and resets it to 0")
	void returnChangeReturnsBalanceAndResets() {
		vm.insertMoney(3.75);
		assertEquals(3.75, vm.returnChange(), DELTA);
		assertEquals(0.0, vm.getBalance(), DELTA);
	}

	@Test
	@DisplayName("returnChange on an empty machine returns 0")
	void returnChangeWithZeroBalance() {
		assertEquals(0.0, vm.returnChange(), DELTA);
		assertEquals(0.0, vm.getBalance(), DELTA);
	}

	@Test
	@DisplayName("returnChange after a purchase returns the leftover amount")
	void returnChangeAfterPurchase() {
		vm.addItem(chips, VendingMachine.A_CODE);
		vm.insertMoney(5.00);
		vm.makePurchase(VendingMachine.A_CODE);
		assertEquals(3.50, vm.returnChange(), DELTA);
		assertEquals(0.0, vm.getBalance(), DELTA);
	}

	@Test
	@DisplayName("Calling returnChange twice returns 0 the second time")
	void returnChangeTwice() {
		vm.insertMoney(2.00);
		vm.returnChange();
		assertEquals(0.0, vm.returnChange(), DELTA);
	}
}
