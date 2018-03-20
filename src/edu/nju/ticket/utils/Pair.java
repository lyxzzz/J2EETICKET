package edu.nju.ticket.utils;

public class Pair {
	private String header;
	private String footer;
	protected Pair(String header,String footer)
	{
		this.header = header;
		this.footer = footer;
	}
	protected String getHtml(String id)
	{
		return header+id+footer;
	}
}
