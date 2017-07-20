package com.vanyle.graphics;

public class DisplayThread
  implements Runnable
{
  ExploriumCanvas ec;
  private long NanoSecondsPerFrame = -1L;
  private long tempNanoSecondsPerFrame = -1L;
  private long oldtime = 0L;
  private static final long NanoToSecond = (long)Math.pow(10, 9);
  private long MinFrameDuration = 0L;
  
  public DisplayThread(ExploriumCanvas exploriumcanvas)
  {
    ec = exploriumcanvas;
  }
  
  public void run()
  {
    for (;;)
    {
      oldtime = System.nanoTime();
      ec.repaint();
      tempNanoSecondsPerFrame = (System.nanoTime() - oldtime);
      if (tempNanoSecondsPerFrame < MinFrameDuration)
      {
        long durationToAdd = 1000L * (MinFrameDuration - tempNanoSecondsPerFrame) / NanoToSecond;
        try
        {
          Thread.sleep((int)durationToAdd);
        }
        catch (InterruptedException e)
        {
          e.printStackTrace();
        }
      }
      NanoSecondsPerFrame = (System.nanoTime() - oldtime);
    }
  }
  
  public int getFPS()
  {
    return (int)(NanoToSecond / (NanoSecondsPerFrame + 0.001D));
  }
  
  public void lockFPS(int min)
  {
    MinFrameDuration = (NanoToSecond / min);
  }
}
