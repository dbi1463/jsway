/* SwayDemoFrame.java created on 2011/9/16
 *
 * Copyright (C) 2011. Pin-Ying Tu all rights reserved.
 *
 * This file is a part of the JavaSway project.
 * 
 * The JavaSway project is published under the MIT license.
 * 
 * If you want to use the classes of this project in any commercial
 * product, please contact the author for the license.
 */
package tw.funymph.jsway.example;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.*;

import tw.funymph.jsway.ImageButton;
import tw.funymph.jsway.SimpleMultiLanguageSupport;
import tw.funymph.jsway.StyledToolBar;
import tw.funymph.jsway.color.AddibleLeveledColors;
import tw.funymph.jsway.color.ShowColorChooserAction;
import tw.funymph.jsway.memory.*;
import tw.funymph.jsway.property.*;
import tw.funymph.jsway.property.editor.CategorizedPropertiesDialogPanel;
import tw.funymph.jsway.property.editor.CategorizedPropertiesEditPanel;
import tw.funymph.jsway.property.editor.OnOffSwitch;
import tw.funymph.jsway.property.editor.PropertiesDialogPanel;
import tw.funymph.jsway.property.editor.PropertiesEditPanel;

/** A simple demo of Java Sway.
 * 
 * @author Pin-Ying Tu
 * @version 1.1
 * @since 1.0
 */
public class SwayDemoFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = -2154466670492917432L;

	private static final String DEFAULT_TITLE = "Java Sway Demo";

	private static final int DEFAULT_WIDTH = 640;
	private static final int DEFAULT_HEIGHT = 480;

	private StyledToolBar toolbar;
	private JTabbedPane pages;
	private ImageButton imageButton;

	private List<Choice> choices;
	private ShowColorChooserAction showTextualColorChooserAction;
	private ShowColorChooserAction showBackgroundColorChooserAction;
	private ShowColorChooserAction showForegroundColorChooserAction;

	private AddibleLeveledColors colors;
	private EditableProperties basicProperties;
	private EditableProperties advancedProperties;
	private CategorizedEditableProperties categorizedProperties;

	private PropertiesEditPanel colorsEditPanel;
	private PropertiesDialogPanel propertiesEditPanel;
	private CategorizedPropertiesEditPanel categorizedPropertiesEditPanel;

	private MemoryUsageMonitor monitor;
	private MemoryUsageMonitorBarModel mointorBarModel;
	private RecentMemoryUsageLineChart historyLineChart;
	private SimpleMultiLanguageSupport colorPropertyText;
	private SimpleMultiLanguageSupport colorCategoryText;
	private SimpleMultiLanguageSupport propertiesCategoryText;

	private int garbage[];
	private boolean english;

	public SwayDemoFrame() {
		super(DEFAULT_TITLE);
		english = true;
		monitor = new MemoryUsageMonitor(1000, false);
		createColorProperties();
		createBasicProperties();
		createAdvancedProperties();
		mointorBarModel = new DefaultMemoryUsageMonitorBarModel();
		getContentPane().setLayout(new BorderLayout());
		createContentPane();
		createPropertiesPane();
		createCategorizedPropertiesPane();
		createMenu();
		createToolBar();
		Timer timer = new Timer(1000, this);
		timer.start();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	private void createColorProperties() {
		colors = new AddibleLeveledColors("Memory Usage Colors");
		colors.addColor(1.0, new ColorProperty("Usage >= 0.75", Color.red));
		colors.addColor(0.75, new ColorProperty("Usage >= 0.5", Color.orange));
		colors.addColor(0.5, new ColorProperty("Usage >= 0.25", Color.blue));
		colors.addColor(0.25, new ColorProperty("Usage < 0.25", Color.green));
	}

	private void createToolBar() {
		toolbar = new StyledToolBar();
		toolbar.setBackgroundImage("icons/ToolBarBg.png");
		toolbar.add(showBackgroundColorChooserAction);
		toolbar.add(showForegroundColorChooserAction);
		toolbar.add(showTextualColorChooserAction);
		toolbar.setFloatable(false);
		MemoryUsageMonitorBar bar = new MemoryUsageMonitorBar(mointorBarModel, colors);
		toolbar.add(bar);
		toolbar.addSeparator();
		toolbar.add(new AbstractAction("Change") {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(pages.getSelectedIndex() == 0 || pages.getSelectedIndex() == 1) {
					pages.setSelectedIndex(3);
				}
				english = !english;
				colorPropertyText.setDisplayText(english? "Color property" : "顏色屬性");
				colorCategoryText.setDisplayText(english? "Colors" : "顏色屬性");
				propertiesCategoryText.setDisplayText(english? "Properties" : "屬性");
				for(Choice choice : choices) {
					((MyChoice)choice).setDisplayInEnglish(english);
				}
				switchEditable(basicProperties);
				switchEditable(advancedProperties);
			}
		});
		toolbar.addSeparator();
		MemoryUsageIcon icon = new MemoryUsageIcon();
		icon.setLeveledColors(colors);
		toolbar.add(new JLabel(icon));
		toolbar.addSeparator();
		imageButton = new ImageButton(new AlertAction("Image Button Clicked!"), "icons/WeekNormal.png", null, "icons/WeekPressed.png");
		toolbar.add(imageButton);
		getContentPane().add(toolbar, NORTH);
	}

	private void switchEditable(EditableProperties properties) {
		int count = properties.size();
		for(int index = 0; index < count; index++) {
			EditableProperty property = properties.getProperty(index);
			property.setUIEditable(!property.isUIEditable());
		}
	}

	private void createMenu() {
		JMenuBar menubar = new JMenuBar();
		JMenu colorMenu = new JMenu("Colors");
		colorMenu.add(showBackgroundColorChooserAction);
		colorMenu.add(showForegroundColorChooserAction);
		colorMenu.add(showTextualColorChooserAction);
		setJMenuBar(menubar);
		menubar.add(colorMenu);
	}

	private void createContentPane() {
		pages = new JTabbedPane();
		getContentPane().add(pages, CENTER);
		createHistoryLineChart();
		createColorEditPane();
		createStatusBar();
	}

	private void createColorEditPane() {
		colorsEditPanel = new PropertiesEditPanel(colors.toEditableProperties());
		pages.add("Edit Colors", colorsEditPanel);
	}

	private void createPropertiesPane() {
		EditableProperties[] propertiesSet = new EditableProperties[] { basicProperties, advancedProperties };
		propertiesEditPanel = new PropertiesDialogPanel(propertiesSet);
		propertiesEditPanel.setEditorWidth(0, 0, 120);
		propertiesEditPanel.setEditorWidth(0, 1, 100);
		propertiesEditPanel.setEditorWidth(1, 0, 120);
		propertiesEditPanel.setEditorWidth(1, 3, 100);
		pages.add("Properties", propertiesEditPanel);
	}

	private void createCategorizedPropertiesPane() {
		categorizedProperties = new DefaultCategorizedEditableProperties("Preferences");
		categorizedProperties.addProperties("Colors", colorCategoryText = new SimpleMultiLanguageSupport("Colors"), colors.toEditableProperties());
		categorizedProperties.addProperties("Properties", propertiesCategoryText = new SimpleMultiLanguageSupport("Properties"), basicProperties);
		categorizedProperties.addProperties("Properties", advancedProperties);
		categorizedPropertiesEditPanel = new CategorizedPropertiesEditPanel(categorizedProperties);
		pages.add("Preferences in Table", categorizedPropertiesEditPanel);
		CategorizedPropertiesDialogPanel panel = new CategorizedPropertiesDialogPanel(categorizedProperties);
		panel.setSelectedCategory("Colors");
		panel.setCategorySelectorWidth(120);
		panel.setEditorWidth("Properties", 0, 0, 140);
		panel.setEditorWidth("Properties", 0, 1, 120);
		panel.setEditorWidth("Properties", 1, 0, 130);
		panel.setEditorWidth("Properties", 1, 3, 120);
		pages.add("Preferences", panel);
	}

	private void createAdvancedProperties() {
		advancedProperties = new DefaultEditableProperties("Advanced Properties");
		NumberProperty margin = new NumberProperty("Margin", 2.54, 0.1, "cm");
		margin.addAvailableUnit("inch");
		margin.setUIEditable(false);
		advancedProperties.addEditableProperty(margin);
		advancedProperties.addEditableProperty(new IPv4AddressProperty("eth 0 IP Address", "192.168.0.1"));
		advancedProperties.addEditableProperty(new MACAddressProperty("eth 0 MAC Address", "AB:CD:EF:00:11:22:33", ":"));
		choices = new Vector();
		choices.add(new MyChoice(1));
		choices.add(new MyChoice(2));
		choices.add(new MyChoice(3));
		advancedProperties.addEditableProperty(new ChoicesProperty("Choices", new Integer(2), choices));
	}

	private void createBasicProperties() {
		basicProperties = new DefaultEditableProperties("Basic Properties");
		ColorProperty colorProperty = new ColorProperty("Color property", colorPropertyText = new SimpleMultiLanguageSupport("Color property"), Color.blue);
		basicProperties.addEditableProperty(colorProperty);
		basicProperties.addEditableProperty(new BooleanProperty("Boolean property", false));
		basicProperties.addEditableProperty(new TextProperty("Text property", "This is a text property."));
		basicProperties.addEditableProperty(monitor.getRecycleBoundaryProperty());
		basicProperties.addEditableProperty(new NumberProperty("Distance", 100, 1, "m"));
		basicProperties.addEditableProperty(new DateProperty("Date"));
	}

	private void createHistoryLineChart() {
		RecentMemoryUsageHistory history = new DefaultMemoryUsageEventHistory(50);
		monitor.addMemoryUsageUpdateListener(history);
		historyLineChart = new RecentMemoryUsageLineChart(history);
		historyLineChart.setColors(colors);
		historyLineChart.setUseColorsToDrawStatisticData(true);
		pages.add("Recent Memory Usage", historyLineChart);
		showTextualColorChooserAction = new ShowColorChooserAction(historyLineChart.getTextualColorProperty());
		showBackgroundColorChooserAction = new ShowColorChooserAction(historyLineChart.getBackgroundColorProperty());
		showForegroundColorChooserAction = new ShowColorChooserAction(historyLineChart.getForegroundColorProperty());
		showTextualColorChooserAction.setUseColorInfoAsActionName(false);
		showBackgroundColorChooserAction.setUseColorInfoAsActionName(false);
		showForegroundColorChooserAction.setUseColorInfoAsActionName(false);
	}

	private void createStatusBar() {
		OnOffSwitch onOffSwitch = new OnOffSwitch(monitor.getAutoRecycleProperty(), "Open", "Close");
		getContentPane().add(onOffSwitch, SOUTH);
	}

	private void generateGarbage() {
		garbage = new int[2000000];
		for(int i = 0; i < garbage.length; i++) {
			garbage[i] = i;
		}
	}

	public static void main(String[] args) {
		JFrame frame = new SwayDemoFrame();
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		generateGarbage();
	}
}
