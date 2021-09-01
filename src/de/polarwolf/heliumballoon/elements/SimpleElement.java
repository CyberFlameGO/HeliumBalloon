package de.polarwolf.heliumballoon.elements;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import de.polarwolf.heliumballoon.exception.BalloonException;
import de.polarwolf.heliumballoon.spawnmodifiers.HeliumModifier;
import de.polarwolf.heliumballoon.system.Rule;

public abstract class SimpleElement implements Element{

	private final Player player;
	private final Rule rule;
	protected final HeliumModifier heliumModifier;
	

	protected SimpleElement(Player player, Rule rule, HeliumModifier heliumModifier) {
		this.player = player;
		this.rule=rule;
		this.heliumModifier = heliumModifier;
	}
	
			
	public Rule getRule() {
		return rule;
	}
	
	
	protected abstract void spawn(Location targetLocation) throws BalloonException;


	@Override
	public Player getPlayer() {
		return player;
	}
	

	@Override
	public boolean hasEntity(Entity entityToCheck) {
		return ((getEntity() != null) && (getEntity().equals(entityToCheck)));
	}
	

	@Override
	public void show(Location centralLocation) throws BalloonException {
		hide();
		
		Location targetLocation = centralLocation.clone();
		targetLocation.add(getOffset());
		if (getRule().isEnableRisingYWorkaround()) {
			targetLocation.setY(targetLocation.getY() -0.01);
		}

		spawn(targetLocation);
		heliumModifier.modify(this);
	}
	
	
	@Override
	public void setVelocity(Vector newVelocity) {
		if (getEntity() != null) {
			getEntity().setVelocity(newVelocity);
		}
	}
	
	
	@Override
	public void keepAlive() {
		if (getEntity() != null) {
			getEntity().setTicksLived(1);
		}
	}
	
	
	@Override
	public boolean isValid() {
		if (getEntity() == null) {
			return false;
		} else {
			return getEntity().isValid();
		}
	}
	
	
	@Override
	public Location getCurrentCentralLocation() {
		if ((getEntity() == null) || (!getEntity().isValid())) {
			return null;
		}
		Location location = getEntity().getLocation().clone();
		location.subtract(getOffset());
		return location;
	}

}