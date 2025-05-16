import importlib

def handle_request(request):
    class_name = request.get("class")
    method_name = request.get("method")
    data = request.get("data", {})

    try:
        module = importlib.import_module(f"services.{class_name.lower()}")
        cls = getattr(module, class_name)()
        method = getattr(cls, method_name)
        return method(data)
    except Exception as e:
        return {"error": str(e)}
