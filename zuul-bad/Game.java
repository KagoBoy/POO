/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room previousRoom;
       
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, ilhasRoom, campRoom, admRoom, bathRoom, secretRoom;
      
        // create the rooms
        outside = new Room("Na parte de fora da entrada princiapl da lan house");
        ilhasRoom = new Room("Na sala de computadores chamada Ilhas");
        campRoom = new Room("Na sala de computadores frente a frente, direcionada a campeonatos");
        admRoom = new Room("Na sala de admnistrador da lan house");
	bathRoom = new Room("No banheiro unissex da lan house");
        secretRoom = new Room("Você achou a sala secreta, parabéns!!");
        
        // initialise room exits
        /*outside.setExits(null, admRoom, null, null);    
        ilhasRoom.setExits(null, null, bathRoom, admRoom);
        campRoom.setExits(admRoom, bathRoom, secretRoom, null);
        admRoom.setExits(null, ilhasRoom, campRoom, outside);
	bathRoom.setExits(ilhasRoom, null, null, campRoom);
        secretRoom.setExits(campRoom, null, null, null);*/

	// initialise room exits update
	outside.setExits("east", admRoom);
	ilhasRoom.setExits("south", bathRoom);
	ilhasRoom.setExits("west", admRoom);
	campRoom.setExits("north", admRoom);
	campRoom.setExits("east", bathRoom);
	campRoom.setExits("south", secretRoom);
	admRoom.setExits("east", ilhasRoom);
	admRoom.setExits("south", campRoom);
	admRoom.setExits("west", outside);
	bathRoom.setExits("north", ilhasRoom);
	bathRoom.setExits("west", campRoom);
	secretRoom.setExits("north", campRoom);

	//Itens de cada sala
	outside.setItem("Caneta azul", 0.018);
	ilhasRoom.setItem("Cadeado", 0.060);
	campRoom.setItem("Cadeira Gamer", 20.15);
	admRoom.setItem("Vaso de planta", 0.035);
	bathRoom.setItem("Chave de uma sala secreta", 0.010);
	secretRoom.setItem("Bola de pilates", 0.045);
	    
	
        currentRoom = outside;  // start game outside
    }	    
    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    // printLocationInfo atualizado, porém será usado o getLongDescription
    /* private void printLocationInfo()
    {	
	System.out.println("You are " + currentRoom.getDescription() + ".\n" + currentRoom.getExitString());
    }*/

	
    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
        System.out.println();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            	printHelp();
        }
        else if (commandWord.equals("go")) {
            	goRoom(command);
        }
	else if (commandWord.equals("look")) {
		look();
	}
	else if (commandWord.equals("eat")) {
		eat();
	}
	else if (commandWord.equals("back")) {
		goBack();
	}
    	else if (commandWord.equals("quit")) {
            	wantToQuit = quit(command);
    	}

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
	Room previousRoom = currentRoom;
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            System.out.println();
	    ArrayList<Items> itemsInRoom = nextRoom.getItems();
            if (!itemsInRoom.isEmpty()) {
                System.out.println("You see the following items:");
                for (Item item : itemsInRoom) {
                    System.out.println("- " + item.getDescription() + " (Weight: " + item.getWeight() + ")");
                }
            }

        }
    }
    private void goBack() {
        if (previousRoom != null) {
            Room currentRoom = Room previousRoom;
            System.out.println(currentRoom.getLongDescription());
	    System.out.println();
        } else {
            System.out.println("You can't go back any further.");
        }
    }	

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    private void look()
    {
	System.out.println(currentRoom.getLongDescription());
    }
    private void eat()
    {
	System.out.println("You have eaten now and you are not hungry any more.");
    }
}
