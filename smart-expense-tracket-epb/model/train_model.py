import pickle
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.linear_model import LogisticRegression

descriptions = [
    "milk and bread", "uber ride", "paid rent", 
    "swiggy order", "electricity bill", "train ticket"
]
categories = ["Food", "Transport", "Rent", "Food", "Utilities", "Transport"]

vectorizer = TfidfVectorizer()
X = vectorizer.fit_transform(descriptions)
model = LogisticRegression()
model.fit(X, categories)

with open("model/vectorizer.pkl", "wb") as f:
    pickle.dump(vectorizer, f)

with open("model/model.pkl", "wb") as f:
    pickle.dump(model, f)

print("✅ Model trained and saved.")
