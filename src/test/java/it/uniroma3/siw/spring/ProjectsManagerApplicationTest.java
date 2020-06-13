package it.uniroma3.siw.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
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
		
//		Project project1 = new Project("testproject1","è il test project 1"); // FIXME
//		project1.setOwner(user1Update);
//		project1 = projectService.saveProject(project1);
//		assertEquals(project1.getOwner(),user1Update);
//		assertEquals(project1.getName(),"testproject1");
//		
//		Project project2 = new Project("testproject2","è il test project 2"); // FIXME
//		project2.setOwner(user2Update);
//		project2 = projectService.saveProject(project2);
//		assertEquals(project1.getOwner(),user1Update);
//		assertEquals(project1.getName(),"testproject2");
//		
//		project1 = projectService.shareProjectWithUser(project1,user2);
//		List<Project> projects = projectRepository.findByOwner(user1Update);
//		assertEquals(projects.size(),2);
//		assertEquals(projects.get(0),project1);
//		assertEquals(projects.get(1),project2);
//		
//		List<Project> projectsVisibleByUser2 = projectRepository.findByMembers(user2);
//		assertEquals(projectsVisibleByUser2.size(),1);
//		assertEquals(projectsVisibleByUser2.get(0),project1);
//		
//		List<User> project1Members = userRepository.findByVisibleProjects(project1);
//		assertEquals(project1Members.get(0),user2);
//		assertEquals(project1Members.size(),1);
	}

}
