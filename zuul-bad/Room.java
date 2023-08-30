﻿/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class Room 
{
    private String description;
    private HashMap<String, Roomm> exits;
    private Items item;
    private ArrayList<Items> items;
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description, Items item) 
    {
        this.description = description;
        this.item = item;
        exits = new HashMap<>();
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */
    public void setExits(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }

    public String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys){
            returnString += " " + exit;
        }
        return returnString;
    }

    public String getLongDescription()
    {
        return "You are " + description + getItems() + ".\n" + getExitString();
    }

    public void setItem(Items item) 
    {
        this.item = item;
    }

    public String getItem()
    {
        String returnString = "Peso: " + item.getDescription() + ".\n" + "Peso: " + item.getWeight();
        return returnString;
    }

    public void addItem(Items item)
    {
        items.add(item);
    }

    public ArrayList<Item> getItems() 
    {
        return items;
    }

}
