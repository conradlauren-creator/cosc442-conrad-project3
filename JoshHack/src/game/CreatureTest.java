package game;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import java.awt.Color;
import org.junit.Test;

public class CreatureTest {

	Creature myPlayerCreature;
	World myWorld;
	Tile[][][] myTiles;
	Item myItem;

	@Before
	public void setUp() {
		myTiles = new Tile[1][1][1];
		myWorld = new World(myTiles);
		myItem = new Item('A',Color.BLUE,"string1", "string2");
		myPlayerCreature = new Creature(myWorld, '@', Color.RED, "Testing", 100, 15, 10);
	}

	@After
	public void tearDown() {
		myTiles = null;
		myWorld = null;
		myPlayerCreature = null;
	}

	@Test
	public void testAdd_ModifyAttackValue() {
		//attack value was established as 15 for myPlayerCreature
		myPlayerCreature.modifyAttackValue(1);
		assertEquals(16, myPlayerCreature.attackValue());
	}

	@Test
	public void testZero_ModifyAttackValue() {
		//attack value was established as 15 for myPlayerCreature
		myPlayerCreature.modifyAttackValue(0);
		assertEquals(15, myPlayerCreature.attackValue());
	}

	@Test
	public void testSubtract_ModifyAttackValue() {
		//attack value was established as 15 for myPlayerCreature
		myPlayerCreature.modifyAttackValue(-1);
		assertEquals(14, myPlayerCreature.attackValue());
	}

	@Test
	public void testConfirmsPlayerGlyph() {
		//Creature was established with a glyph of @
		//ASSERT
		assertTrue(myPlayerCreature.isPlayer());
	}

	@Test
	public void testConfirmsNonPlayerGlyph() {
		//Creature was established with a glyph of C
		Creature myNonPlayerCreature = new Creature(myWorld, 'C', Color.RED, "Testing", 100, 15, 10);
		//ASSERT
		assertFalse(myNonPlayerCreature.isPlayer());
	}
}
