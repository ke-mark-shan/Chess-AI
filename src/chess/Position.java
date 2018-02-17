package chess;

public class Position{
	private int first;
	private int second;
	
	public Position(int first, int second){
		this.first = first;
		this.second = second;
	}
	
	public int getFirst(){
		return this.first;
	}
	public int getSecond(){
		return this.second;
	}
	
	public void setFirst(int a){
		first = a;
	}
	public void setSecond(int b){
		second = b;
	}
	
     public int hashCode() {
         final int prime = 31;
         int result = 1;
         result = prime * result + this.first;
         result = prime * result + this.second;
         return result;
     }
	 
	public boolean equals(Object other){
		if (!(other instanceof Position)) {
	        return false;
	    }
		Position otherPosition = (Position) other;
		return (this.first == otherPosition.getFirst() && this.second == otherPosition.getSecond());
	}
}
