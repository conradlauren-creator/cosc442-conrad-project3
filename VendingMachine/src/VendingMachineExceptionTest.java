import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

public class VendingMachineExceptionTest {
    
    VendingMachineException myVendingException;

    @Test
    void testEmptyVendingMachineException() {
        //ARRANGE
        myVendingException = new VendingMachineException();
        //ASSERT
        assertNull(myVendingException.getMessage());
    }

    @Test
    void testVendingMachineException(){
        //ARRANGE
        myVendingException = new VendingMachineException("Reason");
        //ASSERT
        assertEquals("Reason", myVendingException.getMessage());

    }
}
