import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link VendingMachineItem}.
 */
public class VendingMachineItemAITest {

	private static final double DELTA = 0.0001;

	@Test
	@DisplayName("Constructor stores the given name and price")
	void constructorSetsNameAndPrice() {
		VendingMachineItem item = new VendingMachineItem("Soda", 1.25);
		assertEquals("Soda", item.getName());
		assertEquals(1.25, item.getPrice(), DELTA);
	}

	@Test
	@DisplayName("A price of exactly 0 is allowed (boundary)")
	void zeroPriceIsAllowed() {
		VendingMachineItem item = new VendingMachineItem("Free Sample", 0);
		assertEquals(0.0, item.getPrice(), DELTA);
	}

	@Test
	@DisplayName("A price between 0 and 1 is allowed")
	void fractionalPriceIsAllowed() {
		VendingMachineItem item = new VendingMachineItem("Gum", 0.50);
		assertEquals(0.50, item.getPrice(), DELTA);
	}

	@Test
	@DisplayName("A negative price throws VendingMachineException with the right message")
	void negativePriceThrows() {
		VendingMachineException e = assertThrows(VendingMachineException.class,
				() -> new VendingMachineItem("Bad", -0.01));
		assertEquals("Price cannot be less than zero", e.getMessage());
	}
}
