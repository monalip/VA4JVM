package se.kth.jpf_visual;



import java.util.Map;

/**
 * Should be implemented by publishers that redirect output to the Shell's trace
 * report panel.
 * 
 * @author Igor Andjelkovic
 * 
 */
public interface TopicPublisher {

  /**
   * Returns publisher's output grouped by topic name.
   * 
   * @return publisher's output grouped by topic name
   */
  public Map<String, Topic> getResults();

}
