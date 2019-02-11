class State:
    def __init__(self, name, population):
        self.name = name
        self.population = population
        self.rate_of_growth = 0
        self.population_rank = 0
        self.fastest_growth_rank = 0

    def set_rate_of_growth(self, rog):
        self.rate_of_growth = rog

    def get_rate_of_growth(self):
        return self.rate_of_growth

    def set_population_rank(self, pop):
        self.population_rank = pop

    def get_population_rank(self):
        return self.population_rank

    def set_fastest_growth_rank(self, fgr):
        self.fastest_growth_rank = fgr

    def get_fastest_growth_rank(self):
        return self.fastest_growth_rank

    def get_name(self):
        return self.name
