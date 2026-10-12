import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link VendingMachineException}.
 */
public class VendingMachineExceptionAITest {

	@Test
	@DisplayName("Default constructor creates an exception with no message")
	void defaultConstructorHasNullMessage() {
		VendingMachineException e = new VendingMachineException();
		assertNull(e.getMessage());
	}

	@Test
	@DisplayName("Message constructor stores the given message")
	void messageConstructorStoresMessage() {
		VendingMachineException e = new VendingMachineException("Something went wrong");
		assertEquals("Something went wrong", e.getMessage());
	}

	@Test
	@DisplayName("Is an unchecked (Runtime) exception")
	void isRuntimeException() {
		// Checked at runtime so the test is meaningful rather than a compile-time tautology.
		Object e = new VendingMachineException("x");
		assertTrue(e instanceof RuntimeException);
	}

	@Test
	@DisplayName("Can be thrown and caught with its message intact")
	void canBeThrownAndCaught() {
		VendingMachineException e = assertThrows(VendingMachineException.class, () -> {
			throw new VendingMachineException("boom");
		});
		assertEquals("boom", e.getMessage());
	}
}
