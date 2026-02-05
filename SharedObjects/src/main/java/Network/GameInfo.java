package Network;

import java.util.Objects;

public class GameInfo {
    
    private final String id;
    private final String name;
    private final String host;
    private final int port;

    public GameInfo(String id, String name, String host, int port){
        this.id = id;
        this.name = name;
        this.host = host;
        this.port = port;
    }

    public String getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getHost(){
        return this.host;
    }

    public int getPort(){
        return this.port;
    }

     @Override
    public String toString() {
        return String.format("GameInfo{id='%s', name='%s', host='%s', port=%d}", id, name, host, port);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameInfo)) return false;
        GameInfo that = (GameInfo) o;
        return port == that.port &&
               Objects.equals(id, that.id) &&
               Objects.equals(name, that.name) &&
               Objects.equals(host, that.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, host, port);
    }
}
