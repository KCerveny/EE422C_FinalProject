This folder has a simple client-server program where the client and server _ARE IN THE SAME PROJECT_ but with no shared code. The client sends a message with an input from the command line, converts it to JSON and sends it to the server. The server parses the JSON message, then processes the input and returns it to all the clients. This contains examples for socket connections, readers and writers, JSON messages, and the observer pattern, so it should help you if you're still stuck on how to get started.

To run the example:

1. Download the "example" folder and put it under the src directory of a project.
2. Add the Gson library to that project and create build configurations for both the client and the server's main classes.
3. First run the server and then one or more clients.
4. On the console of any of the clients, type in `upper,hello,3` and press enter.
5. Check each of the clients. You should see the message `From server: HELLO HELLO HELLO` on each console.

Other example inputs/outputs:

```
lower,HeLlO,2 --> hello hello
strip,H e l l o World,1 --> HelloWorld
```

Please note that this is just an example, and you will most likely need to change a _lot_ of things for your implementation of the bidding system. **ESPECIALLY, MAKE SURE TO CREATE DIFFERENT PROJECTS FOR THE CLIENT AND SERVER, EVEN THOUGH THEY'RE IN THE SAME PROJECT IN THIS EXAMPLE.**
