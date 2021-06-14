package com.manics.rest.service;

import java.util.ArrayList;
import java.util.List;

import com.manics.rest.exception.NotFoundException;
import com.manics.rest.model.request.SuggestionRequest;
import com.manics.rest.repository.SuggestionRepository;
import com.manics.rest.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.manics.rest.model.Suggestion;
import com.manics.rest.model.User;
@Service 
public class SuggestionService{

    @Autowired
    SuggestionRepository suggestionRepository;

    @Autowired
    UserRepository userRepository;

    public List<Suggestion> getSuggestion(){
        List<Suggestion> suggestions = new ArrayList<>();
        suggestionRepository.findAll().iterator().forEachRemaining(suggestions::add);
        return suggestions;
    }

    public Suggestion getSuggestionById(Integer suggestionId){
        return suggestionRepository
                .findById(suggestionId)
                .orElseThrow(
                    () -> new NotFoundException("No se encontro la sugerencia con Id " + suggestionId)
                );
    }

    public Suggestion createSuggestion(SuggestionRequest req, String username){
        User user = userRepository.findByUsername(username);
        Suggestion suggestion = new Suggestion(user, req.getContent());
        Suggestion result = suggestionRepository.save(suggestion);
        return result;
    }

}