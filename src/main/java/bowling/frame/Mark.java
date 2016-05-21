package bowling.frame;

public enum Mark {
	
	SPARE("/"),
	STRIKE("X");

	String mark;
	
	Mark(String mark)
	{
		this.mark = mark;
	}
	
	public String display()
	{
		return mark;
	}
}
