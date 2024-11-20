# from fastapi import HTTPException, status
# from ..models.reactions import ReactionsModel

# reactions_model = ReactionsModel()

# def create_reaction(user_id: int, post_id: int, emoji_type: int):
#     reaction = reactions_model.CreateReaction(user_id=user_id, post_id=post_id, emoji_type=emoji_type)
#     if not reaction:
#         raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Failed to create reaction")
#     return reaction

# def read_reactions(reaction_id: int = None, user_id: int = None, post_id: int = None):
#     reactions = reactions_model.ReadReaction(reaction_id=reaction_id, user_id=user_id, post_id=post_id)
#     if not reactions:
#         if reaction_id:
#             raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Reaction not found")
#         return []  
#     return reactions

# def update_reaction(reaction_id: int, emoji_type: int):
#     success = reactions_model.UpdateReaction(reaction_id=reaction_id, emoji_type=emoji_type)
#     if not success:
#         raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Failed to update reaction")
#     return {"message": "Reaction updated successfully"}

# def delete_reaction(reaction_id: int):
#     success = reactions_model.DeleteReaction(reaction_id=reaction_id)
#     if not success:
#         raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Failed to delete reaction")
#     return {"message": "Reaction deleted successfully"}

# # Convenience functions
# def get_reactions_by_post(post_id: int):
#     return read_reactions(post_id=post_id)

# def get_reactions_by_user(user_id: int):
#     return read_reactions(user_id=user_id)

# def get_reaction(reaction_id: int):
#     reactions = read_reactions(reaction_id=reaction_id)
#     return reactions[0] if reactions else None

# def get_user_reaction_to_post(user_id: int, post_id: int):
#     reactions = read_reactions(user_id=user_id, post_id=post_id)
#     return reactions[0] if reactions else None