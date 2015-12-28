package info.skydark.yaum.mt.event;

import minetweaker.api.event.IEventHandle;
import minetweaker.util.EventList;
import minetweaker.util.IEventHandler;

/**
 * Created by skydark on 15-11-19.
 */
public class EventModule <T> {
    private final EventList<T> eventList = new EventList<T>();

    public IEventHandle on(IEventHandler<T> ev) {
        return this.eventList.add(ev);
    }

    public boolean empty() {
        return !eventList.hasHandlers();
    }

    public void publish(T event) {
        eventList.publish(event);
    }

    public void clear() {
        eventList.clear();
    }
}
