package nl.nina.takenote;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import nl.nina.takenote.controller.Controller;
import nl.nina.takenote.model.Note;
import nl.nina.takenote.view.Window;

@Configuration
public class Config {
	
	@Bean
	public Window window() {
		return new Window();
	}
	
	@Bean
	public Controller controller() {
		return new Controller(window());
	}
		
	@Bean
	@Scope("prototype")
	public Note note() {
		return new Note();
	}
	
}
