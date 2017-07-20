package com.vanyle.loader;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class FileLoader
{
  protected byte[] fileContent;
  private File f;
  
  public FileLoader(File in)
  {
    f = in;
    load();
  }
  
  public static String toBase64(String str)
  {
    return new String(Base64.getEncoder().encode(str.getBytes()));
  }
  
  public static String fromBase64(String str)
  {
    return new String(Base64.getDecoder().decode(str));
  }
  
  private void FileNotFoundHandel()
  {
    System.err.println("Unable to find " + f.getAbsolutePath());
  }
  
  public void reload()
  {
    load();
  }
  
  private void load()
  {
    try
    {
      DataInputStream dis = new DataInputStream(
        new BufferedInputStream(
        new FileInputStream(f)));
      fileContent = new byte[dis.available()];
      dis.read(fileContent);
      dis.close();
    }
    catch (FileNotFoundException e)
    {
      FileNotFoundHandel();
      fileContent = new byte[0];
    }
    catch (IOException e)
    {
      e.printStackTrace();
      fileContent = new byte[0];
    }
  }
  
  public void save()
  {
    try
    {
      FileOutputStream out = new FileOutputStream(f);
      out.write(fileContent);
      out.close();
    }
    catch (FileNotFoundException e)
    {
      FileNotFoundHandel();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}
