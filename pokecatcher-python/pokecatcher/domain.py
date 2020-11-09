from enum import Enum, auto


class States(Enum):
    HIDDEN = auto()
    VISIBLE = auto()
    INJURED = auto()
    CAPTURED = auto()
    DEAD = auto()


class Events(Enum):
    FIND = auto()
    OBSERVE = auto()
    ATTACK = auto()
    CATCH = auto()

    @staticmethod
    def get_by_name(name):
        for enum_name, item in Events.__members__.items():
            if enum_name == name.upper():
                return item


class Pokemon:
    def __init__(self, poke_id, name, level):
        self.__poke_id = poke_id
        self.__name = name
        if level > 5:
            raise ValueError('Pokemon level cant be more than 5')
        self.__level = level

    @property
    def poke_id(self):
        return self.__poke_id

    @property
    def name(self):
        return self.__name

    @property
    def level(self):
        return self.__level
