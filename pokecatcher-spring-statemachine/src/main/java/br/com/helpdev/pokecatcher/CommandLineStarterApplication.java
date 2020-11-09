package br.com.helpdev.pokecatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CommandLineStarterApplication {

  private final CommandLineReader commandLineRunner;

  @Autowired
  CommandLineStarterApplication(final CommandLineReader commandLineRunner) {
    this.commandLineRunner = commandLineRunner;
  }

  @EventListener(ContextRefreshedEvent.class)
  public void startApplication() {
    commandLineRunner.run();
  }

}
