from fsm.fsm_factory import StateMachineFactory
from fsm.fsm_transaction import StateMachineTransaction
from pokecatcher.actions import hidden_to_visible_action, injured_to_attack_action
from pokecatcher.domain import States, Events
from pokecatcher.pokecatcher_fsm import PokeCatcherFsm

if __name__ == '__main__':
    fsm_factory = StateMachineFactory(
        states=States, final_states=(States.CAPTURED, States.DEAD)
    ).add_transaction(StateMachineTransaction(
        state=States.HIDDEN, event=Events.FIND, target=States.VISIBLE, action_method=hidden_to_visible_action)
    ).add_transaction(StateMachineTransaction(
        state=States.VISIBLE, event=Events.OBSERVE, target=States.VISIBLE)
    ).add_transaction(StateMachineTransaction(
        state=States.VISIBLE, event=Events.ATTACK, target=States.INJURED)
    ).add_transaction(StateMachineTransaction(
        state=States.INJURED, event=Events.ATTACK, target=States.DEAD, action_method=injured_to_attack_action)
    ).add_transaction(StateMachineTransaction(
        state=States.INJURED, event=Events.CATCH, target=States.CAPTURED))

    PokeCatcherFsm(fsm_factory) \
        .start_main_loop()
