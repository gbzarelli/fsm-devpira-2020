class StateMachineTransaction:
    def __init__(self, state, event, target, action_method=None):
        """
        Define a transaction of one state.

        :param state: The State enum
        :param event: The Event enum
        :param target: The State enum target
        :param action_method: The method to be executed when this transaction is called.
         The method needed return a Boolean and receive some object was argument
        """
        self.__state = state
        self.__event = event
        self.__target = target
        self._action_method = action_method

    @property
    def state(self):
        return self.__state

    @property
    def target(self):
        return self.__target

    @property
    def event(self):
        return self.__event

    @property
    def action_method(self):
        return self._action_method
