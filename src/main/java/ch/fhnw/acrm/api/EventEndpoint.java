package ch.fhnw.acrm.api;

import ch.fhnw.acrm.business.service.AgentService;
import ch.fhnw.acrm.data.domain.Agent;
import ch.fhnw.acrm.business.service.EventService;
import ch.fhnw.acrm.data.domain.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.ConstraintViolationException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class EventEndpoint {
    @Autowired
    private EventService eventService;
    @Autowired
    private AgentService agentService;

    @PostMapping(path = "/eventHandling", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Event> postMedia(@RequestBody String str) {
        Event event;
        try {
            Agent agent = agentService.getCurrentAgent();
            JSONObject json = new JSONObject(str);
            ObjectMapper mapper = new ObjectMapper();
            event = mapper.readValue(json.toString(), Event.class);
            event.setAgent(agent);
            event = eventService.saveEvent(event);
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getConstraintViolations().iterator().next().getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{mediaID}")
                .buildAndExpand(event.getId()).toUri();

        return ResponseEntity.created(location).body(event);
    }

    @GetMapping(path = "/allEventHandling", produces = "application/json")
    public List<Event> getMedia() {
        return eventService.getAllEvent();
    }

    @GetMapping(path = "/eventHandling", produces = "application/json")
    public List<Event> getAgentMedia() {
        Agent agent = agentService.getCurrentAgent();
        Long agentId = agent.getId();
        return eventService.getAgentEvent(agentId);
    }

    @GetMapping(path = "/eventHandling/{agentName}", produces = "application/json")
    public ResponseEntity<List<Event>> getNameMedia(@PathVariable(value = "agentName") String agentName) {
        List<Event> events = null;
        try {
            events = eventService.getNameEvent(agentName);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.ok(events);
    }

    @GetMapping(path = "/likeEvent/{eventID}", produces = "application/json")
    public ResponseEntity<Long> getMediaLikes(@PathVariable(value = "eventID") String eventID) {
        long likesCount;
        try {
            Event event = eventService.getEventByID(Long.valueOf(eventID));
            likesCount = event.getLikes().size();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.ok(likesCount);
    }

    @PutMapping(path = "/likeEvent/{eventID}", produces = "application/json")
    public ResponseEntity<Event> putLike(@PathVariable(value = "eventID") String eventID) {
        Event event;
        try {
            Agent agent = agentService.getCurrentAgent();
            event = eventService.getEventByID(Long.valueOf(eventID));
            eventService.putEventLike(event, agent);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
        return ResponseEntity.accepted().body(event);
    }
}