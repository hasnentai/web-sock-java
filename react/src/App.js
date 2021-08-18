import React, { useState } from "react";
import { Client } from '@stomp/stompjs';

const SOCKET_URL = "ws://localhost:8080/ws-message";

const App = () => {
  let onConnected = () => {
    console.log("Connected!!");
    client.subscribe("/topic/message/Tower1", function (msg) {
      if (msg.body) {
        var jsonBody = JSON.parse(msg.body);
        if (jsonBody.message) {
          console.log(jsonBody.message);
        }
      }
    });

    
  };

  let onDisconnected = () => {
    console.log("Disconnected!!");
  };


  const [client,setClient]=useState(new Client({
    brokerURL: SOCKET_URL,
    reconnectDelay: 5000,
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
    onConnect: onConnected,
    onDisconnect: onDisconnected,
  }));

  

  useEffect(() => {

    client.activate();
    return ()=>{
      client.deactivate();
      debugger;
      alert("I am getting deactivated")
    }
  }, []);

  const pub = () =>{
    client.publish({destination:"/app/sendMessage/Tower1",body:JSON.stringify({"message":"Hello"})})
  }

  return (
    <div>
      
      <button onClick={()=>{pub()}}>Pub</button>
    </div>
  );
};

export default App;
