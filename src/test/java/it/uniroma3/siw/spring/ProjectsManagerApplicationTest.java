package it.uniroma3.siw.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.uniroma3.siw.spring.controller.session.SessionData;
import it.uniroma3.siw.spring.model.Project;
import it.uniroma3.siw.spring.model.Task;
import it.uniroma3.siw.spring.model.User;
import it.uniroma3.siw.spring.repository.ProjectRepository;
import it.uniroma3.siw.spring.repository.TaskRepository;
import it.uniroma3.siw.spring.repository.UserRepository;
import it.uniroma3.siw.spring.service.ProjectService;
import it.uniroma3.siw.spring.service.TaskService;
import it.uniroma3.siw.spring.service.UserService;

@SpringBootTest
@RunWith(SpringRunner.class)
class ProjectsManagerApplicationTest {
	
	@Autowired
	private UserRepository userRepository;	
	@Autowired
	private TaskRepository taskRepository;	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private UserService userService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private ProjectService projectService;
	
//	@Autowired
//	SessionData sessionData;
	
	@Before
	public void deleteAll() {
		this.userRepository.deleteAll();
		this.taskRepository.deleteAll();
		this.projectRepository.deleteAll();
	}

	@Test
	void testUpdateUser() {
		User user1 = new User("Mario","Rossi");
		user1 = userService.saveUser(user1);
		assertEquals(user1.getId().longValue(), 1L);
		assertEquals(user1.getName(),"Mario");
		
		User user2 = new User("Luca","Bianchi");
		user2 = userService.saveUser(user2);
		assertEquals(user2.getId().longValue(), 2L);
		assertEquals(user2.getName(),"Luca");
				
		User user1Update = new User("Maria","Rossi");
		user1Update.setId(user1.getId());
		user1Update = userService.saveUser(user1Update);
		assertEquals(user1Update.getId().longValue(), 1L);
		assertEquals(user1Update.getName(),"Maria");
		
		Project project1 = new Project("testproject1");
		project1.setOwner(user1Update);
		
		
		Task task1 = new Task("NomeTask1","Descrizione Task 1",false);
		project1.getProjectTasks().add(task1);
		
		project1 = projectService.saveProject(project1);
		assertEquals(project1.getOwner(),user1Update);
		assertEquals(project1.getName(),"testproject1");
		
		Project project2 = new Project("testproject2");
		project2.setOwner(user1Update);
		project2 = projectService.saveProject(project2);
		assertEquals(project2.getOwner(),user1Update);
		assertEquals(project2.getName(),"testproject2");
		
		project1 = projectService.shareProjectWithUser(project1,user2);
		List<Project> projects = projectRepository.findByOwner(user1Update);
		assertEquals(projects.size(),2);
		assertEquals(projects.get(0).getName(),project1.getName());
		assertEquals(projects.get(1).getName(),project2.getName());
		
		List<Project> projectsVisibleByUser2 = projectRepository.findByMembers(user2);
		assertEquals(projectsVisibleByUser2.size(),1);
		assertEquals(projectsVisibleByUser2.get(0).getName(),project1.getName());
		
		List<User> project1Members = userRepository.findByVisibleProjects(project1);
		assertEquals(project1Members.get(0).getName(),user2.getName());
		assertEquals(project1Members.size(),1);
	}

}
