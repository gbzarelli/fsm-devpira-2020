package br.com.helpdev.pokecatcher;

import java.io.BufferedReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class CommandLineReader {

  private static final String COMMAND_EXIT = "exit";

  private final BufferedReader bufferedReader;
  private final CommandLineProcessor processor;

  @Autowired
  CommandLineReader(final BufferedReader reader,
                    final CommandLineProcessor processor) {
    this.bufferedReader = reader;
    this.processor = processor;
  }

  public void run() {
    var command = "";
    do {
      try {
        System.out.print("> ");
        command = bufferedReader.readLine().trim();
        processor.doProcess(command.split(","));
      } catch (final Exception ex) {
        System.out.println("Error: " + ex);
      }
    } while (!command.toLowerCase().startsWith(COMMAND_EXIT));
  }

}
