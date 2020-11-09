from abc import ABC, abstractmethod


class StateMachine(ABC):
    """
    Use the fsm.state_machine_factory.StateMachineFactory to build a new Machine
    """

    @abstractmethod
    def send_event(self, event, message=None):
        pass

    @property
    @abstractmethod
    def machine_id(self):
        pass

    @property
    @abstractmethod
    def current_state(self):
        pass


class _StateMachineImpl(StateMachine):

    def __init__(self, machine_id, initial_state, final_states, transactions):
        self.__machine_id = machine_id
        self.__current_state = initial_state
        self.__final_states = final_states
        self.__transactions = transactions
        self.__ended = self.__is_ended()

    def send_event(self, event, message=None):
        if self.__ended:
            print(f'The machine {self.__machine_id} was ended')
            return True

        transaction = self.__get_transaction_from_event(event)
        if not transaction:
            return False

        if self.__finalize_transaction_change(message, transaction):
            self.__current_state = transaction.target
            self.__ended = self.__is_ended()

        return True

    @staticmethod
    def __finalize_transaction_change(message, transaction):
        return transaction.action_method(message) if transaction.action_method else True

    def __get_transaction_from_event(self, event):
        transaction_group = self.__transactions.get(self.__current_state)
        if not transaction_group or not transaction_group.get(event):
            return None
        return transaction_group[event]

    def __is_ended(self):
        return self.__current_state in self.__final_states

    @property
    def machine_id(self):
        return self.__machine_id

    @property
    def current_state(self):
        return self.__current_state
