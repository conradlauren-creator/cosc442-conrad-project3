import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class VendingMachineItemTest {
   
    VendingMachineItem myItem;


   @Test
    void testValidVendingMachineItem() {
        // ARRANGE
        myItem = new VendingMachineItem("Doritos", 1.25);
        //ASSERT
        assertEquals("Doritos", myItem.getName());
        assertEquals(1.25, myItem.getPrice(), 0.0001);
        myItem = null;
    }

    @Test
    void testNegativeItemPrice() {
        //ASSERT
         assertThrows(VendingMachineException.class,
        () -> new VendingMachineItem("Poptart", -1.25));
        myItem = null;
    }
   
    @Test
    void testGetName() {
        // ARRANGE
        myItem = new VendingMachineItem("Doritos", 1.25);
        //ASSERT
        assertEquals("Doritos", myItem.getName());
        myItem = null;
    }


    @Test
    void testGetPrice() {
        //ARRANGE
        myItem = new VendingMachineItem("Doritos", 1.25);
        //ASSERT
        assertEquals(1.25, myItem.getPrice());
        myItem = null;
    }
}
