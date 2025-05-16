import pickle
import os

base_dir = os.path.dirname(__file__)
model_path = os.path.join(base_dir, "model.pkl")
vec_path = os.path.join(base_dir, "vectorizer.pkl")

with open(model_path, "rb") as f:
    model = pickle.load(f)
with open(vec_path, "rb") as f:
    vectorizer = pickle.load(f)

def predict_category(text):
    X = vectorizer.transform([text])
    return model.predict(X)[0]
