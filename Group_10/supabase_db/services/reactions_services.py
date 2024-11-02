# reaction_services.py
from fastapi import HTTPException, status
from .models.reactions import ReactionsModel

# Initialize ReactionsModel
reactions_model = ReactionsModel()

# Add a reaction to a post
def AddReaction(emoji_type: int, user_id: int, post_id: int):
    reaction = reactions_model.CreateReaction(emoji_type=emoji_type, user_id=user_id, post_id=post_id)
    if not reaction:
        raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Failed to add reaction")
    return reaction

# Update an existing reaction
def UpdateReaction(reaction_id: int, emoji_type: int):
    success = reactions_model.UpdateReaction(reaction_id=reaction_id, emoji_type=emoji_type)
    if not success:
        raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Failed to update reaction")
    return {"message": "Reaction updated successfully"}

# Delete a reaction
def DeleteReaction(reaction_id: int):
    success = reactions_model.DeleteReaction(reaction_id=reaction_id)
    if not success:
        raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Failed to delete reaction")
    return {"message": "Reaction deleted successfully"}