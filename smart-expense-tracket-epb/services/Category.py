from model.ai_model import predict_category

class TransactionService:
    def predictTransaction(self, data):
        description = data.get("description", "")
        if not description:
            return {"error": "Missing description"}
        
        prediction = predict_category(description)
        return {"predictedCategory": prediction, "status": "success"}
