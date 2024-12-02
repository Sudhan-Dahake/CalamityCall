# import unittest
# from unittest.mock import patch, MagicMock
# from Group_10 import ReactionsModel

# class TestReactionsModel(unittest.TestCase):

#     @patch('Group_10.supabase_db.models.reactions.create_client')  # Ensure the correct module path
#     def setUp(self, mock_create_client):
#         self.mock_client = MagicMock()
#         mock_create_client.return_value = self.mock_client
#         self.reaction_model = ReactionsModel()

#     def test_create_reaction_success(self):
#         emoji_type = "\U0001F600"  # 😀 emoji
#         self.mock_client.from_().insert().execute.return_value = MagicMock(status_code=201, data=[{'reaction_id': 1}])
#         result = self.reaction_model.CreateReaction(emoji_type=emoji_type, user_id=1, post_id=1)
#         self.assertIsNotNone(result)
#         self.assertEqual(result[0]['reaction_id'], 1)

#     def test_create_reaction_failure(self):
#         emoji_type = "\U0001F600"  # 😀 emoji
#         self.mock_client.from_().insert().execute.return_value = MagicMock(status_code=400, data=None)
#         result = self.reaction_model.CreateReaction(emoji_type=emoji_type, user_id=1, post_id=1)
#         self.assertIsNone(result)

#     def test_update_reaction_success(self):
#         emoji_type = "\U0001F600"  # 😀 emoji
#         self.mock_client.from_().update().eq().execute.return_value = MagicMock(status_code=204)
#         result = self.reaction_model.UpdateReaction(reaction_id=1, emoji_type=emoji_type)
#         self.assertTrue(result)

#     def test_update_reaction_failure(self):
#         emoji_type = "\U0001F600"  # 😀 emoji
#         self.mock_client.from_().update().eq().execute.return_value = MagicMock(status_code=400)
#         result = self.reaction_model.UpdateReaction(reaction_id=1, emoji_type=emoji_type)
#         self.assertFalse(result)

#     def test_delete_reaction_success(self):
#         self.mock_client.from_().delete().eq().execute.return_value = MagicMock(status_code=204)
#         result = self.reaction_model.DeleteReaction(reaction_id=1)
#         self.assertTrue(result)

#     def test_delete_reaction_failure(self):
#         self.mock_client.from_().delete().eq().execute.return_value = MagicMock(status_code=400)
#         result = self.reaction_model.DeleteReaction(reaction_id=1)
#         self.assertFalse(result)

# if __name__ == '__main__':
#     unittest.main()
