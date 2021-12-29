package ch.fhnw.acrm.business.service;

import ch.fhnw.acrm.data.domain.Agent;
import ch.fhnw.acrm.data.domain.Event;
import ch.fhnw.acrm.data.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Validated
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event saveEvent(@Valid Event event) throws Exception {
        return eventRepository.save(event);
    }

    // Returns all events in the system
    public List<Event> getAllEvent() {
        return eventRepository.findAll();
    }

    // Returns all events posted by a specific user. input is the userID
    public List<Event> getAgentEvent(Long agentID) {
        return eventRepository.findByAgent_IdEquals(agentID);
    }

    // Returns all events posted by a specific user. input is the user name
    public List<Event> getNameEvent(String agentName) {
        return eventRepository.findByAgent_NameEquals(agentName);
    }

    // Returns Event with specific ID
    public Event getEventByID(Long ID) {
        return eventRepository.findByIdEquals(ID);
    }

    // adds or removes a like to a specific Event
    public Event putEventLike(Event event, Agent agent) {
        try {
            Set<Agent> likesList = event.getLikes();

            Set<Agent> tempLikesList = new HashSet<>(likesList);
            tempLikesList.removeIf(agent1 -> agent.getId().equals(agent1.getId()));

            if(likesList.size()-tempLikesList.size() > 0){
                likesList = tempLikesList;
                event.setLikes(likesList);
                eventRepository.save(event);
            }
            else{
                likesList.add(agent);
                event.setLikes(likesList);
                eventRepository.save(event);
            }

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
        return event;
    }
}