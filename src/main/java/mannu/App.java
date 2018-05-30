package mannu;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

	public static void main(String[] args) 
	{
		@SuppressWarnings("resource")
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringContext.class);
        ClintMail clintMail = (ClintMail) context.getBean("clintMail");
        clintMail.setArgs(args);
	}

}
