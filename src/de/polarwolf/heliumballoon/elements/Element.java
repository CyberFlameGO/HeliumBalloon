package de.polarwolf.heliumballoon.elements;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import de.polarwolf.heliumballoon.exception.BalloonException;

public interface Element {
	
	// Returns true if the Element is valid.
	// If not, the Element must get refreshed by using "show".
	public boolean isValid();
	
	
	// Check if the Element contains the given entity.
	// This is needed because an Element can contain more than one Entity.
	public boolean hasEntity(Entity entityToCheck);
	
	// Returns the underlying Entity.
	// Can be null if the Element has no unique Entity.
	public Entity getEntity();
	
	// Returns the player who owns the Element. Can be null.
	// This is used to attach a rope.
	public Player getPlayer();
	
	// Get the current location of the Element.
	// It's not the real position of the underlying Entity
	// because it's before the offset is applied.
	// Null means that current location can not be detected,
	// so it's expected that the caller recreates the Element by using "show".
	public Location getCurrentCentralLocation();
	
	// Get the offset from the central location
	public Vector getOffset();

	
	// Hide the Entity by removing it.
	public void hide();
	
	// Remove the old entity by calling "hide". 
	// Then create a new  entity by calling "spawn".
	public void show(Location centralLocation) throws BalloonException;
	
	// Set the Velocity (direction to travel to)
	// Should not be null. Use an empty Vector instead. 
	public void setVelocity(Vector newVelocity);
	
	// The supervising object should call this function on every Tick
	// because the underlying Entity can have a limited lifetime.
	public void keepAlive();
		
}
