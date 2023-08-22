public clas Location
{
private String atualLocation;


public Location(String atualLocation)
{
	this.atualLocation = atualLocation;
}

public void printLocationInfo() {
	System.out.println(atualLocation);
}

public void getLocation(String location){
	atualLocation = location;
}



}