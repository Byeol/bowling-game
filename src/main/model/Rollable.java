package main.model;


public interface Rollable {
	public void roll(PinCount knockOverCount) throws Exception;
	public boolean canRoll();
}
