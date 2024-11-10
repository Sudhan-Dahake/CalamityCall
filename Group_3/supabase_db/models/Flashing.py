from . import BasePreference

class FlashingModel(BasePreference):
    def __init__(self):
        super().__init__(tableName="flashing")