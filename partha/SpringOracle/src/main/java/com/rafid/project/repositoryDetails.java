package com.rafid.project;

import groovy.lang.Singleton;
import org.kohsuke.github.GHRepository;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by ASUS on 01-May-17.
 */
@Singleton
public class repositoryDetails {
    private String  name;
    private String  lastMessage;
    private String lastCommitter;
    private String repositoryLink;


    public repositoryDetails(String name, String lastMessage, String lastCommitter, String repositoryLink) {
        this.name = name;
        this.lastMessage = lastMessage;
        this.lastCommitter = lastCommitter;
        this.repositoryLink = repositoryLink;
    }

    public repositoryDetails(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getLastCommitter() {
        return lastCommitter;
    }

    public String getRepositoryLink() {
        return repositoryLink;
    }

    public static List<repositoryDetails> getAsList(Map<String, GHRepository> userRepositoryMap)
    {

        List<repositoryDetails> repositoryDetails=new LinkedList<>();
        for(String key:userRepositoryMap.keySet())
        {

                repositoryDetails temp=new repositoryDetails(userRepositoryMap.get(key).getName()
                       // ,userRepositoryMap.get(key).queryCommits().list().asList().get(0).getCommitShortInfo().getMessage(),
                       // userRepositoryMap.get(key).queryCommits().list().asList().get(0).getCommitShortInfo().getCommitter().getName(),
                      //  userRepositoryMap.get(key).getGitTransportUrl()
                        );
                repositoryDetails.add(temp);


        }
        return repositoryDetails;
    }

}
