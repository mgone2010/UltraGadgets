package br.com.floodeer.ultragadgets.util;

import org.bukkit.entity.Player;

public enum Direction
{
	NORTH(1, 0),
	SOUTH(-1, 0),
	EAST(0, 1),
	WEST(0, -1),
	NORTHEAST(1, 1),
	SOUTHEAST(-1, 1),
	NORTHWEST(1, -1),
	SOUTHWEST(-1, -1);
	
	private int x;
	private int z;
	
	private Direction( int x , int z )
	{
		this.x = x;
		this.z = z;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getZ()
	{
		return z;
	}
	
	public static Direction getDirection(String direction)
	{
		for ( Direction dir : Direction.values() )
		{
			if ( dir.name().equalsIgnoreCase(direction) )
				return dir;
		}
		return null;
	}

	  public static Direction getCardinalDirection(Player player)
	  {
	    double rotation = (player.getLocation().getYaw() - 90.0F) % 360.0F;
	    if (rotation < 0.0D) {
	      rotation += 360.0D;
	    }
	    if ((0.0D <= rotation) && (rotation < 67.5D)) {
	      return Direction.NORTH;
	    }
	    if ((67.5D <= rotation) && (rotation < 112.5D)) {
	      return Direction.EAST;
	    }
	    if ((157.5D <= rotation) && (rotation < 247.5D)) {
	      return Direction.SOUTH;
	    }
	    if ((247.5D <= rotation) && (rotation < 337.5D)) {
	      return Direction.WEST;
	    }
	    return Direction.NORTH;
	  }
	}