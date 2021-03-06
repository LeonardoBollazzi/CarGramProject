/*
 * Copyright (c) 2020. University of Applied Sciences and Arts Northwestern Switzerland FHNW.
 * All rights reserved.
 */

package ch.fhnw.acrm.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ch.fhnw.acrm.data.domain.Agent;
import ch.fhnw.acrm.data.repository.AgentRepository;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.Set;

@Service
@Validated
public class AgentService {

    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    Validator validator;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveAgent(@Valid Agent agent) throws Exception {
        if (agent.getId() == null) {
            if (agentRepository.findByEmail(agent.getEmail()) != null) {
                throw new Exception("Email address " + agent.getEmail() + " already assigned another agent.");
            }
        } else if (agentRepository.findByEmailAndIdNot(agent.getEmail(), agent.getId()) != null) {
            throw new Exception("Email address " + agent.getEmail() + " already assigned another agent.");
        }
        agent.setPassword(passwordEncoder.encode(agent.getPassword()));
        agentRepository.save(agent);
    }
    /* test get agent password method
    public String getAgentPassword(Agent agent) throws Exception {
        return agent.getPassword();
    }
    */
    public Agent getCurrentAgent() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return agentRepository.findByEmail(user.getUsername());
    }

    public Agent getSpecificAgent(String agentName) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return agentRepository.findByNameEquals(agentName);
    }

    public Agent getAgentByID(Long agentID) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return agentRepository.findByIdEquals(agentID);
    }
/* find agents email
    public Agent getAgentByEmail(String email) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return agentRepository.findByEmail(email);
    }
*/
    public Agent putFollow(Agent follower, Agent followee) {
        try {
            if(!follower.getId().equals(followee.getId())) {
                Set<Agent> followList = follower.getAgentFollows();

                Set<Agent> tempFollowList = new HashSet<>(followList);
                tempFollowList.removeIf(agent1 -> followee.getId().equals(agent1.getId()));

                if (followList.size() - tempFollowList.size() > 0) {
                    followList = tempFollowList;
                    follower.setAgentFollows(followList);
                    agentRepository.save(follower);
                } else {
                    followList.add(followee);
                    follower.setAgentFollows(followList);
                    agentRepository.save(follower);
                }
            }

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
        return follower;
    }

    public Agent editBio(Agent agent, String bio) {
        try {
            agent.setBio(bio);
            agentRepository.save(agent);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
        return agent;
    }

    public int ifFollows(int followeeID) {
        int isFollowing = 0;
        try {
            Agent follower = getCurrentAgent();
            Set<Agent> followList = follower.getAgentFollows();
            Set<Agent> tempFollowList = new HashSet<>(followList);
            tempFollowList.removeIf(agent1 -> followeeID == agent1.getId());

            if (followList.size() - tempFollowList.size() > 0) {
                isFollowing = 1;
            }

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
        return isFollowing;
    }

}
