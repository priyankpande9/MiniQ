# MiniQ
MiniQ - A Message Queuing which provides Restful API endpoints access for producer and consumer to produce and consume the message respectively..Once consumer processed the message it will notify MiniQ that message with messageIds has been processed and hence delete these message. Also if processing is taking more than 30 second then message can be read by any consumer.

# Prerequisites
Java, Maven, DropWizard and MySql

# Build Steps
1. Run `mvn clean install` to build your application
2. Start application with `java -jar target/miniqf-0.0.1-SNAPSHOT.jar server config.yml`
3. To check that your application is running enter url `http://localhost:8080`

# API Access Details

Base Url- `http://localhost:8080/message`

1. Http Method : `/GET` receives all messages in queue - 

  [
  {
    "id": "0e65a8e7-7573-44c3-9566-b8ac1722a0e0",
    "description": "First Message",
    "timeStamp": 1476600016000
  },
  {
    "id": "5131b394-6719-4a65-9fa4-8df7c94c0303",
    "description": "testing Message",
    "timeStamp": 1476600255000
  }]
  
2. HTTP Method : `/POST` allows creation of new message in the queue
     
     Request Body : `{ "description" : "First Message" }`
     
3. HTTP Method : `/DELETE/{id}` - allows for acknowledgement of message by a consumer - necessary unless messages are having a configurable timeout which keeps track of all messages duration received by a consumer.

Response :
{
  "status": "Success"
}

# Data Storage Abstraction : 
   Data storage is abstracted, as before running application dataSource can be changed to different data storage.
   In this case I have used MySQL

# Scalability :
  - Faster access of data can be obtained through Redis/MongoDB for high volume data.
  - Load balancer can be used for distributing the data to multiple machines.
  - A push forward approach can be used where Producer can always fill the buffer of consumer to a threshold, in which way consumer do       not have to wait for producer to produce the message in the queue.

# Design : 
   - In this I have tried to use Factory pattern to decide the which kind of action needs to be taken for an incoming request.
   - Also messaging queues implementation uses pub/sub models where data is asynchronously published/subsribed to the queue.
