package service.ReqAndResponses;

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
    User usersArray[];
    Person personsArray[];
    Event eventsArray[];

    /**
     *
     * @param usersArray Array of users to be created.
     * @param personsArray Persons in the tree of the users.
     * @param eventsArray Events in the life of the users.
     */
    public LoadRequest(User[] usersArray, Person[] personsArray, Event[] eventsArray) {
        this.usersArray = usersArray;
        this.personsArray = personsArray;
        this.eventsArray = eventsArray;
    }

    public User[] getUsersArray() {
        return usersArray;
    }

    public void setUsersArray(User[] usersArray) {
        this.usersArray = usersArray;
    }

    public Person[] getPersonsArray() {
        return personsArray;
    }

    public void setPersonsArray(Person[] personsArray) {
        this.personsArray = personsArray;
    }

    public Event[] getEventsArray() {
        return eventsArray;
    }

    public void setEventsArray(Event[] eventsArray) {
        this.eventsArray = eventsArray;
    }
}
