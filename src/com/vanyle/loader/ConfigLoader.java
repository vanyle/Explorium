package com.vanyle.loader;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ConfigLoader
  extends FileLoader
{
  private static final String version = "pre-alpha 0.1";
  Map<String, String> m = new HashMap<String,String>();
  public static final String[] EDITABLE = { "volume", "music" };
  public static final String[] KEYS = { "version", "volume", "music", "difficulty", "generationDiversity" };
  public static final String[] DEFAULT = {version, "1", "1", "1", "3" };
  
  public ConfigLoader(File f)
  {
    super(f);
    String s = new String(fileContent);
    String[] cfs = s.split("\n");
    for (short i = 0; i < cfs.length; i = (short)(i + 1))
    {
      String[] current = cfs[i].split("%");
      if (current.length >= 2)
      {
        String key = fromBase64(current[0]);
        String value = fromBase64(current[1]);
        if (!"version".equals(key)) {
          for (short j = 0; j < KEYS.length; j = (short)(j + 1)) {
            if (KEYS[j].equals(key)) {
              m.put(key, value);
            }
          }
        }
      }
    }
    for (short i = 0; i < KEYS.length; i = (short)(i + 1)) {
      if (!m.containsKey(KEYS[i])) {
        m.put(KEYS[i], DEFAULT[i]);
      }
    }
    overWriteConfig();
  }
  
  public String get(String key)
  {
    return (String)m.get(key);
  }
  
  public void overWriteConfig()
  {
    Iterator<String> values = m.keySet().iterator();
    
    String f = "";
    while (values.hasNext())
    {
      String next = (String)values.next();
      f = f + toBase64(next) + '%' + toBase64((String)m.get(next));
      if (values.hasNext()) {
        f = f + '\n';
      }
    }
    fileContent = f.getBytes();
    save();
  }
  
  public void generateDebugLog()
  {
    System.out.println("Generating Debug Log ...");
    Iterator<String> values = m.keySet().iterator();
    while (values.hasNext())
    {
      String next = (String)values.next();
      System.out.println(next + '=' + (String)m.get(next));
    }
  }
  
  public void edit(String key, String value)
    throws ReadOnlyException
  {
    for (short i = 0; i < EDITABLE.length; i = (short)(i + 1)) {
      if (EDITABLE[i].equals(key)) {
        throw new ReadOnlyException();
      }
    }
    if (m.get(key) != null) {
      m.replace(key, value);
    }
  }
}
