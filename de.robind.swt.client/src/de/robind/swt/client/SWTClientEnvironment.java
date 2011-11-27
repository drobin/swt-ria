package de.robind.swt.client;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.eclipse.swt.widgets.Display;

import de.robind.swt.msg.SWTRequest;

/**
 * Environment shared between the SWT-loop and {@link SWTClientHandler}.
 *
 * @author Robin Doer
 */
public class SWTClientEnvironment {
  /**
   * The display of the SWT-application.
   */
  public Display display;

  /**
   * Queue contains request-messages received from the server.
   */
  public BlockingQueue<SWTRequest> requestQueue;

  /**
   * Creates a new {@link SWTClientEnvironment}-instance.
   *
   * @param display The display of the SWT-application
   */
  public SWTClientEnvironment(Display display) {
    this.display = display;
    this.requestQueue = new LinkedBlockingQueue<SWTRequest>();
  }
}
