from fsm.sate_machine import StateMachine
from pokecatcher.domain import States, Events, Pokemon

wild_animals = {
    '1': Pokemon(1, "Bulbasaur", 3),
    '2': Pokemon(2, "Charizard", 5),
    '3': Pokemon(3, "Magikarp", 1)
}


class PokeCatcherFsm:

    def __init__(self, machine_factory):
        self.__machines = {}
        self.__fsm_factory = machine_factory

    def start_main_loop(self):
        print("Input the command: poke_id,action / Like: 1,find:")
        while True:
            data = input().split(",")
            if data[0] == 'exit':
                break

            pokemon = wild_animals.get(data[0])
            if not pokemon:
                print('Pokemon ID is invalid')
                continue

            event = Events.get_by_name(data[1])
            if not event:
                print('Event is invalid')
                continue

            self.execute(pokemon, event)

    def execute(self, pokemon, event):
        machine = self.__get_machine(pokemon)
        accepted_event = machine.send_event(event, pokemon)
        if accepted_event is False:
            print(f'The event {event.name} is not accepted for status {machine.current_state.name}')

    def __get_machine(self, pokemon) -> StateMachine:
        if not self.__machines.get(pokemon.poke_id):
            self.__machines[pokemon.poke_id] = self.__fsm_factory.build_machine(pokemon.poke_id, States.HIDDEN)
        return self.__machines[pokemon.poke_id]
