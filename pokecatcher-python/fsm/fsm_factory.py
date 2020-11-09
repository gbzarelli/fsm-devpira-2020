from fsm.fsm_transaction import StateMachineTransaction
from fsm.sate_machine import StateMachine, _StateMachineImpl


class StateMachineFactory:

    def __init__(self, states, final_states):
        self.__states = states
        self.__final_states = final_states
        self.__transactions = {}

    def add_transaction(self, transaction: StateMachineTransaction):
        if transaction.state not in self.__states:
            raise ValueError(f'Transaction state {transaction.state.name} not in {self.__states}')
        elif self.__transactions.get(transaction.state):
            self.__transactions[transaction.state][transaction.event] = transaction
        else:
            self.__transactions[transaction.state] = {transaction.event: transaction}
        return self

    def build_machine(self, machine_id, current_state) -> StateMachine:
        return _StateMachineImpl(machine_id, current_state, self.__final_states, self.__transactions)
