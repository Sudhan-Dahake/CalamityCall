# import unittest
# from unittest.mock import patch, MagicMock
# from models.reaction_model import ReactionsModel

# class TestReactionsModelEdgeCases(unittest.TestCase):

#     @patch('models.reaction_model.create_client')
#     def setUp(self, mock_create_client):
#         self.mock_client = MagicMock()
#         mock_create_client.return_value = self.mock_client
#         self.reaction_model = ReactionsModel()

#     # Test case for creating a reaction without emoji_type, which should fail.
#     def test_create_reaction_missing_emoji_type(self):
#         result = self.reaction_model.CreateReaction(emoji_type=None, user_id=1, post_id=1)
#         self.assertIsNone(result)

#     # Test case for updating a reaction with an invalid reaction ID.
#     def test_update_reaction_invalid_id(self):
#         self.mock_client.from_().update().eq().execute.return_value = MagicMock(status_code=404)
#         result = self.reaction_model.UpdateReaction(reaction_id=99999, emoji_type="😢")
#         self.assertFalse(result)

#     # Test case for deleting a reaction with an invalid reaction ID.
#     def test_delete_reaction_invalid_id(self):
#         self.mock_client.from_().delete().eq().execute.return_value = MagicMock(status_code=404)
#         result = self.reaction_model.DeleteReaction(reaction_id=99999)
#         self.assertFalse(result)

# if __name__ == '__main__':
#     unittest.main()
