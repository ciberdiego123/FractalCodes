package comanche;

public class SequentialScheduler implements Scheduler {
  public void schedule (Runnable task) { task.run(); }
}
