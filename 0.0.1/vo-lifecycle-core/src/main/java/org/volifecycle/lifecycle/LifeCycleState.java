package org.volifecycle.lifecycle;

import java.util.Map;

/**
 * State interface
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public interface LifeCycleState<T> {

    /**
     * @return the transitionsById
     */
    Map<String, LifeCycleTransition<T>> getTransitionsById();

    /**
     * @return the id
     */
    String getId();

    /**
     * @return the description
     */
    String getDescription();
}
