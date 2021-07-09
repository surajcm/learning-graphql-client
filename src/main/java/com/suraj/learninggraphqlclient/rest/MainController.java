package com.suraj.learninggraphqlclient.rest;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rick.morty.EpisodesByIdsQuery;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/rick")
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    private String responseHolder = null;

    @GetMapping(name = "/morty/", produces = "application/json")
    public ResponseEntity<Object> episodeById(@RequestParam(value = "id", defaultValue = "1") String id) {
        ApolloClient apolloClient = ApolloClient.builder()
                .serverUrl("https://rickandmortyapi.com/graphql")
                .build();
        logger.info("Started querying....");
        apolloClient.query(new com.rick.morty.EpisodesByIdsQuery(List.of("1")))
                .enqueue(new ApolloCall.Callback<>() {

                    @Override
                    public void onResponse(@NotNull Response<EpisodesByIdsQuery.Data> response) {
                        populateResponseHolder(response.getData().episodesByIds().get(0));
                        logger.info("Apollo :first id is: " + response.getData().episodesByIds().get(0).name());
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        logger.error("Apollo : Error", e);
                    }
                });
        logger.info("Done querying....");
        return new ResponseEntity<>(responseHolder, HttpStatus.OK);
    }

    private void populateResponseHolder(EpisodesByIdsQuery.EpisodesById episodesById) {
        String resp;
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            resp = mapper.writeValueAsString(episodesById);
            logger.info("resp is :"+resp);
        } catch (IOException ex) {
            resp = "ERROR";
            logger.error("error parsing to json : " + ex.getMessage());
        }
        responseHolder = resp;
    }
}
