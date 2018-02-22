package chess;

public class PlayerHuman extends Player {
	
	public PlayerHuman(PlayerColour pc) {
		super(pc, PlayerType.HUMAN);
	}

	@Override
	public void startTurn() {
		//UI should be used to make move
	}
}
