from . import BasePreference

class TextToSpeechModel(BasePreference):
    def __init__(self):
        super().__init__(tablename="text_to_speech")