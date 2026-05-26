import SockJS from "sockjs-client";
import Stomp from "stompjs";
import authService from "./authService";

let stompClient = null;

const connect = (onMessageReceived) => {
    const socket = new SockJS("http://localhost:8080/ws-notifications");
    stompClient = Stomp.over(socket);

    const headers = {
        'Authorization': `Bearer ${authService.getToken()}`
    };

    stompClient.connect(headers, () => {
        stompClient.subscribe("/topic/matches", (message) => {
            if (message.body === "REFRESH_MATCHES") {
                onMessageReceived();
            } else {
                onMessageReceived(JSON.parse(message.body));
            }
        });
    }, (error) => {
        console.error("WebSocket connection error:", error);
    });
};

const disconnect = () => {
    if (stompClient) {
        stompClient.disconnect(() => {
        });
    }
};

export default { connect, disconnect };