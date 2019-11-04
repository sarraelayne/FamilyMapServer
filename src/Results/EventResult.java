package Results;

import Model.Event;

/**
 * result of event result getting an event or events
 */
public class EventResult extends Result {
    private Event[] data;
    private String message;

    public EventResult(Event[] data) {
        this.data = data;
    }
    public EventResult(String message) {
        this.message = message;
    }

    public Event[] getEvent() {return data;}
}
