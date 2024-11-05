import unittest
from unittest.mock import patch, MagicMock
from Group_10 import ReactionsModel

class TestReactionsModel(unittest.TestCase):


    @patch('Group_10.supabase_db.models.reactions.create_client')  # Ensure the correct module path
    def setUp(self, mock_create_client):
        self.mock_client = MagicMock()
        mock_create_client.return_value = self.mock_client
        self.reaction_model = ReactionsModel()

    def test_create_reaction_success(self):
            self.mock_client.from_().insert().execute.return_value = MagicMock(data=[{'reaction_id': 1}])
            result = self.reactions_model.CreateReaction(
                user_id=123,
                post_id=456,
                emoji_type=1
            )
            self.assertIsNotNone(result)
            self.assertEqual(result['reaction_id'], 1)

    def test_create_reaction_duplicate(self):
            self.mock_client.from_().insert().execute.return_value = MagicMock(status_code=409)
            result = self.reactions_model.CreateReaction(
                user_id=123,
                post_id=456,
                emoji_type=2
            )
            self.assertIsNone(result)

    def test_read_reaction_by_user_id(self):
            self.mock_client.from_().select().eq().execute.return_value = MagicMock(data=[{'reaction_id': 1}])
            result = self.reactions_model.ReadReaction(user_id=123)
            self.assertIsNotNone(result)
            self.assertEqual(result[0]['reaction_id'], 1)

    def test_update_reaction_success(self):
            self.mock_client.from_().update().eq().execute.return_value = MagicMock(status_code=204)
            result = self.reactions_model.UpdateReaction(
                reaction_id=1,
                emoji_type=3
            )
            self.assertTrue(result)

    def test_delete_reaction_success(self):
            self.mock_client.from_().delete().eq().execute.return_value = MagicMock(status_code=204)
            result = self.reactions_model.DeleteReaction(reaction_id=1)
            self.assertTrue(result)

if __name__ == '__main__':
    unittest.main()