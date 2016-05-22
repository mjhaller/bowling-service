package bowling.frame;

public enum Mark {
	
	OPEN(""),
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
