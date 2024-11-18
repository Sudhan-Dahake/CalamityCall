from . import BasePreference

class NoiseModel(BasePreference):
    def __init__(self):
        super().__init__(tableName="noise")