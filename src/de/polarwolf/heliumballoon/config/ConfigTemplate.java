package de.polarwolf.heliumballoon.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import de.polarwolf.heliumballoon.exception.BalloonException;
import de.polarwolf.heliumballoon.helium.HeliumSection;

public class ConfigTemplate {
	
	private final String name;
	private ConfigRule rule;
	private ConfigAnimal animal;
	private List<ConfigElement> elements = new ArrayList<>();
	private String custom;
	
	
	public ConfigTemplate(String name) {
		this.name = name;
	}
	
	
	public ConfigTemplate(ConfigurationSection fileSection, ConfigSection balloonSection) throws BalloonException {
		this.name = fileSection.getName();
		loadConfig(fileSection, balloonSection);
	}
	
	
	public String getName() {
		return name;
	}


	public ConfigRule getRule() {
		return rule;
	}


	protected void setRule(ConfigRule rule) {
		this.rule = rule;
	}


	public ConfigAnimal getAnimal() {
		return animal;
	}


	protected void setAnimal(ConfigAnimal animal) {
		this.animal = animal;
	}


	public List<ConfigElement> enumElements() {
		return new ArrayList<>(elements);
	}
	
	
	protected void replaceElements(List<ConfigElement> newElements) {
		this.elements = newElements;
	}
	

	public String getCustom() {
		return custom;
	}


	protected void setCustom(String custom) {
		this.custom = custom;
	}
	
	
	protected void loadAnimalConfig(ConfigurationSection fileSection) throws BalloonException {
		ConfigurationSection fileSectionAnimal = fileSection.getConfigurationSection(ParamTemplate.ANIMAL.getAttributeName());
		animal = new ConfigAnimal (fileSectionAnimal);
	}
	
	
	protected void loadElementsConfig(ConfigurationSection fileSection) throws BalloonException {
		ConfigurationSection fileSectionElements = fileSection.getConfigurationSection(ParamTemplate.ELEMENTS.getAttributeName());
		
		List<ConfigElement> myElements = new ArrayList<>();
		for (String elementName : fileSectionElements.getKeys(false)) {
			if (!fileSectionElements.contains(elementName, true)) { // ignore default from jar
				continue;
			}
			if (!fileSectionElements.isConfigurationSection(elementName)) {
				throw new BalloonException (getName(), "Illegal elements section", elementName);
			}
			myElements.add(new ConfigElement(fileSectionElements.getConfigurationSection(elementName)));
		}
		replaceElements(myElements);
	}


	protected void loadConfig(ConfigurationSection fileSection, ConfigSection balloonSection) throws BalloonException {
		HeliumSection heliumSection = new HeliumSection(fileSection, Arrays.asList(ParamTemplate.values()));

		String ruleName = heliumSection.getString(ParamTemplate.RULE);
		if ((ruleName == null) || (ruleName.isEmpty())) {
			setRule(balloonSection.getDefaultRule());
		} else {
			ConfigRule myRule = balloonSection.findRule(ruleName);
			if (myRule == null) {
				throw new BalloonException (getName(), "Unknown rule", ruleName);
			}
			setRule(myRule);
		}
		
		if (heliumSection.isSection(ParamTemplate.ANIMAL)) {
			loadAnimalConfig(fileSection);
		}
		if (heliumSection.isSection(ParamTemplate.ELEMENTS)) {
			loadElementsConfig(fileSection);
		}
						
		setCustom(heliumSection.getString(ParamTemplate.CUSTOM));
	}

}
