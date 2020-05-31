package cn.redarm.socketclient.socket;

import cn.redarm.socketclient.comm.SocketComm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

@Slf4j
public class Client {

    private Socket socket;

    public boolean connected = false;

    private String username;

    public Client(String username) {
        try {
            socket = new Socket(SocketComm.IP,SocketComm.PORT);

            this.username = username;
            connected = true;
        } catch (UnknownHostException e) {
            log.warn("connect error" + e);
        } catch (IOException e) {
            log.warn("connect error" + e);
        }
    }

    @Async
    public void sendText(String text) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        writer.write(text + "\n");

        writer.flush();

        writer.close();
    }
}