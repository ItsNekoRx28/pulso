package es.nekaso.pulso.monitor;

import org.springframework.stereotype.Service;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MonitorService {

    final ConcurrentHashMap<Integer, Monitor> monitorMap;

    public MonitorService(){
        this.monitorMap = new ConcurrentHashMap<>();
    }

    public LinkedList<Monitor> get(){
        return new LinkedList<>(monitorMap.values());
    }

    public Monitor get(int id){
        return monitorMap.get(id);
    }

}