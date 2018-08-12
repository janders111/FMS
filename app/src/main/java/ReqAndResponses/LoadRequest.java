package ReqAndResponses;

import model.Event;
import model.Person;
import model.User;

/**
 * From the specification: The users property in the request body contains an array of users to be
 created. The persons and events properties contain family history information for these
 users. The objects contained in the persons and events arrays should be added to the
 server’s database. The objects in the users array have the same format as those passed to
 the /user/register API with the addition of the personID. The objects in the persons array have
 the same format as those returned by the /person/[personID] API. The objects in the events
 array have the same format as those returned by the /event/[eventID] API.
 */
public class LoadRequest {
    User users[];
    Person persons[];
    Event events[];

    /**
     *
     * @param users Array of users to be created.
     * @param persons Persons in the tree of the users.
     * @param events Events in the life of the users.
     */
    public LoadRequest(User[] users, Person[] persons, Event[] events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public User[] getUsersArray() {
        return users;
    }

    public Person[] getPersonsArray() {
        return persons;
    }

    public Event[] getEventsArray() {
        return events;
    }
}
