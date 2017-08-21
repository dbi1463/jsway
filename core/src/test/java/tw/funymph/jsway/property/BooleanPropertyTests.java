/* BooleanPropertyTests.java created on 2017/08/02
 *
 * Copyright (c) <2017> Pin-Ying Tu <dbi1463@gmail.com>
 *
 * This file is a part of the JavaSway project.
 * 
 * The JavaSway project is published under the MIT license.
 * 
 * If you want to use the classes of this project in any commercial
 * product, please contact the author for the license.
 */
package tw.funymph.jsway.property;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * This class tests the functionalities of {@link BooleanProperty}.
 * 
 * @author Spirit Tu
 * @version 1.2
 * @since 1.2
 */
public class BooleanPropertyTests {

	@Test
	public void testProperties() {
		BooleanProperty testee = new BooleanProperty("highlighted", false);
		assertFalse(testee.getBooleanValue());
	}
}
