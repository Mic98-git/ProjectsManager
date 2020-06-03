package it.uniroma3.siw.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.uniroma3.siw.spring.model.Project;
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
		assertEquals(user1.getId().longValue(), 1L);
		assertEquals(user1.getName(),"Maria");
		
//		Project project1 = new Project("testProject1","è il test proj 1");
		
	}

}
