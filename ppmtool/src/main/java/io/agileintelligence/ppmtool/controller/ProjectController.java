package io.agileintelligence.ppmtool.controller;

import io.agileintelligence.ppmtool.domain.Project;
import io.agileintelligence.ppmtool.service.MapValidationErrorService;
import io.agileintelligence.ppmtool.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    MapValidationErrorService mapValidationErrorService;

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result){

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) return errorMap;

        Project tempProject = projectService.saveOrUpdateProject(project);
        return new ResponseEntity<Project>(tempProject, HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId){

        Project tempProject = projectService.findProjectByIdentifier(projectId.toUpperCase());

        return new ResponseEntity<Project>(tempProject, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Project> getAllProjects(){
        return projectService.findAllProject();
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectId){

        projectService.deleteProjectByIdentifier(projectId.toUpperCase());

        return new ResponseEntity<String>( "Project with Id " + projectId + "was deleted", HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<?> updateProject(@RequestBody Project project){

        Project tempProject = projectService.update(project);
        return new ResponseEntity<Project>(tempProject, HttpStatus.OK);
    }
}
