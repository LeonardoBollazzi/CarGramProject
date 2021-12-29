/*
 * Copyright (c) 2020. University of Applied Sciences and Arts Northwestern Switzerland FHNW.
 * All rights reserved.
 */

package ch.fhnw.acrm.business.service;

import ch.fhnw.acrm.data.domain.Event;
import ch.fhnw.acrm.data.domain.Media;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    public Agent putFollow(Agent follower, Agent followee) {
        try {
            if(!follower.getId().equals(followee.getId())) {
                Set<Agent> followList = follower.getAgentFollows();

                Set<Agent> tempFollowList = new HashSet<>(followList);
                tempFollowList.removeIf(agent1 -> followee.getId().equals(agent1.getId()));

                System.out.println(followList.size());
                System.out.println(tempFollowList.size());


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
}
