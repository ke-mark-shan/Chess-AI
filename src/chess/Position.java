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
	
	public boolean equals(Position other){
		return (this.first == other.getFirst() && this.second == other.getSecond());
	}
}
