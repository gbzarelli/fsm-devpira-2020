import random


def hidden_to_visible_action(pokemon):
    print(f'Trying to find {pokemon.name}...')
    value = random.randint(0, pokemon.level)
    if value > 3:
        print('Uh oh! Pokemon 404!')
        return False

    print(f'Wild {pokemon.name} appeared!')
    return True
