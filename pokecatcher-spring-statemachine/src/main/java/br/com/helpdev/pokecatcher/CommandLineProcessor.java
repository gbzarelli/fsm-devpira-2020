package br.com.helpdev.pokecatcher;

import java.util.HashMap;
import java.util.Map;

import br.com.helpdev.pokecatcher.domain.PokeEvent;
import br.com.helpdev.pokecatcher.domain.PokeEventMessage;
import br.com.helpdev.pokecatcher.domain.PokeState;
import br.com.helpdev.pokecatcher.domain.Pokemon;
import br.com.helpdev.pokecatcher.repository.PokemonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class CommandLineProcessor {

  private final StateMachineFactory<PokeState, PokeEvent> machineFactory;
  private final PokemonRepository repository;
  private final Map<Pokemon, StateMachine<PokeState, PokeEvent>> machines = new HashMap<>();

  public void doProcess(final String... commands) {
    if (commands.length < 2)
      throw new IllegalArgumentException("Enter with min 2 arguments [1=pokemon id; 2=action; 3=powerAttack(in attack action)]");

    final var pokemon = getPokemon(commands[0]);
    final var event = getEvent(commands[1]);
    final var machine = getMachine(pokemon);
    final var message = new PokeEventMessage(pokemon, event, commands);

    final var accepted = machine.sendEvent(message);

    if (!accepted) {
      System.out.printf("The event %s is not accepted for status %s%n", event, machine.getState().getId());
    }
  }

  private PokeEvent getEvent(String command) {
    return PokeEvent.valueOf(command.toUpperCase());
  }

  private Pokemon getPokemon(String command) {
    final var id = Integer.valueOf(command);
    return repository.getPokemonByUuid(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid pokemon ID"));
  }

  private StateMachine<PokeState, PokeEvent> getMachine(final Pokemon pokemon) {
    var machine = machines.getOrDefault(pokemon,
        machineFactory.getStateMachine(pokemon.getId().toString())
    );

    if (!machines.containsKey(pokemon)) {
      machine.start();
      machines.put(pokemon, machine);
    }

    return machine;
  }
}
