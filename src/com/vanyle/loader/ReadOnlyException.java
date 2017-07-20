package com.vanyle.loader;

public class ReadOnlyException
  extends Exception
{
  private static final long serialVersionUID = 1L;
  
  public ReadOnlyException() {}
  
  public ReadOnlyException(String s)
  {
    super(s);
  }
}
