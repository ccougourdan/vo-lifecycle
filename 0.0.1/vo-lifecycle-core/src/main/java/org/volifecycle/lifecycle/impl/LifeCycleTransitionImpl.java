package org.volifecycle.lifecycle.impl;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.volifecycle.utils.DateUtils.getCurrentTime;

import java.util.List;

import org.volifecycle.event.EventManager;
import org.volifecycle.event.vo.Event;
import org.volifecycle.lifecycle.LifeCycleAdapter;
import org.volifecycle.lifecycle.LifeCycleChecker;
import org.volifecycle.lifecycle.LifeCycleConstants;
import org.volifecycle.lifecycle.LifeCycleTransition;

/**
 * Transition implementation
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 * 
 * @param <T> value object type
 */
public class LifeCycleTransitionImpl<T> implements LifeCycleTransition<T> {
    protected List<LifeCycleChecker<T>> checkers;
    protected String target;

    /**
     * auto | manual
     */
    protected String type;

    /**
     * Description
     */
    protected String description;

    /**
     * @return the checkers
     */
    public List<LifeCycleChecker<T>> getCheckers() {
        return checkers;
    }

    /**
     * @param checkers the checkers to set
     */
    public void setCheckers(List<LifeCycleChecker<T>> checkers) {
        this.checkers = checkers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTarget() {
        return target;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String changeState(T valueObject, LifeCycleAdapter<T> adapter, EventManager evtManager) {
        return changeState(valueObject, adapter, evtManager, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String changeState(T valueObject, LifeCycleAdapter<T> adapter, EventManager evtManager, List<String> forcedCheckers) {
        String rtn = LifeCycleConstants.TRUE;

        if (isNotEmpty(checkers)) {
            for (LifeCycleChecker<T> checker : checkers) {
                boolean filter = false;

                // Searching if is a checker to ignore
                if (isNotEmpty(forcedCheckers)) {
                    for (String idChecker : forcedCheckers) {
                        if (null != checker.getId() && idChecker.equalsIgnoreCase(checker.getId())) {
                            filter = true;
                            break;
                        }
                    }
                }

                String result = checker.getResult(valueObject);
                if (null == result || LifeCycleConstants.FALSE.equalsIgnoreCase(result)) {
                    if (!filter) {
                        rtn = LifeCycleConstants.FALSE;
                        String message = "Failed checker : " + checker.getId();
                        logCustomEvent(valueObject, adapter, evtManager, LifeCycleConstants.EVENT_TYPE_FAILED_CHECKER, message);
                        break;
                    } else {
                        String message = "Forced checker : " + checker.getId();
                        logCustomEvent(valueObject, adapter, evtManager, LifeCycleConstants.EVENT_TYPE_FORCED_CHECKER, message);
                    }
                }
            }
        }

        return rtn;
    }

    /**
     * Log custom event
     * 
     * @param typeEvent
     * @param details
     */
    public void logCustomEvent(T valueObject, LifeCycleAdapter<T> adapter, EventManager evtManager, String typeEvent, String details) {
        Event event = new Event();
        event.setTypeEvent(typeEvent);
        event.setDate(getCurrentTime());
        event.setDetails(details);
        event.setActor(LifeCycleConstants.SYS_ACTOR);
        event.setIdValueObject(adapter.getId(valueObject));
        event.setTypeValueObject(adapter.getType(valueObject));
        evtManager.logEvent(event);
    }
}
