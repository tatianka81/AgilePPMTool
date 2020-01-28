package io.agileintelligence.ppmtool.service;

import io.agileintelligence.ppmtool.domain.Project;
import io.agileintelligence.ppmtool.exceptions.ProjectIdException;
import io.agileintelligence.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project){

        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return  projectRepository.save(project);
        } catch (Exception e){
            throw  new ProjectIdException("Project ID" + project.getProjectIdentifier().toUpperCase() + "exist");
        }
    }

    public Project findProjectByIdentifier(String projectId){
        Project tempProject = projectRepository.findByProjectIdentifier(projectId);

        if (tempProject == null){
            throw new ProjectIdException("Project " +projectId+ " dosn't exist");
        }
        return tempProject;
    }

    public Iterable<Project> findAllProject(){
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId){
        Project tempProject = projectRepository.findByProjectIdentifier(projectId);

        if (tempProject == null){
            throw new ProjectIdException("Project " + projectId + " dosn't exist");
        }

        projectRepository.delete(tempProject);
    }

    public Project update(Project project){
        return projectRepository.save(project);
    }
}
