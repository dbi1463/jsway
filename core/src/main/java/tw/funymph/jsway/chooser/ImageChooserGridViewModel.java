/* ImageChooserGridViewModel.java created on 2013/5/5
 *
 * Copyright (C) 2013. Pin-Ying Tu all rights reserved.
 *
 * This file is a part of the JavaSway project.
 * 
 * The JavaSway project is published under the MIT license.
 * 
 * If you want to use the classes of this project in any commercial
 * product, please contact the author for the license.
 */
package tw.funymph.jsway.chooser;

/**
 * A class can implement this interface to provide information for
 * image layout in the image chooser.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.2
 */
public interface ImageChooserGridViewModel {

	int getHorizontalOffset();

	int getHorizontalGrap();

	int getColumns();

	int getRows();

	int getRowHeight();
}
