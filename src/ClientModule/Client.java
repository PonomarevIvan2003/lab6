package ClientModule;

import ClientModule.util.Console;
import common.exceptions.IncorrectInputInScriptException;
import common.util.Request;
import common.util.Response;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class Client {
    private String host;
    private int port;
    private ClientModule.util.Console console;
    private Socket socket;
    private ByteBuffer byteBuffer = ByteBuffer.allocate(64000);
    private SocketChannel socketChannel;
    private SocketAddress address;
    private Selector selector;

    public Client(String host, int port, Console console){
        this.host = host;
        this.port = port;
        this.console = console;
    }

    public void run() throws IOException, IncorrectInputInScriptException {
        try{
            System.out.println("Клиент запущен!");
            socketChannel = SocketChannel.open();
            address = new InetSocketAddress("localhost", this.port);
            socketChannel.connect(address);
            socketChannel.configureBlocking(false);
            selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_WRITE);
            send(new Request("loadCollection", null));
            Response responseLoading = receive();
            System.out.println(responseLoading.getResponseBody());
            Request requestToServer = null;
            Response serverResponse = null;
            do {
                requestToServer = serverResponse != null ? console.interactiveMode(serverResponse.getResponseCode()) :
                        console.interactiveMode(null);
                if (requestToServer.isEmpty()) continue;
                send(requestToServer);
                serverResponse = receive();
                System.out.println(serverResponse.getResponseBody());
            } while (!requestToServer.getCommandName().equals("exit"));
        } catch (IOException | ClassNotFoundException exception) {
            System.out.println("К сожалению, сервер не найден!");
            System.exit(0);
        }
    }

    private void makeByteBufferToRequest(Request request) throws IOException {
        byteBuffer.put(serialize(request));
        byteBuffer.flip();
    }

    private byte[] serialize(Request request) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(request);
        byte[] buffer = byteArrayOutputStream.toByteArray();
        objectOutputStream.flush();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        objectOutputStream.close();
        return buffer;
    }

    private Response deserialize() throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteBuffer.array());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Response response = (Response) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        byteBuffer.clear();
        return response;
    }

    private void send(Request request) throws IOException {

        //makeByteBufferToRequest(request);
        SocketChannel channel = null;

        while (channel == null) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (SelectionKey key : selectionKeys) {
                if (key.isWritable()) {
                    channel = (SocketChannel) key.channel();
                    byte[] buffer = serialize(request);
                    if (buffer.length <= byteBuffer.array().length) {
                        byteBuffer.put(serialize(request));
                        byteBuffer.flip();
                        channel.write(ByteBuffer.allocateDirect(1));
                        channel.write(byteBuffer);
                    } else {
                        int limit = byteBuffer.limit();
                        int size = (int) (Math.ceil((double) limit / (double) byteBuffer.array().length));
                        ByteBuffer[] ret = new ByteBuffer[size];
                        int srcIndex = 0;
                        channel.write(ByteBuffer.allocateDirect(size));
                        for (int i = 0; i < size; i++) {
                            int bufferSize = byteBuffer.array().length;
                            if (i == size - 1) {
                                bufferSize = byteBuffer.limit() % byteBuffer.array().length;
                            }
                            byte[] dest = new byte[bufferSize];
                            System.arraycopy(byteBuffer.array(), srcIndex, dest, 0, dest.length);
                            srcIndex += bufferSize;
                            ret[i] = ByteBuffer.wrap(dest);
                            ret[i].position(0);
                            ret[i].limit(ret[i].capacity());
                            channel.write(ret[i]);
                        }
                    }
                    channel.register(selector, SelectionKey.OP_READ);
                    break;
                }
            }
        }
        byteBuffer.clear();
    }

    private Response receive() throws IOException, ClassNotFoundException {
        SocketChannel channel = null;
        while (channel == null) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (SelectionKey key : selectionKeys) {
                if (key.isReadable()) {
                    channel = (SocketChannel) key.channel();
                    channel.read(byteBuffer);
                    byteBuffer.flip();
                    channel.register(selector, SelectionKey.OP_WRITE);
                    break;
                }
            }
        }
        return deserialize();
    }
}
