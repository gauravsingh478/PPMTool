package com.greatglory.ppmtool.services;

import com.greatglory.ppmtool.domain.Project;
import com.greatglory.ppmtool.exceptions.ProjectIdException;
import com.greatglory.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    public Project saveOrUpdateProject( Project project){
        try{
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        }catch (Exception e){
            throw new ProjectIdException("Prject id ' "+project.getProjectIdentifier().toUpperCase()+" ' already exists");
        }
    }

    public Project findProjectByIdentifier(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if(project == null)
        {
            throw new ProjectIdException("Prject id ' "+projectId+" ' doesn't exists");
        }
         return project;
    }

    public Iterable<Project> findAllProjects(){
        return projectRepository.findAll();
    }

    public void deleteProjectByProjectId(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if(project == null){
            throw new ProjectIdException("Can't delete project with project id ' "+projectId+" ', This project doesn't exists");
        }
        projectRepository.delete(project);
    }
}
