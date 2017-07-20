package com.vanyle.loader;

import com.vanyle.graphics.ExploriumFrame;
import com.vanyle.graphics.Menu;
import java.io.File;

public class Explorium
{
  public static final int resolutionX = 1024;
  public static final int resolutionY = 720;
  private static ConfigLoader config;
  
  public static void main(String[] args)
  {
    config = new ConfigLoader(new File("./c.cf"));
    
    ExploriumFrame ef = new ExploriumFrame();
    ef.setDisplayFeed(new Menu(ef));
  }
  
  public static String getConfig(String key)
  {
    return config.get(key);
  }
  
  public static void editConfig(String key, String value)
    throws ReadOnlyException
  {
    config.edit(key, value);
  }
}
