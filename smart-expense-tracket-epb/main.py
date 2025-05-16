import pika
import json
from rpc_handler import handle_request

def on_request(ch, method, props, body):
    try:
        request = json.loads(body)
        print("Received:", request)

        response = handle_request(request)

        ch.basic_publish(
            exchange='',
            routing_key=props.reply_to,
            properties=pika.BasicProperties(correlation_id=props.correlation_id),
            body=json.dumps(response),
        )
        ch.basic_ack(delivery_tag=method.delivery_tag)
    except Exception as e:
        print("Error:", str(e))

def main():
    connection = pika.BlockingConnection(pika.ConnectionParameters(host="localhost"))
    channel = connection.channel()

    queues = ["create.category", "predict.transaction", "give.summary"]
    for q in queues:
        channel.queue_declare(queue=q)
        channel.basic_consume(queue=q, on_message_callback=on_request)

    print("Waiting for RPC requests...")
    channel.start_consuming()

if __name__ == "__main__":
    main()
