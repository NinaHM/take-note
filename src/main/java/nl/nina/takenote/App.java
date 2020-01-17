package nl.nina.takenote;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import nl.nina.takenote.view.Window;

@SpringBootApplication
public class App {

	public static void main(String[] args) {
		new SpringApplicationBuilder(App.class)
	        .headless(false)
	        .web(WebApplicationType.NONE)
	        .run(args);
	}
	
	@Bean
    public Window frame() {
        return new Window();
    }
}
